package model;

import dao.Dao;
import dao.mongodb.DaoImplMongoDB;
import main.Logable;

public class Employee extends Person implements Logable {

    private Dao employeeDao = new DaoImplMongoDB();

    public Employee(int employeeId, String password) {
        super();
    }

    @Override
    public boolean login(int userId, String password) {
        
    	boolean authenticated = false;
        
    	try {
            employeeDao.connect();
            if (employeeDao.getEmployee(userId, password) != null) {
                authenticated = true;
            }
            employeeDao.disconnect();
        
    	} catch (Exception e) {
            e.printStackTrace();
        }
        
    	return authenticated;
    }
}