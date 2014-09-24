package mapper.datagetter;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import mapper.MapperException;
import mapper.mapclass.MapClass;
import mapper.mapclass.MapField;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;

import classexamples.good.FromClass;
import classexamples.good.ToClass;

@RunWith(JUnit4.class)
public class DoMapClassTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Mock
	MapClass mockMapClass = mock(MapClass.class);
	@Mock
	FromClass mockSource = mock(FromClass.class);
	@Mock
	ToClass mockTo = mock(ToClass.class);
	@Mock
	MapField mockMapField = mock(MapField.class);
	
	@Test(expected = MapperException.class)
	public void testMapNullSourceObject() throws MapperException {
		DoMapClass mapClass = new DoMapClass();
		mapClass.map(null, mockTo, mockMapClass);
	}
	

	@Test
	public void testMapNullTargetObject() throws MapperException {
		DoMapClass mapClass = new DoMapClass();
		
		Mockito.<Class<?>>when(mockMapClass.getTargetClass()).thenReturn(ToClass.class);
		Mockito.<Class<?>>when(mockMapClass.getSourceClass()).thenReturn(FromClass.class);
		when(mockMapClass.getNewInstanceOfTarget()).thenReturn(new ToClass());
		
		mapClass.map(new FromClass(), null, mockMapClass);
		verify(mockMapClass,times(1)).getNewInstanceOfTarget();
	}

	@Test
	public void testMapObjectObjectMapField() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetData() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDataFromNullField() throws MapperException {

		exception.expect(MapperException.class);
		exception.expectMessage("Field is null");
		DoMapClass mapClass = new DoMapClass();
		mapClass.getData(null, mockSource);
	}

	@Test
	public void testGetDataFromNullSoruceField() throws MapperException {

		exception.expect(MapperException.class);
		exception.expectMessage("Source field is null");
		when(mockMapField.getGetter()).thenReturn(null);
		DoMapClass mapClass = new DoMapClass();
		mapClass.getData(mockMapField, mockSource);
	}
	
	@Test
	public void testGetDataIfNoGetterNoFieldExpectedNullPointer() throws MapperException, IllegalArgumentException, IllegalAccessException, InstantiationException{
		DoMapClass mapClass = new DoMapClass();
		when(mockMapField.getGetter()).thenReturn(null);
		when(mockMapField.getSourceField()).thenReturn(null);
		mapClass.getData(mockMapField, mockSource);
		verify(mockMapField,times(1)).getGetter();
		verify(mockMapField,times(1)).getSourceField();
	}

}
