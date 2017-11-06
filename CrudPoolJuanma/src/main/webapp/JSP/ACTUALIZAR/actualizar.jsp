<%-- 
    Document   : actualizar
    Created on : 04-nov-2017, 14:40:36
    Author     : shirone
--%>

<%@page import="es.albarregas.beans.Ave"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/todos.css"/>
        <title>Actualizacion</title>
    </head>
    <body>
        <!-- aqui no podemos usar el include debido a unas modificaciones del css -->
        <img src="<%= request.getContextPath() %>/IMAGES/crudlogo.png" class="logo">
        <img src="<%= request.getContextPath() %>/IMAGES/mysql.png" class="mysql">
        <div id="centradoformu">
        <form method="post" action="<%=request.getContextPath()%>/concluir">
        <% HttpSession sesion = request.getSession(false);
            if(sesion.getAttribute("error") != null){
        %>        
        <h2><%= (String)sesion.getAttribute("error")%> </h2>       
        <%   }else{
            Ave ave=(Ave)sesion.getAttribute("ave");
        %>
        <h2>Datos para el registro con la anilla <p><%= ave.getAnilla() %><p></h2>
        <table>
            <tr>
                <td>Anilla: </td>
                <td><%=ave.getAnilla()%><input type="hidden" value="<%=ave.getAnilla()%>" name="anilla"></td>
            </tr>
            <tr>
                <td>Especie: </td>
                <td><input type="text" name="especie" value="<%=ave.getEspecie()%>"></td>
            </tr>
            <tr>
                <td>Lugar: </td>
                <td><input type="text" name="lugar" value="<%=ave.getLugar()%>"></td>
            </tr>
            <tr>
                <td>Fecha: </td>
                <td><input type="text" name="fecha" value="<%=ave.getFecha()%>"></td>
            </tr>
        </table>
        <%
        }
        %>
        <input type="submit" value="Aceptar" name="actualizar"> <input type="button" onclick=" location.href='<%= request.getContextPath() %>/index.html' " value="Cancelar" name="boton" /> 
        </form>
    </div>
    </body>
</html>
