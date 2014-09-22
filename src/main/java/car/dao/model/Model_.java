package car.dao.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import car.dao.mark.Mark;

/**
 * Entity model for car model.
 * @author Aleksei_Ivshin
 *
 */
@StaticMetamodel(Model.class)
public class Model_ {

	public static SingularAttribute<Model, Integer> id;
	public static SingularAttribute<Model, String> name;
	public static SingularAttribute<Model, Mark> mark;
}
