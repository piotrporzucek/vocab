package pl.egalit.vocab.server.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import pl.egalit.vocab.client.core.exceptions.RegisteredInManySchoolsException;

@Aspect
@Component
public class SecurityTranslatorExceptionAspect {
	@Around("@annotation(pl.egalit.vocab.server.security.GwtMethod)")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		// start stopwatch
		Object retVal = null;
		try {
			retVal = pjp.proceed();
		} catch (AccessDeniedException ex) {

			// throw new AppSecurityException("SEC");

		} catch (AuthenticationServiceException ase) {
			if (ase.getCause() instanceof RegisteredInManySchoolsException) {
				throw ase.getCause();
			}
			throw ase;
		}
		// stop stopwatch
		return retVal;
	}
}
