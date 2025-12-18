package main;

import vista.FrmDocente;
import vista.FrmEmpleados;
import vista.FrmDescuentos;
import javax.swing.*;
import java.awt.*;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Configurar colores
            Color colorPrincipal = new Color(41, 128, 185);
            Color colorSecundario = new Color(52, 152, 219);
            Color colorFondo = new Color(245, 245, 245);
            
            JFrame menu = new JFrame("🏠 Sistema de Gestión - Menú Principal");
            menu.setSize(500, 400);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setLocationRelativeTo(null);
            menu.setResizable(false);
            
            // Panel principal con fondo
            JPanel panelPrincipal = new JPanel(new BorderLayout());
            panelPrincipal.setBackground(colorFondo);
            
            // Panel de título
            JPanel panelTitulo = new JPanel();
            panelTitulo.setBackground(colorPrincipal);
            panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            JLabel lblTitulo = new JLabel("SISTEMA DE GESTIÓN ACADÉMICA");
            lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
            lblTitulo.setForeground(Color.WHITE);
            panelTitulo.add(lblTitulo);
            
            // Panel de botones
            JPanel panelBotones = new JPanel();
            panelBotones.setLayout(new GridLayout(3, 1, 0, 15));
            panelBotones.setBackground(Color.WHITE);
            panelBotones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(40, 50, 40, 50)
            ));
            
            // Crear botones con estilo
            JButton btnDocente = crearBotonMenu("👨‍🏫 Formulario Docente", new Color(155, 89, 182));
            JButton btnEmpleados = crearBotonMenu("🏢 CRUD Empleados", new Color(41, 128, 185));
            JButton btnDescuentos = crearBotonMenu("💰 CRUD Descuentos", new Color(230, 126, 34));
            
            // Agregar botones al panel
            panelBotones.add(btnDocente);
            panelBotones.add(btnEmpleados);
            panelBotones.add(btnDescuentos);
            
            // Panel de información
           
            // Ensamblar la interfaz
            panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
            panelPrincipal.add(panelBotones, BorderLayout.CENTER);
            
            menu.add(panelPrincipal);
            
            // Action Listeners
            btnDocente.addActionListener(e -> {
                new FrmDocente().setVisible(true);
            });
            
            btnEmpleados.addActionListener(e -> {
                new FrmEmpleados().setVisible(true);
            });
            
            btnDescuentos.addActionListener(e -> {
                new FrmDescuentos().setVisible(true);
            });
            
            menu.setVisible(true);
        });
    }
    
    private static JButton crearBotonMenu(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.brighter());
                boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color.darker(), 2),
                    BorderFactory.createEmptyBorder(12, 20, 12, 20)
                ));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
                boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color.darker(), 2),
                    BorderFactory.createEmptyBorder(12, 20, 12, 20)
                ));
            }
        });
        
        return boton;
    }
}