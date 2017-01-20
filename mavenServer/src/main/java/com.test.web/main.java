package com.test.web;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by zhenhanmo on 2017/1/17.
 */
public class main {
    public static void main(String[] args) {
        //String empURL="http://10.139.34.3:8082/MWGate/wmgw.asmx/MongateCsSpSendSmsNew?userId=DCB001&password=123456&pszMobis={0}&pszMsg={1}&iMobiCount={2}&pszSubPort=*";
        String empURL="http://120.27.166.221:8082/MWGate/wmgw.asmx/MongateCsSpSendSmsNew?userId=DCB001&password=123456&pszMobis={0}&pszMsg={1}&iMobiCount={2}&pszSubPort=*";



        String message="n你好";
        String sendTo="13113047357";
        try {
            String e = message.replaceAll("\\【多财宝\\】", "");
            Integer phoneCount = Integer.valueOf(sendTo.split(",").length);
            String url = MessageFormat.format(empURL, new Object[]{sendTo, URLEncoder.encode(e, Charsets.UTF_8.name()), phoneCount});
            Document doc = Jsoup.connect(url).get();
            String code = doc.body().getElementsByIndexEquals(0).html();
            if(!getFailMap().containsKey(code) && code.length() >= 10) {
                code = "0";
            } else {
                System.out.println("短信发送失败 >> GATEWAY:EMP ; SEND_TO:" + sendTo + " ; CODE:" + code);
            }
        } catch (IOException e) {
            System.out.println("短信发送失败 >> GATEWAY:EMP ; SEND_TO:" + sendTo);

        }

    }

    private static Map<String, Integer> getFailMap() {
        HashMap map = Maps.newHashMap();
        map.put("-1", Integer.valueOf(1));
        map.put("-2", Integer.valueOf(1));
        map.put("-10", Integer.valueOf(1));
        map.put("-11", Integer.valueOf(1));
        map.put("-12", Integer.valueOf(1));
        map.put("-13", Integer.valueOf(1));
        map.put("-14", Integer.valueOf(1));
        map.put("-101", Integer.valueOf(1));
        map.put("-102", Integer.valueOf(1));
        map.put("-103", Integer.valueOf(1));
        map.put("-200", Integer.valueOf(1));
        map.put("-999", Integer.valueOf(1));
        return map;
    }


    public String replaceTemplate(String content, Map<String, String> templateVal) {
        String res = content;
        Set set = templateVal.keySet();

        String name;
        for(Iterator it = set.iterator(); it.hasNext(); res = res.replace(String.valueOf(name), String.valueOf(templateVal.get(String.valueOf(name))))) {
            name = (String)it.next();
        }

        return res;
    }
}
