package pl.egalit.vocab.server.service.impl;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.egalit.vocab.server.security.VocabUser;

class AbstractService {
	protected VocabUser getVocabUser() {
		AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof VocabUser) {
			VocabUser vocabUser = (VocabUser) authentication.getPrincipal();
			return vocabUser;
		} else {
			return null;
		}

	}

	protected boolean hasRole(
			Collection<? extends GrantedAuthority> authorities, String... roles) {
		for (GrantedAuthority auth : authorities) {
			String role = auth.getAuthority();
			if (Arrays.asList(roles).contains(role)) {
				return true;
			}
		}
		return false;
	}

}
