package UserInterface.WorkAreas.AdminRole.ManageNUIDWorkResp;

import Business.Business;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageNUIDJPanel extends JPanel {

    private Business business;
    private JPanel CardSequencePanel;

    private JTable table;
    private JTextField txtNuid;

    public ManageNUIDJPanel(Business b, JPanel clp) {
        business = b;
        CardSequencePanel = clp;
        init();
        refresh();
    }

    private void init(){
        setLayout(new BorderLayout(10,10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("NUID:"));
        txtNuid = new JTextField(12);
        top.add(txtNuid);

        JButton btnAdd = new JButton("Add Allowed NUID");
        btnAdd.addActionListener(e -> {
            String n = txtNuid.getText();
            if(n==null || n.trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "Enter a NUID");
                return;
            }
            business.getStudentDirectory().addAllowedNUID(n.trim());
            txtNuid.setText("");
            refresh();
            JOptionPane.showMessageDialog(this, "NUID added");
        });
        top.add(btnAdd);

        JButton btnBack = new JButton("<< Back");
        btnBack.addActionListener(e -> {
            CardLayout layout = (CardLayout) CardSequencePanel.getLayout();
            layout.previous(CardSequencePanel);
        });
        top.add(btnBack);

        add(top, BorderLayout.NORTH);

        table = new JTable(new DefaultTableModel(new Object[]{"NUID","Status"},0){
            public boolean isCellEditable(int r,int c){ return false; }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void refresh(){
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.setRowCount(0);
        for(String n : business.getStudentDirectory().getAllowedNuids()){
            String status = business.getStudentDirectory().isNUIDUsed(n) ? "USED" : "AVAILABLE";
            dtm.addRow(new Object[]{n, status});
        }
    }
}
