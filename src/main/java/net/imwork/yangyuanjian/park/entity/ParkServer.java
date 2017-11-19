package net.imwork.yangyuanjian.park.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import net.imwork.yangyuanjian.common.interfaces.CommonEntityMethod;

import java.io.Serializable;

/**
 * Created by thunderobot on 2017/11/18.
 */
public class ParkServer extends Model<ParkServer> implements CommonEntityMethod{
    @TableId("id")
    private Long id;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
