package mapper.mapclass;

import static org.junit.Assert.*;
import mapper.MapperException;

import org.junit.Ignore;
import org.junit.Test;

import classexamples.good.FromClass;

public class MapProviderImplTest {

	@Test
	public void testCreateMap() throws MapperException {
		MapProviderImpl prov = new MapProviderImpl();
		prov.createMap(FromClass.class);
		assertNotNull("Root map object not created", prov.getMap());
		assertNotNull("Fields set not created", prov.getFields());
	}

	@Test
	public void testGetMap() {
		MapProviderImpl prov = new MapProviderImpl();
		assertTrue("Map is created but map not readed", MapClass.isEmpty(prov.getMap()));
		assertTrue("Map is created but map not readed", prov.getFields().isEmpty());
	}

	@Test
	public void testGetFields() {
		MapProviderImpl prov = new MapProviderImpl();
		assertNotNull(prov.getFields());
	}
	
	@Test
	public void testGetFieldsOnEmpty() {
		MapProviderImpl prov = new MapProviderImpl();
		assertEquals(prov.getFields().size(),0);
	}
	

}
