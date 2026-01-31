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
            int ownerId, String firstName, String lastName, long phoneNumber, String serviceDate,
            int vehicleId, String make, String model, int year, String registration,
            Service service) {

        Owner owner = new Owner();
        owner.setOwnerId(ownerId);
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setPhoneNumber(phoneNumber);

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
    public ArrayList<ServiceRecord> search(String query) {
    ArrayList<ServiceRecord> results = new ArrayList<>();
    String q = query.trim().toLowerCase();

    for (ServiceRecord r : recordList) {

        // Vehicle ID (numeric exact match)
        if (q.matches("\\d+")) {
            int id = Integer.parseInt(q);
            if (r.getVehicle().getVehicleId() == id) {
                results.add(r);
            }
            continue; // skip other checks if numeric search
        }

        // Vehicle Model match (partial)
        if (r.getVehicle().getModel() != null &&
            r.getVehicle().getModel().toLowerCase().contains(q)) {
            results.add(r);
            continue;
        }

        // Vehicle Make match (partial)
        if (r.getVehicle().getMake() != null &&
            r.getVehicle().getMake().toLowerCase().contains(q)) {
            results.add(r);
            continue;
        }

        // Owner First Name match (partial)
        if (r.getOwner().getFirstName() != null &&
            r.getOwner().getFirstName().toLowerCase().contains(q)) {
            results.add(r);
            continue;
        }

        // Owner Last Name match (partial)
        if (r.getOwner().getLastName() != null &&
            r.getOwner().getLastName().toLowerCase().contains(q)) {
            results.add(r);
        }
    }

    return results;
}

    // DELETE
    public void deleteRecord(ServiceRecord record) {
        recordList.remove(record);
    }
    // UPDATE 
        public void updateRecord(ServiceRecord record,
        int ownerId, String firstName, String lastName, String serviceDate,
        int vehicleId, String make, String model, int year, String registration,
        Service service) {

        record.getOwner().setOwnerId(ownerId);
        record.getOwner().setFirstName(firstName);
        record.getOwner().setLastName(lastName);

        record.setServiceDate(serviceDate);

        record.getVehicle().setVehicleId(vehicleId);
        record.getVehicle().setMake(make);
        record.getVehicle().setModel(model);
        record.getVehicle().setYear(year);
        record.getVehicle().setRegistrationNumber(registration);

        record.setService(service);
}


    @Override
    public String toString() {
        return "VehicleDirectory{" + "recordList=" + recordList + '}';
    }
}
