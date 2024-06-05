import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener {

    // Definition of our frame and its elements
    JTextArea textArea;
    JScrollPane scrollPane;
    JLabel fontLabel;
    JSpinner fontSizeSpineer;
    JButton fontColorButton;
    JComboBox fontBox;

    JMenuBar menuBar;
    JMenu fileMnu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;

    // This is the builder on which we define each element on the frame
    TextEditor() {

        // Code to create the frame that will host each item
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Bro Text Editor");
        this.setSize(500, 500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        // The code to set the section that will contain the place where the text will appear
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 50));

        // The code to add a cropping bar for the area that will host the text
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // The code to set the name "Font" that will be followed by a zone to set the text size
        fontLabel = new JLabel("Font: ");

        // The code to define the size of the dial that will set the text size
        fontSizeSpineer = new JSpinner();
        fontSizeSpineer.setPreferredSize(new Dimension(50, 25));
        fontSizeSpineer.addChangeListener(new ChangeListener() {
            
            // Code to set the font used for our number that will set the text size
            @Override
            public void stateChanged(ChangeEvent e) {

                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpineer.getValue()));
            }
        });

        // Code to set the text inside our button to set the text color
        fontColorButton = new JButton("Color");
        fontColorButton.addActionListener(this);

        // Code meant to lead us to a database that contains the font types, so we can select the one we want.
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();


        /* Code to instantiate the function of connecting to the database that will lead us to the
        multitude of fonts by accessing the button 'Arial' */
        fontBox = new JComboBox(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");

//        ----------- Creating the Menu and initializing each of it's options ------------

        menuBar = new JMenuBar();
        fileMnu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMnu.add(openItem);
        fileMnu.add(saveItem);
        fileMnu.add(exitItem);
        menuBar.add(fileMnu);

//        ----------- Code to add all the added items ------------


        this.setJMenuBar(menuBar);
        this.add(fontLabel);
        this.add(fontSizeSpineer);
        this.add(fontColorButton);
        this.add(fontBox);
        this.add(scrollPane);
        this.setVisible(true);

    }

    // Here is the code where we make the functionality of each element work and provide the desired result
    @Override
    public void actionPerformed(ActionEvent e) {

        // Code meant to lead us to a database that contains a multitude of colors, so we can select the one we want
        if (e.getSource() == fontColorButton) {

            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null, "Choose a color", Color.black);

            textArea.setForeground(color);
        }

        // Here is the code to make the functionality to change the font of our text possible
        if (e.getSource() == fontBox) {

            textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
        }

        // Here is the code meant to put into operation the option of accessing an object on the PC
        if (e.getSource() == openItem) {

            // Initializing our object, so we can access device data
            JFileChooser FileChooser = new JFileChooser();
            FileChooser.setCurrentDirectory(new File("."));

            // Create a new extension filter that supports text and HTML files
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "text", "HTML files");
            FileChooser.setFileFilter(filter);

            // This line opens the file save dialog and stores the user respond in the response variable
            int response = FileChooser.showSaveDialog(null);

            // Code used to declare the creation of a new object that will be used to read the contents of the file
            if (response == JFileChooser.APPROVE_OPTION) {

                File file = new File(FileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;

                // code to open our file created for reading
                try {

                    fileIn = new Scanner(file);
                    while (fileIn.hasNextLine()) {

                        String line = fileIn.nextLine() + "\n ";
                        textArea.append(line);
                    }
                } catch (FileNotFoundException e1) {

                    e1.printStackTrace();
                } finally {

                    fileIn.close();
                }
            }
            
        }

        // Code to create all the functionality of our file save button
        if (e.getSource() == saveItem) {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response = fileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {

                File file;
                PrintWriter fileOut = null;

                file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                try {

                    fileOut = new PrintWriter(file);
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException e1) {

                    e1.printStackTrace();
                } finally {

                    fileOut.close();
                }
            }
        }

        // Code to complete our process
        if (e.getSource() == exitItem) {

            System.exit(0);
        }
    }
}