package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import resenas.conexion.SQLConnection;
import resenas.modelo.Proyecto;

public class DAOProyecto {
    private SQLConnection sqlConnection;

    public boolean agregarProyecto(Proyecto proyecto) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "INSERT INTO PROYECTO (idProyecto, idPersona, fecha, idDireccion, descripcion, estado) VALUES(?, ?, ?, ?, ?, ?)");
            ps.setString(1, proyecto.getIdProyecto());
            ps.setString(2, proyecto.getIdPersona());
            ps.setDate(3, proyecto.getFecha());
            ps.setString(4, proyecto.getIdDireccion());
            ps.setString(5, proyecto.getDescripcion());
            ps.setString(6, "Disponible");
            int res = ps.executeUpdate();
            if (res > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            try {
                con.close();
                if (con.isClosed()) {
                    sqlConnection.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<JsonObject> obtenerProyectos() {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<JsonObject> proyectos = new ArrayList<JsonObject>();
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT\n" + //
                    "    py.idProyecto,\n" + //
                    "    p.nombre,\n" + //
                    "    p.correo,\n" + //
                    "    p.rfc,\n" + //
                    "    p.telefono,\n" + //
                    "    d1.calle AS calle_persona,\n" + //
                    "    d1.ciudad AS ciudad_persona,\n" + //
                    "    d1.colonia AS colonia_persona,\n" + //
                    "    d1.numero AS numero_persona,\n" + //
                    "    d2.calle AS calle_proyecto,\n" + //
                    "    d2.ciudad AS ciudad_proyecto,\n" + //
                    "    d2.colonia AS colonia_proyecto,\n" + //
                    "    d2.numero AS numero_proyecto\n" + //
                    "FROM \n" + //
                    "    PERSONA p\n" + //
                    "JOIN \n" + //
                    "    DIRECCION d1 ON p.idDireccion = d1.idDireccion\n" + //
                    "JOIN \n" + //
                    "    PROYECTO py ON p.idPersona = py.idPersona\n" + //
                    "JOIN \n" + //
                    "    DIRECCION d2 ON py.idDireccion = d2.idDireccion WHERE py.estado = 'Disponible';\n" + //
                    "");

            rs = ps.executeQuery();
            while (rs.next()) {
                JsonObject proyecto = new JsonObject();
                proyecto.addProperty("idProyecto", rs.getString("idProyecto"));
                proyecto.addProperty("nombre", rs.getString("nombre"));
                proyecto.addProperty("correo", rs.getString("correo"));
                proyecto.addProperty("rfc", rs.getString("rfc"));
                proyecto.addProperty("telefono", rs.getString("telefono"));
                proyecto.addProperty("calle_persona", rs.getString("calle_persona"));
                proyecto.addProperty("ciudad_persona", rs.getString("ciudad_persona"));
                proyecto.addProperty("colonia_persona", rs.getString("colonia_persona"));
                proyecto.addProperty("numero_persona", rs.getString("numero_persona"));
                proyecto.addProperty("calle_proyecto", rs.getString("calle_proyecto"));
                proyecto.addProperty("ciudad_proyecto", rs.getString("ciudad_proyecto"));
                proyecto.addProperty("colonia_proyecto", rs.getString("colonia_proyecto"));
                proyecto.addProperty("numero_proyecto", rs.getString("numero_proyecto"));
                proyectos.add(proyecto);

            }
            return proyectos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            try {
                con.close();
                if (con.isClosed()) {
                    sqlConnection.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public boolean eliminarProyecto(String id) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        System.out.println("id: " + id);
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "UPDATE PROYECTO SET estado = ? WHERE idProyecto = ?");
            ps.setString(1, "Inactivo");
            ps.setString(2, id);
            int res = ps.executeUpdate();
            if (res > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.close();
                if (con.isClosed()) {
                    sqlConnection.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JsonObject obtenById(String idProyecto){
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        JsonObject proyecto = new JsonObject();
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT\n" + //
                    "    py.idProyecto,\n" + //
                    "    p.nombre,\n" + //
                    "    p.correo,\n" + //
                    "    p.rfc,\n" + //
                    "    p.telefono,\n" + //
                    "    d1.calle AS calle_persona,\n" + //
                    "    d1.ciudad AS ciudad_persona,\n" + //
                    "    d1.colonia AS colonia_persona,\n" + //
                    "    d1.numero AS numero_persona,\n" + //
                    "    d2.calle AS calle_proyecto,\n" + //
                    "    d2.ciudad AS ciudad_proyecto,\n" + //
                    "    d2.colonia AS colonia_proyecto,\n" + //
                    "    d2.numero AS numero_proyecto\n" + //
                    "FROM \n" + //
                    "    PERSONA p\n" + //
                    "JOIN \n" + //
                    "    DIRECCION d1 ON p.idDireccion = d1.idDireccion\n" + //
                    "JOIN \n" + //
                    "    PROYECTO py ON p.idPersona = py.idPersona\n" + //
                    "JOIN \n" + //
                    "    DIRECCION d2 ON py.idDireccion = d2.idDireccion WHERE py.idProyecto = ?;\n" + //
                    "");
            ps.setString(1, idProyecto);
            rs  =ps.executeQuery();
            while (rs.next()) {
                proyecto.addProperty("idProyecto", rs.getString("idProyecto"));
                proyecto.addProperty("nombre", rs.getString("nombre"));
                proyecto.addProperty("correo", rs.getString("correo"));
                proyecto.addProperty("rfc", rs.getString("rfc"));
                proyecto.addProperty("telefono", rs.getString("telefono"));
                proyecto.addProperty("calle_persona", rs.getString("calle_persona"));
                proyecto.addProperty("ciudad_persona", rs.getString("ciudad_persona"));
                proyecto.addProperty("colonia_persona", rs.getString("colonia_persona"));
                proyecto.addProperty("numero_persona", rs.getString("numero_persona"));
                proyecto.addProperty("calle_proyecto", rs.getString("calle_proyecto"));
                proyecto.addProperty("ciudad_proyecto", rs.getString("ciudad_proyecto"));
                proyecto.addProperty("colonia_proyecto", rs.getString("colonia_proyecto"));
                proyecto.addProperty("numero_proyecto", rs.getString("numero_proyecto"));
            }
            return proyecto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            try {
                con.close();
                if (con.isClosed()) {
                    sqlConnection.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
