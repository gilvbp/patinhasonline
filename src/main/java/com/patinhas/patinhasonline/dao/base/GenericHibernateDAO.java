package com.patinhas.patinhasonline.dao.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;



public abstract class GenericHibernateDAO<T, ID extends Serializable>
		implements GenericDAO<T, ID> {

	private Class<T> persistentClass;
	@Inject
	public SessionFactory sessionFactory;
	private Session session;

	public GenericHibernateDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	public void setSession(Session s) {
		this.session = s;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		if (sessionFactory.getCurrentSession() == null)
			throw new IllegalStateException(
					"Session has not been set on DAO before usage");
		return session;
	}

	public Session retornaSessao() {
		return this.getSessionFactory().getCurrentSession();
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	@Transactional
	@Override
	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		T entity;

		entity = (T) retornaSessao().createCriteria(getPersistentClass()).add(
				Restrictions.idEq(id)).uniqueResult();
		Hibernate.initialize(entity);

		return entity;
	}

	@Transactional
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return findByCriteria();
	}
	@Transactional
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
		Criteria crit = retornaSessao().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}
	@Transactional
	@Override
	@SuppressWarnings("unchecked")
	public T makePersistent(T entity) {
		retornaSessao().saveOrUpdate(entity);
		retornaSessao().flush();
		return entity;
	}
	@Transactional
	@Override
	public void makeTransient(T entity) {
		retornaSessao().delete(entity);
		retornaSessao().flush();
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@Transactional
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(Criterion... criterion) {
		Criteria crit = retornaSessao().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public T findUniqueByProperty(String property, Object value) {
		
		Criteria criteria = retornaSessao()
				.createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq(property, value));
		//criteria.setProjection((Projections.property("nomeGerencia")));
		
		return (T) criteria.uniqueResult();
	}
}
