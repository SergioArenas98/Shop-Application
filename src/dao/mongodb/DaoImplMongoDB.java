package dao.mongodb;

import org.bson.Document;
import org.bson.conversions.Bson;
import java.time.LocalDateTime;
import java.util.ArrayList;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import dao.Dao;
import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplMongoDB implements Dao{
	
	private MongoDatabase mongoDatabase;
    private MongoClient mongoClient;

	@Override
	public void connect() {
		
		try {
			String uri = "mongodb://localhost:27017";
			MongoClientURI mongoClientURI = new MongoClientURI(uri);
			
			mongoClient = new MongoClient(mongoClientURI);
			mongoDatabase = mongoClient.getDatabase("shop");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError("MongoDB initialization failed: " + e);
		}
	}

	@Override
	public ArrayList<Product> getInventory() {
	    ArrayList<Product> products = new ArrayList<>();
	    MongoCollection<Document> inventoryCollection = mongoDatabase.getCollection("inventory");

	    try {
	        for (Document doc : inventoryCollection.find()) {
	            int id = doc.getInteger("id");
	            String name = doc.getString("name");

	            Document wholesalerPriceDoc = doc.get("wholesalerPrice", Document.class);
	            double price = 0.0;
	            if (wholesalerPriceDoc != null) {
	                Object priceObj = wholesalerPriceDoc.get("price");
	                if (priceObj instanceof Number) {
	                    price = ((Number) priceObj).doubleValue();
	                }
	            }

	            boolean available = doc.getBoolean("available", true);
	            int stock = doc.getInteger("stock", 0);

	            Product product = new Product(id, name, price, available, stock);
	            product.setWholesalerPrice(new Amount(price, "€"));
	            product.setPublicPrice(new Amount(price * 2, "€"));

	            products.add(product);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return products;
	}


	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
	    boolean isSuccess = false;

	    try {
	        MongoCollection<Document> inventoryCollection = mongoDatabase.getCollection("historical_inventory");
	        ArrayList<Document> documents = new ArrayList<>();

	        for (Product product : inventory) {
	            Document productDoc = new Document()
	            	.append("id", product.getProductId())
	                .append("name", product.getName())
	                .append("wholesalerPrice", new Document("price", product.getPrice()).append("currency", "€"))
	                .append("available", product.isAvailable())
	                .append("stock", product.getStock())
	            	.append("created_at", LocalDateTime.now().toString());

	            documents.add(productDoc);
	        }

	        if (!documents.isEmpty()) {
	            inventoryCollection.insertMany(documents);
	        }

	        isSuccess = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return isSuccess;
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
	    Employee employee = null;

	    try {
	    	Bson filter = Filters.and(Filters.eq("employeeId", employeeId), Filters.eq("password", password));
	    	
	        MongoCollection<Document> employeeCollection = mongoDatabase.getCollection("users");
	        Document employeeDoc = employeeCollection.find(filter).first();

	        if (employeeDoc != null) {
	            employee = new Employee(
	                employeeDoc.getInteger("employeeId"),
	                employeeDoc.getString("password")
	            );
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return employee;
	}

	@Override
	public void disconnect() {
	    if (mongoClient != null) {
	        mongoClient.close();
	    }
	}

	@Override
	public void addProduct(Product product) {
	    try {
	        MongoCollection<Document> inventoryCollection = mongoDatabase.getCollection("inventory");
	        
	        Document productDoc = new Document()
	            .append("name", product.getName())
	            .append("wholesalerPrice", new Document("price", product.getPrice()).append("currency", "€"))
	            .append("available", product.isAvailable())
	            .append("stock", product.getStock())
	            .append("id", Product.getLastProductId() + 1);
	        
	        product.setProductId(Product.getLastProductId() + 1);

	        inventoryCollection.insertOne(productDoc);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void updateProduct(Product product) {
	    try {
	        MongoCollection<Document> inventoryCollection = mongoDatabase.getCollection("inventory");

	        Bson filter = Filters.eq("id", product.getProductId());

	        Document updatedProduct = new Document("name", product.getName())
	                .append("wholesalerPrice", new Document("price", product.getPrice()).append("currency", "€"))
	                .append("available", product.isAvailable())
	                .append("stock", product.getStock());

	        inventoryCollection.updateOne(filter, new Document("$set", updatedProduct));

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public void deleteProduct(int productId) {
	    try {
	        MongoCollection<Document> inventoryCollection = mongoDatabase.getCollection("inventory");

	        Bson filter = Filters.eq("id", productId);

	        inventoryCollection.deleteOne(filter);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}