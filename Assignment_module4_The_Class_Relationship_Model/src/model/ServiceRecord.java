package model;

/**
 *
 * @author fabio
 */
public class ServiceRecord {

    private Owner owner;
    private Vehicle vehicle;
    private Service service;
    private String serviceDate;

    public ServiceRecord() {
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    @Override
    public String toString() {
        return "ServiceRecord{" +
                "owner=" + owner +
                ", vehicle=" + vehicle +
                ", service=" + service +
                ", serviceDate='" + serviceDate + '\'' +
                '}';
    }
}
