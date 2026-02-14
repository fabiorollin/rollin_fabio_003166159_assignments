/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Profiles;

import Business.Person.Person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author kal bugrara
 */
public class StudentDirectory {


    ArrayList<StudentProfile> studentlist;

    private Set<String> allowedNuids;
    private Set<String> usedNuids;

public StudentDirectory() {

     studentlist = new ArrayList();
        allowedNuids = new HashSet<>();
        usedNuids = new HashSet<>();

    }

    public StudentProfile newStudentProfile(Person p) {

        StudentProfile sp = new StudentProfile(p);
        studentlist.add(sp);
        return sp;
    }

    public StudentProfile findStudent(String id) {

        for (StudentProfile sp : studentlist) {

            if (sp.isMatch(id)) {
                return sp;
            }
        }
            return null; //not found after going through the whole list
         }

    public void addAllowedNUID(String nuid){
        if(nuid==null) return;
        allowedNuids.add(nuid.trim());
    }

    public boolean isNUIDAllowedAndAvailable(String nuid){
        if(nuid==null) return false;
        String x = nuid.trim();
        return allowedNuids.contains(x) && !usedNuids.contains(x);
    }

    public void markNUIDUsed(String nuid){
        if(nuid==null) return;
        usedNuids.add(nuid.trim());
    }

    public boolean isNUIDUsed(String nuid){
        if(nuid==null) return false;
        return usedNuids.contains(nuid.trim());
    }

    public Set<String> getAllowedNuids(){
        return allowedNuids;
    }

    public Set<String> getUsedNuids(){
        return usedNuids;
    }

    public ArrayList<StudentProfile> getStudentList(){
        return studentlist;
    }

    public void removeStudent(StudentProfile sp){
        studentlist.remove(sp);
    }

}
