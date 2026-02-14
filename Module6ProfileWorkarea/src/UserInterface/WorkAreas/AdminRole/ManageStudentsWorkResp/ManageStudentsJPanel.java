package UserInterface.WorkAreas.AdminRole.ManageStudentsWorkResp;

import Business.Business;
import Business.Profiles.StudentProfile;
import Business.UserAccounts.UserAccount;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageStudentsJPanel extends JPanel {

    private Business business;
    private JPanel CardSequencePanel;
    private JTable table;

    public ManageStudentsJPanel(Business b, JPanel clp) {
        business = b;
        CardSequencePanel = clp;
        init();
        refresh();
    }

    private void init(){
        setLayout(new BorderLayout(10,10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnBack = new JButton("<< Back");
        btnBack.addActionListener(e -> ((CardLayout)CardSequencePanel.getLayout()).previous(CardSequencePanel));
        top.add(btnBack);

        JButton btnManageNUID = new JButton("Manage NUIDs");
        btnManageNUID.addActionListener(e -> {
            UserInterface.WorkAreas.AdminRole.ManageNUIDWorkResp.ManageNUIDJPanel panel =
                    new UserInterface.WorkAreas.AdminRole.ManageNUIDWorkResp.ManageNUIDJPanel(business, CardSequencePanel);
            CardSequencePanel.add("ManageNUIDJPanel", panel);
            ((CardLayout)CardSequencePanel.getLayout()).next(CardSequencePanel);
        });
        top.add(btnManageNUID);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> refresh());
        top.add(btnRefresh);

        add(top, BorderLayout.NORTH);

        table = new JTable(new DefaultTableModel(new Object[]{"Student","NUID","Username","Last Access","Last Updated"},0){
            public boolean isCellEditable(int r,int c){return false;}
        });
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnResetPw = new JButton("Reset Password");
        btnResetPw.addActionListener(e -> resetPassword());
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteStudent());
        bottom.add(btnResetPw);
        bottom.add(btnDelete);
        add(bottom, BorderLayout.SOUTH);
    }

    private void refresh(){
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.setRowCount(0);

        for(StudentProfile sp : business.getStudentDirectory().getStudentList()){
            String username = "";
            String lastAccess = "";
            String lastUpdated = "";
            UserAccount ua = business.getUserAccountDirectory().findUserAccount(sp.getPerson().getPersonId());
            if(ua!=null){
                username = ua.getUserLoginName();
                lastAccess = ua.getLastAccess();
                lastUpdated = ua.getLastUpdated();
            }
            dtm.addRow(new Object[]{sp.getPerson().getPersonId(), sp.getNUID(), username, lastAccess, lastUpdated});
        }
    }

    private StudentProfile getSelected(){
        int row = table.getSelectedRow();
        if(row<0) return null;
        String personId = (String) table.getValueAt(row,0);
        return business.getStudentDirectory().findStudent(personId);
    }

    private void resetPassword(){
        StudentProfile sp = getSelected();
        if(sp==null){
            JOptionPane.showMessageDialog(this, "Select a student");
            return;
        }
        UserAccount ua = business.getUserAccountDirectory().findUserAccount(sp.getPerson().getPersonId());
        if(ua==null){
            JOptionPane.showMessageDialog(this, "No user account found for this student");
            return;
        }
        String pw = JOptionPane.showInputDialog(this, "Enter new password:");
        if(pw==null || pw.trim().isEmpty()) return;
        ua.setPassword(pw);
        JOptionPane.showMessageDialog(this, "Password updated");
        refresh();
    }

    private void deleteStudent(){
        StudentProfile sp = getSelected();
        if(sp==null){
            JOptionPane.showMessageDialog(this, "Select a student");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete student and user account?", "Confirm", JOptionPane.YES_NO_OPTION);
        if(confirm!=JOptionPane.YES_OPTION) return;

        UserAccount ua = business.getUserAccountDirectory().findUserAccount(sp.getPerson().getPersonId());
        if(ua!=null){
            business.getUserAccountDirectory().deleteUserAccount(ua);
        }
        business.getStudentDirectory().removeStudent(sp);
        JOptionPane.showMessageDialog(this, "Deleted");
        refresh();
    }
}
