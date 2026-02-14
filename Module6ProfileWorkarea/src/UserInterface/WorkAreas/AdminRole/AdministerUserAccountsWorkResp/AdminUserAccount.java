package UserInterface.WorkAreas.AdminRole.AdministerUserAccountsWorkResp;

import Business.Business;
import Business.UserAccounts.UserAccount;
import javax.swing.*;
import java.awt.*;

public class AdminUserAccount extends JPanel {

    private JPanel CardSequencePanel;
    private Business business;
    private UserAccount selecteduseraccount;

    private JLabel lblUsername;
    private JLabel lblRole;
    private JLabel lblLastAccess;
    private JLabel lblLastUpdated;
    private JPasswordField txtNewPassword;

    public AdminUserAccount(Business b, UserAccount sua, JPanel jp) {
        business = b;
        CardSequencePanel = jp;
        selecteduseraccount = sua;
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
        form.add(new JLabel("Username:"));
        lblUsername = new JLabel();
        form.add(lblUsername);

        form.add(new JLabel("Role:"));
        lblRole = new JLabel();
        form.add(lblRole);

        form.add(new JLabel("Last Access:"));
        lblLastAccess = new JLabel();
        form.add(lblLastAccess);

        form.add(new JLabel("Last Updated:"));
        lblLastUpdated = new JLabel();
        form.add(lblLastUpdated);

        form.add(new JLabel("New Password:"));
        txtNewPassword = new JPasswordField();
        form.add(txtNewPassword);

        add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnUpdate = new JButton("Update Password");
        btnUpdate.addActionListener(e -> updatePassword());
        JButton btnDelete = new JButton("Delete Account");
        btnDelete.addActionListener(e -> deleteAccount());
        bottom.add(btnDelete);
        bottom.add(btnUpdate);
        add(bottom, BorderLayout.SOUTH);
    }

    private void load(){
        if(selecteduseraccount==null) return;
        lblUsername.setText(selecteduseraccount.getUserLoginName());
        lblRole.setText(selecteduseraccount.getRole());
        lblLastAccess.setText(String.valueOf(selecteduseraccount.getLastAccess()));
        lblLastUpdated.setText(String.valueOf(selecteduseraccount.getLastUpdated()));
    }

    private void updatePassword(){
        if(selecteduseraccount==null) return;
        String pw = new String(txtNewPassword.getPassword());
        if(pw==null || pw.trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Enter a password");
            return;
        }
        selecteduseraccount.setPassword(pw);
        JOptionPane.showMessageDialog(this, "Saved");
        load();
    }

    private void deleteAccount(){
        if(selecteduseraccount==null) return;
        if("admin".equalsIgnoreCase(selecteduseraccount.getUserLoginName())){
            JOptionPane.showMessageDialog(this, "Cannot delete default admin");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this user account?", "Confirm", JOptionPane.YES_NO_OPTION);
        if(confirm!=JOptionPane.YES_OPTION) return;

        business.getUserAccountDirectory().deleteUserAccount(selecteduseraccount);
        JOptionPane.showMessageDialog(this, "Deleted");
        ((CardLayout)CardSequencePanel.getLayout()).previous(CardSequencePanel);
    }
}
