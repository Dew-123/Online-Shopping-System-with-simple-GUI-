package Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class ShoppingCartGUI extends JFrame {
    private final JTable cartTable;
    private final ShoppingCart shoppingCart;
    private final JLabel totalLabel;
    private JLabel totalDescriptionLabel;
    private JLabel discountLabel;
    private JLabel discount2Lable;
    private JLabel discountDescriptionLabel;
    private JLabel finalTotalLabel;
    private JLabel finalTotalDescriptionLabel;
    private JLabel discountDescription2Label;

    public ShoppingCartGUI(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;

        cartTable = new JTable();
        updateCartTable();

        totalDescriptionLabel = new JLabel("Total");
        totalDescriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalLabel = new JLabel("0.00£");
        totalLabel.setBorder(new EmptyBorder(0,30,0,0));
        discountDescription2Label= new JLabel("First purchase Discount (10%)");
        discountDescription2Label.setHorizontalAlignment(SwingConstants.RIGHT);
        discount2Lable = new JLabel("- 0.00£");
        discount2Lable.setBorder(new EmptyBorder(0,30,0,0));
        discountDescriptionLabel = new JLabel("Three items in same Category Discount (20%)");
        discountDescriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        discountLabel = new JLabel("- 0.00£");
        discountLabel.setBorder(new EmptyBorder(0,30,0,0));
        finalTotalDescriptionLabel = new JLabel("Final Total");
        finalTotalDescriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        finalTotalLabel = new JLabel("0.00£");
        finalTotalLabel.setBorder(new EmptyBorder(0,30,0,0));


        updateFinalTotal();

        JPanel tablePanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBorder(new EmptyBorder(40,50,40,50));
        tablePanel.add(scrollPane, BorderLayout.CENTER);


        JPanel totalPanel = new JPanel(new GridLayout(4, 2));
        totalPanel.add(totalDescriptionLabel);
        totalPanel.add(totalLabel);
        totalPanel.add(discountDescription2Label);
        totalPanel.add(discount2Lable);
        totalPanel.add(discountDescriptionLabel);
        totalPanel.add(discountLabel);
        totalPanel.add(finalTotalDescriptionLabel);
        totalPanel.add(finalTotalLabel);

        totalPanel.setBorder(new EmptyBorder(10,10,10,10));
        tablePanel.add(totalPanel, BorderLayout.SOUTH);

        add(tablePanel);

        setSize(600, 400);
    }

    private void updateCartTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product");
        model.addColumn("Quantity");
        model.addColumn("Price");

        for (Map.Entry<Product,Integer> entry:shoppingCart.getProductQuantity().entrySet()){
            Product product = entry.getKey();
            int count = entry.getValue();
            model.addRow(new Object[]{getProductDetails(product),count,product.getPrice()});
        }

        cartTable.setModel(model);
    }

    private String getProductDetails(Product product) {
        if (product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            return product.getProductID()+'\n'+product.getProductName()+'\n'+ clothing.getSize()+'\n'+ clothing.getColour();
        } else if (product instanceof Electronics) {
            Electronics electronics = (Electronics) product;
            return product.getProductID()+'\n'+ product.getProductName()+'\n'+ electronics.getWarrantyPeriod();
        } else {
            return "Unknown Product Type";
        }
    }

    private void updateFinalTotal(){
        double total = calculateTotal();
        double discount10 = calculateDiscount10();
        double discount20 = calculateDiscount20();
        double finalTotal = calculateFinalTotal();

        totalLabel.setText(String.format("%.2f£", total));
        discount2Lable.setText(String.format("- %.2f£", discount10));
        discountLabel.setText(String.format("- %.2f£", discount20));
        finalTotalLabel.setText(String.format("%.2f£", finalTotal));
    }

    private double calculateFinalTotal(){
        double total = calculateTotal();
        double discount10 = calculateDiscount10();
        double discount20 = calculateDiscount20();

        return total-(discount10+discount20);
    }

    private double calculateTotal(){
        double total =0;
        for (Map.Entry<Product,Integer> entry:shoppingCart.getProductQuantity().entrySet()){ //iterating over the entries(pair of key and value)
            Product product = entry.getKey();
            int count = entry.getValue();
            total+=product.getPrice()*count;
        }
        return total;
    }

    private double calculateDiscount10(){
        double total = calculateTotal();
        if (shoppingCart.discountEligible10()){
            return total*0.1;
        }else {
            return 0;
        }
    }

    private double calculateDiscount20() {
        double total = calculateTotal();
        if (shoppingCart.discountEligible()) {
            return total * 0.2;
        } else {
            return 0;
        }
    }
}
