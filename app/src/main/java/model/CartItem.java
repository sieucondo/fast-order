package model;


public class CartItem {

    private Product product;
    private int quantity;
    private int totalPrices;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrices() {
        return this.product.getPrice() * quantity;
    }


    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if((this.product).equals(((CartItem)obj).getProduct())){
            return true;
        }
        return false;
    }


}
