package app;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import app.models.Brand;
import app.models.Product;
import app.utils.HibernateUtils;

public class AppOneToManyExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		
		//parameterized try block
		try (Session session = HibernateUtils.getSessionFactory("hibernate.cfg.brand.xml").openSession()){ //returning session factory object and directly open session on factory
			transaction = session.beginTransaction();
			Brand brand1 = new Brand("Samsung");
			Product p1 = new Product("samsung s7","1000",brand1);
			Product p2 = new Product("samsung s8","800",brand1);
			Product p3 = new Product("samsung s9","1200",brand1);
			
			Set<Product> products = new HashSet<Product>();
			products.add(p1);
			products.add(p2);
			products.add(p3);
			
			brand1.setProducts(products);
			session.save(brand1); //or persist...here we have to save only the brand...products are save automatically!
			//but we have to declare mapping resource of both in cfg file, see hibernate.hbm.brand.xml

			transaction.commit();
			
			System.out.println("success creating category");
			//session.close(); -> is not needed here because we declare session
			//object as try parameter so it will be autoclosed after try block was
			//executed
		} catch (Exception e) {
			// TODO: handle exception
			if(transaction != null) {
				transaction.rollback();
			}
		}
		
		HibernateUtils.shutdown();

	}

}
