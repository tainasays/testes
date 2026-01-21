

import com.ifpe.ifpe.Usuario;
import com.ifpe.ifpe.UsuarioRepository;
import com.ifpe.ifpe.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuarioValido;

    @BeforeEach
    void setUp() {
        usuarioValido = new Usuario("João Silva", "joao@email.com", "12345678901", "Rua 1", "senha123");
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        when(repository.existePorEmail(usuarioValido.getEmail())).thenReturn(false);
        when(repository.existePorCpf(usuarioValido.getCpf())).thenReturn(false);
        service.cadastrarUsuario(usuarioValido);
        verify(repository, times(1)).salvar(usuarioValido);
    }

    @Test
    void deveDeletarUsuarioComSucesso() {
        when(repository.existePorCpf(usuarioValido.getCpf())).thenReturn(true);
        service.deletarUsuarioPorCpf(usuarioValido.getCpf());
        verify(repository, times(1)).deletarPorCpf(usuarioValido.getCpf());
    }

    @Test
    void naoDeveCadastrarUsuarioComNomeVazio() {
        Usuario usuario = new Usuario("", "email@email.com", "12345678901", "Rua 1", "senha123");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuario));
        assertTrue(ex.getMessage().contains(""));
        verify(repository, never()).salvar(any());
    }

    @Test
    void naoDeveCadastrarUsuarioComEmailDuplicado() {
        when(repository.existePorEmail(usuarioValido.getEmail())).thenReturn(true);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioValido));
        assertTrue(ex.getMessage().contains("Email já cadastrado"));
        verify(repository, never()).salvar(any());
    }

    @Test
    void naoDeveCadastrarUsuarioComCpfDuplicado() {
        when(repository.existePorEmail(usuarioValido.getEmail())).thenReturn(false);
        when(repository.existePorCpf(usuarioValido.getCpf())).thenReturn(true);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioValido));
        assertTrue(ex.getMessage().contains("CPF já cadastrado"));
        verify(repository, never()).salvar(any());
    }

    @Test
    void naoDeveCadastrarUsuarioComCpfInvalido() {
        Usuario usuario = new Usuario("João", "email@email.com", "123", "Rua 1", "senha123");
        when(repository.existePorEmail(usuario.getEmail())).thenReturn(false);
        when(repository.existePorCpf(usuario.getCpf())).thenReturn(false);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuario));
        assertTrue(ex.getMessage().contains("CPF deve ter 11 dígitos"));
        verify(repository, never()).salvar(any());
    }

    @Test
    void naoDeveCadastrarUsuarioComSenhaFraca() {
        Usuario usuario = new Usuario("João", "email@email.com", "12345678901", "Rua 1", "123");
        when(repository.existePorEmail(usuario.getEmail())).thenReturn(false);
        when(repository.existePorCpf(usuario.getCpf())).thenReturn(false);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuario));
        assertTrue(ex.getMessage().contains("Senha deve ter no mínimo 8 caracteres"));
        verify(repository, never()).salvar(any());
    }

    @Test
    void naoDeveCadastrarUsuarioComEnderecoVazio() {
        Usuario usuario = new Usuario("João", "email@email.com", "12345678901", "", "senha123");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuario));
        assertTrue(ex.getMessage().contains("Nenhum campo"));
        verify(repository, never()).salvar(any());
    }

    @Test
    void naoDeveDeletarUsuarioInexistente() {
        when(repository.existePorCpf(usuarioValido.getCpf())).thenReturn(false);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.deletarUsuarioPorCpf(usuarioValido.getCpf()));
        assertTrue(ex.getMessage().contains("Usuário não encontrado"));
        verify(repository, never()).deletarPorCpf(any());
    }
}
