/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 *
 * @author Simon
 */
@WebFilter(filterName = "UTF8Filter", urlPatterns = {"/*"})
public class UTF8Filter implements Filter {
    private String encoding;
    
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    public void destroy() {        
    }
    
    public void init(FilterConfig filterConfig) throws ServletException{        
        encoding = filterConfig.getInitParameter("requestEncoding");
        if (encoding == null){
            encoding = "UTF-8";
        }
    }

    
}