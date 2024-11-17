package resenas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import resenas.conexion.SQLConnection;
import resenas.modelo.Direccion;

public class DAODireccion {
    private SQLConnection sqlConnection = new SQLConnection();

    public boolean agregarDireccion(Direccion direccion) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("INSERT INTO DIRECCION VALUES (?,?,?,?,?)");
            ps.setString(1, direccion.getId());
            ps.setString(2, direccion.getCiudad());
            ps.setString(3, direccion.getColonia());
            ps.setString(4, direccion.getCalle());
            ps.setString(5, direccion.getNumero());
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

    public boolean editarDireccion(Direccion direccion) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement(
                    "UPDATE DIRECCION SET ciudad = ?, colonia = ?, calle = ?, numero = ? WHERE idDireccion = ?");
            ps.setString(1, direccion.getCiudad());
            ps.setString(2, direccion.getColonia());
            ps.setString(3, direccion.getCalle());
            ps.setString(4, direccion.getNumero());
            ps.setString(5, direccion.getId());
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

    public boolean eliminarDireccion(String id) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("DELETE FROM DIRECCION WHERE idDireccion = ?");
            ps.setString(1, id);
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

    public Direccion obtenerDireccionById(String id_direccion) {
        sqlConnection = new SQLConnection();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = sqlConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM DIRECCION WHERE idDireccion = ?");
            ps.setString(1, id_direccion);
            rs = ps.executeQuery();
            if (rs.next()) {
                Direccion direccion = new Direccion();
                direccion.setId(rs.getString("idDireccion"));
                direccion.setCiudad(rs.getString("ciudad"));
                direccion.setColonia(rs.getString("colonia"));
                direccion.setCalle(rs.getString("calle"));
                direccion.setNumero(rs.getString("numero"));
                return direccion;
            } else {
                return null;
            }
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

}
