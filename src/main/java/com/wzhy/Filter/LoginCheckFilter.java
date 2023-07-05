package com.wzhy.Filter;

import com.alibaba.fastjson.JSON;
import com.wzhy.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//檢查是否過濾
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
//    路径匹配器
    public static final AntPathMatcher Path_Matcher = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
//    1.获取本次请求的URI
       String uri = request.getRequestURI();
//        不需要处理的请求 如何匹配/backend/index.html 使用路径匹配器
        String[] URLs= new String[]{
                "/employee/login","/employee/logout","/backend/**","/front/**"
        };
        //    2.判断本次请求是否需要处理
        boolean check = check(uri,URLs);
//    3.如果不需要处理，则直接放行判断登录状态，
    if (check){
        log.info("本次请求不需要处理{} ",uri);
        filterChain.doFilter(request,response);
        return;
    }
//    4.如果已登录，则直接放行
        if (request.getSession().getAttribute("employee")!=null){
            log.info("用户已登录 Id为{} ",request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;

        };
//    5.如果未登录则返回未登录结果,通过输出流向页面响应
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("用户未登录");

        return;
    }
//    封装判断方法
    public boolean check (String requestURI,String[] urls) {
        for (String url : urls) {
            boolean match = Path_Matcher.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
