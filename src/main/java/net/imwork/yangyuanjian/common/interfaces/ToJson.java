package net.imwork.yangyuanjian.common.interfaces;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by thunderobot on 2017/11/18.
 */
public interface ToJson {
    Function<Object, Object> jsonDeal = e -> {
        if (e instanceof ToJson) {
            return ((ToJson) e).toJsonObject();
        } else if (e instanceof Collection || e instanceof Map) {
            return toJsonArray(e);
        } else {
            return JSONObject.parse(JSON.toJSONString(e));
        }
    };

    default String toJson() {
        return toJsonObject().toString();
    }

    default JSONObject toJsonObject() {
        Field[] fields = this.getClass().getDeclaredFields();
        JSONObject jobj = new JSONObject(fields.length,true);
        Arrays.asList(fields)
                .stream()
                .sorted(Comparator.comparing(Field::getName))
                .filter(e -> !Modifier.isStatic(e.getModifiers()))
                .forEach(e -> {
                    try {
                        e.setAccessible(true);
                        String name = e.getName();
                        Object value = e.get(this);
                        if(value==null)
                            return ;
                        if (value instanceof Map) {
                            jobj.put(name, toJsonObject(value));
                        } else if (value instanceof Collection) {
                            jobj.put(name, toJsonArray(value));
                        } else if (value instanceof Number || value instanceof Character || value.getClass().isEnum() || value instanceof Boolean) {
                            jobj.put(name, value.toString());
                        } else if (value instanceof CharSequence) {
                            jobj.put(name, value);
                        } else {
                            jobj.put(name, JSON.parseObject(JSON.toJSONString(value)));
                        }
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                });
        return jobj;
    }

    static JSONObject toJsonObject(Object obj) {
        if(obj==null)
            return null;
        if (obj instanceof ToJson) {
            return ((ToJson) obj).toJsonObject();
        } else if (obj instanceof Map) {
            JSONObject jobj = new JSONObject();
            ((Map) obj).entrySet().stream().forEach(e -> {
                Map.Entry entry = (Map.Entry) e;
                Object key = entry.getKey();
                Object value = entry.getValue();
                key = jsonDeal.apply(key);
                value = jsonDeal.apply(value);
                jobj.put(key.toString(), value);
            });
            return jobj;
        } else {
            return JSONObject.parseObject(JSON.toJSONString(obj));
        }

    }

    static JSONArray toJsonArray(Object obj) {
        if(obj==null)
            return null;
        if (obj instanceof Collection) {
            Collection collection = (Collection) obj;
            JSONArray jsonArray = new JSONArray();
            collection.stream().forEach(e -> jsonArray.add(jsonDeal.apply(e)));
            return jsonArray;
        }
        throw new IllegalArgumentException("method toJsonArray can only accept collection as arg !");
    }

    static String toJson(Object obj) {
        if (obj instanceof Collection) {
            return toJsonArray(obj).toJSONString();
        } else if (obj instanceof Map) {
            return toJsonObject(obj).toString();
        } else if (obj instanceof ToJson) {
            return ((ToJson) obj).toJson();
        } else {
            return JSONObject.toJSONString(obj);
        }
    }
}
