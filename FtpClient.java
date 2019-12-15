package com.cn.test;
 
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient; 
import java.io.File;
import java.io.FileInputStream;
 
public class Test {
    public static void testFTPClient() {
        try {
            FTPClient ftpClient = new FTPClient();  //创建一个FTPClient对象        
            ftpClient.connect("192.168.41.99", 21);   //创建ftp链接
            ftpClient.login("anonymous", ""); //登录ftp，使用用戶名和密碼，没有设置的话使用anonymous，密码随意
            FileInputStream inputStream = new FileInputStream(new File("E:\\q.jar"));     //读取本地文件
            //设置为被动模式(如上传文件夹成功，不能上传文件，注释这行，否则报错refused:connect  )
            //ftpClient.enterLocalPassiveMode();
            //设置上传路径
            ftpClient.changeWorkingDirectory("pub/data");
            //修改上传文件格式   2是二进制流
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //System.out.println("1");
            //上传文件
            ftpClient.storeFile("q2.jar", inputStream);
            //System.out.println("2");
            //关闭链接
            ftpClient.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) { testFTPClient(); }
}
