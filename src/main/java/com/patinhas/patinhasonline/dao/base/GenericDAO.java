package com.patinhas.patinhasonline.dao.base;

import java.util.List;

import org.hibernate.criterion.Criterion;

// TODO: Auto-generated Javadoc
/**
 * The Interface GenericDAO.
 *
 * @param <T> the generic type
 * @param <ID> the generic type
 */
public interface GenericDAO<T, ID> {

	/**
	 * Find by criteria.
	 *
	 * @param criterion the criterion
	 * @return the list
	 */
	public abstract List<T> findByCriteria(Criterion... criterion);

	/**
	 * Make transient.
	 *
	 * @param entity the entity
	 */
	public abstract void makeTransient(T entity);

	/**
	 * Make persistent.
	 *
	 * @param entity the entity
	 * @return the t
	 */
	public abstract T makePersistent(T entity);

	/**
	 * Find by example.
	 *
	 * @param exampleInstance the example instance
	 * @param excludeProperty the exclude property
	 * @return the list
	 */
	public abstract List<T> findByExample(T exampleInstance, String[] excludeProperty);

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public abstract List<T> findAll();
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the t
	 */
	public abstract T findById(ID id);

	/**
	 * Find unique by property.
	 *
	 * @param property the property
	 * @param value the value
	 * @return the t
	 */
	public abstract T findUniqueByProperty(String property, Object value);

}
