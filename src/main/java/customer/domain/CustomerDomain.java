package customer.domain;

import java.util.Date;

/**
 * Customer domain interface.
 * 
 * @author Aleksei_Ivshin
 *
 */
public interface CustomerDomain {

	/**
	 * Get customer id.
	 * 
	 * @return id
	 */
	int getId();

	/**
	 * Get customer name.
	 * 
	 * @return name
	 */
	String getName();

	/**
	 * Set customer name.
	 * 
	 * @param name
	 *            name
	 */
	void setName(String name);

	/**
	 * Get customer surname.
	 * 
	 * @return surname
	 */
	String getSurname();

	/**
	 * Set customer surname.
	 * 
	 * @param surname
	 *            surname
	 */
	void setSurname(String surname);

	/**
	 * Get customer patronymic.
	 * 
	 * @return patronymic
	 */
	String getPatronymic();

	/**
	 * Set customer patronymic.
	 * 
	 * @param patronymic
	 *            patronymic
	 */
	void setPatronymic(String patronymic);

	/**
	 * Get customer passport number.
	 * 
	 * @return passport number
	 */
	String getPassportNumber();

	/**
	 * Set customer passport number.
	 * 
	 * @param passportNumber
	 *            passport number
	 */
	void setPassportNumber(String passportNumber);

	/**
	 * Get customer passport series.
	 * 
	 * @return passport series
	 */
	String getPassportSeries();

	/**
	 * Set customer passport series.
	 * 
	 * @param passportSeries
	 *            passport series
	 */
	void setPassportSeries(String passportSeries);

	/**
	 * Get customer birth date.
	 * 
	 * @return birth date
	 */
	Date getBirthDate();

	/**
	 * Set customer birth date.
	 * 
	 * @param birthDate
	 *            birth date
	 */
	void setBirthDate(Date birthDate);
}
