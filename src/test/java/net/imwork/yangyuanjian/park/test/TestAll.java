package net.imwork.yangyuanjian.park.test;

import net.imwork.yangyuanjian.common.assist.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


public class TestAll {

    @Before
    public void init(){
        LogFactory.setLogLevel(LogFactory.LogLevel.DEBUG);
        LogFactory.init();
    }

    @Test
    public void start(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("xml/spring.xml");
        System.out.println(ac);
    }
}
