/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.controllers;

import es.albarregas.beans.Ave;
import es.albarregas.connection.Conectar;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author shirone
 */
@WebServlet(name = "Operar", urlPatterns = {"/operar"})
public class Operar extends HttpServlet {
    DataSource datasource;
    
    public void init(ServletConfig config) throws ServletException{//** rellenamos el datasource con nuestra clase Conectar **//
        try{
            datasource=Conectar.getData();
        }catch(Exception e){
            System.out.println("fallo al crear el datasource");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //** lista de objetos generales que utilizaremos dependiendo de nuestra funcion **//
        ArrayList<Ave> aves= null;
        Ave ave = null;
        Connection con = null;
        ResultSet resultado = null;
        PreparedStatement prp = null;
        String url="";
        //** Aqui creamos una sesión antes de llegar al primer jsp y en todas las demas solo iremos obteniendo esta sesion creada aqui  **//
        HttpSession sesion = request.getSession(true);
        
        //** si ya existia algún error cuando haciamos alguna operación borramos el atributo error o el atributo ave porque estamos empezando desde el principio **//
        if(sesion.getAttribute("error") != null){
            sesion.removeAttribute("error");
        }
        if(sesion.getAttribute("ave") != null){
           sesion.removeAttribute("error");
        }

        
        switch(request.getParameter("opcion")){//** dependiendo de la opcion elegida en el formulario del index iremos a un jsp u otro **//
            case "crear":
                url="JSP/INSERTAR/inicioInsertar.jsp"; 
                break;
            case "leer":
                url="JSP/LEER/leer.jsp";
                break;
            case "actualizar":
                url="JSP/ACTUALIZAR/leerActualizar.jsp";
                break;
            case "eliminar":
                url="JSP/ELIMINAR/leerEliminar.jsp";
                break;
        }
        
        if(request.getParameter("opcion").equals("leer") || request.getParameter("opcion").equals("actualizar") || request.getParameter("opcion").equals("eliminar")){
            //** la opcion leer, actualizar y eliminar necesitan la misma opcion que es visualizar nuestra tabla asi que la dejamos en un arraylist por sesión **//
            try{
                //** cargamos los datos de la tabla a mostrar y lo añadimos a un ArrayList<Ave> **//
                con=datasource.getConnection();
                prp=con.prepareStatement("SELECT * FROM pajaros");
                resultado=prp.executeQuery();
                aves= new ArrayList<Ave>();
                while(resultado.next()){
                 aves.add( new Ave(resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getDate(4)));
                }
                sesion.setAttribute("aves", aves);
            }catch(SQLException ex){
                ex.printStackTrace();
            }finally{//** cerramos la conexion, el resultset y el preparedStatement **//
                Conectar.cerrarConexiones(con, resultado, prp);
            }
        }

        
        request.getRequestDispatcher(url).forward(request, response);
        
    }

}
