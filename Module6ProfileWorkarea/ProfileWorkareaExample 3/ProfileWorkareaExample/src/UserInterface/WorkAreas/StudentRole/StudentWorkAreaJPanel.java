package UserInterface.WorkAreas.StudentRole;

import Business.Business;
import Business.Profiles.StudentProfile;
import javax.swing.*;
import java.awt.*;

public class StudentWorkAreaJPanel extends JPanel {

    private Business business;
    private StudentProfile studentProfile;
    private JPanel CardSequencePanel;

    public StudentWorkAreaJPanel(Business b, StudentProfile sp, JPanel clp) {
        business = b;
        studentProfile = sp;
        CardSequencePanel = clp;
        init();
    }

    private void init(){
        setLayout(new BorderLayout(10,10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            // go back to login card
            CardSequencePanel.removeAll();
            JOptionPane.showMessageDialog(this, "Please restart app to login again (demo).");
        });
        top.add(btnLogout);
        add(top, BorderLayout.NORTH);

        
        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        center.add(new JLabel("Student Name:"), gbc);
        gbc.gridx = 1;
        center.add(new JLabel(studentProfile.getPerson().getPersonId()), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        center.add(new JLabel("NUID:"), gbc);
        gbc.gridx = 1;
        String nuid = studentProfile.getNUID();
        center.add(new JLabel(nuid == null ? "(not assigned)" : nuid), gbc);

        add(center, BorderLayout.CENTER);
}
}
