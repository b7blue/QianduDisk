package util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JOptionPane;

public class txttest {                                                    //读取txt文件内容
    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){                                  //使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    public static boolean detect(String keyword,String str) {
    	boolean ret = str.contains(keyword);
    	if(str.contains(keyword)){                                  //屏蔽词
        	JOptionPane.showMessageDialog(null, "内容不规范");         //弹窗提示出错
        	
    	}   
    	return ret;
    }
    public static void main(String[] args){
        File file = new File("C:\\Users\\86181\\Desktop\\test.txt");                            //文件位置

        String str=txt2String(file);
        System.out.print(txt2String(file));                            //打印txt文件内容（可删）
        if(str.contains("北风")||str.contains("。1")){                      //屏蔽词
        	JOptionPane.showMessageDialog(null, "内容不规范");         //弹窗提示出错
        	}     
    } 
}