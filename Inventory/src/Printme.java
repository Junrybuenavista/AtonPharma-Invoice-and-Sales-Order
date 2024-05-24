import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.awt.FontMetrics;
import java.sql.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
 
public class Printme implements Printable {

  String arr[];
  int type;
  DecimalFormat form,form2;
  Statement st;
  ResultSet set;
  MyTable tab;
  SimpleDateFormat formatd = new SimpleDateFormat("MMM/dd/yyyy");
  
  
  public Printme(int type,Statement st,MyTable tab) {
    this.type=type;
    this.st=st;
    this.tab=tab;
    form=new DecimalFormat("###,###");
    form2=new DecimalFormat("###,###.00");
    
    PrinterJob printJob = PrinterJob.getPrinterJob();
   

    printJob.setPrintable(this);
  
    if (printJob.printDialog()) {
      try {
        printJob.print();
      } catch (Exception PrintException) {
        PrintException.printStackTrace();
      }
    }
 
  }
 
  public int print(Graphics g, PageFormat pageFormat, int page) {
 
    int i=15;
    Graphics2D g2d;
    
 
    
    if (page == 0) { 
      g2d = (Graphics2D) g;
      g2d.setColor(Color.black);
      g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
     
     
	 FontMetrics fontMetrics = g2d.getFontMetrics();
	 String s = "Whatever";
	 int rightEdge=550;
	 int rightEdge2=580;
	 //g2d.drawString(s, rightEdge - fontMetrics.stringWidth(s), 100);
	 
	 
	      
	      int base=170;
	      int base2=268;
	      int Rows[]=tab.getTb().getSelectedRows();
		  int count=0;
		  double grandtotal=0;
		  double tempd=0;
		  arr=new String[14];
	      
	      	while(count<Rows.length)
	      	{
	      		try
	      		{   
	      			set=st.executeQuery("Select (select UM from Item_prof where ID=sales_order.item)as tUM, (SELECT Address FROM Customer WHERE ID=Sales_invoice.Customer) AS Temp2 ,(select Customer_name from Customer where ID=sales_invoice.Customer)as tcust,(select Brand_name from Item_prof where ID=sales_order.item)as tbrand,(select Generic_name from Item_prof where ID=sales_order.item)as tGname,(select Unit from Item_prof where ID=sales_order.item)as tunit, Sales_invoice.ID, Sales_invoice.Customer, Sales_invoice.Date_order, Sales_invoice.Price, Sales_invoice.Quantity, Sales_invoice.Discount, Sales_order.Date_expiry, Sales_order.Date_order, Sales_Order.Lot_no, Sales_Order.Quantity FROM Sales_order INNER JOIN Sales_invoice ON Sales_order.ID=Sales_invoice.Order_ID and Sales_invoice.ID='"+tab.getValue(Rows[count],0)+"'");
	      			set.next();
	      							arr[0]=ifnull(set.getString("Temp2"));           			
			            			arr[1]=ifnull(set.getString("tbrand"));
			            			arr[2]=ifnull(set.getString("tGname"));
			            			arr[3]=ifnull(set.getString("tunit"));
			            			arr[4]=ifnull(set.getString("tcust"));
			            			arr[5]=ifnull(formatd.format(set.getDate("Sales_order.Date_expiry")));
			            			arr[6]=ifnull(formatd.format(set.getDate("Sales_invoice.Date_order")));
			            			arr[7]=ifnull(set.getString("Sales_order.Lot_no"));
			            			arr[8]=ifnull(set.getString("Sales_invoice.Quantity"));
			            			double tempd2=set.getDouble("Sales_invoice.Price");
			            			arr[9]=ifnull(form2.format(tempd2));
			            			tempd=set.getDouble("Sales_invoice.Discount");
			            			arr[10]=ifnull(form.format((100*tempd))+"%");
			            			double tempd3=Integer.parseInt(arr[8])*tempd2;
			            			arr[11]=form2.format(tempd3);
			            			double tempd4=tempd*tempd3;
			            			arr[12]=form2.format(tempd4);
			            			arr[13]=ifnull(set.getString("tUM"));
			            			grandtotal+=tempd3;
			            			
			            		
	      			
	      		}catch(Exception ee){ee.printStackTrace();}
	      		
	      			
	      			if(type==1)
	      				{
	      				
				      		if(count==0){
				      				
							     g2d.drawString(arr[6],490,104);//orderdate
							     g2d.drawString(arr[0],275,134);//address
							     g2d.drawString(arr[4],133,123);//cust
				      		}
						     
						     
						     g2d.drawString(arr[8],75,base);//quantity
						     g2d.drawString(arr[3],123,base);//unit
						     g2d.drawString(arr[1]+"/"+arr[13],193,base);//brand
						     g2d.drawString(arr[9]+"/"+arr[3],382,base);//price/unit
						     g2d.drawString(arr[11],rightEdge - fontMetrics.stringWidth(arr[11]),base);//total price
						  	 
						  	 base+=15;
						 	 g2d.drawString(arr[2],193,base);//Gname
						 	 
						 	 base+=15;
						 	 g2d.drawString("Lot no.:"+arr[7],193,base);//lot no
						 	 g2d.drawString("Exp. Date: "+arr[5],300,base);//expiry 
						 	 base+=15;
						 	
						 	 
	      				}
	      			if(type==2)
	      				{
	      				    
				      		if(count==0){
				      				
							     g2d.drawString(arr[6],444,163);//orderdate
							     g2d.drawString(arr[0],79,228);//address
							     g2d.drawString(arr[4],107,176);//cust
				      		}
						     
						     
						     g2d.drawString(arr[8],25,base2);//quantity
						     g2d.drawString(arr[3],59,base2);//unit
						     g2d.drawString(arr[1]+"/"+arr[13],202,base2);//brand
						     g2d.drawString(arr[9]+"/"+arr[3],430,base2);//price/unit
						     g2d.drawString(arr[11],rightEdge2 - fontMetrics.stringWidth(arr[11]),base2);//total price
						  	 
						  	 base2+=18;
						 	 g2d.drawString(arr[2],202,base2);//Gname
						 	 
						 	 base2+=18;
						 	 g2d.drawString("Lot no.:"+arr[7],115,base2);//lot no
						 	 g2d.drawString("Exp. Date: "+arr[5],280,base2);//expiry
						 	 base2+=18;
						 	 
						 	
	      				}
	      				count++;
	      	
 	 
	 }
	  if(type==1) 
	  {
	     		 
	    	 	 
	     	g2d.drawString(form2.format(grandtotal),rightEdge - fontMetrics.stringWidth(form2.format(grandtotal)),281);//total price with discount
	    	g2d.drawString("Less "+form.format(tempd*100)+"%",420,296);//less%
	    	double temdisc=grandtotal*tempd;
	    	double tdisc=grandtotal-tempd;
	    	g2d.drawString(form2.format(temdisc),rightEdge - fontMetrics.stringWidth(form2.format(temdisc)),296);//descounted price
	    	g2d.drawString(form2.format(tdisc),rightEdge - fontMetrics.stringWidth(form2.format(tdisc)),311);//gtotal
	    	 	
	  }	 
	      	
 	 if(type==2) 
 	 {
 		 
	 	 
 		 g2d.drawString(form2.format(grandtotal),rightEdge2 - fontMetrics.stringWidth(form2.format(grandtotal)),600);//total price with discount
	 	 g2d.drawString("Less "+form.format(tempd*100)+"%",449,618);//less%
	 	 double temdisc=grandtotal*tempd;
	 	 double tdisc=grandtotal-tempd;
	 	 g2d.drawString(form2.format(temdisc),rightEdge2 - fontMetrics.stringWidth(form2.format(temdisc)),618);//descounted price
	 	 g2d.drawString(form2.format(tdisc),rightEdge2 - fontMetrics.stringWidth(form2.format(tdisc)),636);//gtotal
	 	
 	 }
  
      return (PAGE_EXISTS);
    } else
      return (NO_SUCH_PAGE);
  }
  public String ifnull(String s)
    {
    	if(s==null)return "";
    	return s;
    }
 
} 