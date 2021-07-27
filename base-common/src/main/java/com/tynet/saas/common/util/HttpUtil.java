package com.tynet.saas.common.util;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * Http工具类
 *
 * @author Created by 思伟 on 2020/7/6
 */
public class HttpUtil {

    /**
     * 获取Http链接扩展名
     *
     * @param url            URL地址
     * @param defaultExtName 当获取到为空时，要返回默认的扩展名
     * @return 获取到的扩展名或默认值
     */
    public static String getExtName(final String url, final String defaultExtName) {
        return StringUtils.defaultIfBlank(FileUtil.extName(url), defaultExtName);
    }

    /**
     * 拼接URL字符串(同时会转换\或/出现2次以上的转/)推荐使用
     *
     * @param url URL字符串
     * @return 拼接后的URL字符串
     */
    public static final String generateHttpUrl(final String... url) {
        if (null == url) {
            return null;
        }
        // org.apache.commons.lang3.StringUtils.trimToEmpty(url);
        // ArrayUtils.nullToEmpty(url);

        // StringUtils.join自动过滤Null值
        String uri = StringUtils.join(url, "/");
        // (?i)在前面 不区分大小写
        // ((ht|f)tp(s?)\:)?
        return uri.replaceAll("(\\\\|/){2,}", Matcher.quoteReplacement("/"))
                .replaceFirst("(?i)((ht|f)tp\\:(\\\\|/)+)", "http://")
                .replaceFirst("(?i)((ht|f)tps\\:(\\\\|/)+)", "https://");
    }

    /**
     * GET方式拼接URL参数
     * <p>
     * TODO Get方式暂时只实现了Map类型入参拼接URL，后续可增加String、JSONArray...
     * 如果body不为空，且URL为/结尾时，必须拿除
     * </p>
     *
     * @param url  URL地址
     * @param body http参数内容
     * @return URL地址
     */
    public static String generateGetParams(final String url, final Object body) {
        if (null == url) {
            return null;
        }
        StringBuffer sb = new StringBuffer(StringUtils.removeEnd(url, "/"));
        if (null != body && body instanceof Map && !ObjectUtils.isEmpty(body)) {
            // 拼接URI（特殊处理）
            if (!StringUtils.contains(url, GET_REQ_CHAR)) {
                sb.append(GET_REQ_CHAR);
            } else {
                if (!StringUtils.endsWith(url, GET_REQ_CHAR)
                        && !StringUtils.endsWith(url, GET_SPLIT_CHAR)) {
                    sb.append(GET_SPLIT_CHAR);
                }
            }
            String urlParams = transferParamsByJoin((Map<?, ?>) body, GET_JOIN_CHAR, GET_SPLIT_CHAR);
            sb.append(urlParams);
        }
        return sb.toString();
    }

    /**
     * Map集合转为自定义连接字符串
     *
     * @param paramsMap Map<?, ?>
     * @param join      连接的字符串
     * @param split     分隔符
     * @return String
     */
    public static final String transferParamsByJoin(Map<?, ?> paramsMap, String join, String split) {
        if (ObjectUtils.isEmpty(paramsMap)) {
            return StringUtils.EMPTY;
        }
        StringBuffer sb = new StringBuffer();
        Set<? extends Map.Entry<?, ?>> entrySet = paramsMap.entrySet();
        // 是否是第一次
        boolean first = true;
        for (Map.Entry<?, ?> entry : entrySet) {
            if (null != entry.getValue()) {
                if (!first) {
                    sb.append(split);
                }
                first = false;
                sb.append(entry.getKey());
                sb.append(join);
                // TODO 如果Value也是Map的话还要进行判断
                // sb.append(entry.getValue());
                sb.append(com.tynet.saas.common.util.StringUtils.toString(entry.getValue()));
            }
        }
        return sb.toString();
    }

    /**
     * Get方式发送请求的标识
     */
    public final static String GET_REQ_CHAR = "?";

    /**
     * Get方式参数拼接符
     */
    public final static String GET_JOIN_CHAR = "=";

    /**
     * Get方式参数分割符
     */
    public final static String GET_SPLIT_CHAR = "&";

    /**
     * 判断字符串是否是一个IP地址
     *
     * @param addr 请求主机IP地址
     * @return boolean
     */
    public static boolean isIPAddr(String addr) {
        if (StringUtils.isEmpty(addr)) {
            return false;
        }
        String[] ips = StringUtils.split(addr, '.');
        if (ips.length != 4) {
            return false;
        }
        try {
            int ipa = Integer.parseInt(ips[0]);
            int ipb = Integer.parseInt(ips[1]);
            int ipc = Integer.parseInt(ips[2]);
            int ipd = Integer.parseInt(ips[3]);
            return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0
                    && ipc <= 255 && ipd >= 0 && ipd <= 255;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 是否内网ip地址
     *
     * @param addr 请求主机IP地址
     * @return boolean
     */
    public static boolean isLocalIPAddr(String addr) {
        return (addr.startsWith("10.") || addr.startsWith("192.168.") || "127.0.0.1".equals(addr));
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request HttpServletRequest
     * @return 请求主机IP地址
     * @throws IOException
     */
    public final static String getRemoteAddr(HttpServletRequest request) throws IOException {
        Assert.notNull(request, "request must not be null");
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        String unknown = "unknown";
        if (StringUtils.isNotEmpty(ip) && !unknown.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (StringUtils.contains(ip, ",")) {
                ip = ip.split(",")[0];
            }
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else {
            int maxIpCheckLength = 15;
            if (StringUtils.isNotBlank(ip) && ip.length() > maxIpCheckLength) {
                String[] ips = ip.split(",");
                for (int index = 0; index < ips.length; index++) {
                    String strIp = ips[index];
                    if (!unknown.equalsIgnoreCase(strIp)) {
                        ip = strIp;
                        break;
                    }
                }
            }
        }
        // 获取失败返回本机IP
        return isIPAddr(ip) ? ip : "127.0.0.1";
    }
}
