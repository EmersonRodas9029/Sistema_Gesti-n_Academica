package dao;

import modelo.PagCursosLibres;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursosLibresDAO {

    public void insertar(PagCursosLibres curso) {
        String sql = "INSERT INTO cursos_libres (id_empleado, nom_curso, num_horas, valor_hora, fecha) VALUES (?,?,?,?,CURDATE())";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, curso.getIdEmpleado());
            ps.setString(2, curso.getNomCurso());
            ps.setInt(3, curso.getNumHoras());
            ps.setDouble(4, curso.getValorHora());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<PagCursosLibres> listarPorEmpleado(int idEmpleado) {
        List<PagCursosLibres> lista = new ArrayList<>();
        String sql = "SELECT * FROM cursos_libres WHERE id_empleado = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PagCursosLibres curso = new PagCursosLibres(
                    rs.getInt("id"),
                    rs.getInt("id_empleado"),
                    rs.getString("nom_curso"),
                    rs.getInt("num_horas"),
                    rs.getDouble("valor_hora")
                );
                lista.add(curso);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public double getTotalHorasPorEmpleado(int idEmpleado) {
        String sql = "SELECT SUM(num_horas) as total_horas FROM cursos_libres WHERE id_empleado = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_horas");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}