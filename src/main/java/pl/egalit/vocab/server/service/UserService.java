package pl.egalit.vocab.server.service;

import java.util.List;
import java.util.Set;

import pl.egalit.vocab.client.core.exceptions.UserExistsException;
import pl.egalit.vocab.server.entity.UserEntity;

public interface UserService {
	List<UserEntity> getUser(String email);

	void save(UserEntity user, Set<Long> courseIds) throws UserExistsException;

	List<UserEntity> getUsers();

	UserEntity findUserFromCurrentSchool(Long userId);

	void modify(UserEntity user, Set<Long> courseIds)
			throws UserExistsException;

	void removeUser(long userId);

	UserEntity getCurrentUserFromCurrentSchool();

}
