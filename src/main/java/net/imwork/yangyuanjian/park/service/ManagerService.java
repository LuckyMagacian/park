package net.imwork.yangyuanjian.park.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import net.imwork.yangyuanjian.park.entity.Manager;

import java.util.List;

/**
 * Created by thunderobot on 2017/11/18.
 */
public interface ManagerService {
    Boolean addManager(Manager manager);
    Boolean modifyManager(Manager manager);
    Manager queryManagerInfo(Long id);
    List<Manager> queryManagers(Wrapper<Manager> wrapper, Page<Manager> page);
}
