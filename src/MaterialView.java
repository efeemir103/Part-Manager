import java.io.FileNotFoundException;
import java.io.IOException;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MaterialView implements View {
    private String name;
    private Material material;
    private JLabel materialLabel;
    private JLabel materialStockLabel;
    private JLabel materialPriceLabel;
    private JFrame window;
    
    public MaterialView(String materialName) {
        name = materialName;
        material = null;
        window = new JFrame();

        JPanel materialInfo = new JPanel();

        materialLabel = new JLabel("Material: [Unknown]");
		materialLabel.setHorizontalAlignment(JLabel.CENTER);
        materialLabel.setFont(materialLabel.getFont().deriveFont(24f));

        materialStockLabel = new JLabel("Material is not in stock.");
        materialStockLabel.setHorizontalAlignment(JLabel.CENTER);
        materialStockLabel.setFont(materialStockLabel.getFont().deriveFont(22f));

        materialPriceLabel = new JLabel("Material Unit Price: [Unknown]");
        materialPriceLabel.setHorizontalAlignment(JLabel.CENTER);
        materialPriceLabel.setFont(materialPriceLabel.getFont().deriveFont(16f));

        materialInfo.setLayout(new GridLayout(3, 1, 10, 10));
        materialInfo.add(materialLabel);
        materialInfo.add(materialStockLabel);
        materialInfo.add(materialPriceLabel);

        window.getContentPane().setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
        window.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        window.getContentPane().add(materialInfo);
        window.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Set up window.
        window.setMinimumSize(new Dimension(1400, 900));
    	
		// Title and Title Icon.
        window.setTitle("Part Manager - Project");
        
    	window.addWindowListener(new WindowAdapter() {
            @Override
    	    public void windowClosing(WindowEvent e) {
                window.dispose();
    	    }
    	});
    }

    @Override
    public void run() {
        try {
            material = FileManager.loadMaterial(name);
            materialLabel.setText("Material: " + name);
            materialStockLabel.setText(material.isInStock() ? "Material is in stock.": "Material is not in stock.");
            materialPriceLabel.setText("Material Unit Price: " + material.getPrice());
            materialLabel.setIcon(new ImageIcon(material.getImage()));
            window.setVisible(true);
        } catch(FileNotFoundException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(window, "Material file couldn't be found.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch(IOException e2) {
            e2.printStackTrace();
            JOptionPane.showMessageDialog(window, "Material file couldn't be loaded.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch(ClassNotFoundException e3) {
            e3.printStackTrace();
            JOptionPane.showMessageDialog(window, "Material file is corrupted.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}