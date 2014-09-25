package mapper;

import mapper.datatransfer.DataTransferImplTest;
import mapper.mapitems.ClassItemTest;
import mapper.mapitems.FieldItemTest;
import mapper.mapping.ClassMapperImplTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MyMapperTest.class, ClassMapperImplTest.class,
		DataTransferImplTest.class, ClassItemTest.class, FieldItemTest.class })
public class ClassesSuite {

}
