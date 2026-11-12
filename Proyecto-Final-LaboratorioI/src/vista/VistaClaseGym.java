
package vista;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ClaseGym;
import modelo.Entrenador;
import persistencia.ClaseGymData;
import persistencia.EntrenadorData;

public class VistaClaseGym extends javax.swing.JInternalFrame {

    private static final Pattern DISCIPLINA = Pattern.compile("[\\p{L}][\\p{L}\\p{N} .'-]*");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private ClaseGymData claseData;
    private EntrenadorData entrenadorData;
    private ClaseGym claseCargada;
    private List<ClaseGym> listaActual = new ArrayList<>();
    private List<Entrenador> entrenadoresCombo = new ArrayList<>();
    private DefaultTableModel modeloTabla;

    public VistaClaseGym(Connection cn) {
        initComponents();

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Disciplina", "Entrenador", "Horario", "Capacidad", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblClases.setModel(modeloTabla);

        claseData = new ClaseGymData(cn);
        entrenadorData = new EntrenadorData(cn);

        txtId.setEditable(false);

        ButtonGroup grupoEstado = new ButtonGroup();
        grupoEstado.add(rbActivo);
        grupoEstado.add(rbInactivo);
        rbActivo.setSelected(true);

        cargarEntrenadores();

        tblClases.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tblClases.getSelectedRow();
                if (fila >= 0 && fila < listaActual.size()) {
                    mostrar(listaActual.get(fila));
                }
            }
        });

        cargarTabla();
        limpiar();
    }

         
  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        lblDisciplina = new javax.swing.JLabel();
        lblEntrenador = new javax.swing.JLabel();
        lblHorario = new javax.swing.JLabel();
        lblCapacidad = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtDisciplina = new javax.swing.JTextField();
        cmbEntrenador = new javax.swing.JComboBox<>();
        txtHorario = new javax.swing.JTextField();
        txtCapacidad = new javax.swing.JTextField();
        rbActivo = new javax.swing.JRadioButton();
        rbInactivo = new javax.swing.JRadioButton();
        lblMostrar = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        cmbMostrar = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClases = new javax.swing.JTable();

        setTitle("ClaseGym");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Clases AboriGym");

        lblId.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblId.setText("ID:");

        lblDisciplina.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDisciplina.setText("Disciplina:");

        lblEntrenador.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEntrenador.setText("Entrenador:");

        lblHorario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblHorario.setText("Horario:");

        lblCapacidad.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCapacidad.setText("Capacidad:");

        lblEstado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEstado.setText("Estado:");

        cmbEntrenador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtCapacidad.addActionListener(this::txtCapacidadActionPerformed);

        rbActivo.setText("Activo");

        rbInactivo.setText("Inactivo");

        lblMostrar.setText("Mostrar:");

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        cmbMostrar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Activo", "Inactivo" }));
        cmbMostrar.addActionListener(this::cmbMostrarActionPerformed);

        tblClases.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Disciplina", "Entrenador", "Horario", "Capacidad", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblClases);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDisciplina, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDisciplina, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(txtId)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(rbInactivo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblHorario)
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblEntrenador)))
                        .addGap(18, 18, 18)
                        .addComponent(cmbEntrenador, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmbMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(557, 557, 557))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(258, 258, 258)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblEstado)
                                .addGap(26, 26, 26)
                                .addComponent(rbActivo))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblCapacidad)
                                .addGap(18, 18, 18)
                                .addComponent(txtCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblId)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(lblTitulo)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMostrar)
                            .addComponent(cmbMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDisciplina)
                            .addComponent(txtDisciplina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEntrenador)
                            .addComponent(cmbEntrenador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHorario)
                            .addComponent(txtHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCapacidad)
                            .addComponent(txtCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEstado)
                            .addComponent(rbActivo)
                            .addComponent(rbInactivo))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevo)
                            .addComponent(btnGuardar)
                            .addComponent(btnActualizar)
                            .addComponent(btnEliminar)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(210, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCapacidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCapacidadActionPerformed
    }//GEN-LAST:event_txtCapacidadActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        limpiar();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        ClaseGym nueva = leerFormulario();
        if (nueva == null) {
            return;
        }
        try {
            if (claseData.alta(nueva)) {
                info("Clase guardada correctamente. ID asignado: " + nueva.getIdClase());
                limpiar();
                cargarTabla();
            } else {
                aviso("No se pudo guardar la clase.", null);
            }
        } catch (SQLException ex) {
            error("No se pudo guardar la clase.", ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (claseCargada == null) {
            aviso("Seleccione una clase de la tabla.", null);
            return;
        }

        ClaseGym modificada = leerFormulario();
        if (modificada == null) {
            return;
        }
        modificada.setIdClase(claseCargada.getIdClase());

        try {
            if (claseData.actualizar(modificada)) {
                info("Clase actualizada correctamente.");
                limpiar();
                cargarTabla();
            } else {
                aviso("No se pudo actualizar la clase.", null);
            }
        } catch (SQLException ex) {
            error("No se pudo actualizar la clase.", ex);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (claseCargada == null) {
            aviso("Seleccione una clase de la tabla.", null);
            return;
        }
        if (!confirmar("¿Eliminar esta clase?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación")) {
            return;
        }
        try {
            if (claseData.borrar(claseCargada.getIdClase())) {
                info("Clase eliminada.");
                limpiar();
                cargarTabla();
            } else {
                aviso("No se pudo eliminar la clase.\n"
                        + "Puede tener asistencias asociadas.", null);
            }
        } catch (SQLException ex) {
            error("No se pudo eliminar la clase.", ex);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void cmbMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMostrarActionPerformed
        cargarTabla();
        limpiar();
    }//GEN-LAST:event_cmbMostrarActionPerformed

    private void cargarEntrenadores() {
        try {
            entrenadoresCombo = entrenadorData.listar();
            cmbEntrenador.removeAllItems();
            for (Entrenador e : entrenadoresCombo) {
                String texto = e.getApellido() + ", " + e.getNombre()
                        + " (" + e.getEspecialidad() + ")";
                if (!e.isEstado()) {
                    texto += " - Inactivo";
                }
                cmbEntrenador.addItem(texto);
            }
            if (cmbEntrenador.getItemCount() == 0) {
                cmbEntrenador.setSelectedIndex(-1);
            }
        } catch (SQLException ex) {
            error("No se pudo cargar la lista de entrenadores.", ex);
        }
    }

    private void cargarTabla() {
        try {
            int filtro = cmbMostrar.getSelectedIndex();
            List<ClaseGym> clases;
            if (filtro == 1) {
                clases = claseData.listarActivos();
            } else if (filtro == 2) {
                clases = claseData.listar();
                clases.removeIf(ClaseGym::isEstado);
            } else {
                clases = claseData.listar();
            }

            modeloTabla.setRowCount(0);
            listaActual = new ArrayList<>();
            for (ClaseGym c : clases) {
                listaActual.add(c);
                String entrenador = "";
                if (c.getEntrenador() != null) {
                    entrenador = c.getEntrenador().getApellido() + ", "
                            + c.getEntrenador().getNombre();
                }
                modeloTabla.addRow(new Object[]{
                    c.getIdClase(),
                    c.getDisciplina(),
                    entrenador,
                    c.getHorario().format(FORMATO_HORA),
                    c.getCapacidad(),
                    c.isEstado() ? "Activo" : "Inactivo"
                });
            }
        } catch (SQLException ex) {
            error("No se pudo cargar la lista de clases.", ex);
        }
    }

    private void mostrar(ClaseGym c) {
        claseCargada = c;
        txtId.setText(String.valueOf(c.getIdClase()));
        txtDisciplina.setText(c.getDisciplina());
        seleccionarEntrenador(c.getEntrenador() == null ? 0 : c.getEntrenador().getIdEntrenador());
        txtHorario.setText(c.getHorario().format(FORMATO_HORA));
        txtCapacidad.setText(String.valueOf(c.getCapacidad()));
        rbActivo.setSelected(c.isEstado());
        rbInactivo.setSelected(!c.isEstado());
        actualizarBotones();
    }

    private void seleccionarEntrenador(int idEntrenador) {
        for (int i = 0; i < entrenadoresCombo.size(); i++) {
            if (entrenadoresCombo.get(i).getIdEntrenador() == idEntrenador) {
                cmbEntrenador.setSelectedIndex(i);
                return;
            }
        }
    }

    private void limpiar() {
        claseCargada = null;
        txtId.setText("");
        txtDisciplina.setText("");
        if (cmbEntrenador.getItemCount() > 0) {
            cmbEntrenador.setSelectedIndex(0);
        } else {
            cmbEntrenador.setSelectedIndex(-1);
        }
        txtHorario.setText("");
        txtCapacidad.setText("");
        rbActivo.setSelected(true);
        tblClases.clearSelection();
        actualizarBotones();
        txtDisciplina.requestFocusInWindow();
    }

    private void actualizarBotones() {
        boolean hay = claseCargada != null;
        btnGuardar.setEnabled(!hay);
        btnActualizar.setEnabled(hay);
        btnEliminar.setEnabled(hay);
    }

    private ClaseGym leerFormulario() {
        String disciplina = txtDisciplina.getText().trim();
        if (disciplina.isEmpty() || !DISCIPLINA.matcher(disciplina).matches()) {
            aviso("Ingrese una disciplina válida (letras, números, espacios, puntos, guiones o apóstrofes).",
                    txtDisciplina);
            return null;
        }

        int iEntrenador = cmbEntrenador.getSelectedIndex();
        if (iEntrenador < 0 || iEntrenador >= entrenadoresCombo.size()) {
            aviso("Seleccione un entrenador.", cmbEntrenador);
            return null;
        }

        String horarioTxt = txtHorario.getText().trim();
        LocalTime horario;
        try {
            horario = LocalTime.parse(horarioTxt, FORMATO_HORA);
        } catch (DateTimeParseException e) {
            aviso("El horario debe tener el formato HH:mm (por ejemplo 18:30).", txtHorario);
            return null;
        }

        String capacidadTxt = txtCapacidad.getText().trim();
        int capacidad;
        try {
            capacidad = Integer.parseInt(capacidadTxt);
        } catch (NumberFormatException e) {
            aviso("La capacidad debe ser un número entero.", txtCapacidad);
            return null;
        }
        if (capacidad <= 0) {
            aviso("La capacidad debe ser mayor que cero.", txtCapacidad);
            return null;
        }

        boolean estado = !rbInactivo.isSelected();
        return new ClaseGym(disciplina, entrenadoresCombo.get(iEntrenador),
                horario, capacidad, estado);
    }

    private void aviso(String mensaje, JComponent foco) {
        JOptionPane.showMessageDialog(this, mensaje, "Atención", JOptionPane.WARNING_MESSAGE);
        if (foco != null) {
            foco.requestFocusInWindow();
        }
    }

    private void info(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "AboriGym", JOptionPane.INFORMATION_MESSAGE);
    }

    private void error(String mensaje, SQLException ex) {
        JOptionPane.showMessageDialog(this, mensaje + "\n" + ex.getMessage(),
                "Error de base de datos", JOptionPane.ERROR_MESSAGE);
    }

    private boolean confirmar(String mensaje, String titulo) {
        return JOptionPane.showConfirmDialog(this, mensaje, titulo,
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbEntrenador;
    private javax.swing.JComboBox<String> cmbMostrar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCapacidad;
    private javax.swing.JLabel lblDisciplina;
    private javax.swing.JLabel lblEntrenador;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblHorario;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblMostrar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JRadioButton rbActivo;
    private javax.swing.JRadioButton rbInactivo;
    private javax.swing.JTable tblClases;
    private javax.swing.JTextField txtCapacidad;
    private javax.swing.JTextField txtDisciplina;
    private javax.swing.JTextField txtHorario;
    private javax.swing.JTextField txtId;
    // End of variables declaration//GEN-END:variables
}
