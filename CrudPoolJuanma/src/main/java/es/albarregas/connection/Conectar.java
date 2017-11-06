/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.connection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.sql.DataSource;

/**
 *
 * @author shirone
 */
public class Conectar {
       private static DataSource data;

    public static DataSource getData() {
       try{
           Context initialContext= new InitialContext();
           data= (DataSource)initialContext.lookup("java:comp/env/jdbc/poolcrud");
       }catch(NamingException e){
           System.out.println("Problemas al crear el datasource");
           e.printStackTrace();
       }
       return data;
    }
    
    public static void cerrarConexiones(Connection con,ResultSet rset,PreparedStatement prp){
        if(con != null){
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conectar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(rset != null){
            try {
                rset.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conectar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(prp != null){
            try {
                prp.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conectar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
