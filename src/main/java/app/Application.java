package app;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import app.models.Category;
import app.utils.HibernateUtils;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Transaction transaction = null;
		
		//parameterized try block
		try (Session session = HibernateUtils.getSessionFactory().openSession()){ //returning session factory object and directly open session on factory
			transaction = session.beginTransaction();
			Category cat1 = new Category("input device");
			Category cat2 = new Category("output device");
			session.save(cat1); //or persist
			session.save(cat2); //or persist
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
		}*/
		
		//HibernateUtils.shutdown();
		
		getCategories(); //to print the values we need a default constructor of Category
		
		getCategoryById(1);//if we dont have default constructor of Category, following error occurs:
		
		/*
		 * 
		 * Juni 05, 2021 7:36:36 VORM. org.hibernate.proxy.pojo.bytebuddy.ByteBuddyProxyFactory getProxy
ERROR: HHH000143: Bytecode enhancement failed because no public, protected or package-private default constructor 
was found for entity: app.models.Category. Private constructors don't work with runtime proxies!
		 * 
		 * */
		
		updateCategories(10, "ABC");
		
		deleteCategories(1);


	}
	
	
	public static void getCategories() {
		try (Session session = HibernateUtils.getSessionFactory("hibernate.cfg.xml").openSession()){ //returning session factory object and directly open session on factory
			List<Category> categories = session.createQuery("from Category", Category.class).list();
			categories.forEach(category -> System.out.println(category.toString()));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void getCategoryById(int id) {
		try (Session session = HibernateUtils.getSessionFactory("hibernate.cfg.xml").openSession()){ //returning session factory object and directly open session on factory
			Category cat = session.load(Category.class, id); //use load() to get by id!!
			if(cat != null) {
				System.out.println(cat);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void updateCategories(int id, String name) {
		Transaction transaction = null;
		try (Session session = HibernateUtils.getSessionFactory("hibernate.cfg.xml").openSession()){ //returning session factory object and directly open session on factory
			transaction = session.beginTransaction();
			Category cat = session.get(Category.class, id);
			
			if(cat !=null) {
				cat.setName(name);
			}else {
				
			}
			
			session.update(cat); 
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null) { //allways rollback a transaction!!!
				transaction.rollback();
			}
		}
	}
	
	public static void deleteCategories(int id) {
		Transaction transaction = null;
		try (Session session = HibernateUtils.getSessionFactory("hibernate.cfg.xml").openSession()){ //returning session factory object and directly open session on factory
			transaction = session.beginTransaction();
			Category cat = session.get(Category.class, 1);
			
			if(cat !=null) {
				session.delete(cat);
			}else {
				System.out.println("object not found");
			}

			transaction.commit();
		} catch (Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
	}

}
