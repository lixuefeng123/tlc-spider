package cn.com.fero.tlc.spider.http.test;

import cn.com.fero.tlc.spider.util.IdGeneratorUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Created by wanghongmeng on 2015/8/21.
 */
public class IdGeneratorUtilTest {
    @Test
    public void testGenerateUUID() throws UnsupportedEncodingException {
        for (int a = 0; a < 10; a++) {
            String id = IdGeneratorUtil.generateId();
            System.out.println(id + ", " + id.length());
        }
    }
}
