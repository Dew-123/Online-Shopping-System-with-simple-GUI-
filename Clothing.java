package Main;

public class Clothing extends Product{

    protected String size;
    protected String colour;

    public Clothing(String productID, String productName, int numOfAvailableItems, double price, String size, String colour) {
        super(productID, productName, numOfAvailableItems, price);
        this.size = size;
        this.colour = colour;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return this.colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String printInfo() {
        return
                productID + ','+
                productName + ',' +
                numOfAvailableItems + ','+
                price + ','+
                colour + ',' +
                size + ','+
                "Clothing"
                ;
    }

    @Override
    public String productType() {
        return "Clothing";
    }

    @Override
    public String info() {
        return size+","+colour;
    }

    @Override
    public String infoGUI(){
        return "Product ID: " + productID+ "\n" + "Category: Clothing"+ "\n"+
               "Name: "+ productName+ "\n" +
                "Size: "+ size + "\n"+ "Colour: "+ colour + "\n"+ "Items Available: "+
                numOfAvailableItems;
    }
}

