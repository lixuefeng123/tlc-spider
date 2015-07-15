package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.common.TLCSpiderConstants;
import cn.com.fero.tlc.spider.http.TLCSpiderRequest;
import cn.com.fero.tlc.spider.util.TLCSpiderProxyUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by gizmo on 15/6/19.
 */
public class TLCSpiderProxyJobTest {

    @Test
    public void testInit() {
        TLCSpiderProxyUtil.fetchIpAddress();
    }

    @Test
    public void testUpdateProxy() throws IOException {
//        System.setProperty("http.proxySet", "true");
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_HOST, "36.99.1.121");
        System.setProperty(TLCSpiderConstants.SPIDER_CONST_HTTP_PROXY_PORT, "9797");
//        TLCSpiderProxyUtil.loadIpToMemory();
//        TLCSpiderProxyUtil.updateProxy();
        String response = TLCSpiderRequest.get("http://1111.ip138.com/ic.asp");
        System.out.println(response);

//        URL url = new URL("http://1111.ip138.com/ic.asp");
//        InetSocketAddress addr = new InetSocketAddress("134.249.139.239", 8080);
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
//        URLConnection conn = url.openConnection();
//        InputStream in = conn.getInputStream();
//        InputStreamReader input = new InputStreamReader(in, "utf-8");
//        BufferedReader bufReader = new BufferedReader(input);
//        String line;
//        while ((line = bufReader.readLine()) != null) {
//            System.out.println(line);
//        }
    }

}
