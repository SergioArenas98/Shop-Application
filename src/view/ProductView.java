package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import main.Shop;
import model.Product;
import utils.Constants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;

public class ProductView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField name;
	private JTextField stock;
	private JTextField price;
	private JPanel buttonPane;
	private JButton okButton;
	private Shop shop;
	private JButton cancelButton;
	
	int option = 0;

	/**
	 * Create the dialog.
	 */
	public ProductView(Shop shop, int option) {
		
		this.shop = shop;
		this.option = option;
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 436, 230);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		if (option == Constants.OPTION_ADD_PRODUCT || option == Constants.OPTION_ADD_STOCK || option == Constants.OPTION_REMOVE_PRODUCT) {
		
			JLabel labelNombreProducto = new JLabel("Product Name:");
			labelNombreProducto.setBounds(53, 48, 129, 20);
			labelNombreProducto.setFont(new Font("Tahoma", Font.PLAIN, 16));
			contentPanel.add(labelNombreProducto);
			
			name = new JTextField();
			name.setBounds(207, 46, 164, 28);
			contentPanel.add(name);
			name.setColumns(10);
			
			if (option == Constants.OPTION_ADD_PRODUCT || option == Constants.OPTION_ADD_STOCK) {
				JLabel labelStockProducto = new JLabel("Product Stock:");
				labelStockProducto.setFont(new Font("Tahoma", Font.PLAIN, 16));
				labelStockProducto.setBounds(53, 104, 129, 20);
				contentPanel.add(labelStockProducto);
				
				stock = new JTextField();
				stock.setColumns(10);
				stock.setBounds(207, 102, 164, 28);
				contentPanel.add(stock);
				
				if (option == Constants.OPTION_ADD_PRODUCT) {
					JLabel labelProductPrice = new JLabel("Product Price:");
					labelProductPrice.setFont(new Font("Tahoma", Font.PLAIN, 16));
					labelProductPrice.setBounds(53, 162, 129, 20);
					contentPanel.add(labelProductPrice);
					
					price = new JTextField();
					price.setColumns(10);
					price.setBounds(207, 154, 164, 28);
					contentPanel.add(price);
				}
			}
		}

		{
			buttonPane = new JPanel();
			buttonPane.setBounds(0, 230, 436, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// If user clicks on OK button
		boolean productExists;
		Product productFound;

		switch (this.option) {

		case Constants.OPTION_ADD_PRODUCT:

			if (e.getSource() == okButton) {

				// check if product does not exists
				productExists = shop.productExists(name.getText());

				if (!productExists) {
					shop.addProduct(name.getText(), Integer.parseInt(price.getText()), Integer.parseInt(stock.getText()));
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Error, product " + name.getText() + " already exists.",
							"Product Already Exists", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			} else if (e.getSource() == cancelButton) {
				dispose();
			}
		break;

		case Constants.OPTION_ADD_STOCK:

			if (e.getSource() == okButton) {

				// Check if product does not exists
				productFound = shop.findProduct(name.getText());

				if (productFound != null) {
					shop.addStock(productFound, Integer.parseInt(stock.getText()));
					dispose();
				} else {
					JOptionPane.showMessageDialog(null,
							"Error, product " + name.getText() + " not found.",
							"Error Product Not Found", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			} else if (e.getSource() == cancelButton) {
				dispose();
			}
		break;
			
		case Constants.OPTION_REMOVE_PRODUCT:

			if (e.getSource() == okButton) {

				// Check if product does not exists
				productFound = shop.findProduct(name.getText());

				if (productFound != null) {
					shop.deleteProduct(productFound);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null,
							"Error, product " + name.getText() + " could not be deleted.",
							"Error Delete Stock", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			} else if (e.getSource() == cancelButton) {
				dispose();
			}
		break;
		}
	}
}