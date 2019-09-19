// Mihir Patel
// 
// Description:
//               Notepad will allow user to input text in JTextArea.
//               It will have pop-up menu for Copy/Cut/paste. Ask user to
//               save any changes made to file before exiting current file. 
//               Notepad includes custome JFontChooser.
//               Has find/replace, go-to, status bar functions built in the Notepad.


import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.*;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import javax.swing.undo.UndoManager;

class JNotepad extends JFrame {
     
    private JLabel jlab;
    private JFileChooser jfc; 
    private static JTextArea textArea;
    private static ImageIcon imgIcon;
    private static Image img;
    private static PrinterJob pgSet; 
    private boolean edited = false;
    private File file = null;
    private static JPanel statusBar;

    private int x = 0;
    private String Title = "Untitled";
     
    UndoManager undomanager;
 
    JNotepad(){
        textArea = new JTextArea();

        img = Toolkit.getDefaultToolkit().getImage("JavaViewer.png");
        imgIcon = new ImageIcon("JavaViewer.png");
        setIconImage(img);

        setTitle(Title);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        setSize(800,600);

        add(BorderLayout.CENTER,textArea);
        add(new JScrollPane(textArea , JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
              JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.CENTER);

        // add all the Menu to JMenuBar 
        menuItems();

        // Popup menu 
        popUp();

        statusBar = new JPanel(new BorderLayout());

        JLabel statusLabel= new JLabel("Ln 1, Col 1");
        statusLabel.setFont(new Font("Arial",Font.PLAIN,15));

        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
        statusBar.add(statusLabel,BorderLayout.EAST);

        textArea.addCaretListener(cl ->{
              int caretPosition =  textArea.getCaretPosition();

              try {
                  x = textArea.getLineOfOffset(caretPosition);
                  caretPosition = caretPosition - textArea.getLineStartOffset(x);
              } catch (BadLocationException e1) {}
              statusLabel.setText("Ln " + (x+1)  +", Col " + (caretPosition+1));

      });

        textArea.getDocument().addUndoableEditListener(undomanager = new UndoManager());

        setLocationRelativeTo(null);
        setVisible(true);     
   }

   public void popUp() {
         //Create the popup menu.
        JPopupMenu popup = new JPopupMenu();

        Action cutAction = new AbstractAction(DefaultEditorKit.cutAction)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textArea.cut();
            }
        };

        Action copyAction = new AbstractAction(DefaultEditorKit.copyAction)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textArea.copy();
            }
        };

        Action pasteAction = new AbstractAction(DefaultEditorKit.pasteAction)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textArea.paste();
            }
        };

        JMenuItem cut = new JMenuItem(cutAction);
        JMenuItem copy = new JMenuItem(copyAction);
        JMenuItem paste = new JMenuItem(pasteAction);

        popup.add(cut);
        popup.add(copy);
        popup.add(paste);

        textArea.setComponentPopupMenu(popup);
   }

   public void menuItems() {
        JMenuBar jmb = new JMenuBar();
        JMenu file = new JMenu("File");
        // newO = saves current text, open new "untitled" Notepad
        JMenuItem newO = new JMenuItem("New");       // Acc = n
        // Use FileChooser to open .txt & .java
        JMenuItem open = new JMenuItem("Open...");   // Acc = O
        JMenuItem save = new JMenuItem("Save");      // Acc = S
        JMenuItem saveAs = new JMenuItem("Save As...");
        JMenuItem pgSetUp = new JMenuItem("Page setup...");
        JMenuItem print = new JMenuItem("Print");
        JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_X);

        JMenu edit = new JMenu("Edit");
        JMenuItem undo = new JMenuItem("Undo");        // Acc = Z
        JMenuItem redo = new JMenuItem("Redo");        // Acc = R
        JMenuItem cutM = new JMenuItem("Cut");        // Acc = x
        JMenuItem copyM = new JMenuItem("Copy");      // Acc = C
        JMenuItem pasteM = new JMenuItem("paste");    // Acc = v
        JMenuItem deleteM = new JMenuItem("Delete");  // Del Key
        JMenuItem find = new JMenuItem("Find");      // Acc = F
        JMenuItem findNext = new JMenuItem("Find Next");  
        JMenuItem replace = new JMenuItem("Replace");  
        JMenuItem goTo = new JMenuItem("Go To");  
        JMenuItem selectAll = new JMenuItem("Select All");     // Acc = A
        JMenuItem timeStamp = new JMenuItem("Time Stamp");     // F5 key

        JMenu format = new JMenu("Format");
        JMenuItem font = new JMenuItem("Font...", KeyEvent.VK_F);
        JCheckBoxMenuItem wordW = new JCheckBoxMenuItem("Word Wrap");
        wordW.setSelected(true);

        JMenu view = new JMenu("View");
        JMenuItem status = new JMenuItem("status Bar", KeyEvent.VK_S);

        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About Notepad", KeyEvent.VK_A);
        JMenuItem viewH = new JMenuItem("View help", KeyEvent.VK_H);

        file.setMnemonic('F');
        edit.setMnemonic('E');
        view.setMnemonic('V');
        format.setMnemonic('O');
        wordW.setMnemonic('W');
        help.setMnemonic('H');

        // add Menu 
        jmb.add(file);
        jmb.add(edit);
        jmb.add(format);
        jmb.add(view);
        jmb.add(help);

        // Add File sub MenuItem
        file.add(newO); 
        newO.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        file.add(open);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 2));
        file.add(save);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 2));
        file.add(saveAs);
        file.addSeparator();
        file.add(pgSetUp);
        file.add(print);
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 2));
        file.addSeparator();
        file.add(exit);

        // Edit sub MenuItem
        edit.add(undo);
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 2));
        edit.add(redo);
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 2));
        edit.addSeparator();
        edit.add(cutM);
        cutM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, 2));
        edit.add(copyM);
        copyM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 2));
        edit.add(pasteM);
        pasteM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, 2));
        edit.add(deleteM); //  key need to set up  just delete
        edit.addSeparator();
        edit.add(find);
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, 2));
        edit.add(findNext); // F3 key 
        edit.add(replace);
        replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, 2));
        edit.add(goTo);
        goTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, 2));
        edit.addSeparator();
        edit.add(selectAll);
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 2));
        edit.add(timeStamp); // F5 key 

        // Add Format Sub MenuItem
        format.add(wordW);
        format.add(font);

        // View status bar
        view.add(status);

        // Help menu
        help.add(viewH);
        help.addSeparator();
        help.add(about);

        setJMenuBar(jmb);

        // Grayout since new file is empty. nothing to undo
        undo.setEnabled(false);
        redo.setEnabled(false);
        cutM.setEnabled(false);
        copyM.setEnabled(false);
        pasteM.setEnabled(false);
        deleteM.setEnabled(false);
        findNext.setEnabled(false);
        find.setEnabled(false);
        goTo.setEnabled(false);
        status.setEnabled(false);

        textArea.addCaretListener(ta ->{
             if(edited){
                  setTitle("*" + Title);
                  undo.setEnabled(true);
                  cutM.setEnabled(true);
                  copyM.setEnabled(true);
                  pasteM.setEnabled(true);
                  deleteM.setEnabled(true);
                  findNext.setEnabled(true);
                  find.setEnabled(true);
                  goTo.setEnabled(true);
             }
        });
          
          // Start adding action Listeners 
          
          textArea.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e) {
                edited = true;
                undo.setEnabled(undomanager.canUndo());
                redo.setEnabled(undomanager.canRedo());
            }
            public void removeUpdate(DocumentEvent e) {
                edited = true;
                undo.setEnabled(undomanager.canUndo());
                redo.setEnabled(undomanager.canRedo());
            }
            public void changedUpdate(DocumentEvent e) {
                edited = true;
                undo.setEnabled(undomanager.canUndo());
                redo.setEnabled(undomanager.canRedo());
            }
        });
          
          newO.addActionListener(n ->{
               newfile();
          });
          
          exit.addActionListener(e ->{
               exitDlg();
          });
 
          addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
               exit.doClick();
            }});
          
          copyM.addActionListener(e ->{
               textArea.copy();
          });
          
          cutM.addActionListener(e ->{
               textArea.cut();
          });
          
          wordW.addActionListener(ww ->{
               if(!wordW.isSelected()){
                    textArea.setLineWrap(false);
                    status.setEnabled(true);
                    
               }
               
               if(wordW.isSelected()){
                    remove(statusBar);
                    textArea.setLineWrap(true);
                    setSize(getWidth(),getHeight()-1);
                    status.setEnabled(false);
               }
               
          });
          pasteM.addActionListener(e ->{
               textArea.paste();
          });
          
          save.addActionListener( e ->{
               savefile(textArea.getText());
          });
          
          saveAs.addActionListener(sa -> {
               savefile(textArea.getText());
          });
          
          undo.addActionListener(u -> {
               if(undomanager.canUndo()){
                    undomanager.undo();
               } else {
                    undo.setEnabled(false);
               }
          });
          
          redo.addActionListener(r ->{
               if(undomanager.canRedo()){
                    undomanager.redo();
               } else {
                    redo.setEnabled(false);
               }
          });
          
          goTo.addActionListener(gt ->{
               gotoDlg();
               
          });
          replace.addActionListener(r ->{
               find();
          });
          pgSetUp.addActionListener(l -> {
               pgSet = PrinterJob.getPrinterJob();
               PageFormat pf = pgSet.pageDialog(pgSet.defaultPage());
               
          });
          print.addActionListener(p ->{
                try {
                    textArea.print();
                }
                catch (PrinterException e1) {
                }
          });
          
          deleteM.addActionListener(e ->{
               if(textArea.getSelectedText()==null){
                textArea.moveCaretPosition(textArea.getCaretPosition()+1);
                }
            textArea.replaceSelection("");
          });
          
          timeStamp.addActionListener(tse->{
               textArea.insert(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").
                    format(Calendar.getInstance().getTime()),textArea.getCaretPosition());
          });
          
          viewH.addActionListener(vhe ->{
               try {
                    Desktop.getDesktop().browse(new URL("https://support.microsoft.com/en-us/help/4466414/windows-help-in-notepad").toURI());
               } catch (Exception e) {
                    e.printStackTrace();
               }
          });
          
          status.addActionListener(sta ->{
                    add(statusBar,BorderLayout.SOUTH);
                    setSize(getWidth(), getHeight()+1);
                
          });
           
          find.addActionListener(fal ->{
               find();
          });
          
          selectAll.addActionListener(sa -> {
                try {
                    textArea.setSelectionStart(0);
                    textArea.setSelectionEnd(textArea.getLineEndOffset(textArea.getLineCount()-1));
                } catch (BadLocationException e1) {}
          });
          
          open.addActionListener(ae -> {
               jfc = new JFileChooser(".");
               
               FileFilter filter = new FileNameExtensionFilter("Java Files","java");
               jfc.setFileFilter(filter);
               
               int result = jfc.showOpenDialog(JNotepad.this); 
               File fileO;
            
               if(result == JFileChooser.APPROVE_OPTION){
                    
                    // To remove old text and updating with new file text 
                    textArea.setText("");
                    fileO = jfc.getSelectedFile();
                    try {
                         BufferedReader in = new BufferedReader(new FileReader(fileO));
                         String line = in.readLine();
                         while(line != null){
                              textArea.append(line + "\n");
                              line = in.readLine();
                         }
                    } catch (FileNotFoundException ex) {
                         Logger.getLogger(JNotepad.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                         Logger.getLogger(JNotepad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   Title = fileO.getName();
                   setTitle(Title);
               }
               
               else{ 
                    jlab.setText("No file selected."); 
               }
               
          }); // end of open
          
          font.addActionListener(ae -> {
              Font currFont = textArea.getFont();
              Font fonts = JFontChooser.showDialog(JNotepad.this, currFont);
              if(fonts != null){
                    textArea.setFont(fonts);
              }
          });
          
          about.addActionListener(ae -> {
               JOptionPane.showMessageDialog(null, "(c) Mihir Patel "
                       + "\n All Rights reserved"
                       + "\n Version: 1.0 (OS Build 17134.407)","About" , 1, imgIcon );
          });
     } // End of MenuItems()
      
     
     public void savefile(String text){
        if(Title.equals("Untitled")){
            jfc = new JFileChooser();
            jfc.showSaveDialog(JNotepad.this);
            file = jfc.getSelectedFile();
            try {
                file.createNewFile();
                Title = file.getName();
            } catch (IOException e) {}
        }

        try (FileWriter fw = new FileWriter(file)){
                      fw.write(text);
                  } catch (IOException e) {}
        setTitle(Title);

        edited = false;
    }
     
     public void gotoDlg(){
          JDialog gotoframe = new JDialog(JNotepad.this , "Go To" , true);
          JButton gotoButton = new JButton("Go To");
          gotoframe.setSize(250, 175);
          gotoframe.setLayout(null);
          gotoframe.setLocationRelativeTo(JNotepad.this);
          
          JTextField findfield = new JTextField(); 
          JLabel findlabel = new JLabel("Go To: ");
          JLabel finderror = new JLabel();
          // set bounds ( x,y,width,height)
          findlabel.setBounds(20, 10 , 50, 20);
          findfield.setBounds(findlabel.getX()+80, findlabel.getY(), 150, findlabel.getHeight());
          gotoframe.add(findlabel);
          gotoframe.add(findfield);
          gotoframe.add(gotoButton);
          findfield.setBounds(70, 10, 100, 20);
          gotoButton.setBounds(70, 50,100, 20);
          
          gotoButton.addActionListener(e -> {
               int lineNumber;  
               
               try  
               {  
               lineNumber = Integer.parseInt(findfield.getText());  
               textArea.setCaretPosition(textArea.getLineStartOffset(lineNumber-1));  
               gotoframe.dispose();
               
               }catch(BadLocationException el){
                    finderror.setText("Error! Line out of bounce");
                    gotoframe.add(finderror);
          finderror.setBounds(53, 50, 150, 85);
               }  
               
          });
          
          gotoframe.setVisible(true);
     }
     
     public void find(){
      JDialog findframe = new JDialog(JNotepad.this , "Find" , true);
        findframe.setSize(400, 200);
        findframe.setLayout(null);

        JTextField findfield = new JTextField(); 
        JLabel findlabel = new JLabel("Find: ");
        findlabel.setBounds(10, 10, 100, 20);
        findfield.setBounds(findlabel.getX()+100, findlabel.getY(), 150, findlabel.getHeight());
        findframe.add(findlabel);
        findframe.add(findfield);
        
        JTextField replacefield = new JTextField(); 
        JLabel replacelabel = new JLabel("Replace With:");
        replacelabel.setBounds(10, 60, 100, 20);
        replacefield.setBounds(replacelabel.getX()+100, replacelabel.getY(), 150, replacelabel.getHeight());
        findframe.add(replacelabel);
        findframe.add(replacefield);
        
        JCheckBox matchcase = new JCheckBox("Match Case");
        matchcase.setBounds(270, 30, 150, 30);
        findframe.add(matchcase);
        
        JLabel statusFindN = new JLabel();
        statusFindN.setBounds(10, 130, 200, 20);
        
        findframe.add(statusFindN);
        JButton findnext = new JButton("Find Next");
        findnext.setBounds(10,100,120,20);
        findnext.setMnemonic('N');
        findnext.setDisplayedMnemonicIndex(5);
        
        findnext.addActionListener(new ActionListener(){
            int i=0;
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();
                Pattern pat = Pattern.compile(findfield.getText());
                Matcher matcher=pat.matcher(text) ;
                
                if(text.length() == 0){
                    statusFindN.setText("Text Area is Empty!");
                }
                if(matcher.find(i)){
                    textArea.setSelectionStart(matcher.start());
                    textArea.setSelectionEnd(i=matcher.end());
                }
                else{
                    statusFindN.setText("No more words found");
                    statusFindN.repaint();
                }
            }
        });
        
        JButton replace = new JButton("Replace");
        replace .setBounds(findnext.getX()+findnext.getWidth()+10,findnext.getY(),findnext.getWidth(),findnext.getHeight());
        replace.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();
                Pattern pattern = Pattern.compile(findfield.getText());

                Matcher match = pattern.matcher(text);
                if(match.find()){
                    System.out.println(match.start());
                    textArea.setText(match.replaceFirst(replacefield.getText()));
                }
                else{
                    statusFindN.setText("No more words found");
                    statusFindN.repaint();
                }

            }

        });
        findframe.add(replace);
        
        JButton findPre = new JButton("Find Previous");
        findPre.setBounds(250,100,120,20);
        
        findframe.add(findnext);
        findframe.setLocationRelativeTo(JNotepad.this);
        findframe.setVisible(true);
     }
     
     public void exitDlg(){
          if(edited){
               int option = JOptionPane.showConfirmDialog(JNotepad.this, "Do you want to save the changes made to "+Title, "Confirm Exit", JOptionPane.YES_NO_CANCEL_OPTION);
              
               if(option ==JOptionPane.YES_OPTION){
                   savefile(textArea.getText());
                   JNotepad.this.dispose();
               }
               
               if(option == JOptionPane.NO_OPTION){
                    JNotepad.this.dispose();
               }
               
          } else {
                int confirmed = JOptionPane.showConfirmDialog(JNotepad.this, "Do you want to exit?", 
                  "Exit", JOptionPane.YES_NO_OPTION);
           
               if (confirmed == JOptionPane.YES_OPTION) {
                    dispose();
               }
           }     
     }
     
     public void newfile(){
          if(edited){
               int option = JOptionPane.showConfirmDialog(JNotepad.this, "Do you want to save the changes made to "+Title, "Confirm New", JOptionPane.YES_NO_CANCEL_OPTION);
                    if(option ==JOptionPane.YES_OPTION){
                        savefile(textArea.getText());
                        textArea.setText("");
                        setTitle("Untitled");
                    }
                    if(option == JOptionPane.NO_OPTION){
                         //JNotepad.this.dispose();
                         textArea.setText("");
                         setTitle("Untitled");
                    }
          }
     }
     
     public static void main(String args[]){
          SwingUtilities.invokeLater(new Runnable(){
               public void run(){
                    new JNotepad();
               }
          });
     }
}
