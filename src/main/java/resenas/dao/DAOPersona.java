package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.google.gson.JsonObject;

import resenas.conexion.SQLConnection;
import resenas.modelo.Persona;

public class DAOPersona {
    private SQLConnection sqlConnection = new SQLConnection();

    public boolean agregarPersona(Persona Persona) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "INSERT INTO persona (idPersona, idDireccion, nombre, telefono, correo, rfc, idRol) VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, Persona.getId());
            ps.setString(2, Persona.getId_direccion());
            ps.setString(3, Persona.getNombre());
            ps.setString(4, Persona.getTelefono());
            ps.setString(5, Persona.getCorreo());
            ps.setString(6, Persona.getRfc());
            ps.setString(7, Persona.getIdRol());

            System.out.println(ps);
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
                    ps.close();
                    sqlConnection.closeConnection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    

}
