package net.imwork.yangyuanjian.common.assist;


import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangyuanjian on 12/27/2017.
 */
public interface IpAssist {
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
