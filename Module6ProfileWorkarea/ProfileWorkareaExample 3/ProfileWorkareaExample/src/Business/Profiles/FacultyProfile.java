package Business.Profiles;

import Business.Person.Person;

public class FacultyProfile extends Profile {

    private String facultyId;
    private String department;

    public FacultyProfile(Person p) {
        super(p);
    }

    @Override
    public String getRole() {
        return "Faculty";
    }

    public boolean isMatch(String id) {
        return getPerson().getPersonId().equals(id);
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
