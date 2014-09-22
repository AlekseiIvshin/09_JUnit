package merchant.service;

import merchant.dao.Merchant;
import merchant.dao.MerchantDAOImpl;
import merchant.domen.MerchantDomain;

import common.service.DomainServiceImpl;

/**
 * Merchant service implementation.
 * 
 * @author Aleksei_Ivshin
 *
 */
public class MerchantServiceImpl
		extends
		DomainServiceImpl
				<MerchantDomain, Integer, Merchant, Integer, MerchantDAOImpl>
		implements MerchantService {

	/**
	 * Default constructor.
	 */
	public MerchantServiceImpl() {
		super();
		dao = new MerchantDAOImpl(entityManager);
	}

}
