package iss.server;

import iss.model.Conference;
import iss.model.Role;
import iss.networking.utils.AbstractServer;
import iss.networking.utils.ConfRpcConcurrentServer;
import iss.networking.utils.ServerException;
import iss.persistence.MyTable;
import iss.persistence.RoleRepo;
import iss.persistence.UserRepository;
import iss.persistence.ConferenceRepo;
import iss.server.service.ImplementedService;
import iss.services.IConfServer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

import iss.model.User;

/**
 * Created by flori on 5/17/2017.
 */
public class StartCMSServer {

    static SessionFactory sessionFactory;
    private static int defaultPort=55555;

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
        UserRepository userRepo = new UserRepository(sessionFactory);
        ConferenceRepo conferenceRepo=new ConferenceRepo(sessionFactory);
        RoleRepo roleRepo=new RoleRepo(sessionFactory);



        IConfServer serverImpl = new ImplementedService(userRepo,conferenceRepo,roleRepo);
        int confPort = defaultPort;
        System.out.println("Starting server on port: " + confPort);
        AbstractServer server = new ConfRpcConcurrentServer(confPort, serverImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
        close();
    }
}
