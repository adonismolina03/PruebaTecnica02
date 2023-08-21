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
    
    
    
}
