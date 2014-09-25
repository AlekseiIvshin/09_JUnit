package mapper.mapping;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import mapper.MapperException;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import classexamples.good.FromClass;
import static org.mockito.Mockito.*;

public class ClassMapperImplTest {

	@Mock
	ClassMapperImpl mockMapper = mock(ClassMapperImpl.class);
	@Mock
	FromClass mockSourceClass = mock(FromClass.class);
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
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
	
	@Ignore
	@Test
	public void testOnCheckSelfContain() throws MapperException{
		exception.expect(MapperException.class);
		exception.expectMessage("contains itself in fields");
		when(mockMapper.classContainItself(FromClass.class)).thenReturn(true);
		mockMapper.createMap(FromClass.class);
		verify(mockMapper, times(1)).classContainItself(FromClass.class);
	}
	
	@Test
	public void testNullSourceClass() throws MapperException{
		ClassMapper prov = new ClassMapperImpl();
		exception.expect(MapperException.class);
		exception.expectMessage("Source class are null");
		prov.createMap(null);
	}
	
	@Test
	public void testSourceNotHaveTargetClass() throws MapperException{
		ClassMapper prov = new ClassMapperImpl();
		exception.expect(MapperException.class);
		exception.expectMessage("Source class not contain annotation @ClassTarge");
		prov.createMap(classexamples.bad.FA.class);
	}

}
