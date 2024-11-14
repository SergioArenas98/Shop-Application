package view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.Shop;

public class CashView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField cashLabel;

	/**
	 * Create the dialog.
	 */
	public CashView(Shop shop) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Dinero en caja:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblNewLabel.setBounds(69, 109, 122, 27);
			contentPanel.add(lblNewLabel);
		}
		
		cashLabel = new JTextField();
		cashLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cashLabel.setBounds(201, 104, 149, 37);
		contentPanel.add(cashLabel);
		cashLabel.setColumns(10);
		cashLabel.setEditable(false);
		cashLabel.setText(String.valueOf(shop.getCashValue()) + "€");
		
		JButton closeButton = new JButton("Cerrar");
		closeButton.setBounds(174, 179, 89, 23);
		contentPanel.add(closeButton);
        
		closeButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}
}