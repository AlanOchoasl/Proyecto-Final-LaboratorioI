package vista;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Pase;
import modelo.Socio;
import modelo.ReglaNegocioException;
import persistencia.PaseData;
import persistencia.SocioData;

public class VistaPase extends javax.swing.JInternalFrame {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private PaseData paseData;
    private SocioData socioData;
    private Pase paseCargado;
    private List<Pase> listaActual = new ArrayList<>();
    private List<Socio> sociosCombo = new ArrayList<>();
    private DefaultTableModel modeloTabla;

    public VistaPase(Connection cn) {
        initComponents();
        txtFin.setEditable(false);
        btnEliminar.setText("Dar de baja");
        txtInicio.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                actualizarFin();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                actualizarFin();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                actualizarFin();
            }
        });

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Socio", "Entrada", "Fecha de Inicio", "Fecha de Fin", "Costo", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblClases.setModel(modeloTabla);

        paseData = new PaseData(cn);
        socioData = new SocioData(cn);

        txtId.setEditable(false);

        ButtonGroup grupoEstado = new ButtonGroup();
        grupoEstado.add(rbActivo);
        grupoEstado.add(rbInactivo);
        rbActivo.setSelected(true);

        btnNuevo.addActionListener(this::btnNuevoActionPerformed);
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);
        btnModificar.addActionListener(this::btnModificarActionPerformed);
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        cargarSocios();

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
        lblSocio = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        cmbSocio = new javax.swing.JComboBox<>();
        lblEntrada = new javax.swing.JLabel();
        txtEntrada = new javax.swing.JTextField();
        lblFechaInicio = new javax.swing.JLabel();
        txtInicio = new javax.swing.JTextField();
        lblFechaFin = new javax.swing.JLabel();
        txtFin = new javax.swing.JTextField();
        lblCosto = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        rbActivo = new javax.swing.JRadioButton();
        rbInactivo = new javax.swing.JRadioButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClases = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Pase");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Pase AboriGym");

        lblId.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblId.setText("ID:");

        lblSocio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSocio.setText("Socio:");

        txtId.addActionListener(this::txtIdActionPerformed);

        lblEntrada.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEntrada.setText("Entrada:");

        lblFechaInicio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFechaInicio.setText("Fecha de Inicio:");

        lblFechaFin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFechaFin.setText("Fecha de Fin:");

        lblCosto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCosto.setText("Costo:");

        lblEstado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEstado.setText("Estado:");

        rbActivo.setText("Activo");

        rbInactivo.setText("Inactivo");

        btnNuevo.setText("Nuevo");

        btnGuardar.setText("Guardar");

        btnModificar.setText("Modificar");

        btnEliminar.setText("Eliminar");

        tblClases.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Socio", "Entrada", "Inicio", "Finalizado", "Costo", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblClases);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Pases Registrados");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFechaFin)
                                    .addComponent(lblFechaInicio)
                                    .addComponent(lblEntrada)
                                    .addComponent(lblSocio)
                                    .addComponent(lblId)
                                    .addComponent(lblCosto))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFin, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtEntrada, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtInicio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtId, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cmbSocio, javax.swing.GroupLayout.Alignment.LEADING, 0, 107, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnGuardar))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblEstado)
                                        .addGap(34, 34, 34)
                                        .addComponent(rbActivo)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rbInactivo)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnModificar)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnEliminar))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(lblTitulo)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(233, 233, 233))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblId)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSocio)
                            .addComponent(cmbSocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEntrada)
                            .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFechaInicio)
                            .addComponent(txtInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFechaFin)
                            .addComponent(txtFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCosto)
                            .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEstado)
                            .addComponent(rbActivo)
                            .addComponent(rbInactivo))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevo)
                            .addComponent(btnGuardar)
                            .addComponent(btnModificar)
                            .addComponent(btnEliminar)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
    }//GEN-LAST:event_txtIdActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {
        limpiar();
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        Pase nuevo = leerFormulario();
        if (nuevo == null) {
            return;
        }
        try {
            if (paseData.alta(nuevo)) {
                info("Pase guardado correctamente. \nID asignado: " + nuevo.getIdPase()
                        + "\nVence el " + nuevo.getFechaFin().format(FORMATO_FECHA)
                        + " (" + Pase.DIAS_VALIDEZ + " días).");
                limpiar();
                cargarTabla();
            } else {
                aviso("No se pudo guardar el pase.", null);
            }
        } catch (ReglaNegocioException ex) {
            aviso(ex.getMessage(), null);
        } catch (SQLException ex) {
            error("No se pudo guardar el pase.", ex);
        }
    }

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {
        if (paseCargado == null) {
            aviso("Seleccione un pase de la tabla.", null);
            return;
        }

        Pase modificado = leerFormulario();
        if (modificado == null) {
            return;
        }
        modificado.setIdPase(paseCargado.getIdPase());

        try {
            if (paseData.actualizar(modificado)) {
                info("Pase actualizado correctamente.\nVence el " + modificado.getFechaFin().format(FORMATO_FECHA)
                        + " (" + Pase.DIAS_VALIDEZ + " días).");
                limpiar();
                cargarTabla();
            } else {
                aviso("No se pudo actualizar el pase.", null);
            }
        } catch (ReglaNegocioException ex) {
            aviso(ex.getMessage(), null);
        } catch (SQLException ex) {
            error("No se pudo actualizar el pase.", ex);
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        if (paseCargado == null) {
            aviso("Seleccione un pase de la tabla.", null);
            return;
        }
        if (!confirmar("¿Dar de baja este pase?\nQuedará inactivo y no se podrá usar para nuevas asistencias.",
                "Confirmar baja")) {
            return;
        }
        try {
            if (paseData.bajaLogica(paseCargado.getIdPase())) {
                info("Pase eliminado.");
                limpiar();
                cargarTabla();
            } else {
                aviso("No se pudo dar de baja el pase.", null);
            }
        } catch (SQLException ex) {
            error("No se pudo dar de baja el pase.", ex);
        }
    }
    
    private void actualizarFin() {
        try {
            LocalDate inicio = LocalDate.parse(txtInicio.getText().trim(), FORMATO_FECHA);
            txtFin.setText(inicio.plusDays(Pase.DIAS_VALIDEZ).format(FORMATO_FECHA));
        } catch (DateTimeParseException e) {
            txtFin.setText("");
        }
    }

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
        } catch (SQLException ex) {
            error("No se pudo cargar la lista de socios.", ex);
        }
    }

    private void cargarTabla() {
        try {
            List<Pase> pases = paseData.listar();
            modeloTabla.setRowCount(0);
            listaActual = new ArrayList<>();
            for (Pase p : pases) {
                listaActual.add(p);
                String socio = "";
                if (p.getSocio() != null) {
                    socio = p.getSocio().getApellido() + ", " + p.getSocio().getNombre();
                }
                modeloTabla.addRow(new Object[]{
                    p.getIdPase(),
                    socio,
                    p.getCantEntrada(),
                    p.getFechaInicio().format(FORMATO_FECHA),
                    p.getFechaFin().format(FORMATO_FECHA),
                    String.format("%.2f", p.getCosto()),
                    p.isEstado() ? "Activo" : "Inactivo"
                });
            }
        } catch (SQLException ex) {
            error("No se pudo cargar la lista de pases.", ex);
        }
    }

    private void mostrar(Pase p) {
        paseCargado = p;
        txtId.setText(String.valueOf(p.getIdPase()));
        seleccionarSocio(p.getSocio() == null ? 0 : p.getSocio().getIdSocio());
        txtEntrada.setText(String.valueOf(p.getCantEntrada()));
        txtInicio.setText(p.getFechaInicio().format(FORMATO_FECHA));
        txtFin.setText(p.getFechaFin().format(FORMATO_FECHA));
        txtCosto.setText(String.format("%.2f", p.getCosto()));
        rbActivo.setSelected(p.isEstado());
        rbInactivo.setSelected(!p.isEstado());
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

    private void limpiar() {
        paseCargado = null;
        txtId.setText("");
        if (cmbSocio.getItemCount() > 0) {
            cmbSocio.setSelectedIndex(0);
        } else {
            cmbSocio.setSelectedIndex(-1);
        }
        txtEntrada.setText("");
        txtInicio.setText(LocalDate.now().format(FORMATO_FECHA));
        txtFin.setText(LocalDate.now().plusDays(Pase.DIAS_VALIDEZ).format(FORMATO_FECHA));
        txtCosto.setText("");
        rbActivo.setSelected(true);
        tblClases.clearSelection();
        actualizarBotones();
        txtEntrada.requestFocusInWindow();
    }

    private void actualizarBotones() {
        boolean hay = paseCargado != null;
        btnGuardar.setEnabled(!hay);
        btnModificar.setEnabled(hay);
        btnEliminar.setEnabled(hay);
    }

    private Pase leerFormulario() {
        int iSocio = cmbSocio.getSelectedIndex();
        if (iSocio < 0 || iSocio >= sociosCombo.size()) {
            aviso("Seleccione un socio.", cmbSocio);
            return null;
        }

        String entradaTxt = txtEntrada.getText().trim();
        int entrada;
        try {
            entrada = Integer.parseInt(entradaTxt);
        } catch (NumberFormatException e) {
            aviso("La cantidad de entradas debe ser un número entero.", txtEntrada);
            return null;
        }
        if (entrada <= 0) {
            aviso("La cantidad de entradas debe ser mayor que cero.", txtEntrada);
            return null;
        }

        LocalDate inicio;
        try {
            inicio = LocalDate.parse(txtInicio.getText().trim(), FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            aviso("La fecha de inicio debe tener el formato dd/MM/yyyy.", txtInicio);
            return null;
        }

        LocalDate fin = inicio.plusDays(Pase.DIAS_VALIDEZ);

        String costoTxt = txtCosto.getText().trim().replace(',', '.');
        double costo;
        try {
            costo = Double.parseDouble(costoTxt);
        } catch (NumberFormatException e) {
            aviso("El costo debe ser un número.", txtCosto);
            return null;
        }
        if (costo < 0) {
            aviso("El costo no puede ser negativo.", txtCosto);
            return null;
        }

        boolean estado = !rbInactivo.isSelected();
        return new Pase(sociosCombo.get(iSocio), entrada, inicio, fin, costo, estado);
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
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbSocio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCosto;
    private javax.swing.JLabel lblEntrada;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblSocio;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JRadioButton rbActivo;
    private javax.swing.JRadioButton rbInactivo;
    private javax.swing.JTable tblClases;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtEntrada;
    private javax.swing.JTextField txtFin;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtInicio;
    // End of variables declaration//GEN-END:variables
}
