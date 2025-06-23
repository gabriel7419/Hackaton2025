package br.edu.unialfa.gabarito.service;

import br.edu.unialfa.gabarito.model.Usuario;
import br.edu.unialfa.gabarito.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // DEBUG - Mostra os dados do usuário encontrado
        System.out.println("Usuário carregado: " + usuario.getEmail() +
                " | Perfil: " + usuario.getPerfil() +
                " | Senha: " + usuario.getSenha());

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getPerfil().name()) // Isso já adiciona "ROLE_" automaticamente
                .disabled(!usuario.getAtivo())
                .build();
    }
}