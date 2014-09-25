package mapper;

import mapper.datatransfer.DataTransferImplTest;
import mapper.mapping.ClassMapperImplTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MyMapperTest.class, ClassMapperImplTest.class,
		DataTransferImplTest.class })
public class ClassesSuite {

}
