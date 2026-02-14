package UserInterface.SignUp;

import Business.Business;
import Business.Person.Person;
import Business.Profiles.StudentProfile;
import javax.swing.*;
import java.awt.*;

public class SignUpJPanel extends JPanel {

    private Business business;
    private JPanel CardSequencePanel;

    private JTextField txtName;
    private JTextField txtNUID;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public SignUpJPanel(Business b, JPanel clp) {
        business = b;
        CardSequencePanel = clp;
        init();
    }

    private void init(){
        setLayout(new BorderLayout(10,10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnBack = new JButton("<< Back");
        btnBack.addActionListener(e -> ((CardLayout)CardSequencePanel.getLayout()).previous(CardSequencePanel));
        top.add(btnBack);
        add(top, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(0,2,8,8));
        form.add(new JLabel("Full Name:"));
        txtName = new JTextField();
        form.add(txtName);

        form.add(new JLabel("NUID:"));
        txtNUID = new JTextField();
        form.add(txtNUID);

        form.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        form.add(txtUsername);

        form.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        form.add(txtPassword);

        add(form, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCreate = new JButton("Create Student Account");
        btnCreate.addActionListener(e -> createAccount());
        bottom.add(btnCreate);
        add(bottom, BorderLayout.SOUTH);
    }

    private void createAccount(){
        String name = txtName.getText();
        String nuid = txtNUID.getText();
        String un = txtUsername.getText();
        String pw = new String(txtPassword.getPassword());

        if(name==null || name.trim().isEmpty() ||
           nuid==null || nuid.trim().isEmpty() ||
           un==null || un.trim().isEmpty() ||
           pw==null || pw.trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "All fields are required");
            return;
        }

        if(!business.getStudentDirectory().isNUIDAllowedAndAvailable(nuid.trim())){
            JOptionPane.showMessageDialog(this, "NUID is not available. Admin must add it first.");
            return;
        }

        if(business.getUserAccountDirectory().isUsernameTaken(un.trim())){
            JOptionPane.showMessageDialog(this, "Username already exists");
            return;
        }

        Person p = business.getPersonDirectory().newPerson(name.trim());
        StudentProfile sp = business.getStudentDirectory().newStudentProfile(p);
        sp.setNUID(nuid.trim());
        business.getStudentDirectory().markNUIDUsed(nuid.trim());

        business.getUserAccountDirectory().newUserAccount(sp, un.trim(), pw);
        JOptionPane.showMessageDialog(this, "Account created. You can now login.");
        ((CardLayout)CardSequencePanel.getLayout()).previous(CardSequencePanel);
    }
}
