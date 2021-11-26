package app;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import app.models.Brand;
import app.models.Product;
import app.utils.HibernateUtils;

public class HibernateCriteriaJoinExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
Transaction transaction = null;
		
		//parameterized try block
		try (Session session = HibernateUtils.getSessionFactory("hibernate.cfg.brand.xml").openSession()){ //returning session factory object and directly open session on factory
			transaction = session.beginTransaction();
			
			// Using FROM and JOIN
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
            Root<Product> productRoot = criteriaQuery.from(Product.class);
            Root<Brand> brandRoot = criteriaQuery.from(Brand.class);
            
            criteriaQuery.multiselect(productRoot, brandRoot);
            criteriaQuery.where(builder.equal(productRoot.get("brand"), brandRoot.get("id")));
            
            Query<Object[]> query = session.createQuery(criteriaQuery);
            List<Object[]> list = query.getResultList();
            for (Object[] objects : list) {
                Product product = (Product) objects[0]; //index 0, see line 34 because productRoot.get("brand") is on 1st place
                Brand brand = (Brand) objects[1]; //index 1, see line 34
                System.out.println("Product NAME=" + product.getName() + "\t Brand NAME=" + brand.getName());
            }
			
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
