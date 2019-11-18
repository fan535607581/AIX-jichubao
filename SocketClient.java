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
import java.io.*;//
import java.net.*;//



@DesignerComponent(version = 8,
    description = " made in fan hao jie     E-mail:535607581@qq.com ",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "images/extension.png")

@SimpleObject(external = true)
public Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
	{
        	GetMessage(msg.obj.toString());
        }
    };

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

    /*public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            GetMessage(msg.obj.toString());
        }
 
    };*/
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
    public void sendMessage(int s1 , int s2 ,int s3 ,int s4 , int s5 ,int s6 , int s7 , int s8 ,int s9 ,
                           int s10 , int s11 ,int s12 , int s )
    {        
        if(socket != null){
            mt = new MyThread(SENDMESSAGE);
            mt.setText(s1,0,s);mt.setText(s1,1,s);
            mt.setText(s2,2,s);mt.setText(s3,3,s);mt.setText(s4,4,s);
            mt.setText(s5,5,s);mt.setText(s6,6,s);mt.setText(s7,7,s);
            mt.setText(s8,8,s);mt.setText(s9,9,s);mt.setText(s10,10,s);
            mt.setText(s11,11,s);mt.setText(s12,12,s);
            mt.start();//启动发送
        }else{ GetMessage("连接未创建！");}
    }
    @SimpleFunction(description = "start")
    public void connect(String ip , int port){
        if(socket == null){
            mt = new MyThread(CONNECT);
            mt.setDK(port);
            mt.setIP(ip);
            mt.start();
        }else{ GetMessage("连接已创建！"); }
    }

    @SimpleEvent
    public void GetMessage(String s){
        EventDispatcher.dispatchEvent(this, "GetMessage", s);
    }
    class MyThread extends Thread {
 
        public String IP;
        public int DK;
        public int shu;
        public int[]i=new int[13];
        Message msg;
        public int flag;
        public MyThread(int flag) {
            this.flag = flag;
        }
        public void setText(int s , int b , int k){
            i[b] = s;  shu = k;
        }
        public void setIP(String ip){
            IP = ip;
        }
        public void setDK(int port){
            DK = port;
        }
        @Override
        public void run() {
           /* //////////////////////***********************************************************************
           BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream() ,"Unicode"));
           while(true)
		    {	
                int msg = 0;  int msk = 0; int msb = 0;
                msg = br.read();  msk = msg;
                msb = msg>>8;  msg = msg&0xff;
                if(msk > -1)
                {
                    message_2 = handler.obtainMessage();
                    message_2.obj = msb;
                    handler.sendMessage(message_2);
                    message_2 = handler.obtainMessage();
                    message_2.obj = msg;
                    handler.sendMessage(message_2);
                }
                else
                { socket.close();  br.close();}
            }
            //////////////////////**********************************************************/
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
                        ////////////////////////////////////*******************************************************
                        ServerSocket = new ServerSocket(5020);
                        Socket s = Socket.accept();
                        String ip = s.getInetAddress().getHostAddress();
                        ///////////////////////////****************************************************************
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
                         for(int j = 0; j<shu ;j++)ou.write(i[j]);
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
