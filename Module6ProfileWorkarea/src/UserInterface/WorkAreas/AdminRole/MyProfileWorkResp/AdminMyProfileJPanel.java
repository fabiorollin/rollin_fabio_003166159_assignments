package UserInterface.WorkAreas.AdminRole.MyProfileWorkResp;

import Business.Business;
import Business.Profiles.EmployeeProfile;
import Business.UserAccounts.UserAccount;
import javax.swing.*;
import java.awt.*;

public class AdminMyProfileJPanel extends JPanel {

    private Business business;
    private EmployeeProfile employeeProfile;
    private UserAccount userAccount;
    private JPanel CardSequencePanel;

    private JTextField txtName;
    private JTextField txtEmployeeId;
    private JTextField txtTitle;

    public AdminMyProfileJPanel(Business b, EmployeeProfile ep, UserAccount ua, JPanel clp) {
        business = b;
        employeeProfile = ep;
        userAccount = ua;
        CardSequencePanel = clp;
        init();
        load();
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
        add(top, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(0,2,8,8));
        form.add(new JLabel("Name:"));
        txtName = new JTextField();
        form.add(txtName);

        form.add(new JLabel("Employee ID:"));
        txtEmployeeId = new JTextField();
        form.add(txtEmployeeId);

        form.add(new JLabel("Title:"));
        txtTitle = new JTextField();
        form.add(txtTitle);

        form.add(new JLabel("Username:"));
        form.add(new JLabel(userAccount!=null?userAccount.getUserLoginName():""));

        form.add(new JLabel("Last Access:"));
        form.add(new JLabel(userAccount!=null?String.valueOf(userAccount.getLastAccess()):""));

        form.add(new JLabel("Last Updated:"));
        form.add(new JLabel(userAccount!=null?String.valueOf(userAccount.getLastUpdated()):""));

        add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> save());
        JButton btnResetPw = new JButton("Reset Password");
        btnResetPw.addActionListener(e -> resetPassword());
        bottom.add(btnResetPw);
        bottom.add(btnSave);
        add(bottom, BorderLayout.SOUTH);
    }

    private void load(){
        if(employeeProfile==null) return;
        txtName.setText(employeeProfile.getPerson().getPersonId());
        txtEmployeeId.setText(employeeProfile.getEmployeeId());
        txtTitle.setText(employeeProfile.getTitle());
    }

    private void save(){
        if(employeeProfile==null) return;
        // Person only stores id; treat as name
        String name = txtName.getText();
        if(name==null || name.trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Name is required");
            return;
        }
        employeeProfile.getPerson().setPersonId(name.trim());
        employeeProfile.setEmployeeId(txtEmployeeId.getText());
        employeeProfile.setTitle(txtTitle.getText());
        JOptionPane.showMessageDialog(this, "Saved");
    }

    private void resetPassword(){
        if(userAccount==null){
            JOptionPane.showMessageDialog(this, "No user account");
            return;
        }
        String pw = JOptionPane.showInputDialog(this, "Enter new password:");
        if(pw==null || pw.trim().isEmpty()) return;
        userAccount.setPassword(pw);
        JOptionPane.showMessageDialog(this, "Password updated");
    }
}
