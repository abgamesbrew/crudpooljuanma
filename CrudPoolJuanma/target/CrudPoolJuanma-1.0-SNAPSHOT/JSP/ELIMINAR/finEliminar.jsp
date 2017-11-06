<%-- 
    Document   : finEliminar
    Created on : 04-nov-2017, 19:51:06
    Author     : shirone
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/todos.css"/>
        <title>Fin Eliminar</title>
    </head>
    <body>
        <%@ include file="../../INCLUDES/cabecera.inc" %>
        <% HttpSession sesion = request.getSession(false);
           int filas = (int)sesion.getAttribute("filas");
        %>
        <h2>Se han eliminado un total de <%= filas %> registros de nuestra base de datos</h2>
        <input type="button" onclick=" location.href='<%= request.getContextPath() %>/index.html' " value="Volver" name="boton" /> 
    </div>
    </body>
</html>
