package TheBusiness.Supplier;

import java.util.ArrayList;

public class SupplierReport {
    private ArrayList<SupplierSummary> summaries = new ArrayList<>();

    public void addSummary(SupplierSummary summary) {
        summaries.add(summary);
    }

    public ArrayList<SupplierSummary> getSummaries() {
        return summaries;
    }
}