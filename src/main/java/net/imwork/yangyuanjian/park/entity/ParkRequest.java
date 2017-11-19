package net.imwork.yangyuanjian.park.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import net.imwork.yangyuanjian.common.interfaces.CommonEntityMethod;

import java.io.Serializable;

/**
 * Created by thunderobot on 2017/11/18.
 */
@TableName("park_request")
public class ParkRequest extends Model<ParkRequest> implements CommonEntityMethod{
    @TableId("id")
    private Long id;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
