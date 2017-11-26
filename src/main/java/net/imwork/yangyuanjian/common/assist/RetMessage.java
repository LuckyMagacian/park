package net.imwork.yangyuanjian.common.assist;

import com.alibaba.fastjson.serializer.ClobSeriliazer;
import net.imwork.yangyuanjian.common.interfaces.ToJson;

import java.io.Serializable;

/**
 * Created by thunderobot on 2017/11/18.
 */
public class RetMessage implements ToJson{
    public static final String SUCCESS="0000";

    public static final String FAIL="9000";

    public static final String ERROR="9999";


    /**返回码*/
    private String retCode;
    /**返回信息*/
    private String retMessage;
    /**返回内容*/
    private Serializable detail;

    public RetMessage() {
    }

    public RetMessage(String retCode, String retMessage,Serializable  detail) {
        this.retCode = retCode;
        this.retMessage = retMessage;
        this.detail = detail;
    }

    public void setCodeAndMessage(String retCode, String retMessage){
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public void setAll(String retCode, String retMessage,Serializable  detail){
        this.retCode = retCode;
        this.retMessage = retMessage;
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "RetMessage{" +
                "retCode='" + retCode + '\'' +
                ", retMessage='" + retMessage + '\'' +
                ", detail=" + detail +
                '}';
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public Serializable getDetail() {
        return detail;
    }

    public void setDetail(Serializable detail) {
        this.detail = detail;
    }
}
