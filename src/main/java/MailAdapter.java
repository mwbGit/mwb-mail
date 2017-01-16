import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * Created by MengWeiBo on 2017-01-13
 */
public class MailAdapter {

    private static final String mailUser = "13029727767@163.com";
    private static final String mailPassword = "***";
    private static final String mailHost = "smtp.163.com";
    private static final String mailAuth = "true";
    private static Properties props;
    private static MimeMessage message;

    public static void main(String[] args) throws Exception {
        List<String> recipients = Arrays.asList("2829535630@qq.com");
        String subject = "邮件主题";
        String content = "<br><h3>邮件内容</h3><br>https://123.sogou.com/";
        sendMail(recipients, subject, content);
    }

    public static void sendMail(List<String> recipients, String subject, String content) throws Exception {
        initProperties();

        //1 设置收件人

        for (String recipient : recipients) {
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient, "USER_DD", "UTF-8"));

        }
        // 4. Subject: 邮件主题
        message.setSubject(subject, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");

        // 6. 设置显示的发件时间
        message.setSentDate(new Date());

        // 7. 保存前面的设置
        message.saveChanges();

        // 8. 将该邮件保存到本地
//        OutputStream out = new FileOutputStream("MyEmail.eml");
//        message.writeTo(out);
//        out.flush();
//        out.close();

        // 发送邮件
        Transport.send(message);
    }

    private static void initProperties() throws Exception {
        props = new Properties();
        props.put("mail.smtp.auth", mailAuth);
        props.put("mail.smtp.host", mailHost);
        // 发件人的账号
        props.put("mail.user", mailUser);
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", mailPassword);
        // 构建授权信息，用于进行SMTP进行身份验证

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(
                props.getProperty("mail.user"));
        message.setFrom(form);
    }

}
