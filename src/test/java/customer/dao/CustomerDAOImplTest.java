package customer.dao;

import static org.junit.Assert.*;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class CustomerDAOImplTest {

    EntityManager entityManager;
    
    CustomerDAOImpl mockDAO;
    
    @Mock
    Customer mockCusomer;
    
    @Before
    public void setUp(){
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("07_JPA");
        entityManager = entityManagerFactory.createEntityManager();
        mockDAO = new CustomerDAOImpl(entityManager);
    }
    
    @Test
    public void testFindByPassport() {
        assertNull("Not founded cusotmer", mockDAO.findByPassport(mockCusomer));
    }

    @Ignore
    @Test
    public void testCreate() {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testUpdate() {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testFindIdType() {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testFindAll() {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testDeleteById() {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testFindIntInt() {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testDelete() {
        fail("Not yet implemented");
    }

}
