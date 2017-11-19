package net.imwork.yangyuanjian.park.controller;

import net.imwork.yangyuanjian.common.annotation.EasyLog;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.common.assist.RetMessage;
import net.imwork.yangyuanjian.park.entity.Park;
import net.imwork.yangyuanjian.park.service.ParkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by thunderobot on 2017/11/18.
 */
@EasyLog(LogFactory.LogLevel.INFO)
@Controller
public class ParkController {
    @Resource
    private ParkService parkService;

    @RequestMapping
    public String queryParkInfo(HttpServletRequest req, HttpServletResponse res){
        RetMessage message=new RetMessage();

        return message.toJson();
    }
    @RequestMapping
    public String queryParks(HttpServletRequest req,HttpServletResponse res){
        RetMessage message=new RetMessage();

        return message.toJson();
    }


}
