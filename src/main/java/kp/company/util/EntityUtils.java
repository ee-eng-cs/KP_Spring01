package kp.company.util;

import java.util.Collection;

import org.springframework.orm.ObjectRetrievalFailureException;

import kp.company.BaseEntity;

/**
 * Utility methods for handling entities. Separate from the BaseEntity class
 * mainly because of dependency on the ORM-associated
 * ObjectRetrievalFailureException.
 * 
 * @see kp.company.BaseEntity
 */
public abstract class EntityUtils {

	/**
	 * Look up the entity of the given class with the given id in the given
	 * collection.
	 * 
	 * @param <T>         the type parameter
	 * @param entities    the collection to search
	 * @param entityClass the entity class to look up
	 * @param entityId    the entity id to look up
	 * @return the found entity
	 * @throws ObjectRetrievalFailureException if the entity was not found
	 */
	public static <T extends BaseEntity> T getById(Collection<T> entities, Class<T> entityClass, int entityId)
			throws ObjectRetrievalFailureException {

		for (T entity : entities) {
			if (entity.getId().intValue() == entityId && entityClass.isInstance(entity)) {
				return entity;
			}
		}
		throw new ObjectRetrievalFailureException(entityClass, Integer.valueOf(entityId));
	}
}