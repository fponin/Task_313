package com.fponin.Task_312.myapp_springboot.dao;

import com.fponin.Task_312.myapp_springboot.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    private final EntityManager entityManager;

    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Role getRoleByName(String roleName) {
        @SuppressWarnings("unchecked")
        List<Role> resultList = entityManager.createQuery("from Role where roleName=?1")
                .setParameter(1, roleName)
                .getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public List<Role> getAllRole() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }

}
