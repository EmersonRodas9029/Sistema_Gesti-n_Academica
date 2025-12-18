package dao;

import modelo.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public void insertar(Empleado e) {
        String sql = "INSERT INTO empleados (nombre, puesto, salario, nivel_academico, num_carnet) VALUES (?,?,?,?,?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getPuesto());
            ps.setDouble(3, e.getSalario());
            ps.setString(4, e.getNivelAcademico());
            ps.setString(5, e.getNumCarnet());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Empleado> listar() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleados";
        try (Connection con = Conexion.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Empleado e = new Empleado(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("puesto"),
                    rs.getDouble("salario"),
                    rs.getString("nivel_academico"),
                    rs.getString("num_carnet")
                );
                lista.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void actualizar(Empleado e) {
        String sql = "UPDATE empleados SET nombre=?, puesto=?, salario=?, nivel_academico=?, num_carnet=? WHERE id=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getPuesto());
            ps.setDouble(3, e.getSalario());
            ps.setString(4, e.getNivelAcademico());
            ps.setString(5, e.getNumCarnet());
            ps.setInt(6, e.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}