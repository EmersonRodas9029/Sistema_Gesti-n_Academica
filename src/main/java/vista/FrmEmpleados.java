package vista;

import dao.EmpleadoDAO;
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
    private EmpleadoDAO dao = new EmpleadoDAO();
    private int idSeleccionado = -1;

    public FrmEmpleados() {
        setTitle("🏢 Gestión de Empleados");
        setSize(900, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Configurar colores
        Color colorPrincipal = new Color(41, 128, 185);
        Color colorSecundario = new Color(52, 152, 219);
        Color colorFondo = new Color(245, 245, 245);
        
        // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(colorPrincipal);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel lblTitulo = new JLabel("GESTIÓN DE EMPLEADOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Panel principal que contendrá formulario y tabla
        JPanel panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(Color.WHITE);
        
        // Panel de formulario - ahora más compacto
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panelFormulario.setPreferredSize(new Dimension(0, 200)); // Altura fija

        // Configurar componentes del formulario
        Font fontLabel = new Font("Segoe UI", Font.BOLD, 12);
        Font fontField = new Font("Segoe UI", Font.PLAIN, 12);

        JLabel lbl1 = new JLabel("Nombre:");
        JLabel lbl2 = new JLabel("Puesto:");
        JLabel lbl3 = new JLabel("Salario:");
        JLabel lbl4 = new JLabel("Nivel Académico:");
        JLabel lbl5 = new JLabel("N° Carnet:");

        txtNombre = new JTextField();
        txtPuesto = new JTextField();
        txtSalario = new JTextField();
        txtNivel = new JTextField();
        txtCarnet = new JTextField();

        // Estilizar labels
        lbl1.setFont(fontLabel); lbl2.setFont(fontLabel); 
        lbl3.setFont(fontLabel); lbl4.setFont(fontLabel); lbl5.setFont(fontLabel);
        
        // Estilizar campos
        txtNombre.setFont(fontField); txtPuesto.setFont(fontField);
        txtSalario.setFont(fontField); txtNivel.setFont(fontField); txtCarnet.setFont(fontField);

        // Posicionamiento en dos columnas
        // Columna izquierda - Campos
        lbl1.setBounds(20, 20, 100, 25);
        lbl2.setBounds(20, 55, 100, 25);
        lbl3.setBounds(20, 90, 100, 25);
        lbl4.setBounds(20, 125, 120, 25);
        lbl5.setBounds(20, 160, 100, 25);

        txtNombre.setBounds(150, 20, 220, 30);
        txtPuesto.setBounds(150, 55, 220, 30);
        txtSalario.setBounds(150, 90, 220, 30);
        txtNivel.setBounds(150, 125, 220, 30);
        txtCarnet.setBounds(150, 160, 220, 30);

        // Botones con mejor diseño - Columna derecha
        JButton btnAgregar = crearBoton("➕ Agregar", colorSecundario);
        JButton btnActualizar = crearBoton("✏️ Actualizar", new Color(39, 174, 96));
        JButton btnEliminar = crearBoton("🗑️ Eliminar", new Color(231, 76, 60));
        JButton btnLimpiar = crearBoton("🧹 Limpiar", new Color(149, 165, 166));

        btnAgregar.setBounds(400, 20, 130, 35);
        btnActualizar.setBounds(400, 65, 130, 35);
        btnEliminar.setBounds(400, 110, 130, 35);
        btnLimpiar.setBounds(400, 155, 130, 35);

        // Agregar componentes al panel de formulario
        panelFormulario.add(lbl1); panelFormulario.add(lbl2); 
        panelFormulario.add(lbl3); panelFormulario.add(lbl4); panelFormulario.add(lbl5);
        panelFormulario.add(txtNombre); panelFormulario.add(txtPuesto); 
        panelFormulario.add(txtSalario); panelFormulario.add(txtNivel); panelFormulario.add(txtCarnet);
        panelFormulario.add(btnAgregar); panelFormulario.add(btnActualizar); 
        panelFormulario.add(btnEliminar); panelFormulario.add(btnLimpiar);

        // Panel de tabla con título
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Título de la tabla
        JPanel panelTituloTabla = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTituloTabla.setBackground(Color.WHITE);
        JLabel lblTituloTabla = new JLabel("📋 Lista de Empleados");
        lblTituloTabla.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTituloTabla.setForeground(colorPrincipal);
        panelTituloTabla.add(lblTituloTabla);
        
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Puesto", "Salario", "Nivel", "N° Carnet"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tabla.setRowHeight(25);
        tabla.setSelectionBackground(new Color(52, 152, 219));
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(new Color(220, 220, 220));
        
        // Mejorar el header de la tabla
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(colorPrincipal);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        scroll.setPreferredSize(new Dimension(0, 350)); // Altura fija para la tabla
        
        panelTabla.add(panelTituloTabla, BorderLayout.NORTH);
        panelTabla.add(scroll, BorderLayout.CENTER);

        // Ensamblar la interfaz
        panelContenido.add(panelFormulario, BorderLayout.NORTH);
        panelContenido.add(panelTabla, BorderLayout.CENTER);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        
        add(panelPrincipal);

        // Listeners
        btnAgregar.addActionListener(e -> {
            try {
                if (validarCampos()) {
                    Empleado emp = new Empleado(txtNombre.getText(), txtPuesto.getText(),
                            Double.parseDouble(txtSalario.getText()), txtNivel.getText(), txtCarnet.getText());
                    dao.insertar(emp);
                    limpiarCampos();
                    listar();
                    JOptionPane.showMessageDialog(this, "✅ Empleado agregado exitosamente");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Revisa el formato del salario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
            try {
                if (idSeleccionado != -1) {
                    if (validarCampos()) {
                        Empleado emp = new Empleado(idSeleccionado, txtNombre.getText(), txtPuesto.getText(),
                                Double.parseDouble(txtSalario.getText()), txtNivel.getText(), txtCarnet.getText());
                        dao.actualizar(emp);
                        limpiarCampos();
                        listar();
                        JOptionPane.showMessageDialog(this, "✅ Empleado actualizado exitosamente");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "⚠️ Selecciona un empleado para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Revisa el formato del salario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            if (idSeleccionado != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "¿Estás seguro de eliminar este empleado?", "Confirmar Eliminación", 
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    dao.eliminar(idSeleccionado);
                    limpiarCampos();
                    listar();
                    JOptionPane.showMessageDialog(this, "✅ Empleado eliminado exitosamente");
                }
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un empleado para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    idSeleccionado = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
                    txtNombre.setText(tabla.getValueAt(fila, 1).toString());
                    txtPuesto.setText(tabla.getValueAt(fila, 2).toString());
                    
                    // Remover el símbolo $ si existe para poder editar
                    String salario = tabla.getValueAt(fila, 3).toString().replace("$", "");
                    txtSalario.setText(salario);
                    
                    txtNivel.setText(tabla.getValueAt(fila, 4).toString());
                    txtCarnet.setText(tabla.getValueAt(fila, 5) != null ? tabla.getValueAt(fila, 5).toString() : "");
                }
            }
        });

        // Cargar lista al abrir el formulario
        listar();
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
        
        // Efecto hover
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

    private void listar() {
        modelo.setRowCount(0);
        List<Empleado> lista = dao.listar();
        for (Empleado e : lista) {
            modelo.addRow(new Object[]{
                e.getId(), 
                e.getNombre(), 
                e.getPuesto(), 
                String.format("$%.2f", e.getSalario()), 
                e.getNivelAcademico(),
                e.getNumCarnet()
            });
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El nombre es obligatorio", txtNombre);
            return false;
        }
        if (txtPuesto.getText().trim().isEmpty()) {
            mostrarError("El puesto es obligatorio", txtPuesto);
            return false;
        }
        if (txtSalario.getText().trim().isEmpty()) {
            mostrarError("El salario es obligatorio", txtSalario);
            return false;
        }
        if (txtNivel.getText().trim().isEmpty()) {
            mostrarError("El nivel académico es obligatorio", txtNivel);
            return false;
        }
        if (txtCarnet.getText().trim().isEmpty()) {
            mostrarError("El número de carnet es obligatorio", txtCarnet);
            return false;
        }
        
        try {
            double salario = Double.parseDouble(txtSalario.getText());
            if (salario <= 0) {
                mostrarError("El salario debe ser mayor a 0", txtSalario);
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("El salario debe ser un número válido", txtSalario);
            return false;
        }
        
        return true;
    }

    private void mostrarError(String mensaje, JTextField campo) {
        JOptionPane.showMessageDialog(this, "❌ " + mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        campo.requestFocus();
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
}