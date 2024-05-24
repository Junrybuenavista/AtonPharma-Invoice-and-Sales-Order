import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;

public class EditItem extends JFrame implements ActionListener{

	MsAccessDatabaseConnectionInJava8 conn8;
    Statement st;
    DecimalFormat form;
    JButton b1,b3;
    TextField t2,t4,t5;
    JComboBox unit;    
    ResultSet set;
    String ID;
    public EditItem(Statement st,String ID) 
    {
    	
    	
    	
    	this.st=st;
    	this.ID=ID;
    	form=new DecimalFormat(".00");
    	GridLayout grid=new GridLayout(4,2);
    	grid.setVgap(6);
    	setLayout(new BorderLayout());
    

        JPanel p11=new JPanel(){public Dimension getPreferredSize(){return new Dimension(400,150);}};
        JPanel p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(350,125);}};
        JPanel p3=new JPanel(){public Dimension getPreferredSize(){return new Dimension(350,40);}};
        
        p1.setLayout(grid);
       
                       
      
        b1=new JButton("SAVE");b1.addActionListener(this);   
        b3=new JButton("CANCEL");b3.addActionListener(this);  
        t2=new TextField(15);
        t4=new TextField(15);
        t5=new TextField(15);
        
        String sunit[]={"vial","ampule","piece","box","bottle","tablet","Capsule"};
        unit=new JComboBox(sunit);
        
        p1.add(new JLabel("Brand Name:"));p1.add(t5);
        
        p1.add(new JLabel("Generic Name:"));p1.add(t4);
        
        p1.add(new JLabel("UM:"));p1.add(t2);
        p1.add(new JLabel("Unit:"));p1.add(unit);
       
        p11.add(p1);
        
        p3.add(b1);p3.add(b3);
        
        JPanel finalP=new JPanel(){public Dimension getPreferredSize(){return new Dimension(400,140);}};
        finalP.add(p11,BorderLayout.CENTER);
        load();
        
        Object[] message = new Object[1];          
        message[0]=finalP;
                                
    
	      

        String[] options = {"Save","Cancel"};
  
   		int result = JOptionPane.showOptionDialog(
   		null,
   		message,
   		"Edit Item",
   		JOptionPane.PLAIN_MESSAGE,
   		JOptionPane.PLAIN_MESSAGE,
   		null,
   		options,
   		options[0]

				);
   		if(result==0) {save();}
    
   		
    }
    public void load() 
    { 
    	
    	
    	try {	
    		
    			set=st.executeQuery("Select * FROM Item_prof where ID='"+ID+"'");	
				set.next();

					t5.setText(set.getString("Brand_name"));
					t4.setText(set.getString("Generic_name"));
					t2.setText(set.getString("UM"));
					unit.setSelectedItem(set.getString("Unit"));				
				
    		}catch(Exception ee) {ee.printStackTrace();t5.setText("ff");
    		t4.setText("ff");
    		t2.setText("ff");}
    }
    public String getBrand() {return t5.getText();}
    public void save()
    	{   int tempint=0;
    	    try
    	    {
    	    	st.execute("UPDATE Item_prof SET Brand_name='"+t5.getText()+"', Generic_name='"+t4.getText()+"', UM='"+t2.getText()+"', Unit='"+unit.getSelectedItem()+"'  WHERE ID='"+ID+"'");
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