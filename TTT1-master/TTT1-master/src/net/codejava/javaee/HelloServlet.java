package net.codejava.javaee;

import java.io.IOException;
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;
import org.owasp.encoder.Encode;


// CLEAN CODEEEEEEEEEEEEEEEEEE
/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/helloServlet")
public class HelloServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() 
	{
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
		{
			/*
		BAD CODE XSS
		
		PrintWriter out = response.getWriter();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		out.println("<h1>Hello " + username + "</h1>");
		out.println("<h1>Your Password: "+ password + "</h1>");
		*/
		//END OF BAD CODE XSS 
		
		//Blacklisting xss code prevention
	/*	PrintWriter out=response.getWriter();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		

				 String user1=username.replaceAll("(?i)<script.*?>.*?</script.*?>", "");  // case 1
			     username.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", ""); // case 2
			     username.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");     // case 3
				 
				 String password1=password.replaceAll("(?i)<script.*?>.*?</script.*?>", "");  // case 1
			     password.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", ""); // case 2
			     password.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");     // case 3
				 
		
		out.println("Blacklisting XSS prevention ");
	
		response.getWriter().write(Encode.forHtml("Helo " +user1 +"\n"));
		response.getWriter().write(Encode.forHtml("Your Password : " +password1 ));
*/
	
	
		//END OF BLACKLISTING XSS CODE PREVENTION
		
		//Whitelisting xss code prevention
		PrintWriter out=response.getWriter();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
		Matcher matcher = pattern.matcher(username);

		
		
		if (matcher.matches()) 
		{
			out.println("Whitelisting Xss Prevent");
			response.getWriter().write(Encode.forHtml("Helo " +username +"\n"));
			response.getWriter().write(Encode.forHtml("Your Password  :" +password +"\n"));
			
	
			
		}
		else
		{
		out.println("Whitelisting Xss Prevent");
		out.println("User Name: "+ "Please enter only alphabets and numbers");
		out.println("Password: "+ "Please enter only alphabets and numbers");
		
		}
		//END OF WHITELISTING XSS CODE PREVENTION 
		

		
		try {

            Connection conn = null;
            String url = "jdbc:mysql://192.168.2.128:3306/";
            String dbName = "anvayaV2";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String passwd = "";
            try {
                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, passwd);

				
				//bad code sqli 
               // Statement st = conn.createStatement();
               // String query = "SELECT * FROM  User where userId= "+ username;
				//bad code sqli
				
				//good code sqli
				PreparedStatement st = conn.prepareStatement("SELECT * FROM  User where userId=?");
				st.setString(1, username); 
				//good code sqli
				
			
				//bad code sqli
				//ResultSet res = st.executeQuery(query);
				//bad code sqli
				
				//good code sqli
				ResultSet res = st.executeQuery();
				//good code sqli

                out.println("Results");
                while (res.next()) {
                    String s = res.getString("username");
					response.getWriter().write(Encode.forHtml(s));
                }
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            out.close();
        }
		//out.close();
	}

}
