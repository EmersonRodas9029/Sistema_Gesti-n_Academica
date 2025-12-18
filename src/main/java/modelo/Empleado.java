package modelo;

public class Empleado {
    private int id;
    private String nombre;
    private String puesto;
    private double salario;
    private String nivelAcademico;
    private String numCarnet;

    public Empleado(int id, String nombre, String puesto, double salario, String nivelAcademico, String numCarnet) {
        this.id = id;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
        this.nivelAcademico = nivelAcademico;
        this.numCarnet = numCarnet;
    }

    public Empleado(String nombre, String puesto, double salario, String nivelAcademico, String numCarnet) {
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
        this.nivelAcademico = nivelAcademico;
        this.numCarnet = numCarnet;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPuesto() { return puesto; }
    public double getSalario() { return salario; }
    public String getNivelAcademico() { return nivelAcademico; }
    public String getNumCarnet() { return numCarnet; }
    
    public void setNumCarnet(String numCarnet) { this.numCarnet = numCarnet; }
}