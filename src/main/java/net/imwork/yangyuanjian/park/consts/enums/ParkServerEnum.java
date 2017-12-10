package net.imwork.yangyuanjian.park.consts.enums;

import net.imwork.yangyuanjian.common.interfaces.GetEnumType;

/**
 * Created by thunderobot on 2017/11/18.
 */
public enum ParkServerEnum implements GetEnumType{
    XingBei("立方行呗","park24","1"),
    LingMao("凌猫停车","linkMore","2"),
    YiXiu("一咻停车","cabinApp","3");
    String name;
    String code;
    String id;

    ParkServerEnum(String name,String code,String id){
        this.name=name;
        this.code=code;
        this.id=id;
    }

    @Override
    public String toString() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
