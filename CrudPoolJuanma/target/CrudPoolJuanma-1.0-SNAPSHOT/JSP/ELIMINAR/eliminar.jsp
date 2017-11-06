<%-- 
    Document   : eliminar
    Created on : 04-nov-2017, 19:11:13
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
        <title>Eliminacion</title>
    </head>
    <body>
        <%@ include file="../../INCLUDES/cabecera.inc" %>
         <% HttpSession sesion = request.getSession(false);
        ArrayList<Ave> aves = (ArrayList<Ave>)sesion.getAttribute("aves");
        %>
        <h2>¿Está seguro que desea eliminar esta/s entradas de nuestra base de datos?</h2>
            <form method="post" action="<%=request.getContextPath()%>/concluir">
                <table>
                    <tr>
                        <td>Anilla</td>
                        <td>Especie</td>
                        <td>Lugar</td>
                        <td>Fecha</td>
                    </tr>
                    <%
                    for(int i=0;i<=aves.size()-1;i++){    
                    %>    
                    <tr>
                        <td><%= aves.get(i).getAnilla()%></td>
                        <td><%= aves.get(i).getEspecie()%></td>
                        <td><%= aves.get(i).getLugar()%></td>
                        <td><%= aves.get(i).getFecha()%></td>
                    <%}
                    %>
                </table><br>
                <input type="submit" name="eliminar" value="Eliminar"> <input type="submit" name="cancelar" value="Cancelar">
            </form> 
    </div>
    </body>
</html>
