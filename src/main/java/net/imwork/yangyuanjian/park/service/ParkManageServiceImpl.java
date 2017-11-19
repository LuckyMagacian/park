package net.imwork.yangyuanjian.park.service;

import net.imwork.yangyuanjian.common.annotation.EasyLog;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.park.entity.Park;
import org.springframework.stereotype.Service;

/**
 * Created by thunderobot on 2017/11/18.
 */
@EasyLog(LogFactory.LogLevel.INFO)
@Service("parkManagerService")
public class ParkManageServiceImpl implements ParkManageService{
    @Override
    public Boolean addPark(Park park) {
        return null;
    }

    @Override
    public Boolean modifierPark(Park park) {
        return null;
    }

    @Override
    public Boolean freezePark(Park park) {
        return null;
    }

    @Override
    public Boolean unfreezePark(Park park) {
        return null;
    }

    @Override
    public Boolean delPark(Park park) {
        return null;
    }
}
