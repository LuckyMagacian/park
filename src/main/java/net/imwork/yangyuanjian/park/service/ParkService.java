package net.imwork.yangyuanjian.park.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import net.imwork.yangyuanjian.park.entity.Park;

import java.io.File;
import java.util.List;

/**
 * Created by thunderobot on 2017/11/18.
 */
public interface ParkService {
    Boolean addPark(Park park);
    public Boolean addParks(List<List<String>> datas);
    Boolean modifierPark(Park park);
    Boolean freezePark(Park park);
    Boolean unfreezePark(Park park);
    Boolean delPark(Park park);
    Park queryParkInfo(Long parkId);
    List<Park> queryParks(Wrapper<Park> wrapper, Page<Park> page);
}
