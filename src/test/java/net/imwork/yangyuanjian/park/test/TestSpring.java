package net.imwork.yangyuanjian.park.test;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.common.assist.TimeUtil;
import net.imwork.yangyuanjian.park.consts.enums.ParkStatus;
import net.imwork.yangyuanjian.park.controller.ParkController;
import net.imwork.yangyuanjian.park.entity.Park;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.math.BigDecimal;

public class TestSpring {
    private ParkController controller;
    @Before
    public void init(){
        LogFactory.setLogLevel(LogFactory.LogLevel.DEBUG);
        LogFactory.init();
        ApplicationContext ac=new ClassPathXmlApplicationContext("xml/spring.xml");
        controller=ac.getBean(ParkController.class);
    }

    @Test
    public void testQueryParkInfo(){
        LogFactory.init();
        MockHttpServletRequest  req=new MockHttpServletRequest();
        MockHttpServletResponse res=new MockHttpServletResponse();
        req.addParameter("parkId","935170464077111297");
        System.out.println(controller.queryParkInfo(req,res));
    }
    @Test
    public void testQueryParks(){
        LogFactory.init();
        MockHttpServletRequest  req=new MockHttpServletRequest();
        MockHttpServletResponse res=new MockHttpServletResponse();
        req.addParameter("pageNum","1");
        req.addParameter("pageSize","1000");
        System.out.println(controller.queryParks(req,res));
    }
    @Test
    public void testAddPark(){
        LogFactory.init();
        MockHttpServletRequest  req=new MockHttpServletRequest();
        req.addParameter("province","33");
        req.addParameter("city","00");
        req.addParameter("area","00");
        req.addParameter("address","滨江区浦沿街道");
        req.addParameter("name","临时停车场");
        req.addParameter("server","10086");
        req.addParameter("services","没有服务");
        req.addParameter("longitude","1.00000");
        req.addParameter("latitude","2.0000");


        req.addParameter("pageNum","1");
        req.addParameter("pageSize","1000");
        MockHttpServletResponse res=new MockHttpServletResponse();
        System.out.println(controller.addPark(req,res));
        System.err.println(controller.queryParks(req,res));
    }
    @Test
    public void testModifyPark(){
        LogFactory.init();
        MockHttpServletRequest  req=new MockHttpServletRequest();
        MockHttpServletResponse res=new MockHttpServletResponse();
        req.addParameter("parkId","935170464077111297");
        req.addParameter("parkName","嘿嘿恶化");
        System.out.println(controller.modifyPark(req,res));
        System.out.println(controller.queryParkInfo(req,res));
    }
    @Test
    public void testAddParks(){
        LogFactory.init();
        MockHttpServletRequest  req=new MockHttpServletRequest();
        MockHttpServletResponse res=new MockHttpServletResponse();
    }
    @Test
    public void testSureAddPark(){
        LogFactory.init();
        MockHttpServletRequest  req=new MockHttpServletRequest();
        MockHttpServletResponse res=new MockHttpServletResponse();
    }
    @Test
    public void testTest(){
        LogFactory.init();
        MockHttpServletRequest  req=new MockHttpServletRequest();
        MockHttpServletResponse res=new MockHttpServletResponse();
        System.out.println(controller.test(req,res));
    }
    @Test
    public void testNone(){
        System.out.println(controller);
    }
}
