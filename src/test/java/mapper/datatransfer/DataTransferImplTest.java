package mapper.datatransfer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import mapper.MapperException;
import mapper.mapitems.ClassItem;
import mapper.mapitems.FieldItem;
import mapper.mapitems.MapItem;
import mapper.mapping.ClassMapperImpl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;

import classexamples.good.FA;
import classexamples.good.FromClass;
import classexamples.good.ToClass;

public class DataTransferImplTest {


	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Mock
	ClassItem mockMapClass = mock(ClassItem.class);
	@Mock
	FromClass mockSource = mock(FromClass.class);
	@Mock
	ToClass mockTo = mock(ToClass.class);
	@Mock
	FieldItem mockMapField = mock(FieldItem.class);
	
	FromClass sourceObject;
	ToClass targetObject;
	
	ClassItem mapFromClassToClass; 
	
	@Before
	public void setUp() throws MapperException{
		ClassMapperImpl mapper = new ClassMapperImpl();
		mapper.createMap(FromClass.class);
		mapFromClassToClass = mapper.getMap();

		sourceObject = new FromClass();
		sourceObject.setId("rootId");
		sourceObject.name = "Test";
		sourceObject.lastName = "Elephant";
		sourceObject.fa = new FA();
		sourceObject.fa.setId("fa.id");
		sourceObject.fa.number = 100;
	}
	
	@Test(expected = MapperException.class)
	public void testMapNullSourceObject() throws MapperException {
		DataTransfer mapClass = new DataTransferImpl();
		mapClass.map(null, mockTo, mockMapClass);
	}
	
	@Test
	public void testNullMap() throws MapperException{
		exception.expect(MapperException.class);
		exception.expectMessage("Map is null");
		DataTransfer mapClass = new DataTransferImpl();
		mapClass.map(mockSource,mockTo,null);
	}
	

	@Test
	public void testMapUseFieldList() throws MapperException{
		DataTransfer mapClass = new DataTransferImpl();
		when(mockMapClass.sourceEquals(mockSource.getClass())).thenReturn(true);
		when(mockMapClass.targetEquals(mockTo.getClass())).thenReturn(true);
		mapClass.map(mockSource,mockTo,mockMapClass);
		verify(mockMapClass, times(1)).getClassFields();
	}
	

	@Test
	public void testDataTransfer() throws MapperException{
		DataTransfer mapClass = new DataTransferImpl();		
		ToClass result = null;
		assertNotNull("Result is null",
				(result = (ToClass) mapClass.map(sourceObject, new ToClass(), mapFromClassToClass)));
		assertEquals(result.userId, sourceObject.getId());
		assertEquals(result.userLastName, sourceObject.lastName);
		assertEquals(result.userName, sourceObject.name);
	}
	
	@Ignore
	@Test
	public void testMapNullTargetObject() throws MapperException, InstantiationException, IllegalAccessException {
		DataTransfer mapClass = new DataTransferImpl();
		
		Mockito.<Class<?>>when(mockMapClass.getTargetClass()).thenReturn(ToClass.class);
		Mockito.<Class<?>>when(mockMapClass.getSourceClass()).thenReturn(FromClass.class);
		Mockito.<ToClass>when((ToClass)mockMapClass.getTargetClass().newInstance()).thenReturn(new ToClass());
		
		mapClass.map(new FromClass(), null, mockMapClass);
		verify(mockMapClass,times(1)).getTargetClass().newInstance();
	}
	
	@Test
	public void testSourceClassesNotEquals() throws MapperException{
		exception.expect(MapperException.class);
		exception.expectMessage("Wrong classes of parameters");
		DataTransfer mapClass = new DataTransferImpl();
		mapClass.map(mockTo,mockSource,mapFromClassToClass);
	}



	
//	@Test
//	public void testGetDataIfNoGetterNoFieldExpectedNullPointer() throws MapperException, IllegalArgumentException, IllegalAccessException, InstantiationException{
//		DataTransfer mapClass = new DataTransferImpl();
//		when(mockMapField.getGetter()).thenReturn(null);
//		when(mockMapField.getSourceField()).thenReturn(null);
//		mapClass.getData(mockMapField, mockSource);
//		verify(mockMapField,times(1)).getGetter();
//		verify(mockMapField,times(1)).getSourceField();
//	}

}
