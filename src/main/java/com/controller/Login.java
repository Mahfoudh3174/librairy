package com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.database.UserDB;
import com.model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

        try {
            // Get form parameters
            String nom = request.getParameter("nom");
            if(nom == null || nom.trim().isEmpty()) {
            	throw new IllegalArgumentException("nom ne peut pas etre vide");
            }
            String password = request.getParameter("password");
            if(password==null || password.trim().isEmpty()) {
            	throw new IllegalArgumentException("mot de passe ne peut pas etre vide");
            }
            // Validate user credentials
            User user = UserDB.findUser(nom, password);
                 
                if (user != null) {
                    
                    session.setAttribute("id", user.getNumero());
                    
                    // Redirect based on role
                    switch (user.getRole()) {
                        case "admin":
                            request.getRequestDispatcher("/WEB-INF/admin_dashboard.jsp").forward(request, response);
                            break;
                        case "bibliothecaire":
                            request.getRequestDispatcher("/WEB-INF/gestionEmp.jsp").forward(request, response);
                            break;
                        default:
                            request.getRequestDispatcher("/WEB-INF/user_home.jsp").forward(request, response);
                            break;
                    }
                } else {
                    request.setAttribute("message", "User not found");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }

        } catch (IllegalArgumentException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
            
        }
	}

}
