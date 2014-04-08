package com.cy.cms.dao;

import java.io.Serializable;
import java.util.List;


public interface GenericDao<T, PK extends Serializable> {

    /**
     * @return List<T> get all object
     */
    List<T> getAll();

    /**
     * @param id the Primary Key
     * @return T  get the object by the primary key
     */
    T get(PK id);

    /*
    * @param id the Primary Key
    * @return boolean
    **/

    boolean exists(PK id);

    /**
     * @param object the model object
     * @return T
     */
    T save(T object);

    /**
     * @param object the model object
     * @return T
     */
    T update(T object);

    /**
     * @param object the model object
     * @return T
     */
    boolean updating(T object);

    /**
     * @param  the Primary Key
     * @return void
     */
    void remove(PK id);

}