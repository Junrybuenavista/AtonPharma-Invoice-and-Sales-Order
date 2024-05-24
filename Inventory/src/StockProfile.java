import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;

public class StockProfile extends JInternalFrame implements ActionListener{

	MsAccessDatabaseConnectionInJava8 conn8;
  
    Statement st;
    DecimalFormat form;
    JButton b1,b3;
    TextField t2,t4,t5;
    JComboBox unit;
    JDesktopPane desk;
	 
    public StockProfile(JDesktopPane desk,MsAccessDatabaseConnectionInJava8 conn8) 
    {
    	super("ITEM PROFILE", true, // resizable
					true, // closable
					true, // maximizable
					true);
    	this.desk=desk;
    
    	st=conn8.getMyState();
    
       	
    	form=new DecimalFormat(".00");
    	this.st=st;
    	GridLayout grid=new GridLayout(4,2);
    	grid.setVgap(6);
    	setLayout(new BorderLayout());
    

        JPanel p11=new JPanel(){public Dimension getPreferredSize(){return new Dimension(150,150);}};
        JPanel p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(350,125);}};
        JPanel p3=new JPanel(){public Dimension getPreferredSize(){return new Dimension(350,40);}};
        
        p1.setLayout(grid);
       
                       
      
        b1=new JButton("SAVE");b1.addActionListener(this);   
        b3=new JButton("CANCEL");b3.addActionListener(this);  
        t2=new TextField(15);
        t4=new TextField(15);
        t5=new TextField(15);
        
        String sunit[]={"VIALS","AMPULES","PIECES","BOXES","BOTTLES","TABLETS","CAPSULES","NEBULES","ROLLS","GALLONS","TUBES","SACHETS","PACKS","PADS","PAIRS"};
        unit=new JComboBox(sunit);
        
        p1.add(new JLabel("Brand Name:"));p1.add(t5);
        
        p1.add(new JLabel("Generic Name:"));p1.add(t4);
        
        p1.add(new JLabel("UM:"));p1.add(t2);
        p1.add(new JLabel("Unit:"));p1.add(unit);
       
        p11.add(p1);
        
        p3.add(b1);p3.add(b3);
        
        
        add(p11,BorderLayout.CENTER);
        add(p3,BorderLayout.SOUTH);
        
    	
    	setVisible(true);
        desk.add(this); 
    	setSize(400,215);
    	moveToFront();
    }
    public void save()
    	{   int tempint=0;
    	    try
    	    {
    	    	st.execute("Insert Into Item_prof(Brand_name,Generic_name,UM,Unit) values('"+t5.getText()+"','"+t4.getText()+"','"+t2.getText()+"','"+unit.getSelectedItem()+"')");
    	    }
    	    catch(Exception ee){ee.printStackTrace();JOptionPane.showMessageDialog(this,"Error !");tempint=1;}
    	    if(tempint==0){
    	    JOptionPane.showMessageDialog(this,"Succesfully Save !");}
    	}
    public void actionPerformed(ActionEvent e)
    	{
    		if(e.getSource()==b1){save(); t5.setText("");t4.setText("");t2.setText("");}
    		if(e.getSource()==b3){this.dispose();}
    	}
    public static void main(String args[]){}
    
}