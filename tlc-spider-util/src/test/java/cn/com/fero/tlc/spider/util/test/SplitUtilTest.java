package cn.com.fero.tlc.spider.util.test;

import cn.com.fero.tlc.spider.util.SplitUtil;
import org.junit.Test;

/**
 * Created by gizmo on 15/6/19.
 */
public class SplitUtilTest {

    @Test
    public void testSplitChineseNumber() {
        String str = "1,000元 最高投标金额";
        System.out.println(SplitUtil.splitChineseNumber(str, 3));
    }
}
