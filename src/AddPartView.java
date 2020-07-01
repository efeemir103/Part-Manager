import java.io.IOException;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddPartView implements View {
    private JFrame window;
    private String image;

    public AddPartView() {
        image = null;
        window = new JFrame();
        JLabel nameLabel = new JLabel("Enter name of the Part: ");
        JTextField nameField = new JTextField();
        JLabel imageLabel = new JLabel("Select a image (*.png) for Part: ");
        JButton imageButton = new JButton("Browse files...");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files (*.png)", "png"));
        fileChooser.setAcceptAllFileFilterUsed(false);
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
                    image = FileManager.getImageResource(image, nameField.getText(), "part");
                    Part p = new Part(nameField.getText(), image);
                    FileManager.addNewPart(p);
                    window.dispose();
                    new PartLibraryView().run();
                } catch(IOException error) {
                    error.printStackTrace();
                    JOptionPane.showMessageDialog(window, "New part couldn't be created.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> {
            window.dispose();
            new PartLibraryView().run();
        });

        window.getContentPane().setLayout(new GridLayout(3, 2, 10, 10));
        window.getContentPane().add(nameLabel);
        window.getContentPane().add(nameField);
        window.getContentPane().add(imageLabel);
        window.getContentPane().add(imageButton);
        window.getContentPane().add(confirmButton);
        window.getContentPane().add(cancelButton);

        // Set up window.
        window.setMinimumSize(new Dimension(600, 200));
    	
		// Title and Title Icon.
        window.setTitle("Part Manager - Add Part");
        
    	window.addWindowListener(new WindowAdapter() {
            @Override
    	    public void windowClosing(WindowEvent e) {
                window.dispose();
                new PartLibraryView().run();
    	    }
    	});
    }

    @Override
    public void run() {
        window.setVisible(true);
    }
    
}