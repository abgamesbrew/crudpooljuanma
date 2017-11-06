<%-- 
    Document   : finActualizar
    Created on : 04-nov-2017, 16:59:47
    Author     : shirone
--%>

<%@page import="es.albarregas.beans.Ave"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/todos.css"/>
        <title>FinActualizar</title>
    </head>
    <body>
        <%@ include file="../../INCLUDES/cabecera.inc" %>
        <% HttpSession sesion =  request.getSession(false);
           Ave ave= (Ave)sesion.getAttribute("ave");
           if(sesion.getAttribute("error") != null){//** si hemos llegado aqui con algun error lo mostramos y volvemos al index **//
          %>
          <h2><%= (String)sesion.getAttribute("error") %></h2>
          
           <%}else{//** si no, mostramos la entrada que ha sido eliminada en la base de datos (se dijo en clase que solo la anilla) **//
           %>
           <h2>Se han cambiado los valores de la anilla: <p>"<%=ave.getAnilla()%>"<p></h2>
           <%
            }
           %>
           <input type="button" onclick=" location.href='<%= request.getContextPath() %>/index.html' " value="Volver" name="boton" /> 
           </div>
    </body>
</html>
