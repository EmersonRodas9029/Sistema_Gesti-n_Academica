package modelo;

import java.util.List;

public class DocentePagFijo {
    private String numCarnet;
    private String nomDocente;
    private String nivelAcademico;
    private double salarioBase;
    private List<Descuento> descuentosAdicionales;

    public DocentePagFijo(String numCarnet, String nomDocente, String nivelAcademico, double salarioBase) {
        this.numCarnet = numCarnet;
        this.nomDocente = nomDocente;
        this.nivelAcademico = nivelAcademico;
        this.salarioBase = salarioBase;
    }

    public DocentePagFijo(String numCarnet, String nomDocente, String nivelAcademico, double salarioBase, List<Descuento> descuentosAdicionales) {
        this.numCarnet = numCarnet;
        this.nomDocente = nomDocente;
        this.nivelAcademico = nivelAcademico;
        this.salarioBase = salarioBase;
        this.descuentosAdicionales = descuentosAdicionales;
    }

    public double calcularDescuento() {
        double descuentoLey = salarioBase * 0.10;
        double descuentosAdicionalesTotal = 0.0;
        
        if (descuentosAdicionales != null) {
            for (Descuento descuento : descuentosAdicionales) {
                descuentosAdicionalesTotal += salarioBase * (descuento.getPorcentaje() / 100);
            }
        }
        
        return descuentoLey + descuentosAdicionalesTotal;
    }

    public double calcularIngreso() {
        return salarioBase - calcularDescuento();
    }

    // Getters
    public String getNumCarnet() { return numCarnet; }
    public String getNomDocente() { return nomDocente; }
    public String getNivelAcademico() { return nivelAcademico; }
    public double getSalarioBase() { return salarioBase; }
    public List<Descuento> getDescuentosAdicionales() { return descuentosAdicionales; }
}