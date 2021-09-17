package Database;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import util.MapString;

public class FileList {
	File file = null;
	String root = "D:\\QianduDiskUpload";
	public FileList(String Name, String Route) {
		if(Name == null) {
			return;
		}
		//默认打开用户（用户组）对应的存储空间根目录
		file = new File(root + "\\" + Name);
		//若找不到该文件夹，则重新创建
		if(file.exists() == false) {
			file.mkdir();
			return;
		}else {
			
			if(Route == null || Route.equals("")) {
				file = new File(root + "\\" + Name);
			//若路径不为空，则打开指定路径的文件夹
			}else {
				file = new File(root + "\\" + Name + "\\" + Route);
			}
		}
	}
	
	public String getFileList(){
		HashMap<String,String> map = new HashMap<>();
		
		if(file == null) {
			map.put("Command", "FileList Empty");
			return MapString.MapToString(map);
			
		}
		map.put("Command", "FileList");
		File[] FileList = file.listFiles();
		for(int i = 0;i < FileList.length;i++)
		{
			HashMap<String,String> temp = new HashMap<>();
			//文件大小
			double Filesize = 0;
			if(FileList[i].isDirectory())//如果是文件夹
				temp.put("Filesize", "-");
			else if(FileList[i].length() < 1024) // < 1KB
			{
				Filesize = (double)Math.round(FileList[i].length()*100)/100;
				temp.put("Filesize", Filesize + " B");
			}
			else if(FileList[i].length() > 1024 && FileList[i].length() < 1024 * 1024)// >1KB <1MB
			{
				Filesize = (double)Math.round(FileList[i].length() / (double)1024*100)/100;
				temp.put("Filesize", Filesize + " KB");
			}
			else if(FileList[i].length() > 1024 * 1024 && 
					FileList[i].length() < 1024 * 1024 * 1024)// >1MB <1GB
			{
				Filesize = (double)Math.round(FileList[i].length() / (double)(1024 *1024)*100)/100;
				temp.put("Filesize", Filesize + " MB");
			}
			else
			{
				Filesize = (double)Math.round(FileList[i].length() / (double)(1024 *1024*1024)*100)/100;
				temp.put("Filesize", Filesize + " GB");
			}
			//修改时间
			Calendar cal = Calendar.getInstance();   
			long time = FileList[i].lastModified();
			cal.setTimeInMillis(time);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			temp.put("ModTime",format.format(cal.getTime()));
			//打包
			map.put(FileList[i].getName(), MapString.MapToStringX(temp));
		}
		return MapString.MapToString(map);
			
		
	}
}
