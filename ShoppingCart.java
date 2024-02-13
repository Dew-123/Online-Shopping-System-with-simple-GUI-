package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private static List<Product> cartProductList;
    private Map<Product,Integer> productQuantity;
    private User owner;

    public ShoppingCart(User owner) {
        this.cartProductList = new ArrayList<>();
        this.productQuantity = new HashMap<>();
        this.owner= owner;
    }

    public void addProductToCart(Product product){
        int count = productQuantity.getOrDefault(product,0);
        productQuantity.put(product,count+1);
        this.cartProductList.add(product);

    }

    public List<Product> getProducts() {
        return cartProductList;
    }

    public Map<Product,Integer> getProductQuantity(){
        return productQuantity;
    }

    public boolean discountEligible() {
        Map<String, Integer> categoryCount = new HashMap<>();

        for (Product product : cartProductList) {
            String category = getCategoryFromProduct(product);
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }

        // Check if the user has bought three or more products from the same category
        for (int count : categoryCount.values()) {
            if (count >= 3) {
                return true; // Eligible for a 20% discount
            }
        }

        return false; // Not eligible for a discount
    }

    private String getCategoryFromProduct(Product product) {
        if (product instanceof Electronics) {
            return "Electronics";
        } else if (product instanceof Clothing) {
            return "Clothing";
        } else {
            return "Unknown"; // Adjust accordingly for other product types
        }
    }


    public boolean discountEligible10(){
        if (owner.isFirstPurchase()){
            return true;
        }else {
            return false;
        }
    }


}
