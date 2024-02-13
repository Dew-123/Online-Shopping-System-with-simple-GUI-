package Main;

public class Electronics extends Product{

    protected String brand;
    protected int warrantyPeriod;

    public Electronics(String productID, String productName, int numOfAvailableItems, double price, String brand, int warrantyPeriod) {
        super(productID, productName, numOfAvailableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return this.brand;
    }

    public int getWarrantyPeriod() {
        return this.warrantyPeriod;
    }

    @Override
    public String productType(){
        return "Electronics";
    }

    @Override
    public String printInfo() {
        return
                productID + ',' +
                productName + ',' +
                numOfAvailableItems + ','+
                price + ','+
                warrantyPeriod + ','+
                brand + ',' +
                "Electronic"
                ;

    }

    @Override
    public String info() {
        return brand+","+warrantyPeriod;
    }

    @Override
    public String infoGUI() {
        return "Product ID: " + productID+ "\n" + "Category: Electronics"+ "\n"+
                "Name: "+ productName+ "\n" +
                "Brand: "+ brand + "\n"+ "Warranty Period: "+ warrantyPeriod + "\n"+ "Items Available: "+
                numOfAvailableItems;
    }
}

