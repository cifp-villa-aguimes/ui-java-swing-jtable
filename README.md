# JTable + Eventos en Java Swing

<p align="center">
  <strong>Proyecto didáctico para 1º DAW / 1º DAM</strong><br/>
  <em>UT5.2 - Interfaces gráficas con Java Swing</em>
</p>

<p align="center">
  <img alt="Java" src="https://img.shields.io/badge/Java-11%2B-007396?logo=openjdk&logoColor=white">
  <img alt="Swing" src="https://img.shields.io/badge/UI-Java%20Swing-1f6eb3">
  <img alt="Nivel" src="https://img.shields.io/badge/Nivel-Inicial%20(POO%20aplicada)-2ea44f">
  <img alt="Licencia educativa" src="https://img.shields.io/badge/Uso-Educativo-8250df">
</p>

> Ejemplo visual, comentado y pensado para alumnado que se enfrenta por primera vez a eventos y componentes Swing reales.

<p align="center">
  <img src="docs/preview.png" alt="Preview de la aplicacion Swing JTable" width="100%">
</p>

---

## Contenido

- [Objetivo didáctico](#objetivo-didáctico)
- [Qué se practica](#qué-se-practica)
- [Vista general del proyecto](#vista-general-del-proyecto)
- [Ejecución](#ejecución)
- [Flujo funcional en clase](#flujo-funcional-en-clase)
- [Problemas comunes](#problemas-comunes)
- [Autoría y licencia](#autoría-y-licencia)

---

## Objetivo didáctico

Este proyecto ayuda a pasar de ejemplos pequeños a una miniaplicación GUI con estructura clara:

- separación entre **modelo de datos** y **vista**
- gestión de **eventos de usuario**
- uso de diálogo modal para **alta de registros**
- operaciones CRUD básicas sobre `JTable` (alta y baja)

---

## Qué se practica

| Bloque | Conceptos |
|---|---|
| Estructura GUI | `JFrame`, `JPanel`, layouts (`BorderLayout`, `FlowLayout`, `GridBagLayout`) |
| Tabla de datos | `JTable`, `DefaultTableModel`, `RowSorter` |
| Eventos | `ActionListener`, `ListSelectionListener`, `TableModelListener` |
| Formularios | `JDialog`, `JTextField`, `JComboBox`, `JSpinner`, validación |
| Estilo | `UIManager`, Look & Feel Nimbus, paleta visual reutilizable |

---

## Vista general del proyecto

```text
src/
  app/
    EjemploJTableAddDelete.java    -> Ventana principal, tabla y eventos
    DialogoAgregarProducto.java    -> Formulario modal para alta de productos
  config/
    UIConfig.java                  -> Configuración visual global
```

### Funcionalidades incluidas

- Alta de productos desde diálogo modal
- Eliminación de fila seleccionada con confirmación
- Limpieza completa de tabla con confirmación
- Ordenación por columnas en cabecera
- Resumen inferior (total + elemento seleccionado)
- Validación de campos e ID no duplicado

---

## Ejecución

### Requisitos

- JDK 11 o superior

### Compilar

```bash
javac -d bin src/config/UIConfig.java src/app/DialogoAgregarProducto.java src/app/EjemploJTableAddDelete.java
```

### Ejecutar

```bash
java -cp bin app.EjemploJTableAddDelete
```

### Compilar y ejecutar (rápido)

```bash
javac -d bin src/config/UIConfig.java src/app/DialogoAgregarProducto.java src/app/EjemploJTableAddDelete.java && java -cp bin app.EjemploJTableAddDelete
```

---

## Flujo funcional en clase

1. La app arranca en el **EDT** con `SwingUtilities.invokeLater(...)`.
2. Se crea el `DefaultTableModel` con datos de ejemplo.
3. La `JTable` muestra datos y permite ordenación por columnas.
4. El alumnado interactúa con botones para:
   - agregar productos
   - eliminar selección
   - limpiar tabla
5. El resumen se actualiza automáticamente cuando cambian selección o datos.

---

## Problemas comunes

- `javac: file not found`: asegúrate de ejecutar los comandos desde la carpeta raíz del proyecto.
- La aplicación no arranca: verifica que tienes `JDK 11+` y que `java -version` funciona en tu terminal.
- Cambios visuales no se ven: recompila y vuelve a ejecutar para regenerar los `.class` en `bin/`.

---

## Autoría y licencia

- Material preparado para uso docente en 1º DAW / 1º DAM.
- Uso educativo y adaptación permitida para clase.
