package cn.com.fero.tlc.spider.http.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wanghongmeng on 2015/8/3.
 */
public class TestGetByJDK {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFtzcuQtoMO739-mwrqoaWPi4&eqs=pLsqoWtgfIG6osjbygAtCud8xqO5CwnfW%2FMb%2F1qtjEICoi1ZVmfZcmuCOexPe1wuwFIBJ&ekv=7");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
        connection.disconnect();
    }
}
