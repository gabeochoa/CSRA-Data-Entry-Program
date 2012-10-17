package com.gabeochoa;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//Gabriel Ochoa

//"
//Your goal is to write a basic program to manage information for the CSRA group. 
//The program should receive input from the user allowing him to add members to the group. Each member should have the following statistics:
//name
//project they are working on (for example, bioinformatics research project with Dr. Land)
//major
//programming languages they know
//hours per week(time commitment to the club)
//previous programming projects (This can be just a sentence description for each project).
//Program should also be able to sort the members based on the number of hours per week they are available.
//
//Extras: 
//Make the program be able to save/load
//additional features you think may be necessary for this group
//"



public class CSRA {
    private JFrame frame = new JFrame("CSRA Entry Form");
    
    private JTextField nameEntry = new JTextField(20);
    
    private JTextField researchEntry = new JTextField(20);
    private JTextField majorEntry = new JTextField(20);
    private JTextField langEntry = new JTextField(20);
    private JTextArea prevEntry = new JTextArea(2,1);
    
    private JTextField hourEntry = new JTextField(4);
    
    private JButton clearData = new JButton("Clear this Data");
    private JButton saveData = new JButton("Save this Data");

    private int WIDTH = 600;
    private int HEIGHT = 300;
	private Dimension size;

    private CSRA(PrintWriter out) {
    	
    	size = new Dimension(WIDTH, HEIGHT);
    	frame.setSize(size);
    	frame.setPreferredSize(size);
    	frame.setMinimumSize(size);
    	frame.setMaximumSize(size);
        
    	
    	frame.setLayout(new BorderLayout(3,3));
    	
        frame.getContentPane().setBackground(Color.lightGray);
        frame.add(buildStudentEntryPanel(), BorderLayout.PAGE_START);
        frame.add(buildButtonPanel(out), BorderLayout.PAGE_END);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ClosingListener(out));
        frame.setVisible(true);
    }

    private JPanel buildStudentEntryPanel() {
        JPanel panel = new JPanel();
        
        JPanel namePanel = new JPanel();
        JPanel majorPanel = new JPanel();
        JPanel researchPanel = new JPanel();
        JPanel langPanel = new JPanel();
        //JPanel prevPanel = new JPanel();
        JPanel hourPanel = new JPanel();
        
        panel.setLayout(new GridLayout(7,1));
        
        namePanel.setLayout(new BoxLayout(namePanel,BoxLayout.LINE_AXIS));
        majorPanel.setLayout(new BoxLayout(majorPanel,BoxLayout.LINE_AXIS));
        researchPanel.setLayout(new BoxLayout(researchPanel,BoxLayout.LINE_AXIS));
        langPanel.setLayout(new BoxLayout(langPanel,BoxLayout.LINE_AXIS));
        //prevPanel.setLayout(new BoxLayout(prevPanel,BoxLayout.LINE_AXIS));
        hourPanel.setLayout(new BoxLayout(hourPanel,BoxLayout.LINE_AXIS));
        
        panel.add(namePanel);
        panel.add(majorPanel);
        panel.add(researchPanel);
        panel.add(langPanel);
       // panel.add(prevPanel);
        panel.add(hourPanel);
        
        
        namePanel.add(new JLabel("Name: ", JLabel.RIGHT));
        namePanel.add(nameEntry);
        
        majorPanel.add(new JLabel("Major: ", JLabel.RIGHT));
        majorPanel.add(majorEntry);
        
        researchPanel.add(new JLabel("Research Project: ", JLabel.RIGHT));
        researchPanel.add(researchEntry);
        
        langPanel.add(new JLabel("Languages Known (Seperate with commas): ", JLabel.RIGHT));
        langPanel.add(langEntry);
        
       // prevPanel.add(new JLabel("Previous Projects: ", JLabel.RIGHT));
       // prevPanel.add(prevEntry);
        
        hourPanel.add(new JLabel("Hours Available per week (0-140): ", JLabel.RIGHT));
        hourPanel.add(hourEntry);
        
        return panel;       
    }

    private JPanel buildButtonPanel(PrintWriter out) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,2));

        panel.add(saveData);
        saveData.addActionListener(new SaveListener(out));
        
        panel.add(clearData);
        clearData.addActionListener(new ClearListener());
        return panel;
    }
   
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FileOutputStream outfile;
                try {
                	
                	//String in = JOptionPane.showInputDialog(null,"What would you like to name the file?");
                	//outfile = new FileOutputStream("./files/"+in+".csv", true);
                	
                	outfile = new FileOutputStream("Data.csv", true);
                    PrintWriter output = new PrintWriter(outfile);
                    new CSRA(output);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class ClosingListener extends WindowAdapter {
        PrintWriter out;

        public ClosingListener(PrintWriter out) {
            this.out= out;
        }

        @Override
        public void windowClosing(WindowEvent arg0) {
            if (frame == null) System.exit(0);
            int response = JOptionPane.showConfirmDialog(frame,
                    "Do you want to exit the program?",
                    "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if(response == JOptionPane.YES_OPTION) {
                if (out != null) out.close();
                System.exit(0);
            }
        }
    }
   
    private class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            nameEntry.setText("");
            majorEntry.setText(""); 
            researchEntry.setText(""); 
            langEntry.setText(""); 
            hourEntry.setText("");     
        }
    }
    private class SaveListener implements ActionListener {
        PrintWriter out;
        public SaveListener(PrintWriter out) {
            this.out = out;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            
        	int response = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to save?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
            if(response == JOptionPane.NO_OPTION) {
                return;
            }
        	
            String name = nameEntry.getText();
            
            String major = majorEntry.getText();
            String research = researchEntry.getText();
            String langKnown = langEntry.getText();
            
            try {
                int hour = Integer.parseInt(hourEntry.getText().trim());
                
                // add the other scores here
                StringBuilder bldr = new StringBuilder(name);
                
                bldr.append(",");
                bldr.append(major);
                bldr.append(",");
                bldr.append(research);
                bldr.append(",");

                String[] pieces = langKnown.split(",");
                
                for(String s: pieces)
                {
                	bldr.append(s);
                	if(pieces.length >1)
                    bldr.append(" ^ ");
                }
                bldr.append(",");
                bldr.append(hour);
                
                
                out.println(bldr);
                
                JOptionPane.showMessageDialog (frame, "File Saved", " ", JOptionPane.OK_OPTION); 
                
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog (frame,
                        "A number is missing, or there is\n" +
                        "an error in one of the numbers.\n",
                        "Check the numbers", JOptionPane.OK_OPTION);
            }
        }
    }
}