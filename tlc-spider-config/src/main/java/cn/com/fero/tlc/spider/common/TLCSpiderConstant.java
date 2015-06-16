package cn.com.fero.tlc.spider.common;

/**
 * Created by wanghongmeng on 2015/5/28.
 */
public final class TLCSpiderConstant {
    public static final String APPLICATION_NAME = "gtsboss";
    public static final String APPLICATION_TITLE = "招商银行|交易银行";
    public static final String APPLICATION_CHARACTER_ENCODING = "UTF-8";
    public static final String APPLICATION_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final int SERVICE_VERIFY_REGIST_HOUR = 48;
    public static final int SERVICE_VERIFY_RESET_MINUTE = 30;
    public static final String SERVICE_MAIL_TITLE_REGIST = "招商银行|交易银行--邮箱身份验证";
    public static final String SERVICE_MAIL_TITLE_RESET = "招商银行|交易银行--密码修改验证";
    public static final String SERVICE_MAIL_CONTENT_REGIST = "<div style=\"color: #000;font-weight: normal;font-family: 'Microsoft YaHei','Arial';\">\n" +
            "<div style=\"height:24px;background-color:rgb(224,236,249);\">\n" +
            "<img class=\"img_s\" src=\"{0}/gtsboss/image/footer_logo.png\">\n" +
            "</div>\n" +
            "<div style=\"height:20px;text-indent:3px;font-weight:bold; font-size:16px;line-height:20px;  color: #ea5404;\">\n" +
            "<p>&nbsp;&nbsp;尊敬的{1}，您好！</p>\n" +
            "</div>\n" +
            "<div style=\"text-indent:3px;font-size:14px;\">\n" +
            "<br>\n" +
            "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;感谢您在招商银行|交易银行网站上注册，请点击下面的链接完成您的激活，链接有效期为" + SERVICE_VERIFY_REGIST_HOUR +
            "小时，激活成功后自动失效。</p>\n" +
            "<br>\n" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
            "<a href=\"\n" +
            "{2}\n" +
            "\" target=\"_blank\" >点击这里立即激活</a>\n" +
            "<p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;链接如下：</p>\n" +
            "<p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
            "{2}\n" +
            "</p>\n" +
            "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果以上链接无法点击，请将上面链接的地址复制你的浏览器（如IE、Chrome等）的地址栏直接打开。激活成功后，您方能正常登录使用！</p>\n" +
            "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;谢谢！ </p>\n" +
            "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-- </p>\n" +
            "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;招商银行|交易银行 </p>\n" +
            "</div>\n" +
            "<div style=\"font-size:16px; color: #fff;height:24px; line-height:24px; background-color: #5f6ac0;\">\n" +
            "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（这是一封自动产生的email，请勿直接回复。）</p>\n" +
            "</div>\n" +
            "</div>";
    public static final String SERVICE_MAIL_CONTENT_RESET = "<div style=\"color: #000;font-weight: normal;font-family: 'Microsoft YaHei','Arial';\">\n" +
            "\t\t\t\t\t\t\t<div style=\"height:24px;background-color:rgb(224,236,249);\">\n" +
            "\t\t\t\t\t\t\t<img class=\"img_s\" src=\"{0}/gtsboss/image/footer_logo.png\">\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t<div style=\"height:20px;text-indent:3px;font-weight:bold; font-size:16px;line-height:20px;  color: #ea5404;\">\n" +
            "\t\t\t\t\t\t\t<p>&nbsp;&nbsp;尊敬的{1}，您好！</p>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t<div style=\"text-indent:3px;font-size:14px;\">\n" +
            "\t\t\t\t\t\t\t<br>\n" +
            "\t\t\t\t\t\t\t<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;感谢您使用招商银行|交易银行网站，请点击下面的链接重置您的密码，链接有效期为30分钟。</p>\n" +
            "\t\t\t\t\t\t\t<br>\n" +
            "\t\t\t\t\t\t\t&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
            "\t\t\t\t\t\t\t<a href=\"\n" +
            "\t\t\t\t\t\t\t{2}\n" +
            "\t\t\t\t\t\t\t\" target=\"_blank\" >点击这里立即重置密码</a>\n" +
            "\t\t\t\t\t\t\t<p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;链接如下：</p>\n" +
            "\t\t\t\t\t\t\t<p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
            "\t\t\t\t\t\t\t{2}\n" +
            "\t\t\t\t\t\t\t</p>\n" +
            "\t\t\t\t\t\t\t<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果以上链接无法点击，请将上面链接的地址复制你的浏览器（如IE、Chrome等）的地址栏直接打开。</p>\n" +
            "\t\t\t\t\t\t\t<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;谢谢！ </p>\n" +
            "\t\t\t\t\t\t\t<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-- </p>\n" +
            "\t\t\t\t\t\t\t<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;招商银行|交易银行 </p>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t<div style=\"font-size:16px; color: #fff;height:24px; line-height:24px; background-color: #5f6ac0;\">\n" +
            "\t\t\t\t\t\t\t<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（这是一封自动产生的email，请勿直接回复。）</p>\n" +
            "\t\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t\t\t</div>";
    public static final String CONTROLLER_SESSION_INDEX = "menuIndex";
    public static final String CONTROLLER_SESSION_VERIFY_CODE = "verifyCode";
    public static final String CONTROLLER_SESSION_LOGIN_USER = "loginUser";
    public static final String DAO_KEY_SALT = "3ca508c502cb1d6309df71949fc84659d3c87463e290e6fb39f7d54adb4e3a16";
    public static final int DAO_PAGE_QUERY_SIZE = 20;
    public static final int DAO_PAGE_INFORMATION_SIZE = 6;
    public static final int DAO_PAGE_DEFAULT_NUMBER = 1;
    public static final String DAO_HTTP_PARAMETER_SEPARATOR = ",";
    public static final String UTIL_IP_URL_TAOBAO = "http://ip.taobao.com/service/getIpInfo.php?ip=";
    public static final String UTIL_IP_UNKNOW = "未分配或者内网IP";
    public static final String UTIL_MAIL_FROM = "gtsboss@fero.com.cn";
    public static final String UTIL_MAIL_PASSWORD = "fero1234";
    public static final String UTIL_MAIL_SMTP = "smtp.exmail.qq.com";
    public static final int UTIL_MAIL_PORT = 25;
    public static final String UTIL_ENCRYPT_PASSWORD_ALGORITHM = "PBKDF2WithHmacSHA1";
    public static final int UTIL_ENCRYPT_PASSWORD_SALT_BYTE_SIZE = 32;
    public static final int UTIL_ENCRYPT_PASSWORD_HASH_BYTE_SIZE = 32;
    public static final int UTIL_ENCRYPT_PASSWORD_ALGORITHM_ITERATIONS = 1000;
    public static final String UTIL_ENCRYPT_TEXT_ALGORITHM = "DES";
    public static final String UTIL_IP_ADDRESS_LOCALHOST = "127.0.0.1";


    private TLCSpiderConstant() {
        throw new UnsupportedOperationException();
    }


}
