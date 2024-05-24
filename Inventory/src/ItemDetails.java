 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
 
public class ItemDetails implements KeyListener, ActionListener
        {
        	ImageIcon img2;
    		Image img3;
            Statement st;
            ResultSet set;
            String arr[];
            String arr2[][]=null; 
            JTextField t1,t2,t3,t6,t7,tsearch;
            SimpleDateFormat formatd = new SimpleDateFormat("MMM/dd/yyyy");  
           	int MyID;
            JPanel p3;
            Inventory inv;
            MyTable tab,tab3;
       
            JComboBox comboM,comboD,comboY,SItem;
            JComboBox comboM2,comboD2,comboY2;
            JButton butAdd,butDelete,bsearch,invoice;
            DecimalFormat form,formID;
            String img;
            
		    double cashin1=0;
		    double disc=0;
		    int quan=0;
		  	double price=0;
		  	
		  
            public void actionPerformed(ActionEvent e)
            {
            	if(e.getSource()==bsearch)
    			{
    				load();
    			}
        		if(e.getSource()==butAdd)
        		{  
        			if(MyID==2) {
        				if(isAlready()) {
        					JOptionPane.showMessageDialog(null,"Item already in invoice!");
        					return;
        				}
        			}
        				try
        				{
        					String datetemp="";
        					if(MyID==2) datetemp=t7.getText();
        					else datetemp=comboY.getSelectedItem()+"-"+(comboM.getSelectedIndex()+1)+"-"+comboD.getSelectedItem();
        					
        					String arrcashin[]={tab.getValue(tab.getTb().getSelectedRow(),0),t6.getText(),datetemp,t1.getText(),t2.getText(),t3.getText()};   					
        					//if(MyID==2)stcash+="INSERT INTO paymentC (Trans_ID,Customer_ID,Payment_date,Payment_type,Amount) VALUES ((SELECT max(id) FROM Sales_invoice),'"+tab2.getValue(tab2.getTb().getSelectedRow(),0)+"',#"+comboY.getSelectedItem()+"-"+(comboM.getSelectedIndex()+1)+"-"+comboD.getSelectedItem()+"#,'Cash',"+cashdouble+") ";
        					//else stcash+="INSERT INTO paymentS (Trans_ID,Supplier_ID,Payment_date,Payment_type,Amount) VALUES ((SELECT max(id) FROM Sales_order),'"+tab2.getValue(tab2.getTb().getSelectedRow(),0)+"',#"+comboY.getSelectedItem()+"-"+(comboM.getSelectedIndex()+1)+"-"+comboD.getSelectedItem()+"#,'Cash',"+cashdouble+") ";
        					t6.setText("");
        					t1.setText("");
        					t2.setText("");
        					t3.setText("");
        					if(MyID==2)t7.setText("");
        					tab3.insert(arrcashin);
        				}catch(Exception ee){ee.printStackTrace();JOptionPane.showMessageDialog(null,"Please select Item");}
        		}        			
        		if(e.getSource()==butDelete)
        				{
        					try
        					{
        						tab3.delete(tab3.getTb().getSelectedRow());
        					}catch(Exception ee){JOptionPane.showMessageDialog(null,"Error!"+ee);}
        					
        				}
        		if(e.getSource()==invoice)
				{ 

        			try {	
							new Invoice(st,tab3,MyID);
        				}catch(Exception ee) {}
        			if(MyID==2)load();
					deleteInvoice();
					t1.setText("");
					t2.setText("");
					t6.setText("");
					t7.setText("");
					t3.setText("");
					
				}
           	}
            public boolean isAlready() 
            {
            	boolean res=false;
            		for(int ii=0;ii<tab3.getTb().getRowCount();ii++) 
            		{
            			if(tab.getValue(tab.getTb().getSelectedRow(),0).equals(tab3.getValue(ii,0)))return true;
            		}
            	return res;	
            }
            public void deleteInvoice() 
            {	try{
	            	while(true)
	            	{
	            		tab3.delete(0);
	            	}
            	}catch(Exception ee) {}
            }
			public void keyReleased(KeyEvent e) {
		        	
		        	
		        	
		        	if(e.getSource()==t1)
						{  
							try{
							price=Double.parseDouble(t1.getText());
							}catch(Exception ee){price=0;}
						}
					if(e.getSource()==t2)
						{
							try{						
								quan=Integer.parseInt(t2.getText());
									if(MyID==2){
										
										try{
										
											if(Integer.parseInt(tab.getValue(tab.getTb().getSelectedRow(),4))<quan)
							    			{
								    			JOptionPane.showMessageDialog(null,"Number entered is more than to the current stock!");
								    			t2.setText("");
								    			t3.setText("");
								    			
								    			quan=0;
								    			return; 
							    			}
										}catch(Exception eee){t2.setText("");
								    			t3.setText("");
								    			JOptionPane.showMessageDialog(null,"Please select Item!");return;}
									}	
							   }catch(Exception ee){quan=0;}
						}
					
						
	
					double totalam=price*quan;
				    double disc2=totalam*(disc);

					t3.setText(form.format(totalam-disc2)+"");
					
		      }
		      public void keyTyped(KeyEvent e) {
		      }
		
		      public void keyPressed(KeyEvent e) {
		      }  
					  
            public void load()
            {  arr=new String[7];
              
              try
               	{
               		while(true)
               			{ 
               				tab.delete(0);
               			}
               			
               	}
               	catch(Exception ee){}
              	String searchtx = tsearch.getText();
              	if(SItem.getSelectedIndex()==0)searchtx="";
              	
            	try
            	{   
            	    	set=st.executeQuery("Select ID from Item_prof where Brand_name LIKE '"+searchtx+"%'order by Brand_name");
            	    	String str="";
				            		while(set.next())
				            			{
											str+=set.getString("ID")+" ";
											
				            			}	
				        int count=0;int inttemp=0,inttot=0;   		   
				        StringTokenizer stt = new StringTokenizer(str);
				        
            	    	if(MyID==2)
            	    	{  
            	    		while (stt.hasMoreElements())
                         	{   count=0; inttemp=0; inttot=0; 
			            		
                         	set=st.executeQuery("Select (SELECT Date_order FROM Sales_trans WHERE Sales_trans.ID = Sales_order.Trans_ID)as Date_orderA, ID, Brand_name, Generic_name, UM, Unit,(SELECT SUM(Quantity) FROM Sales_invoice where Order_ID=Sales_order.ID) as summ2,Date_expiry, Lot_no,Quantity,Price FROM Sales_order INNER JOIN Item_prof ON Sales_order.Item=Item_prof.ID AND Sales_order.Item='"+stt.nextElement()+"'");
     			           
                         	while(set.next())
			            		{   
			            			if(count!=0){
			            		    
			            			arr[1]="";
			            			arr[2]="";
			            			arr[3]="";		
			            			}else
			            				{
			            					
					            			arr[1]=set.getString("Brand_name");
					            			arr[2]=set.getString("Generic_name");
					            			arr[3]=set.getString("Unit");
					            			
			            				}
			            			arr[0]=formID.format(set.getInt("Sales_order.ID"));
			            			inttemp=(set.getInt("Quantity")-set.getInt("summ2"));
			            			inttot+=inttemp;
			            			arr[4]=inttemp+"";
			            			
			            			
			            			arr[5]=set.getString("Lot_no");
			            			arr[6]=set.getDate("Date_expiry").toString();
			            			if(inttemp!=0) 
		            				{	
		            					if(!arr[1].equals(""))count=1;
		            					tab.insert(arr);
		            				}
			            		}
			            		
                         	}
            	    	}
            	    	else
            	    	{	
        						set=st.executeQuery("Select * from Item_prof where Brand_name LIKE '"+searchtx+"%' order by Brand_name");
				           		while(set.next())
			            		{	int IDtemp=set.getInt("ID");
			            		    arr[0]=formID.format(IDtemp);
			            			arr[1]=set.getString("Brand_name");
			            			arr[2]=set.getString("Generic_name");
			            			arr[3]=set.getString("UM");
			            			arr[4]=set.getString("Unit");
			            			arr[5]=IDtemp+"";
									tab.insert(arr);
			            		}
	
                         
            	    	}	
                         	
            	
            	 
            	}catch(Exception ee){ee.printStackTrace();}
            	
              
            }
          
           public void loadBrand(String ins)
           	{
           		 try
               	{
               		while(true)
               			{ 
               				tab.delete(0);
               			}
               			
               	}
               	
               	catch(Exception ee){}
           		
           		try{
           				
           				set=st.executeQuery("Select ID from Item_prof where Brand_name='"+ins+"' order by Brand_name");
           				
            	    	String str="";
				            		while(set.next())
				            			{
											str+=set.getString("ID")+" ";
											
				            			}	
				        int count=0;int inttemp=0,inttot=0;   		   
				        StringTokenizer stt = new StringTokenizer(str);
				        
            	    	if(MyID==2)
            	    	{  
            	    		while (stt.hasMoreElements())
                         	{   count=0; inttemp=0; inttot=0; 
			            		set=st.executeQuery("Select ID, Brand_name, Generic_name, UM, Unit,(SELECT SUM(Quantity) FROM Sales_invoice where Order_ID=Sales_order.ID) as summ2,Date_expiry, Date_order,Lot_no,Quantity FROM Sales_order INNER JOIN Item_prof ON Sales_order.Item=Item_prof.ID AND Sales_order.Item='"+stt.nextElement()+"'");
			           			while(set.next())
			            		{   
			            			if(count!=0){
			            		    
			            			arr[1]="";
			            			arr[2]="";
			            			arr[3]="";		
			            			}else
			            				{
			            					
					            			arr[1]=set.getString("Brand_name");
					            			arr[2]=set.getString("Generic_name");
					            			arr[3]=set.getString("Unit");
					            			
			            				}
			            			arr[0]=formID.format(set.getInt("Sales_order.ID"));
			            			inttemp=(set.getInt("Quantity")-set.getInt("summ2"));
			            			inttot+=inttemp;
			            			arr[4]=inttemp+"";
			            			
			            			
			            			arr[5]=set.getString("Lot_no");
			            			arr[6]=set.getDate("Date_expiry").toString();
			            			if(inttemp!=0)tab.insert(arr);
			            			count=1;
			            		}
			            		
                         	}
            	    	}
            	    	else
            	    	{	
        						set=st.executeQuery("Select * from Item_prof where Brand_name='"+ins+"' order by Brand_name");
				           		while(set.next())
			            		{   int IDtemp=set.getInt("ID");
			            		    arr[0]=formID.format(IDtemp);
			            			arr[1]=set.getString("Brand_name");
			            			arr[2]=set.getString("Generic_name");
			            			arr[3]=set.getString("UM");
			            			arr[4]=set.getString("Unit");
			            			arr[5]=IDtemp+"";
									tab.insert(arr);
			            		}
	
                         
            	    	}
           			
           		}catch(Exception ee){}
           	}
           	
        public void loadLot(String ins)
           	{
           		 try
               	{
               		while(true)
               			{ 
               				tab.delete(0);
               			}
               			
               	}
               	
               	catch(Exception ee){}
           		
           		try{
           				
           			
				        
            	    	if(MyID==2)
            	    	{  
            	    	
                         	    set=st.executeQuery("Select ID from Item_prof order by Brand_name");
            	    			String str="";
				            		while(set.next())
				            			{
											str+=set.getString("ID")+" ";
											
				            			}	
				        		int count=0;int inttemp=0,inttot=0;   		   
				        		StringTokenizer stt = new StringTokenizer(str);

			            		while (stt.hasMoreElements())
                         		{   count=0; inttemp=0; inttot=0; 
				            		set=st.executeQuery("Select ID, Brand_name, Generic_name, UM, Unit,(SELECT SUM(Quantity) FROM Sales_invoice where Order_ID=Sales_order.ID) as summ2,Date_expiry, Date_order,Lot_no,Quantity FROM Sales_order INNER JOIN Item_prof ON Sales_order.Item=Item_prof.ID AND Sales_order.Item='"+stt.nextElement()+"' AND Sales_order.Lot_no='"+tsearch.getText()+"'");
				           			while(set.next())
				            		{   
				            			if(count!=0){
				            		    
				            			arr[1]="";
				            			arr[2]="";
				            			arr[3]="";		
				            			}else
				            				{
				            					
						            			arr[1]=set.getString("Brand_name");
						            			arr[2]=set.getString("Generic_name");
						            			arr[3]=set.getString("Unit");
						            			
				            				}
				            			arr[0]=formID.format(set.getInt("Sales_order.ID"));	
				            			arr[4]=formatd.format(set.getDate("Date_expiry"));
				            			arr[5]=set.getString("Lot_no");
				            			
				            			inttemp=(set.getInt("Quantity")-set.getInt("summ2"));
				            			inttot+=inttemp;
				            			
				            			arr[6]=inttemp+"";
				            			if(inttemp!=0)tab.insert(arr);
				            			count=1;
			            		}
			            		
                         	}
			            		
                         	
            	    	}
            	    	
           			
           		}catch(Exception ee){}
           	}
           	
          
        	
        	public ItemDetails(final Statement st,Inventory inv,int MyID)
        	{
        		 this.st=st;
        		 this.inv=inv;
        		 this.MyID=MyID;
        		 form=new DecimalFormat("###,###.00");
        		 formID=new DecimalFormat("I-0000000");
        		 String arr[]={"ID","Brand Name","Generic Name","UM","Unit",""};
        		 
        		 setCombo();
		 
        		 String arrcash[]={"Item-ID","Lot no","Exp. Date","Item Price","Quantity","Total Amount"};                     
        		 tab=new MyTable();
        		 tab3=new MyTable();
        		 
        		 tab.setData(arr2,arr);
        		 tab3.setData(arr2,arrcash);
        		 
        		 
        		 
        		 if(MyID==2){
        		 				String arr7[]={"ID","Brand Name","Generic Name","Unit","Quantity","",""};
        		 				tab.setData(arr2,arr7);
        		 			}
        		 
 
				 if(MyID==2) {
				 tab.getTb().getColumnModel().getColumn(5).setMinWidth(0);
				 tab.getTb().getColumnModel().getColumn(5).setMaxWidth(0);
				 tab.getTb().getColumnModel().getColumn(6).setMinWidth(0);
				 tab.getTb().getColumnModel().getColumn(6).setMaxWidth(0);
				 
				 }else 
				 {
					 tab.getTb().getColumnModel().getColumn(5).setMinWidth(0);
					 tab.getTb().getColumnModel().getColumn(5).setMaxWidth(0);
				 }    
				 
				 TableColumn column=tab.getTb().getColumnModel().getColumn(0);
        	     column.setPreferredWidth(60);
        	     column=tab.getTb().getColumnModel().getColumn(1);
        	     column.setPreferredWidth(200);
        	     column=tab.getTb().getColumnModel().getColumn(2);
        	     column.setPreferredWidth(200);
        	     column=tab.getTb().getColumnModel().getColumn(3);
        	     column.setPreferredWidth(75);
        	     column=tab.getTb().getColumnModel().getColumn(4);
        	     column.setPreferredWidth(75);
				 
				 
				 DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
				 rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
				 
				 tab3.getTb().getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
				 tab3.getTb().getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
				 tab3.getTb().getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
				 
        		 if(MyID==2){
        		 tab.getTb().getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        		 }

        		 
        		 JPanel p1c=new JPanel();
        		 
        		 p1c.add(comboM);p1c.add(comboD);p1c.add(comboY);
        		 
        		 JPanel p1c2=new JPanel();
        		 p1c2.add(comboM2);p1c2.add(comboD2);p1c2.add(comboY2);
        		 JPanel p1c3=new JPanel();
        		

        		 
        		 JPanel p1;
        		
        		 p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(1250,510);}};
        		 
        		 p1.setLayout(new BorderLayout());
        		 JPanel p2=new JPanel();
        		 p2.setLayout(new BorderLayout());
        		 p3=new JPanel(){public Dimension getPreferredSize(){return new Dimension(180,160);}};
        		 
        		 JPanel p4=new JPanel(){public Dimension getPreferredSize(){return new Dimension(120,120);}};
        		 p3.setLayout(new BorderLayout());p3.setBackground(Color.BLACK);
        		 
        		 t1=new JTextField(5);
        		 t2=new JTextField(5);
        		 t3=new JTextField(5);  
        		 t6=new JTextField(5);
        		 t7=new JTextField(5); 
        		 tsearch=new JTextField(15);
        		
        		 
        		 
        		 p4.add(p1c3); 
               
                 
      
        	
        		 JPanel cashpan=new JPanel();
        		 cashpan.setLayout(new BorderLayout());
        		 JPanel cashpan2;
        		 JPanel gridcash;
        		
        		 JPanel logoP=new JPanel();
        		 cashpan2=new JPanel(){public Dimension getPreferredSize(){return new Dimension(550,150);}};//335
	        	 gridcash=new JPanel(){public Dimension getPreferredSize(){return new Dimension(380,150 );}};
        		 JPanel noteP=new JPanel(){public Dimension getPreferredSize(){return new Dimension(550,200);}};
        		 noteP.setLayout(new BorderLayout());
        		 GridLayout grid;		 
        		 grid=new GridLayout(5,2);
        		 gridcash.setLayout(grid);
        		 cashpan2.add(gridcash);
        		 
        		 
        		
        		 
        		 JPanel cashp=new JPanel();
        		 butAdd=new JButton("Add Item");
        		 butDelete=new JButton("Delete Item");
        		 invoice=new JButton("Invoice Item");
        		 bsearch=new JButton("Search");
        		 cashp.add(butAdd);cashp.add(butDelete);cashp.add(invoice);
        		 butAdd.addActionListener(this);
        		 butDelete.addActionListener(this);
        		 bsearch.addActionListener(this);
        		 invoice.addActionListener(this);
        		 
        		    		 
        		 gridcash.add(new JLabel("Item Price:"));
        		 gridcash.add(t1);	 
        		 gridcash.add(new JLabel("Quantity:"));
        		 gridcash.add(t2);
	 
        		 t1.addKeyListener(this);
        		
        		 t2.addKeyListener(this);
        		 
        		 gridcash.add(new JLabel("Total Amount:"));t3.setEditable(false);
        		 gridcash.add(t3);
        		 
        		 gridcash.add(new JLabel("Lot no.:"));
        		 gridcash.add(t6);
        		 
        		 
        		 if(MyID==2){
        			 			gridcash.add(new JLabel("Expiry Date:"));
        			 			gridcash.add(t7);
        		 				t7.setEditable(false);
        		 				t6.setEditable(false);	
        		 			}else {

			        		 gridcash.add(new JLabel("Expiry Date:"));
			        		 gridcash.add(p1c);}
			        		 
        	
        		 
        		 p2.add(new JScrollPane(tab.getTb()),"Center");
        		 noteP.add(cashp,"North");
        		 noteP.add(new JScrollPane(tab3.getTb()),"Center");
        		
        		 
        		   try {
        	         
			             img2=new  ImageIcon("image\\AtonLogo.gif");
			            
        				} catch (Exception ex) {}
        		  
        		  
        		 
        		
        		  
        		 cashpan.add(new JLabel(img2),"North"); 		  
        		 cashpan.add(cashpan2,"Center");
        		 cashpan.add(noteP,"South");
        		 
        		 JPanel Pn=new JPanel(){public Dimension getPreferredSize(){return new Dimension(380,50);}};
        		 JPanel Pninsert=new JPanel(){public Dimension getPreferredSize(){return new Dimension(380,50);}};
        		 Pn.add(bsearch);Pn.add(tsearch);Pn.add(SItem);Pn.add(Pninsert);
        		 p1.add(Pn,BorderLayout.NORTH);
        		 p1.add(cashpan,BorderLayout.EAST);
        		 p1.add(p2,BorderLayout.CENTER);
        		
        
        		 
        		 Object[] message = new Object[1];          
                 message[0]=p1;
                                         
             
                

                 String[] options = {"EXIT"};
                 String title;
                 if(MyID==2)title="Sales Invoice"; 
                 else title="Sales Order";
                 
                 if(MyID==1) 
                 {
                	 tab.getTb().addMouseListener(new MouseAdapter(){
             		    public void mouseClicked(MouseEvent evnt) {
             		        if (evnt.getClickCount() == 2) 
             		        	{
             		        	
             		        	  EditItem IT=new EditItem(st,tab.getValue(tab.getTb().getSelectedRow(),5));
             		        	 
             		        	 load();
	
             		            }
             		     	}
             			});
                 }
                 
                 load();
                
                 tab.getTb().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        		 
        		 if(MyID==2){
        		 	
        		 		tab.getTb().addMouseListener(new MouseAdapter(){
						    public void mouseClicked(MouseEvent evnt) {
						        if (evnt.getClickCount() == 1) {
						        	t7.setText(tab.getValue(tab.getTb().getSelectedRow(),6));
									t6.setText(tab.getValue(tab.getTb().getSelectedRow(),5));
									t1.setText("");
									t2.setText("");
						            }
						     }
						});
						
						
						
        		 	}
		    		int result = JOptionPane.showOptionDialog(
		    		inv,
		    		message,
		    		title,
		    		JOptionPane.DEFAULT_OPTION,
		    		JOptionPane.INFORMATION_MESSAGE,
		    		null,
		    		options,
		    		options[0]

						);
					 
               
           }
           
            public void setCombo()
         	{
         		String s1[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
         	    String s2[]={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
         	    String s3[]={"2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024",};
         	   
         	   String s4[]={"All","by Brand Name"};
         	   SItem=new JComboBox(s4);
         	    	
         		comboM=new JComboBox(s1);comboD=new JComboBox(s2);comboY=new JComboBox(s3);
         		comboM2=new JComboBox(s1);comboD2=new JComboBox(s2);comboY2=new JComboBox(s3);
         	
         	}
         	public int getyears(int ins)
         		{   
         			int ret=-1;
         			
         			if(ins==114)ret=0;
         			if(ins==115)ret=1;
         			if(ins==116)ret=2;
         			if(ins==117)ret=3;
         			if(ins==118)ret=4;
         			if(ins==119)ret=5;
         			if(ins==120)ret=6;
         			return ret;
         		}  
        	public String getMonth(int i)
    		{
    			String ret="";
    			if(i==1)ret="January";
    			if(i==2)ret="February";
    			if(i==3)ret="March";
    			if(i==4)ret="April";
    			if(i==5)ret="May";
    			if(i==6)ret="June";
    			if(i==7)ret="July";
    			if(i==8)ret="August";
    			if(i==9)ret="September";
    			if(i==10)ret="October";
    			if(i==11)ret="November";
    			if(i==12)ret="December";
    			return ret;
   		    }
   		   	
                
        }