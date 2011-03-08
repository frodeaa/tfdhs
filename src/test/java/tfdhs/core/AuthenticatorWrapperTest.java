package tfdhs.core;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import tfdhs.api.AuthenticatorResolver;

public class AuthenticatorWrapperTest {

    @Test
    public void testGetPass() {

	AuthenticatorResolver mockResolver = mock(AuthenticatorResolver.class);
	AuthenticatorWrapper wrapper = new AuthenticatorWrapper(mockResolver);

	wrapper.getPasswordAuthentication();

	verify(mockResolver).authenticate(wrapper);
    }

}
