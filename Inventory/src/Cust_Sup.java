import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import java.util.StringTokenizer;

public class Cust_Sup extends JInternalFrame implements ActionListener{
    
    Connection conn;
    Statement st;
    ResultSet set;
    MyTable tab;
    String arr2[][]=null; 
    String arr[];	
    JButton searchbut;
    JTextField searchtx;
    JComboBox searchbox;
    DecimalFormat form,form2;
    JDesktopPane desk;
    MsAccessDatabaseConnectionInJava8 conn8;	
    public void actionPerformed(ActionEvent e)
    	{
    	
    	
    		if(e.getSource()==searchbut)
    			{
    			searchActivate(); 
    			}	
    	} 
    public void searchActivate() 
    {
    	if(searchbox.getSelectedIndex()==2)load();
		else load2(searchbox.getSelectedIndex());
    }			
    public Cust_Sup(JDesktopPane desk,MsAccessDatabaseConnectionInJava8 conn8) {
    		
    	
    	super("Cutomer and Supplier", true, // resizable
					true, // closable
					true, // maximizable
					true);
		this.desk=desk;
		
		set=conn8.getMySet();
    	st=conn8.getMyState();			
    	
    	
	    Container con=getContentPane();
	    con.setLayout(new BorderLayout());
	    
	    
	    
	    
	    	    
	    String arr[]={"ID","Type","Name","Address","Tin No.","License No."};                     
        tab=new MyTable();
        tab.setData(arr2,arr);
        
        tab.getTb().getColumnModel().getColumn(0).setMinWidth(0);
		tab.getTb().getColumnModel().getColumn(0).setMaxWidth(0);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		tab.getTb().getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		
		TableColumn column=tab.getTb().getColumnModel().getColumn(1);
        column.setPreferredWidth(40);
        column=tab.getTb().getColumnModel().getColumn(2);
        column.setPreferredWidth(300);
        column=tab.getTb().getColumnModel().getColumn(3);
        column.setPreferredWidth(300);
	    
	    JPanel pn=new JPanel(){public Dimension getPreferredSize(){return new Dimension(400,450);}};
	 
	    pn.setLayout(new BorderLayout());
	    pn.add(new JScrollPane(tab.getTb()),"Center"); 
	    
	    
	    
	    JPanel searchpanel=new JPanel();
	    searchbut=new JButton("Search");
	    searchbut.addActionListener(this);
	    searchtx=new JTextField(15);
	    String s[]={"by Customer","by Supplier","All"};
	    searchbox=new JComboBox(s);
	    searchbox.setSelectedIndex(2);
	    searchpanel.add(searchbut);searchpanel.add(searchtx);searchpanel.add(searchbox);	
	    
	    con.add(searchpanel,"North");
	    con.add(pn,"Center");
	    
	    tab.getTb().addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent evnt) {
		        if (evnt.getClickCount() == 2) 
		        	{
		        		if(tab.getValue(tab.getTb().getSelectedRow(),1).equalsIgnoreCase("Customer"))
		        		new EditSC(desk,st,1,tab.getValue(tab.getTb().getSelectedRow(),0),Cust_Sup.this);
	    				else new EditSC(desk,st,2,tab.getValue(tab.getTb().getSelectedRow(),0),Cust_Sup.this);
		        		
		        		if(searchbox.getSelectedIndex()==2)load();
	    				else load2(searchbox.getSelectedIndex());
		        		
		            }
		     	}
			});
	    
	    
	    load(); 
	    show();
	    desk.add(this); 
    	setSize(1100,550);
    	moveToFront();
    }
    
    public void load()
    {
    	 arr=new String[6];
               try
               	{
               		while(true)
               			{ 
               				tab.delete(0);
               			}
               			
               	}
               	catch(Exception ee){}
              // try
              // 	{
              // 		st.execute("Delete from inventory where Quantity=0");
              // 	}catch(Exception ee){ee.printStackTrace();}
               		
            		try
            		{   
            		if(searchbox.getSelectedIndex()==2)set=st.executeQuery("Select * FROM Customer where Customer_name like '%' order by Customer_name ");          	                      
            		else set=st.executeQuery("Select * FROM Customer where Customer_name like '"+searchtx.getText()+"%' order by Customer_name ");
            		while(set.next())
            		{  
            			arr[0]=set.getString("ID");
            			arr[1]="Customer";
            			arr[2]=set.getString("Customer_name");
            			arr[3]=set.getString("Address");
            			arr[4]=set.getString("Tin_no");
            			arr[5]=set.getString("License_no");
            			
            			
            			tab.insert(arr);
            		}
            		if(searchbox.getSelectedIndex()==2) set=st.executeQuery("Select * FROM Supplier where Supplier_name like '%' order by Supplier_name ");
            		else set=st.executeQuery("Select * FROM Supplier where Supplier_name like '"+searchtx.getText()+"%' order by Supplier_name ");
            		while(set.next())
            		{  
            			arr[0]=set.getString("ID");
            			arr[1]="Supplier";
            			arr[2]=set.getString("Supplier_name");
            			arr[3]=set.getString("Address");
            			arr[4]=set.getString("Tin_no");
            			arr[5]=set.getString("License_no");
            			
            			
            			tab.insert(arr);
            		}
            	 }catch(Exception ee){ee.printStackTrace();JOptionPane.showMessageDialog(this,"Please select transaction!"+ee);}
    }
    public void load2(int ins)
    {
    	 arr=new String[6];
    
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
            				
            						if(ins==0)
            						{
            					    	set=st.executeQuery("Select * FROM Customer where Customer_name like '"+searchtx.getText()+"%' order by Customer_name ");
            						}
            						if(ins==1)
            						{
            							set=st.executeQuery("Select * FROM Supplier where Supplier_name like '"+searchtx.getText()+"%' order by Supplier_name ");
            					    }
            						
            					
            						while(set.next())
            						{  
	            							arr[0]=set.getString("ID");
	            							if(ins==0)
	            							{  
	            								arr[1]="Customer";
	            								arr[2]=set.getString("Customer_name");
	            							}
					            			if(ins==1)
	            							{	
	            								arr[1]="Supplier";
	            								arr[2]=set.getString("Supplier_name");
	            							}
	            							arr[3]=set.getString("Address");
					            			arr[4]=set.getString("Tin_no");
					            			arr[5]=set.getString("License_no");
					            			
								    			
				            			tab.insert(arr);
            						}
            				
            			
            		
            	 }catch(Exception ee){ee.printStackTrace();JOptionPane.showMessageDialog(this,"Please select transaction!"+ee);}
    }
    
    public static void main(String args[]){}
}