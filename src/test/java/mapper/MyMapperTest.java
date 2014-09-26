package mapper;

import static org.junit.Assert.*;
import mapper.datatransfer.DataTransfer;
import mapper.datatransfer.DataTransferException;
import mapper.datatransfer.DataTransferImpl;
import mapper.mapping.ClassMapperImpl;
import mapper.mapping.MappingException;

import org.junit.Before;
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

	FromClass sourceObject;
	Mapper mapper;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock
	private ClassMapperImpl mockMapProvider = mock(ClassMapperImpl.class);
	@Mock
	private DataTransfer mockDoMap = mock(DataTransferImpl.class);
	@Mock
	private FromClass mockSource = mock(FromClass.class);
	@Mock
	private ToClass mockTo = mock(ToClass.class);

	@Before
	public void setUp() {
		mapper = new MyMapper();
		sourceObject = initSourceObject();
	}

	@Test(expected = MappingException.class)
	public void testProviderIsNull() throws MappingException {
		mapper.prepareMap(mockSource.getClass());
	}

	@Test(expected = MappingException.class)
	public void testNoEqualsMapAndObjectClasses() throws MappingException,
			DataTransferException {
		mapper.setMapProvider(new ClassMapperImpl());
		mapper.setTransferProvider(new DataTransferImpl());
		mapper.map(mockSource, mockTo);
	}
	
	@Test
	public void testMap() throws MappingException, DataTransferException {
		mapper.setMapProvider(new ClassMapperImpl());
		mapper.setTransferProvider(new DataTransferImpl());
		mapper.prepareMap(FromClass.class);
		ToClass result = null;
		assertNotNull("Result is null",
				(result = (ToClass) mapper.map(sourceObject, new ToClass())));
		assertEquals(result.userId, sourceObject.getId());
		assertEquals(result.userLastName, sourceObject.lastName);
		assertEquals(result.userName, sourceObject.name);
		assertEquals(result.ta.dNumber, sourceObject.fa.number);
	}

	@Test
	public void testMapTargetNull() throws MappingException,
			DataTransferException {
		mapper.setMapProvider(new ClassMapperImpl());
		mapper.setTransferProvider(new DataTransferImpl());
		mapper.prepareMap(FromClass.class);
		ToClass result = null;
		assertNotNull("Result is null",
				(result = (ToClass) mapper.map(sourceObject, null)));
		assertEquals(result.userId, sourceObject.getId());
		assertEquals(result.userLastName, sourceObject.lastName);
		assertEquals(result.userName, sourceObject.name);
		assertEquals(result.ta.dNumber, sourceObject.fa.number);
	}

	@Test
	public void testNotNullMapProviderWithoutItInit() {
		assertNull("Map provider not initialize, but it is not null",
				mapper.getMapProvider());
	}

	@Test
	public void testNotNullTransferProviderWithoutItInit() {
		assertNull("Transfer provider not initialize, but it is not null",
				mapper.getTransferProvider());
	}

	@Test
	public void testPrepareMap() throws MappingException {
		mapper.setMapProvider(mockMapProvider);
		mapper.prepareMap(FromClass.class);
		verify(mockMapProvider, times(1)).createMap(FromClass.class);
	}

	@Test
	public void testPrepareMapOnReal() throws MappingException {
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
		mapper.setMapProvider(new ClassMapperImpl());
		mapper.setTransferProvider(new DataTransferImpl());
		mapper.map(mockSource, mockTo);
	}
	
	
	private FromClass initSourceObject(){
		FromClass fromObject = new FromClass();
		fromObject.setId("rootId");
		fromObject.name = "Test";
		fromObject.lastName = "Elephant";
		fromObject.fa = new FA();
		fromObject.fa.setId("fa.id");
		fromObject.fa.number = 100;
		return fromObject;
	}

}
