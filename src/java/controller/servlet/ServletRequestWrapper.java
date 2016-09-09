/*
 * ServletRequestWrapper.java
 */
package controller.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Contains the session and request parameters
 * 
 * @author Kostiantyn Kovalchuk
 */
public class ServletRequestWrapper extends HttpServletRequestWrapper {

    public ServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    
    public static ServletRequestWrapper getRequest(HttpServletRequest request) {
        return new ServletRequestWrapper(request);
    }
}
