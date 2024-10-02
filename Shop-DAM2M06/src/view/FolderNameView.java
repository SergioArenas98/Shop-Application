package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import main.Shop;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class FolderNameView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField nameFolder;
	private String folderName;
	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Create the dialog.
	 */
	public FolderNameView(Shop shop) {
		
	    super();
	    setModal(true);
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			textField = new JTextField();
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		
		nameFolder = new JTextField();
		nameFolder.setBounds(111, 114, 206, 20);
		contentPanel.add(nameFolder);
		nameFolder.setColumns(10);
		
		JLabel firstTitle = new JLabel("\"Files\" folder doesn't exists!");
		firstTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		firstTitle.setBounds(120, 39, 185, 14);
		contentPanel.add(firstTitle);
		
		JLabel secondTitle = new JLabel("Insert the new folder's name:");
		secondTitle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		secondTitle.setBounds(130, 87, 175, 14);
		contentPanel.add(secondTitle);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
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

		// if user clicks on OK button
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				folderName = nameFolder.getText();
				dispose();
			}
		});
		
		// if user clicks on cancel button
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				folderName = null;
				dispose();
			}
		});
	}

    public String getFolderName() {
        return folderName;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}