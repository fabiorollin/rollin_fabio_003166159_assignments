package TheBusiness.Supplier;

public class SupplierSummary {
    private String supplierName;
    private int totalSales;
    private double loyaltyScore;
    private double avgSpendPerCustomer;
    private double top5SalesScore;

    public SupplierSummary(String supplierName, int totalSales,
                           double loyaltyScore, double avgSpendPerCustomer,
                           double top5SalesScore) {
        this.supplierName = supplierName;
        this.totalSales = totalSales;
        this.loyaltyScore = loyaltyScore;
        this.avgSpendPerCustomer = avgSpendPerCustomer;
        this.top5SalesScore = top5SalesScore;
    }

    public String getSupplierName()        { return supplierName; }
    public int getTotalSales()             { return totalSales; }
    public double getLoyaltyScore()        { return loyaltyScore; }
    public double getAvgSpendPerCustomer() { return avgSpendPerCustomer; }
    public double getTop5SalesScore()      { return top5SalesScore; }
}