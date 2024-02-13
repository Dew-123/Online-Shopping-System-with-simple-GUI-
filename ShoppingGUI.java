package Main;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

//Main GUI class for shopping application
public class ShoppingGUI extends JFrame {

    private JTable productTable;
    private JTextArea productInfo;
    private WestminsterShoppingManager westminsterShoppingManager;
    private User loggedInUser;
    private ShoppingCart shoppingCart;
    private List<Product> products2;
    private List<Product> categorizedProductsList;

    // Update the table with the given product list
    public void updateTable(List<Product> productList) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Category");
        model.addColumn("Price");
        model.addColumn("Info");

        for (Product product : productList) {
            model.addRow(new Object[]{product.getProductID(), product.getProductName(), product.productType(), product.getPrice(), product.info()});
        }

        productTable.setModel(model);
        applyRowColor(productList);
    }

    // Filter products by category and update the table accordingly
    private void filterByCategory(String category) {
        if (category.equals("All")) {
            updateTable(products2);
            categorizedProductsList = new ArrayList<>(products2);
        } else {
            categorizedProductsList = new ArrayList<>();
            for (Product product : products2) {
                if ((category.equals("Electronics") && product.productType().equals("Electronics")) ||
                        (category.equals("Clothing") && product.productType().equals("Clothing"))) {
                    categorizedProductsList.add(product);
                }
            }
            updateTable(categorizedProductsList);
        }
    }

    // Display details of the selected product in the text area
    private void displayProductDetails(int selectedRow) {
        Product selectedProduct = categorizedProductsList.get(selectedRow);
        productInfo.setText("Selected Product - Details \n");
        productInfo.append(selectedProduct.infoGUI());
    }

    // Constructor for ShoppingGUI
    public ShoppingGUI(WestminsterShoppingManager westminsterShoppingManager1, User loggedInUser) {
        try {
            this.westminsterShoppingManager = westminsterShoppingManager1;
            this.products2 = westminsterShoppingManager1.getSortedList();
            this.loggedInUser = loggedInUser;
            this.shoppingCart = new ShoppingCart(this.loggedInUser);

            productTable = new JTable();
            filterByCategory("All");

            productInfo = new JTextArea();
            productTable.getSelectionModel().addListSelectionListener(e -> {  //to track currently selected row
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    displayProductDetails(selectedRow);
                }
            });

            JPanel tablepanel = new JPanel(new BorderLayout());
            JScrollPane scrollPane = new JScrollPane(productTable);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
            tablepanel.add(scrollPane, BorderLayout.CENTER);
            tablepanel.add(productInfo, BorderLayout.SOUTH);

            JComboBox dropDownList = new JComboBox(new String[]{"All", "Electronics", "Clothing"});
            dropDownList.addActionListener(e -> filterByCategory((String) dropDownList.getSelectedItem())); //String casting to ensure the selected item is treated as a String

            JButton viewCartButton = new JButton("Shopping Cart");
            viewCartButton.addActionListener(e -> openShoppingCart());

            JPanel controlpanel = new JPanel(new GridLayout(1, 3));
            controlpanel.add(Box.createHorizontalStrut(3));
            controlpanel.add(new JLabel("Select product category"));
            controlpanel.add(dropDownList);
            controlpanel.add(Box.createHorizontalStrut(8));
            controlpanel.add(viewCartButton);


            JPanel p1 = new JPanel(new BorderLayout());
            p1.add(controlpanel, BorderLayout.NORTH);
            p1.add(tablepanel, BorderLayout.CENTER);

            JButton addToCartBtn = new JButton("Add to Shopping Cart");
            addToCartBtn.addActionListener(e -> {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow>=0 && selectedRow<categorizedProductsList.size()){
                    Product selectedProduct = categorizedProductsList.get(selectedRow);
                    shoppingCart.addProductToCart(selectedProduct);
                }else {
                    JOptionPane.showMessageDialog(this, "Please select a valid product to add to the cart.", "Error", JOptionPane.ERROR_MESSAGE); //https://www.javatpoint.com/java-joptionpane
                }
            });

            JPanel p2 = new JPanel();
            p2.add(addToCartBtn);

            add(p1, BorderLayout.CENTER);
            add(p2, BorderLayout.SOUTH);
            setTitle("Westminster Shopping Centre");
            setSize(800, 600);

            // Add a window listener to handle the window closing event to update first purchase
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    loggedInUser.setFirstPurchase(false);
                    westminsterShoppingManager.saveUsersToFile();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Open the shopping cart GUI
    private void openShoppingCart() {
        ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(shoppingCart);
        shoppingCartGUI.setVisible(true);
    }

    private Product getProductById(String productID, List<Product> productList) {
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                return product;
            }
        }
        return null;
    }

    // Apply different row colors based on product availability
    private void applyRowColor(List<Product> productList) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String productID = (String) table.getValueAt(row, 0);
                Product product = getProductById(productID, productList);

                if (product != null && product.getNumOfAvailableItems() < 3) {
                    c.setBackground(Color.RED);
                } else {
                    c.setBackground(table.getBackground());
                }
                return c;
            }
        };
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }
}
