package com.lk.collaborative.blogging.permission;

import com.lk.collaborative.blogging.data.domain.User;
import com.lk.collaborative.blogging.service.exception.AnonymousUserException;
import com.lk.collaborative.blogging.util.AuthenticationUtil;



public abstract class PermissionChecker<T extends Enum<?>, Resource> {

     protected abstract boolean hasPermission(T action, Resource object, User user);

     /**
      * Checks if the authenticated user has permission to perform given action on object.
      *
      * @param action The action to be performed on the entity. E.g. ADD,UPDATE,DELETE,etc.
      * @param object Object upon which the action is getting performed
      * @return true if the action is allowed, false otherwise.
      * @throws AnonymousUserException if the user is unauthenticated.
      *
      */
      public boolean hasPermission(T action, Resource object) throws AnonymousUserException {
          return hasPermission(action, object, AuthenticationUtil.getAuthenticatedUser());
     }
}
