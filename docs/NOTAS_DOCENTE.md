# Notas Docente (Interno)

Documento interno para preparar clase. No es necesario para el alumnado.

## Puntos clave para explicar en aula

- `JTable` muestra datos, pero el estado vive en `DefaultTableModel`.
- Con tabla ordenada, hay que convertir índice de vista a índice de modelo con `convertRowIndexToModel(...)`.
- Swing trabaja en un único hilo gráfico (EDT).
- `SwingUtilities.invokeLater(...)` evita problemas de sincronización al refrescar UI.
- Validar antes de añadir al modelo evita estados inconsistentes.
- `UIManager` permite estilado global sin repetir código en cada componente.

## Posibles mejoras para siguientes sesiones

1. Añadir edición de producto con un `JDialog` de modificación.
2. Persistencia en CSV (guardar/cargar datos).
3. Filtro por categoría (`TableRowSorter` + `RowFilter`).
4. Validación en tiempo real con `DocumentListener`.
5. Refactor a estructura Vista + Controlador (paso previo a MVC).

## Guion breve de explicación (10-15 min)

1. Mostrar la app funcionando (alta, baja, limpieza, ordenación).
2. Señalar diferencia entre **vista** (`JTable`) y **modelo** (`DefaultTableModel`).
3. Explicar evento de botón (`ActionListener`) en alta y eliminación.
4. Explicar por qué convertimos índices de vista a modelo.
5. Mostrar validación del formulario y control de ID duplicado.
6. Cerrar con propuesta de ejercicio guiado (añadir botón Editar).

## Checklist antes de clase

- Compila con:
  - `javac -d bin src/config/UIConfig.java src/app/DialogoAgregarProducto.java src/app/EjemploJTableAddDelete.java`
- Ejecuta con:
  - `java -cp bin app.EjemploJTableAddDelete`
- Revisar que `docs/preview.png` se muestra en GitHub.
- Preparar ejercicio extra según ritmo del grupo.
