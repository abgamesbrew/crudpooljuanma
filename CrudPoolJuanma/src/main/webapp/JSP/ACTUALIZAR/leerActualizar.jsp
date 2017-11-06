<%-- 
    Document   : leerActualizar
    Created on : 04-nov-2017, 13:49:57
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
        <title>Actualizar Registros</title>
    </head>
    <body>
        <!-- aqui no podemos usar el include debido a unas modificaciones del css -->
        <img src="<%= request.getContextPath() %>/IMAGES/crudlogo.png" class="logo">
        <img src="<%= request.getContextPath() %>/IMAGES/mysql.png" class="mysql">
        <div id="centradoformu">
         <% HttpSession sesion = request.getSession(false);
        ArrayList<Ave> aves = (ArrayList<Ave>)sesion.getAttribute("aves");
        
        if(aves.size()>=1){//** si hay uno o mas de un registro sacado de la consulta a la base de datos **//
        %>
        <h2>Seleccione que entrada desea modificar</h2>
            <form method="post" action="<%=request.getContextPath()%>/realizar">
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
                        <td><input type="radio" name="valoranilla" value="<%= aves.get(i).getAnilla()%>"></td>
                    <%}//** fin for **//
                %>
                </table><br>
                <input type="submit" name="actualizar" value="Actualizar"> <input type="submit" name="cancelar" value="Cancelar">
               <%}else{//** si no hay registros en la base de datos **//
                %>
                <h2>No hay registros que se puedan actualizar en nuestra base de datos</h2>
                <input type="button" onclick=" location.href='<%= request.getContextPath() %>/index.html' " value="Volver" name="boton">
                <%
                }
                %>
            </form> 
        </div>
    </body>
</html>
