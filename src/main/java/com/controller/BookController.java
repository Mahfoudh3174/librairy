package com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.database.BookDB;
import com.database.UserDB;
import com.model.Book;

/**
 * Servlet implementation class BookController
 */
@WebServlet("BookController")
public class BookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/admin/dasboard").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	       
		try {
			Book book=new Book();
		    // Validate ISBN
		    String isbn = request.getParameter("isbn");
		    if (isbn == null || isbn.trim().isEmpty()) {
		        throw new IllegalArgumentException("ISBN ne peut pas etre vide.");
		    }
		    if(BookDB.isBook(isbn)) {
		    	 throw new IllegalArgumentException("le livre dejas existe.");
		    }
		    book.setISBN(isbn);

		    // Validate Title
		    String titre = request.getParameter("titre");
		    if (titre == null || titre.trim().isEmpty()) {
		        throw new IllegalArgumentException("Titre ne peut pas etre vide.");
		    }
		    book.setTitre(titre);

		    // Validate Author
		    String auteur = request.getParameter("auteur");
		    if (auteur == null || auteur.trim().isEmpty()) {
		        throw new IllegalArgumentException("Author ne peut pas etre vide.");
		    }
		    book.setAuteur(auteur);

		    // Validate Publication Year
		    String publicationYear = request.getParameter("annee");
		    if (publicationYear == null || !publicationYear.matches("\\d{4}")) { // Validate 4-digit year
		        throw new IllegalArgumentException("Anne de Publication doit etre quatre chifre.");
		    }
		    book.setPublication(publicationYear);

		    // Validate Quantity
		    String quantityStr = request.getParameter("quantite");
		    if (quantityStr == null || !quantityStr.matches("\\d+")) { // Validate numeric quantity
		        throw new IllegalArgumentException("Quantite doit etre un nombre positive.");
		    }
		    int quantity = Integer.parseInt(quantityStr);
		    if (quantity < 0) {
		        throw new IllegalArgumentException("Quantite doit etre un nombre positive.");
		    }
		    book.setQuantity(quantity);
		    BookDB.addBook(book);
		    request.setAttribute("success","book added successfully" );
		    request.getRequestDispatcher("WEB-INF/admin/dashboard.jsp").forward(request, response);
            
		    
		} catch (IllegalArgumentException e) {
		    // Handle validation error (e.g., forward to an error page or send an error response)
		    request.setAttribute("fail", e.getMessage());
		    request.getRequestDispatcher("WEB-INF/admin/dashboard.jsp").forward(request, response);
		    
		}
	}

}
