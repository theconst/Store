/*
 * ControllerServlert.java 4.01.2016
 */
package controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.command.ActionFactory;
import controller.command.ActionCommand;
import static controller.servlet.ServletConstants.ADDTOBLACKLIST;
import static controller.servlet.ServletConstants.ADDTOCART;
import static controller.servlet.ServletConstants.LOGIN;
import static controller.servlet.ServletConstants.LOGOUT;
import static controller.servlet.ServletConstants.ORDER;
import static controller.servlet.ServletConstants.REMOVEFROMBLACKLIST;
import static controller.servlet.ServletConstants.REMOVEFROMCART;
import static controller.servlet.ServletConstants.SERVLET_NAME;
import static controller.servlet.ServletConstants.SUBMITORDER;
import static controller.servlet.ServletConstants.VIEWBLACKLIST;
import static controller.servlet.ServletConstants.VIEWCUSTOMERLIST;
import static controller.servlet.ServletConstants.VIEWSHOPCONTENT;

/**
 * Main controller servlet for the application
 * 
 * @author Kostiantyn Kovalchuk
 */
@WebServlet(
        name = SERVLET_NAME,
        urlPatterns = {
            LOGOUT,
            LOGIN,
            ORDER,
            ADDTOBLACKLIST,
            REMOVEFROMBLACKLIST,
            ADDTOCART,
            REMOVEFROMCART,
            SUBMITORDER,
            VIEWSHOPCONTENT,
            VIEWCUSTOMERLIST,
            VIEWBLACKLIST
        }
)
public class ControllerServlet extends HttpServlet {
    
    private static final String DESCRIPTION = "Web store";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ServletRequestWrapper content = ServletRequestWrapper.getRequest(request);
        ActionFactory actionFactory = ActionFactory.getInstance();
        ActionCommand command = actionFactory.defineCommand(content);
        String jspPage = command.execute(content);
        if (jspPage != null) {
            request.getRequestDispatcher(jspPage).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return DESCRIPTION;
    }// </editor-fold>

}
