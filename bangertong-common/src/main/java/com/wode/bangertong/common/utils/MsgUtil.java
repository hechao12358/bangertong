package com.wode.bangertong.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URLEncoder;

public class MsgUtil {
    private static Log log = LogFactory.getLog(MsgUtil.class);

    public static void sendSMS(String[] mobiles, String smsContent) throws Exception {
        String cellphones = String.join(",", mobiles);
        smsContent = URLEncoder.encode(smsContent, "GBK");
        String url = "https://sms.53api.com/sdk/SMS?cmd=send&uid=2521&psw=f4c799048655da9c953382f55388a0e5&mobiles=" + cellphones + "&msgid=10000&msg=" + smsContent;
        try {
            JSONObject paramObj = HttpsUtil.getHttpResponse(url);
            if (paramObj != null && StringUtils.isNotBlank(paramObj.getString("code"))) {
                String msg = SmsCode.getCodeName(paramObj.getString("code"));
                paramObj.put("msg", msg);
            }
            log.info("sending cellphone and code is :" + paramObj.toString());
        } catch (Exception e) {
            log.error("sending cellphone and code is : exception null");
        }

    }

    public enum SmsCode {

        SUCCESS("100", "成功"),
        FAIL("101", "失败"),
        VEFIFY("102", "验证失败(密码不对)"),
        CELL_ERROR("103", "号码有错(接收号码格式错误)"),
        CONTENT_ERROR("104", "内容有错(敏感内容)"),
        FAST("105", "操作频率过快(每秒十次)"),
        LIMIT_ERROR("106", "限制发送(无条数)"),
        PARAM("107", "参数不全(请查看提交的参数)");

        private String code;

        private String name;

        private SmsCode(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return name;
        }

        public static String getCodeName(String code) {
            SmsCode[] values = SmsCode.values();
            for (SmsCode sms : values) {
                if (code.equals(sms.getCode())) {
                    return sms.getName();
                }
            }
            return null;
        }
    }

    public static void sendMTV2(String mobiles, String smsContent) {
        String url = "http://47.102.112.118:8080/sdk/smssdk!mt2.action";
        String username = "600362";
        String password = "wdgp600362";
        String srcnumber = "0363002"; //服务号（子号）

        String timestamp = DateTimeUtils.getCurrentTimestamp().toString(); //时间戳，系统当前时间。

        StringBuffer response = new StringBuffer();

        JSONObject rootJson = new JSONObject();
        rootJson.put("username", username);
        rootJson.put("timestamp", timestamp);

        StringBuffer sb = new StringBuffer(); //
        sb.append(username + password + timestamp);

        JSONArray itemList = new JSONArray();
        {
            String dstnumbers = mobiles;
            String msgcontent = smsContent;

            JSONObject itemOjb = new JSONObject();
            itemOjb.put("srcnumber", srcnumber);
            itemOjb.put("dstnumbers", dstnumbers);
            itemOjb.put("msgcontent", msgcontent);
            itemList.add(itemOjb);

            sb.append(dstnumbers + msgcontent);
        }
        rootJson.put("items", itemList);

        String checkhash = MD5Util.MD5Encode(sb.toString(), "utf-8");
        rootJson.put("checkhash", checkhash);

        System.out.println(rootJson.toString());

        boolean result = false;
        try {
            String s = HttpsUtil.doPost(url, rootJson.toString());
            log.info("sending cellphone and code is :" + s);
        } catch (Exception e) {
            log.info("短信发送异常");

        }

    }
}