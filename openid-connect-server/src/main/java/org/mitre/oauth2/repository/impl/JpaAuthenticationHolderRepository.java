package org.mitre.oauth2.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.mitre.oauth2.model.AuthenticationHolderEntity;
import org.mitre.oauth2.repository.AuthenticationHolderRepository;
import org.mitre.util.jpa.JpaUtil;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JpaAuthenticationHolderRepository implements AuthenticationHolderRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public AuthenticationHolderEntity getById(Long id) {
		return manager.find(AuthenticationHolderEntity.class, id);
	}

	@Override
	public AuthenticationHolderEntity getByAuthentication(OAuth2Authentication a) {
		TypedQuery<AuthenticationHolderEntity> query = manager.createNamedQuery("AuthenticationHolderEntity.getByAuthentication", AuthenticationHolderEntity.class);
		query.setParameter("authentication", a);
		return JpaUtil.getSingleResult(query.getResultList());
	}

	@Override
	@Transactional
	public void removeById(Long id) {
		AuthenticationHolderEntity found = getById(id);
		if (found != null) {
			manager.remove(found);
		} else {
			throw new IllegalArgumentException("AuthenticationHolderEntity not found: " + id);
		}
	}

	@Override
	@Transactional
	public void remove(AuthenticationHolderEntity a) {
		AuthenticationHolderEntity found = getById(a.getId());
		if (found != null) {
			manager.remove(found);
		} else {
			throw new IllegalArgumentException("AuthenticationHolderEntity not found: " + a);
		}
	}

	@Override
	@Transactional
	public AuthenticationHolderEntity save(AuthenticationHolderEntity a) {
		return JpaUtil.saveOrUpdate(a.getId(), manager, a);
	}

}
