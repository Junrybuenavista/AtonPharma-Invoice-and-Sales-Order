import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;

public class EditSC extends JInternalFrame implements ActionListener{

	
  
    Statement st;
    DecimalFormat form;
    JButton b1,b3;
    TextField t2,t3,t4,t5,t6;
    int type;
    String ID;
    JDesktopPane desk;
  	ResultSet set;
  	Cust_Sup CS;
    public EditSC(JDesktopPane desk,Statement st,int type,String ID ,Cust_Sup CS) 
    {
    			
    	super("ADD SUPPLIER", true, // resizable
					true, // closable
					true, // maximizable
					true);
    	this.desk=desk;
    	this.type=type;
    	this.st=st;
    	this.ID=ID;
       	this.CS=CS;
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
        t3=new TextField(15);
        t4=new TextField(15);
        t5=new TextField(15);
        
        if(type==1)p1.add(new JLabel("Customer Name:"));
        else p1.add(new JLabel("Supplier Name:"));
        
        p1.add(t5);
        
        p1.add(new JLabel("Tin No.:"));p1.add(t4);
        
        p1.add(new JLabel("Licence No.:"));p1.add(t2);
        p1.add(new JLabel("Address:"));p1.add(t3);
       
        p11.add(p1);
        
        p3.add(b1);p3.add(b3);
        
        load();
        add(p11,BorderLayout.CENTER);
        add(p3,BorderLayout.SOUTH);
        
    	if(type==1)setTitle("Edit Customer");
    	else setTitle("Edit Supplier");
    	setVisible(true);
        desk.add(this); 
    	setSize(400,215);
    	
    	moveToFront();
    }
    public void load() 
    { try {		
    			if(type==1)set=st.executeQuery("Select * FROM Customer where ID='"+ID+"'");
    			else set=st.executeQuery("Select * FROM Supplier where ID='"+ID+"'");
				while(set.next())
				{  
					if(type==1)t5.setText(set.getString("Customer_name"));
					else t5.setText(set.getString("Supplier_name"));
					t3.setText(set.getString("Address"));
					t4.setText(set.getString("Tin_no"));
					t2.setText(set.getString("License_no"));				
				}
    		}catch(Exception ee) {}
    }
    public void save()
    	{   int tempint=0;
    	    try
    	    {	if(type==1)
    	    	st.execute("UPDATE Customer SET Customer_name='"+t5.getText()+"', Tin_no='"+t4.getText()+"', License_no='"+t2.getText()+"', Address='"+t3.getText()+"'  WHERE ID='"+ID+"'");
    	    	else st.execute("UPDATE Supplier SET Supplier_name='"+t5.getText()+"', Tin_no='"+t4.getText()+"', License_no='"+t2.getText()+"', Address='"+t3.getText()+"'  WHERE ID='"+ID+"'");
	    	
    	    }
    	    catch(Exception ee){ee.printStackTrace();JOptionPane.showMessageDialog(this,"Error !");tempint=1;}
    	    if(tempint==0){
    	    JOptionPane.showMessageDialog(this,"Succesfully Save !");}
    	    dispose();
    	    CS.searchActivate();
    	    
    	}
    public void actionPerformed(ActionEvent e)
    	{
    		if(e.getSource()==b1){save(); t5.setText("");t4.setText("");t2.setText("");t3.setText("");}
    		if(e.getSource()==b3){this.dispose();}
    	}
    public static void main(String args[]){}
    
}