<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="aamg20230821.entidadesdenegocio.Libro"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<% List<Libro> libro = (List<Libro>) request.getAttribute("libros");
    int numPage = 1;
    int numReg = 5;
    int countReg = 0;
    if (libro == null) {
        libro =  new ArrayList<>();
    } else if (libro.size() > numReg) {
        double divNumPage = (double) libro.size() / (double) numReg;
        numPage = (int) Math.ceil(divNumPage);
    }   
%>

<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />    
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">  
            <div class="row">
                <div class="col l12 s12">
                    <h5>Ingresar datos de libro</h5>
                    <form action="Libro" method="post">                                   
                        <div class="row">
                            <input type="hidden" name="accion" value="create">
                            <div class="input-field col l3 s12">
                                <input  id="txtTitulo" type="text" name="titulo" required class="validate" maxlength="50">
                                <label for="txtTitulo">Titulo</label>
                            </div> 
                             <div class="input-field col l3 s12">
                                <input  id="txtAutor" type="text" name="autor" required class="validate" maxlength="50">
                                <label for="txtAutor">Autor</label>
                            </div> 
                             <div class="input-field col l3 s12">
                                 <input  id="txtAño" type="number" name="publicacion" required class="validate">
                                <label for="txtAño">Año de Publicacion</label>
                            </div> 
                             
                        </div>
                        <div class="row">
                            <div class="col l12 s12">
                                <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>                       
                            </div>
                        </div>
                    </form>     
                </div>
            </div>
            <div class="row">
                <div class="col l12 s12">
                    <div style="overflow: auto">
                        <table class="paginationjs">
                            <thead>
                                <tr>
                                        <td style="text-align: center" colspan="5">Libros</td>                                   
                                </tr>
                                <tr>
                                    <th>Titulo</th> 
                                    <th>Autor</th> 
                                    <th>Año de publicacion</th> 
                                </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (Libro libroitem : libro) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                %>
                                <tr data-page="<%= tempNumPage%>">
                                    <td><%=libroitem.getTitulo()%></td>  
                                    <td><%=libroitem.getAutor()%></td>  
                                    <td><%=libroitem.getAñoPublicación()%></td>  
                                    <td>
                                    </td>                                   
                                </tr>
                                <%}%>                                                       
                            </tbody>
                        </table>
                    </div>                  
                </div>
            </div>
            <div class="row">
                <div class="col l12 s12">
                    <jsp:include page="/Views/Shared/paginacion.jsp">
                        <jsp:param name="numPage" value="<%= numPage%>" />                        
                    </jsp:include>
                </div>
            </div>
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />        
    </body>
</html>
