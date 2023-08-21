package aamg20230821.accesoadatos;

import aamg20230821.entidadesdenegocio.Libro;
import java.util.*; 
import java.sql.*;

/**
 *
 * @author Adonis Molina
 */
public class LibroDAL {
    
    static String obtenerCampos() {
        return "r.Id,r.titulo,r.autor,r.año_publicación";
    }
    
    private static String obtenerSelect(Libro plibro) {
        String sql;
        sql = "SELECT ";
        if (plibro.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
            // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y "getTop_aux" es mayor a cero
            sql += "TOP " + plibro.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Libros r"); // Agregar los campos de la tabla de Rol mas el FROM a la tabla Rol con su alias "r"
        return sql;
    }
    
    private static String agregarOrderBy(Libro pLibro) {
        String sql = " ORDER BY r.Id DESC";
        if (pLibro.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            // Agregar el LIMIT a la consulta SELECT de la tabla de Rol en el caso que getTop_aux() sea mayor a cero y el gestor de base de datos
            // sea MYSQL
            sql += " LIMIT " + pLibro.getTop_aux() + " ";
        }
        return sql;
    }
    
     public static int crear(Libro pLibro) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "INSERT INTO Libros(titulo,autor,año_publicación) VALUES(?,?,?)"; // Definir la consulta INSERT a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pLibro.getTitulo()); // Agregar el parametro a la consulta donde estan el simbolo ? #1
                ps.setString(2, pLibro.getAutor());
                ps.setInt(3, pLibro.getAñoPublicación());
                result = ps.executeUpdate(); // Ejecutar la consulta INSERT en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el INSERT en la base de datos 
    }
     
     public static int modificar(Libro pLibro) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "UPDATE Libros SET titulo=? SET autor=? SET año_publicación=? WHERE Id=?"; // Definir la consulta UPDATE a la tabla de Rol utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                ps.setString(1, pLibro.getTitulo()); // Agregar el parametro a la consulta donde estan el simbolo ? #1  
                ps.setString(2, pLibro.getAutor());
                ps.setInt(3, pLibro.getAñoPublicación());
                ps.setInt(4, pLibro.getId()); // Agregar el parametro a la consulta donde estan el simbolo ? #2 
                result = ps.executeUpdate(); // Ejecutar la consulta UPDATE en la base de datos
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;  // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el UPDATE en la base de datos 
    }
     
     static int asignarDatosResultSet(Libro plibro, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT r.Id(indice 1),r.Nombre(indice 2) * FROM Rol
        pIndex++;
        plibro.setId(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        plibro.setTitulo(pResultSet.getString(pIndex)); // index 2
        return pIndex;
    }
     
     private static void obtenerDatos(PreparedStatement pPS, ArrayList<Libro> pRoles) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Rol
                Libro rol = new Libro(); 
                asignarDatosResultSet(rol, resultSet, 0); // Llenar las propiedaddes de la Entidad Rol con los datos obtenidos de la fila en el ResultSet
                pRoles.add(rol); // Agregar la entidad Rol al ArrayList de Rol
            }
            resultSet.close(); // Cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }
     
         // Metodo para obtener todos los registro de la tabla de Rol
    public static ArrayList<Libro> obtenerTodos() throws Exception {
        ArrayList<Libro> roles;
        roles = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {// Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(new Libro());  // Obtener la consulta SELECT de la tabla Rol
            sql += agregarOrderBy(new Libro());  // Concatenar a la consulta SELECT de la tabla Rol el ORDER BY por Id 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                obtenerDatos(ps, roles); // Llenar el ArrayList de Rol con las fila que devolvera la consulta SELECT a la tabla de Rol
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return roles; // Devolver el ArrayList de Rol
    }
     
     
     
     

    
    
}
