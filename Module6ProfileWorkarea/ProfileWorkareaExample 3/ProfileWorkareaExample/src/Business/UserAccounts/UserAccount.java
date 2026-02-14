/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.UserAccounts;

import Business.Profiles.Profile;



/**
 *
 * @author kal bugrara
 */
public class UserAccount {
    
    Profile profile;
    String username;
    String password;
    String lastAccess;
    String lastUpdated;
    
    public UserAccount (Profile profile, String un, String pw){
        username = un;
         password = pw;
         this.profile = profile;
        this.lastUpdated = now();
    }

    public String getPersonId(){
        return profile.getPerson().getPersonId();
    }
    public String getUserLoginName(){
        return username;
    }

        public boolean isMatch(String id){
        if(getPersonId().equals(id)) return true;
        return false;
    }
        public boolean IsValidUser(String un, String pw){
        
            if (username.equalsIgnoreCase(un) && password.equals(pw)) return true;
            else return false;
        
        }
        public String getRole(){
            return profile.getRole();
        }
        
        public Profile getAssociatedPersonProfile(){
            return profile;
        }
        
    @Override
        public String toString(){
            
            return getUserLoginName();
        }

    private String now(){
        return java.time.LocalDateTime.now().toString();
    }

    public String getLastAccess() {
        return lastAccess;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void touchAccess(){
        this.lastAccess = now();
    }

    public void touchUpdated(){
        this.lastUpdated = now();
    }

    public void setPassword(String newPw){
        this.password = newPw;
        touchUpdated();
    }

}
