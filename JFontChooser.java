
import java.awt.*;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

/**
 * Code is nowhere near perfect or clean. 
 * Just shows how to create a simple JFontchooser. 
 * Nothing too fancy. Comments and criticism appreciated.
 *
 * @author Mihir Patel
 */

class JFontChooser{
     
     private static String family;
     private static int size;
     private static int style;
     private static Font newFont;
     
     public static Font showDialog(JFrame parent, Font deff){   
          
          JButton btn = new JButton("OK");
          Integer arrsize[] = {6,8,10,12,14,16,18,20,24,28,34,38};

          String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
          
          JComboBox<Integer> sizeList = new JComboBox<>(arrsize);
          JComboBox<String> fontlist = new JComboBox<>(fonts);
          //JList<String> styleList = new JList<>(arrstyle);
          
          JScrollPane jsp = new JScrollPane(fontlist);
          
          DefaultListModel lm = new DefaultListModel();
          
          lm.addElement("Plain");
          lm.addElement("Bold");
          lm.addElement("Italic");
          lm.addElement("Bold & Italic");
          JList styleList = new JList(lm);
          styleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
          // Enable user to enter Font Size
          sizeList.setEditable(true);
          
          JDialog dlg = new JDialog(parent, "Fonts", true);
          dlg.setLocationRelativeTo(parent);
          dlg.setLayout(new FlowLayout());
          dlg.add(jsp);  
          dlg.add(sizeList);
          dlg.add(styleList);
          dlg.add(btn);
          
          family = deff.getName();
          style = deff.getStyle();
          size = deff.getSize();
          
          fontlist.setSelectedItem(family);
          fontlist.addItemListener(new ItemListener(){
               public void itemStateChanged(ItemEvent ie){
                    family = ie.getItem().toString();
                    
               }
          });
          
          styleList.addListSelectionListener((ListSelectionEvent le) -> {
            
               int values = styleList.getSelectedIndex();
               int counter = 3;
               
               if(values ==1){
                    style = Font.BOLD;
               }else if (values==2){
                    style = Font.ITALIC;
               }
               else if (values==3){
                    style = counter ;
               }
               else if (values==0){
                    style = Font.PLAIN;
               }
                       
          });
          
           sizeList.setSelectedItem(size);
          sizeList.addItemListener((ItemEvent ie) -> {
               size = (int) ie.getItem();
          });
          
          btn.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent ae){
                  newFont = new Font(family,style,size);
                  dlg.dispose();
               }
          });
          dlg.pack();
          
          dlg.setLocationRelativeTo(parent);
          dlg.setVisible(true);

          return newFont;
     }
     
}
