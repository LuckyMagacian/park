package net.imwork.yangyuanjian.park.controller;

import net.imwork.yangyuanjian.common.annotation.EasyLog;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.park.service.ParkManageService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by thunderobot on 2017/11/18.
 */
@EasyLog(LogFactory.LogLevel.INFO)
@Controller
public class ParkManageController {
    @Resource
    private ParkManageService manageService;
}
