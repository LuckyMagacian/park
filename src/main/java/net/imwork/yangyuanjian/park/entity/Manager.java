package net.imwork.yangyuanjian.park.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import net.imwork.yangyuanjian.common.interfaces.CommonEntityMethod;

import java.io.Serializable;

/**
 * Created by thunderobot on 2017/11/18.
 */
//@TableName("manager")
public class Manager extends Model<Manager> implements CommonEntityMethod{
    /**管理员编号*/
    @TableId("id")
    private Long id;
    /**管理员姓名*/
    @TableField("name")
    private String name;
    /**管理员手机号码*/
    @TableField("phone")
    private String phone;
    /**管理员添加时间*/
    @TableField("add_time")
    private String addTime;
    /**备注*/
    @TableField("remark")
    private String remark;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", addTime='" + addTime + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
