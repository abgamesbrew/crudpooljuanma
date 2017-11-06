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
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author shirone
 */
@WebServlet(name = "Realizar", urlPatterns = {"/realizar"})
public class Realizar extends HttpServlet {
        DataSource datasource;
        
     public void init(ServletConfig config) throws ServletException{
        try{
            datasource=Conectar.getData();
        }catch(Exception e){
            System.out.println("fallo al crear el datasource");
        }
    }

protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    ArrayList<Ave> aves= null;
    Ave ave = null;
    Connection con = null;
    PreparedStatement prp =null;
    ResultSet resultado = null;
    String url="";
    String error="";
    HttpSession sesion = request.getSession(false);
    
    if(request.getParameter("cancelar") != null){//** si en alguno de los jsp lanzados por Operar llegamos a Realizar con el parametro cancelar nos volvemos al index **//
      url="index.html";
    }
    
    
    if(request.getParameter("insertar") != null){//** si venimos del formulario inicioInsertar **//
        if(request.getParameter("anilla").equals("") || request.getParameter("especie").equals("")|| request.getParameter("lugar").equals("")|| request.getParameter("fecha").equals("")){
           //** comprobamos que todos los campos estén rellenos y si no lo están volveremos al formulario con el error correspondiente **//
            error="Uno de los campos no ha sido rellenado";
            sesion.setAttribute("error", error);
            url="JSP/INSERTAR/inicioInsertar.jsp";
        }else{
            Ave aveBean = new Ave();
            try {//** rellenamos el bean Contenido con los parametros del formulario **//
                if(anioValido(request.getParameter("fecha"))){//** comprobamos primero que la fecha sea válida en nuestro método mas abajo creado **//
                   BeanUtils.populate(aveBean, request.getParameterMap());
                    sesion.setAttribute("ave", aveBean);
                    error="";
                    sesion.setAttribute("error", error);  
                }else{//** si no es correcta lanzamos la excepcion Exception para controlar los errores **//
                 throw new Exception();   
                }
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Realizar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Realizar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e){//** si al rellenar el bean da una excepcion, sera la fecha, dado que los demas campos son String **//
                error="Debe introducir una fecha correcta en formato YYYY-MM-DD";
                sesion.setAttribute("error", error);
                url="JSP/INSERTAR/inicioInsertar.jsp";
            }
            if(error.equals("")){//** si no existe error alguno, marcaremos que vamos a ir al siguiente jsp **//
                url="JSP/INSERTAR/insertar.jsp";   
            }
        }
    }
    
    if(request.getParameter("actualizar")!= null){//** Si venimos del formulario leerActualizar **//
        if(request.getParameter("valoranilla") == null){//** comprobaremos si ha marcado o no algun radiobuton añadiendo el error a la sesion y mostrando el mensaje en actualizar.jsp**//
            error="No se ha seleccionado ningún registro para ser modificado";
            sesion.setAttribute("error", error);
            url="JSP/ACTUALIZAR/actualizar.jsp";
        }else{//** si el radiobuton llega marcado obtendremos el objeto de la base de datos y lo añadiremos en sesion**//
            try{
              StringBuilder sql = new StringBuilder();
              sql.append("SELECT * FROM pajaros WHERE anilla='").append(request.getParameter("valoranilla")).append("'");
              con= datasource.getConnection();
              prp=con.prepareStatement(sql.toString());
              resultado=prp.executeQuery();
              while(resultado.next()){
                  ave=new Ave(resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getDate(4));
              }
              sesion.setAttribute("ave", ave);
              url="JSP/ACTUALIZAR/actualizar.jsp";
            }catch(SQLException ex){
                ex.printStackTrace();
                url="JSP/ACTUALIZAR/leerActualizar.jsp";
            }finally{//** cerramos las conexiones abiertas **//
                Conectar.cerrarConexiones(con, resultado, prp);
            }
        }
    }
    
    if(request.getParameter("eliminar")!= null){//** si venimos del formulario leerEliminar **//
        //** recogemos los valores de los checkbox y comprobamos cuantos hay y si no hay ninguno vamos al index **//
        String select[] = request.getParameterValues("valoranilla"); 
        if(select == null){
            url="index.html";
        }else{
            try{
        //** si hemos seleccionado valores en el checkbox hacemos una consulta en la base de datos, con las anillas recogidas y metemos en un ArrayList<Ave> los resultados de la consulta **//
                StringBuilder consulta=new StringBuilder();
                consulta.append("SELECT * FROM pajaros WHERE ");
                con=datasource.getConnection();
                for(int i=0;i<select.length;i++){
                    if(i==select.length-1){
                     consulta.append("anilla='").append(select[i]).append("'");
                    }else{
                     consulta.append("anilla='").append(select[i]).append("' or ");  
                    }
                }
                prp=con.prepareStatement(consulta.toString());
                resultado=prp.executeQuery();
                aves = new ArrayList<Ave>();
                while(resultado.next()){
                   aves.add(new Ave(resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getDate(4)));
                }
                sesion.removeAttribute("aves");//** Operar crea en sesion el atributo aves, asi que podemos deshacernos de el y aprovechar el mismo nombre para añadir los resultados de nuestra consulta **//
                sesion.setAttribute("aves", aves);
                url="JSP/ELIMINAR/eliminar.jsp";
            }catch(SQLException e){
                e.printStackTrace();
                url="JSP/ELIMINAR/leerEliminar.jsp";
            }finally{//**Cerramos las conexiones **//
            Conectar.cerrarConexiones(con, resultado, prp);
            }
        }
    }
        request.getRequestDispatcher(url).forward(request, response);    

    

}

    public boolean anioValido(String anio) {
        boolean valido=false;
        try{
                String datos[]=anio.split("-");
                int year = Integer.parseInt(datos[0]);                   // año
                int month = Integer.parseInt(datos[1]);                     // mes [1,...,12]
                int dayOfMonth = Integer.parseInt(datos[2]);;                // día [1,...,31] 
                LocalDate today = LocalDate.of(year, month, dayOfMonth);
                valido=true;
        }catch(Exception e){
            valido=false;
        }
        return valido;
    }
        
}
