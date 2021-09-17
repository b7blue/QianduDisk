package util;
import java.net.Socket;
import java.util.HashMap;

import Window.*;
import util.*;

public class Add_File_List
{
	static Main_Window mainwindow = null;
	static HashMap<String,String> data = null;
	public Add_File_List(Main_Window mainwindow,HashMap<String,String> data)
	{
		this.mainwindow = mainwindow;
		this.data = data;
	}

	public void get()
	{
		data.remove("Command");
		int FileNum = data.size();
		String [] Filename = new String[FileNum];
		String [] FileSize = new String[FileNum];
		String [] ModTime = new String[FileNum];
		
		int i = 0;
		for(String str : data.keySet())
		{
			HashMap<String,String> temp = MapString.StringToMapX(data.get(str));
			Filename[i] = str;	
			FileSize[i] = temp.get("Filesize");
			ModTime[i] = temp.get("ModTime");
			i++;
		}
		if(Filename != null && FileSize != null && ModTime != null && mainwindow != null)
			mainwindow.AddMulTableData(Filename, FileSize, ModTime, FileNum);
	}

}
