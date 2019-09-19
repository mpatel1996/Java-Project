
import java.awt.*;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author mpjv1
 */

class JFontChooser{
     
     private static String family;
     private static int size;
     private static int style;
     private static Font newFont;
     
     @SuppressWarnings("unchecked")
     public static Font showDialog(JFrame parent, Font deff){   
          
          JButton okBtn = new JButton("OK");
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
          dlg.add(okBtn);
          
          family = deff.getName();
          style = deff.getStyle();
          size = deff.getSize();
          
          fontlist.setSelectedItem(family);
          fontlist.addItemListener((ItemEvent ie) -> {
              family = ie.getItem().toString();
          });
          
          styleList.addListSelectionListener(new ListSelectionListener() {
              @Override
              public void valueChanged(ListSelectionEvent le) {
                  int values = styleList.getSelectedIndex();
                  
                  switch (values) {
                      case 1:
                          style = Font.BOLD;
                          break;
                      case 2:
                          style = Font.ITALIC;
                          break;
                      case 3:
                          style = Font.ITALIC | Font.BOLD ;
                          break;
                      default:
                          style = Font.PLAIN;
                          break;
                  }
              }
          });
          
          sizeList.setSelectedItem(size);
          sizeList.addItemListener((ItemEvent ie) -> {
               size = (int) ie.getItem();
          });
          
          okBtn.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent ae) {
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
