<%-- 
    Document   : finInsertar
    Created on : 04-nov-2017, 13:30:53
    Author     : shirone
--%>

<%@page import="es.albarregas.beans.Ave"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/todos.css"/>
        <title>Fin insertar</title>
    </head>
    <body>
    <%@ include file="../../INCLUDES/cabecera.inc" %>
        <% HttpSession sesion = request.getSession(false);
           Ave ave = (Ave)sesion.getAttribute("ave");
           //** despues de realizar las operaciones correctamente, mostramos los datos del ave ya insertado **//
        %>
        <h2>En nuestra base de datos se ha hecho la siguiente insercción de este animal</h2>
       <table border="1">
               <tr>
                   <td><strong>Anilla:</strong></td>
                   <td><%= ave.getAnilla() %></td>
               </tr>
               <tr>
                   <td><strong>Especie:</strong></td>
                   <td><%= ave.getEspecie()%></td>
               </tr>
               <tr>
                   <td><strong>Lugar:</strong></td>
                   <td><%= ave.getLugar()%></td>
               </tr>
               <tr>
                   <td><strong>Fecha:</strong></td>
                   <td><%= ave.getFecha()%></td>
               </tr>
       </table>

        <input type="button" onclick=" location.href='<%= request.getContextPath() %>/index.html' " value="Volver" name="boton" /> 
    </div>
    </body>
</html>
