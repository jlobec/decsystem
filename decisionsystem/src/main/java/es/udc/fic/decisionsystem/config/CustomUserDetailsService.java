package es.udc.fic.decisionsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fic.decisionsystem.model.usuario.Usuario;
import es.udc.fic.decisionsystem.repository.usuario.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UsuarioRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String nicknameOrEmail) throws UsernameNotFoundException {
		// Let people login with either username or email
		Usuario user = userRepository.findByNicknameOrEmail(nicknameOrEmail, nicknameOrEmail).orElseThrow(
				() -> new UsernameNotFoundException("User not found with nickname or email : " + nicknameOrEmail));

		return UserPrincipal.create(user);
	}

	// This method is used by JWTAuthenticationFilter
	@Transactional
	public UserDetails loadUserById(Long id) {
		Usuario user = userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

		return UserPrincipal.create(user);
	}
}