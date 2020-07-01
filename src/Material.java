import java.io.Serializable;

public class Material implements Serializable {
    private static final long serialVersionUID = 8578590147803113853L;
    private String name;
    private double price;
    private String image;
    private boolean inStock;
    
    public Material(String name, double price, String image, boolean inStock) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.inStock = inStock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
}