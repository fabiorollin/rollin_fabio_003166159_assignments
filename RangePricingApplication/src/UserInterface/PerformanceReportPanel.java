/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UserInterface;

import TheBusiness.Business.Business;
import TheBusiness.CustomerManagement.CustomerDirectory;
import TheBusiness.CustomerManagement.CustomerProfile;
import TheBusiness.OrderManagement.MasterOrderList;
import TheBusiness.OrderManagement.Order;
import TheBusiness.OrderManagement.OrderItem;
import TheBusiness.ProductManagement.Product;
import TheBusiness.Supplier.Supplier;
import TheBusiness.Supplier.SupplierDirectory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author fabio
 */
public class PerformanceReportPanel extends javax.swing.JPanel {

    /**
     * Creates new form PerformanceReportPanel
     */
    private Business business;
    private javax.swing.JPanel CardSequencePanel;
    
    public PerformanceReportPanel(Business business,javax.swing.JPanel cardPanel) {
        this.business = business;
        this.CardSequencePanel = cardPanel;
        initComponents();
        initReportPanel();
    }
    
    public void initReportPanel() {
        reportComboBox.removeAllItems();
        reportComboBox.addItem("Report 1: Most Expensive Products");
        reportComboBox.addItem("Report 2: Most Valuable Customers");
        reportComboBox.addItem("Report 3: Supplier Report");
    }
    
     public void loadSelectedReport() {
        int selected = reportComboBox.getSelectedIndex();
        switch (selected) {
            case 0: loadReport1(); break;
            case 1: loadReport2(); break;
            case 2: loadReport3(); break;
        }
     }
     
    private void loadReport1() {
        String[] columns = {"Product Name", "Supplier Name", "Target Price"};
        List<Object[]> rows = new ArrayList<>();

        SupplierDirectory supplierDir = business.getSupplierDirectory();
        for (Supplier supplier : supplierDir.getSupplierList()) {
            for (Product p : supplier.getProductCatalog().getProductList()) {
                rows.add(new Object[]{
                    p.toString(),
                    supplier.getName(),
                    p.getTargetPrice()
                });
            }
        }

        rows.sort((a, b) -> Integer.compare((int) b[2], (int) a[2]));
        Object[][] data = rows.toArray(new Object[0][]);

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public Class<?> getColumnClass(int col) {
                return col == 2 ? Integer.class : String.class;
            }
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        reportTable.setModel(model);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        reportTable.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
    }

    private void loadReport2() {
        String[] columns = {"Customer Name", "Total Sales"};

        MasterOrderList mol = business.getMasterOrderList();
        Map<CustomerProfile, Integer> salesMap = new HashMap<>();

        for (Order order : mol.getOrders()) {
            CustomerProfile cp = order.getCustomer();
            salesMap.merge(cp, order.getOrderTotal(), Integer::sum);
        }

        List<Object[]> rows = new ArrayList<>();
        for (Map.Entry<CustomerProfile, Integer> entry : salesMap.entrySet()) {
            rows.add(new Object[]{
                entry.getKey().toString(),
                entry.getValue()
            });
        }

        rows.sort((a, b) -> Integer.compare((int) b[1], (int) a[1]));
        Object[][] data = rows.toArray(new Object[0][]);

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public Class<?> getColumnClass(int col) {
                return col == 1 ? Integer.class : String.class;
            }
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        reportTable.setModel(model);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        reportTable.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
    }

    private void loadReport3() {
        String[] columns = {"Supplier Name", "Total Sales", "Loyalty Score",
                            "Avg Spend/Customer", "Top 5 Sales Score"};

        MasterOrderList mol = business.getMasterOrderList();
        CustomerDirectory customerDir = business.getCustomerDirectory();
        SupplierDirectory supplierDir = business.getSupplierDirectory();
        int totalCustomers = customerDir.getCustomers().size();

        // Build the SupplierReport using SupplierSummary objects
        TheBusiness.Supplier.SupplierReport supplierReport = 
            new TheBusiness.Supplier.SupplierReport();

        for (Supplier supplier : supplierDir.getSupplierList()) {
            Set<Product> supplierProducts = new HashSet<>(
                supplier.getProductCatalog().getProductList()
            );

            Map<CustomerProfile, Integer> spendByCustomer = new HashMap<>();

            for (Order order : mol.getOrders()) {
                for (OrderItem item : order.getOrderItems()) {
                    if (supplierProducts.contains(item.getSelectedProduct())) {
                        CustomerProfile cp = order.getCustomer();
                        spendByCustomer.merge(cp, item.getOrderItemTotal(), Integer::sum);
                    }
                }
            }

            int totalSales = spendByCustomer.values().stream()
                                .mapToInt(Integer::intValue).sum();
            int uniqueCustomers = spendByCustomer.size();

            double loyaltyScore = totalCustomers > 0
                    ? (double) uniqueCustomers / totalCustomers : 0;
            double avgSpend = uniqueCustomers > 0
                    ? (double) totalSales / uniqueCustomers : 0;

            List<Integer> spends = new ArrayList<>(spendByCustomer.values());
            spends.sort((a, b) -> Integer.compare(b, a));
            int top5Sum = 0;
            for (int i = 0; i < Math.min(5, spends.size()); i++) {
                top5Sum += spends.get(i);
            }
            double top5Score = totalSales > 0 ? (double) top5Sum / totalSales : 0;

            // Create a SupplierSummary and add it to the SupplierReport
            TheBusiness.Supplier.SupplierSummary summary =
                new TheBusiness.Supplier.SupplierSummary(
                    supplier.getName(), totalSales, loyaltyScore, avgSpend, top5Score
                );
            supplierReport.addSummary(summary);
        }

        // Build the table rows from the SupplierReport
        List<Object[]> rows = new ArrayList<>();
        for (TheBusiness.Supplier.SupplierSummary s : supplierReport.getSummaries()) {
            rows.add(new Object[]{
                s.getSupplierName(),
                s.getTotalSales(),
                String.format("%.4f", s.getLoyaltyScore()),
                String.format("%.2f", s.getAvgSpendPerCustomer()),
                String.format("%.4f", s.getTop5SalesScore())
            });
        }

        Object[][] data = rows.toArray(new Object[0][]);
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        reportTable.setModel(model);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        reportTable.setRowSorter(sorter);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        reportComboBox = new javax.swing.JComboBox<>();
        reportScrollPane = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        loadReportButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reportComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(reportComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, -1));

        reportScrollPane.setFocusable(false);
        reportScrollPane.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        reportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        reportScrollPane.setViewportView(reportTable);

        add(reportScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 830, 650));

        loadReportButton.setText("Load Report");
        loadReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadReportButtonActionPerformed(evt);
            }
        });
        add(loadReportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 800, -1, -1));

        jButton1.setText("<< Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 800, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void loadReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadReportButtonActionPerformed
        // TODO add your handling code here:
        loadSelectedReport();
    }//GEN-LAST:event_loadReportButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         ((java.awt.CardLayout) CardSequencePanel.getLayout()).previous(CardSequencePanel);
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton loadReportButton;
    private javax.swing.JComboBox<String> reportComboBox;
    private javax.swing.JScrollPane reportScrollPane;
    private javax.swing.JTable reportTable;
    // End of variables declaration//GEN-END:variables
}
