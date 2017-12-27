package net.imwork.yangyuanjian.park.test;

import net.imwork.yangyuanjian.common.assist.ExcelAssist;
import org.junit.Test;

import java.io.File;

public class TestOther {

    @Test
    public void test(){
        File file=new File("park.xls");
        System.out.println(file.exists());
        ExcelAssist assist=new ExcelAssist(file);
    }
    @Test
    public void test1(){
        System.out.println("浙江三立开元名都大酒店停车场".length());
    }
}
