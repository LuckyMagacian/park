package net.imwork.yangyuanjian.common.interfaces;

import com.alibaba.fastjson.JSON;

/**
 * Created by thunderobot on 2017/11/18.
 */
public interface ToJson {
    default  public String toJson(){
        return JSON.toJSONString(this);
    }
    static <T> T fromJson(String json,Class<T> clazz){
        return JSON.parseObject(json,clazz);
    }
}
