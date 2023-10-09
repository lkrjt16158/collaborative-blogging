package com.lk.collaborative.blogging.service.permission;

import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.service.exception.AnonymousUserException;
import com.lk.collaborative.blogging.util.AuthenticationUtil;


/**
 * @param <T> Enum representing an action on resource.
 * @param <E> Object representing a resource.
 */
public abstract class Resource<T extends Enum<?>,E> {

     protected abstract boolean hasPermission(T action, E object, User user);

     /**
      * Checks if the authenticated user has permission to perform given action on object.
      *
      * @param action The action to be performed on the entity. E.g. ADD,UPDATE,DELETE,etc.
      * @param object Object upon which the action is getting performed
      * @return true if the action is allowed, false otherwise.
      * @throws AnonymousUserException if the user is unauthenticated.
      *
      */
      public boolean hasPermission(T action, E object) throws AnonymousUserException {
          return hasPermission(action, object, AuthenticationUtil.getAuthenticatedUser());
     }
}
