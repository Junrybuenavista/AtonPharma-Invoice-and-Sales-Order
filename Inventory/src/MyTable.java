import javax.swing.*;import javax.swing.table.DefaultTableModel;
import java.awt.*;import java.util.*;
import java.awt.event.*;
import javax.swing.table.*;





 class MyTable extends JPanel 
        
    	{

    	 JTable tb;                    
    	 DefaultTableModel mod;        
    	
    
    		public MyTable()
    			{
                
		    		  mod=new DefaultTableModel();
			    	  tb=new JTable(mod)
	    	  				{  
				  				public boolean isCellEditable(int row, int column){  
				    				return false;  
				  					}
				  						  
		        			};
  						
    			
    			tb.setFont(new Font("Calibri",Font.BOLD,15));
              	tb.setRowHeight(25);
              	tb.setShowVerticalLines(true);
                tb.setAutoResizeMode( JTable.AUTO_RESIZE_NEXT_COLUMN);
                //add(JTable.createScrollPaneForTable(tb));


    			}
    	    
    			public DefaultTableModel getMod(){return mod;}
    				public void insert(String []a)
    					{
    						mod.insertRow(mod.getRowCount(), a);
    					}
    				public void delete(int a)
    					{
    						mod.removeRow(a);
    					}
    		public int getcount(){return mod.getRowCount();}
    		public String getValue(int r,int c){return (String)mod.getValueAt(r,c);}
    		public void setValue(int r,int c,String s){mod.setValueAt(s,r,c);}
    		 public void setData(String[][] ss,String[] s){mod.setDataVector(ss,s);}
    		//public Dimension getPreferredSize(){return new Dimension(710,200);}
    	   
           public void setme()
           	{
           		tb.setPreferredScrollableViewportSize(new Dimension(700,getcount()*20));
           	}
      
           	public int getHR(){return tb.getRowHeight();}
           	public JTable getTb(){return tb;}

    
   }