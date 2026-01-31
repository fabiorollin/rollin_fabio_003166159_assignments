package model;

/**
 *
 * @author fabio
 */
public class Owner {

    private int ownerId;
    private String firstName;
    private String lastName;
    private long phoneNumber;

    public Owner() {
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public long getPhoneNumber() {
    return phoneNumber;
}

    public void setPhoneNumber(long phoneNumber) {
    this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (ID: " + ownerId + ")";
    }
}
