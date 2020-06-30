import java.io.IOException;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddPartView implements View {
    private JFrame window;

    public AddPartView() {
        JLabel nameLabel = new JLabel("Enter name of the Part: ");
        JTextField nameField = new JTextField();
        JLabel imageLabel = new JLabel("Select a image (*.png) for Part: ");
        JButton imageButton = new JButton("Browse files...");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files (*.png)", "png"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        imageButton.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(window);
            if(result == JFileChooser.APPROVE_OPTION) {
                String imageChoosen = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    String image = FileManager.getImageResource(imageChoosen, nameField.getText(), "part");
                    Part p = new Part(nameField.getText(), image);
                    FileManager.addNewPart(p);
                } catch(IOException error) {
                    error.printStackTrace();
                    JOptionPane.showMessageDialog(window, "New part couldn't be created.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        window.getContentPane().setLayout(new GridLayout(2, 2, 10, 10));
        window.getContentPane().add(nameLabel);
        window.getContentPane().add(nameField);
        window.getContentPane().add(imageLabel);
        window.getContentPane().add(imageButton);
    }

    @Override
    public void run() {
        
    }
    
}