package modelo;

public class PagCursosLibres {
    private int id;
    private int idEmpleado;
    private String nomCurso;
    private int numHoras;
    private double valorHora;

    public PagCursosLibres(String nomCurso, int numHoras, double valorHora) {
        this.nomCurso = nomCurso;
        this.numHoras = numHoras;
        this.valorHora = valorHora;
    }

    public PagCursosLibres(int id, int idEmpleado, String nomCurso, int numHoras, double valorHora) {
        this.id = id;
        this.idEmpleado = idEmpleado;
        this.nomCurso = nomCurso;
        this.numHoras = numHoras;
        this.valorHora = valorHora;
    }

    public double montoTotalCursos() {
        return numHoras * valorHora;
    }

    public double calcularIngreso(DocentePagFijo docente) {
        double total = montoTotalCursos();
        if (docente.getNivelAcademico() != null && 
            docente.getNivelAcademico().equalsIgnoreCase("Postgrado")) {
            total *= 1.3; // +30% para postgrado
        }
        return total;
    }

    // Getters y Setters
    public int getId() { return id; }
    public int getIdEmpleado() { return idEmpleado; }
    public String getNomCurso() { return nomCurso; }
    public int getNumHoras() { return numHoras; }
    public double getValorHora() { return valorHora; }
    
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }
}