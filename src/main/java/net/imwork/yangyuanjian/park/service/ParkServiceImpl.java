package net.imwork.yangyuanjian.park.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import net.imwork.yangyuanjian.common.annotation.EasyLog;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.park.entity.Park;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by thunderobot on 2017/11/18.
 */
@EasyLog(LogFactory.LogLevel.INFO)
@Service("parkService")
public class ParkServiceImpl implements ParkService{
    @Override
    public Park queryParkInfo(Long parkId) {
        return null;
    }

    @Override
    public List<Park> queryParks(Wrapper<Park> wrapper, Page<Park> page) {
        return null;
    }
}
