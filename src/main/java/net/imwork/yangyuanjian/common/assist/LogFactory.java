package net.imwork.yangyuanjian.common.assist;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by thunderobot on 2017/11/5.
 */
public class LogFactory {
    private static final Map<String, Logger> loggers=new ConcurrentHashMap<String, Logger>();

    /**日志文件已存在时是否清空原有日志*/
    private static boolean clear = false;
    /**日志的记录等级*/
    public static LogLevel logLevel=LogLevel.INFO;
    /**
     * 日志配置初始化
     */
    public static void init() {
        try {
            ClassLoader loader=LogFactory.class.getClassLoader();
            if(loader.getClass().equals(java.net.URLClassLoader.class))
                loader=sun.misc.Launcher.getLauncher().getClassLoader();
            URL url=loader.getResource("");
            String path = url.toURI().getPath();
            if (path.contains("target"))
                path = path.substring(0, path.indexOf("target")) + "/src/main/resources/log/daily.log";
            else if(path.contains("WEB-INF"))
                path = path.substring(0, path.indexOf("WEB-INF")) + "/WEB-INF/classes/log/daily.log";
            else
                path=path+"/log/daily.log";
            String tempPath = path.substring(0, path.indexOf("/daily.log"));
            File logDir = new File(tempPath);
            if (!logDir.exists())
                logDir.mkdirs();
            path=path.substring(0,path.indexOf("/daily.log"));
            Properties properties = new Properties();
            //配置日志等级 以及 日志对象
            properties.setProperty("log4j.rootLogger", logLevel+",debug,info,warn,error,console");
//			properties.setProperty("log4j.appender.INFO", "org.apache.log4j.ConsoleAppender");
//			properties.setProperty("log4j.appender.INFO.layout", "org.apache.log4j.PatternLayout");
//			properties.setProperty("log4j.appender.INFO.layout.ConversionPattern", "%d %p [%c] - <%m>%n");
            //配置每日生成日志文件 形式的日志对象
            properties.setProperty("log4j.appender.debug", "com.lanxi.util.entity.LogAppender");
            //配置日志文件路径
            properties.setProperty("log4j.appender.debug.File", path+"/debug/debug.log");
            File dir=new File(path+"debug");
            if(!dir.exists())
                dir.mkdir();
            dir=new File(dir.getAbsolutePath()+"debug.log");
            if(!dir.exists())
                dir.createNewFile();
            properties.setProperty("log4j.appender.debug.Threshold", "DEBUG");
            //配置日志文件编码方式
            properties.setProperty("log4j.appender.debug.Encoding", "UTF-8");
            //配置日志文件为追加形式
            properties.setProperty("log4j.appender.debug.File.Append", "true");
            //配置自动生成日志文件的后缀名
            properties.setProperty("log4j.appender.debug.DatePattern", "yyyy-MM-dd'.debug.log'");
            //配置日志文件的记录形式
            properties.setProperty("log4j.appender.debug.layout", "org.apache.log4j.PatternLayout");
            //配置日志文件表达式
            properties.setProperty("log4j.appender.debug.layout.ConversionPattern", "%d %p [%c] - <%m>%n");
            //配置每日生成日志文件 形式的日志对象
            properties.setProperty("log4j.appender.info", "com.lanxi.util.entity.LogAppender");
            //配置日志文件路径
            properties.setProperty("log4j.appender.info.File", path+"/info/info.log");
            dir=new File(path+"info");
            if(!dir.exists())
                dir.mkdir();
            dir=new File(dir.getAbsolutePath()+"info.log");
            if(!dir.exists())
                dir.createNewFile();
            properties.setProperty("log4j.appender.info.Threshold", "INFO");
            //配置日志文件编码方式
            properties.setProperty("log4j.appender.info.Encoding", "UTF-8");
            //配置日志文件为追加形式
            properties.setProperty("log4j.appender.info.File.Append", "true");
            //配置自动生成日志文件的后缀名
            properties.setProperty("log4j.appender.info.DatePattern", "yyyy-MM-dd'.info.log'");
            //配置日志文件的记录形式
            properties.setProperty("log4j.appender.info.layout", "org.apache.log4j.PatternLayout");
            //配置日志文件表达式
            properties.setProperty("log4j.appender.info.layout.ConversionPattern", "%d %p [%c] - <%m>%n");
            //配置每日生成日志文件 形式的日志对象
            properties.setProperty("log4j.appender.warn", "com.lanxi.util.entity.LogAppender");
            //配置日志文件路径
            properties.setProperty("log4j.appender.warn.File", path+"/warn/warn.log");
            dir=new File(path+"warn");
            if(!dir.exists())
                dir.mkdir();
            dir=new File(dir.getAbsolutePath()+"warn.log");
            if(!dir.exists())
                dir.createNewFile();
            properties.setProperty("log4j.appender.warn.Threshold", "WARN");
            //配置日志文件编码方式
            properties.setProperty("log4j.appender.warn.Encoding", "UTF-8");
            //配置日志文件为追加形式
            properties.setProperty("log4j.appender.warn.File.Append", "true");
            //配置自动生成日志文件的后缀名
            properties.setProperty("log4j.appender.warn.DatePattern", "yyyy-MM-dd'.warn.log'");
            //配置日志文件的记录形式
            properties.setProperty("log4j.appender.warn.layout", "org.apache.log4j.PatternLayout");
            //配置日志文件表达式
            properties.setProperty("log4j.appender.warn.layout.ConversionPattern", "%d %p [%c] - <%m>%n");
            //配置每日生成日志文件 形式的日志对象
            properties.setProperty("log4j.appender.error", "com.lanxi.util.entity.LogAppender");
            //配置日志文件路径
            properties.setProperty("log4j.appender.error.File", path+"/error/error.log");
            dir=new File(path+"error");
            if(!dir.exists())
                dir.mkdir();
            dir=new File(dir.getAbsolutePath()+"error.log");
            if(!dir.exists())
                dir.createNewFile();
            properties.setProperty("log4j.appender.error.Threshold", "ERROR");
            //配置日志文件编码方式
            properties.setProperty("log4j.appender.error.Encoding", "UTF-8");
            //配置日志文件为追加形式
            properties.setProperty("log4j.appender.error.File.Append", "true");
            //配置自动生成日志文件的后缀名
            properties.setProperty("log4j.appender.error.DatePattern", "yyyy-MM-dd'.error.log'");
            //配置日志文件的记录形式
            properties.setProperty("log4j.appender.error.layout", "org.apache.log4j.PatternLayout");
            //配置日志文件表达式
            properties.setProperty("log4j.appender.error.layout.ConversionPattern", "%d %p [%c] - <%m>%n");
            //配置控制台输出的日志对象
            properties.setProperty("log4j.appender.console", "org.apache.log4j.ConsoleAppender");
            properties.setProperty("log4j.appender.console.layout", "org.apache.log4j.PatternLayout");
            properties.setProperty("log4j.appender.console.layout.ConversionPattern", "%d %p [%c] - <%m>%n");
            PropertyConfigurator.configure(properties);
            System.out.println("log4j初始化完成!");
            LogFactory.info(LogFactory.class,"log4j初始化完成!");
        } catch (Exception e) {
            throw new RuntimeException("加载log4j配置异常", e);
        }
    }

    public static boolean isClear() {
        return clear;
    }

    /**
     * 设置清空日志
     * @param    clear  清空日志的标记
     * 已失效
     */
    @Deprecated
    public static void setClear(boolean clear) {
        LogFactory.clear = clear;
    }

    public static LogLevel getLogLevel() {
        return logLevel;
    }

    public static void setLogLevel(LogLevel logLevel) {
        LogFactory.logLevel = logLevel;
    }

    public static enum LogLevel{
        DEBUG,INFO,WARN,ERROR;
    }

    /**
     * 判定一个实例是否是Class对象
     * @param obj 传入的实例
     * @return 	true obj是Class对象时
     * 			fals obj不是Class对象时
     */
    private static boolean isClass(Object obj){
        if(obj instanceof Class<?>)
            return true;
        else
            return false;
    }
    /**
     * 获取一个对象的Class实例
     * @param obj 传入的对象
     * @return obj本身 obj是Class实例时
     * 		   obj的Class obj不是Class实例时
     */
    private static Class<?> getClass(Object obj){
        if(isClass(obj))
            return (Class<?>) obj;
        else
            return obj.getClass();
    }
    /**
     * 为一个对象创建 logger实例
     * @param obj 需要创建logger的对象的实例或Class对象
     */
    private static void createLogger(Object obj){
        Class<?> clazz=null;
        clazz=LogFactory.getClass(obj);
        Logger logger= Logger.getLogger(clazz);
        loggers.put(clazz.getName(), logger);
    }
    /**
     * 获取一个对象的类的logger实例,若对应logger不存在则会创建并返回
     * @param obj 需要获取logger的对象或实例
     * @return 获取到的logger的实例
     */
    public static Logger getLogger(Object obj){
        Class<?> clazz=null;
        if(obj instanceof Class<?>)
            clazz=(Class<?>)obj;
        else
            clazz=obj.getClass();
        Logger logger=loggers.get(clazz.getName());
        if(logger==null){
            createLogger(clazz);
            return getLogger(clazz);
        }
        return logger;
    }
    /**
     * 记录debug级别的日志
     * @param obj 		记录日志的对象
     * @param message	日志消息
     */
    public static void debug(Object obj,String message){
        getLogger(obj).debug(message);
    }
    /**
     * 记录debug级别的日志
     * @param obj 		记录日志的对象
     * @param message	日志消息
     * @param t 		异常源
     */
    public static void debug(Object obj,String message,Throwable t){
        getLogger(obj).debug(message, t);
    }
    /**
     * 记录info级别的日志
     * @param obj 		记录日志的对象
     * @param message	日志消息
     */
    public static void info(Object obj,String message){
        getLogger(obj).info(message);
    }
    /**
     * 记录info级别的日志
     * @param obj 		记录日志的对象
     * @param message	日志消息
     * @param t 		异常源
     */
    public static void info(Object obj,String message,Throwable t){
        getLogger(obj).info(message, t);
    }
    /**
     * 记录warn级别的日志
     * @param obj 		记录日志的对象
     * @param message	日志消息
     */
    public static void warn(Object obj,String message){
        getLogger(obj).warn(message);
    }
    /**
     * 记录warn级别的日志
     * @param obj 		记录日志的对象
     * @param message	日志消息
     * @param t 		异常源
     */
    public static void warn(Object obj,String message,Throwable t){
        getLogger(obj).warn(message, t);
    }
    /**
     * 记录error级别的日志
     * @param obj 		记录日志的对象
     * @param message	日志消息
     */
    public static void error(Object obj,String message){
        getLogger(obj).error(message);
    }
    /**
     * 记录error级别的日志
     * @param obj 		记录日志的对象
     * @param message	日志消息
     * @param t 		异常源
     */
    public static void error(Object obj,String message,Throwable t){
        getLogger(obj).error(message, t);
    }
}
