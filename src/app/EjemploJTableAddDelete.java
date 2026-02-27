package app;

import config.UIConfig;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EjemploJTableAddDelete extends JFrame {
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;
    private final JLabel etiquetaTotal;
    private final JLabel etiquetaSeleccion;

    public EjemploJTableAddDelete() {
        super("UT5.2 - Gestion de Productos con JTable");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(880, 560));

        // Orden recomendado en Swing: primero datos (modelo), luego componentes (vista).
        modeloTabla = crearModeloTabla();
        tabla = crearTabla();
        etiquetaTotal = new JLabel();
        etiquetaSeleccion = new JLabel();

        setContentPane(crearContenidoPrincipal());
        actualizarResumen();
        pack();
        setSize(920, 580);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // En Swing, toda la interfaz debe crearse en el hilo de eventos (EDT).
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UIConfig.aplicarEstilos();
                new EjemploJTableAddDelete().setVisible(true);
            }
        });
    }

    private DefaultTableModel crearModeloTabla() {
        // El modelo almacena los datos y se conecta con JTable.
        return new DefaultTableModel(
                new Object[][] {
                        { "P001", "Portatil 15\"", "Informatica", "849,99 €" },
                        { "P002", "Raton inalambrico", "Perifericos", "24,95 €" },
                        { "P003", "Teclado mecanico", "Perifericos", "69,90 €" },
                        { "P004", "Monitor 27\"", "Informatica", "219,00 €" },
                        { "P005", "Pendrive 128GB", "Almacenamiento", "17,50 €" }
                },
                new String[] { "ID", "Producto", "Categoria", "Precio" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Mantener la tabla no editable ayuda a centrarse en los eventos de alta/baja.
                return false;
            }
        };
    }

    private JTable crearTabla() {
        JTable tablaLocal = new JTable(modeloTabla);
        tablaLocal.setRowHeight(30);
        tablaLocal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Permite ordenar por columnas desde el encabezado sin escribir codigo extra.
        tablaLocal.setAutoCreateRowSorter(true);
        tablaLocal.getTableHeader().setReorderingAllowed(false);
        tablaLocal.setGridColor(new Color(225, 232, 238));

        // Renderers para alinear columnas y colorear filas alternas.
        tablaLocal.setDefaultRenderer(Object.class, crearRendererConAlineacion(SwingConstants.LEFT));
        tablaLocal.getColumnModel().getColumn(0).setCellRenderer(crearRendererConAlineacion(SwingConstants.CENTER));
        tablaLocal.getColumnModel().getColumn(3).setCellRenderer(crearRendererConAlineacion(SwingConstants.RIGHT));

        // Escuchamos seleccion y cambios de datos para actualizar el panel resumen.
        tablaLocal.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Swing lanza varios eventos de seleccion durante el arrastre.
                // Filtramos el estado intermedio para evitar refrescos duplicados.
                if (!e.getValueIsAdjusting()) {
                    actualizarResumen();
                }
            }
        });
        modeloTabla.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // Tras alta/baja, JTable y RowSorter pueden estar actualizando indices.
                // Diferir 1 ciclo de EDT evita leer seleccion en estado intermedio.
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        actualizarResumen();
                    }
                });
            }
        });

        return tablaLocal;
    }

    private DefaultTableCellRenderer crearRendererConAlineacion(final int alineacion) {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                setHorizontalAlignment(alineacion);

                if (!isSelected) {
                    componente.setBackground(row % 2 == 0 ? Color.WHITE : UIConfig.COLOR_FILA_ALTERNA);
                    componente.setForeground(UIConfig.COLOR_TEXTO_PRINCIPAL);
                }

                if (componente instanceof JComponent) {
                    ((JComponent) componente).setBorder(new EmptyBorder(0, 8, 0, 8));
                }
                return componente;
            }
        };
    }

    private JPanel crearContenidoPrincipal() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(12, 12));
        panelPrincipal.setBorder(new EmptyBorder(16, 16, 16, 16));
        panelPrincipal.setBackground(UIConfig.COLOR_FONDO_PRINCIPAL);

        panelPrincipal.add(crearCabecera(), BorderLayout.NORTH);
        panelPrincipal.add(crearPanelTabla(), BorderLayout.CENTER);
        panelPrincipal.add(crearPanelInferior(), BorderLayout.SOUTH);
        return panelPrincipal;
    }

    private JPanel crearCabecera() {
        JPanel panelCabecera = new JPanel(new BorderLayout(0, 4));
        panelCabecera.setOpaque(false);

        JLabel titulo = new JLabel("UT5.2 - Interfaces Graficas con Java Swing");
        titulo.setFont(UIConfig.FUENTE_TITULO);
        titulo.setForeground(UIConfig.COLOR_TEXTO_PRINCIPAL);

        JLabel subtitulo = new JLabel("JTable + eventos + dialogos + modelo de datos");
        subtitulo.setForeground(new Color(89, 97, 107));

        panelCabecera.add(titulo, BorderLayout.NORTH);
        panelCabecera.add(subtitulo, BorderLayout.SOUTH);
        return panelCabecera;
    }

    private JPanel crearPanelTabla() {
        JPanel panelTabla = new JPanel(new BorderLayout(8, 8));
        panelTabla.setBackground(UIConfig.COLOR_PANEL_BLANCO);
        panelTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(212, 221, 230)),
                new EmptyBorder(12, 12, 12, 12)));

        JLabel ayuda = new JLabel("Tip: puedes ordenar pulsando sobre el encabezado de cada columna.");
        ayuda.setForeground(new Color(98, 105, 115));

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());

        panelTabla.add(ayuda, BorderLayout.NORTH);
        panelTabla.add(scrollTabla, BorderLayout.CENTER);
        return panelTabla;
    }

    private JPanel crearPanelInferior() {
        JPanel panelInferior = new JPanel(new BorderLayout(12, 6));
        panelInferior.setOpaque(false);
        panelInferior.add(crearPanelBotones(), BorderLayout.WEST);
        panelInferior.add(crearPanelResumen(), BorderLayout.EAST);
        return panelInferior;
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelBotones.setOpaque(false);

        JButton botonAgregar = crearBotonPrincipal("Agregar producto");
        botonAgregar.setToolTipText("Abre un dialogo para anadir un producto nuevo.");
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDialogoAlta();
            }
        });

        JButton botonEliminar = crearBotonPrincipal("Eliminar seleccionado");
        botonEliminar.setToolTipText("Elimina la fila seleccionada en la tabla.");
        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarFilaSeleccionada();
            }
        });

        JButton botonLimpiar = crearBotonSecundario("Limpiar tabla");
        botonLimpiar.setToolTipText("Borra todas las filas de la tabla.");
        botonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarTabla();
            }
        });

        panelBotones.add(botonAgregar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonLimpiar);
        return panelBotones;
    }

    private JPanel crearPanelResumen() {
        JPanel panelResumen = new JPanel(new GridLayout(2, 1, 0, 4));
        panelResumen.setOpaque(false);

        etiquetaTotal.setFont(UIConfig.FUENTE_BASE.deriveFont(Font.BOLD));
        etiquetaTotal.setForeground(UIConfig.COLOR_TEXTO_PRINCIPAL);

        etiquetaSeleccion.setForeground(UIConfig.COLOR_EXITO);

        panelResumen.add(etiquetaTotal);
        panelResumen.add(etiquetaSeleccion);
        return panelResumen;
    }

    private JButton crearBotonPrincipal(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(UIConfig.COLOR_AZUL_PRIMARIO);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        return boton;
    }

    private JButton crearBotonSecundario(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(234, 238, 243));
        boton.setForeground(UIConfig.COLOR_TEXTO_PRINCIPAL);
        boton.setFocusPainted(false);
        return boton;
    }

    private void abrirDialogoAlta() {
        DialogoAgregarProducto dialogo = new DialogoAgregarProducto(this, modeloTabla);
        dialogo.setVisible(true);
        actualizarResumen();
    }

    private void eliminarFilaSeleccionada() {
        int filaVista = tabla.getSelectedRow();
        if (filaVista == -1 || filaVista >= tabla.getRowCount()) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // IMPORTANTE: con RowSorter activo, el indice visible NO coincide siempre con el del modelo.
        int filaModelo = tabla.convertRowIndexToModel(filaVista);
        String nombreProducto = String.valueOf(modeloTabla.getValueAt(filaModelo, 1));
        int opcion = JOptionPane.showConfirmDialog(this,
                "Se eliminara \"" + nombreProducto + "\".\nQuieres continuar?",
                "Confirmar eliminacion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            modeloTabla.removeRow(filaModelo);
            reajustarSeleccionTrasEliminacion(filaVista);
        }
    }

    private void limpiarTabla() {
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "La tabla ya esta vacia.", "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this,
                "Se eliminaran todos los productos de la tabla.\nQuieres continuar?",
                "Confirmar limpieza",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (opcion == JOptionPane.YES_OPTION) {
            modeloTabla.setRowCount(0);
        }
    }

    private void actualizarResumen() {
        etiquetaTotal.setText("Total de productos: " + modeloTabla.getRowCount());

        int filaVista = tabla.getSelectedRow();
        if (filaVista < 0 || filaVista >= tabla.getRowCount()) {
            etiquetaSeleccion.setText("Seleccion actual: ninguna");
            return;
        }

        int filaModelo;
        try {
            // Conversion vista -> modelo para leer datos correctos tras ordenar.
            filaModelo = tabla.convertRowIndexToModel(filaVista);
        } catch (IndexOutOfBoundsException ex) {
            etiquetaSeleccion.setText("Seleccion actual: ninguna");
            return;
        }

        if (filaModelo < 0 || filaModelo >= modeloTabla.getRowCount()) {
            etiquetaSeleccion.setText("Seleccion actual: ninguna");
            return;
        }

        String productoSeleccionado = String.valueOf(modeloTabla.getValueAt(filaModelo, 1));
        etiquetaSeleccion.setText("Seleccion actual: " + productoSeleccionado);
    }

    private void reajustarSeleccionTrasEliminacion(final int filaVistaEliminada) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Tras borrar, intentamos dejar seleccionada la fila "siguiente"
                // para mantener una experiencia fluida al usuario.
                int totalFilas = tabla.getRowCount();
                if (totalFilas <= 0) {
                    tabla.clearSelection();
                    return;
                }

                int nuevaFilaVista = Math.min(filaVistaEliminada, totalFilas - 1);
                tabla.setRowSelectionInterval(nuevaFilaVista, nuevaFilaVista);
            }
        });
    }
}
