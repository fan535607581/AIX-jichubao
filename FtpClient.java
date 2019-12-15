package com.cloudpower.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

public class Ftp {

	private String localfilename; //本地文件名	
	private String remotefilename; //远程文件名
	private FtpClient ftpClient; //FTP客户端

	/* @param user 用户名* @param password  密码 * @param path  服务器路径 * @author 周玲斌 */
	public void connectServer(String ip, int port, String user,
			String password, String path) {
		try {
			/* ******连接服务器的两种方法****** */
			// 第一种方法
			// ftpClient = new FtpClient();
			// ftpClient.openServer(ip, port);
			// 第二种方法
			ftpClient = new FtpClient(ip);

			ftpClient.login(user, password);
			// 设置成2进制传输
			ftpClient.binary();
			System.out.println("login success!");
			if (path.length() != 0) {
				// 把远程系统上的目录切换到参数path所指定的目录
				ftpClient.cd(path);
			}
			ftpClient.binary();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 关闭连接
	 * 
	 * @author 周玲斌
	 * @date 2012-7-11
	 */
	public void closeConnect() {
		try {
			ftpClient.closeServer();
			System.out.println("disconnect success");
		} catch (IOException ex) {
			System.out.println("not disconnect");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param localFile
	 *            本地文件
	 * @param remoteFile
	 *            远程文件
	 * @author 周玲斌
	 * @date 2012-7-11
	 */
	public void upload(String localFile, String remoteFile) {
		this.localfilename = localFile;
		this.remotefilename = remoteFile;
		TelnetOutputStream os = null;
		FileInputStream is = null;
		try {
			// 将远程文件加入输出流中
			os = ftpClient.put(this.remotefilename);
			// 获取本地文件的输入流
			File file_in = new File(this.localfilename);
			is = new FileInputStream(file_in);
			// 创建一个缓冲区
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			System.out.println("upload success");
		} catch (IOException ex) {
			System.out.println("not upload");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (os != null) {
						os.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	}

	public static void main(String agrs[]) {

		String filepath[] = { "/temp/aa.txt", "/temp/regist.log" };
		String localfilepath[] = { "C:\\tmp\\1.txt", "C:\\tmp\\2.log" };

		Ftp fu = new Ftp();
		/*
		 * 使用默认的端口号、用户名、密码以及根目录连接FTP服务器
		 */
		fu.connectServer("127.0.0.1", 22, "anonymous", "IEUser@", "/temp");

		// 下载
		for (int i = 0; i < filepath.length; i++) {
			fu.download(filepath[i], localfilepath[i]);
		}

		String localfile = "E:\\号码.txt";
		String remotefile = "/temp/哈哈.txt";
		// 上传
		fu.upload(localfile, remotefile);
		fu.closeConnect();
	}
}
