/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.ArrayList;

/**
 *
 * @author fabio
 */
public class ServiceCatalog {
    private ArrayList<Service> serviceList;

    // Constructor
    public ServiceCatalog() {
        serviceList = new ArrayList<>();
    }

    // CREATE
    public Service addService(int serviceId, String serviceType, double cost, String mechanicName, int duration) {
        Service service = new Service();
        service.setServiceId(serviceId);
        service.setServiceType(serviceType);
        service.setCost(cost);
        service.setMechanicName(mechanicName);
        service.setServiceDuration(duration);

        serviceList.add(service);
        return service;
    }

    // READ - get all services (for JTable / ComboBox)
    public ArrayList<Service> getServiceList() {
        return serviceList;
    }

    // READ - find by ID
    public Service findById(int serviceId) {
        for (Service s : serviceList) {
            if (s.getServiceId() == serviceId) {
                return s;
            }
        }
        return null;
    }

    // UPDATE
    public void updateService(int serviceId, String serviceType, double cost, String mechanicName, int duration) {
        Service service = findById(serviceId);
        if (service != null) {
            service.setServiceType(serviceType);
            service.setCost(cost);
            service.setMechanicName(mechanicName);
            service.setServiceDuration(duration);
        }
    }

    // DELETE
    public void deleteService(Service service) {
        serviceList.remove(service);
    }

    @Override
    public String toString() {
        return "ServiceCatalog{" + "serviceList=" + serviceList + '}';
    }
}
    
}
