import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Part implements Serializable {
    private static final long serialVersionUID = 6483218562224594755L;
    private String name;
    private String image;
    private HashMap<Integer, Material> materials;
    
    public Part(int[] quantities, Material[] materialList) {
        materials = new HashMap<>();
        for(int i = 0; i < materialList.length; i++) {
            materials.put(quantities[i], materialList[i]);
        }
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

    public HashMap<Integer, Material> getMaterials() {
        return materials;
    }
    
    public void setMaterials(HashMap<Integer, Material> materials) {
        this.materials = materials;
    }

    public double getCost() {
        double result = 0;
        for(Map.Entry<Integer, Material> entry: materials.entrySet()) {
            result += entry.getKey()*entry.getValue().getPrice();
        }
        return result;
    }
}