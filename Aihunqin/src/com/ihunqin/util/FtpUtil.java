package com.ihunqin.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {

	/**
	 * 通过ftp上传文件
	 * 
	 * @param url
	 *            ftp服务器地址 如:192.168.1.110
	 * @param port
	 *            端口如:21
	 * @param username
	 *            登录名
	 * @param password
	 *            密码
	 * @param remotePath
	 *            上传到ftp服务器的磁盘路径
	 * @param fileNamPath
	 *            要上传的文件路径
	 * @param fileName
	 *            要上传的文件名
	 * @return
	 */
	public static String ftpUpload(String url, String port, String username,
			String password, String remotePath, String fileNamPath,
			String fileName) {
		FTPClient ftpClient = new FTPClient();
		FileInputStream fis = null;
		String returnMessage = "0";
		try {
			ftpClient.connect(url, Integer.parseInt(port));
			boolean loginResult = ftpClient.login(username, password);
			int returnCode = ftpClient.getReplyCode();
			if (loginResult && FTPReply.isPositiveCompletion(returnCode)) {// 如果登陆成功
				// ftpClient.makeDirectory(remotePath);
				// 设置上传目录
				ftpClient.changeWorkingDirectory(remotePath);
				ftpClient.setBufferSize(1024);
				ftpClient.setControlEncoding("UTF-8");
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				fis = new FileInputStream(fileNamPath);
				ftpClient.storeFile(fileName, fis);
				fis.close();
				ftpClient.logout();
				returnMessage = "1";// 上传成功
			} else {// 如果登录失败
				returnMessage = "0";
			}
		} catch (NumberFormatException e) {

			e.printStackTrace();
		} catch (SocketException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
			throw new RuntimeException("FTP客户端出错!", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return returnMessage;
	}
}
