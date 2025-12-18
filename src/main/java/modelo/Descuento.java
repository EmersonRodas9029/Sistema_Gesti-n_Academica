package modelo;

public class Descuento {
    private int id;
    private String tipo;
    private double porcentaje;

    public Descuento(int id, String tipo, double porcentaje) {
        this.id = id;
        this.tipo = tipo;
        this.porcentaje = porcentaje;
    }

    public Descuento(String tipo, double porcentaje) {
        this.tipo = tipo;
        this.porcentaje = porcentaje;
    }

    public int getId() { return id; }
    public String getTipo() { return tipo; }
    public double getPorcentaje() { return porcentaje; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setPorcentaje(double porcentaje) { this.porcentaje = porcentaje; }
}
