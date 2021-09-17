package util;

import java.util.Date;
import java.util.Properties;
 
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * ①、Message 类:javax.mail.Message 类是创建和解析邮件的核心 API，这是一个抽象类，通常使用它的子类javax.mail.internet.MimeMessage 类。它的实例对象表示一份电子邮件。客户端程序发送邮件时，首先使用创建邮件的 JavaMail API 创建出封装了邮件数据的 Message 对象，然后把这个对象传递给邮件发送API（Transport 类） 发送。客户端程序接收邮件时，邮件接收API把接收到的邮件数据封装在Message 类的实例中，客户端程序在使用邮件解析API从这个对象中解析收到的邮件数据。

　　②、Transport 类：javax.mail.Transport 类是发送邮件的核心API 类，它的实例对象代表实现了某个邮件发送协议的邮件发送对象，例如 SMTP 协议，客户端程序创建好 Message 对象后，只需要使用邮件发送API 得到 Transport 对象，然后把 Message 对象传递给 Transport 对象，并调用它的发送方法，就可以把邮件发送给指定的 SMTP 服务器。

　　③、Store 类：javax.mail.Store 类是接收邮件的核心 API 类，它的实例对象代表实现了某个邮件接收协议的邮件接收对象，例如 POP3 协议，客户端程序接收邮件时，只需要使用邮件接收 API 得到 Store 对象，然后调用 Store 对象的接收方法，就可以从指定的 POP3 服务器获得邮件数据，并把这些邮件数据封装到表示邮件的 Message 对象中。

　　④、Session 类：javax.mail.Session 类用于定义整个应用程序所需的环境信息，以及收集客户端与邮件服务器建立网络连接的会话信息，例如邮件服务器的主机名、端口号、采用的邮件发送和接收协议等。Session 对象根据这些信息构建用于邮件收发的 Transport 和 Store 对象，以及为客户端创建 Message 对象时提供信息支持。
 *
 */

public class mailSender {

    private static String senderAddress = "704081912@qq.com";
    private static String senderAccount = "704081912@qq.com";
    private static String senderPassword = "bsiiwvmhzlnkbbde";//授权码
    
    public static void sendMail(String recipientAddress,String data) throws Exception {
    	Session ses = setSession();//    根据连接邮箱所需的信息初始化session
    	
    	Message msg = setMessage(ses,recipientAddress,data);//    初始化邮件对象
    	
    	Transport trans = setTransport(ses);//    初始化邮件发送对象
    	
        //发送邮件
        trans.sendMessage(msg,msg.getAllRecipients());
        //完事之后关闭邮件连接
        trans.close();
    }
     
//    根据连接邮箱所需的信息初始化session
    private static Session setSession() throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");//设置用户的认证方式
        props.setProperty("mail.transport.protocol", "smtp");//设置传输协议
        props.setProperty("mail.smtp.host", "smtp.qq.com");//设置发件人的SMTP服务器地址
        
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.ssl.enable", "true");//出于安全考虑采用ssl连接邮箱
        
        Session session = Session.getInstance(props);
        
        session.setDebug(true);//设置调试信息在控制台打印出来
        
        return session;
    }
     
//    初始化邮件对象
    private static MimeMessage setMessage(Session session,String recipientAddress,String data) throws Exception{
        
        MimeMessage msg = new MimeMessage(session);//创建一封邮件的实例对象
        
        msg.setFrom(new InternetAddress(senderAddress));//设置发件人地址
        /**
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));//设置收件人地址
        
        msg.setSubject("千度网盘验证码","UTF-8");//设置邮件主题
        
        msg.setContent("验证码是"+data, "text/html;charset=UTF-8");//设置邮件正文
        
        msg.setSentDate(new Date());//设置邮件的发送时间,默认立即发送
         
        return msg;
    }
    
//    初始化邮件发送对象
    private static Transport setTransport(Session session) throws Exception{
    	
        Transport transport = session.getTransport();
        transport.connect(senderAccount, senderPassword);//设置发件人的账户名和密码
        
        return transport;
    }
}