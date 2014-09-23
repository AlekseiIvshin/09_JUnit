package mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import classexamples.FA;
import classexamples.FromClass;

public class Main {

	final static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		logger.info("Start application.");
		Object example = initExampleClass();

		Object myMapper = useMyMapper(example);

		logger.info("*** Compare results ***");
		if (myMapper != null) {
			logger.info("My Mapper Out: " + myMapper.toString());
		} else {
			logger.info("My Mapping has some errors.");
		}

		logger.info("End.");
	}

	private static Object useMyMapper(Object fromClass) {
		logger.info("*** Start use my mapper ***");
		logger.info("In: " + fromClass.toString());
		Mapper m = new MyMapper();
		Object result = null;
		try {
			result = m.map(fromClass);
		} catch (MapperException e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("*** End use my mapper ***");
		return result;
	}

	private static Object initExampleClass() {
		logger.info("*** Init example class ***");
		FromClass example = new FromClass();
		example.setId("rootId");
		example.name = "Test";
		example.lastName = "Elephant";
		example.fa = new FA();
		example.fa.setId("fa.id");
		example.fa.number = 100;
		logger.info(example.toString());
		return example;
	}

}
