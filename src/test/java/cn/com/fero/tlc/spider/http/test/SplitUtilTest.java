package cn.com.fero.tlc.spider.http.test;

import org.junit.Test;

/**
 * Created by gizmo on 15/6/19.
 */
public class SplitUtilTest {

    @Test
    public void testExtract() {
        String str = "\n" +
                "                                 \t$(function() {\n" +
                "\t                                 \tdemo.init(\"1\", \"0.95\");\n" +
                "                                 \t})\n" +
                "                                 ";
        String trimStr = str.replaceAll("\\n", "").replaceAll("\t", "").replaceAll(" ", "");
        System.out.println(trimStr.split("\"")[3]);
    }
}
