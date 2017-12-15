package net.imwork.yangyuanjian.park.assist;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import net.imwork.yangyuanjian.common.annotation.EasyLog;
import net.imwork.yangyuanjian.common.assist.ExcelAssist;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.park.entity.Park;
import static net.imwork.yangyuanjian.park.assist.CheckAssist.*;
import static net.imwork.yangyuanjian.common.assist.CheckAssist.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
@EasyLog(LogFactory.LogLevel.DEBUG)
public class CheckExcel {
    public static Map<String,List<List<String>>> parkArgs=new HashMap<>();

    public static List<String> checkFile(File file,String sessionId){
        ExcelAssist assist=new ExcelAssist(file);
        List<String> temp=null;
        List<String> wrongData=new ArrayList<>();
        List<List<String>> datas=new ArrayList<>();
        int rowNum=1;


        Function<String,String> reduceThreeChar=(s)->s==null?null:s.isEmpty()?s.trim():s.length()<3?s:s.substring(0,s.length()-3);
        Consumer<List<String>> addWrongDate=(l)->wrongData.add(reduceThreeChar.apply(l.stream().reduce("",(a,b)->a==null?null:a.toString()+(b==null?null:b.toString())+" , ")));;


        //todo 若增加了经纬度 需要将colsize改为8
        //excel为   名称 省 市 区 地址 服务商 提供的服务     {经度  纬度}
        while((temp=assist.readLine(0,rowNum++,0,6))!=null){
            String parkName=temp.get(0);
            if(noteParkName(parkName)||isNameOrAddress.negate().test(parkName)){
                LogFactory.debug(CheckExcel.class,"名称校验不通过!");
                addWrongDate.accept(temp);
                continue;
            }
            String province=temp.get(1);
            if(isNameOrAddress.negate().test(province)){
                LogFactory.debug(CheckExcel.class,"省份校验不通过!");
                addWrongDate.accept(temp);
                continue;
            }
            String city=temp.get(2);
            if(isNameOrAddress.negate().test(city)){
                LogFactory.debug(CheckExcel.class,"城市校验不通过!");
                addWrongDate.accept(temp);
                continue;
            }
            String area=temp.get(3);
            if(isNameOrAddress.negate().test(area)){
                LogFactory.debug(CheckExcel.class,"区域校验不通过!");
                addWrongDate.accept(temp);
                continue;
            }
            String address=temp.get(4);
            if(notParkAddress(address)||isNameOrAddress.negate().test(area)){
                LogFactory.debug(CheckExcel.class,"地址校验不通过!");
                addWrongDate.accept(temp);
                continue;
            }
            String server=temp.get(5);
            if(notParkServer(server)){
                LogFactory.debug(CheckExcel.class,"服务商校验不通过!");
                addWrongDate.accept(temp);
                continue;
            }
            String service=temp.get(6);
            if(notParkServices(service)){
                LogFactory.debug(CheckExcel.class,"服务校验不通过!");
                addWrongDate.accept(temp);
                continue;
            }
            datas.add(temp);
        }
        if(wrongData.isEmpty()){
            parkArgs.put(sessionId,datas);
        }
        return wrongData;
    }

    public static List<List<String>> get(String sessionId){
        return parkArgs.get(sessionId);
    }

    public static void remove(String sessiondid){
        parkArgs.remove(sessiondid);
    }

}
