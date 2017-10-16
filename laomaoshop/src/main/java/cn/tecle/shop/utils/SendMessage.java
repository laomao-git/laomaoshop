package cn.tecle.shop.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SendMessage {
	private static String APIKEY ;
	private static long REGISTER_TEMPLATEID;
	private static String ENCODING = "UTF-8";
	//模板发送接口的http地址
    private static String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";
	static{
		Properties pro = new Properties();
		InputStream in = SendMessage.class.getClassLoader().getResourceAsStream("yunpian.properties");
		try {
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		APIKEY = pro.getProperty("APIKEY");
		REGISTER_TEMPLATEID = Long.parseLong(pro.getProperty("register.templateId"));
	}
	/**
	 * 
	 * @param phone  需要发送短信的手机号码
	 * @param code   要发送的验证码
	 * @param tpl_id 调用SendMessage.XXX   例如SendMessage.REGISTER_TEMPLATEID表示注册模板
	 * @return
	 */
	public static String sendMessage(String phone,String code,Long tpl_id){
		String mobile = null;
		String tpl_value = null;
		try {
			mobile = URLEncoder.encode(phone,ENCODING);
			tpl_value = URLEncoder.encode("#code#",ENCODING) +"="
			        + URLEncoder.encode(code, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", APIKEY);
        params.put("tpl_id", String.valueOf(tpl_id));
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
		return HttpUtils.post(URI_TPL_SEND_SMS, params);
	}
	public static void main(String[] args) {
		System.out.println(sendMessage("13201791761", "135965", SendMessage.REGISTER_TEMPLATEID));
	}
}
