package model;

import java.util.ArrayList;

/**
 *
 * @author fabio
 */
public class VehicleDirectory {

    private ArrayList<ServiceRecord> recordList;

    // Constructor
    public VehicleDirectory() {
        recordList = new ArrayList<>();
    }

    // CREATE (used by Register panel and startup data)
    public ServiceRecord addRecord(
            int ownerId, String firstName, String lastName, String serviceDate,
            int vehicleId, String make, String model, int year, String registration,
            Service service) {

        Owner owner = new Owner();
        owner.setOwnerId(ownerId);
        owner.setFirstName(firstName);
        owner.setLastName(lastName);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(vehicleId);
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setYear(year);
        vehicle.setRegistrationNumber(registration);

        ServiceRecord record = new ServiceRecord();
        record.setOwner(owner);
        record.setVehicle(vehicle);
        record.setService(service);
        record.setServiceDate(serviceDate);

        recordList.add(record);
        return record;
    }

    // READ - all records (for JTable)
    public ArrayList<ServiceRecord> getRecordList() {
        return recordList;
    }

    // READ - search by Vehicle ID
    public ServiceRecord findByVehicleId(int vehicleId) {
        for (ServiceRecord r : recordList) {
            if (r.getVehicle().getVehicleId() == vehicleId) {
                return r;
            }
        }
        return null;
    }

    // READ - search by Vehicle MODEL (name) → MULTIPLE matches
    public ArrayList<ServiceRecord> findByVehicleModel(String model) {
        ArrayList<ServiceRecord> result = new ArrayList<>();
        for (ServiceRecord r : recordList) {
            if (r.getVehicle().getModel().equalsIgnoreCase(model)) {
                result.add(r);
            }
        }
        return result;
    }

    // DELETE
    public void deleteRecord(ServiceRecord record) {
        recordList.remove(record);
    }

    @Override
    public String toString() {
        return "VehicleDirectory{" + "recordList=" + recordList + '}';
    }
}
