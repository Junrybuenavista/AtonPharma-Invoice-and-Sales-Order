import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;

public class Payment_check extends JInternalFrame{

	
  
  
    DecimalFormat form;
    
    JTextField t4,t5,t6;
    JComboBox comboM,comboD,comboY;
    JComboBox comboM2,comboD2,comboY2;
    MyTable tab;
	String stcash="";
	String ID;
	Statement st; 
	int MyID;	
    public Payment_check(String ID,Statement st,int MyID) 
    {
    	super("Check Payment", true, // resizable
					true, // closable
					true, // maximizable
					true);
    
    	this.tab=tab;
    	this.MyID=MyID;
    	this.ID=ID;
    	this.st=st;
    	
       	JPanel finalP=new JPanel();
    	form=new DecimalFormat("###,###.00");
    
    	GridLayout grid=new GridLayout(5,2);
    	grid.setVgap(6);
    	setLayout(new BorderLayout());
     	JPanel p1c=new JPanel();
     	JPanel p2c=new JPanel();
        setCombo();
        p1c.add(comboM);p1c.add(comboD);p1c.add(comboY);
		p2c.add(comboM2);p2c.add(comboD2);p2c.add(comboY2);
        
        JPanel p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(450,175);}};
       
        
        p1.setLayout(grid);
       
                       
      
       
      
        t4=new JTextField(15);
        t5=new JTextField(15);
        t6=new JTextField(15);
        
        
        p1.add(new JLabel("Amount:"));p1.add(t5);      
        p1.add(new JLabel("Check No.:"));p1.add(t4);
        p1.add(new JLabel("Receipt No.:"));p1.add(t6);
        p1.add(new JLabel("Check Date:"));p1.add(p1c);
        p1.add(new JLabel("Payment Date:"));p1.add(p2c);
       
       
       
        
       
        
        finalP.setLayout(new BorderLayout());
        finalP.add(p1,BorderLayout.CENTER);
       
        
    	
    			 Object[] message = new Object[1];          
                 message[0]=finalP;
                                         
             	String title;
                if(MyID==2)title="Sales Invoice"; 
                else title="Sales Order";

                 String[] options = {"OK","CANCEL"};
           
		    		int result = JOptionPane.showOptionDialog(
		    		null,
		    		message,
		    		title,
		    		JOptionPane.DEFAULT_OPTION,
		    		JOptionPane.INFORMATION_MESSAGE,
		    		null,
		    		options,
		    		options[0]

						);
					
        		if(result==0)
        			{
        				try
        				{
        					if(MyID==2)st.execute("INSERT INTO paymentC (Trans_ID,Payment_date,Payment_type,Amount,Check_no,Check_date,Rec_no) VALUES ('"+ID+"',#"+comboY2.getSelectedItem()+"-"+(comboM2.getSelectedIndex()+1)+"-"+comboD2.getSelectedItem()+"#,'Check',"+t5.getText()+",'"+t4.getText()+"',#"+comboY.getSelectedItem()+"-"+(comboM.getSelectedIndex()+1)+"-"+comboD.getSelectedItem()+"#,'"+t6.getText()+"') ");
        					else st.execute("INSERT INTO paymentS (Trans_ID,Payment_date,Payment_type,Amount,Check_no,Check_date,Rec_no) VALUES ('"+ID+"',#"+comboY2.getSelectedItem()+"-"+(comboM2.getSelectedIndex()+1)+"-"+comboD2.getSelectedItem()+"#,'Check',"+t5.getText()+",'"+t4.getText()+"',#"+comboY.getSelectedItem()+"-"+(comboM.getSelectedIndex()+1)+"-"+comboD.getSelectedItem()+"#,'"+t6.getText()+"') ");
        				}catch(Exception ee){JOptionPane.showMessageDialog(null,"Error! "+ee);}
        			}
    	
    }
     public String getAmount()
     {
     	return t5.getText();
     }
     public String getRec()
     {
     	return t6.getText();
     }
      public String getDate()
     {
     	return comboY.getSelectedItem()+"-"+(comboM.getSelectedIndex()+1)+"-"+comboD.getSelectedItem();
     }
      public String getNo()
     {
     	return t4.getText();
     }
     public void setCombo()
         	{
         		String s1[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
         	    String s2[]={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
         	    String s3[]={"2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024",};	
         		comboM=new JComboBox(s1);comboD=new JComboBox(s2);comboY=new JComboBox(s3);
         		comboM2=new JComboBox(s1);comboD2=new JComboBox(s2);comboY2=new JComboBox(s3);
         		
         	
         	}	
    public static void main(String args[]){}
    
}