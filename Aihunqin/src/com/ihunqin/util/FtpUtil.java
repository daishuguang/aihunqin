package com.ihunqin.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {

	/**
	 * ͨ��ftp�ϴ��ļ�
	 * 
	 * @param url
	 *            ftp��������ַ ��:192.168.1.110
	 * @param port
	 *            �˿���:21
	 * @param username
	 *            ��¼��
	 * @param password
	 *            ����
	 * @param remotePath
	 *            �ϴ���ftp�������Ĵ���·��
	 * @param fileNamPath
	 *            Ҫ�ϴ����ļ�·��
	 * @param fileName
	 *            Ҫ�ϴ����ļ���
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
			if (loginResult && FTPReply.isPositiveCompletion(returnCode)) {// �����½�ɹ�
				// ftpClient.makeDirectory(remotePath);
				// �����ϴ�Ŀ¼
				ftpClient.changeWorkingDirectory(remotePath);
				ftpClient.setBufferSize(1024);
				ftpClient.setControlEncoding("UTF-8");
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				fis = new FileInputStream(fileNamPath);
				ftpClient.storeFile(fileName, fis);
				fis.close();
				ftpClient.logout();
				returnMessage = "1";// �ϴ��ɹ�
			} else {// �����¼ʧ��
				returnMessage = "0";
			}
		} catch (NumberFormatException e) {

			e.printStackTrace();
		} catch (SocketException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
			throw new RuntimeException("FTP�ͻ��˳���!", e);
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
