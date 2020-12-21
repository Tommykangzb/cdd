package com.example.cdd.connect;


/**
 * Created by Excalibur on 2017/5/30.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.cdd.BlueToothMatch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ConnectService {
    private static final String NAME="MainActivity";

    // UUID-->通用唯一识别码，能唯一地辨识咨询
    private static final UUID MY_UUID=UUID.fromString(
            "00001101-0000-1000-8000-00805F9B34FB");//串口
    // "fa87c0d0-afac-11de-8a39-0800200c9a66");

    private final BluetoothAdapter mAdapter;
    private final Handler mHandler ;
    private AcceptThread mAcceptThread;
    //write
    private ConnectThread mConnectThread;
    //read
    private ConnectedThread mConnectedThread;
//    private ConnectedThread read;
//    private ConnectedThread read_two;
//    private ConnectedThread read_three;

    private BluetoothSocket socket;
    private BluetoothSocket socket_two;
    private BluetoothSocket socket_three;

    private int mState;
    public static final int STATE_NONE = 0;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;


    //构造函数
    public ConnectService(Context context, Handler handler){
        mAdapter=BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
        socket=null;
        socket_two=null;
        socket_three=null;
    }

    //synchronized线程锁，状态改变就将消息发送出去
    private synchronized void setState(int state){
        mState = state;
        mHandler.obtainMessage(BlueToothMatch.MESSAGE_STATE_CHANGE,state,-1)
                .sendToTarget();
    }
    //返回当前状态
    public synchronized int getState(){
        return mState;
    }

    //开始，先清空接受和被接受线程，开启监听状态
    public synchronized void start(){
        if(mConnectThread !=null){
            mConnectThread.cancel();
            mConnectThread=null;
        }

        if(mConnectedThread !=null){
            mConnectedThread.cancel();
            mConnectedThread=null;
        }

        if (mAcceptThread==null){
            mAcceptThread=new AcceptThread();
            mAcceptThread.start();
        }
        setState(STATE_LISTEN);
    }

    // 取消 Connecting Connected状态下的相关线程，然后运行新的mConnectThread线程
    public synchronized void connect(BluetoothDevice device){
        if(mState == STATE_CONNECTED){
            if(mConnectThread !=null){
                mConnectThread.cancel();
                mConnectThread=null;
            }
        }
        if(mConnectedThread !=null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        if(mAcceptThread != null){
            mAcceptThread.cancel();
            mAcceptThread =null;
        }
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    // 开启一个ConnectThread来管理对应的当前连接。之前取消任意现存的mConnectThread
    // mConnectThread，mAcceptThread线程，然后开启新的mConnectThread，传入当前
    // 刚刚接受的socket连接，最后通过Handler来通知UI连接
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device){
        if (socket!=null){
            ConnectedThread read= new ConnectedThread(socket);
            read.start();
        }
        Message msg = mHandler.obtainMessage(BlueToothMatch.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(BlueToothMatch.DEVICE_NAME,device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    // 停止所有相关线程，设当前状态为none
    public synchronized void stop(){
        if(mConnectThread !=null){
            mConnectThread.cancel();
            mConnectThread=null;
        }
        if(mConnectedThread !=null){
            mConnectedThread.cancel();
            mConnectedThread=null;
        }
        if(mAcceptThread !=null){
            mAcceptThread.cancel();
            mAcceptThread=null;
        }
        setState(STATE_NONE);
    }

    // 在STATE_CONNECTED状态下，调用mConnectedThread里的write方法，写入byte
    public void write(byte[]out){
        ConnectedThread r;
        synchronized (this){
            if(mState != STATE_CONNECTED)
                return;
            r = mConnectedThread;
        }
        r.write(out);
    }

    // 连接失败的时候处理，通知UI，并设为STATE_LISTEN状态
    private void connectionFailed(){
        setState(STATE_LISTEN);

        Message msg = mHandler.obtainMessage(BlueToothMatch.MESSAGE_TOAST);
        Bundle bundle=new Bundle();
        bundle.putString(BlueToothMatch.TOAST,"连接不到设备");
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        ConnectService.this.start();
    }

    // 当连接失去的时候，设为STATE_LISTEN
    private void connectionLost(){
        setState(STATE_LISTEN);

        Message msg = mHandler.obtainMessage(BlueToothMatch.MESSAGE_TOAST);
        Bundle bundle=new Bundle();
        bundle.putString(BlueToothMatch.TOAST,"设备连接中断");
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        ConnectService.this.start();
    }

    // 创建监听线程，准备接受新连接。使用阻塞方式，调用BluetoothServerSocket.accept()
    private class AcceptThread extends Thread{
        //用于监听
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread(){
            BluetoothServerSocket tmp = null;
            try{
                tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME,MY_UUID);
            }catch (IOException e){}
            mmServerSocket = tmp;
        }


        public void run(){
            int i=0;
            while(true) {
                try {
                    if (socket == null) {
                        socket = mmServerSocket.accept();
                        if (socket != null) {
                            connected(socket, socket.getRemoteDevice());
                            i++;
                        }
                    }
                    if (socket_two == null) {
                        socket_two = mmServerSocket.accept();
                        if (socket_two != null) {
                            connected(socket_two, socket_two.getRemoteDevice());
                            i++;
                        }
                    }
                    if (socket_three == null) {
                        socket_three = mmServerSocket.accept();
                        if (socket_three != null) {
                            connected(socket_three, socket_three.getRemoteDevice());
                            i++;
                        }
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }
                if (i==3){
                    setState(3);
                    break;
                }
            }
        }

        public void cancel(){
            try{
                mmServerSocket.close();
            }catch (IOException e){}
        }
    }

    // 专连接线程，门用来对外发出连接对方蓝牙的请求并进行处理
    // 构造函数里通过BluetoothDevice.createRfcommSocketToServiceRecord(),
    // 从待连接的device产生BluetoothSocket，然后在run方法中connect
    // 成功后调用 BluetoothChatService的connnected（）方法，定义cancel（）在关闭线程时能关闭socket
    //客服端
    private class ConnectThread extends Thread{
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device){
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            mmDevice=device;
            BluetoothSocket tmp = null;
            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try{
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            }catch (IOException e){}
            mmSocket = tmp;
        }

        public void run(){
            // Cancel discovery because it will slow down the connection
            mAdapter.cancelDiscovery();
            try{
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                //是否需要发送消息？
                mmSocket.connect();
            }catch (IOException e){
                connectionFailed();
                // Unable to connect; close the socket and get out
                try{
                    mmSocket.close();
                }catch (IOException e2){}

                //ChatService.this.start();
                return;
            }
            synchronized(ConnectService.this){
                mConnectedThread = null;
            }
            connected(mmSocket,mmDevice);
        }

        public void cancel(){
            try{
                mmSocket.close();
            }catch (IOException e){}
        }
    }

    // 双方蓝牙连接后一直运行的线程。构造函数中设置输入输出流。
    // Run方法中使用阻塞模式的InputStream.read()循环读取输入流
    // 然后psot到UI线程中更新聊天信息。也提供了write()将聊天消息写入输出流传输至对方，
    // 传输成功后回写入UI线程。最后cancel()关闭连接的socket

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
//        private final InputStream mmInStream;
//        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
        }
//            InputStream tmpIn = null;
//            OutputStream tmpOut=null;
        // Get the input and output streams, using temp objects because
        // member streams are final
//            try{
//                tmpIn=mmSocket.getInputStream();
//                tmpOut=mmSocket.getOutputStream();
//            }catch (IOException e){}
//            mmInStream = tmpIn;
//            mmOutStream = tmpOut;


        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            InputStream mmInStream = null;
            try {
                mmInStream = this.mmSocket.getInputStream();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            while (true) {
                try {
                    // Log.d(TAG, "get inputstream");
                    // Read from the InputStream
                    if ((bytes = mmInStream.read(buffer)) > 0) {
                        byte[] buf_data = new byte[bytes];
                        for (int i = 0; i < bytes; i++) {
                            buf_data[i] = buffer[i];
                        }
                        //收到数据 ，待处理
                        String s = new String(buf_data);
                        //Log.d(TAG, s);
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = s;
                        mHandler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    try {
                        mmInStream.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    break;
                }
            }
        }

        public void cancel(){
            try{
                mmSocket.close();
            }catch (IOException e){}
        }
        //将二进制数据传到各个客服端
        public void write(byte[] buffer) {
            if (socket == null) {
                //   Log.d(TAG, "socket is not connect");
            } else {
                try {
                    OutputStream os = socket.getOutputStream();
                    os.write(buffer);
                    //  Log.d(TAG, "write to outputstream success,socket");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (socket_two == null) {
                // Log.d(TAG, "socket_two is not connect");
            } else {
                try {
                    OutputStream os = socket_two.getOutputStream();
                    os.write(buffer);
                    //    Log.d(TAG, "write to outputstream success,socket_two");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (socket_three == null) {
                //  Log.d(TAG, "socket_three is not connect");
            } else {
                try {
                    OutputStream os = socket_three.getOutputStream();
                    os.write(buffer);
                    //  Log.d(TAG, "write to outputstream success,socket_three");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
