import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Project implements Serializable {
    private static final long serialVersionUID = -6533454118372883122L;
    private String name;
    private String image;
    private HashMap<Part, Integer> parts;
    
    public Project(String projectName, String imageName) {
        name = projectName;
        image = imageName;
        parts = new HashMap<>();
    }

    public Project(String projectName, String imageName, int[] quantities, Part[] partList) {
        name = projectName;
        image = imageName;
        parts = new HashMap<>();
        for(int i = 0; i < partList.length; i++) {
            parts.put(partList[i], quantities[i]);
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

    public HashMap<Part, Integer> getParts() {
        return parts;
    }

    public void setParts(HashMap<Part, Integer> parts) {
        this.parts = parts;
    }

    public void addPart(Part p, int quantity) {
        parts.put(p, quantity);
    }

    public void removePart(Part p) {
        parts.remove(p);
    }

    public double getCost() {
        double result = 0;
        for(Map.Entry<Part, Integer> entry: parts.entrySet()) {
            result += entry.getValue()*entry.getKey().getCost();
        }
        return result;
    }
}