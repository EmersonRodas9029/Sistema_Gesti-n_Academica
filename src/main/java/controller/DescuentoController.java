package controller;

import dao.DescuentoDAO;
import modelo.Descuento;
import java.util.List;

public class DescuentoController {
    private DescuentoDAO descuentoDAO;

    public DescuentoController() {
        this.descuentoDAO = new DescuentoDAO();
    }

    public boolean agregarDescuento(String tipo, double porcentaje) {
        try {
            Descuento descuento = new Descuento(tipo, porcentaje);
            descuentoDAO.insertar(descuento);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Descuento> obtenerTodosDescuentos() {
        return descuentoDAO.listar();
    }

    public boolean actualizarDescuento(int id, String tipo, double porcentaje) {
        try {
            Descuento descuento = new Descuento(id, tipo, porcentaje);
            descuentoDAO.actualizar(descuento);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarDescuento(int id) {
        try {
            descuentoDAO.eliminar(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public double calcularDescuentoTotal(List<Descuento> descuentos, double salarioBase) {
        double descuentoTotal = 0;

        for (Descuento descuento : descuentos) {
            descuentoTotal += salarioBase * (descuento.getPorcentaje() / 100);
        }

        // Agregar descuento de ley (10%)
        descuentoTotal += salarioBase * 0.10;

        return descuentoTotal;
    }

    public boolean validarDatosDescuento(String tipo, String porcentajeStr) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de descuento es obligatorio");
        }

        try {
            double porcentaje = Double.parseDouble(porcentajeStr);
            if (porcentaje <= 0) {
                throw new IllegalArgumentException("El porcentaje debe ser mayor a 0");
            }
            if (porcentaje > 100) {
                throw new IllegalArgumentException("El porcentaje no puede ser mayor a 100");
            }
            return true;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El porcentaje debe ser un número válido");
        }
    }
}