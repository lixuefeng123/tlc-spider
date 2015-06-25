package cn.com.fero.tlc.spider.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;
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

    public static Object json2Object(String jsonStr, Class clazz, String... excludePropery) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludePropery);
        jsonConfig.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
            @Override
            public String transformToJavaIdentifier(String key) {
                char[] chars = key.toCharArray();
                chars[0] = Character.toLowerCase(chars[0]);
                return new String(chars);
            }
        });
        JSONObject jsonObject = JSONObject.fromObject(jsonStr, jsonConfig);

        JsonConfig javaConfig = new JsonConfig();
        javaConfig.setRootClass(clazz);
        javaConfig.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
            @Override
            public String transformToJavaIdentifier(String key) {
                char[] chars = key.toCharArray();
                chars[0] = Character.toLowerCase(chars[0]);
                return new String(chars);
            }
        });
        return JSONObject.toBean(jsonObject, javaConfig);
    }

    public static String getString(String jsonStr, String key) {
        if (StringUtils.isEmpty(jsonStr) || StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }

        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        return jsonObject.get(key).toString();
    }

    public static List getArray(String jsonStr, String key, Class clazz, String... excludeProperty) {
        if (StringUtils.isEmpty(jsonStr) || StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }

        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        JSONArray array = jsonObject.getJSONArray(key);
        Iterator iterator = array.iterator();
        List objectList = new ArrayList();

        while (iterator.hasNext()) {
            Object obj = iterator.next();
            objectList.add(json2Object(obj.toString(), clazz, excludeProperty));
        }

        return objectList;
    }
}
