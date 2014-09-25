package mapper;

import static org.junit.Assert.*;
import mapper.datatransfer.DataTransfer;
import mapper.datatransfer.DataTransferException;
import mapper.datatransfer.DataTransferImpl;
import mapper.mapping.ClassMapper;
import mapper.mapping.ClassMapperImpl;
import mapper.mapping.MappingException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

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
	ClassMapper mockMapProvider = mock(ClassMapperImpl.class);
	@Mock
	DataTransfer mockDoMap = mock(DataTransferImpl.class);
	@Mock
	FromClass mockFrom = mock(FromClass.class);
	@Mock
	ToClass mockTo = mock(ToClass.class);

	@Before
	public void setUp() {
		fromObject = new FromClass();
		fromObject.setId("rootId");
		fromObject.name = "Test";
		fromObject.lastName = "Elephant";
		fromObject.fa = new FA();
		fromObject.fa.setId("fa.id");
		fromObject.fa.number = 100;
	}

	@Test(expected = MappingException.class)
	public void testProviderIsNull() throws MappingException {
		// exception.expect(MapperException.class);
		// exception.expectMessage("Provider is null");
		Mapper mapper = new MyMapper();
		mapper.prepareMap(mockFrom.getClass());
	}

	@Test(expected = MappingException.class)
	public void testNoEqualsMapAndObjectClasses() throws MappingException, DataTransferException {
		Mapper mapper = new MyMapper();
		mapper.setMapProvider(new ClassMapperImpl());
		mapper.setTransferProvider(new DataTransferImpl());
		mapper.map(mockFrom, mockTo);
	}

	@Test
	public void testMap() throws MappingException, DataTransferException {
		Mapper mapper = new MyMapper();
		mapper.setMapProvider(new ClassMapperImpl());
		mapper.setTransferProvider(new DataTransferImpl());
		mapper.prepareMap(FromClass.class);
		ToClass result = null;
		assertNotNull("Result is null",
				(result = (ToClass) mapper.map(fromObject, new ToClass())));
		assertEquals(result.userId, fromObject.getId());
		assertEquals(result.userLastName, fromObject.lastName);
		assertEquals(result.userName, fromObject.name);
		assertEquals(result.ta.dNumber, fromObject.fa.number);
	}
	
	@Test
	public void testMapTargetNull() throws MappingException, DataTransferException {
		Mapper mapper = new MyMapper();
		mapper.setMapProvider(new ClassMapperImpl());
		mapper.setTransferProvider(new DataTransferImpl());
		mapper.prepareMap(FromClass.class);
		ToClass result = null;
		assertNotNull("Result is null",
				(result = (ToClass) mapper.map(fromObject, null)));
		assertEquals(result.userId, fromObject.getId());
		assertEquals(result.userLastName, fromObject.lastName);
		assertEquals(result.userName, fromObject.name);
		assertEquals(result.ta.dNumber, fromObject.fa.number);
	}

	@Test
	public void testNotNullMapProviderWithoutItInit() {
		Mapper mapper = new MyMapper();
		assertNull("Map provider not initialize, but it is not null",
				mapper.getMapProvider());
	}

	@Test
	public void testNotNullTransferProviderWithoutItInit() {
		Mapper mapper = new MyMapper();
		assertNull("Transfer provider not initialize, but it is not null",
				mapper.getTransferProvider());
	}

	@Test
	public void testPrepareMap() throws MappingException {
		Mapper mapper = new MyMapper();
		mapper.setMapProvider(mockMapProvider);
		mapper.prepareMap(FromClass.class);
		verify(mockMapProvider, times(1)).createMap(FromClass.class);
	}

	@Test
	public void testPrepareMapOnReal() throws MappingException {
		Mapper mapper = new MyMapper();
		mapper.setMapProvider(new ClassMapperImpl());
		mapper.prepareMap(FromClass.class);
		assertEquals(mapper.getMapProvider().getMap().getSourceClass(),
				FromClass.class);
		assertEquals(mapper.getMapProvider().getMap().getTargetClass(),
				ToClass.class);
	}

	@Test
	public void testMissingMap() throws MappingException, DataTransferException {
		exception.expect(MappingException.class);
		exception.expectMessage("Map of class is missing");
		Mapper mapper = new MyMapper();
		mapper.setMapProvider(new ClassMapperImpl());
		mapper.setTransferProvider(new DataTransferImpl());
		mapper.map(mockFrom, mockTo);
	}

}
