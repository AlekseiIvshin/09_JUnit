package mapper.datatransfer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Random;

import mapper.mapitems.ClassItem;
import mapper.mapitems.FieldItem;
import mapper.mapitems.MapItem;
import mapper.mapitems.RootItem;
import mapper.mapping.ClassMapperImpl;
import mapper.mapping.MappingException;

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
	
	MapItem workedItem;
	
	DataTransferImpl mapClass;
	
	FromClass sourceObject;
	ToClass targetObject;
	
	ClassItem mapFromClassToClass; 
	
	@Before
	public void setUp() throws DataTransferException, MappingException{
		mapClass = new DataTransferImpl();
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
		
		Random rnd = new Random();
		Iterator<MapItem> iter = mapFromClassToClass.getClassFields().iterator();
		int rnMax = rnd.nextInt(mapFromClassToClass.getClassFields().size());
		int i=0;
		while(iter.hasNext() && i<rnMax){
			i++;
			workedItem = iter.next();
		}
	}
	
	@Test
	public void testOnCantCreateTargetInstance() throws DataTransferException, MappingException{
		exception.expect(DataTransferException.class);
		exception.expectMessage("Wrong classes of parameters");
		ClassMapperImpl mapper = new ClassMapperImpl();
		mapper.createMap(classexamples.bad.coruptedOnTargetCreate.FromClass.class);
		mapClass.map(mockSource, null, new RootItem<MapItem>(mapper.getMap()));		
	}
	
	@Test(expected = DataTransferException.class)
	public void testMapNullSourceObject() throws DataTransferException {
		mapClass.map(null, mockTo, new RootItem<MapItem>(mockMapClass));
	}
	
	@Test
	public void testNullRootMap() throws DataTransferException{
		exception.expect(DataTransferException.class);
		exception.expectMessage("Map is null");
		mapClass.map(mockSource,mockTo,null);
	}
	
	@Test
	public void testNullMap() throws DataTransferException{
		exception.expect(DataTransferException.class);
		exception.expectMessage("Map is null");
		mapClass.map(mockSource,mockTo,new RootItem<MapItem>());
	}
	

	@Test
	public void testMapOnNullSource() throws DataTransferException{
		exception.expect(DataTransferException.class);
		exception.expectMessage("Source or target fields are null");
		mapClass.mapItem(null, mockTo, mockMapClass);
	}
	

	@Test
	public void testMapOnNullTarget() throws DataTransferException{
		exception.expect(DataTransferException.class);
		exception.expectMessage("Source or target fields are null");
		mapClass.mapItem(mockSource, null, mockMapClass);
	}

	@Test
	public void testMapUseFieldList() throws DataTransferException{
		when(mockMapClass.sourceEquals(mockSource.getClass())).thenReturn(true);
		when(mockMapClass.targetEquals(mockTo.getClass())).thenReturn(true);
		mapClass.map(mockSource,mockTo,new RootItem<MapItem>(mockMapClass));
		verify(mockMapClass, times(1)).getClassFields();
	}
	

	@Test
	public void testDataTransfer() throws DataTransferException{
		ToClass result = null;
		assertNotNull("Result is null",
				(result = (ToClass) mapClass.map(sourceObject, new ToClass(), new RootItem<MapItem>(mapFromClassToClass))));
		assertEquals(result.userId, sourceObject.getId());
		assertEquals(result.userLastName, sourceObject.lastName);
		assertEquals(result.userName, sourceObject.name);
	}
	
	
	@Test
	public void testSourceClassesNotEquals() throws DataTransferException{
		exception.expect(DataTransferException.class);
		exception.expectMessage("Wrong classes of parameters");
		mapClass.map(mockTo,mockSource,new RootItem<MapItem>(mapFromClassToClass));
	}


	@Test
	public void testGetDateWithNullSource() throws DataTransferException{
		exception.expect(DataTransferException.class);
		exception.expectMessage("Source object is null");
		mapClass.getData(workedItem, null);
	}
	
	@Test
	public void testGetDateWithNullMap() throws DataTransferException{
		exception.expect(DataTransferException.class);
		exception.expectMessage("Map is null");
		mapClass.getData(null, mockSource);
	}
	

	@Test
	public void testGetDateWithNullMapGetSourceField() throws DataTransferException{
		exception.expect(DataTransferException.class);
		exception.expectMessage("Source field is null");
		when(mockMapField.getSourceField()).thenReturn(null);
		mapClass.getData(mockMapField, mockSource);
	}
	
	@Test
	public void testSetDateWithNullTarget() throws DataTransferException{
		exception.expect(DataTransferException.class);
		exception.expectMessage("Target object is null");
		mapClass.setData(workedItem,null,mockSource);
	}
	@Test
	public void testSetDateWithNullValue() throws DataTransferException{
		mapClass.setData(workedItem,mockTo,null);
	}
	@Test
	public void testSetDateWithNullMap() throws DataTransferException{
		exception.expect(DataTransferException.class);
		exception.expectMessage("Map is null");
		mapClass.setData(null,mockTo,mockSource);
	}
	
	@Test
	public void testSetDateWithNullMapGetTargetField() throws DataTransferException{
		exception.expect(DataTransferException.class);
		exception.expectMessage("Source field is null");
		when(mockMapField.getTargetField()).thenReturn(null);
		mapClass.getData(mockMapField, mockSource);
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
