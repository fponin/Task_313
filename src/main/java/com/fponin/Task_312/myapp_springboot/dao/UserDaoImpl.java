package com.fponin.Task_312.myapp_springboot.dao;


import com.fponin.Task_312.myapp_springboot.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    @Transactional
    public User findUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void updateUser(User updatedUser) {
        entityManager.merge(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        entityManager.createQuery("delete User where id = " + id).executeUpdate();
    }

    @Override
    public User getUserByName(String name) {
        @SuppressWarnings("unchecked")
        List<User> users = entityManager.createQuery("from User where username=?1")
                .setParameter(1, name)
                .getResultList();
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

//    @Override
//    @Transactional
//    public Role getRoleByName(String roleName) {
//        @SuppressWarnings("unchecked")
//        List<Role> resultList = entityManager.createQuery("from Role where roleName=?1")
//                .setParameter(1, roleName)
//                .getResultList();
//        if (resultList.size() > 0) {
//            return resultList.get(0);
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    @Transactional
//    public List<Role> getAllRole() {
//        return entityManager.createQuery("from Role", Role.class).getResultList();
//    }
}
