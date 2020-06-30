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

    public Part(Material[] materialList, int[] quantities) {
        materials = new HashMap<>();
        for(int i = 0; i < materialList.length; i++) {
            materials.put(materialList[i], quantities[i]);
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

    public HashMap<Material, Integer> getMaterials() {
        return materials;
    }
    
    public void setMaterials(HashMap<Material, Integer> materials) {
        this.materials = materials;
    }

    public double getCost() {
        double result = 0;
        for(Map.Entry<Material, Integer> entry: materials.entrySet()) {
            result += entry.getValue()*entry.getKey().getPrice();
        }
        return result;
    }
}