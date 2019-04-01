/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author chentianxin
 */
@WebServlet(name = "PokemonApp",
        urlPatterns = {"/getAPokemon/*"})
public class PokeServlet extends HttpServlet {
    
    PokeAPIModel pm = null;  // The "business model" for this app
    
    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        pm = new PokeAPIModel();
    }
    
    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("hi");
        if((request.getPathInfo())==null){
            response.setStatus(401);
            return;
        }
        
        String pokeID = (request.getPathInfo()).substring(1);
        // return 401 if name not provided
        if(pokeID.equals("")) {
            response.setStatus(401);
            return;
        }        
        
        if (pokeID != null) {
            // use model to do the search and choose the result view
            String pokeInfo = pm.doPokeSearch(pokeID);
            
            // Things went well so set the HTTP response code to 200 OK
            response.setStatus(200);
            // tell the client the type of the response
            response.setContentType("text/plain;charset=UTF-8");

            PrintWriter out = response.getWriter();
            out.println(pokeInfo);
            System.out.println(pokeInfo);
            System.out.println("dakjdsklasjklxamsklmxaklsxakslj im  herer!!!");
        } else {
            response.setStatus(401);
        }
        
    }
    
}