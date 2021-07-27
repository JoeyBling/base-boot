package com.tynet.saas.common.hessian;

import com.caucho.hessian.server.HessianSkeleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.util.NestedServletException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * 自定义Hessian接口服务端-HessianServiceExporter
 * 会将客户端的请求request放到ThreadLocal中，在service实现类中，统一通过{@code HessianContext}获取请求的request对象
 * 通过自定义注解发布Hessian服务:{ https://blog.csdn.net/myth_g/article/details/88041459 }
 * <p>
 * 参考`wechat-repeater` {@code com.tynet.common.hessian.CryptoHessianServiceExporter} 改进
 * </p>
 *
 * @author Created by 思伟 on 2020/7/10
 * @since 4.x
 */
public class HessianServiceExporter extends org.springframework.remoting.caucho.HessianServiceExporter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (!"POST".equals(request.getMethod())) {
            throw new HttpRequestMethodNotSupportedException(request.getMethod(),
                    new String[]{"POST"}, "HessianServiceExporter only supports POST requests");
        }
        response.setContentType(CONTENT_TYPE_HESSIAN);
        try {
            Enumeration<String> enumeration = request.getHeaderNames();
            while (enumeration.hasMoreElements()) {
                final String element = enumeration.nextElement();
                /*logger.debug("Hessian request get headerName[{}],value[{}]", element,
                        URLDecoder.decode(request.getHeader(element), SystemConst.DEFAULT_CHARSET.name()));*/
            }
            final Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                final String[] paramValArr = request.getParameterValues(parameterNames.nextElement());
            }
            // request.getContextPath();
            // String authorization = request.getHeader("Authorization");
            logger.debug("Hessian请求访问:[{}],请求主机:[{}]", request.getRequestURI(), request.getHeader("host"));
            invoke(request.getInputStream(), response.getOutputStream());
        } catch (Throwable ex) {
            logger.error("Hessian skeleton invocation failed", ex);
            throw new NestedServletException("Hessian skeleton invocation failed", ex);
        } finally {
            // 统一释放资源
        }
    }

    @Override
    protected void doInvoke(HessianSkeleton skeleton, InputStream inputStream, OutputStream outputStream)
            throws Throwable {
        super.doInvoke(skeleton, inputStream, outputStream);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        logger.debug("The Hessian serviceInterface [{}] " +
                        "use the implementation class [{}] to load complete!",
                getServiceInterface().getSimpleName(), this.getService());
    }

}
