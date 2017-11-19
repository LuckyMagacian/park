package net.imwork.yangyuanjian.park.consts.enums;

import net.imwork.yangyuanjian.common.interfaces.GetEnumType;

/**
 * Created by thunderobot on 2017/11/18.
 */
public enum ServerTypeEnum implements GetEnumType {
    Pay("主动付费","01"),
    Appoint("预约","02"),
    Buckle("代扣","03");

    String serverName;
    String id;

    ServerTypeEnum(String serverName, String id) {
        this.serverName = serverName;
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
