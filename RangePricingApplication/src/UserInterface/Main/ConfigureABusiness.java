package UserInterface.Main;

import MarketingManagement.MarketingPersonDirectory;
import MarketingManagement.MarketingPersonProfile;
import TheBusiness.Business.Business;
import TheBusiness.CustomerManagement.CustomerDirectory;
import TheBusiness.CustomerManagement.CustomerProfile;
import TheBusiness.OrderManagement.MasterOrderList;
import TheBusiness.OrderManagement.Order;
import TheBusiness.OrderManagement.OrderItem;
import TheBusiness.Personnel.Person;
import TheBusiness.Personnel.PersonDirectory;
import TheBusiness.ProductManagement.Product;
import TheBusiness.ProductManagement.ProductCatalog;
import TheBusiness.SalesManagement.SalesPersonDirectory;
import TheBusiness.SalesManagement.SalesPersonProfile;
import TheBusiness.Supplier.Supplier;
import TheBusiness.Supplier.SupplierDirectory;
import TheBusiness.UserAccountManagement.UserAccount;
import TheBusiness.UserAccountManagement.UserAccountDirectory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author kal bugrara
 */
class ConfigureABusiness {

    static Business initialize() {
        Business business = new Business("Xerox");
        Random random = new Random(42);

// Create Persons
        PersonDirectory persondirectory = business.getPersonDirectory();
// person representing sales organization
        Person xeroxsalesperson001 = persondirectory.newPerson("Xerox sales");
        Person xeroxmarketingperson001 = persondirectory.newPerson("Xerox marketing");
        Person xeroxadminperson001 = persondirectory.newPerson("Xerox admin");

// Create Sales people
        SalesPersonDirectory salespersondirectory = business.getSalesPersonDirectory();
        SalesPersonProfile salespersonprofile = salespersondirectory.newSalesPersonProfile(xeroxsalesperson001);

// Create Marketing people
        MarketingPersonDirectory marketingpersondirectory = business.getMarketingPersonDirectory();
        MarketingPersonProfile marketingpersonprofile0 = marketingpersondirectory.newMarketingPersonProfile(xeroxmarketingperson001);

// Create Admins to manage the business
//        EmployeeDirectory employeedirectory = business.getEmployeeDirectory();
//        EmployeeProfile employeeprofile0 = employeedirectory.newEmployeeProfile(xeroxadminperson001);

// Create User accounts that link to specific profiles
        UserAccountDirectory uadirectory = business.getUserAccountDirectory();
        UserAccount ua1 = uadirectory.newUserAccount(salespersonprofile, "Sales", "XXXX"); /// order products for one of the customers and performed by a sales person
        UserAccount ua2 = uadirectory.newUserAccount(marketingpersonprofile0, "Marketing", "XXXX"); /// order products for one of the customers and performed by a sales person
//        UserAccount ua3 = uadirectory.newUserAccount(employeeprofile0, "Admin", "XXXX"); /// order products for one of the customers and performed by a sales person

// Create person objects to represent customer organizations.
        String[] firstNames = {
            "James", "Mary", "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda",
            "William", "Barbara", "David", "Elizabeth", "Richard", "Susan", "Joseph", "Jessica",
            "Thomas", "Sarah", "Charles", "Karen", "Christopher", "Lisa", "Daniel", "Nancy",
            "Matthew", "Betty", "Anthony", "Margaret", "Mark", "Sandra", "Donald", "Ashley",
            "Steven", "Dorothy", "Paul", "Kimberly", "Andrew", "Emily", "Kenneth", "Donna"
        };
        String[] lastNames = {
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
            "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson",
            "Thomas", "Taylor", "Moore", "Jackson", "Martin", "Lee", "Perez", "Thompson",
            "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson", "Walker",
            "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores"
        };

// Create Customers
        CustomerDirectory customerdirectory = business.getCustomerDirectory();
        ArrayList<CustomerProfile> customerProfiles = new ArrayList<>();

        for (int i = 0; i < 300; i++) {
            String name = firstNames[random.nextInt(firstNames.length)]
                        + " " + lastNames[random.nextInt(lastNames.length)]
                        + " " + (i + 1);
            Person person = persondirectory.newPerson(name);
            CustomerProfile cp = customerdirectory.newCustomerProfile(person);
            customerProfiles.add(cp);
        }

        SupplierDirectory suplierdirectory = business.getSupplierDirectory();

        // Supplier and product name data for random generation
        String[] companyPrefixes = {
            "Global", "National", "Pacific", "Atlantic", "Premier", "Summit", "Apex",
            "Zenith", "Delta", "Omega", "Alpha", "Beta", "Sigma", "Prime", "Core",
            "Peak", "Eagle", "Horizon", "Pioneer", "Nexus", "Vertex", "Quantum",
            "Stellar", "Titan", "Vector", "Fusion", "Synergy", "Pinnacle", "Allied", "United",
            "Dynamic", "Rapid", "Elite", "Supreme", "Advanced", "Superior", "Essential",
            "Standard", "Universal", "Infinite"
        };
        String[] companySuffixes = {
            "Industries", "Solutions", "Technologies", "Enterprises", "Corp", "Group",
            "Systems", "Associates", "Partners", "International"
        };
        String[] productAdjectives = {
            "Premium", "Ultra", "Pro", "Elite", "Classic", "Advanced", "Deluxe",
            "Standard", "Essential", "Superior", "Enhanced", "Compact", "Heavy-Duty",
            "Lightweight", "Portable"
        };
        String[] productNouns = {
            "Scanner", "Printer", "Photocopier", "Fax Machine", "Plotter",
            "Label Maker", "Document Feeder", "Ink System", "Toner Unit", "Print Server"
        };

        // Create 50 Suppliers
        ArrayList<Supplier> allSuppliers = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            String supplierName = companyPrefixes[i % companyPrefixes.length]
                                + " " + companySuffixes[i % companySuffixes.length]
                                + " " + (i + 1);
            Supplier s = suplierdirectory.newSupplier(supplierName);
            allSuppliers.add(s);
        }

        // Pick any 30 Suppliers and add 50 Products to each
        ArrayList<Supplier> shuffledSuppliers = new ArrayList<>(allSuppliers);
        Collections.shuffle(shuffledSuppliers, random);
        ArrayList<Supplier> activeSuppliers = new ArrayList<>(shuffledSuppliers.subList(0, 30));

        ArrayList<Product> allProducts = new ArrayList<>();
        int productCounter = 1;

        for (Supplier supplier : activeSuppliers) {
            ProductCatalog productcatalog = supplier.getProductCatalog();
            for (int j = 0; j < 50; j++) {
                String adj = productAdjectives[random.nextInt(productAdjectives.length)];
                String noun = productNouns[random.nextInt(productNouns.length)];
                String productName = adj + " " + noun + " " + productCounter++;

                int floor   = 1000  + random.nextInt(9000);
                int ceiling = floor + 1000 + random.nextInt(9000);
                int target  = floor + random.nextInt(ceiling - floor);

                Product p = productcatalog.newProduct(productName, floor, ceiling, target);
                allProducts.add(p);
            }
        }

// Process Orders on behalf of sales person and customer
// Add orders so that every customer has from 1-3 orders
// Each order should have up to 10 items
        MasterOrderList masterorderlist = business.getMasterOrderList();

        for (CustomerProfile cp : customerProfiles) {
            int numOrders = 1 + random.nextInt(3);

            for (int o = 0; o < numOrders; o++) {
                Order order = masterorderlist.newOrder(cp, salespersonprofile);
                int numItems = 1 + random.nextInt(10);

                Collections.shuffle(allProducts, random);
                for (int it = 0; it < numItems; it++) {
                    Product p = allProducts.get(it);
                    int actualPrice = p.getFloorPrice()
                            + random.nextInt(p.getCeilingPrice() - p.getFloorPrice() + 1);
                    int quantity = 1 + random.nextInt(5);
                    order.newOrderItem(p, actualPrice, quantity);
                }
            }
        }

        return business;
    }

}