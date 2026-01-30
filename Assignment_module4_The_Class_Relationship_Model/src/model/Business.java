/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Business {

    private ServiceCatalog serviceCatalog;
    private VehicleDirectory vehicleDirectory;

    public Business() {
        serviceCatalog = new ServiceCatalog();
        vehicleDirectory = new VehicleDirectory();
    }

    public ServiceCatalog getServiceCatalog() {
        return serviceCatalog;
    }

    public VehicleDirectory getVehicleDirectory() {
        return vehicleDirectory;
    }
}

