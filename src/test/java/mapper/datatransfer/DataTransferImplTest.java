package mapper.datatransfer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import mapper.mapitems.ClassItem;
import mapper.mapitems.FieldItem;
import mapper.mapitems.MapItem;
import mapper.mapitems.RootItem;
import mapper.mapping.ClassMapperImpl;
import mapper.mapping.MappingException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import classexamples.good.FA;
import classexamples.good.FromClass;
import classexamples.good.ToClass;

public class DataTransferImplTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock
	private ClassItem mockMapClass = mock(ClassItem.class);
	@Mock
	private FromClass mockSource = mock(FromClass.class);
	@Mock
	private ToClass mockTo = mock(ToClass.class);
	@Mock
	private FieldItem mockMapField = mock(FieldItem.class);

	private ClassMapperImpl mapper;
	private MapItem workedItem;
	private DataTransferImpl dataTransfer;
	private FromClass sourceObject;
	private ClassItem mapFromClassToClass;

	@Before
	public void setUp() throws DataTransferException, MappingException {
		dataTransfer = new DataTransferImpl();
		mapper = new ClassMapperImpl();
		mapper.createMap(FromClass.class);
		mapFromClassToClass = mapper.getMap();

		sourceObject = initSourceObject();

		workedItem = getRandomItem(mapFromClassToClass.getClassFields());
	}

	@Test
	public void testOnCantCreateTargetInstance() throws DataTransferException,
			MappingException {
		exception.expect(DataTransferException.class);
		exception.expectMessage("Wrong classes of parameters");
		ClassMapperImpl mapper = new ClassMapperImpl();
		mapper.createMap(classexamples.bad.coruptedOnTargetCreate.FromClass.class);
		dataTransfer.map(mockSource, null,
				new RootItem<MapItem>(mapper.getMap()));
	}

	@Test(expected = DataTransferException.class)
	public void testMapNullSourceObject() throws DataTransferException {
		dataTransfer.map(null, mockTo, new RootItem<MapItem>(mockMapClass));
	}

	@Test
	public void testNullRootMap() throws DataTransferException {
		exception.expect(DataTransferException.class);
		exception.expectMessage("Map is null");
		dataTransfer.map(mockSource, mockTo, null);
	}

	@Test
	public void testNullMap() throws DataTransferException {
		exception.expect(DataTransferException.class);
		exception.expectMessage("Map is null");
		dataTransfer.map(mockSource, mockTo, new RootItem<MapItem>());
	}

	@Test
	public void testMapOnNullSource() throws DataTransferException {
		exception.expect(DataTransferException.class);
		exception.expectMessage("Source or target fields are null");
		dataTransfer.mapItem(null, mockTo, mockMapClass);
	}

	@Test
	public void testMapOnNullTarget() throws DataTransferException {
		exception.expect(DataTransferException.class);
		exception.expectMessage("Source or target fields are null");
		dataTransfer.mapItem(mockSource, null, mockMapClass);
	}

	@Test
	public void testMapUseFieldList() throws DataTransferException {
		when(mockMapClass.sourceEquals(mockSource.getClass())).thenReturn(true);
		when(mockMapClass.targetEquals(mockTo.getClass())).thenReturn(true);
		dataTransfer.map(mockSource, mockTo,
				new RootItem<MapItem>(mockMapClass));
		verify(mockMapClass, times(1)).getClassFields();
	}

	@Test
	public void testDataTransfer() throws DataTransferException {
		ToClass result = null;
		assertNotNull(
				"Result is null",
				(result = (ToClass) dataTransfer.map(sourceObject,
						new ToClass(), new RootItem<MapItem>(
								mapFromClassToClass))));
		assertEquals(result.userId, sourceObject.getId());
		assertEquals(result.userLastName, sourceObject.lastName);
		assertEquals(result.userName, sourceObject.name);
	}

	@Test
	public void testSourceClassesNotEquals() throws DataTransferException {
		exception.expect(DataTransferException.class);
		exception.expectMessage("Wrong classes of parameters");
		dataTransfer.map(mockTo, mockSource, new RootItem<MapItem>(
				mapFromClassToClass));
	}

	@Test
	public void testTargetClassesNotEquals() throws DataTransferException {
		exception.expect(DataTransferException.class);
		exception.expectMessage("Wrong classes of parameters");
		dataTransfer.map(sourceObject, mockSource, new RootItem<MapItem>(
				mapFromClassToClass));
	}

	@Test
	public void testGetDateWithNullSource() throws DataTransferException {
		exception.expect(DataTransferException.class);
		exception.expectMessage("Source object is null");
		dataTransfer.getData(workedItem, null);
	}

	@Test
	public void testGetDateWithNullMap() throws DataTransferException {
		exception.expect(DataTransferException.class);
		exception.expectMessage("Map is null");
		dataTransfer.getData(null, mockSource);
	}

	@Test
	public void testGetDateWithNullMapGetSourceField()
			throws DataTransferException {
		exception.expect(DataTransferException.class);
		exception.expectMessage("Source field is null");
		when(mockMapField.getSourceField()).thenReturn(null);
		dataTransfer.getData(mockMapField, mockSource);
	}

	@Test
	public void testSetDateWithNullTarget() throws DataTransferException {
		exception.expect(DataTransferException.class);
		exception.expectMessage("Target object is null");
		dataTransfer.setData(workedItem, null, mockSource);
	}

	@Test
	public void testSetDateWithNullValue() throws DataTransferException {
		dataTransfer.setData(workedItem, mockTo, null);
	}

	@Test
	public void testSetDateWithNullMap() throws DataTransferException {
		exception.expect(DataTransferException.class);
		exception.expectMessage("Map is null");
		dataTransfer.setData(null, mockTo, mockSource);
	}

	@Test
	public void testSetDateWithNullMapGetTargetField()
			throws DataTransferException {
		exception.expect(DataTransferException.class);
		exception.expectMessage("Source field is null");
		when(mockMapField.getTargetField()).thenReturn(null);
		dataTransfer.getData(mockMapField, mockSource);
	}


	private FromClass initSourceObject() {
		FromClass sourceObject = new FromClass();
		sourceObject.setId("rootId");
		sourceObject.name = "Test";
		sourceObject.lastName = "Elephant";
		sourceObject.fa = new FA();
		sourceObject.fa.setId("fa.id");
		sourceObject.fa.number = 100;
		return sourceObject;
	}

	private MapItem getRandomItem(Collection<MapItem> items) {
		List<MapItem> asList = new ArrayList(
				mapFromClassToClass.getClassFields());
		Collections.shuffle(asList);
		return asList.get(0);
	}

}
