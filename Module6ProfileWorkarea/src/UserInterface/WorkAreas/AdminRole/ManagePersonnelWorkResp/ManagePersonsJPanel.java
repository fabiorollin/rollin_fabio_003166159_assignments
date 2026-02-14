package UserInterface.WorkAreas.AdminRole.ManagePersonnelWorkResp;

import Business.Business;
import Business.Person.Person;
import Business.Profiles.EmployeeProfile;
import Business.Profiles.FacultyProfile;
import Business.UserAccounts.UserAccount;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManagePersonsJPanel extends JPanel {

    private Business business;
    private JPanel CardSequencePanel;
    private JTable table;

    public ManagePersonsJPanel(Business b, JPanel clp) {
        business = b;
        CardSequencePanel = clp;
        init();
        refresh();
    }

    private void init(){
        setLayout(new BorderLayout(10,10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnBack = new JButton("<< Back");
        btnBack.addActionListener(e -> {
        CardSequencePanel.remove(this);  // remove current panel
        CardLayout layout = (CardLayout) CardSequencePanel.getLayout();
        layout.previous(CardSequencePanel);  // go back properly
        });
        top.add(btnBack);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> refresh());
        top.add(btnRefresh);

        JButton btnCreateEmp = new JButton("Register Employee (Admin)");
        btnCreateEmp.addActionListener(e -> createEmployee());
        top.add(btnCreateEmp);

        JButton btnCreateFac = new JButton("Register Faculty");
        btnCreateFac.addActionListener(e -> createFaculty());
        top.add(btnCreateFac);

        add(top, BorderLayout.NORTH);

        table = new JTable(new DefaultTableModel(new Object[]{"Type","Name","Username","Emp/Fac ID","Title/Dept","Last Access","Last Updated"},0){
            public boolean isCellEditable(int r,int c){return false;}
        });
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnUpdate = new JButton("Update Selected");
        btnUpdate.addActionListener(e -> updateSelected());
        JButton btnDelete = new JButton("Delete Selected");
        btnDelete.addActionListener(e -> deleteSelected());
        bottom.add(btnUpdate);
        bottom.add(btnDelete);
        add(bottom, BorderLayout.SOUTH);
    }

    private void refresh(){
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.setRowCount(0);

        for(EmployeeProfile ep : business.getEmployeeDirectory().getEmployeeList()){
            UserAccount ua = business.getUserAccountDirectory().findUserAccount(ep.getPerson().getPersonId());
            dtm.addRow(new Object[]{
                "Employee",
                ep.getPerson().getPersonId(),
                ua!=null?ua.getUserLoginName():"",
                ep.getEmployeeId(),
                ep.getTitle(),
                ua!=null?ua.getLastAccess():"",
                ua!=null?ua.getLastUpdated():""
            });
        }

        for(FacultyProfile fp : business.getFacultyDirectory().getFacultyList()){
            UserAccount ua = business.getUserAccountDirectory().findUserAccount(fp.getPerson().getPersonId());
            dtm.addRow(new Object[]{
                "Faculty",
                fp.getPerson().getPersonId(),
                ua!=null?ua.getUserLoginName():"",
                fp.getFacultyId(),
                fp.getDepartment(),
                ua!=null?ua.getLastAccess():"",
                ua!=null?ua.getLastUpdated():""
            });
        }
    }

    private int getSelectedRow(){
        return table.getSelectedRow();
    }

    private void createEmployee(){
        String name = JOptionPane.showInputDialog(this, "Employee name:");
        if(name==null || name.trim().isEmpty()) return;

        String empId = JOptionPane.showInputDialog(this, "Employee ID:");
        if(empId==null) empId="";

        String title = JOptionPane.showInputDialog(this, "Title:");
        if(title==null) title="";

        String username = JOptionPane.showInputDialog(this, "Username:");
        if(username==null || username.trim().isEmpty()) return;
        if(business.getUserAccountDirectory().isUsernameTaken(username.trim())){
            JOptionPane.showMessageDialog(this, "Username already exists");
            return;
        }

        String password = JOptionPane.showInputDialog(this, "Password:");
        if(password==null || password.trim().isEmpty()) return;

        Person p = business.getPersonDirectory().newPerson(name.trim());
        EmployeeProfile ep = business.getEmployeeDirectory().newEmployeeProfile(p);
        ep.setEmployeeId(empId);
        ep.setTitle(title);

        business.getUserAccountDirectory().newUserAccount(ep, username.trim(), password);
        JOptionPane.showMessageDialog(this, "Employee created");
        refresh();
    }

    private void createFaculty(){
        String name = JOptionPane.showInputDialog(this, "Faculty name:");
        if(name==null || name.trim().isEmpty()) return;

        String facId = JOptionPane.showInputDialog(this, "Faculty ID:");
        if(facId==null) facId="";

        String dept = JOptionPane.showInputDialog(this, "Department:");
        if(dept==null) dept="";

        String username = JOptionPane.showInputDialog(this, "Username:");
        if(username==null || username.trim().isEmpty()) return;
        if(business.getUserAccountDirectory().isUsernameTaken(username.trim())){
            JOptionPane.showMessageDialog(this, "Username already exists");
            return;
        }

        String password = JOptionPane.showInputDialog(this, "Password:");
        if(password==null || password.trim().isEmpty()) return;

        Person p = business.getPersonDirectory().newPerson(name.trim());
        FacultyProfile fp = business.getFacultyDirectory().newFacultyProfile(p);
        fp.setFacultyId(facId);
        fp.setDepartment(dept);

        business.getUserAccountDirectory().newUserAccount(fp, username.trim(), password);
        JOptionPane.showMessageDialog(this, "Faculty created");
        refresh();
    }

    private void updateSelected(){
        int row = getSelectedRow();
        if(row<0){
            JOptionPane.showMessageDialog(this, "Select a row");
            return;
        }
        String type = (String) table.getValueAt(row,0);
        String name = (String) table.getValueAt(row,1);

        if("Employee".equals(type)){
            EmployeeProfile ep = business.getEmployeeDirectory().findEmployee(name);
            if(ep==null) return;
            String empId = JOptionPane.showInputDialog(this, "Employee ID:", ep.getEmployeeId());
            if(empId!=null) ep.setEmployeeId(empId);
            String title = JOptionPane.showInputDialog(this, "Title:", ep.getTitle());
            if(title!=null) ep.setTitle(title);
            JOptionPane.showMessageDialog(this, "Updated");
        } else {
            FacultyProfile fp = business.getFacultyDirectory().findFaculty(name);
            if(fp==null) return;
            String facId = JOptionPane.showInputDialog(this, "Faculty ID:", fp.getFacultyId());
            if(facId!=null) fp.setFacultyId(facId);
            String dept = JOptionPane.showInputDialog(this, "Department:", fp.getDepartment());
            if(dept!=null) fp.setDepartment(dept);
            JOptionPane.showMessageDialog(this, "Updated");
        }
        refresh();
    }

    private void deleteSelected(){
        int row = getSelectedRow();
        if(row<0){
            JOptionPane.showMessageDialog(this, "Select a row");
            return;
        }
        String type = (String) table.getValueAt(row,0);
        String name = (String) table.getValueAt(row,1);

        int confirm = JOptionPane.showConfirmDialog(this, "Delete selected profile and user account?", "Confirm", JOptionPane.YES_NO_OPTION);
        if(confirm!=JOptionPane.YES_OPTION) return;

        if("Employee".equals(type)){
            EmployeeProfile ep = business.getEmployeeDirectory().findEmployee(name);
            if(ep!=null){
                UserAccount ua = business.getUserAccountDirectory().findUserAccount(ep.getPerson().getPersonId());
                if(ua!=null && "admin".equalsIgnoreCase(ua.getUserLoginName())){
                    JOptionPane.showMessageDialog(this, "Cannot delete default admin");
                    return;
                }
                if(ua!=null) business.getUserAccountDirectory().deleteUserAccount(ua);
                business.getEmployeeDirectory().removeEmployee(ep);
            }
        } else {
            FacultyProfile fp = business.getFacultyDirectory().findFaculty(name);
            if(fp!=null){
                UserAccount ua = business.getUserAccountDirectory().findUserAccount(fp.getPerson().getPersonId());
                if(ua!=null) business.getUserAccountDirectory().deleteUserAccount(ua);
                business.getFacultyDirectory().removeFaculty(fp);
            }
        }
        JOptionPane.showMessageDialog(this, "Deleted");
        refresh();
    }
}
