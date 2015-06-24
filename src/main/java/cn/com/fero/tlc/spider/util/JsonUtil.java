package cn.com.fero.tlc.spider.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wanghongmeng on 2015/6/24.
 */
public final class JsonUtil {
    private JsonUtil () {
        throw new UnsupportedOperationException();
    }

    public static String object2Json(Object obj) {
        if (null == obj) {
            return StringUtils.EMPTY;
        }

        return JSONObject.fromObject(obj).toString();
    }

    public static Object json2Object(String jsonStr, Class clazz) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }

        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        return JSONObject.toBean(jsonObject, clazz);
    }

    public static String getString(String jsonStr, String key) {
        if (StringUtils.isEmpty(jsonStr) || StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }

        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        return (String) jsonObject.get(key);
    }

    public static List getArray(String jsonStr, String key, Class clazz) {
        if (StringUtils.isEmpty(jsonStr) || StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }

        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        JSONArray array = jsonObject.getJSONArray(key);
        Iterator iterator = array.iterator();
        List objectList = new ArrayList();

        while (iterator.hasNext()) {
            Object obj = iterator.next();
            objectList.add(json2Object(obj.toString(), clazz));
        }

        return objectList;
    }
}
