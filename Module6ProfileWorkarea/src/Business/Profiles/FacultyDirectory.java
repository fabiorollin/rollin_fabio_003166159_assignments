package Business.Profiles;

import Business.Person.Person;
import java.util.ArrayList;

public class FacultyDirectory {

    private ArrayList<FacultyProfile> facultylist;

    public FacultyDirectory() {
        facultylist = new ArrayList<>();
    }

    public FacultyProfile newFacultyProfile(Person p) {
        FacultyProfile fp = new FacultyProfile(p);
        facultylist.add(fp);
        return fp;
    }

    public ArrayList<FacultyProfile> getFacultyList() {
        return facultylist;
    }

    public void removeFaculty(FacultyProfile fp) {
        facultylist.remove(fp);
    }

    public FacultyProfile findFaculty(String personId) {
        for (FacultyProfile fp : facultylist) {
            if (fp.isMatch(personId)) return fp;
        }
        return null;
    }
}
