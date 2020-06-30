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

public class ProjectView implements View {
    private String name;
    private Project project;
    private JLabel projectLabel;
    private JLabel projectPartCountLabel;
    private JLabel projectCostLabel;
    private JFrame window;
    private JComboBox<String> addOptions;
    private JComboBox<String> removeOptions;

    public ProjectView(String projectName) {
        name = projectName;
        project = null;
        window = new JFrame();

        JPanel projectInfo = new JPanel();

        projectLabel = new JLabel("Project: [Unknown]");
		projectLabel.setHorizontalAlignment(JLabel.CENTER);
        projectLabel.setFont(projectLabel.getFont().deriveFont(24f));

        projectPartCountLabel = new JLabel("Total Number Of Parts: [Unknown]");
        projectPartCountLabel.setHorizontalAlignment(JLabel.CENTER);
        projectPartCountLabel.setFont(projectPartCountLabel.getFont().deriveFont(22f));

        projectCostLabel = new JLabel("Project Total Cost: [Unknown]");
        projectCostLabel.setHorizontalAlignment(JLabel.CENTER);
        projectCostLabel.setFont(projectCostLabel.getFont().deriveFont(16f));

        projectInfo.setLayout(new GridLayout(3, 1, 10, 10));
        projectInfo.add(projectLabel);
        projectInfo.add(projectPartCountLabel);
        projectInfo.add(projectCostLabel);

        JButton addPartButton = new JButton("Add a Part to Project");

        JDialog addDialog = new JDialog(window, "Project - Add Part");
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
                    project.addPart(FileManager.loadPart(selectedOption), q);
                    FileManager.saveProject(project);
                } catch(FileNotFoundException error1) {
                    error1.printStackTrace();
                    JOptionPane.showMessageDialog(window, "Project file couldn't be found.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch(IOException error2) {
                    error2.printStackTrace();
                    JOptionPane.showMessageDialog(window, "Project file couldn't be loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch(ClassNotFoundException error3) {
                    error3.printStackTrace();
                    JOptionPane.showMessageDialog(window, "Project file is corrupted.", "Error", JOptionPane.ERROR_MESSAGE);
                }       
            }
        });

        addDialog.add(addConfirm);

        JButton removePartButton = new JButton("Remove a Part from Project");
        
        JDialog removeDialog = new JDialog(window, "Project - Remove Part");
        removeDialog.setLayout(new GridLayout(3, 1, 10, 10));
        removeDialog.add(new JLabel("Select an option:"));
        removeDialog.setSize(200, 200);
        removeOptions = new JComboBox<>();
        JButton removeConfirm = new JButton("Confirm");

        removeConfirm.addActionListener(e -> {
            String selectedOption = (String) removeOptions.getSelectedItem();
            if(selectedOption != null) {
                try {
                    project.removePart(FileManager.loadPart(selectedOption));
                    FileManager.saveProject(project);
                } catch(FileNotFoundException error1) {
                    error1.printStackTrace();
                    JOptionPane.showMessageDialog(window, "Project file couldn't be found.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch(IOException error2) {
                    error2.printStackTrace();
                    JOptionPane.showMessageDialog(window, "Project file couldn't be loaded.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch(ClassNotFoundException error3) {
                    error3.printStackTrace();
                    JOptionPane.showMessageDialog(window, "Project file is corrupted.", "Error", JOptionPane.ERROR_MESSAGE);
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
        window.getContentPane().add(projectInfo);
        window.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        window.getContentPane().add(addPartButton);
        window.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        window.getContentPane().add(removePartButton);
        
        // Set up window.
        window.setMinimumSize(new Dimension(1400, 900));
    	
		// Title and Title Icon.
        window.setTitle("Part Manager - Project");
        
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
            project = FileManager.loadProject(name);
            projectLabel.setText("Project: " + name);
            projectPartCountLabel.setText("Total Number of Parts: " + project.getParts().size());
            projectCostLabel.setText("Project Total Cost: " + project.getCost());
            projectLabel.setIcon(new ImageIcon(project.getImage()));
            for(Part p: FileManager.loadParts()) {
                addOptions.addItem(p.getName());
            }
            for(Part p: project.getParts().keySet()) {
                removeOptions.addItem(p.getName());
            }
            window.setVisible(true);
        } catch(FileNotFoundException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(window, "Project file couldn't be found.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch(IOException e2) {
            e2.printStackTrace();
            JOptionPane.showMessageDialog(window, "Project file couldn't be loaded.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch(ClassNotFoundException e3) {
            e3.printStackTrace();
            JOptionPane.showMessageDialog(window, "Project file is corrupted.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}