import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import javax.swing.table.*;

public class Items extends JInternalFrame implements ActionListener{
    
    Connection conn;
    SimpleDateFormat formatd = new SimpleDateFormat("MMM/dd/yyyy");
    Statement st;
    ResultSet set;
    MyTable tab;
    String arr2[][]=null; 
    String arr[];	
    JButton b1,b2,b3,searchbut;
    JTextField searchtx;
    JComboBox searchbox;
    DecimalFormat form,form2,formID;
    JDesktopPane desk;
    MsAccessDatabaseConnectionInJava8 conn8;
	
    public void actionPerformed(ActionEvent e)
    	{
    	
    	
    		if(e.getSource()==searchbut)
    			{   
    				if(searchbox.getSelectedIndex()==2) load();
    				else load2(searchbox.getSelectedIndex());
    			}	
    	} 
    			
    public Items(JDesktopPane desk,MsAccessDatabaseConnectionInJava8 conn8) {
    		
    	
    	super("List of Items", true, // resizable
					true, // closable
					true, // maximizable
					true);
		this.desk=desk;
		
		set=conn8.getMySet();
    	st=conn8.getMyState();			
    	
    	
	    Container con=getContentPane();
	    con.setLayout(new BorderLayout());
	    form=new DecimalFormat(".00");
	    form2=new DecimalFormat("###,###.00");
	    formID=new DecimalFormat("I-0000000");
	    
	    b1=new JButton("Purchase Order");b1.addActionListener(this);
	    b2=new JButton("Delete Transaction");b2.addActionListener(this);
	    b3=new JButton("Payment Detail");b3.addActionListener(this);
	    	    
	    String arr[]={"ID","Brand name","Generic name","UM","Unit","Date of order","Date of expiry","Lot no.","P.O Quantity","Current Stock","Price"};                     
        tab=new MyTable();
        tab.setData(arr2,arr);
        
        
        //tab.getTb().getColumnModel().getColumn(0).setMinWidth(0);
		//tab.getTb().getColumnModel().getColumn(0).setMaxWidth(0);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		tab.getTb().getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
		tab.getTb().getColumnModel().getColumn(10).setCellRenderer(rightRenderer);
		
		TableColumn column=tab.getTb().getColumnModel().getColumn(0);
        column.setPreferredWidth(60);
        column=tab.getTb().getColumnModel().getColumn(1);
        column.setPreferredWidth(250);
        column=tab.getTb().getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        
	    
	    JPanel pn=new JPanel(){public Dimension getPreferredSize(){return new Dimension(400,450);}};
	 
	    pn.setLayout(new BorderLayout());
	    pn.add(new JScrollPane(tab.getTb()),"Center"); 
	    JPanel ps=new JPanel(){public Dimension getPreferredSize(){return new Dimension(400,50);}};
	    ps.add(b1);ps.add(b2);ps.add(b3);
	    
	    JPanel searchpanel=new JPanel();
	    searchbut=new JButton("Search");
	    searchbut.addActionListener(this);
	    searchtx=new JTextField(15);
	    String s[]={"by Brand Name","by Generic Name","by Lot no.","All"};
	    searchbox=new JComboBox(s);
	    searchbox.setSelectedIndex(3);
	    searchpanel.add(searchbut);searchpanel.add(searchtx);searchpanel.add(searchbox);	
	    
	    con.add(searchpanel,"North");
	    con.add(pn,"Center");
	    
	    
	    
	    load2(3);
	    
	    		
	    
	    show();
	    desk.add(this); 
    	setSize(1300,850);
    	moveToFront();
    }
   
    public void load(StringTokenizer stt)
    {
    	 arr=new String[11];
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
            	
                         while (stt.hasMoreElements())
                         	{
                         	                 
			            	    set=st.executeQuery("Select (SELECT Date_order FROM Sales_trans WHERE Sales_trans.ID = Sales_order.Trans_ID)as Date_orderA, ID, Brand_name, Generic_name, UM, Unit,(SELECT SUM(Quantity) FROM Sales_invoice where Order_ID=Sales_order.ID) as summ2,Date_expiry, Lot_no,Quantity,Price FROM Sales_order INNER JOIN Item_prof ON Sales_order.Item=Item_prof.ID AND Sales_order.Item='"+stt.nextElement()+"'");
			           
			            		int inttemp=0,inttot=0;
			            		int count=0; 
			            		while(set.next())
			            		{   
			            			
			            			
			            			arr[0]=formID.format(set.getInt("ID"));
			            			
			            			
			            			
			            			if(count!=0){
			            			
					            			arr[1]="";
					            			arr[2]="";
					            			arr[3]="";
					            			arr[4]="";
			            			}else{
					            			arr[1]=set.getString("Brand_name");
					            			arr[2]=set.getString("Generic_name");
					            			arr[3]=set.getString("UM");
					            			arr[4]=set.getString("Unit");
				            			}
			            			
			            			arr[5]=formatd.format(set.getDate("Date_orderA"));
			            			arr[6]=formatd.format(set.getDate("Date_expiry"));
			            			arr[7]=set.getString("Lot_no");
			            			arr[8]=set.getInt("Quantity")+"";
			            			inttemp=(Integer.parseInt(arr[8])-set.getInt("summ2"));
			            			inttot+=inttemp;
			            			arr[9]=inttemp+"";
			            			
			            			arr[10]=form.format(set.getDouble("Price"));
			            			
			            			if(inttemp!=0) 
			            				{	
			            					if(!arr[1].equals(""))count=1;
			            					tab.insert(arr);
			            				}
			            			
			            		}
			            		if(count!=0)
			            		{		
			            		
				            			arr[0]="";
				            			arr[1]="";
				            			arr[2]="";
				            			arr[3]="";
				            			arr[4]="";
				            			arr[5]="";
				            			arr[6]="";
				            			arr[7]="";
				            			arr[8]="Total:";
				            			arr[9]=inttot+"";
				            			arr[10]="";
				            			tab.insert(arr);
				            			
				            			arr[0]="";
				            			arr[1]="";
				            			arr[2]="";
				            			arr[3]="";
				            			arr[4]="";
				            			arr[5]="";
				            			arr[6]="";
				            			arr[7]="";
				            			arr[8]="";
				            			arr[9]="";
				            			tab.insert(arr);
			            				
			            		}
			            			
                         }
            	 }catch(Exception ee){ee.printStackTrace();JOptionPane.showMessageDialog(this,"Please select transaction!"+ee);}
    }
    public void load()
    {
    	 arr=new String[11];
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
            	
                        
                         	                 
			            	    set=st.executeQuery("Select (SELECT Date_order FROM Sales_trans WHERE Sales_trans.ID = Sales_order.Trans_ID)as Date_orderA, ID, Brand_name, Generic_name, UM, Unit,(SELECT SUM(Quantity) FROM Sales_invoice where Order_ID=Sales_order.ID) as summ2,Date_expiry, Lot_no,Quantity,Price FROM Sales_order INNER JOIN Item_prof ON Sales_order.Item=Item_prof.ID AND Sales_order.Lot_no='"+searchtx.getText()+"'");
			           
			            		int inttemp=0,inttot=0;
			            		int count=0; 
			            		while(set.next())
			            		{   
			            			
			            			
			            			arr[0]=formID.format(set.getInt("ID"));
			            			
			            			
			            			
			            			if(count!=0){
			            			
					            			arr[1]="";
					            			arr[2]="";
					            			arr[3]="";
					            			arr[4]="";
			            			}else{
					            			arr[1]=set.getString("Brand_name");
					            			arr[2]=set.getString("Generic_name");
					            			arr[3]=set.getString("UM");
					            			arr[4]=set.getString("Unit");
				            			}
			            			
			            			arr[5]=formatd.format(set.getDate("Date_orderA"));
			            			arr[6]=formatd.format(set.getDate("Date_expiry"));
			            			arr[7]=set.getString("Lot_no");
			            			arr[8]=set.getInt("Quantity")+"";
			            			inttemp=(Integer.parseInt(arr[8])-set.getInt("summ2"));
			            			inttot+=inttemp;
			            			arr[9]=inttemp+"";
			            			
			            			arr[10]=form.format(set.getDouble("Price"));
			            			
			            			if(inttemp!=0) 
			            				{	
			            					if(!arr[1].equals(""))count=1;
			            					tab.insert(arr);
			            				}
			            			
			            		}
			            		if(count!=0)
			            		{		
			            		
				            			arr[0]="";
				            			arr[1]="";
				            			arr[2]="";
				            			arr[3]="";
				            			arr[4]="";
				            			arr[5]="";
				            			arr[6]="";
				            			arr[7]="";
				            			arr[8]="Total:";
				            			arr[9]=inttot+"";
				            			arr[10]="";
				            			tab.insert(arr);
				            			
				            			arr[0]="";
				            			arr[1]="";
				            			arr[2]="";
				            			arr[3]="";
				            			arr[4]="";
				            			arr[5]="";
				            			arr[6]="";
				            			arr[7]="";
				            			arr[8]="";
				            			arr[9]="";
				            			tab.insert(arr);
			            				
			            		}
			            			
                         
            	 }catch(Exception ee){ee.printStackTrace();JOptionPane.showMessageDialog(this,"Please select transaction!"+ee);}
    }
   class RowColorRenderer extends DefaultTableCellRenderer
   {
        public Component getTableCellRendererComponent(
		JTable table, Object value, boolean isSelected, boolean hasFocus,int row,int col)
		{
		    table=tab.getTb();
		    
		 
		    if(row==2) {
		         setForeground(Color.red);
		    }else {
		         setForeground(null);
		    }
		 
		    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
		row, col);
 
		}
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
            				
            						if(ins==0)
            						{
            					    	set=st.executeQuery("Select ID from Item_prof where Brand_name LIKE'"+searchtx.getText()+"%' order by Brand_name");
            						}
            						if(ins==1)
            						{
            					    	set=st.executeQuery("Select ID from Item_prof where Generic_name LIKE '"+searchtx.getText()+"%' order by Brand_name ");
            						}
            						
            					
            						if(ins==3)
            						{
            					    	set=st.executeQuery("Select ID from Item_prof order by Brand_name");
            						}
            						
            						
				            		String str="";
				            		while(set.next())
				            			{
											str+=set.getString("ID")+" ";
											
				            			}	
				            		   
				                    StringTokenizer stt = new StringTokenizer(str);
            						load(stt);
            			
            		
            	 }catch(Exception ee){ee.printStackTrace();JOptionPane.showMessageDialog(this,"Please select transaction!"+ee);}
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