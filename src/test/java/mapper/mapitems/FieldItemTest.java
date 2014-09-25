package mapper.mapitems;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class FieldItemTest {

	FieldItem fieldItem;
	
	@Before
	public void setUp(){
		fieldItem = new FieldItem();
	}
	
	@Test
	public void testGetClassFields() {
		assertNull(fieldItem.getClassFields());
	}

	@Test
	public void testSetClassFields() {
		fieldItem.setClassFields(new HashSet<MapItem>());
	}

	@Test
	public void testAddFields() {
		assertNull(fieldItem.getClassFields());
		fieldItem.addFields(new FieldItem());
	}

	@Test
	public void testIsMappedClass() {
		assertFalse(fieldItem.isMappedClass());
	}

}
