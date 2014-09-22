package car.dao.modifiacation;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import car.dao.model.Model;

/**
 * Entity model of car modification.
 * 
 * @author Aleksei_Ivshin
 *
 */
@StaticMetamodel(Modification.class)
public class Modification_ {

	/**
	 * Modification id.
	 */
	public static SingularAttribute<Modification, Integer> id;
	/**
	 * Modification name.
	 */
	public static SingularAttribute<Modification, String> name;
	/**
	 * Modification model.
	 */
	public static SingularAttribute<Modification, Model> model;
}
