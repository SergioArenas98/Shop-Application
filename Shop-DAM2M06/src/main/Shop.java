package main;

import model.Product;
import model.Client;
import java.awt.EventQueue;
import java.io.*;
import java.util.ArrayList;
import model.Sale;
import model.premiumClient;
import view.CashView;
import view.InventoryView;
import view.LoginView;
import view.ProductView;
import view.FolderNameView;
import model.Amount;
import java.util.Scanner;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import dao.DaoImplXml;
import dao.jaxb.DaoImplJaxb;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Shop {
    public Amount cash;
    public ArrayList<Product> inventory;
    private ArrayList<Sale> sales;
    int sale_num = 0;
    // private DaoImplXml dao;
    private DaoImplJaxb dao;
    final static double TAX_RATE = 1.04;
    String nameFolder;

    public Shop() {
        cash = new Amount(150.0, "€");
        inventory = new ArrayList<Product>();
        sales = new ArrayList<Sale>();
        this.dao = new DaoImplJaxb(this);
        readInventory();
    }
    
    public static void main(String[] args) throws IOException {
    	
    	// Open LoginView when executing the program
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
    
    public void setInventory(ArrayList<Product> inventory) {
        this.inventory = inventory;
    }
     
    /**
     * Get cash value
     */
    public double getCashValue() {
        return cash.getValue();
    }

	/**
	 * read inventory from file
	 */
	public void readInventory() {
		this.setInventory(dao.getInventory());
	}
	
	/**
	 * write inventory from file
	 */
	public boolean writeInventory() {
		return dao.writeInventory(this.inventory);
	}
	
    /**
     * Add new product to inventory
     */
    public void addProduct(String nombreProducto, int precioProducto, int cantidadStock) {      
        inventory.add(new Product(nombreProducto,new Amount(precioProducto, "€"), true, cantidadStock));
        JOptionPane.showMessageDialog(null, "El producto " + nombreProducto + " ha sido añadido con éxito!", "Exit Add Product", JOptionPane.INFORMATION_MESSAGE);  
    }

    /**
     * Add stock for a specific product
     */
    public void addStock(Product product, int newStock) {
    	newStock += product.getStock();
    	product.setStock(newStock);
    	JOptionPane.showMessageDialog(null, "Se ha añadido stock al producto " + product.getName() + " con éxito!", "Exit Add Stock", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Check of product exists
     */
    public boolean productExists(String name) {
    	// Search product on inventory by name
        for (Product product : inventory) {
            if (product != null && product.getName().equalsIgnoreCase(name)) {
            	// Return true if exists
                return true;
            }
        }
        return false;
    }
    
    /**
     * Delete product
     */
    public void deleteProduct(Product product) {
        inventory.remove(product);
        JOptionPane.showMessageDialog(null, "Se ha eliminado el producto " + product.getName() + " con éxito!", "Exit Delete Stock", JOptionPane.INFORMATION_MESSAGE);
    }
	
    /**
     * Set a product as expired
     */
    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();

        Product product = findProduct(name);

        // If product exists 
        if (product != null) {
        	// Call method to modify product price
        	product.expire();
            System.out.println("El precio de venta al público del producto " + name + " ha sido actualizado a " + product.getPublicPrice());
        } else {
        	System.out.println("El producto " + name + " no existe o no se ha encontrado.");
        }
    }
    
	/**
	 * find product by name
	 */
	public Product findProduct(String name) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) != null && inventory.get(i).getName().equalsIgnoreCase(name)) {
				return inventory.get(i);
			}
		}
		return null;
	}
    
    /**
     * Open CashView frame
     */
    public void openCashView(Shop shop) {
    	CashView dialog = new CashView(shop);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
    }
    
    /**
     * Open ProductView frame
     */
    public void openProductView(Shop shop, int option) {   	
    	ProductView dialog = new ProductView(shop, option);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
    }
    
    /**
     * Open InventoryView frame
     */
    
    public void openInventoryView(Shop shop) {
        InventoryView inventoryView = new InventoryView(this);
        inventoryView.setModal(true);
        inventoryView.setVisible(true);
    }
    
    /**
     * Open ProductView frame
     */
    public void openFolderNameView(Shop shop) {
    	FolderNameView dialog = new FolderNameView(shop);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
    }
    
	/*================================*/
	/*================================*/
    
    /**
	 * Make a sale of products
	 */
	public void sale() {
		Scanner sc = new Scanner(System.in);

		Double totalCostProduct = 0.0;
		Amount totalAmountSale = new Amount(0.0, "€");
		String name = "";
		Double totalPriceSale = 0.0;
		boolean saleDone = false;
		Product product;
		int quantity;

		LocalDateTime actualDate = LocalDateTime.now();
		DateTimeFormatter formatedActualDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		ArrayList<Product> products = new ArrayList<Product>();
		
		// Ask if user is premium or not
		System.out.println("Es un cliente premium? (y/n)");
		String clientPremium = sc.nextLine();

		// Sale to a premium client
		switch (clientPremium) {

		case "y":
			System.out.println("Realizar venta, escribir nombre cliente");
			String clientPremiumName = sc.nextLine();

			int pointsSale = 0;

			Client premiumClient = new premiumClient(456, clientPremiumName, new Amount(50, "€"), pointsSale);

			//showInventory();

			// Sale until input name is not 0
			while (!name.equals("0")) {

				sc = new Scanner(System.in);

				System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
				name = sc.nextLine();

				if (name.equals("0")) {
					break;
				}

				product = findProduct(name);

				// If product is found
				if (product != null && product.isAvailable()) {

					System.out.println("Introduce la cantidad deseada:");
					quantity = sc.nextInt();

					// If no more stock, set as not available to sale
					if (product.getStock() == 0) {
						product.setAvailable(false);
						System.out.println("Producto sin stock.");

					// Quantity entered is greater than stock available
					} else if (quantity > product.getStock()) {
						product.setAvailable(false);
						System.out.println("Cantidad mayor al stock disponible del producto.");
						break;

					// If everything okay, proceed to sale
					} else {

						// Get total product cost (price + quantity)
						totalCostProduct = (product.getPublicPrice().getValue() * TAX_RATE) * quantity;
						System.out.println("Importe del producto adquirido: " + totalCostProduct + ".\n");
						
						// Add product to list
						products.add(product);
						System.out.println("Producto añadido a la cesta con éxito.\n");
						
						// Subtract quantity from product stock
						product.setStock(product.getStock() - quantity);

						// Get total sale cost 
						totalPriceSale += (totalAmountSale.getValue() + totalCostProduct);
						System.out.println("Importe total de la compra: " + totalPriceSale + ".\n");

						saleDone = true;
					}
				} 
				
				// If product is not found
				else if (product == null) {
					System.out.println("Producto no encontrado.");
				} 
				
				// If product is not available
				else if (!product.isAvailable()) {
					System.out.println("El producto no está disponible.");
				}
			}

			// Once customer completes the purchase, show total cost and pay
			if (saleDone) {
				
				Amount totalPrice = new Amount (totalPriceSale, "€");
				
				// Check if client has enough cash to pay
				boolean payable = premiumClient.pay(totalPrice);
				
				// If client has enough cash
				if (!payable) {
					
					// Formated date
					String formatedDate = actualDate.format(formatedActualDate);
					
					// Add all sales
					sales.add(new Sale(premiumClient, products, totalAmountSale, formatedDate));
					
					// Show balance
					System.out.println("Saldo restante: " + premiumClient.getBalance() + "\n");
					
				// If client has not enough cash
				} else {
					System.out.println("No tienes saldo suficiente para realizar la compra.\n");
				}
				
			// If client did not purchase any product
			} else {
				System.out.println("Venta no realizada.");
			}

			break;

		// Sale to a standard client
		case "n":
			System.out.println("Realizar venta, escribir nombre cliente");
			String clientStandardName = sc.nextLine();

			Client client = new Client(456, clientStandardName, new Amount(50, "€"));

			// Show inventory
			//showInventory();

			// Sale until input name is not 0
			while (!name.equals("0")) {

				sc = new Scanner(System.in);

				System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
				name = sc.nextLine();

				if (name.equals("0")) {
					break;
				}

				product = findProduct(name);
				
				// If product is found
				if (product != null && product.isAvailable()) {

					System.out.println("Introduce la cantidad deseada:");
					quantity = sc.nextInt();

					// If no more stock, set as not available to sale
					if (product.getStock() == 0) {
						product.setAvailable(false);
						System.out.println("Producto sin stock.");

					// Quantity entered is greater than stock available
					} else if (quantity > product.getStock()) {
						product.setAvailable(false);
						System.out.println("Cantidad mayor al stock disponible del producto.");
						break;

					// If everything okay, proceed to sale
					} else {

						// Get total product cost (price + quantity)
						totalCostProduct = (product.getPublicPrice().getValue() * TAX_RATE) * quantity;
						System.out.println("Importe del producto adquirido: " + totalCostProduct + ".\n");
						
						// Add product to list
						products.add(product);
						System.out.println("Producto añadido a la cesta con éxito.\n");
						
						// Subtract quantity from product stock
						product.setStock(product.getStock() - quantity);

						// Get total sale cost 
						totalPriceSale += (totalAmountSale.getValue() + totalCostProduct);
						System.out.println("Importe total de la compra: " + totalPriceSale + ".\n");

						saleDone = true;
					}
				} 
				
				// If product is not found
				else if (product == null) {
					System.out.println("Producto no encontrado.");
				} 
				
				// If product is not available
				else if (!product.isAvailable()) {
					System.out.println("El producto no está disponible.");
				}
			}

			// Once customer completes the purchase, show total cost and pay
			if (saleDone) {
				
				Amount totalPrice = new Amount (totalPriceSale, "€");
				
				// Check if client has enough cash to pay
				boolean payable = client.pay(totalPrice);
				
				// If client has enough cash
				if (!payable) {
					
					// Formated date
					String formatedDate = actualDate.format(formatedActualDate);
					
					// Add all sales
					sales.add(new Sale(client, products, totalAmountSale, formatedDate));
					
					// Show balance
					System.out.println("Saldo restante: " + client.getBalance() + "\n");
					
				// If client has not enough cash
				} else {
					System.out.println("No tienes saldo suficiente para realizar la compra.\n");
				}
				
			// If client did not purchase any product
			} else {
				System.out.println("Venta no realizada.");
			}

			break;

		default:
			System.out.println("Introduce un carácter válido (y/n)");
			break;
		}
	}
	
    /**
     * Show all sales
     */
    private void showSales() throws IOException {
    	Scanner sc = new Scanner(System.in);
    	
    	// Show all sales
        System.out.println("Lista de ventas:");
        for (Sale sale : sales) {
            if (sale != null) {
                System.out.println(sale);
            }
        }
        
        // Export sales to a text file
        System.out.println("¿Quieres exportar el listado de ventas? (Si/No)");
        String exportSales = sc.nextLine();
        
        if (exportSales.equalsIgnoreCase("Si")){
        	exportSales();
    	}
    }
	
    /**
     * Exports all sales to a text file
     */
    private void exportSales() throws IOException {
    	
		File newFolder = new File(System.getProperty("user.dir") + File.separator + "files");
		if(!newFolder.exists()){
			newFolder.mkdir();
		}
		
		File salesFile = new File(System.getProperty("user.dir") + File.separator + "files/sales.txt");
		if(!salesFile.exists()) {
			salesFile.createNewFile();
		}
		
		FileWriter fw = new FileWriter(salesFile,true);
		PrintWriter pw = new PrintWriter(fw);
		
		// Get sales and print them on a text file
	    for (int i = 0; i < sales.size(); i++) {
	        Sale sale = sales.get(i);

	        pw.println((i + 1) + ";Client=" + sale.getClient() + ";Date=" + sale.getActualDate() + ";");
	        
	        ArrayList<Product> products = sale.getProducts();
	        StringBuilder productsString = new StringBuilder();
	        
	        for (Product product : products) {
	            productsString.append(product.getName()).append(",").append(product.getPublicPrice()).append(";");
	        }
	        
	        pw.println((i + 1) + ";Products=" + productsString.toString());
	        
	        pw.println((i + 1) + ";Amount=" + sale.getAmount() + ";");
	    }
	    
	    System.out.println("¡El listado de ventas ha sido exportado con éxito!");
		pw.close();
		fw.close();
    }

    /**
     * Show total sales value
     */
    private void showSalesValue() {
        Amount totalSales = new Amount(0.0, "€");
        System.out.print("Precio total de las ventas realizadas: ");
        
        // Get total price from sales
        for (Sale sale : sales) {
            if (sale != null) {
                totalSales.setValue(totalSales.getValue() + sale.getAmount().getValue());
            }
        }
        System.out.println(totalSales);
    }
}