package UserInterface.WorkAreas.AdminRole.ManageFacultyWorkResp;

import Business.Business;
import Business.Profiles.FacultyProfile;
import Business.UserAccounts.UserAccount;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageFacultyJPanel extends JPanel {

    private Business business;
    private JPanel CardSequencePanel;
    private JTable table;

    public ManageFacultyJPanel(Business b, JPanel clp) {
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

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> refresh());
        top.add(btnRefresh);

        add(top, BorderLayout.NORTH);

        table = new JTable(new DefaultTableModel(new Object[]{"Faculty","FacultyId","Department","Username","Last Access","Last Updated"},0){
            public boolean isCellEditable(int r,int c){return false;}
        });
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnResetPw = new JButton("Reset Password");
        btnResetPw.addActionListener(e -> resetPassword());
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteFaculty());
        bottom.add(btnResetPw);
        bottom.add(btnDelete);
        add(bottom, BorderLayout.SOUTH);
    }

    private void refresh(){
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.setRowCount(0);

        for(FacultyProfile fp : business.getFacultyDirectory().getFacultyList()){
            UserAccount ua = business.getUserAccountDirectory().findUserAccount(fp.getPerson().getPersonId());
            String username = ua!=null? ua.getUserLoginName():"";
            String lastAccess = ua!=null? ua.getLastAccess():"";
            String lastUpdated = ua!=null? ua.getLastUpdated():"";
            dtm.addRow(new Object[]{fp.getPerson().getPersonId(), fp.getFacultyId(), fp.getDepartment(), username, lastAccess, lastUpdated});
        }
    }

    private FacultyProfile getSelected(){
        int row = table.getSelectedRow();
        if(row<0) return null;
        String personId = (String) table.getValueAt(row,0);
        return business.getFacultyDirectory().findFaculty(personId);
    }

    private void resetPassword(){
        FacultyProfile fp = getSelected();
        if(fp==null){
            JOptionPane.showMessageDialog(this, "Select a faculty");
            return;
        }
        UserAccount ua = business.getUserAccountDirectory().findUserAccount(fp.getPerson().getPersonId());
        if(ua==null){
            JOptionPane.showMessageDialog(this, "No user account found for this faculty");
            return;
        }
        String pw = JOptionPane.showInputDialog(this, "Enter new password:");
        if(pw==null || pw.trim().isEmpty()) return;
        ua.setPassword(pw);
        JOptionPane.showMessageDialog(this, "Password updated");
        refresh();
    }

    private void deleteFaculty(){
        FacultyProfile fp = getSelected();
        if(fp==null){
            JOptionPane.showMessageDialog(this, "Select a faculty");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete faculty and user account?", "Confirm", JOptionPane.YES_NO_OPTION);
        if(confirm!=JOptionPane.YES_OPTION) return;

        UserAccount ua = business.getUserAccountDirectory().findUserAccount(fp.getPerson().getPersonId());
        if(ua!=null){
            business.getUserAccountDirectory().deleteUserAccount(ua);
        }
        business.getFacultyDirectory().removeFaculty(fp);
        JOptionPane.showMessageDialog(this, "Deleted");
        refresh();
    }
}
