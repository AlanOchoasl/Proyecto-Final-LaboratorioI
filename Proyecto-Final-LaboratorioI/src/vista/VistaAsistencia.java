
package vista;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Asistencia;
import modelo.ClaseGym;
import modelo.Socio;
import persistencia.AsistenciaData;
import persistencia.ClaseGymData;
import persistencia.SocioData;

public class VistaAsistencia extends javax.swing.JInternalFrame {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter FORMATO_HORA =
            DateTimeFormatter.ofPattern("HH:mm");

    private AsistenciaData asistenciaData;
    private SocioData socioData;
    private ClaseGymData claseData;
    private Asistencia asistenciaCargada;
    private List<Asistencia> listaActual = new ArrayList<>();
    private List<Socio> sociosCombo = new ArrayList<>();
    private List<ClaseGym> clasesCombo = new ArrayList<>();
    private DefaultTableModel modeloTabla;

    public VistaAsistencia(Connection cn) {
        initComponents();

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Socio", "ClaseGym", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblClases.setModel(modeloTabla);

        asistenciaData = new AsistenciaData(cn);
        socioData = new SocioData(cn);
        claseData = new ClaseGymData(cn);

        txtId.setEditable(false);

        btnNuevo.addActionListener(this::btnNuevoActionPerformed);
        btnModificar.addActionListener(this::btnModificarActionPerformed);
        btnAsistencia.addActionListener(this::btnAsistenciaActionPerformed);
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        cargarSocios();
        cargarClases();

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
        txtId = new javax.swing.JTextField();
        lblSocio = new javax.swing.JLabel();
        cmbSocio = new javax.swing.JComboBox<>();
        lblClaseGym = new javax.swing.JLabel();
        cmbClaseGym = new javax.swing.JComboBox<>();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        btnAsistencia = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClases = new javax.swing.JTable();

        setTitle("Asistencia");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Asistencia de AboriGym");

        lblId.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblId.setText("ID:");

        lblSocio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSocio.setText("Socio:");

        cmbSocio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblClaseGym.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblClaseGym.setText("ClaseGym:");

        cmbClaseGym.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblFecha.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFecha.setText("Fecha:");

        btnAsistencia.setText("Registrar Asistencia");

        btnEliminar.setText("Eliminar");

        btnNuevo.setText("Nuevo");

        btnModificar.setText("Modificar");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Registro de Asistencias");

        tblClases.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Socio", "ClaseGym", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(lblId))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblClaseGym)
                                    .addComponent(lblSocio)
                                    .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(88, 88, 88)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitulo)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cmbClaseGym, javax.swing.GroupLayout.Alignment.LEADING, 0, 104, Short.MAX_VALUE)
                                        .addComponent(cmbSocio, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtFecha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                        .addComponent(txtId, javax.swing.GroupLayout.Alignment.LEADING)))
                                .addGap(0, 177, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAsistencia)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(149, 149, 149))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblId)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblSocio)
                            .addComponent(cmbSocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblClaseGym)
                            .addComponent(cmbClaseGym, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFecha)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAsistencia)
                            .addComponent(btnModificar)
                            .addComponent(btnEliminar)
                            .addComponent(btnNuevo)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(269, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargarSocios() {
        try {
            sociosCombo = socioData.listar();
            cmbSocio.removeAllItems();
            for (Socio s : sociosCombo) {
                String texto = s.getApellido() + ", " + s.getNombre()
                        + " (DNI " + s.getDni() + ")";
                if (!s.isEstado()) {
                    texto += " - Inactivo";
                }
                cmbSocio.addItem(texto);
            }
            if (cmbSocio.getItemCount() == 0) {
                cmbSocio.setSelectedIndex(-1);
            }
            actualizarBotones();
        } catch (SQLException ex) {
            error("No se pudo cargar la lista de socios.", ex);
        }
    }

    private void cargarClases() {
        try {
            clasesCombo = claseData.listar();
            cmbClaseGym.removeAllItems();
            for (ClaseGym c : clasesCombo) {
                String texto = c.getDisciplina() + " - " + c.getHorario().format(FORMATO_HORA);
                if (c.getEntrenador() != null) {
                    texto += " (" + c.getEntrenador().getApellido() + ", "
                            + c.getEntrenador().getNombre() + ")";
                }
                if (!c.isEstado()) {
                    texto += " - Inactiva";
                }
                cmbClaseGym.addItem(texto);
            }
            if (cmbClaseGym.getItemCount() == 0) {
                cmbClaseGym.setSelectedIndex(-1);
            }
            actualizarBotones();
        } catch (SQLException ex) {
            error("No se pudo cargar la lista de clases.", ex);
        }
    }

    private void cargarTabla() {
        try {
            List<Asistencia> todas = asistenciaData.listar();
            modeloTabla.setRowCount(0);
            listaActual = new ArrayList<>();
            for (Asistencia a : todas) {
                listaActual.add(a);
                modeloTabla.addRow(new Object[]{
                    a.getIdAsistencia(),
                    a.getSocio().getApellido() + ", " + a.getSocio().getNombre(),
                    a.getClase().getDisciplina() + " - "
                            + a.getClase().getHorario().format(FORMATO_HORA),
                    a.getFecha().format(FORMATO_FECHA)
                });
            }
        } catch (SQLException ex) {
            error("No se pudo cargar la lista de asistencias.", ex);
        }
    }

    private void mostrar(Asistencia a) {
        asistenciaCargada = a;
        txtId.setText(String.valueOf(a.getIdAsistencia()));
        seleccionarSocio(a.getSocio().getIdSocio());
        seleccionarClase(a.getClase().getIdClase());
        txtFecha.setText(a.getFecha().format(FORMATO_FECHA));
        actualizarBotones();
    }

    private void seleccionarSocio(int idSocio) {
        for (int i = 0; i < sociosCombo.size(); i++) {
            if (sociosCombo.get(i).getIdSocio() == idSocio) {
                cmbSocio.setSelectedIndex(i);
                return;
            }
        }
    }

    private void seleccionarClase(int idClase) {
        for (int i = 0; i < clasesCombo.size(); i++) {
            if (clasesCombo.get(i).getIdClase() == idClase) {
                cmbClaseGym.setSelectedIndex(i);
                return;
            }
        }
    }

    private void limpiar() {
        asistenciaCargada = null;
        txtId.setText("");

        if (cmbSocio.getItemCount() > 0) {
            cmbSocio.setSelectedIndex(0);
        } else {
            cmbSocio.setSelectedIndex(-1);
        }

        if (cmbClaseGym.getItemCount() > 0) {
            cmbClaseGym.setSelectedIndex(0);
        } else {
            cmbClaseGym.setSelectedIndex(-1);
        }

        txtFecha.setText(LocalDate.now().format(FORMATO_FECHA));
        tblClases.clearSelection();
        actualizarBotones();
        cmbSocio.requestFocusInWindow();
    }

    private void actualizarBotones() {
        boolean hay = asistenciaCargada != null;
        boolean datos = !sociosCombo.isEmpty() && !clasesCombo.isEmpty();
        btnAsistencia.setEnabled(!hay && datos);
        btnModificar.setEnabled(hay);
        btnEliminar.setEnabled(hay);
    }

    private Asistencia leerFormulario() {
        int iSocio = cmbSocio.getSelectedIndex();
        if (iSocio < 0 || iSocio >= sociosCombo.size()) {
            aviso("Seleccione un socio.", cmbSocio);
            return null;
        }

        int iClase = cmbClaseGym.getSelectedIndex();
        if (iClase < 0 || iClase >= clasesCombo.size()) {
            aviso("Seleccione una clase.", cmbClaseGym);
            return null;
        }

        String fechaTxt = txtFecha.getText().trim();
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaTxt, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            aviso("La fecha debe tener el formato dd/MM/yyyy.", txtFecha);
            return null;
        }

        return new Asistencia(sociosCombo.get(iSocio), clasesCombo.get(iClase), fecha);
    }

    private boolean existeDuplicado(Asistencia nueva, int idExcluir) throws SQLException {
        for (Asistencia a : asistenciaData.listar()) {
            if (a.getIdAsistencia() == idExcluir) {
                continue;
            }
            if (a.getSocio().getIdSocio() == nueva.getSocio().getIdSocio()
                    && a.getClase().getIdClase() == nueva.getClase().getIdClase()
                    && a.getFecha().equals(nueva.getFecha())) {
                return true;
            }
        }
        return false;
    }

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {
        limpiar();
    }

    private void btnAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {
        Asistencia nueva = leerFormulario();
        if (nueva == null) {
            return;
        }
        try {
            if (existeDuplicado(nueva, 0)) {
                aviso("Ya existe una asistencia para ese socio, clase y fecha.", cmbSocio);
                return;
            }
            if (asistenciaData.alta(nueva)) {
                info("Asistencia registrada correctamente. ID asignado: "
                        + nueva.getIdAsistencia());
                limpiar();
                cargarTabla();
            } else {
                aviso("No se pudo registrar la asistencia.", null);
            }
        } catch (SQLException ex) {
            error("No se pudo registrar la asistencia.", ex);
        }
    }

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {
        if (asistenciaCargada == null) {
            aviso("Seleccione una asistencia de la tabla.", null);
            return;
        }

        Asistencia modificada = leerFormulario();
        if (modificada == null) {
            return;
        }
        modificada.setIdAsistencia(asistenciaCargada.getIdAsistencia());

        try {
            if (existeDuplicado(modificada, modificada.getIdAsistencia())) {
                aviso("Ya existe otra asistencia para ese socio, clase y fecha.", cmbSocio);
                return;
            }
            if (asistenciaData.actualizar(modificada)) {
                info("Asistencia actualizada correctamente.");
                limpiar();
                cargarTabla();
            } else {
                aviso("No se pudo actualizar la asistencia.", null);
            }
        } catch (SQLException ex) {
            error("No se pudo actualizar la asistencia.", ex);
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        if (asistenciaCargada == null) {
            return;
        }
        if (!confirmar("¿Eliminar esta asistencia?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación")) {
            return;
        }
        try {
            if (asistenciaData.borrar(asistenciaCargada.getIdAsistencia())) {
                info("Asistencia eliminada.");
                limpiar();
                cargarTabla();
            } else {
                aviso("No se pudo eliminar la asistencia.", null);
            }
        } catch (SQLException ex) {
            error("No se pudo eliminar la asistencia.", ex);
        }
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
    private javax.swing.JButton btnAsistencia;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbClaseGym;
    private javax.swing.JComboBox<String> cmbSocio;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClaseGym;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblSocio;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblClases;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtId;
    // End of variables declaration//GEN-END:variables
}
