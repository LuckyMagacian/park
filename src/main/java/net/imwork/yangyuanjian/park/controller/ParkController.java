package net.imwork.yangyuanjian.park.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import net.imwork.yangyuanjian.common.annotation.EasyLog;
import net.imwork.yangyuanjian.common.annotation.SetUtf8;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.common.assist.RetMessage;
import net.imwork.yangyuanjian.common.assist.TimeUtil;
import net.imwork.yangyuanjian.park.assist.CheckAssist;
import net.imwork.yangyuanjian.park.assist.CheckExcel;
import net.imwork.yangyuanjian.park.consts.enums.ParkStatus;
import net.imwork.yangyuanjian.park.entity.Park;
import net.imwork.yangyuanjian.park.service.ParkService;
import org.junit.internal.runners.statements.Fail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import static net.imwork.yangyuanjian.common.assist.CheckAssist.*;
import static net.imwork.yangyuanjian.common.assist.RetMessage.*;
import static net.imwork.yangyuanjian.park.assist.CheckAssist.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thunderobot on 2017/11/18.
 */
@EasyLog(LogFactory.LogLevel.INFO)
@Controller
public class ParkController {
    @Resource
    private ParkService parkService;
    private Map<String,File> files=new HashMap<>();
    private Map<String,Boolean> chekResult=new HashMap<>();

    @RequestMapping(value = "queryInfo" ,produces="application/json;charset=utf-8")
    @ResponseBody
    @SetUtf8
    public String queryParkInfo(HttpServletRequest req, HttpServletResponse res){
        RetMessage message=new RetMessage();
        String parkIdStr=req.getParameter("parkId");
        Long parkId=Long.valueOf(parkIdStr);
        Park park=parkService.queryParkInfo(parkId);
        message.setAll(SUCCESS,"查询成功!",park);
        return message.toJson();
    }
    @RequestMapping(value = "queryParks",produces="application/json;charset=utf-8")
    @ResponseBody
    @SetUtf8
    public String queryParks(HttpServletRequest req,HttpServletResponse res){
        RetMessage message=new RetMessage();

        String province=req.getParameter("province");
        String city=req.getParameter("city");
        String area=req.getParameter("area");
        String address=req.getParameter("address");
        String name=req.getParameter("name");
        String status=req.getParameter("status");
        String server=req.getParameter("server");

        String pageNumStr=req.getParameter("pageNum");
        String pageSizeStr=req.getParameter("pageSize");

        Integer pageNum=Integer.valueOf(pageNumStr);
        Integer pageSize=Integer.valueOf(pageSizeStr);

        Page<Park> page = new Page<>(pageNum,pageSize);

        EntityWrapper<Park> wrapper=new EntityWrapper<>();
        if(province!=null&&!province.trim().isEmpty())
            wrapper.eq("province",province);
        if(city!=null&&!city.trim().isEmpty())
            wrapper.eq("city",city);
        if(area!=null&&!area.trim().isEmpty())
            wrapper.eq("area",area);
        if(address!=null&&!address.trim().isEmpty())
            wrapper.eq("address",address);
        if(name!=null&&!name.trim().isEmpty())
            wrapper.like("name",name);
        if(status!=null&&!status.trim().isEmpty())
            wrapper.like("status",status);
        if(status!=null&&!status.trim().isEmpty())
            wrapper.like("status",status);
        if(server!=null&&!server.trim().isEmpty())
            wrapper.like("server",server);

        List<Park> parks = parkService.queryParks(wrapper,page);

        message.setAll(SUCCESS,"查询成功!",(ArrayList)parks);

        return message.toJson();
    }
    @RequestMapping(value = "addPark",produces="application/json;charset=utf-8")
    @ResponseBody
    @SetUtf8
    public String addPark(HttpServletRequest req,HttpServletResponse res){
        RetMessage message=new RetMessage();
        String name=req.getParameter("name");
        if(CheckAssist.noteParkName(name)||isNameOrAddress.negate().test(name)){
            message.setAll(FAIL,"停车场名称校验不通过!",null);
            return message.toJson();
        }
        //todo  待处理 经纬度
        String longitudeStr=req.getParameter("longitude");
        String latitudeStr=req.getParameter("latitude");


        String province=req.getParameter("province");
        String city=req.getParameter("city");
        String area=req.getParameter("area");
        String address=req.getParameter("address");
        if(CheckAssist.notParkAddress(address)||isNameOrAddress.negate().test(address)){
            message.setAll(FAIL,"停车场地址校验不通过!",null);
            return message.toJson();
        }

        String server=req.getParameter("server");
        if(notParkServer(server)){
            message.setAll(FAIL,"服务商校验不通过!",null);
            return message.toJson();
        }
        String services=req.getParameter("services");
        if(notParkServices(services)){
            message.setAll(FAIL,"服务校验不通过!",null);
            return message.toJson();
        }
        Park park=new Park();
        park.setId(IdWorker.getId());
        park.setProvince(province);
        park.setCity(city);
        park.setArea(area);
        park.setAddress(address);
        park.setName(name);
        park.setServer(server);
        park.setStatus(ParkStatus.Freeze.getId());
        park.setAddTime(TimeUtil.getDateTime());
        park.setServices(services);
        park.setLongitude(new BigDecimal(longitudeStr));
        park.setLatitude(new BigDecimal(latitudeStr));


        Boolean result=parkService.addPark(park);
        message.setAll(SUCCESS,"添加成功!",result);
        return message.toJson();
    }
    @RequestMapping(value = "addParks",produces="application/json;charset=utf-8")
    @ResponseBody
    @SetUtf8
    public String addParks(HttpServletRequest req,HttpServletResponse res){
        RetMessage message=new RetMessage();
        String sessionId=req.getSession().getId();
        File file=new File(sessionId+".xls");
        try {
            InputStreamReader reader=new InputStreamReader(req.getInputStream(),"utf-8");
            BufferedReader buffer=new BufferedReader(reader);
            String line=null;

            FileOutputStream fout=new FileOutputStream(file);
            PrintWriter writer=new PrintWriter(new OutputStreamWriter(fout,"utf-8"));

            while ((line=buffer.readLine())!=null){
                writer.append(line);
            }
            writer.flush();
            files.put(sessionId,file);

            List<String> list = CheckExcel.checkFile(file,sessionId);
            if(list==null||list.isEmpty()){
                message.setAll(SUCCESS,"文件校验通过!",null);
                chekResult.put(sessionId,true);
            } else{
                message.setAll(FAIL,"文件校验不通过!", (Serializable) list);
                chekResult.put(sessionId,false);
            }
        } catch (IOException e) {
            message.setAll(ERROR,"上传文件发生异常!",null);
        } finally {
            return message.toJson();
        }
    }
    @SetUtf8
    @ResponseBody
    @RequestMapping(value = "confirmAddParks",produces="application/json;charset=utf-8")
    public String sureAddParks(HttpServletRequest req,HttpServletResponse res){
        RetMessage message=new RetMessage();
        String sessionId=req.getSession().getId();
        if(chekResult.get(sessionId)==null)
            message.setAll(FAIL,"上传的文件校验结果不存在!",null);
        if(chekResult.get(sessionId)){
            Boolean result=parkService.addParks(CheckExcel.get(sessionId));
            if(result==null)
                message.setAll(ERROR,"批量添加时异常!",null);
            if(result)
                message.setAll(SUCCESS,"添加成功!",null);
            else
                message.setAll(FAIL,"批量添加失败!",null);
        }
        return message.toJson();
    }
    @SetUtf8
    @ResponseBody
    @RequestMapping(value = "modifyPark",produces="application/json;charset=utf-8")
    public String modifyPark(HttpServletRequest req,HttpServletResponse res){
        RetMessage message=new RetMessage();
        String parkIdStr=req.getParameter("parkId");
        Long parkId=Long .valueOf(parkIdStr);
        Park park=parkService.queryParkInfo(parkId);

        String province=req.getParameter("province");
        if(province!=null&&!province.trim().isEmpty())
            park.setProvince(province);

        String city=req.getParameter("city");
        if(city!=null&&!city.trim().isEmpty())
            park.setCity(city);

        String area=req.getParameter("area");
        if(area!=null&&!area.trim().isEmpty())
            park.setArea(area);

        String address=req.getParameter("address");
        if(CheckAssist.notParkAddress(address)||isNameOrAddress.negate().test(address)){
            message.setAll(FAIL,"停车场地址校验不通过!",null);
            return message.toJson();
        }else
            park.setAddress(address);

        String name=req.getParameter("name");
        if(CheckAssist.noteParkName(name)||isNameOrAddress.negate().test(name)){
            message.setAll(FAIL,"停车场名称校验不通过!",null);
            return message.toJson();
        }else
            park.setName(name);



        String server=req.getParameter("server");
        if(notParkServer(server)){
            message.setAll(FAIL,"服务商校验不通过!",null);
            return message.toJson();
        } else
            park.setServer(server);

        String services=req.getParameter("services");
        if(notParkServices(services)){
            message.setAll(FAIL,"服务校验不通过!",null);
            return message.toJson();
        } else
            park.setServices(services);

        Boolean result=park.updateById();
        if(result==null)
            message.setAll(ERROR,"修改时发生异常!",null);
        if(result)
            message.setAll(SUCCESS,"修改成功!",null);
        else
            message.setAll(FAIL,"修改失败!",null);

        return message.toJson();
    }
    @EasyLog(LogFactory.LogLevel.DEBUG)
    @SetUtf8
    @ResponseBody
    @RequestMapping(value = "test",produces="application/json;charset=utf-8")
    public String test(HttpServletRequest req,HttpServletResponse res) {
        String sessionId=req.getSession().getId();
        String reqCharset=req.getCharacterEncoding();
        String resCharset=req.getCharacterEncoding();
        System.out.println(sessionId);
        System.out.println(reqCharset);
        System.out.println(resCharset);
        return JSON.toJSONString(new String[]{sessionId,reqCharset,reqCharset});
    }
}
