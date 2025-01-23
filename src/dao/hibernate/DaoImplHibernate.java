package dao.hibernate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import dao.Dao;
import model.Amount;
import model.Employee;
import model.Product;
import model.ProductHistory;

public class DaoImplHibernate implements Dao {

	private SessionFactory sessionFactory;
	
	public DaoImplHibernate() {
		try {
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError("SessionFactory initialization failed: " + e);
		}
	}
	
    @Override
    public void connect() {
    	if (sessionFactory == null) {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
    }

	@Override
	public ArrayList<Product> getInventory() {
		Session session = null;
		Transaction transaction = null;
		ArrayList<Product> products = null;
        
		// Fill inventory with products
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            Long count = (Long) session.createQuery("SELECT count(p) FROM Product p").uniqueResult();
            
            // If inventory is empty, products will be inserted
            if (count == 0) {
            
	            Product[] productData = new Product[] {
	            		new Product("Manzana", 10.00, true, 10),
	                    new Product("Pera", 20.00, true, 20),
	                    new Product("Hamburguesa", 30.00, true, 30),
	                    new Product("Fresa", 5.00, true, 20),
	                    new Product("Danone", 12.00, true, 20),
	                    new Product("Naranja", 11.00, true, 20),
	                    new Product("Zumo", 25.00, true, 20),
	                    new Product("Melón", 3.00, true, 20),
	                    new Product("Sandía", 14.00, true, 20),
	                    new Product("Aguacate", 10.00, true, 20),
	                    new Product("Cacaolat", 20.00, true, 20)
	            };

	            // Insert each product into the database
	            for (Product product : productData) {
	                session.save(product);
	            }
	            
            }
            // Query to get inventory
            products = (ArrayList<Product>) session.createQuery("FROM Product", Product.class).list();
            
            for (Product product : products) {
                product.setWholesalerPrice(new Amount(product.getPrice(), "€"));
                product.setPublicPrice(new Amount(product.getPrice() * 2, "€"));
            }

            transaction.commit();
            
        // Rollback if transaction fails
        } catch (Exception e) {
            if (transaction != null) {
            	transaction.rollback();
            }
            e.printStackTrace();
        
        // Close session
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return products;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		Session session = null;
		Transaction transaction = null;
		boolean isSuccess = false;
        
		// Try exporting inventory
        try {
            session = sessionFactory.openSession();
            
            transaction = session.beginTransaction();

            // Save inventory in "products" table
            for (Product product : inventory) {
                // Create a ProductHistory object to export data
                ProductHistory productHistory = new ProductHistory(
                    null,
                    product.getProductId(), 
                    product.getName(),
                    product.getPrice(),
                    product.isAvailable(),
                    product.getStock(),
                    LocalDateTime.now()
                );
                
                // Save the ProductHistory object to the historical_inventory table
                session.save(productHistory);
            }

            transaction.commit();
            isSuccess = true;
            
        // Rollback if transaction fails
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            
        // Close session
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return isSuccess;
	}
	
	@Override
	public void addProduct(Product product) {
		Session session = null;
		Transaction transaction = null;
        
		// Try exporting inventory
        try {
            session = sessionFactory.openSession();
            
            transaction = session.beginTransaction();

            // Save product in "inventory" table
            session.save(product);

            transaction.commit();
            
        // Rollback if transaction fails
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            
        // Close session
        } finally {
            if (session != null) {
                session.close();
            }
        }
	}

	@Override
	public void updateProduct(Product product) {
		Session session = null;
		Transaction transaction = null;
        
		// Try exporting inventory
        try {
            session = sessionFactory.openSession();
            
            transaction = session.beginTransaction();

            // Update product in "inventory" table
            session.update(product);

            transaction.commit();
            
        // Rollback if transaction fails
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            
        // Close session
        } finally {
            if (session != null) {
                session.close();
            }
        }
	}

	@Override
	public void deleteProduct(Long productId) {
		Session session = null;
		Transaction transaction = null;
        
		// Try exporting inventory
        try {
            session = sessionFactory.openSession();
            
            transaction = session.beginTransaction();
            
            // Get product by its id
            Product product = session.get(Product.class, productId);

            // Delete product in "inventory" table
            session.delete(product);

            transaction.commit();
            
        // Rollback if transaction fails
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            
        // Close session
        } finally {
            if (session != null) {
                session.close();
            }
        }
	}
	
    @Override
    public void disconnect() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

	@Override
	public Employee getEmployee(int employeeId, String password) {
		// TODO Auto-generated method stub
		return null;
    }
}
