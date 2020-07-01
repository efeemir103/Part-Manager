import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class FileManager {
    static ArrayList<Project> loadProjects() throws IOException {
        ArrayList<Project> projects = new ArrayList<>();
        
        // Check if file containing lists of projects exist.
        File projectListFile =  new File("data/projects.dat");

        if(projectListFile.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(projectListFile));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                try {
                    projects.add(loadProject(line));
                } catch(FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch(IOException e2) {
                    e2.printStackTrace();
                } catch(ClassNotFoundException e3) {
                    e3.printStackTrace();
                }
            }

            bufferedReader.close();

        }

        return projects;
    }
    
    static Project loadProject(String name) throws FileNotFoundException, IOException, ClassNotFoundException {
        Project p;

        // Check if file containing lists of projects exist.
        FileInputStream projectFile =  new FileInputStream("data/projects/" + name + ".bin");

        ObjectInputStream oStream = new ObjectInputStream(projectFile);

        p = (Project) oStream.readObject();

        oStream.close();
        projectFile.close();

        return p;
    }

    static void addNewProject(Project p) throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("data/projects.dat", true));

        bufferedWriter.write(p.getName() + "\n");

        bufferedWriter.close();
        
        saveProject(p);
    }

    static void saveProject(Project p) throws IOException{
        FileOutputStream projectFile = new FileOutputStream("data/projects/" + p.getName() + ".bin");
        ObjectOutputStream oStream = new ObjectOutputStream(projectFile);
        oStream.writeObject(p);
        oStream.close();
        projectFile.close();
    }

    static void deleteProject(String name) throws IOException {
        File projectFile = new File("data/projects/" + name + ".bin");
        projectFile.delete();

        File projectImage = new File("data/images/projects/" + name + ".png");
        projectImage.delete();
        
        File temp = new File("data/temp.dat");
        File projectsFile = new File("data/projects.dat");

        BufferedReader reader = new BufferedReader(new FileReader(projectsFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

        String line;

        while((line = reader.readLine()) != null) {
            String trimmedLine = line.trim();
            if(trimmedLine.equals(name)){
                continue;
            }
            writer.write(line + System.getProperty("line.separator"));
        }
        writer.close(); 
        reader.close(); 

        temp.renameTo(projectsFile);
    }

    static ArrayList<Part> loadParts() throws IOException {
        ArrayList<Part> parts = new ArrayList<>();
        
        // Check if file containing lists of projects exist.
        File partListFile =  new File("data/parts.dat");

        if(partListFile.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(partListFile));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                try {
                    parts.add(loadPart(line));
                } catch(FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch(IOException e2) {
                    e2.printStackTrace();
                } catch(ClassNotFoundException e3) {
                    e3.printStackTrace();
                }
            }

            bufferedReader.close();

        }

        return parts;
    }

    static Part loadPart(String name) throws FileNotFoundException, IOException, ClassNotFoundException {
        Part p;

        // Check if file containing lists of projects exist.
        FileInputStream partFile =  new FileInputStream("data/parts/" + name + ".bin");

        ObjectInputStream oStream = new ObjectInputStream(partFile);

        p = (Part) oStream.readObject();

        oStream.close();
        partFile.close();

        return p;
    }

    static void addNewPart(Part p) throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("data/parts.dat", true));

        bufferedWriter.write(p.getName() + "\n");

        bufferedWriter.close();
        
        savePart(p);
    }

    static void savePart(Part p) throws IOException{
        FileOutputStream partFile = new FileOutputStream("data/parts/" + p.getName() + ".bin");
        ObjectOutputStream oStream = new ObjectOutputStream(partFile);
        oStream.writeObject(p);
        oStream.close();
        partFile.close();
    }

    static void deletePart(String name) throws IOException {
        File partFile = new File("data/parts/" + name + ".bin");
        partFile.delete();

        File partImage = new File("data/images/parts/" + name + ".png");
        partImage.delete();
        
        File temp = new File("data/temp.dat");
        File partsFile = new File("data/parts.dat");

        BufferedReader reader = new BufferedReader(new FileReader(partsFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

        String line;

        while((line = reader.readLine()) != null) {
            String trimmedLine = line.trim();
            if(trimmedLine.equals(name)){
                continue;
            }
            writer.write(line + System.getProperty("line.separator"));
        }
        writer.close(); 
        reader.close(); 

        temp.renameTo(partsFile);
    }

    static ArrayList<Material> loadMaterials() throws IOException {
        ArrayList<Material> materials = new ArrayList<>();
        
        // Check if file containing lists of projects exist.
        File materialListFile =  new File("data/materials.dat");

        if(materialListFile.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(materialListFile));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                try {
                    materials.add(loadMaterial(line));
                } catch(FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch(IOException e2) {
                    e2.printStackTrace();
                } catch(ClassNotFoundException e3) {
                    e3.printStackTrace();
                }
            }

            bufferedReader.close();

        }

        return materials;
    }

    static Material loadMaterial(String name) throws FileNotFoundException, IOException, ClassNotFoundException {
        Material p;

        // Check if file containing lists of projects exist.
        FileInputStream materialFile =  new FileInputStream("data/materials/" + name + ".bin");

        ObjectInputStream oStream = new ObjectInputStream(materialFile);

        p = (Material) oStream.readObject();

        oStream.close();
        materialFile.close();

        return p;
    }

    static void addNewMaterial(Material m) throws IOException{
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("data/materials.dat", true));

        bufferedWriter.write(m.getName() + "\n");

        bufferedWriter.close();
        
        saveMaterial(m);
    }

    static void saveMaterial(Material m) throws IOException{
        FileOutputStream materialFile = new FileOutputStream("data/materials/" + m.getName() + ".bin");
        ObjectOutputStream oStream = new ObjectOutputStream(materialFile);
        oStream.writeObject(m);
        oStream.close();
        materialFile.close();
    }

    static void deleteMaterial(String name) throws IOException {
        File materialFile = new File("data/materials/" + name + ".bin");
        materialFile.delete();

        File materialImage = new File("data/images/materials/" + name + ".png");
        materialImage.delete();
        
        File temp = new File("data/temp.dat");
        File materialsFile = new File("data/materials.dat");

        BufferedReader reader = new BufferedReader(new FileReader(materialsFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

        String line;

        while((line = reader.readLine()) != null) {
            String trimmedLine = line.trim();
            if(trimmedLine.equals(name)){
                continue;
            }
            writer.write(line + System.getProperty("line.separator"));
        }
        writer.close(); 
        reader.close(); 

        temp.renameTo(materialsFile);
    }

    static String getImageResource(String absolutePath, String name, String resourceClass) throws IOException {
        File imageSourceFile = new File(absolutePath);
        File imageDestinationFile = new File("data/images/" + resourceClass + "s/" + name + ".png");
        
        BufferedImage inputImage = ImageIO.read(imageSourceFile);
        BufferedImage outputImage = new BufferedImage(50, 50, inputImage.getType());
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, 50, 50, null);
        g2d.dispose();
 
        // writes to output file
        ImageIO.write(outputImage, "png", imageDestinationFile);

        return imageDestinationFile.getPath();
    }
}