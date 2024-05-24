import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
 
 
public class login 
        {
        	JTextField txt1;
        	JPasswordField pass;
        	JDesktopPane fr;
        	Statement st;
        	ResultSet set;
        	String Type="";
        	public String getUser(){return txt1.getText();}
        	public login(JDesktopPane f,Statement st)
        	{
        		 
        		 fr=f;
        		 this.st=st;
        		 JPanel p1=new JPanel();
        		 JPanel p2=new JPanel();       		       
        		 Object[] message = new Object[3];
                 txt1= new JTextField(15);
                 pass=new JPasswordField(15);          
                 
                txt1.setForeground(Color.BLACK);
                
                p1.add(new JLabel("Username:"));p1.add(txt1);
                p2.add(new JLabel("Password:"));p2.add(pass);
                message[0]=p1;               
                message[1] =p2;
                

               String[] options = {"Login","Close Program"};
            while(true)
              {     
		    		int result = JOptionPane.showOptionDialog(
		    		fr,
		    		message,
		    		"LOG IN",
		    		JOptionPane.DEFAULT_OPTION,
		    		JOptionPane.INFORMATION_MESSAGE,
		    		null,
		    		options,
		    		options[0]

						);
						
					//JOptionPane.showMessageDialog(null ,"ff");
					
					if(result==1)
					{
						JOptionPane.showMessageDialog(fr,"Closing Program !");
						System.exit(0);
					}
					if(result==JOptionPane.CLOSED_OPTION )
					{
						int ress=JOptionPane.showConfirmDialog(fr,"Close Program ?");
						if(ress==JOptionPane.YES_OPTION )System.exit(0);
					}	
              		if(result==0)
               		{
           	 	  		try
           	 	  		{
           	 	  		    set=st.executeQuery("select count(*) as me from testTable where Username='"+txt1.getText()+"'");
           	 	  		    set.next();
           	 	  		    if(Integer.parseInt(set.getString("me"))==0)JOptionPane.showMessageDialog(fr,"User Not Found !");
           	 	  		    else 
           	 	  		    {
           	 	  		    	set=st.executeQuery("select pass from testTable where Username='"+txt1.getText()+"'");
           	 	  		        set.next();
           	 	  		        if(!set.getString("pass").equals(pass.getText()))JOptionPane.showMessageDialog(fr,"Incorrect Password Please Try Again !");
           	 	  		        else {  
           	 	  		        	    break;
           	 	  		        	 }
           	 	  		    }
           	 	  		}
           	 	  		catch(Exception ee){}
               		}
               }
           }
           public String getType()
           	{
           		return Type;
           	}
        
        }