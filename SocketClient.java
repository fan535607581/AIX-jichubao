package ftp.ke.hu;

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
import java.net.ServerSocket;
import java.io.*;
import java.net.*;


@DesignerComponent(version = 1,
    description = "made in fan hao jie",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "images/extension.png")

@SimpleObject(external = true)

public class SocketClient extends AndroidNonvisibleComponent 
{
    Socket socket = null;
    OutputStream ou = null;
    String buffer = "";
    String geted1;
    MyThread mt;
    final int CONNECT = 100001;
    public Handler myHandler = new Handler() 
    {
        @Override
        public void handleMessage(Message msg)
	{GetMessage(msg.obj.toString()); 
    }
 };
    @SimpleFunction(description = "start")
    public void startConnect()
    {
        if(socket != null){
            mt = new MyThread(CONNECT);
            mt.start();
        }else{  GetMessage("连接未创建！"); }
    }
	
    @SimpleFunction(description = "start")
    public void connect(String ip , int port)
    {
        if(socket == null){
            mt = new MyThread(CONNECT);
            mt.setDK(port);
            mt.setIP(ip);
            mt.start();
        }else{ GetMessage("连接创建失败！"); }
    }

    @SimpleEvent
    public void GetMessage(String s)
    {
        EventDispatcher.dispatchEvent(this, "GetMessage", s);
    }
    class MyThread extends Thread 
    {
 
        public String IP;
        public int DK;
        public int js;
        public int[]i=new int[1024];
        Message message_2;
        Message msg;
        public int flag;
        public MyThread(int flag) { this.flag = flag; }
        public void setText(int s , int b , int k){ i[b] = s;  js = k; }
        public void setIP(String ip){ IP = ip; }
        public void setDK(int port){ DK = port;}
	    
        @Override
        public void run() 
	{
            switch(flag)
	    {
                case CONNECT:
                    try{} catch (IOException e){}break; 
            }
        }
    }
}
