package app;

import config.UIConfig;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class DialogoAgregarProducto extends JDialog {
    private final JTextField campoID;
    private final JTextField campoNombre;
    private final JComboBox<String> comboCategoria;
    private final JSpinner spinnerPrecio;
    private final JLabel etiquetaError;
    private final DefaultTableModel modeloTabla;
    private final NumberFormat formatoMoneda;

    public DialogoAgregarProducto(JFrame ventanaPadre, DefaultTableModel modeloTabla) {
        // 'true' -> dialogo modal: bloquea la ventana padre hasta cerrar este formulario.
        super(ventanaPadre, "Agregar producto", true);
        this.modeloTabla = modeloTabla;
        this.formatoMoneda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-ES"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(new EmptyBorder(12, 12, 12, 12));
        panelFormulario.setBackground(UIConfig.COLOR_PANEL_BLANCO);

        GridBagConstraints gbc = new GridBagConstraints();
        // Reutilizamos el mismo objeto gbc cambiando fila/columna en cada bloque.
        // Es el patron habitual cuando se trabaja con GridBagLayout.
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("ID:"), gbc);
        campoID = new JTextField();
        campoID.setColumns(12);
        gbc.gridx = 1;
        panelFormulario.add(campoID, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Nombre del producto:"), gbc);
        campoNombre = new JTextField();
        campoNombre.setColumns(20);
        gbc.gridx = 1;
        panelFormulario.add(campoNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Categoria:"), gbc);
        comboCategoria = new JComboBox<>(new String[] {
                "Informatica", "Perifericos", "Almacenamiento", "Accesorios"
        });
        gbc.gridx = 1;
        panelFormulario.add(comboCategoria, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Precio (EUR):"), gbc);
        // SpinnerNumberModel: valor inicial, minimo, maximo y paso.
        spinnerPrecio = new JSpinner(new SpinnerNumberModel(29.99, 0.0, 10000.0, 0.5));
        JSpinner.NumberEditor editorPrecio = new JSpinner.NumberEditor(spinnerPrecio, "#,##0.00");
        spinnerPrecio.setEditor(editorPrecio);
        gbc.gridx = 1;
        panelFormulario.add(spinnerPrecio, gbc);

        etiquetaError = new JLabel(" ");
        etiquetaError.setForeground(new Color(176, 45, 45));
        etiquetaError.setFont(UIConfig.FUENTE_BASE.deriveFont(Font.BOLD));

        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.setBackground(UIConfig.COLOR_AZUL_PRIMARIO);
        botonGuardar.setForeground(Color.WHITE);
        botonGuardar.setFocusPainted(false);
        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFocusPainted(false);
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(botonCancelar);
        panelBotones.add(botonGuardar);

        JPanel panelInferior = new JPanel(new BorderLayout(0, 8));
        panelInferior.setBorder(new EmptyBorder(0, 12, 12, 12));
        panelInferior.setOpaque(false);
        panelInferior.add(etiquetaError, BorderLayout.NORTH);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        add(panelFormulario, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        // Pulsar Enter en cualquier campo dispara el boton guardar.
        getRootPane().setDefaultButton(botonGuardar);
        registrarAtajosTeclado();
        pack();
        setResizable(false);
        setLocationRelativeTo(ventanaPadre);
    }

    private void agregarProducto() {
        // Leemos valores de cada componente del formulario.
        String id = campoID.getText().trim();
        String nombre = campoNombre.getText().trim();
        String categoria = String.valueOf(comboCategoria.getSelectedItem());
        double precio = ((Number) spinnerPrecio.getValue()).doubleValue();

        // Validacion minima antes de tocar el modelo.
        if (id.isEmpty() || nombre.isEmpty()) {
            mostrarError("El ID y el nombre son obligatorios.");
            return;
        }

        if (idYaExiste(id)) {
            mostrarError("Ese ID ya existe. Usa otro identificador.");
            return;
        }

        String precioFormateado = formatoMoneda.format(precio);
        modeloTabla.addRow(new Object[] { id, nombre, categoria, precioFormateado });
        dispose();
    }

    private boolean idYaExiste(String nuevoID) {
        // Recorremos la columna 0 (ID) para evitar duplicados en la tabla.
        for (int fila = 0; fila < modeloTabla.getRowCount(); fila++) {
            String idActual = String.valueOf(modeloTabla.getValueAt(fila, 0)).trim();
            // equalsIgnoreCase evita conflictos por mayusculas/minusculas.
            if (idActual.equalsIgnoreCase(nuevoID)) {
                return true;
            }
        }
        return false;
    }

    private void mostrarError(String mensaje) {
        etiquetaError.setText(mensaje);
    }

    private void registrarAtajosTeclado() {
        // Buen patron UX: permitir cerrar dialogos con Escape.
        getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke("ESCAPE"), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
}
