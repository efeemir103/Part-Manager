import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
        
        JList<String> projectList = new JList<>(projectModel);
        projectList.setPreferredSize(new Dimension(400, 600));
        JButton openButton = new JButton("Open selected Project");
        JButton createButton = new JButton("Create new Project");
        JButton deleteButton = new JButton("Delete selected Project");
        JButton partButton = new JButton("Manage Parts");
        JButton materialButton = new JButton("Manage Materials");

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

        partButton.addActionListener(e -> {
            new PartLibraryView().run();
            window.dispose();
        });

        materialButton.addActionListener(e -> {
            new MaterialLibraryView().run();
            window.dispose();
        });

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttons.add(openButton);
        buttons.add(createButton);
        buttons.add(deleteButton);
        buttons.add(partButton);
        buttons.add(materialButton);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(header);
        panel.add(new JScrollPane(projectList));
        panel.add(buttons);
        panel.setLayout(new GridLayout(3, 1, 30, 30));

        window.add(panel);
         
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
            projectModel.clear();
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