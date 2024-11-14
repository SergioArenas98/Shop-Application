package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import main.Shop;
import model.Product;
import utils.Constants;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class ShopView extends JFrame implements ActionListener, KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton exportInventory;
    private JButton showCash;
    private JButton addProduct;
    private JButton addStock;
    private JButton viewInventory;
    private JButton deleteProduct;
    ArrayList<Product> inventory;
    Shop shop;

	/**
	 * Create the frame.
	 */
	public ShopView() {
		
		this.shop = new Shop();
    	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel textTitle = new JLabel("Seleccione o pulse una opción:");
		textTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textTitle.setBounds(117, 32, 193, 17);
		contentPane.add(textTitle);
		
		exportInventory = new JButton("0. EXPORTAR INVENTARIO");
		exportInventory.setBounds(102, 75, 220, 23);
		exportInventory.setBackground(new Color(255, 255, 255));
		contentPane.add(exportInventory);

		showCash = new JButton("1. CONTAR CAJA");
		showCash.setBounds(102, 109, 220, 23);
		showCash.setBackground(new Color(255, 255, 255));
		contentPane.add(showCash);

		addProduct = new JButton("2. AÑADIR PRODUCTO");
		addProduct.setBackground(new Color(255, 255, 255));
		addProduct.setBounds(102, 143, 220, 23);
		contentPane.add(addProduct);

		addStock = new JButton("3. AÑADIR STOCK");
		addStock.setBackground(new Color(255, 255, 255));
		addStock.setBounds(102, 177, 220, 23);
		contentPane.add(addStock);

		viewInventory = new JButton("5. VER INVENTARIO");
		viewInventory.setBackground(new Color(255, 255, 255));
		viewInventory.setBounds(102, 211, 220, 23);
		contentPane.add(viewInventory);

		deleteProduct = new JButton("9. ELIMINAR PRODUCTO");
		deleteProduct.setBackground(new Color(255, 255, 255));
		deleteProduct.setBounds(102, 245, 220, 23);
		contentPane.add(deleteProduct);
			
		exportInventory.addActionListener(this);
		showCash.addActionListener(this);
		addProduct.addActionListener(this);
		addStock.addActionListener(this);
		viewInventory.addActionListener(this);
		deleteProduct.addActionListener(this);
		
		contentPane.setFocusable(true);
        contentPane.addKeyListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == exportInventory) {
			boolean correctExportation = shop.writeInventory();
	        if (correctExportation) {
	            JOptionPane.showMessageDialog(this, 
	                "La exportación se ha realizado con éxito.", "EXPORTACIÓN CORRECTA", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(this, "La exportación no se ha podido realizar.", "ERROR EXPORTACIÓN", JOptionPane.ERROR_MESSAGE);
	        }
		} 
		
		if (e.getSource() == showCash) {
			shop.openCashView(shop);
		}
		
		if (e.getSource() == addProduct) {
			int option = Constants.OPTION_ADD_PRODUCT;
			shop.openProductView(shop, option);
		} 
		
		if (e.getSource() == addStock) {
			int option = Constants.OPTION_ADD_STOCK;
			shop.openProductView(shop, option);
		}
		
		if (e.getSource() == viewInventory) {
			shop.openInventoryView(shop);
		}
		
		if (e.getSource() == deleteProduct) {
			int option = Constants.OPTION_REMOVE_PRODUCT;
			shop.openProductView(shop, option);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
        
		switch (key) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
                 
            case KeyEvent.VK_0:
            	exportInventory.doClick();
                break;
                
            case KeyEvent.VK_1:
                showCash.doClick();
                break;
                
            case KeyEvent.VK_2:
                addProduct.doClick();
                break;
                
            case KeyEvent.VK_3:
                addStock.doClick();
                break;
                
            case KeyEvent.VK_5:
                viewInventory.doClick();
                break;
                
            case KeyEvent.VK_9:
                deleteProduct.doClick();
                break;
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}