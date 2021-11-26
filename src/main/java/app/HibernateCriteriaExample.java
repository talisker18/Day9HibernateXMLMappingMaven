package app;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import app.models.Brand;
import app.models.Product;
import app.utils.HibernateUtils;

public class HibernateCriteriaExample {

	//following criteria query only works if we do not use the brand in the toString method
	//dont know why exactly...maybe we should change the criteria query to work with brand as well
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		
		//parameterized try block
		try (Session session = HibernateUtils.getSessionFactory("hibernate.cfg.brand.xml").openSession()){ //returning session factory object and directly open session on factory
			transaction = session.beginTransaction();
			
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Product> query = builder.createQuery(Product.class);
			Root<Product> root = query.from(Product.class);
			//query.select(root); -> select all
			query.select(root).where(builder.equal(root.get("id"), 1));
			//query.select(root.get("name")); -> prints only the name of products
			
			Query<Product> q = session.createQuery(query);
			
			//make to print all
			/*List<Product> products = q.getResultList();
			
			for(Product p: products) {
				System.out.println(p);
			}*/
			
			Product product = q.getSingleResult();
			System.out.println(product);
			
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
