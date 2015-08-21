package cn.com.fero.tlc.spider.util;

import org.apache.commons.lang.math.RandomUtils;

/**
 * Created by wanghongmeng on 2015/8/21.
 */
public class IdGeneratorUtil {

    private IdGeneratorUtil() {
        throw new UnsupportedOperationException();
    }

    public static String generateId() {
        long time = System.nanoTime();
        int random = RandomUtils.nextInt(Integer.MAX_VALUE);

        String id = String.valueOf(time) + String.valueOf(random);
        return id;
    }
}
