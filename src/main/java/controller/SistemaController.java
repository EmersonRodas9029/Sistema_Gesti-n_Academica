package controller;

import modelo.*;
import java.util.List;

public class SistemaController {
    private EmpleadoController empleadoController;
    private DescuentoController descuentoController;
    private CursoController cursoController;

    public SistemaController() {
        this.empleadoController = new EmpleadoController();
        this.descuentoController = new DescuentoController();
        this.cursoController = new CursoController();
    }

    public boolean agregarEmpleado(String nombre, String puesto, String salarioStr,
                                   String nivelAcademico, String numCarnet) {
        try {
            empleadoController.validarDatosEmpleado(nombre, puesto, salarioStr, nivelAcademico, numCarnet);
            double salario = Double.parseDouble(salarioStr);
            return empleadoController.agregarEmpleado(nombre, puesto, salario, nivelAcademico, numCarnet);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public List<Empleado> obtenerEmpleados() {
        return empleadoController.obtenerTodosEmpleados();
    }

    public boolean agregarDescuento(String tipo, String porcentajeStr) {
        try {
            descuentoController.validarDatosDescuento(tipo, porcentajeStr);
            double porcentaje = Double.parseDouble(porcentajeStr);
            return descuentoController.agregarDescuento(tipo, porcentaje);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public List<Descuento> obtenerDescuentos() {
        return descuentoController.obtenerTodosDescuentos();
    }

    public boolean agregarCurso(int idEmpleado, String nombreCurso, String horasStr, String valorHoraStr) {
        try {
            cursoController.validarDatosCurso(nombreCurso, horasStr, valorHoraStr);
            int horas = Integer.parseInt(horasStr);
            double valorHora = Double.parseDouble(valorHoraStr);
            return cursoController.agregarCurso(idEmpleado, nombreCurso, horas, valorHora);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public String calcularSalarioCompleto(int idEmpleado, String valorHoraStr) {
        try {
            Empleado empleado = empleadoController.buscarEmpleadoPorId(idEmpleado);
            if (empleado == null) {
                return "Empleado no encontrado";
            }

            List<Descuento> descuentos = descuentoController.obtenerTodosDescuentos();
            DocentePagFijo docente = new DocentePagFijo(
                    empleado.getNumCarnet(),
                    empleado.getNombre(),
                    empleado.getNivelAcademico(),
                    empleado.getSalario(),
                    descuentos
            );

            double valorHora = Double.parseDouble(valorHoraStr);
            double total = cursoController.calcularTotalIngresos(
                    empleado.getSalario(), descuentos, idEmpleado, docente, valorHora
            );

            StringBuilder reporte = new StringBuilder();
            reporte.append("=== REPORTE DE SALARIO ===\n");
            reporte.append("Empleado: ").append(empleado.getNombre()).append("\n");
            reporte.append("Salario Base: $").append(String.format("%.2f", empleado.getSalario())).append("\n");
            reporte.append("Descuento de Ley (10%): $").append(String.format("%.2f", empleado.getSalario() * 0.10)).append("\n");

            if (!descuentos.isEmpty()) {
                reporte.append("Descuentos Adicionales:\n");
                for (Descuento desc : descuentos) {
                    double monto = empleado.getSalario() * (desc.getPorcentaje() / 100);
                    reporte.append("  - ").append(desc.getTipo()).append(" (").append(desc.getPorcentaje())
                            .append("%): $").append(String.format("%.2f", monto)).append("\n");
                }
            }

            double totalHoras = cursoController.obtenerTotalHorasPorEmpleado(idEmpleado);
            reporte.append("Horas Cursos Libres: ").append(totalHoras).append("\n");
            reporte.append("Ingreso Cursos: $").append(String.format("%.2f",
                    cursoController.calcularIngresoCursos(idEmpleado, docente, valorHora))).append("\n");
            reporte.append("TOTAL NETO: $").append(String.format("%.2f", total));

            return reporte.toString();

        } catch (Exception e) {
            return "Error en el cálculo: " + e.getMessage();
        }
    }

    public String obtenerEstadisticas() {
        List<Empleado> empleados = obtenerEmpleados();
        List<Descuento> descuentos = obtenerDescuentos();

        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTADÍSTICAS DEL SISTEMA ===\n");
        stats.append("Total Empleados: ").append(empleados.size()).append("\n");
        stats.append("Total Descuentos Configurados: ").append(descuentos.size()).append("\n");

        double totalSalarios = 0;
        for (Empleado emp : empleados) {
            totalSalarios += emp.getSalario();
        }
        stats.append("Total en Salarios Base: $").append(String.format("%.2f", totalSalarios)).append("\n");

        return stats.toString();
    }
}