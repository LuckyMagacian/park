package net.imwork.yangyuanjian.park.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import net.imwork.yangyuanjian.park.entity.Park;

import java.util.List;

/**
 * Created by thunderobot on 2017/11/18.
 */
public interface ParkService {
    Park queryParkInfo(Long parkId);
    List<Park> queryParks(Wrapper<Park> wrapper, Page<Park> page);
}
