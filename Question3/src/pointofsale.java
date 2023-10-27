
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
public class pointofsale extends JFrame implements ActionListener {
   JPanel panel;
   JLabel user_label, password_label, message;
   JTextField userName_text;
   JPasswordField password_text;
   JButton submit, cancel;
   pointofsale() {
      // UserName Label
      user_label = new JLabel();
      user_label.setText("User Name :");
      user_label.setForeground(Color.DARK_GRAY);
      userName_text = new JTextField();
      userName_text.setPreferredSize(new Dimension(200, 30));
      userName_text.setBackground(Color.black);
      userName_text.setForeground(Color.red);
      // Password Label
      password_label = new JLabel();
      password_label.setText("Password :");
      password_label.setForeground(Color.DARK_GRAY);
      password_text = new JPasswordField();
      password_text.setPreferredSize(new Dimension(200, 30));
      password_text.setBackground(Color.black);
      password_text.setForeground(Color.red);
      
      // Submit
      submit = new JButton("SUBMIT");
      submit.setBackground(Color.red);
      submit.setForeground(Color.DARK_GRAY);
      panel = new JPanel(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL; // check
      gbc.insets = new Insets(10, 20, 30, 40);
      
      gbc.gridx = 0;
      gbc.gridy = 0;
      panel.add(user_label, gbc);
      
      gbc.gridx = 1;
      gbc.gridy = 0;
      panel.add(userName_text, gbc);
      
      gbc.gridx = 0;
      gbc.gridy = 1;
      panel.add(password_label, gbc);
      
      gbc.gridx = 1;
      gbc.gridy = 1;
      panel.add(password_text, gbc);
      
      
      gbc.gridx = 0;
      gbc.gridy = 2;
      panel.add(submit, gbc);
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // Adding the listeners to components..
      submit.addActionListener(this);
      add(panel, BorderLayout.CENTER);
      panel.setBackground(Color.MAGENTA);
      setTitle("Please Login Here !");
      setSize(500,500);
      setVisible(true);
   }
  
   
   
 
   public static void main(String[] args) {
	      new pointofsale();
	     
	      
	   }
	   
	   public void actionPerformed(ActionEvent ae) {
	      String userName = userName_text.getText();
	      String password = String.valueOf(password_text.getPassword());
	    
	      //Connection to database
	      try {
	   			Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","root");
		   		
				PreparedStatement stmt = con.prepareStatement("select username, password from users where binary username = ? and binary password = ?");
				stmt.setString(1, userName);
				stmt.setString(2, password);
				ResultSet rs = stmt.executeQuery();
				
				
				
				if(rs.next()) {
					String st = "LOGGED IN!";
					JOptionPane.showMessageDialog(null, st);
					this.dispose();
					new sales();
				} else {
					String st = "LOGGIN FAILED!!!";
					JOptionPane.showMessageDialog(null, st);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		   		System.out.println("Not Connected");
			}
	   }
	}

