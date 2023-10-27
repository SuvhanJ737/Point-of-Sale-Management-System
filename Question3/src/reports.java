import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class reports implements ActionListener {
	JFrame frame = new JFrame("Stock Information");
	// adding text box
	JTextField txtproi = new JTextField();
	
	JTextArea txtArea = new JTextArea();
	JScrollPane scrollPane = new JScrollPane(txtArea);
	//adding button
	JButton btnView = new JButton("View Report");
	
	JButton btnSal = new JButton("Sales");
	JButton btnStockIn = new JButton("Stock Information");
	
	reports(){
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		//creating a panel
		JPanel panelMenu = new JPanel(new GridBagLayout());
		panelMenu.setBackground(Color.black);
		panelMenu.setPreferredSize(new Dimension(500,50));
		//creating size and adding color
		btnSal.setPreferredSize(new Dimension(150,30));
		btnSal.setBackground(Color.red);
		btnSal.setForeground(Color.black);
		btnSal.addActionListener(this);
		panelMenu.add(btnSal);

		btnStockIn.setPreferredSize(new Dimension(150,30));
		btnStockIn.setBackground(Color.red);
		btnStockIn.setForeground(Color.black);
		btnStockIn.addActionListener(this);
		panelMenu.add(btnStockIn);

		gbc.gridx = 0;
		gbc.gridy = 0;
		frame.add(panelMenu, gbc);
		
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(500, 500));
		panel.setBackground(Color.red);
		gbc.gridx = 0;
		gbc.gridy = 1;
		frame.add(panel, gbc);
		
		JLabel lblCode = new JLabel("Code");
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(lblCode, gbc);
		
		txtproi.setPreferredSize(new Dimension(150,30));
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(txtproi, gbc);
		
		btnView.setPreferredSize(new Dimension(150,30));
		btnView.setBackground(Color.red);
		btnView.setForeground(Color.black);
		btnView.addActionListener(this);
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(btnView, gbc);
		
		txtArea.setPreferredSize(new Dimension(500, 150));
		txtArea.setText("Code\tProduct Name\t\tQty Sold\tTotal");
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(txtArea, gbc);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1080, 720));
		frame.setVisible(true);
	}
    //Connection to database
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnView)) {
			try { 
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","root");
				
				PreparedStatement stmt = con.prepareStatement("select * from salesre");
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					PreparedStatement stmt2 = con.prepareStatement("select productname, Price from pointofsale where productID = ?");
					stmt2.setString(1, rs.getString("idp"));
					ResultSet rs2 = stmt2.executeQuery();
					
					while(rs2.next()) {
						double price = Double.parseDouble(rs.getString("quantity")) * Double.parseDouble(rs2.getString("Price"));
						txtArea.append("\n" + rs.getString("bcode") + "\t" + rs2.getString("productname") + "\t\t" + rs.getString("quantity") + "\t" + price);
					}
					
				}
				stmt.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(e.getSource().equals(btnSal)) {
			frame.dispose();
			new sales();
		} else if(e.getSource().equals(btnStockIn)) {
			//Closing current frame
			frame.dispose();
			//Opening another page
			new stock_info();
		}
	}
}
