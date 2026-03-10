package TheBusiness;

import UserInterface.Main.PricingMainFrame;

public class RangePricingApplication {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PricingMainFrame().setVisible(true);
            }
        });
    }

}