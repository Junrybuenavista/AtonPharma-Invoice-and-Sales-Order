import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;


public class ByDate extends JInternalFrame implements ActionListener{

	
  
  

    
    JComboBox searchbox;
    JComboBox comboM,comboD,comboY;
    JComboBox comboM2,comboD2,comboY2;
    int resultme=0;
	Statement st;
	int MyID;
	MyTable tab;
	String arr2[][];
	String arr[];
	ResultSet set;
	
	public void actionPerformed(ActionEvent e) 
	{
		load();
	}
    public ByDate(Statement st,int MyID) 
    {
    	super("Reports", true, // resizable
					true, // closable
					true, // maximizable
					true);
    
    	
    	this.st=st;
    	this.MyID=MyID;
       	JPanel finalP=new JPanel();
    
    
    	GridLayout grid=new GridLayout(4,2);
    	grid.setVgap(6);
    	setLayout(new BorderLayout());
     	JPanel p1c=new JPanel();
     	JPanel p2c=new JPanel();
     	
     	String arr[]={"ID","Supplier Name"};
     	if(MyID==2)arr[1]="Customer";
     	tab=new MyTable();	 
		tab.setData(arr2,arr);
		tab.getTb().getColumnModel().getColumn(0).setMinWidth(0);
		tab.getTb().getColumnModel().getColumn(0).setMaxWidth(0);
		JPanel p2=new JPanel(){public Dimension getPreferredSize(){return new Dimension(450,160);}};
		p2.setLayout(new BorderLayout());
        p2.add(new JScrollPane(tab.getTb()),"Center");
		
		
        
     	String s[]={"Supplier","All"};
	    if(MyID==2)s[0]="Customer";
	    searchbox=new JComboBox(s);
	    searchbox.addActionListener(this);
	    
        setCombo();
        p1c.add(comboM);p1c.add(comboD);p1c.add(comboY);
		p2c.add(comboM2);p2c.add(comboD2);p2c.add(comboY2);
        
        JPanel p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(450,160);}};
       
        
        p1.setLayout(grid);
        
        p1.add(new JLabel("Search by:"));p1.add(searchbox);
        
        p1.add(new JLabel("Date From:"));p1.add(p1c);
        p1.add(new JLabel("Date To:"));p1.add(p2c);
       
        finalP.setLayout(new BorderLayout());
        finalP.add(p1,BorderLayout.CENTER);
        finalP.add(p2,BorderLayout.SOUTH);
       load();
        
    	
    			 Object[] message = new Object[1];          
                 message[0]=finalP;
                                         
             
                

                 String[] options = {"OK","CANCEL"};
           
		    		int result = JOptionPane.showOptionDialog(
		    		null,
		    		message,
		    		"Reports",
		    		JOptionPane.DEFAULT_OPTION,
		    		JOptionPane.INFORMATION_MESSAGE,
		    		null,
		    		options,
		    		options[0]

						);
					
        		if(result==0)
        			{
        			   	resultme=1;
        			}
    	
    }
    public void load() 
    {	
    	try
       	{
       		while(true)
       			{ 
       				tab.delete(0);
       			}	
       	}
       	catch(Exception ee){}
    	
    	
    	if(searchbox.getSelectedIndex()==0) {
	    	arr=new String[2];
	    	try 
	    	{
	    		if(MyID==2) set=st.executeQuery("Select Customer_name, ID from Customer order by Customer_name");
	    		else set=st.executeQuery("Select Supplier_name, ID from Supplier order by Supplier_name");
	       		while(set.next())
	    		{
	       			arr[0]=set.getString("ID");
	       			if(MyID==2) arr[1]=set.getString("Customer_name"); 
	       			else arr[1]=set.getString("Supplier_name");  			
					tab.insert(arr);
	    		}
	    	}catch(Exception ee) {ee.printStackTrace();}
    	}
    	
    }
    public String getID()
    	{
    		return tab.getValue(tab.getTb().getSelectedRow(),0);
    	}
    public int getSearchBy() 
    	{
    		return searchbox.getSelectedIndex();
    	}
	public int getRes()
	     {
	     	return resultme;
	     }
     public String dateFrom()
	     {
	     	return comboY.getSelectedItem()+"-"+(comboM.getSelectedIndex()+1)+"-"+comboD.getSelectedItem();
	     }
     public String dateTo()
	     {
	     	return comboY2.getSelectedItem()+"-"+(comboM2.getSelectedIndex()+1)+"-"+comboD2.getSelectedItem();
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