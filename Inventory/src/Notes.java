import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;


public class Notes extends JInternalFrame implements ActionListener{

	
  
	Statement st;
	String ID,supID; 
	ResultSet set;
	JButton butcash,butchk;
	
	int MyID;
	JTextArea note;
	String Index;
	public void actionPerformed(ActionEvent e)
	{
			if(e.getSource()==butchk)
				{
					try 
					{
						if(MyID==2) 
						{
							st.execute("UPDATE Sales_trans2 SET Remarks='"+note.getText()+"' WHERE ID='"+Index+"'");
						}else
						{
							st.execute("UPDATE Sales_trans SET Remarks='"+note.getText()+"' WHERE ID='"+Index+"'");
						}	
							
					}catch(Exception ee) {JOptionPane.showMessageDialog(null,"Error"+ee);}
					JOptionPane.showMessageDialog(null,"Saved!");	
					note.setEditable(false);
				}
			if(e.getSource()==butcash)
				{
					note.setEditable(true);
				}
					
	}	
    public Notes(Statement st,int MyID,String Index) 
    {
    	super("Remarks", true, // resizable
					true, // closable
					true, // maximizable
					true);
    	
    	this.st=st;
    	this.MyID=MyID;
    	this.Index=Index;
   
    	
       	JPanel finalP=new JPanel();

    	butcash=new JButton("Edit");
        butchk=new JButton("Save");
        note= new JTextArea(8,40);
        note.setEditable(false);
        
    	setLayout(new BorderLayout());	
    	JPanel p1c=new JPanel();
  
        JPanel p1=new JPanel(){public Dimension getPreferredSize(){return new Dimension(500,150);}};
        JPanel p2=new JPanel(){public Dimension getPreferredSize(){return new Dimension(500,40);}};
        
        p1.add(note);
 
        p2.add(butcash);p2.add(butchk);
        butcash.addActionListener(this);
        butchk.addActionListener(this);


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
		    		JOptionPane.QUESTION_MESSAGE,
		    		null,
		    		options,
		    		options[0]

						);
    }			
    public void load()
    {
    	try 
		{
			if(MyID==2) 
			{
				set=st.executeQuery("SELECT Remarks FROM Sales_trans2 WHERE ID='"+Index+"'");set.next();
				note.setText(set.getString("Remarks"));
			}else
			{
				set=st.executeQuery("SELECT Remarks FROM Sales_trans WHERE ID='"+Index+"'");set.next();
				note.setText(set.getString("Remarks"));
			}	
				
		}catch(Exception ee) {JOptionPane.showMessageDialog(null,"Error"+ee);}
				       
   	}
    	
  
    
}