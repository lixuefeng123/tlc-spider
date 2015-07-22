package cn.com.fero.tlc.spider;

import cn.com.fero.tlc.spider.schedule.TLCSpiderScheduler;
import cn.com.fero.tlc.spider.util.TLCSpiderJsonUtil;
import cn.com.fero.tlc.spider.vo.TransObject;
import org.springframework.context.ApplicationContext;

import java.sql.*;

/**
 * Created by wanghongmeng on 2015/6/16.
 */
public final class TCLSpiderLZYHFix {

    private static ApplicationContext applicationContext;
    private static TLCSpiderScheduler p2pScheduler;


    public static void main(String[] args) throws Exception {
        fixLanzhou();
    }

    private static void fixLanzhou() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        System.out.println("开始修正兰州银行数据");
        Class.forName("org.postgresql.Driver").newInstance();
        String url = "jdbc:postgresql://122.49.31.239:5432/tailicai_production";
        Connection con = DriverManager.getConnection(url, "postgres", "3084dce76f8c14b618b4762f1b7495a9");
        Statement st = con.createStatement();
        String sql = "select * from p2p_spider_data where spider_id = 2";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt(1);
            String json = rs.getString(3);
            System.out.println(json);
            json = json.replaceAll("=>", ":");
            TransObject transObject = (TransObject) TLCSpiderJsonUtil.json2Object(json, TransObject.class);
            transObject.setAmount(transObject.getAmount().substring(0, transObject.getAmount().length() - 1));
            String fixJson = TLCSpiderJsonUtil.object2Json(transObject);
            fixJson = fixJson.replaceAll(":", "=>");
            fixJson = fixJson.replaceAll(",\"", ", \"");
            Statement stl = con.createStatement();
            stl.execute("update p2p_spider_data set finance_data = '" + fixJson + "' where id = " + id);
        }
        rs.close();
        st.close();
        con.close();
    }
}