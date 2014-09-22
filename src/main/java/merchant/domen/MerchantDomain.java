package merchant.domen;

/**
 * Merchant domain interface.
 * @author Aleksei_Ivshin
 *
 */
public interface MerchantDomain {

	
	/**
	 * Get merchant id.
	 * 
	 * @return id
	 */
	int getId();

	/**
	 * Get merchant name.
	 * 
	 * @return name
	 */
	String getName();

	/**
	 * Set merchant name.
	 * 
	 * @param name
	 *            name
	 */
	void setName(String name);

	/**
	 * Get merchant surname.
	 * 
	 * @return surname
	 */
	String getSurname();

	/**
	 * Set merchant surname.
	 * 
	 * @param surname
	 *            surname
	 */
	void setSurname(String surname);

	/**
	 * Get merchant patronymic.
	 * 
	 * @return patronymic
	 */
	String getPatronymic();

	/**
	 * Set merchant patronymic.
	 * 
	 * @param patronymic
	 *            patronymic
	 */
}
