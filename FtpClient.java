package FTP;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        String ftpHost = "172.203.160.104";
        String ftpUserName = "GBNFTP";
        String ftpPassword = "ASDFGHJKL;'";
        int ftpPort = 21;
        String ftpPath = "/FTP/FTPDOWN";
        String localPath = "D:\\FTP\\员工信息表.txt";//"D:\\FTP\\upload.txt";
        String downloadPath = "D:\\FTP";
        String uploadFileName = "员工上传.txt";//"ftp_upload.txt";
        String downloadFileName = "员工下载.txt";//"ftp_download.txt";

        // 上传一个文件
        try {
            System.out.println("=============上传文件操作===============");
            FileInputStream in = new FileInputStream(new File(localPath));
            boolean isUp = FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, uploadFileName, in);
            if (isUp) {
                System.out.println("上传成功 !");
            } else {
                System.out.println("上传失败 !");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }
}
