package view;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import exception.LimitLoginException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Employee;
import utils.Constants;

public class LoginView extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldEmployeeId;
    private JTextField textFieldPassword;
    private JButton botonLogin;
    private int counterErrorLogin;

    public LoginView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel tituloTienda = new JLabel("TIENDA");
        tituloTienda.setFont(new Font("Tahoma", Font.PLAIN, 20));
        tituloTienda.setBounds(161, 11, 78, 38);
        contentPane.add(tituloTienda);
        
        JLabel userName = new JLabel("Username");
        userName.setFont(new Font("Tahoma", Font.PLAIN, 16));
        userName.setBounds(75, 86, 75, 26);
        contentPane.add(userName);
        
        textFieldEmployeeId = new JTextField();
        textFieldEmployeeId.setBounds(173, 82, 158, 38);
        contentPane.add(textFieldEmployeeId);
        textFieldEmployeeId.setColumns(10);
        
        JLabel password = new JLabel("Password");
        password.setFont(new Font("Tahoma", Font.PLAIN, 16));
        password.setBounds(75, 140, 78, 26);
        contentPane.add(password);
        
        textFieldPassword = new JTextField();
        textFieldPassword.setBounds(173, 136, 158, 38);
        contentPane.add(textFieldPassword);
        textFieldPassword.setColumns(10);
        
        botonLogin = new JButton("Login");
        botonLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
        botonLogin.setBounds(139, 214, 135, 38);
        contentPane.add(botonLogin);
        
        botonLogin.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonLogin) {
            
        	try {
                int employeeId = Integer.parseInt(textFieldEmployeeId.getText()); 
                String password = textFieldPassword.getText();
                
                if (employeeId == 0 || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Usuario o password incorrectos", "Error login", JOptionPane.ERROR_MESSAGE);
                
                } else {
                    Employee employee = new Employee(employeeId, password);
                    
                    boolean logged = employee.login(employeeId, password);
                      
                    if (logged) {
                        ShopView shop = new ShopView();
                        shop.setExtendedState(NORMAL);
                        shop.setVisible(true);
                        dispose();
                    
                    } else {
                        counterErrorLogin++;
                        JOptionPane.showMessageDialog(null, "Usuario o password incorrectos", "Error login", JOptionPane.ERROR_MESSAGE);
                        textFieldEmployeeId.setText("");
                        textFieldPassword.setText("");
                        
                        if (Constants.MAX_LOGIN_TIMES <= counterErrorLogin) {
                            throw new LimitLoginException(counterErrorLogin);
                        }
                        
                        if ((textFieldEmployeeId.getText()).matches("\\d+")) {
                            JOptionPane.showMessageDialog(null, "El número de usuario debe contener solo dígitos", "Error login", JOptionPane.ERROR_MESSAGE);
                            textFieldEmployeeId.setText("");
                            textFieldPassword.setText("");
                            throw new NumberFormatException();                            
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El empleado debe ser numérico", "Error login", JOptionPane.ERROR_MESSAGE);
                textFieldEmployeeId.setText("");
                textFieldPassword.setText("");
                
            } catch (LimitLoginException ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "Error login", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        }
    }
}