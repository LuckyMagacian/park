package net.imwork.yangyuanjian.park.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import net.imwork.yangyuanjian.common.annotation.EasyLog;
import net.imwork.yangyuanjian.common.annotation.Secret;
import net.imwork.yangyuanjian.common.annotation.SetUtf8;
import net.imwork.yangyuanjian.common.assist.IpAssist;
import net.imwork.yangyuanjian.common.assist.LogFactory;
import net.imwork.yangyuanjian.common.assist.RetMessage;
import net.imwork.yangyuanjian.common.assist.TimeUtil;
import net.imwork.yangyuanjian.park.assist.CheckAssist;
import net.imwork.yangyuanjian.park.assist.CheckExcel;
import net.imwork.yangyuanjian.park.consts.enums.ParkStatus;
import net.imwork.yangyuanjian.park.entity.Park;
import net.imwork.yangyuanjian.park.service.ParkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.imwork.yangyuanjian.common.assist.CheckAssist.*;
import static net.imwork.yangyuanjian.common.assist.RetMessage.*;
import static net.imwork.yangyuanjian.park.assist.CheckAssist.notParkServer;
import static net.imwork.yangyuanjian.park.assist.CheckAssist.notParkServices;

/**
 * Created by thunderobot on 2017/11/18.
 */

@Controller
@EasyLog (LogFactory.LogLevel.INFO)
public class ParkController {
    @Resource
    private ParkService parkService;
//    private Map<String, File> files = new HashMap<>();
    private Map<String, Boolean> chekResult = new HashMap<>();

    @SetUtf8
    @ResponseBody
    @RequestMapping (value = "queryInfo", produces = "application/json;charset=utf-8")
    public String queryParkInfo(HttpServletRequest req, HttpServletResponse res) {
        try {
            RetMessage message = new RetMessage();
            String parkIdStr = req.getParameter("parkId");
            if (isNullOrEmpty.test(parkIdStr))
                return new RetMessage(FAIL, "停车场编号为空!", null).toJson();
            if (isInteger.negate().test(parkIdStr)) {
                return new RetMessage(FAIL, "停车编号非法!", null).toJson();
            }
            Long parkId = Long.valueOf(parkIdStr);
            Park park = parkService.queryParkInfo(parkId);
            message.setAll(SUCCESS, "查询成功!", park);
            return message.toJson();
        } catch (Exception e) {
            LogFactory.error(this, "查询停车场详情时发生异常!", e);
            return new RetMessage(ERROR, "发生异常!", null).toJson();
        }
    }

    @SetUtf8
    @ResponseBody
    @RequestMapping (value = "queryParks", produces = "application/json;charset=utf-8")
    public String queryParks(HttpServletRequest req, HttpServletResponse res) {
        try {
            RetMessage message = new RetMessage();
            String province = req.getParameter("province");
            String city = req.getParameter("city");
            String area = req.getParameter("area");
            String address = req.getParameter("address");
            String name = req.getParameter("name");
            String status = req.getParameter("status");
            String services = req.getParameter("services");
            if (status != null && !status.isEmpty()) {
                if (!Stream.of(ParkStatus.values()).map(e -> e.getId()).collect(Collectors.toList()).contains(status)) {
                    return new RetMessage(FAIL, "停车场状态参数非法!", null).toJson();
                }
            }

            String server = req.getParameter("server");
            String pageNumStr = req.getParameter("pageNum");
            String pageSizeStr = req.getParameter("pageSize");
            if (pageNumStr == null || pageNumStr.isEmpty() || isInteger.negate().test(pageNumStr)) {
                return new RetMessage(FAIL, "页码不能为空!", null).toJson();
            }
            if (pageSizeStr == null || pageSizeStr.isEmpty() || isInteger.negate().test(pageSizeStr)) {
                return new RetMessage(FAIL, "页尺寸不能为空!", null).toJson();
            }
            Integer pageNum = Integer.valueOf(pageNumStr);
            Integer pageSize = Integer.valueOf(pageSizeStr);
            Page<Park> page = new Page<>(pageNum, pageSize);
            EntityWrapper<Park> wrapper = new EntityWrapper<>();
            if (province != null && !province.trim().isEmpty())
                wrapper.eq("province", province);
            if (city != null && !city.trim().isEmpty())
                wrapper.eq("city", city);
            if (area != null && !area.trim().isEmpty())
                wrapper.eq("area", area);
            if (address != null && !address.trim().isEmpty())
                wrapper.eq("address", address);
            if (name != null && !name.trim().isEmpty())
                wrapper.like("name", name);
            if (status != null && !status.trim().isEmpty())
                wrapper.eq("status", status);
            if (server != null && !server.trim().isEmpty())
                wrapper.eq("server", server);
            if (services != null && !notParkServices(services)) {
                String[] strs = services.split(",");
                if (strs.length == 1) {
                    wrapper.like("services", strs[0]);
                } else if (strs.length == 2) {
                    List<String> list = Arrays.asList(ParkStatus.values()).stream().map(e -> e.getId()).collect(Collectors.toList());
                    list.remove(Arrays.asList(strs));
                    wrapper.notLike("services", list.get(0));
                } else if (strs.length == 0) {
                    wrapper.eq("status", "-1");
                }
            }
            wrapper.ne("status", ParkStatus.Delete.getId());
            List<Park> parks = parkService.queryParks(wrapper, page);
            Map<String, Object> map = new HashMap<>();
            map.put("page", page);
            map.put("list", parks);
            message.setAll(SUCCESS, "查询成功!", (HashMap) map);
            return message.toJson();
        } catch (Exception e) {
            LogFactory.error(this, "查询停车场列表时发生异常!", e);
            return new RetMessage(ERROR, "发生异常!", null).toJson();
        }

    }

    @Secret
    @SetUtf8
    @ResponseBody
    @RequestMapping (value = "addPark", produces = "application/json;charset=utf-8")
    public String addPark(HttpServletRequest req, HttpServletResponse res) {
        try {
            LogFactory.info(this, IpAssist.getIp(req) + "尝试添加停车场!");
            RetMessage message = new RetMessage();
            String name = req.getParameter("name");
            if (CheckAssist.noteParkName(name) || isNameOrAddress.negate().test(name)) {
                message.setAll(FAIL, "停车场名称校验不通过!", null);
                return message.toJson();
            }
            String longitudeStr = req.getParameter("longitude");
            String latitudeStr = req.getParameter("latitude");
            if (longitudeStr != null && isDeciaml.negate().test(longitudeStr)) {
                return new RetMessage(FAIL, "经度参数只能不传或者为小数!", null).toJson();
            }
            if (latitudeStr != null && isDeciaml.negate().test(latitudeStr)) {
                return new RetMessage(FAIL, "纬度参数只能不传或者为小数!", null).toJson();
            }
            String province = req.getParameter("province");
            String city = req.getParameter("city");
            String area = req.getParameter("area");
            String address = req.getParameter("address");
            if (CheckAssist.notParkAddress(address) || isNameOrAddress.negate().test(address)) {
                message.setAll(FAIL, "停车场地址校验不通过!", null);
                return message.toJson();
            }
            String server = req.getParameter("server");
            if (notParkServer(server)) {
                message.setAll(FAIL, "服务商校验不通过!", null);
                return message.toJson();
            }
            String services = req.getParameter("services");
            if (notParkServices(services)) {
                message.setAll(FAIL, "服务校验不通过!", null);
                return message.toJson();
            }
            Park park = new Park();
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
            park.setLongitude(new BigDecimal(longitudeStr == null ? "0" : longitudeStr));
            park.setLatitude(new BigDecimal(latitudeStr == null ? "0" : latitudeStr));
            Boolean result = parkService.addPark(park);
            message.setAll(SUCCESS, "添加成功!", result);
            LogFactory.info(this, IpAssist.getIp(req) + "添加停车场成功!" + park.toJson());
            return message.toJson();
        } catch (Exception e) {
            LogFactory.error(this, "添加停车场时发生异常!", e);
            return new RetMessage(ERROR, "发生异常!", null).toJson();
        }

    }

    @RequestMapping (value = "download", produces = "application/json;charset=utf-8")
    public void downloadExcelTemplate(HttpServletRequest req, HttpServletResponse res) {

        try {
            File file = new File(ParkController.class.getClassLoader().getResource("").toURI().getPath() + "/park.xlsx");
            res.setContentType("octets/stream");
            res.setHeader("Content-Disposition", "attachment;fileName=template.xlsx");
            FileInputStream fin = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int length = -1;
            while ((length = fin.read(bytes)) > 0) {
                res.getOutputStream().write(bytes, 0, length);
            }
            res.getOutputStream().flush();
            fin.close();
        } catch (Exception e) {
            LogFactory.error(this, "下载模版时发生异常!", e);
            res.setContentType("text/html;charset=utf-8");
            try {
                res.getOutputStream().println(new RetMessage(ERROR, "发生异常!", null).toJson());
            } catch (IOException e1) {
                LogFactory.error(this, "下载模版时发生二次异常!", e);
            }
        }

    }

    @Secret
    @SetUtf8
    @ResponseBody
    @RequestMapping (value = "addParks", produces = "application/json;charset=utf-8")
    public String addParks(HttpServletRequest req, HttpServletResponse res,@RequestParam ("file") MultipartFile multifile) {
        LogFactory.info(this, IpAssist.getIp(req) + "尝试批量添加停车场!");
        RetMessage message = new RetMessage();
        //修改为ip?或者前端传入的固定参数
        String sessionId = IpAssist.getIp(req);
        File file = new File(sessionId + ".xlsx");
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(file);
            byte[] content = new byte[1024];
            int byteSize = -1;
            InputStream inputStream=multifile.getInputStream();
            while((byteSize=inputStream.read(content))>0){
                fout.write(content,0,byteSize);
            }
            fout.flush();
            fout.close();
//            files.put(sessionId, file);
            List<String> list = CheckExcel.checkFile(file, sessionId);
            if (list == null || list.isEmpty()) {
                message.setAll(SUCCESS, "文件校验通过!", null);
                LogFactory.info(this, IpAssist.getIp(req) + "添加停车场,文件校验通过!");
                chekResult.put(sessionId, true);
            } else {
                message.setAll(FAIL, "文件校验不通过!", (Serializable) list);
                LogFactory.info(this, IpAssist.getIp(req) + "添加停车场,文件校验不通过!" + list);
                chekResult.put(sessionId, false);
            }
        } catch (Exception e) {
            LogFactory.error(this, "批量添加停车场时发生异常!", e);
            message.setAll(ERROR, "上传文件发生异常!", null);
        } finally {
            if (fout != null)
                try {
                    fout.close();
                    file.deleteOnExit();
//                    Files.deleteIfExists(Paths.get(URI.create(file.getAbsolutePath())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return message.toJson();
        }
    }

    @Secret
    @SetUtf8
    @ResponseBody
    @RequestMapping (value = "confirmAddParks", produces = "application/json;charset=utf-8")
    public String sureAddParks(HttpServletRequest req, HttpServletResponse res) {
        try {
            LogFactory.info(this, IpAssist.getIp(req) + "尝试确定添加停车场!");
            RetMessage message = new RetMessage();
            //修改为ip?或者前端传入的固定参数
            String sessionId = IpAssist.getIp(req);
            if (chekResult.get(sessionId) == null) {
                message.setAll(FAIL, "上传的文件校验结果不存在!", null);
            }
            if (chekResult.get(sessionId)) {
                Boolean result = parkService.addParks(CheckExcel.get(sessionId));
                if (result == null)
                    message.setAll(ERROR, "批量添加时异常!", null);
                if (result) {
                    LogFactory.info(this, IpAssist.getIp(req) + "确定添加停车场成功!");
                    message.setAll(SUCCESS, "添加成功!", null);

                } else
                    message.setAll(FAIL, "批量添加失败!", null);
            }
            return message.toJson();
        } catch (Exception e) {
            LogFactory.error(this, "添加停车场时发生异常!", e);
            return new RetMessage(ERROR, "发生异常!", null).toJson();
        }
    }

    @Secret
    @SetUtf8
    @ResponseBody
    @RequestMapping (value = "modifyPark", produces = "application/json;charset=utf-8")
    public String modifyPark(HttpServletRequest req, HttpServletResponse res) {
        try {

            RetMessage message = new RetMessage();
            String parkIdStr = req.getParameter("parkId");
            Long parkId = Long.valueOf(parkIdStr);
            Park park = parkService.queryParkInfo(parkId);
            LogFactory.info(this, IpAssist.getIp(req) + "尝试修改停车场!" + park.toJson());
            String province = req.getParameter("province");
            if (province != null && !province.trim().isEmpty())
                park.setProvince(province);

            String city = req.getParameter("city");
            if (city != null && !city.trim().isEmpty())
                park.setCity(city);

            String area = req.getParameter("area");
            if (area != null && !area.trim().isEmpty())
                park.setArea(area);

            String address = req.getParameter("address");
            if (address != null)
                if (CheckAssist.notParkAddress(address) || isNameOrAddress.negate().test(address)) {
                    message.setAll(FAIL, "停车场地址校验不通过!", null);
                    return message.toJson();
                } else
                    park.setAddress(address);

            String name = req.getParameter("name");
            if (name != null)
                if (CheckAssist.noteParkName(name) || isNameOrAddress.negate().test(name)) {
                    message.setAll(FAIL, "停车场名称校验不通过!", null);
                    return message.toJson();
                } else
                    park.setName(name);


            String server = req.getParameter("server");
            if (server != null)
                if (notParkServer(server)) {
                    message.setAll(FAIL, "服务商校验不通过!", null);
                    return message.toJson();
                } else
                    park.setServer(server);

            String services = req.getParameter("services");
            if (services != null)
                if (notParkServices(services)) {
                    message.setAll(FAIL, "服务校验不通过!", null);
                    return message.toJson();
                } else
                    park.setServices(services);

            Boolean result = park.updateById();
            if (result == null)
                message.setAll(ERROR, "修改时发生异常!", null);
            if (result) {
                LogFactory.info(this, IpAssist.getIp(req) + "修改停车场成功!" + park.toJson());
                message.setAll(SUCCESS, "修改成功!", null);
            } else
                message.setAll(FAIL, "修改失败!", null);

            return message.toJson();
        } catch (Exception e) {
            LogFactory.error(this, "添加停车场时发生异常!", e);
            return new RetMessage(ERROR, "发生异常!", null).toJson();
        }
    }

    @Secret
    @SetUtf8
    @ResponseBody
    @RequestMapping (value = "freezePark", produces = "application/json;charset=utf-8")
    public String freezePark(HttpServletRequest req, HttpServletResponse res) {
        String parkIdStr = req.getParameter("parkId");
        if (parkIdStr == null || parkIdStr.isEmpty()) {
            return new RetMessage(FAIL, "停车场编号为空!", null).toJson();
        }
        if (isInteger.negate().test(parkIdStr)) {
            return new RetMessage(FAIL, "停车场编号非法!", null).toJson();
        }
        Long id = Long.parseLong(parkIdStr);
        Park park = parkService.queryParkInfo(id);
        LogFactory.info(this, IpAssist.getIp(req) + "尝试停用停车场!" + park.toJson());
        boolean result = parkService.freezePark(park);
        if (result) {
            LogFactory.info(this, IpAssist.getIp(req) + "停用停车场!成功" + park.toJson());
            return new RetMessage(SUCCESS, "操作成功!", null).toJson();
        } else {
            LogFactory.info(this, IpAssist.getIp(req) + "停用停车场!失败" + park.toJson());
            return new RetMessage(FAIL, "操作失败!", null).toJson();
        }
    }

    @Secret
    @SetUtf8
    @ResponseBody
    @RequestMapping (value = "unfreezePark", produces = "application/json;charset=utf-8")
    public String unfreezePark(HttpServletRequest req, HttpServletResponse res) {
        String parkIdStr = req.getParameter("parkId");
        if (parkIdStr == null || parkIdStr.isEmpty()) {
            return new RetMessage(FAIL, "停车场编号为空!", null).toJson();
        }
        if (isInteger.negate().test(parkIdStr)) {
            return new RetMessage(FAIL, "停车场编号非法!", null).toJson();
        }
        Long id = Long.parseLong(parkIdStr);
        Park park = parkService.queryParkInfo(id);
        LogFactory.info(this, IpAssist.getIp(req) + "尝试启用停车场!" + park.toJson());
        boolean result = parkService.unfreezePark(park);
        if (result) {
            LogFactory.info(this, IpAssist.getIp(req) + "启用停车场成功!" + park.toJson());
            return new RetMessage(SUCCESS, "操作成功!", null).toJson();
        } else {
            LogFactory.info(this, IpAssist.getIp(req) + "启用停车场失败!" + park.toJson());
            return new RetMessage(FAIL, "操作失败!", null).toJson();
        }
    }


    @Secret
    @SetUtf8
    @ResponseBody
    @RequestMapping (value = "deletePark", produces = "application/json;charset=utf-8")
    public String deletePark(HttpServletRequest req, HttpServletResponse res) {
        String parkIdStr = req.getParameter("parkId");
        if (parkIdStr == null || parkIdStr.isEmpty()) {
            return new RetMessage(FAIL, "停车场编号为空!", null).toJson();
        }
        if (isInteger.negate().test(parkIdStr)) {
            return new RetMessage(FAIL, "停车场编号非法!", null).toJson();
        }
        Long id = Long.parseLong(parkIdStr);
        Park park = parkService.queryParkInfo(id);
        LogFactory.info(this, IpAssist.getIp(req) + "尝试删除停车场!" + park.toJson());
        boolean result = parkService.delPark(park);
        if (result) {
            LogFactory.info(this, IpAssist.getIp(req) + "删除停车场成功!" + park.toJson());
            return new RetMessage(SUCCESS, "操作成功!", null).toJson();
        } else {
            LogFactory.info(this, IpAssist.getIp(req) + "删除停车场失败!" + park.toJson());
            return new RetMessage(FAIL, "操作失败!", null).toJson();
        }
    }


}
