package mapper.datagetter;

import static org.junit.Assert.*;
import javafx.beans.binding.MapExpression;
import jdk.internal.dynalink.support.ClassMap;
import mapper.MapperException;
import mapper.mapclass.MapClass;
import mapper.mapclass.MapField;

import org.junit.Before;
import org.junit.Test;

import classexamples.good.FA;
import classexamples.good.FromClass;
import classexamples.good.ToClass;

public class DataGetterTest {

	FromClass goodFrom;
	MapClass classMap;
	
	@Before
	public void setUp() throws MapperException{
		goodFrom = new FromClass();
		goodFrom.setId("rootId");
		goodFrom.name = "Test";
		goodFrom.lastName = "Elephant";
		goodFrom.fa = new FA();
		goodFrom.fa.setId("fa.id");
		goodFrom.fa.number = 100;
		
		classMap = new MapClass();
		classMap.setTargetClass(ToClass.class);
		classMap.setFromClass(FromClass.class);
		classMap.getMap();
	}
	
	@Test(expected = MapperException.class)
	public void testNotNullPointer() throws MapperException {
		DataGetter.getData(null, new Object());
	}
	
	
	
	@Test
	public void testAllRight() throws MapperException{
		for(MapField f: classMap.getFields()){
			Object value = DataGetter.getData(f, goodFrom);
		}
	}

}
