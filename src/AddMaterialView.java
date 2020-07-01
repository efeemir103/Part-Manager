import java.io.IOException;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddMaterialView implements View {
    private JFrame window;
    private String image;

    public AddMaterialView() {
        window = new JFrame();
        JLabel nameLabel = new JLabel("Enter name of the Material: ");
        JTextField nameField = new JTextField();
        JLabel priceLabel = new JLabel("Enter unit price of the Material: ");
        JSpinner priceField = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
        JLabel imageLabel = new JLabel("Select a image (*.png) for Material: ");
        JButton imageButton = new JButton("Browse files...");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files (*.png)", "png"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        JLabel stockLabel = new JLabel("Is this material available (in stock): ");
        JCheckBox stockBox = new JCheckBox();
        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");

        imageButton.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(window);
            if(result == JFileChooser.APPROVE_OPTION) {
                image = fileChooser.getSelectedFile().getAbsolutePath();
                imageButton.setText(fileChooser.getSelectedFile().getName() + " is selected.");
            }
        });

        confirmButton.addActionListener(e -> {
            if(image != null) {
                try {
                    image = FileManager.getImageResource(image, nameField.getText(), "material");
                    Material p = new Material(nameField.getText(), (double) priceField.getValue(), image, stockBox.isSelected());
                    FileManager.addNewMaterial(p);
                    window.dispose();
                    new MaterialLibraryView().run();
                } catch(IOException error) {
                    error.printStackTrace();
                    JOptionPane.showMessageDialog(window, "New material couldn't be created.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> {
            window.dispose();
            new MaterialLibraryView().run();
        });

        window.getContentPane().setLayout(new GridLayout(5, 2, 10, 10));
        window.getContentPane().add(nameLabel);
        window.getContentPane().add(nameField);
        window.getContentPane().add(priceLabel);
        window.getContentPane().add(priceField);
        window.getContentPane().add(imageLabel);
        window.getContentPane().add(imageButton);
        window.getContentPane().add(stockLabel);
        window.getContentPane().add(stockBox);
        window.getContentPane().add(confirmButton);
        window.getContentPane().add(cancelButton);

        // Set up window.
        window.setMinimumSize(new Dimension(600, 200));
    	
		// Title and Title Icon.
        window.setTitle("Part Manager - Add Material");
        
    	window.addWindowListener(new WindowAdapter() {
            @Override
    	    public void windowClosing(WindowEvent e) {
                window.dispose();
                new MaterialLibraryView().run();
    	    }
    	});
    }

    @Override
    public void run() {
        window.setVisible(true);
    }

}