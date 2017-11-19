package net.imwork.yangyuanjian.park.consts.enums;

import net.imwork.yangyuanjian.common.interfaces.GetEnumType;

/**
 * Created by thunderobot on 2017/11/18.
 */
public enum ParkStatus implements GetEnumType {
    Normal("正常","01"),
    Freeze("冻结","02"),
    Delete("删除","03");
    String statusName;
    String id;

    ParkStatus(String statusName, String id) {
        this.statusName = statusName;
        this.id = id;
    }

    @Override
    public String toString() {
       return id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
