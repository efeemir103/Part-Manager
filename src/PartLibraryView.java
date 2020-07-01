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

public class PartLibraryView implements View {
    private JFrame window;
    private DefaultListModel<String> partModel;

    public PartLibraryView() {
        JLabel header = new JLabel("Part Library:");
        window = new JFrame();
        header.setHorizontalAlignment(JLabel.CENTER);
        header.setFont(header.getFont().deriveFont(24f));
        partModel = new DefaultListModel<>();
        JList<String> partList = new JList<>(partModel);

        JPanel buttons = new JPanel();
        JButton detailsButton = new JButton("Details");
        JButton addButton = new JButton("Add new Part to Library");
        JButton deleteButton = new JButton("Remove a Part from Library");
        
        detailsButton.addActionListener(e -> {
            String partSelected = partList.getSelectedValue();
            if(partSelected != null) {
                new PartView(partSelected).run();
            }
        });

        addButton.addActionListener(e -> {
            new AddPartView().run();
            window.dispose();
        });

        deleteButton.addActionListener(e -> {
            String partSelected = partList.getSelectedValue();
            if(partSelected != null) {
                int confirm = JOptionPane.showConfirmDialog(window, "Are you sure you wanna delete part: " + partSelected, "", JOptionPane.WARNING_MESSAGE);
                if(confirm == JOptionPane.OK_OPTION) {
                    try {
                        FileManager.deletePart(partSelected);
                        partModel.removeElement(partSelected);
                    } catch(IOException error) {
                        error.printStackTrace();
                        JOptionPane.showMessageDialog(window, "Part couldn't be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
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
        window.getContentPane().add(new JScrollPane(partList));
        window.getContentPane().add(buttons);

        // Set up window.
        window.setMinimumSize(new Dimension(1400, 900));
    	
        // Title and Title Icon.
        window.setTitle("Part Manager - Part Library");
        
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
            ArrayList<Part> materialsLoaded = FileManager.loadParts();
            for(Part p: materialsLoaded) {
                partModel.addElement(p.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Show the window.
        window.setVisible(true);
    }

}