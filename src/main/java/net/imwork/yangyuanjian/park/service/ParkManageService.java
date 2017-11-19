package net.imwork.yangyuanjian.park.service;

import net.imwork.yangyuanjian.park.entity.Park;

/**
 * Created by thunderobot on 2017/11/18.
 */
public interface ParkManageService {
    Boolean addPark(Park park);
    Boolean modifierPark(Park park);
    Boolean freezePark(Park park);
    Boolean unfreezePark(Park park);
    Boolean delPark(Park park);
}
