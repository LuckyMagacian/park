package net.imwork.yangyuanjian.park.test;

import net.imwork.yangyuanjian.common.aop.AddLog;
import net.imwork.yangyuanjian.common.aop.SessionCharset;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.park.controller.ParkController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class TestSpring {
    private ParkController controller;
    ApplicationContext ac;
    @Before
    public void init(){
        LogFactory.setLogLevel(LogFactory.LogLevel.INFO);
        LogFactory.init();
        ac=new ClassPathXmlApplicationContext("xml/spring.xml");
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
        req.addParameter("name","嘿嘿恶化");
        System.out.println(controller.modifyPark(req,res));
//        System.out.println(controller.queryParkInfo(req,res));
    }
    @Test
    public void testAddParks() throws IOException {
        LogFactory.init();
        File file = new File("park.xlsx");
        FileInputStream fin=new FileInputStream(file);
        byte[] bytes=new byte[1024*1024];
        int byteSize=fin.read(bytes);
        bytes= Arrays.copyOf(bytes,byteSize);
        MockHttpServletRequest  req=new MockHttpServletRequest();
        req.setContent(bytes);
        MockHttpServletResponse res=new MockHttpServletResponse();
        System.out.println(controller.addParks(req,res));

        System.out.println(controller.sureAddParks(req,res));
    }

    @Test
    public void testFreeze(){
        MockHttpServletRequest  req=new MockHttpServletRequest();
        req.addParameter("parkId","935170464077111297");
        MockHttpServletResponse res=new MockHttpServletResponse();
        System.out.println(controller.freezePark(req,res));
    }
    @Test
    public void testUnfreeze(){
        MockHttpServletRequest  req=new MockHttpServletRequest();
        req.addParameter("parkId","935170464077111297");
        MockHttpServletResponse res=new MockHttpServletResponse();
        System.out.println(controller.unfreezePark(req,res));
    }
}
