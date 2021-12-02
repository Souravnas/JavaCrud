import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class JavaCrud {
    private JPanel Main;
    private JTextField textName;
    private JTextField textPrice;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField textPid;
    private JTextField textQty;
    private JButton searchButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("JavaCrud");
        frame.setContentPane(new JavaCrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;

    public JavaCrud() {
        connect();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name,price,qty;
                name=textName.getText();
                price=textPrice.getText();
                qty=textQty.getText();

                try {
                    pst=con.prepareStatement("insert into PRODUCTS(PNAME,PRICE,QTY) values(?,?,?)");
                    pst.setString(1,name);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Record Added Successfully...");

                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String pid=textPid.getText();
                    pst=con.prepareStatement("select * from PRODUCTS where pid=?");
                    pst.setString(1,pid);
                    ResultSet rs=pst.executeQuery();

                    if (rs.next()==true){

                        String name=rs.getString(2);
                        String price=rs.getString(3);
                        String qty=rs.getString(4);

                        textName.setText(name);
                        textPrice.setText(price);
                        textQty.setText(qty);


                    }else {

                        textName.setText("");
                        textPrice.setText("");
                        textQty.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid ID");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pid,name,price,qty;
                pid=textPid.getText();
                name=textName.getText();
                price=textPrice.getText();
                qty=textQty.getText();

                try{
                    pst=con.prepareStatement("update PRODUCTS set PNAME=?,PRICE=?,QTY=? where pid=?");
                    pst.setString(1,name);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.setString(4,pid);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Updated Successfully..");

                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();
                    textPid.setText("");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String bid;
                bid=textPid.getText();
                try {
                    pst=con.prepareStatement("delete from products where pid=?");
                    pst.setString(1,bid);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Record Deleted Successfully..");
                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();
                    textPid.setText("");

                } catch (SQLException throwables2) {
                    throwables2.printStackTrace();
                }


            }
        });
    }

    public void connect(){
        try{
            con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","system");
            System.out.println("Sucess.....");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
