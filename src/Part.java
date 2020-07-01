import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Part implements Serializable {
    private static final long serialVersionUID = 6483218562224594755L;
    private String name;
    private String image;
    private HashMap<Material, Integer> materials;

    public Part(String partName, String imageName) {
        name = partName;
        image = imageName;
        materials = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public HashMap<Material, Integer> getMaterials() {
        return materials;
    }
    
    public void setMaterials(HashMap<Material, Integer> materials) {
        this.materials = materials;
    }

    public void addMaterial(Material m, int quantity) {
        materials.put(m, quantity);
    }

    public void removeMaterial(Material m) {
        materials.remove(m);
    }

    public double getCost() {
        double result = 0;
        for(Map.Entry<Material, Integer> entry: materials.entrySet()) {
            if(!entry.getKey().isInStock()) {
                return Double.MAX_VALUE;
            }
            result += entry.getValue()*entry.getKey().getPrice();
        }
        return result;
    }
}