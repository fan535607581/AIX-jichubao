package ftpkehuduan.test;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;

public class FtpTest {
    public static void main(String[] args) {
        testUpload();
     
    public static void testUpload() {
        FTPClient ftpClient = new FTPClient();
        FileInputStream fis = null;
 
        try {
            ftpClient.connect("192.168.1.111");
            ftpClient.login("admin", "javaf");
 
            File srcFile = new File("F:\images\460.jpg");
            fis = new FileInputStream(srcFile);
            //设置上传目录
            ftpClient.changeWorkingDirectory("/lanjie/pic");
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("GBK");
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.storeFile("work.jpg", fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fis);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }
