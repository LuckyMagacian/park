package net.imwork.yangyuanjian.park.assist;

import net.imwork.yangyuanjian.park.consts.enums.ParkServerEnum;
import net.imwork.yangyuanjian.park.consts.enums.ServerTypeEnum;
import net.imwork.yangyuanjian.park.service.ParkService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckAssist {

    public static boolean noteParkName(String parkName){
        if(parkName==null)
            return true;
        return parkName.length()>10;
    }

    public static boolean notParkAddress(String parkAddress){
        if(parkAddress==null)
            return true;
       return parkAddress.length()>30;
    }

    public static boolean notParkServer(String arg){
        List<String> servers= Stream.of(ParkServerEnum.values()).map(e->e.getId()).collect(Collectors.toList());
        return !servers.contains(arg);
    }

    public static boolean notParkServices(String arg){
        String[] strs=arg.split(",");
        List<String> services=Stream.of(ServerTypeEnum.values()).map(e->e.getId()).collect(Collectors.toList());
        for(String each:strs){
            if(!services.contains(each))
                return true;
        }
        return false;
    }

}
