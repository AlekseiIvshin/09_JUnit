package mapper.mapitems;

import static org.junit.Assert.*;
import mapper.mapping.MappingException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ClassItemTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Test
	public void testAddFields() throws MappingException {

		exception.expect(MappingException.class);
		exception.expectMessage("Field already mapped");
		
		ClassItem item = new ClassItem();
		MapItem mapItem = new FieldItem();
		item.addFields(mapItem);
		item.addFields(mapItem);
	}

}
