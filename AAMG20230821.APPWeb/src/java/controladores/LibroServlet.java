/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import aamg20230821.accesoadatos.LibroDAL;
import aamg20230821.entidadesdenegocio.Libro;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Utilidad;

/**
 *
 * @author Adonis Molina
 */
@WebServlet(name = "LibroServlet", urlPatterns = {"/Libro"})
public class LibroServlet extends HttpServlet {

    private Libro ObtnerLibro(HttpServletRequest request) {
        // Obtener el parámetro accion del request
        String accion = Utilidad.getParameter(request, "accion", "index");
        Libro libro = new Libro();
        if (accion.equals("create") == false) { // Si la accion no es create.
            // Obtener el parámetro id del request  y asignar ese valor a la propiedad Id de Rol.
            libro.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }
        libro.setTitulo(Utilidad.getParameter(request, "titulo", ""));
        libro.setAutor(Utilidad.getParameter(request, "autor", ""));
        libro.setAñoPublicación(Integer.parseInt(Utilidad.getParameter(request, "publicacion", "0")));

        // Devolver la instancia de la entidad Rol con los valores obtenidos del request.
        return libro;
    }
    
    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
           
            List<Libro> obtener = LibroDAL.obtenerTodos(); // Ir a la capa de acceso a datos y buscar los registros de Rol.
            // El request.setAttribute se utiliza para enviar datos desde un servlet a un jsp.
            request.setAttribute("libros", obtener ); // Enviar los roles al jsp utilizando el request.setAttribute con el nombre del atributo roles.
             
            request.getRequestDispatcher("Views/Libro/index.jsp").forward(request, response); // Direccionar al jsp index de Rol.
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response); // Enviar al jsp de error si hay un Exception.
        }
    }   
    
   private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // direccionar al jsp create de Rol
        request.getRequestDispatcher("Views/Libro/index.jsp").forward(request, response);
    }
    
     private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Libro libro = ObtnerLibro(request); // Llenar la instancia de Rol con los parámetros enviados en el request.
            // Enviar los datos de Rol a la capa de accesoa a datos para que lo almacene en la base de datos el registro.
            int result = LibroDAL.crear(libro);
            if (result != 0) { // Si el result es diferente a cero significa que los datos fueron ingresados correctamente.
                // Enviar el atributo accion con el valor index al jsp de index
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response); // ir al metodo doGetRequestIndex para que nos direcciones al jsp index
            } else {
                // Enviar al jsp de error el siguiente mensaje. No se logro registrar un nuevo registro
                Utilidad.enviarError("No se logro registrar un nuevo registro", request, response);
            }
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception
            Utilidad.enviarError(ex.getMessage(), request, response);
        }

    }
     
     
         // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          doGetRequestIndex(request, response); // Ir al método doGetRequestIndex.
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          String accion = Utilidad.getParameter(request, "accion", "index");
            // Hacer un switch para decidir a cual metodo ir segun el valor que venga en el parámetro de accion.
            switch (accion) {
                case "create":                   
                    doPostRequestCreate(request, response); // Ir al metodo doGetRequestCreate.
                    break;               
                default:
                    doGetRequestIndex(request, response); // Ir al metodo doGetRequestIndex.
            }
    }
    // </editor-fold>

    



}
