package mapper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import classexamples.good.FA;
import classexamples.good.FromClass;
import classexamples.good.ToClass;

@RunWith(JUnit4.class)
public class MyMapperTest {
	
	FromClass fromObject;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
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
	@Test
	public void testMap() throws MapperException {
		Mapper mapper = new MyMapper();
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
		mapper.prepareMap(FromClass.class);
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
