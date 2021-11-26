package app.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {
	
	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory(String hibernateCfg) {
		if(sessionFactory == null) {
			try {
				registry = new StandardServiceRegistryBuilder().configure(hibernateCfg).build();
				Metadata meta = new MetadataSources(registry).getMetadataBuilder().build();
				sessionFactory = meta.getSessionFactoryBuilder().build();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				//if exception occurs, destroy everthing
				if(registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
			}
		}
		
		return sessionFactory;
	}
	
	public static void shutdown() {
		if(registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

}
