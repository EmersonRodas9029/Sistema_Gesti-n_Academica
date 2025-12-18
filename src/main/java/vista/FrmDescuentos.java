package vista;

import dao.DescuentoDAO;
import modelo.Descuento;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FrmDescuentos extends JFrame {
    private JTextField txtTipo, txtPorcentaje;
    private JTable tabla;
    private DefaultTableModel modelo;
    private DescuentoDAO dao = new DescuentoDAO();
    private int idSeleccionado = -1;

    public FrmDescuentos() {
        setTitle("💰 Gestión de Descuentos");
        setSize(700, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Configurar colores
        Color colorPrincipal = new Color(230, 126, 34);
        Color colorSecundario = new Color(241, 196, 15);
        Color colorFondo = new Color(245, 245, 245);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(colorPrincipal);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        JLabel lblTitulo = new JLabel("GESTIÓN DE DESCUENTOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Panel de contenido principal
        JPanel panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(Color.WHITE);
        
        // Panel de formulario - altura fija
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panelFormulario.setPreferredSize(new Dimension(0, 180));

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 12);
        Font fontField = new Font("Segoe UI", Font.PLAIN, 12);

        JLabel lbl1 = new JLabel("Tipo de Descuento:");
        JLabel lbl2 = new JLabel("Porcentaje (%):");
        lbl1.setFont(fontLabel); 
        lbl2.setFont(fontLabel);

        txtTipo = new JTextField();
        txtPorcentaje = new JTextField();
        txtTipo.setFont(fontField); 
        txtPorcentaje.setFont(fontField);

        // Posicionamiento en dos columnas
        lbl1.setBounds(20, 30, 120, 25);
        lbl2.setBounds(20, 75, 120, 25);
        txtTipo.setBounds(150, 30, 220, 30);
        txtPorcentaje.setBounds(150, 75, 220, 30);

        // Botones - columna derecha
        JButton btnAgregar = crearBoton("➕ Agregar", new Color(39, 174, 96));
        JButton btnActualizar = crearBoton("✏️ Actualizar", new Color(52, 152, 219));
        JButton btnEliminar = crearBoton("🗑️ Eliminar", new Color(231, 76, 60));
        JButton btnLimpiar = crearBoton("🧹 Limpiar", new Color(149, 165, 166));

        btnAgregar.setBounds(400, 30, 130, 35);
        btnActualizar.setBounds(400, 75, 130, 35);
        btnEliminar.setBounds(400, 120, 130, 35);

        panelFormulario.add(lbl1); 
        panelFormulario.add(lbl2);
        panelFormulario.add(txtTipo); 
        panelFormulario.add(txtPorcentaje);
        panelFormulario.add(btnAgregar); 
        panelFormulario.add(btnActualizar); 
        panelFormulario.add(btnEliminar);
        panelFormulario.add(btnLimpiar);

        // Panel de tabla con título
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Título de la tabla
        JPanel panelTituloTabla = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTituloTabla.setBackground(Color.WHITE);
        JLabel lblTituloTabla = new JLabel("📋 Lista de Descuentos");
        lblTituloTabla.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTituloTabla.setForeground(colorPrincipal);
        panelTituloTabla.add(lblTituloTabla);
        
        modelo = new DefaultTableModel(new String[]{"ID", "Tipo", "Porcentaje"}, 0) {
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
        tabla.getTableHeader().setBackground(colorPrincipal);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        scroll.setPreferredSize(new Dimension(0, 300));
        
        panelTabla.add(panelTituloTabla, BorderLayout.NORTH);
        panelTabla.add(scroll, BorderLayout.CENTER);

        // Ensamblar interfaz
        panelContenido.add(panelFormulario, BorderLayout.NORTH);
        panelContenido.add(panelTabla, BorderLayout.CENTER);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        
        add(panelPrincipal);

        // Listeners
        btnAgregar.addActionListener(e -> {
            try {
                if (validarCampos()) {
                    Descuento d = new Descuento(txtTipo.getText(), Double.parseDouble(txtPorcentaje.getText()));
                    dao.insertar(d);
                    listar();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "✅ Descuento agregado exitosamente");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Revisa el formato del porcentaje.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
            try {
                if (idSeleccionado != -1) {
                    if (validarCampos()) {
                        Descuento d = new Descuento(idSeleccionado, txtTipo.getText(), Double.parseDouble(txtPorcentaje.getText()));
                        dao.actualizar(d);
                        listar();
                        limpiarCampos();
                        JOptionPane.showMessageDialog(this, "✅ Descuento actualizado exitosamente");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "⚠️ Selecciona un descuento para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Revisa el formato del porcentaje.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            if (idSeleccionado != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "¿Estás seguro de eliminar este descuento?", "Confirmar Eliminación", 
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    dao.eliminar(idSeleccionado);
                    listar();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "✅ Descuento eliminado exitosamente");
                }
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona un descuento para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    idSeleccionado = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
                    txtTipo.setText(tabla.getValueAt(fila, 1).toString());
                    
                    // Remover el símbolo % si existe para poder editar
                    String porcentaje = tabla.getValueAt(fila, 2).toString().replace("%", "");
                    txtPorcentaje.setText(porcentaje);
                }
            }
        });

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
        List<Descuento> lista = dao.listar();
        for (Descuento d : lista) {
            modelo.addRow(new Object[]{
                d.getId(), 
                d.getTipo(), 
                String.format("%.2f%%", d.getPorcentaje())
            });
        }
    }

    private boolean validarCampos() {
        if (txtTipo.getText().trim().isEmpty()) {
            mostrarError("El tipo de descuento es obligatorio", txtTipo);
            return false;
        }
        if (txtPorcentaje.getText().trim().isEmpty()) {
            mostrarError("El porcentaje es obligatorio", txtPorcentaje);
            return false;
        }
        
        try {
            double porcentaje = Double.parseDouble(txtPorcentaje.getText());
            if (porcentaje <= 0) {
                mostrarError("El porcentaje debe ser mayor a 0", txtPorcentaje);
                return false;
            }
            if (porcentaje > 100) {
                mostrarError("El porcentaje no puede ser mayor a 100", txtPorcentaje);
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("El porcentaje debe ser un número válido", txtPorcentaje);
            return false;
        }
        
        return true;
    }

    private void mostrarError(String mensaje, JTextField campo) {
        JOptionPane.showMessageDialog(this, "❌ " + mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        campo.requestFocus();
        campo.selectAll();
    }

    private void limpiarCampos() {
        txtTipo.setText("");
        txtPorcentaje.setText("");
        idSeleccionado = -1;
        tabla.clearSelection();
    }
}