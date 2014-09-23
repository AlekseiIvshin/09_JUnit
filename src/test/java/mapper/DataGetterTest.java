package mapper;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import classexamples.good.FA;
import classexamples.good.FromClass;
import classexamples.good.ToClass;

public class DataGetterTest {

	FromClass fromObject;
	
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
	public void testGetData() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, MapperException, NoSuchFieldException, SecurityException {
		Field f = getFieldByName("id");
		assertNotNull("Field is null", DataGetter.getData(f, fromObject));
	}
	
	@Test(expected = MapperException.class)
	public void testExceptionIsThrown() throws NoSuchFieldException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MapperException{
		Field f = getFieldByName("anyField");
		DataGetter.getData(f, fromObject);
	}

	private Field getFieldByName(String name){
		Field[] fields = fromObject.getClass().getDeclaredFields();
		for(Field f: fields){
			if(f.getName().equals(name)){
				return f;
			}
		}
		return null;
	}
}
