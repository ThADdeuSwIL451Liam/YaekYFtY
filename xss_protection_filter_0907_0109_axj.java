// 代码生成时间: 2025-09-07 01:09:46
package com.example.security;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(1)
public class XssProtectionFilter implements Filter {

    private final XssFilterConfig xssFilterConfig; // 过滤配置

    public XssProtectionFilter(XssFilterConfig xssFilterConfig) {
        this.xssFilterConfig = xssFilterConfig;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化过滤器时可以进行一些配置
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        boolean shouldApplyFilter = shouldApplyFilter(request);
        if (shouldApplyFilter) {
            chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request, xssFilterConfig), servletResponse);
        } else {
            chain.doFilter(request, servletResponse);
        }
    }

    @Override
    public void destroy() {
        // 清理资源，如果需要
    }

    private boolean shouldApplyFilter(HttpServletRequest request) {
        // 确定是否需要对这个请求应用XSS过滤
        return xssFilterConfig.isEnabled() && !request.getRequestURI().startsWith("/safe-path");
    }
}

// 下面是XssHttpServletRequestWrapper类，用于包装HttpServletRequest，以实现XSS过滤
class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final XssFilterConfig xssFilterConfig;

    public XssHttpServletRequestWrapper(HttpServletRequest request, XssFilterConfig xssFilterConfig) {
        super(request);
        this.xssFilterConfig = xssFilterConfig;
    }

    @Override
    public String getParameter(String name) {
        // 过滤参数值中的XSS攻击
        String value = super.getParameter(name);
        if (xssFilterConfig.isEnabled() && value != null) {
            value = xssFilterConfig.getHtmlEscaper().escape(value);
        }
        return value;
    }

    // 重写其他方法以进行XSS过滤
    // ...
}

// 配置XssFilterConfig类以提供过滤配置
@Component
public class XssFilterConfig {

    private boolean enabled;
    private HtmlEscaper htmlEscaper;

    // 构造函数、getter和setter
    // ...

    public boolean isEnabled() {
        return enabled;
    }

    public HtmlEscaper getHtmlEscaper() {
        return htmlEscaper;
    }

    // 其他方法
    // ...
}

// 错误处理可以使用@ControllerAdvice来全局处理异常
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(XssException.class)
    public ResponseEntity<String> handleXssException(XssException ex) {
        // 处理异常，返回错误信息
        return new ResponseEntity<>("XSS attack detected and blocked.", HttpStatus.BAD_REQUEST);
    }

    // 其他异常处理
    // ...
}