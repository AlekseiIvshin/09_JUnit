package mapper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import classexamples.good.FA;
import classexamples.good.FromClass;

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
		assertNotNull("Result is null", mapper.map(fromObject));
	}
	
	@Test
	public void testPrepareMap() throws MapperException{
		Mapper mapper = new MyMapper();
		mapper.prepareMap(FromClass.class);
	}
	
	@Test
	public void testMapperExceptionNoClassTargetAnnotation() throws MapperException{
		classexamples.bad.FromClass fromObjectBad = new classexamples.bad.FromClass();
		Mapper mapper = new MyMapper();
		exception.expect(MapperException.class);
		exception.expectMessage("Can't find target 'ClassTarget' annotation");
		mapper.map(fromObjectBad);
	}
	
	@Test
	public void testMapperExceptionNoFieldInTargetClass(){
		fail("No field");
	}
	
	@Test
	public void testMapperExceptionNoFieldTypesEquals(){
		fail("No field type equals");
	}
	
	@Test
	public void testMapperExceptionNoSetterMethod(){
		fail("No setter method");
	}

	@Test
	public void testMapperExceptionNoGetterMethod(){
		fail("No getter method");
	}
	
	@Test
	public void testMapperExceptionCantSetValue(){
		fail("Can't set value");
	}
	
	@Test
	public void testMapperExceptionCantGetValue(){
		fail("Can't get value");
	}
}
