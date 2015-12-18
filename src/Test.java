
//Authors Bo Liu, Xiaoyan Zheng

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class Test extends JFrame {
	private JTextPane textPane;
	private Container c;
	private Action openAction = new OpenAction();
	private Action saveAction = new SaveAction();
	
	public Test(){
		super("TextEditor");
		
		createTextfield();
	
		makeActionsPretty();
		createMenuBar();
	    c.add(createToolBar(), BorderLayout.NORTH);
		setSize(1200,800);
		setVisible(true);
	}
	
	protected void createTextfield(){
		c = getContentPane();
		textPane = new JTextPane();
		c.add(textPane, BorderLayout.CENTER);
		textPane.setCaretPosition(0);
		JScrollPane scrollPane = new JScrollPane(textPane);
		//textPane.setLineWrap(true);
		//scrollPane.setPreferredSize(new Dimension(1500,1500));
		textPane.getDocument().addDocumentListener(new Highlight(textPane));
		c.add(scrollPane);

	}
	
	// Create a JMenuBar with file & edit menus.
	protected void createMenuBar() {
		Font bigFont = new Font( "sans-serif", Font.PLAIN, 16 ); //Set Menubar font size here
		//Font littleFont = new Font( "Monospaced", Font.ITALIC, 10 );
		
		JMenuBar menubar = new JMenuBar();
		menubar.add(Box.createRigidArea(new Dimension(0,10)));
		
		setJMenuBar(menubar);
		JMenu file = new JMenu("File");
		file.setFont( bigFont );
		JMenu edit = new JMenu("Edit");
		edit.setFont( bigFont );
		menubar.add(file);
		menubar.add(edit);
		
		//menubar.setFont(new Font("sans-serif", Font.PLAIN, 40));
	    file.add(getOpenAction());
	    file.add(getSaveAction());
	    file.add(new ExitAction());
	    edit.add(textPane.getActionMap().get(DefaultEditorKit.cutAction));
	    edit.add(textPane.getActionMap().get(DefaultEditorKit.copyAction));
	    edit.add(textPane.getActionMap().get(DefaultEditorKit.pasteAction));
	    edit.add(textPane.getActionMap().get(DefaultEditorKit.selectAllAction));
	}
	
	  // Create a simple JToolBar with some buttons.
	  protected JToolBar createToolBar() {
	    JToolBar bar = new JToolBar();

	    // Add simple actions for opening & saving.
	    bar.add(getOpenAction()).setText("");
	    bar.add(getSaveAction()).setText("");
	    bar.addSeparator();

	    // Add cut/copy/paste buttons.
	    bar.add(textPane.getActionMap().get(DefaultEditorKit.cutAction)).setText("");
	    bar.add(textPane.getActionMap().get(
	              DefaultEditorKit.copyAction)).setText("");
	    bar.add(textPane.getActionMap().get(
	              DefaultEditorKit.pasteAction)).setText("");
	    return bar;
	  }
	
	// Add icons and friendly names to actions we care about.
	protected void makeActionsPretty() {
		Action a;
		a = textPane.getActionMap().get(DefaultEditorKit.cutAction);
		a.putValue(Action.SMALL_ICON, new ImageIcon("icons/cut.gif"));
		a.putValue(Action.NAME, "Cut");
		
		a = textPane.getActionMap().get(DefaultEditorKit.copyAction);
		a.putValue(Action.SMALL_ICON, new ImageIcon("icons/copy.gif"));
		a.putValue(Action.NAME, "Copy");
		
		a = textPane.getActionMap().get(DefaultEditorKit.pasteAction);
		a.putValue(Action.SMALL_ICON, new ImageIcon("icons/paste.gif"));
		a.putValue(Action.NAME, "Paste");
		
		a = textPane.getActionMap().get(DefaultEditorKit.selectAllAction);
		a.putValue(Action.NAME, "Select All");
	}
	  // Subclass can override to use a different open action.
	  protected Action getOpenAction() { return openAction; }

	  // Subclass can override to use a different save action.
	  protected Action getSaveAction() { return saveAction; }
	  
	  protected JTextPane getTextComponent() { return textPane; }
	
	  //ACTION INNER CLASSES//

	  // An exit action
	  public class ExitAction extends AbstractAction {
	    public ExitAction() { super("Exit"); }
	    public void actionPerformed(ActionEvent ev) { System.exit(0); }
	  }

	  // An action that opens an existing file
	  class OpenAction extends AbstractAction {
	    public OpenAction() { 
	      super("Open", new ImageIcon("icons/open.gif")); 
	    }

	    // Query user for a filename and attempt to open and read the file into the
	    // text component.
	    public void actionPerformed(ActionEvent ev) {
	      JFileChooser chooser = new JFileChooser();
	      if (chooser.showOpenDialog(Test.this) !=
	          JFileChooser.APPROVE_OPTION)
	        return;
	      File file = chooser.getSelectedFile();
	      if (file == null)
	        return;

	      FileReader reader = null;
	      try {
	        reader = new FileReader(file);
	        textPane.read(reader, null);
	      }
	      catch (IOException ex) {
	        JOptionPane.showMessageDialog(Test.this,
	        "File Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
	      }
	      finally {
	        if (reader != null) {
	          try {
	            reader.close();
	          } catch (IOException x) {}
	        }
	      }
	    }
	  }

	  // An action that saves the document to a file
	  class SaveAction extends AbstractAction {
	    public SaveAction() {
	      super("Save", new ImageIcon("icons/save.gif"));
	    }

	    // Query user for a filename and attempt to open and write the text
	    // component content to the file.
	    public void actionPerformed(ActionEvent ev) {
	      JFileChooser chooser = new JFileChooser();
	      if (chooser.showSaveDialog(Test.this) !=
	          JFileChooser.APPROVE_OPTION)
	        return;
	      File file = chooser.getSelectedFile();
	      if (file == null)
	        return;

	      FileWriter writer = null;
	      try {
	        writer = new FileWriter(file);
	        textPane.write(writer);
	      }
	      catch (IOException ex) {
	        JOptionPane.showMessageDialog(Test.this,
	        "File Not Saved", "ERROR", JOptionPane.ERROR_MESSAGE);
	      }
	      finally {
	        if (writer != null) {
	          try {
	            writer.close();
	          } catch (IOException x) {}
	        }
	      }
	    }
	  }
	
	public static void main(String[] args){
		Test a = new Test();
	}
	
}

