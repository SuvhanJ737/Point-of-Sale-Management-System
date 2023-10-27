import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

public class sales implements ActionListener {
JFrame frame = new JFrame("Sales");
//Adding text box
JTextField txtproi = new JTextField();
JTextField txtquantity = new JTextField();
JTextField txtTot = new JTextField();
//Adding button
JButton btnAdd = new JButton("Add");
JButton btnProSale = new JButton("Process Sale");
// Adding textarea
JTextArea txtAre = new JTextArea();
JScrollPane scrollPane = new JScrollPane(txtAre);

ArrayList<String> barcode = new ArrayList<String>();
ArrayList<String> qty = new ArrayList<String>();

JButton btnStockIn = new JButton("Stock Information");
JButton btnreport = new JButton("Reports");

double total = 0.0;

sales(){
frame.setLayout(new GridBagLayout());
GridBagConstraints gbc = new GridBagConstraints();

JPanel panelMe = new JPanel(new GridBagLayout());
panelMe.setBackground(Color.black);
panelMe.setPreferredSize(new Dimension(600, 50));

btnStockIn.setPreferredSize(new Dimension(150,30));
btnStockIn.setBackground(Color.black);
btnStockIn.setForeground(Color.red);
btnStockIn.addActionListener(this);
panelMe.add(btnStockIn);


btnreport.setPreferredSize(new Dimension(150,30));
btnreport.setBackground(Color.black);
btnreport.setForeground(Color.red);
btnreport.addActionListener(this);
panelMe.add(btnreport);


gbc.gridx = 0;
gbc.gridy = 0;
frame.add(panelMe, gbc);

//Panel
JPanel panel = new JPanel(new GridBagLayout());
panel.setPreferredSize(new Dimension(500,500));
panel.setBackground(Color.red);
gbc.gridx = 0;
gbc.gridy = 1;
frame.add(panel, gbc);
//
JLabel lblCode = new JLabel("Barcode");
gbc.gridx = 0;
gbc.gridy = 0;
panel.add(lblCode, gbc);
//
txtproi.setPreferredSize(new Dimension(150,30));
//Adding color to textbox
txtproi.setBackground(Color.black);
txtproi.setForeground(Color.red);
gbc.gridx = 0;
gbc.gridy = 1;
panel.add(txtproi, gbc);
//
JLabel lblQty = new JLabel("QTY");
gbc.gridx = 1;
gbc.gridy = 0;
panel.add(lblQty, gbc);
//
txtquantity.setPreferredSize(new Dimension(150,30));
txtquantity.setBackground(Color.black);
txtquantity.setForeground(Color.red);
gbc.gridx = 1;
gbc.gridy = 1;
panel.add(txtquantity, gbc);
// add buttons
btnAdd.setPreferredSize(new Dimension(150,30));
btnAdd.setBackground(Color.black);
btnAdd.setForeground(Color.red);
btnAdd.addActionListener(this);
gbc.gridx = 0;
gbc.gridy = 2;
panel.add(btnAdd,gbc);
// process button
btnProSale.setPreferredSize(new Dimension(150,30));
btnProSale.setBackground(Color.black);
btnProSale.setForeground(Color.red);
btnProSale.addActionListener(this);
gbc.gridx = 1;
gbc.gridy = 2;
panel.add(btnProSale,gbc);
// text area
txtAre.append("Barcode\tDescription\tPrice\tQTY\tTotal");
txtAre.setPreferredSize(new Dimension(500, 150));
gbc.gridx = 0;
gbc.gridy = 3;
gbc.gridwidth = 2;
panel.add(txtAre, gbc);
//
JLabel lblTotal = new JLabel("Total");
gbc.gridx = 0;
gbc.gridy = 4;
gbc.gridwidth = 1;
panel.add(lblTotal, gbc);
//
txtTot.setPreferredSize(new Dimension(150,30));
txtTot.setBackground(Color.black);
txtTot.setForeground(Color.red);
gbc.gridx = 1;
gbc.gridy = 4;
panel.add(txtTot, gbc);

frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setSize(new Dimension(1080, 720));
frame.setVisible(true);
}
@Override
public void actionPerformed(ActionEvent e) {

if(e.getSource().equals(btnAdd)) {
//Using try statement
try {
Class.forName("com.mysql.cj.jdbc.Driver");
//Connection for database
java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","root");

PreparedStatement stmt = con.prepareStatement("select productname, Price from pointofsale where productID = ?");
stmt.setString(1, txtproi.getText());
ResultSet rs = stmt.executeQuery();

while(rs.next()) {
double price = Double.parseDouble(txtquantity.getText()) * Double.parseDouble(rs.getString("price"));
total += price;
txtAre.append("\n" + txtproi.getText() + "\t" + rs.getString("productname") + "\t" + rs.getString("Price") + "\t" + txtquantity.getText() + "\t" + price);

barcode.add(txtproi.getText());
qty.add(txtquantity.getText());
}

stmt.close();
txtTot.setText(String.valueOf(Math.round(total * 100) / 100.00));

txtproi.setText(null);
txtquantity.setText(null);
} catch (Exception e1) {
// TODO Auto-generated catch block
e1.printStackTrace();
}
} else if(e.getSource().equals(btnProSale)) {
String[] arrBarcode = barcode.stream().toArray(String[]::new);
String[] arrQty = qty.stream().toArray(String[]::new);
int c = 0,  c2 = 1;
for(int i = 0; i < arrBarcode.length; i++) {
try {
Class.forName("com.mysql.cj.jdbc.Driver");//
java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","root");
PreparedStatement stmt = con.prepareStatement("select qty from pointofsale where productID = ?");
stmt.setString(1, arrBarcode[i]);
ResultSet rs = stmt.executeQuery();
rs.next();
int qty = Integer.parseInt(rs.getString("qty"));//
stmt.close();

int strQty = (qty - Integer.parseInt(arrQty[i]));
String strQty2 = String.valueOf(strQty);
PreparedStatement stmt2 = con.prepareStatement("update pointofsale set qty = ? where productID = ?");//
stmt2.setString(1, strQty2);
stmt2.setString(2, arrBarcode[i]);
stmt2.executeUpdate();
stmt2.close();

PreparedStatement stmt3 = con.prepareStatement("insert into salesre (bcode, quantity) values(?, ?)");//
stmt3.setInt(1, Integer.parseInt(arrBarcode[i]));
stmt3.setInt(2, Integer.parseInt(arrQty[i]));
stmt3.execute();
stmt3.close();

txtAre.setText("Barcode\tdescription\tprice\tQty\ttotal");

JOptionPane.showMessageDialog(null, "Sale Processed", "Success", JOptionPane.INFORMATION_MESSAGE);

} catch (Exception e1) {
// TODO Auto-generated catch block
e1.printStackTrace();

}
}
}
if(e.getSource().equals(btnStockIn)) {
	new stock_info();
	
} else if(e.getSource().equals(btnreport)) {
	new reports();
}

}
}
