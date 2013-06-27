package pl.egalit.vocab.server.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pl.egalit.vocab.client.core.exceptions.RegisteredInManySchoolsException;
import pl.egalit.vocab.server.entity.UserEntity;
import pl.egalit.vocab.server.service.UserService;
import pl.egalit.vocab.server.service.impl.VocabUsernamePasswordAuthenticationToken;

import com.google.common.collect.Lists;

public class VocabUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	public UserDetails loadUserByUsername(String username,
			VocabUsernamePasswordAuthenticationToken authentication)
			throws UsernameNotFoundException {
		List<UserEntity> users = userService.getUser(username);
		Collection<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();

		if (users != null && users.size() == 1) {
			UserEntity userEntity = users.get(0);
			authorities.add(new SimpleGrantedAuthority(userEntity.getRole()));
			User user = new VocabUser(userEntity.getEmail(),
					userEntity.getPassword(), authorities,
					userEntity.getSchoolId(), userEntity.getId());
			return user;
		} else if (users != null && users.size() > 1
				&& authentication.getSchoolId() == null) {
			throw new RegisteredInManySchoolsException(getSchools(users));
		} else if (users != null && users.size() > 1) {
			for (UserEntity userEntity : users) {
				authorities
						.add(new SimpleGrantedAuthority(userEntity.getRole()));
				if (userEntity.getSchool().getId() == authentication
						.getSchoolId()) {
					return new VocabUser(userEntity.getEmail(),
							userEntity.getPassword(), authorities,
							userEntity.getSchoolId(), userEntity.getId());
				}
			}
		}
		throw new UsernameNotFoundException("");

	}

	private List<Long> getSchools(List<UserEntity> users) {
		List<Long> schools = Lists.newArrayList();
		for (UserEntity user : users) {
			schools.add(user.getSchool().getId());
		}
		return schools;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		throw new UnsupportedOperationException();
	}

}
