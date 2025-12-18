package dao;

import modelo.Descuento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DescuentoDAO {

    public void insertar(Descuento d) {
        String sql = "INSERT INTO descuentos (tipo, porcentaje) VALUES (?,?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getTipo());
            ps.setDouble(2, d.getPorcentaje());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Descuento> listar() {
        List<Descuento> lista = new ArrayList<>();
        String sql = "SELECT * FROM descuentos";
        try (Connection con = Conexion.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Descuento d = new Descuento(
                    rs.getInt("id"),
                    rs.getString("tipo"),
                    rs.getDouble("porcentaje")
                );
                lista.add(d);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM descuentos WHERE id=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizar(Descuento d) {
        String sql = "UPDATE descuentos SET tipo=?, porcentaje=? WHERE id=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getTipo());
            ps.setDouble(2, d.getPorcentaje());
            ps.setInt(3, d.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
