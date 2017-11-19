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
    private CustomerInfoDao customerInfoDao;
    @Resource
    private ManagerDao managerDao;
    @Resource
    private ParkDao parkDao;
    @Resource
    private ParkRequestDao parkRequestDao;
    @Resource
    private ParkServerDao parkServerDao;

    public CustomerInfoDao getCustomerInfoDao() {
        return customerInfoDao;
    }

    public void setCustomerInfoDao(CustomerInfoDao customerInfoDao) {
        this.customerInfoDao = customerInfoDao;
    }

    public ManagerDao getManagerDao() {
        return managerDao;
    }

    public void setManagerDao(ManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    public ParkDao getParkDao() {
        return parkDao;
    }

    public void setParkDao(ParkDao parkDao) {
        this.parkDao = parkDao;
    }

    public ParkRequestDao getParkRequestDao() {
        return parkRequestDao;
    }

    public void setParkRequestDao(ParkRequestDao parkRequestDao) {
        this.parkRequestDao = parkRequestDao;
    }

    public ParkServerDao getParkServerDao() {
        return parkServerDao;
    }

    public void setParkServerDao(ParkServerDao parkServerDao) {
        this.parkServerDao = parkServerDao;
    }
}
