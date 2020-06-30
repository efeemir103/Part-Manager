import java.io.FileNotFoundException;
import java.io.IOException;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
    public ProjectView(String projectName) {
        name = projectName;
        project = null;
        window = new JFrame();

        JPanel projectInfo = new JPanel();

        projectLabel = new JLabel("Project: [Unknown]");
		projectLabel.setHorizontalAlignment(JLabel.CENTER);
        projectLabel.setFont(projectLabel.getFont().deriveFont(32f));

        projectPartCountLabel = new JLabel("Total Number Of Parts: [Unknown]");
        projectPartCountLabel.setHorizontalAlignment(JLabel.CENTER);
        projectPartCountLabel.setFont(projectPartCountLabel.getFont().deriveFont(28f));

        projectCostLabel = new JLabel("Project Total Cost: [Unknown]");
        projectCostLabel.setHorizontalAlignment(JLabel.CENTER);
        projectCostLabel.setFont(projectCostLabel.getFont().deriveFont(24f));

        projectInfo.setLayout(new GridLayout(10, 10));
        projectInfo.add(projectLabel);
        projectInfo.add(projectPartCountLabel);
        projectInfo.add(projectCostLabel);

        JButton addPartButton = new JButton("Add a Part to Project");
        JButton removePartButton = new JButton("Add a Part to Project");

        addPartButton.addActionListener(e -> {
            
        });

        removePartButton.addActionListener(e -> {

        });

        window.getContentPane().setLayout(new BorderLayout(10, 20));
        window.getContentPane().add(projectInfo, BorderLayout.NORTH);
        
        // Set up window.
        window.setMinimumSize(new Dimension(900, 600));
    	
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