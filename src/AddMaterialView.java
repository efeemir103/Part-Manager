import java.io.IOException;

import java.awt.GridLayout;

import javax.swing.JButton;
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

    public AddMaterialView() {
        JLabel nameLabel = new JLabel("Enter name of the Material: ");
        JTextField nameField = new JTextField();
        JLabel priceLabel = new JLabel("Enter unit price of the Material: ");
        JSpinner priceField = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
        JLabel imageLabel = new JLabel("Select a image (*.png) for Material: ");
        JButton imageButton = new JButton("Browse files...");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files (*.png)", "png"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        imageButton.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(window);
            if(result == JFileChooser.APPROVE_OPTION) {
                String imageChoosen = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    String image = FileManager.getImageResource(imageChoosen, nameField.getText(), "material");
                    Material p = new Material(nameField.getText(), (Double) priceField.getValue(), image);
                    FileManager.addNewMaterial(p);
                } catch(IOException error) {
                    error.printStackTrace();
                    JOptionPane.showMessageDialog(window, "New material couldn't be created.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        window.getContentPane().setLayout(new GridLayout(3, 3, 10, 10));
        window.getContentPane().add(nameLabel);
        window.getContentPane().add(nameField);
        window.getContentPane().add(priceLabel);
        window.getContentPane().add(priceField);
        window.getContentPane().add(imageLabel);
        window.getContentPane().add(imageButton);
    }

    @Override
    public void run() {

    }

}