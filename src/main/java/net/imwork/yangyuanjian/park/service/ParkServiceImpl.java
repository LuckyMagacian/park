package net.imwork.yangyuanjian.park.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import net.imwork.yangyuanjian.common.annotation.EasyLog;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.park.consts.enums.ParkStatus;
import net.imwork.yangyuanjian.park.entity.Park;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static net.imwork.yangyuanjian.park.consts.enums.ParkStatus.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thunderobot on 2017/11/18.
 */
@EasyLog(LogFactory.LogLevel.INFO)
@Service("parkService")
public class ParkServiceImpl implements ParkService{
    /**根据管理员id临时保存批量导入停车场文件*/
    @Resource
    private DaoService daoService;

    @Override
    public Boolean addPark(Park park) {
        return park.insert();
    }

    //TODO 若增加经纬度 需要添加
    @Override
    public Boolean addParks(List<List<String>> datas) {
        datas.parallelStream().forEach(temp->{
            String parkname=temp.get(0);
            String province=temp.get(1);
            String city=temp.get(2);
            String area=temp.get(3);
            String address=temp.get(4);
            String server=temp.get(5);
            String service=temp.get(6);

            Park park=new Park();
            park.setId(IdWorker.getId());
            park.setName(parkname);
            park.setProvince(province);
            park.setCity(city);
            park.setArea(area);
            park.setAddress(address);
            park.setServer(server);
            park.setServices(service);
            park.insert();
        });
        return true;
    }

    @Override
    public Boolean modifierPark(Park park) {
        return park.updateById();
    }

    @Override
    public Boolean freezePark(Park park) {
        park.setStatus(Freeze.getId());
        return park.updateById();
    }

    @Override
    public Boolean unfreezePark(Park park) {
        park.setStatus(Normal.getId());
        return park.updateById();
    }

    @Override
    public Boolean delPark(Park park) {
        park.setStatus(Delete.getId());
        return null;
    }

    @Override
    public Park queryParkInfo(Long parkId) {
        return daoService.getParkDao().selectById(parkId);
    }

    @Override
    public List<Park> queryParks(Wrapper<Park> wrapper, Page<Park> page) {
        return daoService.getParkDao().selectPage(page,wrapper);
    }
}
