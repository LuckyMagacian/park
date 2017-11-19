package net.imwork.yangyuanjian.park.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import net.imwork.yangyuanjian.common.annotation.EasyLog;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.park.entity.Manager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by thunderobot on 2017/11/18.
 */
@EasyLog(LogFactory.LogLevel.INFO)
@Service("managerService")
public class ManagerServiceImpl implements ManagerService{
    @Override
    public Boolean addManager(Manager manager) {
        return null;
    }

    @Override
    public Boolean modifyManager(Manager manager) {
        return null;
    }

    @Override
    public Manager queryManagerInfo(Long id) {
        return null;
    }

    @Override
    public List<Manager> queryManagers(Wrapper<Manager> wrapper, Page<Manager> page) {
        return null;
    }
}
