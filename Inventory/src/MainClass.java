	
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.*;
import java.sql.*;

public class MainClass implements ActionListener{
    
    MsAccessDatabaseConnectionInJava8 conn8;    private JDesktopPane jdpDesktop;
    private static int openFrameCount = 0;
    private BufferedImage img;
    JToolBar toolBar;
    JButton b1,b2,b3,b4,b5,b6,b7; 
    ImageIcon img2;
    Image img3;
    Statement st;
	public void actionPerformed(ActionEvent ee)
		{
			if(ee.getSource()==b1){new StockProfile(jdpDesktop,conn8);}
			if(ee.getSource()==b2){new AddSupplier(jdpDesktop,conn8);}
			if(ee.getSource()==b3){new Inventory(jdpDesktop,conn8,1);}
			if(ee.getSource()==b4){new AddCustomer(jdpDesktop,conn8);}
			if(ee.getSource()==b5){new Items(jdpDesktop,conn8);}
			if(ee.getSource()==b6){new Cust_Sup(jdpDesktop,conn8);}
			if(ee.getSource()==b7){new Inventory(jdpDesktop,conn8,2);}
	
		}
			
	
	
    public MainClass() {
    	
    	conn8=new MsAccessDatabaseConnectionInJava8();
    	if(!conn8.serials())
    		{
    			return;
    		}
    	st=conn8.getMyState();
    	
    	
    	login log=new login(jdpDesktop,st);
        JFrame frame = new JFrame("INVENTORY SYSTEM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
      
        
        
        JPanel north = new JPanel(new BorderLayout());
        JToolBar toolBar = new JToolBar();
        b1=new JButton(new ImageIcon("image\\add.png"));b2=new JButton(new ImageIcon("image\\DEL.png"));b3=new JButton(new ImageIcon("image\\salesorder.png"));b4=new JButton(new ImageIcon("image\\AddCust.png"));b5=new JButton(new ImageIcon("image\\listitem.png"));
        b6=new JButton(new ImageIcon("image\\profiles.png"));b7=new JButton(new ImageIcon("image\\salesinvoice.png"));
        b1.addActionListener(this);b2.addActionListener(this);b3.addActionListener(this);b4.addActionListener(this);b5.addActionListener(this);b6.addActionListener(this);b7.addActionListener(this);
        toolBar.add(b1);toolBar.add(b2);toolBar.add(b4);toolBar.add(b5);toolBar.add(b6);toolBar.add(b3);toolBar.add(b7);
        north.add(toolBar, "North");
        north.setBackground(new Color(68,107,140));
        
        
        try {
        	 
             //URL imagePath = new URL(getClass().getResource("image\\logo2.png").toString());
             //img = ImageIO.read(imagePath);
        	 img2=new  ImageIcon("image\\Icon.png");
             img3=img2.getImage();
        } catch (Exception ex) {
            
        }

        
        
        jdpDesktop = new JDesktopPane() {
        
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        	 double w1 = screenSize.getWidth();
			 double h1 = screenSize.getHeight();
		
			 
			 	
            protected void paintComponent(Graphics grphcs) {
            	
                super.paintComponent(grphcs);
                grphcs.drawImage(img3, ((int)w1/2)-227,((int)h1/2)-290, null);
            
                
            }

           
        };

       // chk3=new Check3();
	   //	chk3.load();
       
          
        frame.getContentPane().add(north, "North");
        frame.getContentPane().add(jdpDesktop);
        
        

        // Make dragging faster by setting drag mode to Outline
        jdpDesktop.putClientProperty("JDesktopPane.dragMode", "outline");
        
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Frame");
        menu.setMnemonic(KeyEvent.VK_N);
        JMenuItem menuItem = new JMenuItem("New IFrame");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
            }
        });
        menu.add(menuItem);
        menuBar.add(menu);
        return menuBar;
        
    }

   
    
    public static void main(String[] args) {
    	try {
             UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    	    }catch(Exception ee){ee.printStackTrace();}
    	
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainClass();
            }
 
        });
    }

    class MyInternalFrame extends JInternalFrame {

        static final int xPosition = 30, yPosition = 30;

        public MyInternalFrame() {
            super("IFrame #" + (++openFrameCount), true, // resizable
                    true, // closable
                    true, // maximizable
                    true);// iconifiable
            setSize(300, 300);
            // Set the window's location.
            setLocation(xPosition * openFrameCount, yPosition
                    * openFrameCount);
        }
    }
}

