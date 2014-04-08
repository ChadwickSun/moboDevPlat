package com.cy.cms.dao.impl;



import com.cy.cms.dao.GenericDao;
import com.cy.cms.util.iBatisDaoUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.util.ClassUtils;


import java.io.Serializable;
import java.util.List;


public class GenericDaoImpl<T, PK extends Serializable> extends SqlMapClientDaoSupport implements GenericDao<T, PK> {
    protected final Log log = LogFactory.getLog(getClass());
    private Class<T> persistentClass;


    /**
     * Constructor that takes in a class to see which type of entity to persist
     *
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoImpl(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return getSqlMapClientTemplate().queryForList(
                iBatisDaoUtils.getSelectQuery(ClassUtils.getShortName(this.persistentClass)), null);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        return (T) getSqlMapClientTemplate().queryForObject(iBatisDaoUtils.getFindQuery(ClassUtils.getShortName(this.persistentClass)), id);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        T object = (T) getSqlMapClientTemplate().queryForObject(
                iBatisDaoUtils.getFindQuery(ClassUtils.getShortName(this.persistentClass)), id);
        return object != null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T save(final T object) {
        String className = ClassUtils.getShortName(object.getClass());        
        Class primaryKeyClass = iBatisDaoUtils.getPrimaryKeyFieldType(object);
        Object primaryKey = getSqlMapClientTemplate().insert(iBatisDaoUtils.getInsertQuery(className), object);    
        iBatisDaoUtils.setPrimaryKey(object, primaryKeyClass, primaryKey);
        if (null == iBatisDaoUtils.getPrimaryKeyValue(object)) {
            //throw new ObjectRetrievalFailureException(className, object);
        	return null;
        } else {
            return object;
        }
    }

    public T update(T object) {
        String className = ClassUtils.getShortName(object.getClass());
        if (null == iBatisDaoUtils.getPrimaryKeyValue(object)) {
            throw new ObjectRetrievalFailureException(className, object);
        } else {
            getSqlMapClientTemplate().update(iBatisDaoUtils.getUpdateQuery(className), object);
            return object;
        }
    }

    public boolean updating(T object) {
        String className = ClassUtils.getShortName(object.getClass());
        if (null == iBatisDaoUtils.getPrimaryKeyValue(object)) {
            throw new ObjectRetrievalFailureException(className, object);
        } else {
            int opNum = getSqlMapClientTemplate().update(iBatisDaoUtils.getUpdateQuery(className), object);
            return opNum > 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        getSqlMapClientTemplate().update(
                iBatisDaoUtils.getDeleteQuery(ClassUtils.getShortName(this.persistentClass)), id);
    }
}