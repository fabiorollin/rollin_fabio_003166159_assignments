package TheBusiness;

import MarketingManagement.MarketingPersonDirectory;
import MarketingManagement.MarketingPersonProfile;
import TheBusiness.Business.Business;
import TheBusiness.MarketModel.ChannelCatalog;
import TheBusiness.CustomerManagement.CustomerDirectory;
import TheBusiness.CustomerManagement.CustomerProfile;
import TheBusiness.MarketModel.Channel;
import TheBusiness.MarketModel.Market;
import TheBusiness.MarketModel.MarketCatalog;
import TheBusiness.MarketModel.MarketChannelAssignment;
import TheBusiness.MarketModel.MarketChannelComboCatalog;
import TheBusiness.MarketModel.SolutionOffer;
import TheBusiness.MarketModel.SolutionOfferCatalog;
import TheBusiness.OrderManagement.MasterOrderList;
import TheBusiness.SolutionOrders.MasterSolutionOrderList;
import TheBusiness.OrderManagement.Order;
import TheBusiness.OrderManagement.OrderItem;
import TheBusiness.Personnel.EmployeeDirectory;
import TheBusiness.Personnel.EmployeeProfile;
import TheBusiness.Personnel.Person;
import TheBusiness.Personnel.PersonDirectory;
import TheBusiness.ProductManagement.Product;
import TheBusiness.ProductManagement.ProductSummary;
import TheBusiness.ProductManagement.ProductCatalog;
import TheBusiness.SalesManagement.SalesPersonDirectory;
import TheBusiness.SalesManagement.SalesPersonProfile;
import TheBusiness.SolutionOrders.SolutionOrder;
import TheBusiness.Supplier.Supplier;
import TheBusiness.Supplier.SupplierDirectory;
import TheBusiness.UserAccountManagement.UserAccount;
import TheBusiness.UserAccountManagement.UserAccountDirectory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ConfigureABusiness {

    static Business initialize() {
        Business business = new Business("Xerox");
        Random random = new Random(42); // seeded so data is reproducible

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
 //       EmployeeDirectory employeedirectory = business.getEmployeeDirectory();
 //       EmployeeProfile employeeprofile0 = employeedirectory.newEmployeeProfile(xeroxadminperson001);

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
                        + " " + (i + 1); // number ensures uniqueness
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
            int numOrders = 1 + random.nextInt(3); // 1, 2, or 3 orders per customer

            for (int o = 0; o < numOrders; o++) {
                Order order = masterorderlist.newOrder(cp, salespersonprofile);
                int numItems = 1 + random.nextInt(10); // up to 10 items per order

                Collections.shuffle(allProducts, random);
                for (int it = 0; it < numItems; it++) {
                    Product p = allProducts.get(it);
                    // Actual price is set within the product's floor-ceiling range
                    int actualPrice = p.getFloorPrice()
                            + random.nextInt(p.getCeilingPrice() - p.getFloorPrice() + 1);
                    int quantity = 1 + random.nextInt(5);
                    order.newOrderItem(p, actualPrice, quantity);
                }
            }
        }

//===============================

        MarketCatalog mc = business.getMarketCatalog();
        ChannelCatalog cc = business.getChannelCatalog();
        MarketChannelComboCatalog mccc = business.getMarketChannelComboCatalog();

        /* 1) Define the business
           2) Define markets
           3) Define valid channels
           4) Define market/channel combs
           5) Prepare product catalogs for different suppliers
           ---
           6) Define solution offers based on the suppliers' products
           7) Link solutions offers to market/channel combs
           8) Create solution orders..
        */

        return business;
    }

    static Business initializeMarkets() {
        Business business = new Business("Xerox");

// Create Persons
        PersonDirectory persondirectory = business.getPersonDirectory();
// person representing sales organization        
        Person xeroxsalesperson001 = persondirectory.newPerson("Xerox sales");
        Person xeroxmarketingperson001 = persondirectory.newPerson("Xerox marketing");

// Create Customers
        CustomerDirectory customedirectory = business.getCustomerDirectory();
        CustomerProfile customerprofile1
                = customedirectory.newCustomerProfile(xeroxsalesperson001);

// Create Sales people
        SalesPersonDirectory salespersondirectory = business.getSalesPersonDirectory();
        SalesPersonProfile salespersonprofile = salespersondirectory.newSalesPersonProfile(xeroxsalesperson001);

        // Create Marketing people
        MarketingPersonDirectory marketingpersondirectory = business.getMarketingPersonDirectory();
        MarketingPersonProfile marketingpersonprofile0 = marketingpersondirectory.newMarketingPersonProfile(xeroxmarketingperson001);

        SupplierDirectory suplierdirectory = business.getSupplierDirectory();

        Supplier supplier1 = suplierdirectory.newSupplier("Lenovo");
        ProductCatalog productcatalog = supplier1.getProductCatalog();
        Product products1p1 = productcatalog.newProduct("Scanner 3  1", 2000, 16500, 10000);
        Product products1p2 = productcatalog.newProduct("Scanner 4", 10000, 25000, 16500);
        Product products1p3 = productcatalog.newProduct("Printer 2", 22000, 40000, 36500);
        Product products1p4 = productcatalog.newProduct("Photocopier 2 ", 30000, 70000, 50000);
        Product products1p5 = productcatalog.newProduct("Scanner  5", 19000, 36500, 25000);
        Product products1p6 = productcatalog.newProduct("Scanner 6", 90000, 125000, 105000);
        Product products1p7 = productcatalog.newProduct("Printer 3", 22000, 60000, 36500);
        Product products1p8 = productcatalog.newProduct("Photocopier 3", 30000, 70000, 50000);

        //       SupplierDirectory suplierdirectory = business.getSupplierDirectory();
        Supplier supplier2 = suplierdirectory.newSupplier("Epson");
        productcatalog = supplier2.getProductCatalog();
        Product products2p1 = productcatalog.newProduct("Scanner 13  1", 12000, 26000, 18500);
        Product products2p2 = productcatalog.newProduct("Scanner 14", 90000, 165000, 125000);
        Product products2p3 = productcatalog.newProduct("Color Printer 112", 422000, 540000, 495000);
        Product products2p4 = productcatalog.newProduct("Photocopier 922 ", 430000, 890000, 550000);
        Product products2p5 = productcatalog.newProduct("Low toner Scanner  102", 195000, 500100, 365102);
        Product products2p6 = productcatalog.newProduct("Speedy color Scanner 611", 900000, 125000, 1650000);
        Product products2p7 = productcatalog.newProduct("Premier Printer 300", 322000, 470000, 736500);
        Product products2p8 = productcatalog.newProduct("Color Photocopier 500", 350000, 580000, 780000);

//=============== Define markets and channels...

        MarketCatalog mc = business.getMarketCatalog();
        Market teenmarket = mc.newMarket("Teenagers");
        Market teenmarket2 = mc.newMarket("College Grads");

        ChannelCatalog channelCatalog = business.getChannelCatalog();

        Channel tvchannel = channelCatalog.newChannel("tv");
        Channel webchannel = channelCatalog.newChannel("web");

        teenmarket.addValidChannel(webchannel);
        teenmarket.addValidChannel(tvchannel);

        MarketChannelComboCatalog mccc = business.getMarketChannelComboCatalog();

        MarketChannelAssignment tvchannelteenmarket = mccc.newMarketChannelCombo(teenmarket, tvchannel);
        MarketChannelAssignment webchannelteenmarket = mccc.newMarketChannelCombo(teenmarket, webchannel);

        SolutionOfferCatalog solutionoffercatalog = business.getSolutionOfferCatalog();

        SolutionOffer solutiontvteen = solutionoffercatalog.newSolutionOffer(tvchannelteenmarket);
        solutiontvteen.addProduct(products2p2);
        solutiontvteen.addProduct(products2p1);
        solutiontvteen.setTotalPrice(1000);

        SolutionOffer solutionwebteen = solutionoffercatalog.newSolutionOffer(webchannelteenmarket);
        solutionwebteen.addProduct(products2p2);
        solutionwebteen.addProduct(products2p1);
        solutionwebteen.setTotalPrice(500);

        MasterSolutionOrderList msol = business.getMasterSolutionOrderList();

        SolutionOrder so = msol.newSolutionOrder(solutiontvteen, tvchannelteenmarket);
        SolutionOrder so2 = msol.newSolutionOrder(solutionwebteen, webchannelteenmarket);

        msol.getRevenueByMarketChannelCombo(tvchannelteenmarket);
        msol.getRevenueByChannel(tvchannel);

        return business;
    }
}