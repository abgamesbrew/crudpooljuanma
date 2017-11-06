<%-- 
    Document   : inicioInsertar
    Created on : 03-nov-2017, 17:43:00
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
        <!-- aqui no podemos usar el include debido a unas modificaciones del css -->
        <img src="<%= request.getContextPath() %>/IMAGES/crudlogo.png" class="logo">
        <img src="<%= request.getContextPath() %>/IMAGES/mysql.png" class="mysql">
        <div id="centradoformu">
        <h2>Inserción de datos</h2>
        <% 
            HttpSession sesion = request.getSession(false);
            Ave ave = null;
            
            if(sesion.getAttribute("error") != null){//** si hemos llegado aqui con algún error que se muestre **//
            String error=(String)sesion.getAttribute("error");
            ave=(Ave)sesion.getAttribute("ave");//** si hemos venido de algun error cogeremos el ave de la sesion que tendrá sus datos **//

           
            %>
            <p><%= error %></p>
        <%
            }
        %>
        
        <form method="post" action="<%=request.getContextPath()%>/realizar">
            <table>
                <tr> 
                    <!-- mostraremos el input dependiendo de si viene por request, por sesion o si todavia no lo hemos tocado (no hacia falta hacerlo pero me gustaba la idea de que si cancelamos mas adelante pudieramos rectificar el error)-->
                    <td><label>Anilla: </label></td>
                    <%if(request.getParameter("anilla") != null){
                    %>
                    <td><input type="text" name="anilla" placeholder="123" value="<%= (request.getParameter("anilla") != null )?request.getParameter("anilla"):"" %>"></td>
                   <%
                    }else if(ave != null){
                    %>
                    <td><input type="text" name="anilla" placeholder="123" value="<%= (ave.getAnilla() != null )?ave.getAnilla():"" %>"></td>
                    <%                   
                    }else{
                    %>
                    <td><input type="text" name="anilla" placeholder="123" value=""></td>
                   <%
                    }
                    %>
                </tr>
                <tr>
                    <td><label>Especie: </label></td>
                    <%if(request.getParameter("anilla") != null){
                    %>
                    <td><input type="text" name="especie" placeholder="Agaporni" value="<%= (request.getParameter("especie") != null )?request.getParameter("especie"):"" %>"></td>
                   <%
                    }else if(ave != null){
                    %>
                    <td><input type="text" name="especie" placeholder="Agaporni" value="<%= (ave.getEspecie() != null )?ave.getEspecie():"" %>"></td>
                    <%                   
                    }else{
                    %>
                    <td><input type="text" name="especie" placeholder="Agaporni" value=""></td>
                   <%
                    }
                    %>
                </tr>
                <tr>
                    <td><label>Lugar: </label></td>
                    <%if(request.getParameter("lugar") != null){
                    %>
                    <td><input type="text" name="lugar" placeholder="en la selva" value="<%= (request.getParameter("lugar") != null )?request.getParameter("lugar"):"" %>"></td>
                   <%
                    }else if(ave != null){
                    %>
                    <td><input type="text" name="lugar" placeholder="en la selva" value="<%= (ave.getLugar()!= null )?ave.getLugar():"" %>"></td>
                    <%                   
                    }else{
                    %>
                    <td><input type="text" name="lugar" placeholder="en la selva" value=""></td>
                   <%
                    }
                    %>
                </tr>
                <tr>
                    <td><label>Fecha </label></td>
                    <%if(request.getParameter("fecha") != null){
                    %>
                    <td><input type="text" name="fecha" placeholder="2010-03-03" value="<%= (request.getParameter("fecha") != null )?request.getParameter("fecha"):"" %>"></td>
                   <%
                    }else if(ave != null){
                    %>
                    <td><input type="text" name="fecha" placeholder="2010-03-03" value="<%= (ave.getFecha()!= null )?ave.getFecha():"" %>"></td>
                    <%                   
                    }else{
                    %>
                    <td><input type="text" name="fecha" placeholder="2010-03-03" value=""></td>
                   <%
                    }
                    %>
                </tr>
                <tr>
                    <td> <input type="submit" name="insertar" value="Insertar"></td>
                    <td><input type="submit" value="Cancelar" name="cancelarinserccion"></td>
                </tr>
            </table>
             
        </form>
        </div>
    </body>
</html>
