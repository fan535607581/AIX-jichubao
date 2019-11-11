package cn.roger.socket;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.runtime.util.*;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import android.graphics.drawable.GradientDrawable;
import android.graphics.Color;
import android.content.res.ColorStateList;
import android.view.View;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.Drawable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;



@DesignerComponent(version = 2,
    description = " made in fan hao jie    \n   e-mail:535607581@qq.com ",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "images/extension.png")

@SimpleObject(external = true)

public class SocketClient extends AndroidNonvisibleComponent {
    Socket socket = null;
    OutputStream ou = null;
    String buffer = "";
    String geted1;
    MyThread mt;
    final int CONNECT = 100001;
    final int SENDMESSAGE = 100002;
    final int CLOSE = 100003;
    public SocketClient(ComponentContainer container) {
        super(container.$form());
    }
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            GetMessage(msg.obj.toString());
        }
 
    };
    @SimpleFunction(description = "start")
    public void closeConnect(){
        if(socket != null){
            mt = new MyThread(CLOSE);
            mt.start();
        }else{
            GetMessage("连接未创建！");
        }
    }
    @SimpleFunction(description = "start")
    public void sendMessage(byte s1 , byte s2 ,byte s3 ,byte s4 , byte s5 ,byte s6 , byte s7 , byte s8 ,byte s9 ,
                           byte s10 , byte s11 ,byte s12 ){
        
        if(socket != null){
            mt = new MyThread(SENDMESSAGE);
            mt.setText(s1,0);mt.setText(s1,1);
            mt.setText(s2,2);mt.setText(s3,3);mt.setText(s4,4);
            mt.setText(s5,5);mt.setText(s6,6);mt.setText(s7,7);
            mt.setText(s8,8);mt.setText(s9,9);mt.setText(s10,10);
            mt.setText(s11,11);mt.setText(s12,12);
        }else{
            GetMessage("连接未创建！");
        }
    }
    @SimpleFunction(description = "start")
    public void connect(String ip , int port){
        if(socket == null){
            mt = new MyThread(CONNECT);
            mt.setDK(port);
            mt.setIP(ip);
            mt.start();
        }else{
            GetMessage("连接已创建！");
        }
    }

    @SimpleEvent
    public void GetMessage(String s){
        EventDispatcher.dispatchEvent(this, "GetMessage", "\n"+s);
    }
    class MyThread extends Thread {
 
        public String IP;
        public int DK;
        public byte[]i=new byte[13];
        Message msg;
        public int flag;
        public MyThread(int flag) {
            this.flag = flag;
        }
        public void setText(byte s , int b ){
            i[b] = s;
        }
        public void setIP(String ip){
            IP = ip;
        }
        public void setDK(int port){
            DK = port;
        }
        @Override
        public void run() {
            switch(flag){
                case CONNECT:
                    try {
                        socket = new Socket();
                        msg = myHandler.obtainMessage();
                        msg.obj = "开始连接";
                        myHandler.sendMessage(msg);
                        socket.connect(new InetSocketAddress(IP, DK), 1000);
                        ou = socket.getOutputStream();
                        msg = myHandler.obtainMessage();
                        msg.obj = "连接成功";
                        myHandler.sendMessage(msg);
                    } catch (SocketTimeoutException aa) {
                        msg = myHandler.obtainMessage();
                        msg.obj = "连接超时";
                        myHandler.sendMessage(msg);
                        socket = null;
                    } catch (IOException e) {
                        msg = myHandler.obtainMessage();
                        msg.obj = "未知错误";
                        myHandler.sendMessage(msg);
                        socket = null;
                    }
                break;
                case SENDMESSAGE:
                    try {
                         for(int j = 0; j<13 ;j++)ou.write(i[j]);
                        msg = myHandler.obtainMessage();
                        msg.obj = "发送完毕";
                        myHandler.sendMessage(msg);
                    }catch (IOException e) {
                        msg = myHandler.obtainMessage();
                        msg.obj = "未知错误";
                        myHandler.sendMessage(msg);
                    }
                break;
                case CLOSE:
                    try {
                        ou.close();
                        socket.close();
                        socket = null;
                        msg = myHandler.obtainMessage();
                        msg.obj = "关闭";
                        myHandler.sendMessage(msg);
                    }catch (IOException e) {
                        msg = myHandler.obtainMessage();
                        msg.obj = "未知错误";
                        myHandler.sendMessage(msg);
                    }
                break;
            }
        }
    }
}
