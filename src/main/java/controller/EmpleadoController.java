package controller;

import dao.EmpleadoDAO;
import modelo.Empleado;
import java.util.List;

public class EmpleadoController {
    private EmpleadoDAO empleadoDAO;

    public EmpleadoController() {
        this.empleadoDAO = new EmpleadoDAO();
    }

    public boolean agregarEmpleado(String nombre, String puesto, double salario,
                                   String nivelAcademico, String numCarnet) {
        try {
            Empleado empleado = new Empleado(nombre, puesto, salario, nivelAcademico, numCarnet);
            empleadoDAO.insertar(empleado);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Empleado> obtenerTodosEmpleados() {
        return empleadoDAO.listar();
    }

    public boolean actualizarEmpleado(int id, String nombre, String puesto, double salario,
                                      String nivelAcademico, String numCarnet) {
        try {
            Empleado empleado = new Empleado(id, nombre, puesto, salario, nivelAcademico, numCarnet);
            empleadoDAO.actualizar(empleado);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarEmpleado(int id) {
        try {
            empleadoDAO.eliminar(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Empleado buscarEmpleadoPorId(int id) {
        List<Empleado> empleados = empleadoDAO.listar();
        for (Empleado emp : empleados) {
            if (emp.getId() == id) {
                return emp;
            }
        }
        return null;
    }

    public double calcularSalarioNeto(int idEmpleado) {
        Empleado empleado = buscarEmpleadoPorId(idEmpleado);
        if (empleado == null) return 0;
        return empleado.getSalario();
    }

    public boolean validarDatosEmpleado(String nombre, String puesto, String salarioStr,
                                        String nivelAcademico, String numCarnet) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (puesto == null || puesto.trim().isEmpty()) {
            throw new IllegalArgumentException("El puesto es obligatorio");
        }
        if (nivelAcademico == null || nivelAcademico.trim().isEmpty()) {
            throw new IllegalArgumentException("El nivel académico es obligatorio");
        }
        if (numCarnet == null || numCarnet.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de carnet es obligatorio");
        }

        try {
            double salario = Double.parseDouble(salarioStr);
            if (salario <= 0) {
                throw new IllegalArgumentException("El salario debe ser mayor a 0");
            }
            return true;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El salario debe ser un número válido");
        }
    }
}