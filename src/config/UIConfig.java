package config;

import javax.swing.*;
import java.awt.*;

public final class UIConfig {
    // Constantes visuales centralizadas: facilitan coherencia y mantenimiento.
    public static final Color COLOR_FONDO_PRINCIPAL = new Color(244, 248, 252);
    public static final Color COLOR_PANEL_BLANCO = Color.WHITE;
    public static final Color COLOR_AZUL_PRIMARIO = new Color(30, 110, 184);
    public static final Color COLOR_AZUL_SECUNDARIO = new Color(64, 145, 220);
    public static final Color COLOR_TEXTO_PRINCIPAL = new Color(33, 37, 43);
    public static final Color COLOR_FILA_ALTERNA = new Color(245, 250, 255);
    public static final Color COLOR_EXITO = new Color(34, 139, 34);
    public static final Font FUENTE_BASE = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FUENTE_TITULO = new Font("SansSerif", Font.BOLD, 22);

    private UIConfig() {
        // Clase de utilidades: no se debe instanciar.
    }

    public static void aplicarEstilos() {
        // Se aplica una sola vez al arrancar la app.
        aplicarLookAndFeelNimbus();
        aplicarUIManagerGlobal();
    }

    private static void aplicarLookAndFeelNimbus() {
        try {
            // Buscamos Nimbus por nombre para no depender de rutas hardcodeadas.
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo aplicar Nimbus. Se usara estilo por defecto.");
        }
    }

    private static void aplicarUIManagerGlobal() {
        // Estos valores actuan como estilo global para componentes Swing creados despues.
        UIManager.put("control", COLOR_FONDO_PRINCIPAL);
        UIManager.put("info", COLOR_FONDO_PRINCIPAL);
        UIManager.put("nimbusBase", COLOR_AZUL_PRIMARIO);
        UIManager.put("nimbusFocus", COLOR_AZUL_SECUNDARIO);

        UIManager.put("Label.font", FUENTE_BASE);
        UIManager.put("Button.font", FUENTE_BASE.deriveFont(Font.BOLD));
        UIManager.put("Table.font", FUENTE_BASE);
        UIManager.put("TableHeader.font", FUENTE_BASE.deriveFont(Font.BOLD));
        UIManager.put("TextField.font", FUENTE_BASE);
        UIManager.put("ComboBox.font", FUENTE_BASE);
        UIManager.put("Spinner.font", FUENTE_BASE);

        UIManager.put("Table.background", Color.WHITE);
        UIManager.put("Table.alternateRowColor", COLOR_FILA_ALTERNA);
        UIManager.put("Table.selectionBackground", COLOR_AZUL_PRIMARIO);
        UIManager.put("Table.selectionForeground", Color.WHITE);
    }
}
