package mapper.mapping;

import static org.junit.Assert.*;
import mapper.MapperException;

import org.junit.Test;

import classexamples.good.FromClass;

public class ClassMapperImplTest {

	@Test
	public void testCreateMap() throws MapperException {
		ClassMapper prov = new ClassMapperImpl();
		prov.createMap(FromClass.class);
		assertNotNull("Root map object not created", prov.getMap());
		assertNotNull("Fields set not created", prov.getMap().getClassFields());
	}

	@Test
	public void testGetMap() {
		ClassMapper prov = new ClassMapperImpl();
		assertTrue("Map is created but map not readed", prov.getMap().isEmpty());
		assertTrue("Map is created but map not readed", prov.getMap().getClassFields().isEmpty());
	}

	@Test
	public void testGetFields() {
		ClassMapper prov = new ClassMapperImpl();
		assertNotNull(prov.getMap().getClassFields());
	}
	
	@Test
	public void testGetFieldsOnEmpty() {
		ClassMapper prov = new ClassMapperImpl();
		assertEquals(prov.getMap().getClassFields().size(),0);
	}

}
