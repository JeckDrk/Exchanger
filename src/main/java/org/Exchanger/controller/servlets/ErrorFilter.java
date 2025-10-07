package org.Exchanger.controller.servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.Exchanger.errors.*;
import org.Exchanger.utils.mapper.BaseMapper;
import org.Exchanger.utils.wrapper.ResponseWrapper;

import java.io.IOException;

@WebFilter(urlPatterns = {"/currencies", "/currency/*", "/exchangeRate/*", "/exchangeRates", "/exchange"})
public class ErrorFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        servletRequest.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ApplicationException e) {
            ResponseWrapper.send(response, e.getError());
        } catch (Exception e) {
            ResponseWrapper.send(response, BaseMapper.toJson("Неопознанная ошибка"), HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
