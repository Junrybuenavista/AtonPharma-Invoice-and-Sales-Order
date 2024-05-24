import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.SimpleDateFormat;

public class Payment extends JInternalFrame implements ActionListener{

	
  
    int MyID;
    DecimalFormat form;
    
    SimpleDateFormat formatd = new SimpleDateFormat("MMM/dd/yyyy");
    String arr2[][]=null;
	String stcash="";
	Statement st;
	String ID; 
	ResultSet set;
	JButton butcash,butchk,butdelete;
	double total,balance,totalpaid;
	Inventory inv;
	
	MyTable tab;
	public void actionPerformed(ActionEvent e)
	{
			if(e.getSource()==butchk)
				{
					if(MyID==2)
						{
							new Payment_check(ID,st,2);
							
						}
					else {
							new Payment_check(ID,st,1);
						 }
						 
					load();
					inv.search();
				}
			if(e.getSource()==butcash)
				{
					if(MyID==2)
						{
							new Payment_cash(ID,st,2);
						}
					else {
							new Payment_cash(ID,st,1);
						 }
					 
					load();
					inv.search();	
				}
			if(e.getSource()==butdelete)
				{ try{
				        if(MyID==2)
				        	{
				        		st.execute("DELETE FROM paymentC WHERE ID='"+tab.getValue(tab.getTb().getSelectedRow(),0)+"'");
				        		 	
				        	}
						else{ 
								st.execute("DELETE FROM paymentS WHERE ID='"+tab.getValue(tab.getTb().getSelectedRow(),0)+"'");
								
							}
				        	
						load();
						inv.search();
						JOptionPane.showMessageDialog(null,"Payment Canceled!");
					  }catch(Exception ee){}
				}		
	}	
    public Payment(Statement st,String ID,int MyID,Inventory inv) 
    {
    	super("Payment Details", true, // resizable
					true, // closable
					true, // maximizable
					true);
    	this.inv=inv;
    	this.ID=ID;
    	this.st=st;
    	this.MyID=MyID;
    	
    	
   
    	
       	JPanel finalP=new JPanel();
    	form=new DecimalFormat("###,###.00");
    
    
    	butcash=new JButton("Cash Payment");
        butchk=new JButton("Check Payment");
        butdelete=new JButton("Cancel Payment");
        
        
    	
    	setLayout(new BorderLayout());	
    	JPanel p1c=new JPanel();
  
        JPanel p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(750,200);}};
        JPanel p2=new JPanel(){public Dimension getPreferredSize(){return new Dimension(750,40);}};
        p1.setLayout(new BorderLayout());
        
        p2.add(butcash);p2.add(butchk);p2.add(butdelete);
        butcash.addActionListener(this);
        butchk.addActionListener(this);
        butdelete.addActionListener(this);
        String arrcash[]={"ID","Pament Type","Payment Date","Check Date","Check no.","Amount","Receipt No."};
        tab=new MyTable();
        tab.setData(arr2,arrcash);

        tab.getTb().getColumnModel().getColumn(0).setMinWidth(0);
		tab.getTb().getColumnModel().getColumn(0).setMaxWidth(0);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		tab.getTb().getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		
        p1.add(new JScrollPane(tab.getTb()),"Center");
                       

        finalP.setLayout(new BorderLayout());
        finalP.add(p1,BorderLayout.CENTER);
        finalP.add(p2,BorderLayout.SOUTH);
       
        
    	load();
    			 Object[] message = new Object[1];          
                 message[0]=finalP;
                                         
             
                

                 String[] options = {"EXIT"};
           
		    		int result = JOptionPane.showOptionDialog(
		    		null,
		    		message,
		    		"Payment Detail",
		    		JOptionPane.DEFAULT_OPTION,
		    		JOptionPane.INFORMATION_MESSAGE,
		    		null,
		    		options,
		    		options[0]

						);
    }			
    public void load()
        	{
        	  String arr[]=new String[7];
        	  double tbal=0,ttal=0,tpaid=0;    
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
	            	   
            	    
				       				double temp;
				       				if(MyID==2)set=st.executeQuery("Select * from PaymentC where Trans_ID="+ID+" ORDER BY Payment_date");
				            		else set=st.executeQuery("Select * from PaymentS where Trans_ID="+ID+" ORDER BY Payment_date");
				            		while(set.next())
				            		{
				            			arr[0]=set.getString("ID");
				            		    arr[1]=set.getString("Payment_type");
				            			arr[2]=formatd.format(set.getDate("Payment_date"));
				            			try{
				            				 arr[3]=formatd.format(set.getDate("Check_date"));
				            			}catch(Exception ee){arr[3]="";}
				            			
				         			    arr[4]=set.getString("Check_no");
				         			    temp=set.getDouble("Amount");
				         			    arr[5]=form.format(temp);
				         			    arr[6]=set.getString("Rec_no");
				         			    totalpaid+=temp;
				            			tab.insert(arr);
            						}
            	    	
            	
            		
				            	
				            	//	tpaid+=totalpaid;
				            	
				            	 	
				            	// 	if(MyID==2)set=st.executeQuery("Select Price, Quantity, Customer, Discount from Sales_invoice where ID="+tab2.getValue(tab2.getTb().getSelectedRow(),0));
				            	 //	else set=st.executeQuery("Select Price, Quantity, Supplier, Discount from Sales_order where ID="+tab2.getValue(tab2.getTb().getSelectedRow(),0));
				            	 //	set.next();
				            	 //	double discount;
				            	 //	total=set.getDouble("Price")*set.getDouble("Quantity");
				            	 	
				            	 //	if(MyID==2)supID=set.getString("Customer");
				            	 //	else supID=set.getString("Supplier");
				            	 //	discount=total*set.getDouble("Discount");
				            	 	
				            	//	ttal+=(total-discount);
 	
				            		//tbal+=(total-discount)-totalpaid;
				            	 	
				            	 	//totalpaid=0;
            	 	
            	    
            	  //  arr[0]="";
            		//arr[1]="";
            		//arr[2]="";
            		//arr[3]="";
            		//arr[4]="";
            		//arr[5]="";
            		//arr[6]="";
            	 	//tab.insert(arr);
            	    	
            	  //  arr[0]="";
            		//arr[1]="";
            		//arr[2]="";
            		//arr[3]="";
            		//arr[4]="Amount Paid:";
            		//arr[5]=form.format(tpaid);
            	 	//tab.insert(arr);
            	 	
            	 
            	 	//arr[0]="";
            	 	//arr[1]="";
            		//arr[2]="";
            		//arr[3]="";
            		//arr[4]="Total Amount:";
            		//arr[5]=form.format(ttal);
            	 	//tab.insert(arr);
            	 	
            	 	//arr[0]="";
            	 	//arr[1]="";
            		//arr[2]="";
            		//arr[3]="";
            		//arr[4]="Balance:";
            		//arr[5]=form.format(tbal);
            	 	//tab.insert(arr);
            	 	//tpaid=0;	
            	 	
            	}catch(Exception ee){ee.printStackTrace();}
        	
    	
   			 }
    	
  
    
}