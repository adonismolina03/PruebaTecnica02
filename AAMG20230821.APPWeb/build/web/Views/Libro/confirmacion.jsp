<%-- 
    Document   : confirmacion
    Created on : 17 ago 2023, 11:34:57
    Author     : Mateo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirmacion</title>
    </head>
    <body>
        <h1><%=request.getAttribute("confirmacion")%></h1>
         <a href="Task" class="waves-effect waves-light btn blue">Aceptar</a>    
    </body>
</html>
