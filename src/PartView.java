import java.io.FileNotFoundException;
import java.io.IOException;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PartView implements View {
    private String name;
    private Part part;
    private JLabel partLabel;
    private JLabel partMaterialCountLabel;
    private JLabel partCostLabel;
    private JFrame window;
    private JComboBox<String> addOptions;
    private JComboBox<String> removeOptions;
    
    public PartView(String partName) {
        name = partName;
        part = null;
        window = new JFrame();

        JPanel partInfo = new JPanel();

        partLabel = new JLabel("Part: [Unknown]");
		partLabel.setHorizontalAlignment(JLabel.CENTER);
        partLabel.setFont(partLabel.getFont().deriveFont(24f));

        partMaterialCountLabel = new JLabel("Total Number Of Materials: [Unknown]");
        partMaterialCountLabel.setHorizontalAlignment(JLabel.CENTER);
        partMaterialCountLabel.setFont(partMaterialCountLabel.getFont().deriveFont(22f));

        partCostLabel = new JLabel("Part Unit Cost: [Unknown]");
        partCostLabel.setHorizontalAlignment(JLabel.CENTER);
        partCostLabel.setFont(partCostLabel.getFont().deriveFont(16f));

        partInfo.setLayout(new GridLayout(3, 1, 10, 10));
        partInfo.add(partLabel);
        partInfo.add(partMaterialCountLabel);
        partInfo.add(partCostLabel);

        JButton addPartButton = new JButton("Add a Material to Part");

        JDialog addDialog = new JDialog(window, "Part - Add Material");
        addDialog.setLayout(new GridLayout(3, 1, 10, 10));
        addDialog.setSize(200, 200);
        addDialog.add(new JLabel("Select an option:"));
        addOptions = new JComboBox<>();
        addDialog.add(addOptions);
        JButton addConfirm = new JButton("Confirm");
        
        addConfirm.addActionListener(e -> {
            String selectedOption = (String) addOptions.getSelectedItem();
            if(selectedOption != null) {
                int q = Integer.parseInt(JOptionPane.showInputDialog(addDialog, "Enter quantity of part:"));
                try {
                    part.addMaterial(FileManager.loadMaterial(selectedOption), q);
                    FileManager.savePart(part);
                } catch(FileNotFoundException error1) {
                    error1.printStackTrace();
                    JOptionPane.showMessageDialog(window, "Part file couldn't be found.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch(IOException error2) {
                    error2.printStackTrace();
                    JOptionPane.showMessageDialog(window, "Part file couldn't be loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch(ClassNotFoundException error3) {
                    error3.printStackTrace();
                    JOptionPane.showMessageDialog(window, "Part file is corrupted.", "Error", JOptionPane.ERROR_MESSAGE);
                }       
            }
        });

        addDialog.add(addConfirm);

        JButton removePartButton = new JButton("Remove a Material from Part");
        
        JDialog removeDialog = new JDialog(window, "Part - Remove Material");
        removeDialog.setLayout(new GridLayout(3, 1, 10, 10));
        removeDialog.add(new JLabel("Select an option:"));
        removeDialog.setSize(200, 200);
        removeOptions = new JComboBox<>();
        JButton removeConfirm = new JButton("Confirm");

        removeConfirm.addActionListener(e -> {
            String selectedOption = (String) removeOptions.getSelectedItem();
            if(selectedOption != null) {
                try {
                    part.removeMaterial(FileManager.loadMaterial(selectedOption));
                    FileManager.savePart(part);
                } catch(FileNotFoundException error1) {
                    error1.printStackTrace();
                    JOptionPane.showMessageDialog(window, "Part file couldn't be found.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch(IOException error2) {
                    error2.printStackTrace();
                    JOptionPane.showMessageDialog(window, "Part file couldn't be loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch(ClassNotFoundException error3) {
                    error3.printStackTrace();
                    JOptionPane.showMessageDialog(window, "Part file is corrupted.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeDialog.add(removeConfirm);

        addPartButton.addActionListener(e -> {
            addDialog.setVisible(true);
        });

        removePartButton.addActionListener(e -> {
            removeDialog.setVisible(true);
        });

        window.getContentPane().setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
        window.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        window.getContentPane().add(partInfo);
        window.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        window.getContentPane().add(addPartButton);
        window.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        window.getContentPane().add(removePartButton);
        
        // Set up window.
        window.setMinimumSize(new Dimension(1400, 900));
    	
		// Title and Title Icon.
        window.setTitle("Part Manager - Part");
        
    	window.addWindowListener(new WindowAdapter() {
            @Override
    	    public void windowClosing(WindowEvent e) {
                window.dispose();
                System.exit(0);
    	    }
    	});
    }

    @Override
    public void run() {
        try {
            part = FileManager.loadPart(name);
            partLabel.setText("Part: " + name);
            partMaterialCountLabel.setText("Total Number of Materials: " + part.getMaterials().size());
            partCostLabel.setText("Part Unit Cost: " + part.getCost());
            partLabel.setIcon(new ImageIcon(part.getImage()));
            for(Material m: FileManager.loadMaterials()) {
                addOptions.addItem(m.getName());
            }
            for(Material m: part.getMaterials().keySet()) {
                removeOptions.addItem(m.getName());
            }
            window.setVisible(true);
        } catch(FileNotFoundException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(window, "Part file couldn't be found.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch(IOException e2) {
            e2.printStackTrace();
            JOptionPane.showMessageDialog(window, "Part file couldn't be loaded.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch(ClassNotFoundException e3) {
            e3.printStackTrace();
            JOptionPane.showMessageDialog(window, "Part file is corrupted.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}