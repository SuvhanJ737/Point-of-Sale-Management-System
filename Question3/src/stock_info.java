import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.*;

import javax.swing.*;

public class stock_info implements ActionListener {
	//Creating a frame
JFrame frame = new JFrame("Stock Information");
JTextArea txtArea = new JTextArea();
JScrollPane scrollPane = new JScrollPane(txtArea);

JButton btnSal = new JButton("Sales");

stock_info(){
frame.setLayout(new GridBagLayout());
GridBagConstraints gbc = new GridBagConstraints();
//creating a menu panel
JPanel panelMenu = new JPanel(new GridBagLayout());
panelMenu.setBackground(Color.black);
panelMenu.setPreferredSize(new Dimension(500,50));

gbc.gridx = 0;
gbc.gridy = 0;
frame.add(panelMenu, gbc);

//Creating size and giving color to button
btnSal.setPreferredSize(new Dimension(150,30));
btnSal.setBackground(Color.red);
btnSal.setForeground(Color.black);
btnSal.addActionListener(this);
panelMenu.add(btnSal);

//Panel
JPanel panel = new JPanel(new GridBagLayout());
panel.setPreferredSize(new Dimension(500,500));
panel.setBackground(Color.red);
gbc.gridx = 0;
gbc.gridy = 1;
frame.add(panel, gbc);

//Text area
txtArea.append("Barcode\tDescription\tPrice\tQTY");
txtArea.setPreferredSize(new Dimension(400, 150));
gbc.gridx = 0;
gbc.gridy = 0;
panel.add(txtArea, gbc);

display();

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setSize(new Dimension(1080, 720));
frame.setVisible(true);
}
//Connection to database
public void display() {
try {
Class.forName("com.mysql.cj.jdbc.Driver");
java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","root");

PreparedStatement stmt = con.prepareStatement("select * from pointofsale");
ResultSet rs = stmt.executeQuery();

while(rs.next()) {
txtArea.append("\n" + rs.getString("productID") + "\t" + rs.getString("productname") + "\t" + rs.getString("Price") + "\t" + rs.getString("qty"));
}
stmt.close();
} catch (Exception e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}

@Override
public void actionPerformed(ActionEvent e) {
if(e.getSource().equals(btnSal)) {
frame.dispose();
new reports();
}
}
}