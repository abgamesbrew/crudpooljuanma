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

/**
 *
 * @author shirone
 */
@WebServlet(name = "Concluir", urlPatterns = {"/concluir"})
public class Concluir extends HttpServlet {
    DataSource datasource;
    
    public void init(ServletConfig config) throws ServletException{//** rellenamos el data source con la clase Conectar**//
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
        ResultSet resultado = null;
        PreparedStatement preparada = null;
        String sentencia = null;
        String url="";
        String error="";
        //** Como en operar ya hemos abierto una sesión aqui queremos solo recogerla asi que usamos el false en el parametros de getSession **//
        HttpSession sesion = request.getSession(false);
        
        if(request.getParameter("cancelarinsertar") != null){
                    url="JSP/INSERTAR/inicioInsertar.jsp";
                    ave=(Ave)sesion.getAttribute("ave");
        }
        if(request.getParameter("cancelar") != null){//** si en alguno de los formularios han presionado sobre cancelar y vienen a este controlador vamos al index **//
             url="index.html";
        }
        if(request.getParameter("inserccion") != null){//** si llegamos del formulario insertar **//
            try{
                ave=(Ave)sesion.getAttribute("ave");//** cogemos el atributo ave que contendrá los datos que hemos rellenado en inicioInsertar **//
                if(ave.getAnilla().length() > 3){//** si la anilla tiene mas de 3 caracteres, no podra entrar a nuestra base de datos asi que volveremos atras con un error **//
                  error ="La anilla debe tener una longitud máxima de 3 caracteres";
                  sesion.setAttribute("error", error);  
                  url="JSP/INSERTAR/inicioInsertar.jsp"; 
                }else{//** si la anilla tiene 3 caracteres se hara el update a la base de datos e iremos al finInsertar **//
                    con = datasource.getConnection();
                    StringBuilder sql=new StringBuilder();
                    sql.append("INSERT INTO pajaros(anilla, especie, lugar, fecha) VALUES ('").append(ave.getAnilla()).append("','").append(ave.getEspecie()).append("','").append(ave.getLugar()).append("','").append(ave.getFecha()).append("')");
                    preparada = con.prepareStatement(sql.toString());
                    preparada.executeUpdate();
                    url="JSP/INSERTAR/finInsertar.jsp";  
                }
            }catch (SQLException ex) {//** si se da el error 1062 (primary key duplicada) volveremos al formulario de nuevo indicando el error **//
                if(ex.getErrorCode() == 1062){
                     error ="Esa anilla ya existe en nuestra base de datos";
                     sesion.setAttribute("error", error);
                     url="JSP/INSERTAR/inicioInsertar.jsp";  
                }
                ex.printStackTrace();
            }finally{//** cerramos las conexiones **//
                Conectar.cerrarConexiones(con, null, preparada);
            }
        }
        
        else if(request.getParameter("actualizar")!= null){//** si llegamos del formulario actualizar **//
            error=(String)sesion.getAttribute("error");//** comprobamos si hay algun error para volver al index o no **//
            ave=(Ave)sesion.getAttribute("ave");//** cargamos el objeto ave de la seasion **//
            
            if(error != null){
                url="index.html";
            }else{//** comprobaremos que si ninguno de los campos se ha modificado muestre un mensaje de error y no se haga nada en nuestra base de datos **//
                if(request.getParameter("anilla").equals(ave.getAnilla()) && request.getParameter("especie").equals(ave.getEspecie()) && 
                        request.getParameter("lugar").equals(ave.getLugar()) && request.getParameter("fecha").equals(ave.getFecha().toString())){
                    
                 error="No se ha realizado ninguna modificación en los valores del formulario de actualizacion";
                 sesion.setAttribute("error", error);
                 url="JSP/ACTUALIZAR/finActualizar.jsp";
                 
                }else{//** si se ha realizado alguna modificacion realizamos el update en la base de datos y vamos a fin Actualizar para decir la anilla del ave modificada **//
                    try{
                      con = datasource.getConnection();
                      StringBuilder sql = new StringBuilder();
                      sql.append("UPDATE pajaros SET anilla='").append(request.getParameter("anilla")).append("',especie='").append(request.getParameter("especie")).append("',lugar='").append(request.getParameter("lugar")).append("',fecha='").append(request.getParameter("fecha")).append("' WHERE anilla='").append(ave.getAnilla()).append("'");
                      preparada = con.prepareStatement(sql.toString());
                      preparada.executeUpdate();
                      url="JSP/ACTUALIZAR/finActualizar.jsp";
                    }catch(SQLException e){//** si hay algún error (probablemente fecha) volvemos al formulario actualizar (en clase se dijo que aqui no ibamos a alargarlo mas) **//
                      e.printStackTrace();
                      url="JSP/ACTUALIZAR/actualizar.jsp";
                    }finally{//** cerramos las conexiones **//
                        Conectar.cerrarConexiones(con, resultado, preparada);
                    }   
                }
                
            }
        }
         if(request.getParameter("eliminar") != null){//** si venimos del formulario eliminar **//
            aves=(ArrayList<Ave>)sesion.getAttribute("aves");//** cargamos el arraylist con las aves marcadas para ser eliminadas **//
            
                    try{
                      con = datasource.getConnection();
                      //** utilizamos el stringBuilder para realizar las concatenaciones necesarias **//
                      StringBuilder consulta= new StringBuilder();
                      consulta.append("DELETE FROM pajaros WHERE ");
                      //** creamos un delete para todas las anillas que nos han llegado en aves del ArrayList<Ave>**//
                      for(int i=0;i<aves.size();i++){
                       if(i==aves.size()-1){
                           consulta.append("anilla='").append(aves.get(i).getAnilla()).append("'");
                       }else{
                           consulta.append("anilla='").append(aves.get(i).getAnilla()).append("' or "); 
                       }
                      }
                      //** preparedstatement necesita un string para funcionar no un StringBuilder asi que hay que sacar su .toString() **//
                      preparada = con.prepareStatement(consulta.toString());
                      int filas =preparada.executeUpdate();//guardamos el numero de registros borrados y lo añadiremos a sesion **//
                      url="JSP/ELIMINAR/finEliminar.jsp";
                      sesion.setAttribute("filas", filas);
                    }catch(SQLException e){//si hay algun error volveremos al formulario de eliminar (aunque es imposible dado que nosotros solo vamos marcando las opciones)
                      e.printStackTrace();
                      url="JSP/ELIMINAR/eliminar.jsp";
                    }finally{//** cerramos las conexiones **//
                        Conectar.cerrarConexiones(con, resultado, preparada);
                    }
        }
         
        request.getRequestDispatcher(url).forward(request, response);

}

}
