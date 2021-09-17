package util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/*
 * MapString向外提供map和String的相互转换
 */
public class MapString {

	//Map转换为String
	public static String MapToString(Map map)
	{
		java.util.Map.Entry entry;
		StringBuffer sb = new StringBuffer();
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
		{
			entry = (java.util.Map.Entry) iterator.next();
			sb.append(entry.getKey().toString()).append("、")
					.append(null == entry.getValue() ? "" : entry.getValue().toString())
					.append(iterator.hasNext() ? "^" : "");
		}
		
//		三重des加密
		String s = null;
		try {
			s=Triple_DES.encode(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	//String转换为Map
	public static HashMap<String,String> StringToMap(String mapString)
	{
//		三重des解密
		String ms = null;
		try {
			ms=Triple_DES.decode(mapString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HashMap map = new HashMap();
		java.util.StringTokenizer items;
		for (StringTokenizer entrys = new StringTokenizer(ms, "^"); entrys.hasMoreTokens(); 
				map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
			items = new StringTokenizer(entrys.nextToken(), "、");
		return map;
	}
	
	public static String MapToStringX(Map map)
	{
		java.util.Map.Entry entry;
		StringBuffer sb = new StringBuffer();
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
		{
			entry = (java.util.Map.Entry) iterator.next();
			sb.append(entry.getKey().toString()).append(";")
					.append(null == entry.getValue() ? "" : entry.getValue().toString())
					.append(iterator.hasNext() ? "|" : "");
		}
		return sb.toString();
	}

	public static HashMap<String,String> StringToMapX(String mapString)
	{
		HashMap map = new HashMap();
		java.util.StringTokenizer items;
		for (StringTokenizer entrys = new StringTokenizer(mapString, "|"); entrys.hasMoreTokens(); map
				.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
			items = new StringTokenizer(entrys.nextToken(), ";");
		return map;
	}
}
