package net.imwork.yangyuanjian.park.service;

import net.imwork.yangyuanjian.park.dao.*;
import net.imwork.yangyuanjian.park.entity.Manager;
import net.imwork.yangyuanjian.park.entity.Park;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by thunderobot on 2017/11/18.
 */
@Service("daoService")
public class DaoServiceImpl implements DaoService{
    @Resource
    private ParkDao parkDao;

    public ParkDao getParkDao() {
        return parkDao;
    }

    public void setParkDao(ParkDao parkDao) {
        this.parkDao = parkDao;
    }

}
