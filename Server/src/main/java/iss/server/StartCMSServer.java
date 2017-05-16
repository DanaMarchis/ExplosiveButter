package iss.server;

import iss.persistence.UserRepository;
import iss.server.service.ImplementedService;
import iss.services.ConfException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.util.Properties;

import iss.model.User;

/**
 * Created by flori on 5/17/2017.
 */
public class StartCMSServer {

    static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

    public static void main(String[] args) {
        initialize();
        UserRepository userRepo=new UserRepository(sessionFactory);
        ImplementedService service=new ImplementedService(userRepo);
        close();
    }
}
