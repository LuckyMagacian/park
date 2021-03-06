package net.imwork.yangyuanjian.park.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import net.imwork.yangyuanjian.common.interfaces.CommonEntityMethod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by thunderobot on 2017/11/18.
 */
@TableName("park")
public class Park extends Model<Park> implements CommonEntityMethod{
    /**停车场编号*/
    @TableId("id")
    private Long id;



    /**停车场名称*/
    @TableField("name")
    private String name;
    /**停车场所在省份*/
    @TableField("province")
    private String province;
    /**停车场所在城市*/
    @TableField("city")
    private String city;
    /**停车场所在区域*/
    @TableField("area")
    private String area;
    /**停车场详细地址*/
    @TableField("address")
    private String address;
    /**停车场状态 {@link net.imwork.yangyuanjian.park.consts.enums.ParkStatus}*/
    @TableField("status")
    private String status;
    /**停车服务类型*/
    @TableField("services")
    private String services;
    /**停车场服务商*/
    @TableField("server")
    private String server;
    /**停车场添加时间*/
    @TableField("add_time")
    private String addTime;
    /**停车场添加管理员编号*/
    @TableField("adder_id")
    private Long adderId;
    /**停车场添加管理员姓名*/
    @TableField("adder_name")
    private String adderName;

    /**停车场修改时间*/
    @TableField("modify_time")
    private String modifyTime;
    /**停车场最后修改者编号*/
    @TableField("modifer_id")
    private Long modiferId;
    /**停车场最后一次修改者姓名*/
    @TableField("modifer_name")
    private String modiferName;
    /**备用*/
    @TableField("remark")
    private String remark;
    /**纬度*/
    @TableField("latitude")
    private BigDecimal latitude;
    /**经度*/
    @TableField("longitude")
    private BigDecimal longitude;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Long getAdderId() {
        return adderId;
    }

    public void setAdderId(Long adderId) {
        this.adderId = adderId;
    }

    public String getAdderName() {
        return adderName;
    }

    public void setAdderName(String adderName) {
        this.adderName = adderName;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModiferId() {
        return modiferId;
    }

    public void setModiferId(Long modiferId) {
        this.modiferId = modiferId;
    }

    public String getModiferName() {
        return modiferName;
    }

    public void setModiferName(String modiferName) {
        this.modiferName = modiferName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Park{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", services='" + services + '\'' +
                ", server='" + server + '\'' +
                ", addTime='" + addTime + '\'' +
                ", adderId=" + adderId +
                ", adderName='" + adderName + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", modiferId=" + modiferId +
                ", modiferName='" + modiferName + '\'' +
                ", remark='" + remark + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
