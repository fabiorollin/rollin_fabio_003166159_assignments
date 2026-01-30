package ui;

import javax.swing.JOptionPane;
import model.ServiceCatalog;

/**
 *
 * @author fabio
 */
public class ManageServicesJPanel extends javax.swing.JPanel {

    private javax.swing.JPanel bottomPanel;
    private ServiceCatalog serviceCatalog;

    public ManageServicesJPanel() {
        initComponents();
    }

    // Constructor used by MainJFrame
    public ManageServicesJPanel(javax.swing.JPanel bottomPanel, ServiceCatalog serviceCatalog) {
        initComponents();
        this.bottomPanel = bottomPanel;
        this.serviceCatalog = serviceCatalog;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblServiceId = new javax.swing.JLabel();
        lblServiceType = new javax.swing.JLabel();
        lblCost = new javax.swing.JLabel();
        lblMechanic = new javax.swing.JLabel();
        lblDuration = new javax.swing.JLabel();

        txtServiceId = new javax.swing.JTextField();
        txtServiceType = new javax.swing.JTextField();
        txtCost = new javax.swing.JTextField();
        txtMechanic = new javax.swing.JTextField();
        txtDuration = new javax.swing.JTextField();

        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 229, 255));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitle.setText("Manage Services");

        lblServiceId.setText("Service ID:");
        lblServiceType.setText("Service Type:");
        lblCost.setText("Cost:");
        lblMechanic.setText("Mechanic Name:");
        lblDuration.setText("Duration (mins):");

        btnAdd.setText("Add Service");
        btnUpdate.setText("Update Service");

        btnAdd.addActionListener(evt -> addService());
        btnUpdate.addActionListener(evt -> updateService());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblServiceId)
                            .addComponent(lblServiceType)
                            .addComponent(lblCost)
                            .addComponent(lblMechanic)
                            .addComponent(lblDuration))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtServiceId)
                            .addComponent(txtServiceType)
                            .addComponent(txtCost)
                            .addComponent(txtMechanic)
                            .addComponent(txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addGap(20, 20, 20)
                        .addComponent(btnUpdate)))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblTitle)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServiceId)
                    .addComponent(txtServiceId))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServiceType)
                    .addComponent(txtServiceType))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCost)
                    .addComponent(txtCost))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMechanic)
                    .addComponent(txtMechanic))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDuration)
                    .addComponent(txtDuration))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate))
                .addContainerGap(40, Short.MAX_VALUE))
        );
    }

    // ---------------- LOGIC ----------------

    private void addService() {
        try {
            int id = Integer.parseInt(txtServiceId.getText());
            String type = txtServiceType.getText();
            double cost = Double.parseDouble(txtCost.getText());
            String mechanic = txtMechanic.getText();
            int duration = Integer.parseInt(txtDuration.getText());

            if (type.isEmpty() || mechanic.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required");
                return;
            }

            serviceCatalog.addService(id, type, cost, mechanic, duration);
            JOptionPane.showMessageDialog(this, "Service added successfully");
            clearFields();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values");
        }
    }

    private void updateService() {
        try {
            int id = Integer.parseInt(txtServiceId.getText());
            String type = txtServiceType.getText();
            double cost = Double.parseDouble(txtCost.getText());
            String mechanic = txtMechanic.getText();
            int duration = Integer.parseInt(txtDuration.getText());

            serviceCatalog.updateService(id, type, cost, mechanic, duration);
            JOptionPane.showMessageDialog(this, "Service updated successfully");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values");
        }
    }

    private void clearFields() {
        txtServiceId.setText("");
        txtServiceType.setText("");
        txtCost.setText("");
        txtMechanic.setText("");
        txtDuration.setText("");
    }

    // ---------------- VARIABLES ----------------
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblServiceId;
    private javax.swing.JLabel lblServiceType;
    private javax.swing.JLabel lblCost;
    private javax.swing.JLabel lblMechanic;
    private javax.swing.JLabel lblDuration;

    private javax.swing.JTextField txtServiceId;
    private javax.swing.JTextField txtServiceType;
    private javax.swing.JTextField txtCost;
    private javax.swing.JTextField txtMechanic;
    private javax.swing.JTextField txtDuration;

    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnUpdate;



    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
|

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

