package controller;

import dao.CursosLibresDAO;
import modelo.PagCursosLibres;
import modelo.DocentePagFijo;
import java.util.List;

public class CursoController {
    private CursosLibresDAO cursosDAO;

    public CursoController() {
        this.cursosDAO = new CursosLibresDAO();
    }

    public boolean agregarCurso(int idEmpleado, String nomCurso, int numHoras, double valorHora) {
        try {
            PagCursosLibres curso = new PagCursosLibres(nomCurso, numHoras, valorHora);
            curso.setIdEmpleado(idEmpleado);
            cursosDAO.insertar(curso);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PagCursosLibres> obtenerCursosPorEmpleado(int idEmpleado) {
        return cursosDAO.listarPorEmpleado(idEmpleado);
    }

    public double obtenerTotalHorasPorEmpleado(int idEmpleado) {
        return cursosDAO.getTotalHorasPorEmpleado(idEmpleado);
    }

    public double calcularIngresoCursos(int idEmpleado, DocentePagFijo docente, double valorHora) {
        double totalHoras = obtenerTotalHorasPorEmpleado(idEmpleado);
        PagCursosLibres curso = new PagCursosLibres("Total Cursos", (int)totalHoras, valorHora);
        return curso.calcularIngreso(docente);
    }

    public double calcularTotalIngresos(double salarioBase, List<modelo.Descuento> descuentos,
                                        int idEmpleado, DocentePagFijo docente, double valorHora) {
        double descuentoLey = salarioBase * 0.10;
        double descuentosAdicionales = 0;

        if (descuentos != null) {
            for (modelo.Descuento desc : descuentos) {
                descuentosAdicionales += salarioBase * (desc.getPorcentaje() / 100);
            }
        }

        double ingresoFijo = salarioBase - (descuentoLey + descuentosAdicionales);

        double ingresoCursos = calcularIngresoCursos(idEmpleado, docente, valorHora);

        return ingresoFijo + ingresoCursos;
    }

    public boolean validarDatosCurso(String nombreCurso, String horasStr, String valorHoraStr) {
        if (nombreCurso == null || nombreCurso.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del curso es obligatorio");
        }

        try {
            int horas = Integer.parseInt(horasStr);
            if (horas <= 0) {
                throw new IllegalArgumentException("Las horas deben ser mayores a 0");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Las horas deben ser un número entero válido");
        }

        try {
            double valorHora = Double.parseDouble(valorHoraStr);
            if (valorHora <= 0) {
                throw new IllegalArgumentException("El valor por hora debe ser mayor a 0");
            }
            return true;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El valor por hora debe ser un número válido");
        }
    }
}