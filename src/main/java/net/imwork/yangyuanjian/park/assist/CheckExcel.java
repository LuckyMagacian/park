package net.imwork.yangyuanjian.park.assist;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import net.imwork.yangyuanjian.common.assist.ExcelAssist;
import net.imwork.yangyuanjian.park.entity.Park;
import static net.imwork.yangyuanjian.park.assist.CheckAssist.*;
import static net.imwork.yangyuanjian.common.assist.CheckAssist.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckExcel {
    public static Map<String,List<List<String>>> parkArgs=new HashMap<>();

    public static List<String> checkFile(File file,String sessionId){
        ExcelAssist assist=new ExcelAssist();
        assist.setWorkbook(file);

        List<String> temp=null;
        List<String> wrongData=new ArrayList<>();
        List<List<String>> datas=new ArrayList<>();
        int rowNum=1;
        //todo 若增加了经纬度 需要将colsize改为9
        //excel为   名称 省 市 区 地址 服务商 提供的服务     {经度  纬度}
        while((temp=assist.readLine(1,rowNum,1,7))!=null){

            String parkName=temp.get(0);
            if(noteParkName(parkName)||isNameOrAddress.negate().test(parkName)){
                wrongData.add(temp.stream().reduce("",String::concat));
                continue;
            }

            String province=temp.get(1);
            if(isNameOrAddress.negate().test(province)){
                wrongData.add(temp.stream().reduce("",String::concat));
                continue;
            }

            String city=temp.get(2);
            if(isNameOrAddress.negate().test(city)){
                wrongData.add(temp.stream().reduce("",String::concat));
                continue;
            }

            String area=temp.get(3);
            if(isNameOrAddress.negate().test(area)){
                wrongData.add(temp.stream().reduce("",String::concat));
                continue;
            }

            String address=temp.get(4);
            if(notParkAddress(address)||isNameOrAddress.negate().test(area)){
                wrongData.add(temp.stream().reduce("",String::concat));
                continue;
            }

            String server=temp.get(5);
            if(notParkServer(server)){
                wrongData.add(temp.stream().reduce("",String::concat));
                continue;
            }

            String service=temp.get(6);
            if(notParkServices(server)){
                wrongData.add(temp.stream().reduce("",String::concat));
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
