package net.imwork.yangyuanjian.park.test;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.park.entity.Park;
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
    }

    @Test
    public void start(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("xml/spring.xml");
        System.out.println(ac);
    }
    @Test
    public void test1(){
        Page page=new Page<Park>(1,10);
        page.setRecords(null);
        System.out.println(JSON.toJSON(page));
    }

}
