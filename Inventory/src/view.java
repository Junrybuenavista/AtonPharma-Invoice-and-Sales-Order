import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class view extends JInternalFrame{
  
    MyTable tab;
	Statement st;
	ResultSet set,set2;
	String arr2[][];
	String arr[];
	String ID;
	DecimalFormat form2;
	SimpleDateFormat formatd = new SimpleDateFormat("MMM/dd/yyyy");
	int MyID;
    public view(Statement st,String ID,int MyID) 
    {
    	super("Invoice", true, // resizable
					true, // closable
					true, // maximizable
					true);
    	
    	this.st=st;
    	this.ID=ID;
    	this.MyID=MyID;
    	String arr[]={"ID","Brand Name","Generic Name","UM","Unit","Lot No.","Exp. Date","Item Price","Quantity","Amount"};
    	form2=new DecimalFormat("###,###.00");
    	tab=new MyTable();	 
		tab.setData(arr2,arr);
		tab.getTb().getColumnModel().getColumn(0).setMinWidth(0);
		tab.getTb().getColumnModel().getColumn(0).setMaxWidth(0);
		
		TableColumn column=tab.getTb().getColumnModel().getColumn(1);
        column.setPreferredWidth(250);
        column=tab.getTb().getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		tab.getTb().getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
    	
       	JPanel finalP=new JPanel();
    	
     	
        
        JPanel p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(1100,300);}};
        p1.setLayout(new BorderLayout());
        p1.add(new JScrollPane(tab.getTb()),"Center");
      
        
        
        finalP.add(p1,BorderLayout.CENTER);
        
        
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
        			 
        			}
    	
    }
    public void load() 
    {
    	arr=new String[10];
    	try 
    	{   if(MyID==2) {
				    		double totalM=0;
				    		set=st.executeQuery("SELECT Sales_invoice.ID, Date_expiry, Sales_invoice.Price, Sales_invoice.Quantity, Sales_order.Item, Sales_order.Lot_no FROM Sales_invoice INNER JOIN Sales_order ON Sales_invoice.Order_ID = Sales_order.ID and Sales_invoice.Trans_ID = '"+ID+"'");
				       		while(set.next())
				    		{
				       			arr[0]=set.getString("Sales_invoice.ID");
				       			double pricetemp=set.getDouble("Sales_invoice.Price");
				    		    arr[6]= formatd.format(set.getDate("Date_expiry"));
				    		    int quantemp=set.getInt("Sales_invoice.Quantity");
				    		    arr[7]=form2.format(pricetemp);
				    		    arr[8]=quantemp+"";
				    		    double tempTotal=pricetemp*quantemp;
				    		    arr[9]=form2.format(tempTotal);
				    		    totalM+=tempTotal;
				    		    
				    		    set2=st.executeQuery("SELECT * from Item_prof where ID="+set.getString("Sales_order.Item"));
				    		    set2.next();
				    		    arr[1]=set2.getString("Brand_name");
				    		    arr[2]=set2.getString("Generic_name");
				    		    arr[3]=set2.getString("UM");
				    		    arr[4]=set2.getString("Unit");
				    		    arr[5]=set.getString("Sales_order.Lot_no");
				    		    
								tab.insert(arr);
				    		}
				       		arr[0]="";arr[1]="";arr[2]="";arr[3]="";arr[4]="";arr[5]="";arr[6]="";arr[7]="";arr[8]="";arr[9]="";
				       		tab.insert(arr);
				       		arr[0]="";arr[1]="";arr[2]="";arr[3]="";arr[4]="";arr[5]="";arr[6]="";arr[7]="";arr[8]="Subtotal";arr[9]=form2.format(totalM);
				       		tab.insert(arr);
    					}
    		else {
			    		double totalM=0;
			    		set=st.executeQuery("SELECT Sales_order.ID, Brand_name, Generic_name, UM, Unit, Date_expiry, Price, Quantity, Lot_no FROM Item_prof INNER JOIN Sales_order ON Item_prof.ID = Sales_order.Item and Sales_order.Trans_ID = '"+ID+"'");
			       		while(set.next())
			    		{
			       			arr[0]=set.getString("Sales_order.ID");
			    		    arr[1]=set.getString("Brand_name");
			    		    arr[2]=set.getString("Generic_name");
			    		    arr[3]=set.getString("UM");
			    		    arr[4]=set.getString("Unit");
			    		    arr[5]=set.getString("Lot_no");
			    		    double pricetemp=set.getDouble("Price");
			    		    arr[6]= formatd.format(set.getDate("Date_expiry"));
			    		    int quantemp=set.getInt("Quantity");
			    		    arr[7]=form2.format(pricetemp);
			    		    arr[8]=quantemp+"";
			    		    double tempTotal=pricetemp*quantemp;
			    		    arr[9]=form2.format(tempTotal);
			    		    totalM+=tempTotal;
							tab.insert(arr);
			    		}
			       		arr[0]="";arr[1]="";arr[2]="";arr[3]="";arr[4]="";arr[5]="";arr[6]="";arr[7]="";arr[8]="";arr[9]="";
			       		tab.insert(arr);
			       		arr[0]="";arr[1]="";arr[2]="";arr[3]="";arr[4]="";arr[5]="";arr[6]="";arr[7]="";arr[8]="Subtotal";arr[9]=form2.format(totalM);
			       		tab.insert(arr);
    				}
    	}catch(Exception ee) {ee.printStackTrace();}
    }
   
 
    
}