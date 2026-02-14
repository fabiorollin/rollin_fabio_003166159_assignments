package UserInterface.WorkAreas.FacultyRole;

import Business.Business;
import Business.Profiles.FacultyProfile;
import javax.swing.*;
import java.awt.*;

public class FacultyWorkAreaJPanel extends JPanel {

    private Business business;
    private FacultyProfile facultyProfile;
    private JPanel CardSequencePanel;

    public FacultyWorkAreaJPanel(Business b, FacultyProfile fp, JPanel clp) {
        business = b;
        facultyProfile = fp;
        CardSequencePanel = clp;
        init();
    }

    private void init(){
        setLayout(new BorderLayout(10,10));
        JPanel center = new JPanel(new GridLayout(0,2,10,10));
        center.add(new JLabel("Faculty Name:"));
        center.add(new JLabel(facultyProfile.getPerson().getPersonId()));
        center.add(new JLabel("Faculty ID:"));
        center.add(new JLabel(facultyProfile.getFacultyId()));
        center.add(new JLabel("Department:"));
        center.add(new JLabel(facultyProfile.getDepartment()));
        add(center, BorderLayout.CENTER);
    }
}
