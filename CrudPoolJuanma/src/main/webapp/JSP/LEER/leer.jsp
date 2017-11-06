<%-- 
    Document   : leer
    Created on : 04-nov-2017, 13:51:48
    Author     : shirone
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="es.albarregas.beans.Ave"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/todos.css"/>
        <title>Lectura Base de datos</title>
    </head>
    <body>
            <%@ include file="../../INCLUDES/cabecera.inc" %>
            <% HttpSession sesion = request.getSession(false);
            ArrayList<Ave> aves = (ArrayList<Ave>)sesion.getAttribute("aves");

            if(aves.size()>=1){//** si hay registros para leer en nuestra base de datos **//
            %>


            <h2>Estos son todos los registros de nuestra base de datos</h2>
            <table>
                <tr>
                    <td>Anilla</td>
                    <td>Especie</td>
                    <td>Lugar</td>
                    <td>Fecha</td>
                </tr>
                <%
                for(int i=0;i<=aves.size()-1;i++){//** mostramos cada fila de la base de datos guardada dentro de nuestro ArrayList<Ave> **//
                %>    
                <tr>
                    <td><%= aves.get(i).getAnilla()%></td>
                    <td><%= aves.get(i).getEspecie()%></td>
                    <td><%= aves.get(i).getLugar()%></td>
                    <td><%= aves.get(i).getFecha()%></td>

                <%}

                }else{//** si no hay registros en nuestro arralist **//
                %>
                <h2>No hay registros que se puedan leer en nuestra base de datos</h2>
               <%
                }
                %>
            </table>
            <input type="button" onclick=" location.href='<%= request.getContextPath() %>/index.html' " value="Volver" name="boton" /> 
        </div>
    </body>
</html>
