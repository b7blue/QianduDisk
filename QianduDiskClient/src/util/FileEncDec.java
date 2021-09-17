package util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileEncDec {

	public static File EncFile(File srcFile, File encFile, String key) throws Exception {

		InputStream fis = new FileInputStream(srcFile);
		OutputStream fos = new FileOutputStream(encFile);

		byte[] bytes = new byte[1024];
		int length = 0;
		while ((length = fis.read(bytes, 0, bytes.length)) != -1) {

			bytes = AES.encrypt(bytes, key);
			fos.write(bytes, 0, length);
			fos.flush();

		}

		fis.close();
		fos.close();

		return encFile;
	}

	public static File DecFile(File srcFile, File decFile, String key) throws Exception {

		InputStream fis = new FileInputStream(srcFile);
		OutputStream fos = new FileOutputStream(decFile);

		byte[] bytes = new byte[1024];
		int length = 0;
		while ((length = fis.read(bytes, 0, bytes.length)) != -1) {

			bytes = AES.decrypt(bytes, key);
			fos.write(bytes, 0, length);
			fos.flush();

		}

		fis.close();
		fos.close();

		return decFile;
	}

//	public static void main(String[] args) {
//
//		File srcFile = new File("C:\\Users\\70408\\Desktop\\test.txt"); // ��ʼ�ļ�
//		File encFile = new File("C:\\Users\\70408\\Desktop\\test1.txt"); // �����ļ�
//		File decFile = new File("C:\\Users\\70408\\Desktop\\test2.txt"); // �����ļ�
//		String key = "1234567890123456";
//
//		try {
//			EncFile(srcFile, encFile, key); // ���ܲ���
//			DecFile(encFile, decFile, key);//����
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
