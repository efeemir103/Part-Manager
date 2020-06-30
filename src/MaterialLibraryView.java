import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridLayout;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MaterialLibraryView implements View {
    private JFrame window;
    private DefaultListModel<String> materialModel;
    
    public MaterialLibraryView() {
        JLabel header = new JLabel("Material Library:");
        window = new JFrame();
        header.setHorizontalAlignment(JLabel.CENTER);
        header.setFont(header.getFont().deriveFont(24f));
        materialModel = new DefaultListModel<>();
        JList<String> materialList = new JList<>(materialModel);

        JPanel buttons = new JPanel();
        JButton detailsButton = new JButton("Details");
        JButton addButton = new JButton("Add new Material to Library");
        JButton deleteButton = new JButton("Remove a Material from Library");
        
        detailsButton.addActionListener(e -> {
            String materialSelected = materialList.getSelectedValue();
            if(materialSelected != null) {
                new MaterialView(materialSelected).run();
            }
        });

        addButton.addActionListener(e -> {
            new AddMaterialView().run();
            window.dispose();
        });

        deleteButton.addActionListener(e -> {
            String materialSelected = materialList.getSelectedValue();
            if(materialSelected != null) {
                int confirm = JOptionPane.showConfirmDialog(window, "Are you sure you wanna delete material: " + materialSelected, "", JOptionPane.WARNING_MESSAGE);
                if(confirm == JOptionPane.OK_OPTION) {
                    try {
                        FileManager.deleteMaterial(materialSelected);
                        materialModel.removeElement(materialSelected);
                    } catch(IOException error) {
                        error.printStackTrace();
                        JOptionPane.showMessageDialog(window, "Material couldn't be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttons.add(detailsButton);
        buttons.add(addButton);
        buttons.add(deleteButton);

        window.getContentPane().setLayout(new GridLayout(3, 1, 10, 10));
        window.getContentPane().add(header);
        window.getContentPane().add(new JScrollPane(materialList));
        window.getContentPane().add(buttons);

        // Set up window.
        window.setMinimumSize(new Dimension(1400, 900));
    	
        // Title and Title Icon.
        window.setTitle("Part Manager - Material Library");
        
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               window.dispose();
               new MenuView().run();
            }
        });
    }

    @Override
    public void run() {
        try {
            ArrayList<Material> materialsLoaded = FileManager.loadMaterials();
            for(Material m: materialsLoaded) {
                materialModel.addElement(m.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Show the window.
        window.setVisible(true);
    }

}