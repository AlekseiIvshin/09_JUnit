package mapper.datagetter;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import mapper.MapperException;
import mapper.mapclass.MapClass;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;

import classexamples.good.FromClass;
import classexamples.good.ToClass;

@RunWith(JUnit4.class)
public class DoMapClassTest {

	@Mock
	MapClass mockMapClass = mock(MapClass.class);
	@Mock
	FromClass mockSource = mock(FromClass.class);
	@Mock
	ToClass mockTo = mock(ToClass.class);
	
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
	public void testGetData() {
		fail("Not yet implemented");
	}

}
