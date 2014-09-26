package mapper.mapping;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Set;

import mapper.mapitems.MapItem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import classexamples.good.FromClass;
import static org.mockito.Mockito.*;

public class ClassMapperImplTest {


	ClassMapperImpl prov;
	
	@Mock
	ClassMapperImpl mockMapper = mock(ClassMapperImpl.class);
	@Mock
	FromClass mockSourceClass = mock(FromClass.class);

	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp(){
		 prov = new ClassMapperImpl();
	}
	
	@Test
	public void testCreateMap() throws MappingException {
		prov.createMap(FromClass.class);
		assertNotNull("Root map object not created", prov.getMap());
		assertNotNull("Fields set not created", prov.getMap().getClassFields());
	}

	@Test
	public void testGetMap() {
		assertTrue("Map is created but map not readed", prov.getMap().isEmpty());
		assertTrue("Map is created but map not readed", prov.getMap()
				.getClassFields().isEmpty());
	}

	@Test
	public void testGetFields() {
		assertNotNull(prov.getMap().getClassFields());
	}

	@Test
	public void testGetFieldsOnEmpty() {
		assertEquals(prov.getMap().getClassFields().size(), 0);
	}

	@Test
	public void testOnCheckSelfContain() throws MappingException {		
		doCallRealMethod().when(mockMapper).createMap(FromClass.class);
		doNothing().when(mockMapper).setDataToMap((Class<?>)any(), (Class<?>)any());
		when(mockMapper.getFieldsItems((Class<?>) any(), (Class<?>)any())).thenReturn((Set<MapItem>) any());
		mockMapper.createMap(FromClass.class);
		verify(mockMapper, atLeastOnce()).classContainItself(FromClass.class);
	}
	

	@Test
	public void testOnCheckSelfContainIfItTrue() throws MappingException {	
		exception.expect(MappingException.class);
		exception.expectMessage("contains itself in fields");	
		doCallRealMethod().when(mockMapper).createMap(FromClass.class);
		doNothing().when(mockMapper).setDataToMap((Class<?>)any(), (Class<?>)any());
		when(mockMapper.classContainItself((Class<?>) any())).thenReturn(true);
		mockMapper.createMap(FromClass.class);
	}

	@Test
	public void testNullSourceClass() throws MappingException {
		exception.expect(MappingException.class);
		exception.expectMessage("Source class are null");
		prov.createMap(null);
	}

	@Test
	public void testSourceNotHaveTargetClass() throws MappingException {
		exception.expect(MappingException.class);
		exception
				.expectMessage("Source class not contain annotation @ClassTarge");
		prov.createMap(classexamples.bad.withOutClassTargetAnnotation.FA.class);
	}

}
