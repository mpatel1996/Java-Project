
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JSlideShow extends JFrame{
    
    File files[];
    private Timer timer;
    private int slideIndex = 0;
    private final JPanel imagePanel = new JPanel();
    private JLabel lblPicture = new JLabel();
    
    JSlideShow(){
        
        setTitle("Slide Show");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        
        SetUpPanels();
        setResizable(false);
        
        
        JMenuBar jmb = new JMenuBar();
        JMenu file = new JMenu("File");
        // newO = saves current text, open new "untitled" Notepad
        JMenuItem open = new JMenuItem("Open...");   // Acc = O
        JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_X);

        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About Notepad", KeyEvent.VK_A);
        JMenuItem viewH = new JMenuItem("View help", KeyEvent.VK_H);
        
        file.setMnemonic('F');
        help.setMnemonic('H');
        
        jmb.add(file);
        jmb.add(help);
        
        // Add File sub MenuItem
        file.add(open);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 2));
        file.addSeparator();
        file.add(exit);
        
        help.add(about);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 2));
        help.add(viewH);
        viewH.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, 2));
        
            timer = new Timer((3 * 1000), new TimerListener());
            timer.start();
            
        open.addActionListener(ae -> {
            //clear slide
            lblPicture.setIcon(null);
            
            JFileChooser fc = new JFileChooser();
            fc.setMultiSelectionEnabled(true);

            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
            fc.setFileFilter(filter);
            
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                // initalize the array to the files.
                files = fc.getSelectedFiles();
               
            }
            
          }); // end of open
       
        viewH.addActionListener(vhe ->{
             JOptionPane.showMessageDialog(null, "Program to display slide show of images. \n"
                     + "Each picture will be displayed for 3 secodns. \n"
                     + "User can open their own .png files to the slideshow. \n","Help" , 1 ); 
          });
        
        about.addActionListener(ae -> {
               JOptionPane.showMessageDialog(null, "(c) Mihir Patel "
                       + "\n All Rights reserved"
                       + "\n Version: 1.0 (OS Build 17134.407)","About" , 1 );
          });
        
        exit.addActionListener(e ->{
              JSlideShow.this.dispose();
          });
        
        setJMenuBar(jmb);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // If there are files
            if (files == null) {
                
            } else if (slideIndex  == 0) {
                slideIndex = files.length;
            } else {
                ImageIcon ii = new ImageIcon(files[slideIndex - 1].getAbsolutePath());
                Image img = ii.getImage();
                Image newimg = img.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                ii = new ImageIcon(newimg);
                lblPicture.setIcon(ii);

                slideIndex--;
                pack();
            }
        }
    }
     
    public void SetUpPanels() {
    imagePanel.add(lblPicture);
    add(imagePanel, BorderLayout.NORTH);
    }
        
    public static void main(String args[]){
        SwingUtilities.invokeLater(()->{
            new JSlideShow();
        });
    }
}