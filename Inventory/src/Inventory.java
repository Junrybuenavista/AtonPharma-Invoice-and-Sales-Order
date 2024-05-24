import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import javax.swing.table.TableColumn;

public class Inventory extends JInternalFrame implements ActionListener{
    
    Connection conn;
    Statement st;
    ResultSet set,set2;
    MyTable tab;
    int MyID;
    String arr2[][]=null; 
    String arr[];	
    JButton b1,b2,b3,searchbut,byDate,print,print2;
    JTextField searchtx;
    JComboBox searchbox;
    DecimalFormat form,form2;
    JDesktopPane desk;
    MsAccessDatabaseConnectionInJava8 conn8;
    ByDate dateto;
    String printSt[];
    SimpleDateFormat formatd = new SimpleDateFormat("MMM/dd/yyyy");
    public void search()
    	{
    			if(searchbox.getSelectedIndex()==2)
    				{	
    					if(MyID==2)load("SELECT (select Customer_name from Customer where ID=Sales_trans2.Customer)as tcust, Remarks, Discount, ID, Date_order, Invoice_no, DR_no FROM Sales_trans2 ",false);	
    					else load("SELECT (select Supplier_name from Supplier where ID=Sales_trans.Supplier)as tcust, Remarks, Discount, ID, Date_order, Invoice_no FROM Sales_trans ",false);	 
	    	   		}
	    			else if(searchbox.getSelectedIndex()==1)
	    			{
	    				if(MyID==2)load("SELECT (select Customer_name from Customer where ID=Sales_trans2.Customer)as tcust, DR_no, Remarks, Discount, ID, Date_order, Invoice_no FROM Sales_trans2 where Invoice_no ='"+searchtx.getText()+"'",false);
        				else load("SELECT (select Supplier_name from Supplier where ID=Sales_trans.Supplier)as tcust, Remarks, Discount, ID, Date_order, Invoice_no FROM Sales_trans where Invoice_no ='"+searchtx.getText()+"'",false);
	    			}
    				else load2(searchbox.getSelectedIndex());
    	}	
    public void actionPerformed(ActionEvent e)
    	{
    		if(e.getSource()==b1)
    			{   
    				if(MyID==2){new ItemDetails(st,this,2);}
    				else new ItemDetails(st,this,1);
    				search();
    			}
    		if(e.getSource()==b2)
    			{
    				int rest=JOptionPane.showConfirmDialog(this,"Are you sure you want to cancel this transaction?");
    				if(rest==0)
    					{
    						try{
    								if(MyID==2)
    								{
	    								st.execute("DELETE FROM Sales_invoice WHERE Trans_ID='"+tab.getValue(tab.getTb().getSelectedRow(),0)+"'");
	    								st.execute("DELETE FROM paymentC WHERE Trans_ID='"+tab.getValue(tab.getTb().getSelectedRow(),0)+"'");
	    								st.execute("DELETE FROM Sales_trans2 WHERE ID='"+tab.getValue(tab.getTb().getSelectedRow(),0)+"'"); 
	    								search();
	    							}
	    							else{
	    							    st.execute("DELETE FROM Sales_order WHERE Trans_ID='"+tab.getValue(tab.getTb().getSelectedRow(),0)+"'");
    									st.execute("DELETE FROM paymentS WHERE Trans_ID='"+tab.getValue(tab.getTb().getSelectedRow(),0)+"'");
    									st.execute("DELETE FROM Sales_trans WHERE ID='"+tab.getValue(tab.getTb().getSelectedRow(),0)+"'");  
	    								search();
	    								}
    							}catch(Exception ee){JOptionPane.showMessageDialog(this,"Please select transaction!");}
    					}
    			}
    		if(e.getSource()==b3)
    			{
    				
    				if(MyID==2)new Payment(st,tab.getValue(tab.getTb().getSelectedRow(),0),2,this);
    				else new Payment(st,tab.getValue(tab.getTb().getSelectedRow(),0),1,this);
    			}
    		if(e.getSource()==print)
    			{		
	    				if(tab.getTb().getSelectedRowCount()<=2)
	    				printjob1(1,tab.getTb().getSelectedRowCount());
	    				else JOptionPane.showMessageDialog(null,"Please select only a maximum of 2 item");
    				
    			}
    		if(e.getSource()==print2)
    			{	
    				new Notes(st,MyID,tab.getValue(tab.getTb().getSelectedRow(),0));
        			search();	
    			}
    		if(e.getSource()==searchbut)
    			{
    				search();
    			}
    		if(e.getSource()==byDate)
    			{   
    			
    				searchbox.setSelectedIndex(2);
    				dateto=new ByDate(st,MyID);
    				if(dateto.getRes()==0)return;
    				try{
    						if(dateto.getSearchBy()==0) {
								if(MyID==2)
								{
									 load("SELECT (select Customer_name from Customer where ID=Sales_trans2.Customer)as tcust, Remarks, Discount, ID, Date_order, Invoice_no, DR_no FROM Sales_trans2 where Customer="+dateto.getID()+" and Date_order >= #"+dateto.dateFrom()+"# and Date_order <= #"+dateto.dateTo()+"#",false);		 			
								}
								else
								{
									load("SELECT (select Supplier_name from Supplier where ID=Sales_trans.Supplier)as tcust, Remarks, Discount, ID, Date_order, Invoice_no FROM Sales_trans where Supplier= "+dateto.getID()+" and Date_order >= #"+dateto.dateFrom()+"# and Date_order <= #"+dateto.dateTo()+"#",false);
								}
    						}else {
	    							if(MyID==2)
									{
										 load("SELECT (select Customer_name from Customer where ID=Sales_trans2.Customer)as tcust, Remarks, Discount, ID, Date_order, Invoice_no, DR_no FROM Sales_trans2 where Date_order >= #"+dateto.dateFrom()+"# and Date_order <= #"+dateto.dateTo()+"#",false);		 			
									}
									else
									{
										load("SELECT (select Supplier_name from Supplier where ID=Sales_trans.Supplier)as tcust, Remarks, Discount, ID, Date_order, Invoice_no FROM Sales_trans where Date_order >= #"+dateto.dateFrom()+"# and Date_order <= #"+dateto.dateTo()+"#",false);
									}
    						}	
    				}catch(Exception ee) {}
			
    			}		
    	}
    public void printjob1(int ints1,int ints2)
    			{
    				arr=new String[14];
    				
    				 try{
    				 	if(MyID==2)
    				 	{
    				 		if(ints1==1)
    				 		{
    				 				int Rows[]=tab.getTb().getSelectedRows();
    				 				if(Rows.length==0) {JOptionPane.showMessageDialog(null,"Pleaes Select transaction");return;}
    				 				
		    				 		String InputDR=JOptionPane.showInputDialog(null,"Enter DR No.");
		    				 		if(InputDR==null)return;
		    				 		
		    				 		int count=0;
		    				 		while(count<Rows.length){
		    				 		st.execute("UPDATE Sales_invoice SET DR_no = '"+InputDR+"' WHERE ID='"+tab.getValue(Rows[count],0)+"'");
		    				 		count++;
		    				 		}
		    				 		
		    				 		
    				 		}
    				 		
    				 		if(ints1==2)
    				 		{
    				 				int Rows[]=tab.getTb().getSelectedRows();
    				 				if(Rows.length==0) {JOptionPane.showMessageDialog(null,"Pleaes Select transaction");return;}
    				 				
		    				 		String InputDR=JOptionPane.showInputDialog(null,"Enter Invoice No.");
		    				 		if(InputDR==null)return;
		    				 		
		    				 		int count=0;
		    				 		while(count<Rows.length){
		    				 		st.execute("UPDATE Sales_invoice SET Invoice_no = '"+InputDR+"' WHERE ID='"+tab.getValue(Rows[count],0)+"'");
		    				 		count++;
		    				 		}
    				 		}
    				 		new Printme(ints1,st,tab);
    				 		search();
    				 	}else {
    				 		new Notes(st,MyID,tab.getValue(tab.getTb().getSelectedRow(),0));
    		        		search();	
        				 	
    				 	}
    				 	
	            		
    				   }catch(Exception ee){ee.printStackTrace();JOptionPane.showMessageDialog(null,"Pleaes Select transaction");}
	            	
    			}					
    public Inventory(JDesktopPane desk,MsAccessDatabaseConnectionInJava8 conn8,int MyID) {
    		
    	
    	super("Sales Order", true, // resizable
					true, // closable
					true, // maximizable
					true);
		if(MyID==2){setTitle("Sales Invoice");}			
		this.desk=desk;
		this.MyID=MyID;
		set=conn8.getMySet();
    	st=conn8.getMyState();			
    	
    	
	    Container con=getContentPane();
	    con.setLayout(new BorderLayout());
	    form=new DecimalFormat("###,###");
	    form2=new DecimalFormat("###,###.00");
	  
	    
	    if(MyID==2)b1=new JButton("Sales Invoice");
	    else b1=new JButton("Sales Order");
	    
	    b1.addActionListener(this);
	    b2=new JButton("Cancel Transaction");b2.addActionListener(this);
	    b3=new JButton("Payment Detail");b3.addActionListener(this);
	    print=new JButton("Print DR");print.addActionListener(this);
	    print2=new JButton("Edit Remarks");
	    
	    print2.addActionListener(this);
	    tab=new MyTable();
	    if(MyID==2){
	    	String arr[]={"ID","Costumer Name","Date of order","Invoice No.","DR No.","Remarks","Discount","Subtotal","Amount Paid","Balance"};
	    	tab.setData(arr2,arr);
	    }else
	    {
	    	String arr[]={"ID","Supplier Name","Date of order","Invoice No.","Remarks","Discount","Subtotal","Amount Paid","Balance"};
	    	tab.setData(arr2,arr);	
	    }	
	    	    
	                       
	    TableColumn column=tab.getTb().getColumnModel().getColumn(1);
        column.setPreferredWidth(350);
        //column=table.getColumnModel().getColumn(1);
        //column.setPreferredWidth(70);
        
        
        tab.getTb().getColumnModel().getColumn(0).setMinWidth(0);
		tab.getTb().getColumnModel().getColumn(0).setMaxWidth(0);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		if(MyID==2) 
		{
			tab.getTb().getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
			tab.getTb().getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
			tab.getTb().getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
			tab.getTb().getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
		}
		else {tab.getTb().getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
			tab.getTb().getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
			tab.getTb().getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
			tab.getTb().getColumnModel().getColumn(8).setCellRenderer(rightRenderer);}
		
		
	    
	    JPanel pn=new JPanel(){public Dimension getPreferredSize(){return new Dimension(400,450);}};
	 
	    pn.setLayout(new BorderLayout());
	    pn.add(new JScrollPane(tab.getTb()),"Center"); 
	    JPanel ps=new JPanel(){public Dimension getPreferredSize(){return new Dimension(400,50);}};
	    ps.add(b1);ps.add(b2);ps.add(b3);ps.add(print2);
	    
	    //if(MyID==2){ps.add(print);}
	    
	    JPanel searchpanel=new JPanel();
	    searchbut=new JButton("Search");
	    searchbut.addActionListener(this);
	    searchtx=new JTextField(15);
	    String s[]={"by Supplier","by Invoice No.","All"};
	    if(MyID==2)s[0]="by Customer";
	    searchbox=new JComboBox(s);
	    searchbox.setSelectedIndex(2);
	    byDate=new JButton("Reports");
	    byDate.addActionListener(this);
	    searchpanel.add(searchbut);searchpanel.add(searchtx);searchpanel.add(searchbox);searchpanel.add(byDate);	
	    
	    con.add(searchpanel,"North");
	    con.add(pn,"Center");
	    con.add(ps,"South");
	    if(MyID==2)load("SELECT (select Customer_name from Customer where ID=Sales_trans2.Customer)as tcust, DR_no, Remarks, Discount, ID, Date_order, Invoice_no FROM Sales_trans2",false);	  
	    else       load("SELECT (select Supplier_name from Supplier where ID=Sales_trans.Supplier)as tcust, Remarks, Discount, ID, Date_order, Invoice_no FROM Sales_trans",false);	 
	    
	    
	    tab.getTb().addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent evnt) {

		        if (evnt.getClickCount() == 2) 
		        	{
		        		new view(st,tab.getValue(tab.getTb().getSelectedRow(),0),MyID);
		        		        		
		            }
		        
		     	}
			});
	    
	    show();
	    desk.add(this); 
    	setSize(1250,600);
    	moveToFront();
    }
    
    public void load(String ins,boolean isName)
    {
    	 arr=new String[10];
            
    	 	if(!isName) 
    	 	{
    	 		try
               	{
               		while(true)
               			{ 
               				tab.delete(0);
               			}	
               	}
               	catch(Exception ee){}
    	 	}
              // try
              // 	{
              // 		st.execute("Delete from inventory where Quantity=0");
              // 	}catch(Exception ee){ee.printStackTrace();}
               		
            		try
            		{   
                    	                      
            		set=st.executeQuery(ins);
            		double  ttotal=0,tbalance=0,tAmt=0;
            		while(set.next())
            		{   
            			
            			if(MyID==2)
            			{
            				//"ID","Costumer Name","Date of order","Invoice No.","DR No.","Remarks","Discount","Subtotal","Amount Paid","Balance"
            				
            				arr[0]=set.getString("ID");
            				arr[1]=set.getString("tcust");
            				arr[2]=formatd.format(set.getDate("Date_order"));
            				arr[3]=set.getString("Invoice_no");
            				arr[4]=set.getString("Dr_no");
            				arr[5]=set.getString("Remarks");
            				double tempdisc=set.getDouble("Discount");
            				arr[6]=form.format(100*tempdisc)+"%";

            				double totalM=0;
            				set2=st.executeQuery("SELECT Price, Quantity FROM Item_prof INNER JOIN Sales_invoice ON Item_prof.ID = (Select Item from Sales_order where Sales_order.ID=Sales_invoice.Order_ID) and Sales_invoice.Trans_ID = '"+arr[0]+"'");
            				while(set2.next())
            	    		{
            	       		
            	    		    double pricetemp=set2.getDouble("Price");
            	    		    int quantemp=set2.getInt("Quantity"); 
            	    		    double tempTotal=pricetemp*quantemp;            	    		 
            	    		    totalM+=tempTotal;
            					
            	    		}
            				double totalM2 = tempdisc*totalM;
            				arr[7]=form2.format(totalM-totalM2);
            				
            				set2=st.executeQuery("SELECT SUM(amount) as amountA FROM PaymentC WHERE Trans_ID='"+arr[0]+"'");
            				set2.next();
            				double tempAmount=set2.getDouble("amountA");
            				arr[8]=form2.format(tempAmount);
            				
            				arr[9]=form2.format(totalM-totalM2-tempAmount);
            			}
            			else{
            				arr[0]=set.getString("ID");
            				arr[1]=set.getString("tcust");
            				arr[2]=formatd.format(set.getDate("Date_order"));
            				double tempdisc=set.getDouble("Discount");
            				arr[3]=set.getString("Invoice_no");
            				arr[4]=set.getString("Remarks");
            				arr[5]=form.format(100*tempdisc)+"%";
            				
            				double totalM=0;
            				set2=st.executeQuery("SELECT Price, Quantity FROM Item_prof INNER JOIN Sales_order ON Item_prof.ID = Sales_order.Item and Sales_order.Trans_ID = '"+arr[0]+"'");
            				while(set2.next())
            	    		{
            	       		
            	    		    double pricetemp=set2.getDouble("Price");
            	    		    int quantemp=set2.getInt("Quantity"); 
            	    		    double tempTotal=pricetemp*quantemp;            	    		 
            	    		    totalM+=tempTotal;
            					
            	    		}
            				double totalM2 = tempdisc*totalM;
            				arr[6]=form2.format(totalM-totalM2);
            				
            				set2=st.executeQuery("SELECT SUM(amount) as amountA FROM PaymentS WHERE Trans_ID='"+arr[0]+"'");
            				set2.next();
            				double tempAmount=set2.getDouble("amountA");
            				arr[7]=form2.format(tempAmount);
            				
            				arr[8]=form2.format(totalM-totalM2-tempAmount);
            					
            				
	            			}

            			tab.insert(arr);
            		}
            		//arr[0]="";arr[1]="";arr[2]="";arr[3]="";arr[4]="";arr[5]="";arr[6]="";arr[7]="";arr[8]="";arr[9]="";arr[10]="";arr[11]="";arr[12]="";arr[13]="";arr[14]="";arr[15]="";arr[16]="";
            		//tab.insert(arr);
            		//arr[0]="";arr[1]="";arr[2]="";arr[3]="";arr[4]="";arr[5]="";arr[6]="";arr[7]="";arr[8]="";arr[9]="";arr[10]="Total:";arr[11]=form2.format(ttotal);arr[12]=form2.format(tAmt);arr[13]=form2.format(tbalance);arr[14]="";
            		//tab.insert(arr);
            	 }catch(Exception ee){ee.printStackTrace();JOptionPane.showMessageDialog(this,"Please select transaction!"+ee);}
    }
    public String ifnull(String s)
    {
    	if(s==null)return "";
    	return s;
    }
    public void load2(int ins)	
    {
    	try
       	{
       		while(true)
       			{ 
       				tab.delete(0);
       			}	
       	}
       	catch(Exception ee){}
    	
    		try
            {   
    			String stok="";
            			if(ins==0)
            			{   
            				if(MyID==2)set=st.executeQuery("select ID from Customer where Customer_name LIKE '"+searchtx.getText()+"%'");                   
            				else set=st.executeQuery("select ID from Supplier where Supplier_name LIKE '"+searchtx.getText()+"%'");
            				
            				while(set.next()) 
            				{
            					stok+=set.getString("ID")+" ";
            				}
            			}
            	StringTokenizer stok2 = new StringTokenizer(stok);
            	while (stok2.hasMoreElements()) 
            	{		
            			
            			if(ins==0)
            			{   
            				if(MyID==2)load("SELECT (select Customer_name from Customer where ID=Sales_trans2.Customer)as tcust, DR_no, Remarks, Discount, ID, Date_order, Invoice_no FROM Sales_trans2 where Customer="+stok2.nextElement(),true);
            				else load("SELECT (select Supplier_name from Supplier where ID=Sales_trans.Supplier)as tcust, Remarks, Discount, ID, Date_order, Invoice_no FROM Sales_trans where Supplier="+stok2.nextElement(),true);
            			}
            	}

            }catch(Exception ee){ee.printStackTrace();JOptionPane.showMessageDialog(this,"not found");}
    }
   
    
    	public int getyears(int ins)
         		{   
         			int ret=-1;
         			
         			if(ins==114)ret=2014;
         			if(ins==115)ret=2015;
         			if(ins==116)ret=2016;
         			if(ins==117)ret=2017;
         			if(ins==118)ret=2018;
         			if(ins==119)ret=2019;
         			if(ins==120)ret=2020;
         			return ret;
         		}  
        	public String getMonth(int i)
    		{
    			String ret="";
    			if(i==1)ret="Jan";
    			if(i==2)ret="Feb";
    			if(i==3)ret="Mar";
    			if(i==4)ret="Apr";
    			if(i==5)ret="May";
    			if(i==6)ret="Jun";
    			if(i==7)ret="Jul";
    			if(i==8)ret="Aug";
    			if(i==9)ret="Sep";
    			if(i==10)ret="Oct";
    			if(i==11)ret="Nov";
    			if(i==12)ret="Dec";
    			return ret;
   		 } 		
    public static void main(String args[]){}
}