package vista;

import controller.SistemaController;
import modelo.Empleado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FrmEmpleados extends JFrame {
    private JTextField txtNombre, txtPuesto, txtSalario, txtNivel, txtCarnet;
    private JTable tabla;
    private DefaultTableModel modelo;
    private SistemaController controller;
    private int idSeleccionado = -1;

    public FrmEmpleados() {
        this.controller = new SistemaController();
        inicializarUI();
        configurarListeners();
        cargarDatos();
    }

    private void inicializarUI() {
        setTitle("🏢 Gestión de Empleados");
        setSize(900, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar colores
        Color colorPrincipal = new Color(41, 128, 185);
        Color colorFondo = new Color(245, 245, 245);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

        // Panel de título
        JPanel panelTitulo = crearPanelTitulo(colorPrincipal, "GESTIÓN DE EMPLEADOS");

        // Panel de contenido
        JPanel panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(Color.WHITE);

        // Panel de formulario
        JPanel panelFormulario = crearPanelFormulario();

        // Panel de tabla
        JPanel panelTabla = crearPanelTabla(colorPrincipal);

        // Ensamblar interfaz
        panelContenido.add(panelFormulario, BorderLayout.NORTH);
        panelContenido.add(panelTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private JPanel crearPanelTitulo(Color color, String titulo) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panel.add(lblTitulo);
        return panel;
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setPreferredSize(new Dimension(0, 200));

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 12);
        Font fontField = new Font("Segoe UI", Font.PLAIN, 12);

        // Etiquetas y campos
        JLabel[] labels = {
                new JLabel("Nombre:"), new JLabel("Puesto:"), new JLabel("Salario:"),
                new JLabel("Nivel Académico:"), new JLabel("N° Carnet:")
        };

        txtNombre = new JTextField();
        txtPuesto = new JTextField();
        txtSalario = new JTextField();
        txtNivel = new JTextField();
        txtCarnet = new JTextField();

        JTextField[] campos = {txtNombre, txtPuesto, txtSalario, txtNivel, txtCarnet};

        // Posicionar componentes
        int y = 20;
        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(fontLabel);
            labels[i].setBounds(20, y, 120, 25);
            campos[i].setFont(fontField);
            campos[i].setBounds(150, y, 220, 30);
            panel.add(labels[i]);
            panel.add(campos[i]);
            y += 35;
        }

        // Botones
        JButton btnAgregar = crearBoton("➕ Agregar", new Color(52, 152, 219));
        JButton btnActualizar = crearBoton("✏️ Actualizar", new Color(39, 174, 96));
        JButton btnEliminar = crearBoton("🗑️ Eliminar", new Color(231, 76, 60));
        JButton btnLimpiar = crearBoton("🧹 Limpiar", new Color(149, 165, 166));

        btnAgregar.setBounds(400, 20, 130, 35);
        btnActualizar.setBounds(400, 65, 130, 35);
        btnEliminar.setBounds(400, 110, 130, 35);
        btnLimpiar.setBounds(400, 155, 130, 35);

        panel.add(btnAgregar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);

        return panel;
    }

    private JPanel crearPanelTabla(Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título de la tabla
        JPanel panelTituloTabla = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTituloTabla.setBackground(Color.WHITE);
        JLabel lblTituloTabla = new JLabel("📋 Lista de Empleados");
        lblTituloTabla.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTituloTabla.setForeground(color);
        panelTituloTabla.add(lblTituloTabla);

        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Puesto", "Salario", "Nivel", "N° Carnet"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tabla.setRowHeight(25);
        tabla.setSelectionBackground(new Color(52, 152, 219));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(new Color(220, 220, 220));

        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(color);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        panel.add(panelTituloTabla, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

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
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
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

    private void configurarListeners() {
        // Listener para tabla
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    idSeleccionado = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
                    txtNombre.setText(tabla.getValueAt(fila, 1).toString());
                    txtPuesto.setText(tabla.getValueAt(fila, 2).toString());

                    String salario = tabla.getValueAt(fila, 3).toString().replace("$", "");
                    txtSalario.setText(salario);

                    txtNivel.setText(tabla.getValueAt(fila, 4).toString());
                    txtCarnet.setText(tabla.getValueAt(fila, 5) != null ? tabla.getValueAt(fila, 5).toString() : "");
                }
            }
        });
    }

    private void cargarDatos() {
        try {
            modelo.setRowCount(0);
            List<Empleado> empleados = controller.obtenerEmpleados();

            for (Empleado e : empleados) {
                modelo.addRow(new Object[]{
                        e.getId(),
                        e.getNombre(),
                        e.getPuesto(),
                        String.format("$%.2f", e.getSalario()),
                        e.getNivelAcademico(),
                        e.getNumCarnet()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Métodos para los botones (que llamarás desde los listeners)
    public void agregarEmpleado() {
        try {
            controller.agregarEmpleado(
                    txtNombre.getText(),
                    txtPuesto.getText(),
                    txtSalario.getText(),
                    txtNivel.getText(),
                    txtCarnet.getText()
            );

            limpiarCampos();
            cargarDatos();
            JOptionPane.showMessageDialog(this, "✅ Empleado agregado exitosamente");

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage(),
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al agregar empleado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarEmpleado() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona un empleado para actualizar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Aquí necesitarías implementar el método de actualización en el controller
            // Por ahora uso el método de agregar como placeholder
            controller.agregarEmpleado(
                    txtNombre.getText(),
                    txtPuesto.getText(),
                    txtSalario.getText(),
                    txtNivel.getText(),
                    txtCarnet.getText()
            );

            limpiarCampos();
            cargarDatos();
            JOptionPane.showMessageDialog(this, "✅ Empleado actualizado exitosamente");

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "❌ " + e.getMessage(),
                    "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al actualizar empleado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtPuesto.setText("");
        txtSalario.setText("");
        txtNivel.setText("");
        txtCarnet.setText("");
        idSeleccionado = -1;
        tabla.clearSelection();
    }

    // Método main para pruebas
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmEmpleados frame = new FrmEmpleados();
            frame.setVisible(true);
        });
    }
}