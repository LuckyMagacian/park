package net.imwork.yangyuanjian.park.service;

import net.imwork.yangyuanjian.park.dao.*;

/**
 * Created by thunderobot on 2017/11/18.
 */
public interface DaoService {
    CustomerInfoDao getCustomerInfoDao();
    ManagerDao getManagerDao();
    ParkDao getParkDao();
    ParkRequestDao getParkRequestDao();
    ParkServerDao getParkServerDao();
}
