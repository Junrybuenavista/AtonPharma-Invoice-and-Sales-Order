import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;

public class Invoice extends JInternalFrame{
  
    MyTable tab,tab3;
    JComboBox comboM,comboD,comboY; 
	Statement st;
	ResultSet set;
	int MyID;
	String arr2[][];
	String arr[];
	JTextArea remarks;
	JTextField searchtx,invoicetx,discounttx,DRtx;
	String query="",ID="";
	
	
    public Invoice(Statement st,MyTable tab3,int MyID) 
    {
    	super("Invoice Item", true, // resizable
					true, // closable
					true, // maximizable
					true);
    
    	this.MyID=MyID;
    	this.st=st;
    	this.tab3=tab3;
       	JPanel finalP=new JPanel();

    	GridLayout grid =new  GridLayout(3,2); 
    	if(MyID==2) grid.setRows(4);
    	
    	grid.setVgap(1);
    	setLayout(new BorderLayout());
     	JPanel p1c=new JPanel();
     	searchtx=new JTextField();
     	invoicetx=new JTextField();
     	discounttx=new JTextField();
     	DRtx=new JTextField();
     	remarks=new JTextArea(3,15);
     	
	    
        setCombo();
        p1c.add(comboM);p1c.add(comboD);p1c.add(comboY);
        
        String arr[]={"ID","Supplier Name"};
        if(MyID==2)arr[1]="Customer";
		tab=new MyTable();	 
		tab.setData(arr2,arr);
		tab.getTb().getColumnModel().getColumn(0).setMinWidth(0);
		tab.getTb().getColumnModel().getColumn(0).setMaxWidth(0);
		JPanel p1;
		if(MyID==2) p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(450,160);}};
		else p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(450,120);}};
        JPanel p2=new JPanel(){public Dimension getPreferredSize(){return new Dimension(450,160);}};
        JPanel p3=new JPanel(){public Dimension getPreferredSize(){return new Dimension(450,60);}};
        
        p2.setLayout(new BorderLayout());
        p2.add(new JScrollPane(tab.getTb()),"Center");
        
        p1.setLayout(grid);  
        p1.add(new JLabel("Date Of Order:"));p1.add(p1c);
        p1.add(new JLabel("Discount:"));p1.add(discounttx);
        p1.add(new JLabel("Invoice No.:"));p1.add(invoicetx);
        if(MyID==2) {p1.add(new JLabel("DR No.:"));p1.add(DRtx);}
        finalP.setLayout(new BorderLayout());
        
        grid=new GridLayout(1,2);
        p3.setLayout(grid);
        p3.add(new JLabel("Remarks:"));p3.add(remarks);
        
        finalP.add(p2,BorderLayout.NORTH);
        finalP.add(p1,BorderLayout.CENTER);
        finalP.add(p3,BorderLayout.SOUTH);
        
        load();
      
        
    	
    			 Object[] message = new Object[1];          
                 message[0]=finalP;
                                         
             
                

                 String[] options = {"OK","CANCEL"};
           
		    		int result = JOptionPane.showOptionDialog(
		    		null,
		    		message,
		    		"Invoice",
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
    			 		
    			 		if(MyID==2) {
    			 						st.execute("INSERT INTO Sales_trans2 (Sales_trans2.Customer, Sales_trans2.Date_order, Sales_trans2.Discount, Sales_trans2.Invoice_no, Sales_trans2.Remarks,DR_no) VALUES ('"+tab.getValue(tab.getTb().getSelectedRow(),0)+"',#"+comboY.getSelectedItem()+"-"+(comboM.getSelectedIndex()+1)+"-"+comboD.getSelectedItem()+"#,'"+Double.parseDouble(discounttx.getText())/100+"','"+invoicetx.getText()+"','"+remarks.getText()+"','"+DRtx.getText()+"')");
    			 						set=st.executeQuery("SELECT MAX(ID) AS ID FROM Sales_trans2");set.next();
    			 						ID=set.getString("ID");
    			 					}	
    			 		
    			 		else 		{ 
    			 			
    			 						st.execute("INSERT INTO Sales_trans (Sales_trans.Supplier, Sales_trans.Date_order, Sales_trans.Discount, Sales_trans.Invoice_no, Sales_trans.Remarks) VALUES ('"+tab.getValue(tab.getTb().getSelectedRow(),0)+"',#"+comboY.getSelectedItem()+"-"+(comboM.getSelectedIndex()+1)+"-"+comboD.getSelectedItem()+"#,'"+Double.parseDouble(discounttx.getText())/100+"','"+invoicetx.getText()+"','"+remarks.getText()+"')");
    			 						set=st.executeQuery("SELECT MAX(ID) AS ID FROM Sales_trans");set.next();
    			 						ID=set.getString("ID");
    			 		
    			 					}

    			 	}catch(Exception ee) {ee.printStackTrace();JOptionPane.showMessageDialog(null,"Error!");return;}
        				
        			 	try 
        			 	{	
        			 		for(int ii=0;ii<tab3.getcount();ii++) 
							{   if(MyID==2) query+="INSERT INTO Sales_invoice (Order_ID, Trans_ID, price, Quantity) VALUES ('"+tab3.getValue(ii,0)+"','"+ID+"','"+tab3.getValue(ii,3)+"','"+tab3.getValue(ii,4)+"') ";
								else        query+="INSERT INTO Sales_order (Item, Trans_ID, Date_expiry, price, Quantity, Lot_no) VALUES ('"+tab3.getValue(ii,0)+"','"+ID+"',#"+tab3.getValue(ii,2)+"#,'"+tab3.getValue(ii,3)+"','"+tab3.getValue(ii,4)+"','"+tab3.getValue(ii,1)+"') ";
							}
        			 		st.execute(query);
        			 		JOptionPane.showMessageDialog(null,"Invoice Save!");
        			 	}catch(Exception ee) {ee.printStackTrace();
	
        			 		try {
		        			 		if(MyID==2)st.execute("DELETE FROM Sales_trans2 WHERE ID="+ID);
		        			 		else st.execute("DELETE FROM Sales_trans WHERE ID="+ID);
		        			 		JOptionPane.showMessageDialog(null,"Error!");
        			 		}catch(Exception eee) {}
        			 	}
        			}
    	
    }
    public void load() 
    {
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
   
    public String getsearch()
    	{
    		return searchtx.getText();
    	}
	
     public String dateFrom()
	     {
	     	return comboY.getSelectedItem()+"-"+(comboM.getSelectedIndex()+1)+"-"+comboD.getSelectedItem();
	     }
    
    
     public void setCombo()
         	{
         		String s1[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
         	    String s2[]={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
         	    String s3[]={"2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024",};	
         		comboM=new JComboBox(s1);comboD=new JComboBox(s2);comboY=new JComboBox(s3);
         			        	
         	}	
    public static void main(String args[]){}
    
}