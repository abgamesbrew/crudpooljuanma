<%-- 
    Document   : insertar
    Created on : 04-nov-2017, 11:01:48
    Author     : shirone
--%>

<%@page import="es.albarregas.beans.Ave"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/todos.css"/>
        <title>Insertar</title>
    </head>
    <body>
       <%@ include file="../../INCLUDES/cabecera.inc" %>
        <% HttpSession sesion = request.getSession(false);
           Ave ave = (Ave)sesion.getAttribute("ave");//** obtenemos el ave para mostrar sus datos y que el cliente confirme si quiere continuar o no **//
                   
        %>
        <h1>Tus datos elegidos son:</h1>
        <table border="1">
                <tr>
                    <td><strong>Anilla: </strong></td>
                    <td><%= ave.getAnilla() %></td>
                </tr>
                <tr>
                    <td><strong>Especie: </strong></td>
                    <td><%= ave.getEspecie()%></td>
                </tr>
                <tr>
                    <td><strong>Lugar: </strong></td>
                    <td><%= ave.getLugar()%></td>
                </tr>
                <tr>
                    <td><strong>Fecha: </strong></td>
                    <td><%= ave.getFecha()%></td>
                </tr>
        </table>
                <br>
                <form method="post" action="<%=request.getContextPath()%>/concluir">
                    <input type="submit" name="inserccion" value="Insertar"> <input type="submit" name="cancelarinsertar" value="Cancelar">
                </form>
        </div>

    </body>
</html>
