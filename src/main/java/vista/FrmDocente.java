package vista;

import modelo.Empleado;
import modelo.DocentePagFijo;
import modelo.Descuento;
import modelo.PagCursosLibres;
import dao.DescuentoDAO;
import dao.EmpleadoDAO;
import dao.CursosLibresDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FrmDocente extends JFrame {
    private JComboBox<String> cmbEmpleados;
    private JTextField txtNivel, txtSalario, txtCarnet, txtHoras, txtValorHora, txtCurso;
    private JLabel lblResultado;
    private JTextArea txtDetalles;
    private List<Empleado> listaEmpleados;
    private List<Descuento> listaDescuentos;
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private DescuentoDAO descuentoDAO = new DescuentoDAO();
    private CursosLibresDAO cursosDAO = new CursosLibresDAO();

    public FrmDocente() {
        setTitle("👨‍🏫 Cálculo de Salario Docente");
        setSize(700, 650);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Configurar colores
        Color colorPrincipal = new Color(155, 89, 182);
        Color colorSecundario = new Color(142, 68, 173);
        Color colorFondo = new Color(245, 245, 245);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(colorPrincipal);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel lblTitulo = new JLabel("CÁLCULO DE SALARIO DOCENTE");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Panel de contenido
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de selección de empleado
        JPanel panelEmpleado = crearPanelGrupo("Información del Docente");
        panelEmpleado.setLayout(new GridLayout(3, 2, 10, 10));
        
        JLabel lblNombre = new JLabel("Empleado:");
        cmbEmpleados = new JComboBox<>();
        JLabel lblCarnet = new JLabel("N° Carnet:");
        txtCarnet = new JTextField();
        txtCarnet.setEditable(false);
        JLabel lblNivel = new JLabel("Nivel Académico:");
        txtNivel = new JTextField();
        txtNivel.setEditable(false);
        
        panelEmpleado.add(lblNombre); panelEmpleado.add(cmbEmpleados);
        panelEmpleado.add(lblCarnet); panelEmpleado.add(txtCarnet);
        panelEmpleado.add(lblNivel); panelEmpleado.add(txtNivel);

        // Panel de salario base
        JPanel panelSalario = crearPanelGrupo("Salario Base");
        panelSalario.setLayout(new GridLayout(1, 2, 10, 10));
        
        JLabel lblSalario = new JLabel("Salario Base:");
        txtSalario = new JTextField();
        panelSalario.add(lblSalario); panelSalario.add(txtSalario);

        // Panel de cursos libres
        JPanel panelCursos = crearPanelGrupo("Cursos Libres");
        panelCursos.setLayout(new GridLayout(3, 2, 10, 10));
        
        JLabel lblCurso = new JLabel("Nombre Curso:");
        txtCurso = new JTextField("Curso de Especialización");
        JLabel lblHoras = new JLabel("Horas curso:");
        txtHoras = new JTextField("20");
        JLabel lblValorHora = new JLabel("Valor por hora ($):");
        txtValorHora = new JTextField("15");
        
        panelCursos.add(lblCurso); panelCursos.add(txtCurso);
        panelCursos.add(lblHoras); panelCursos.add(txtHoras);
        panelCursos.add(lblValorHora); panelCursos.add(txtValorHora);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Color.WHITE);
        
        JButton btnCalcular = crearBoton("🧮 Calcular Total", new Color(39, 174, 96));
        JButton btnAgregarCurso = crearBoton("➕ Agregar Curso", new Color(52, 152, 219));
        
        panelBotones.add(btnCalcular);
        panelBotones.add(btnAgregarCurso);

        // Panel de resultados
        JPanel panelResultados = crearPanelGrupo("Resultados del Cálculo");
        panelResultados.setLayout(new BorderLayout());
        
        lblResultado = new JLabel("Seleccione un empleado y haga clic en Calcular");
        lblResultado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblResultado.setForeground(colorSecundario);
        lblResultado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        txtDetalles = new JTextArea();
        txtDetalles.setFont(new Font("Consolas", Font.PLAIN, 11));
        txtDetalles.setEditable(false);
        txtDetalles.setBackground(new Color(250, 250, 250));
        txtDetalles.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        JScrollPane scrollDetalles = new JScrollPane(txtDetalles);
        scrollDetalles.setPreferredSize(new Dimension(0, 150));
        
        panelResultados.add(lblResultado, BorderLayout.NORTH);
        panelResultados.add(scrollDetalles, BorderLayout.CENTER);

        // Ensamblar contenido
        panelContenido.add(panelEmpleado);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));
        panelContenido.add(panelSalario);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));
        panelContenido.add(panelCursos);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));
        panelContenido.add(panelBotones);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));
        panelContenido.add(panelResultados);

        // Ensamblar interfaz principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        
        add(panelPrincipal);

        // Cargar datos y configurar eventos (mantener misma funcionalidad)
        cargarEmpleados();
        cargarDescuentos();

        cmbEmpleados.addActionListener(e -> {
            int index = cmbEmpleados.getSelectedIndex();
            if (index > 0) {
                Empleado emp = listaEmpleados.get(index - 1);
                txtCarnet.setText(emp.getNumCarnet());
                txtNivel.setText(emp.getNivelAcademico());
                txtSalario.setText(String.valueOf(emp.getSalario()));
            } else {
                txtCarnet.setText("");
                txtNivel.setText("");
                txtSalario.setText("");
            }
        });

        btnCalcular.addActionListener(e -> calcularTotal());
        btnAgregarCurso.addActionListener(e -> agregarCurso());
    }

    private JPanel crearPanelGrupo(String titulo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                titulo,
                0, 0,
                new Font("Segoe UI", Font.BOLD, 12),
                new Color(100, 100, 100)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        return panel;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker()),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent e) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }

    // Mantener los métodos cargarEmpleados(), cargarDescuentos(), agregarCurso(), calcularTotal()
    // con la misma funcionalidad que en la versión anterior
    private void cargarEmpleados() {
        listaEmpleados = empleadoDAO.listar();
        cmbEmpleados.removeAllItems();
        cmbEmpleados.addItem("-- Selecciona un empleado --");
        for (Empleado e : listaEmpleados) {
            cmbEmpleados.addItem(e.getNombre() + " - " + e.getPuesto());
        }
    }

    private void cargarDescuentos() {
        listaDescuentos = descuentoDAO.listar();
    }

    private void agregarCurso() {
        int index = cmbEmpleados.getSelectedIndex();
        if (index <= 0) {
            JOptionPane.showMessageDialog(this, "❌ Selecciona un empleado primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Empleado emp = listaEmpleados.get(index - 1);
            String nomCurso = txtCurso.getText();
            int horas = Integer.parseInt(txtHoras.getText());
            double valorHora = Double.parseDouble(txtValorHora.getText());

            PagCursosLibres curso = new PagCursosLibres(nomCurso, horas, valorHora);
            curso.setIdEmpleado(emp.getId());
            
            cursosDAO.insertar(curso);
            JOptionPane.showMessageDialog(this, "✅ Curso agregado exitosamente");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❌ Revisa los valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularTotal() {
        int index = cmbEmpleados.getSelectedIndex();
        if (index <= 0) {
            JOptionPane.showMessageDialog(this, "❌ Selecciona un empleado primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Empleado emp = listaEmpleados.get(index - 1);
        try {
            double salarioBase = Double.parseDouble(txtSalario.getText());
            
            DocentePagFijo docente = new DocentePagFijo(
                emp.getNumCarnet(), 
                emp.getNombre(), 
                emp.getNivelAcademico(), 
                salarioBase, 
                listaDescuentos
            );

            double totalHoras = cursosDAO.getTotalHorasPorEmpleado(emp.getId());
            double valorHora = Double.parseDouble(txtValorHora.getText());
            PagCursosLibres curso = new PagCursosLibres("Total Cursos", (int)totalHoras, valorHora);

            double ingresoFijo = docente.calcularIngreso();
            double ingresoCursos = curso.calcularIngreso(docente);
            double total = ingresoFijo + ingresoCursos;

            StringBuilder detalles = new StringBuilder();
            detalles.append("=== DETALLE DE CÁLCULOS ===\n");
            detalles.append("Salario Base: $").append(salarioBase).append("\n");
            detalles.append("Descuento de Ley (10%): $").append(salarioBase * 0.10).append("\n");
            
            if (listaDescuentos != null) {
                detalles.append("Descuentos Adicionales:\n");
                for (Descuento desc : listaDescuentos) {
                    double montoDesc = salarioBase * (desc.getPorcentaje() / 100);
                    detalles.append("  - ").append(desc.getTipo()).append(" (").append(desc.getPorcentaje())
                           .append("%): $").append(String.format("%.2f", montoDesc)).append("\n");
                }
            }
            
            detalles.append("Total Descuentos: $").append(String.format("%.2f", docente.calcularDescuento())).append("\n");
            detalles.append("Ingreso Plaza Fija: $").append(String.format("%.2f", ingresoFijo)).append("\n");
            detalles.append("Horas Cursos Libres: ").append(totalHoras).append("\n");
            detalles.append("Ingreso Cursos Libres: $").append(String.format("%.2f", ingresoCursos)).append("\n");
            detalles.append("TOTAL INGRESO: $").append(String.format("%.2f", total));

            lblResultado.setText(String.format("Total para %s: $%.2f", emp.getNombre(), total));
            txtDetalles.setText(detalles.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❌ Revisa los valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}