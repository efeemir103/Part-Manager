import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuView implements View {
    private JFrame window;
    private DefaultListModel<String> projectModel;

    public MenuView() {
        // Set up menu view layout.
        window = new JFrame();
        projectModel = new DefaultListModel<>();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files (*.png)", "png"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        JLabel header = new JLabel("Welcome to Part Manager");
		header.setHorizontalAlignment(JLabel.CENTER);
        header.setFont(header.getFont().deriveFont(32f));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JList<String> projectList = new JList<>(projectModel);
        projectList.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton openButton = new JButton("Open selected Project");
        openButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton createButton = new JButton("Create new Project");
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton deleteButton = new JButton("Delete selected Project");
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        openButton.addActionListener(e -> {
            String projectSelected = projectList.getSelectedValue();
            if(projectSelected != null) {
                new ProjectView(projectSelected).run();
                window.dispose();
            }
        });

        createButton.addActionListener(e -> {
            String name = (String) JOptionPane.showInputDialog(window, "Enter new Project name:");
            int result = fileChooser.showOpenDialog(window);
            if(result == JFileChooser.APPROVE_OPTION) {
                String imageChoosen = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    String image = FileManager.getImageResource(imageChoosen, name, "project");
                    Project p = new Project(name, image);
                    FileManager.addNewProject(p);
                    projectModel.addElement(p.getName());
                } catch(IOException error) {
                    error.printStackTrace();
                    JOptionPane.showMessageDialog(window, "New project couldn't be created.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(e -> {
            String projectSelected = projectList.getSelectedValue();
            if(projectSelected != null) {
                int confirm = JOptionPane.showConfirmDialog(window, "Are you sure you wanna delete project: " + projectSelected, "", JOptionPane.WARNING_MESSAGE);
                if(confirm == JOptionPane.OK_OPTION) {
                    try {
                        FileManager.deleteProject(projectSelected);
                        projectModel.removeElement(projectSelected);
                    } catch(IOException error) {
                        error.printStackTrace();
                        JOptionPane.showMessageDialog(window, "Project couldn't be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        window.getContentPane().setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
        window.getContentPane().add(Box.createRigidArea(new Dimension(0, 30)));
        window.getContentPane().add(header);
        window.getContentPane().add(Box.createRigidArea(new Dimension(0, 20)));
        window.getContentPane().add(projectList);
        window.getContentPane().add(Box.createRigidArea(new Dimension(0, 50)));
        window.getContentPane().add(openButton);
        window.getContentPane().add(Box.createRigidArea(new Dimension(0, 20)));
        window.getContentPane().add(createButton);
        window.getContentPane().add(Box.createRigidArea(new Dimension(0, 20)));
        window.getContentPane().add(deleteButton);
         
        // Set up window.
        window.setMinimumSize(new Dimension(900, 600));
    	
		// Title and Title Icon.
        window.setTitle("Part Manager");
        
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
            ArrayList<Project> projectsLoaded = FileManager.loadProjects();
            for(Project p: projectsLoaded) {
                projectModel.addElement(p.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Show the window.
    	window.setVisible(true);
    }
    
}