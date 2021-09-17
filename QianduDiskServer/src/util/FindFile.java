package util;

import java.io.*;
public class FindFile {

	public static String getDir(String Filename, String Dir) {

		ByteArrayOutputStream baoStream = new ByteArrayOutputStream(1024);
		PrintStream cacheStream = new PrintStream(baoStream);// ��ʱ���
		PrintStream oldStream = System.out;// ����ϵͳ���
		System.setOut(cacheStream);

		File d=new File(Dir);      //ȷ��Ҫ���ҵ�Ŀ¼dir;
		FindFile.find(Filename,d);                  //function�����ݹ�ʵ��;// �����ӡ������̨
		
		String DeleteFileDir = baoStream.toString();
		System.setOut(oldStream);// ��ԭ��ϵͳ���
		return DeleteFileDir;
	}
	
	public static void find(String filename,File dir)
	{
		String name = null;
		
		File[]files=dir.listFiles();
		for(File file:files)
		{
			if(file.isDirectory())                      //file��Ŀ¼ʱ�������µ���function����;
			{
					find(filename,file);
			}
			if(file.isFile() && filename.equals(file.getName()))  //fileʱ�ļ����ļ�����ͬʱ�����;
			{			
					name = file.getAbsolutePath();
					System.out.print(name);
					break;
			}
		}
		return;
	}

}

