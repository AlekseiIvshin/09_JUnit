package mapper;

import static org.junit.Assert.*;
import mapper.datagetter.DoMap;
import mapper.datagetter.DoMapClass;
import mapper.mapclass.MapProvider;
import mapper.mapclass.MapProviderImpl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;

import classexamples.good.FA;
import classexamples.good.FromClass;
import classexamples.good.ToClass;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class MyMapperTest {
	
	FromClass fromObject;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Mock
	MapProvider mockMapProvider = mock(MapProviderImpl.class);
	@Mock
	DoMap mockDoMap = mock(DoMapClass.class);	
	@Mock
	FromClass mockFrom = mock(FromClass.class);
	@Mock
	ToClass mockTo = mock(ToClass.class);
	
	@Before
	public void setUp(){
		fromObject = new FromClass();
		fromObject.setId("rootId");
		fromObject.name = "Test";
		fromObject.lastName = "Elephant";
		fromObject.fa = new FA();
		fromObject.fa.setId("fa.id");
		fromObject.fa.number = 100;
	}
	
	@Test(expected = MapperException.class)
	public void testProviderIsNull() throws MapperException{
//		exception.expect(MapperException.class);
//		exception.expectMessage("Provider is null");
		Mapper mapper = new MyMapper();
		mapper.prepareMap(mockFrom.getClass());
	}
	
	@Test(expected = MapperException.class)
	public void testNoEqualsMapAndObjectClasses() throws MapperException{
		Mapper mapper = new MyMapper();
		mapper.setMapProvider(new MapProviderImpl());
		mapper.setTransferProvider(new DoMapClass());
		mapper.map(Mockito.any(), Mockito.any());
	}
	
	@Test
	public void testMap() throws MapperException {
		Mapper mapper = new MyMapper();
		mapper.setMapProvider(new MapProviderImpl());
		mapper.setTransferProvider(new DoMapClass());
		mapper.prepareMap(FromClass.class);
		ToClass result = null;
		assertNotNull("Result is null",(result = (ToClass) mapper.map(fromObject, new ToClass())));
		assertEquals(result.userId, fromObject.getId());
		assertEquals(result.userLastName, fromObject.lastName);
		assertEquals(result.userName, fromObject.name);
	}
	
	
	
	@Test
	public void testPrepareMap() throws MapperException{
		Mapper mapper = new MyMapper();
		mapper.setMapProvider(mockMapProvider);
		mapper.prepareMap(FromClass.class);
		verify(mockMapProvider, times(1)).createMap(FromClass.class);
	}

	@Test
	public void testMissingMap() throws MapperException {
		exception.expect(MapperException.class);
		exception.expectMessage("Map of class is missing");
		Mapper mapper = new MyMapper();
		mapper.setMapProvider(new MapProviderImpl());
		mapper.setTransferProvider(new DoMapClass());
		mapper.map(mockFrom, mockTo);
	}
	
	@Ignore
	@Test
	public void testMapperExceptionNoClassTargetAnnotation() throws MapperException{
		classexamples.bad.FromClass fromObjectBad = new classexamples.bad.FromClass();
		Mapper mapper = new MyMapper();
		exception.expect(MapperException.class);
		exception.expectMessage("Can't find target 'ClassTarget' annotation");
		mapper.map(fromObjectBad, new classexamples.bad.ToClass());
	}
	
	@Ignore
	@Test
	public void testMapperExceptionNoFieldInTargetClass(){
		fail("No field");
	}

	@Ignore
	@Test
	public void testMapperExceptionNoFieldTypesEquals(){
		fail("No field type equals");
	}

	@Ignore
	@Test
	public void testMapperExceptionNoSetterMethod(){
		fail("No setter method");
	}

	@Ignore
	@Test
	public void testMapperExceptionNoGetterMethod(){
		fail("No getter method");
	}

	@Ignore
	@Test
	public void testMapperExceptionCantSetValue(){
		fail("Can't set value");
	}

	@Ignore
	@Test
	public void testMapperExceptionCantGetValue(){
		fail("Can't get value");
	}
}
