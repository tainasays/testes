import com.ifpe.ifpe.Usuario;
import com.ifpe.ifpe.UsuarioRepository;
import com.ifpe.ifpe.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

        @Test
        void naoDeveCadastrarUsuarioComDataNascimentoVaziaOuNull() {
            Usuario usuarioVazio = new Usuario("Clara Maria Alves", "email@email.com", "12545758485", "!SenhaValida123", "", "4655784474", "Rua X", "Bairro Y", "Cidade Z", "77", "PE", "68371456", null);
            Usuario usuarioNull = new Usuario("Clara Maria Alves", "email@email.com", "12545758485", "!SenhaValida123", null, "4655784474", "Rua X", "Bairro Y", "Cidade Z", "77", "PE", "68371456", null);
            Exception ex1 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioVazio));
            Exception ex2 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioNull));
            assertTrue(ex1.getMessage().contains("Data de nascimento é obrigatória!"));
            assertTrue(ex2.getMessage().contains("Data de nascimento é obrigatória!"));
            verify(repository, never()).salvar(any());
        }

        @Test
        void naoDeveCadastrarUsuarioComNumeroContatoVazioOuNull() {
            Usuario usuarioVazio = new Usuario("Clara Maria Alves", "email@email.com", "12545758485", "!SenhaValida123", "10-10-2000", "", "Rua X", "Bairro Y", "Cidade Z", "77", "PE", "68371456", null);
            Usuario usuarioNull = new Usuario("Clara Maria Alves", "email@email.com", "12545758485", "!SenhaValida123", "10-10-2000", null, "Rua X", "Bairro Y", "Cidade Z", "77", "PE", "68371456", null);
            Exception ex1 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioVazio));
            Exception ex2 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioNull));
            assertTrue(ex1.getMessage().contains("Numero de contato é obrigatório!"));
            assertTrue(ex2.getMessage().contains("Numero de contato é obrigatório!"));
            verify(repository, never()).salvar(any());
        }

        @Test
        void naoDeveCadastrarUsuarioComCepVazioOuNull() {
            Usuario usuarioVazio = new Usuario("Clara Maria Alves", "email@email.com", "12545758485", "!SenhaValida123", "10-10-2000", "4655784474", "Rua X", "Bairro Y", "Cidade Z", "77", "PE", "", null);
            Usuario usuarioNull = new Usuario("Clara Maria Alves", "email@email.com", "12545758485", "!SenhaValida123", "10-10-2000", "4655784474", "Rua X", "Bairro Y", "Cidade Z", "77", "PE", null, null);
            Exception ex1 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioVazio));
            Exception ex2 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioNull));
            assertTrue(ex1.getMessage().contains("CEP é obrigatório!"));
            assertTrue(ex2.getMessage().contains("CEP é obrigatório!"));
            verify(repository, never()).salvar(any());
        }

        @Test
        void naoDeveCadastrarUsuarioComNumeroVazioOuNull() {
            Usuario usuarioVazio = new Usuario("Clara Maria Alves", "email@email.com", "12545758485", "!SenhaValida123", "10-10-2000", "4655784474", "Rua X", "Bairro Y", "Cidade Z", "", "PE", "68371456", null);
            Usuario usuarioNull = new Usuario("Clara Maria Alves", "email@email.com", "12545758485", "!SenhaValida123", "10-10-2000", "4655784474", "Rua X", "Bairro Y", "Cidade Z", null, "PE", "68371456", null);
            Exception ex1 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioVazio));
            Exception ex2 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioNull));
            assertTrue(ex1.getMessage().contains("Número é obrigatório!"));
            assertTrue(ex2.getMessage().contains("Número é obrigatório!"));
            verify(repository, never()).salvar(any());
        }
    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuarioValido;

    @BeforeEach
    void setUp() {
        usuarioValido = new Usuario("João Silva", "joao@email.com", "12345678901", "senha123",
            "01-01-1990", "81999999999", "Rua 1", "Centro", "Recife", "100", "PE", "50700000", null);
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
        Usuario usuario = new Usuario("", "email@email.com", "12345678901", "senha123",
            "01-01-1990", "81999999999", "Rua 1", "Centro", "Recife", "100", "PE", "50700000", null);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuario));
        assertTrue(ex.getMessage().contains("Nome é obrigatório!"));
        verify(repository, never()).salvar(any());
    }


    @Test
    void naoDeveCadastrarUsuarioComEmailJaCadastrado() {
        Usuario usuario = new Usuario(
            "Bentinho Santana", "bentinho@email.com", "12345678921", "Senhacer95#",
            "15102002", "81988556632", "Rua Poloni", "Várzea", "Recife", "218", "PE", "50740340", null
        );
        when(repository.existePorEmail(usuario.getEmail())).thenReturn(true);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuario));
        assertTrue(ex.getMessage().contains("Email já cadastrado"));
        verify(repository, never()).salvar(any());
    }

    @Test
    void naoDeveCadastrarUsuarioComSenhaInvalida() {
        // Senha menor que 8 caracteres
        Usuario usuarioCurta = new Usuario(
            "José Santos", "jose1novo@email.com", "12345678921", "Se12#",
            "15102003", "8292334587", "Rua Poloni", "Várzea", "Recife", "218", "PE", "50740340", null
        );
        when(repository.existePorEmail(usuarioCurta.getEmail())).thenReturn(false);
        when(repository.existePorCpf(usuarioCurta.getCpf())).thenReturn(false);
        Exception exCurta = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioCurta));
        assertTrue(exCurta.getMessage().contains("Senha deve ter no mínimo 8 caracteres"));
        verify(repository, never()).salvar(any());
       
    }

    @Test
    void naoDeveCadastrarUsuarioComNomeVazioOuNull() {
        Usuario usuarioVazio = new Usuario("", "email@email.novo1.com", "12545758485", "!SenhaValida123", "10-10-2000", "4655784474", "Rua X", "Bairro Y", "Cidade Z", "77", "PE", "68371456", null);
        Usuario usuarioNull = new Usuario(null, "email@email.novo1.com", "12545758485", "!SenhaValida123", "10-10-2000", "4655784474", "Rua X", "Bairro Y", "Cidade Z", "77", "PE", "68371456", null);
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioVazio));
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioNull));
        assertTrue(ex1.getMessage().contains("Nome é obrigatório!"));
        assertTrue(ex2.getMessage().contains("Nome é obrigatório!"));
        verify(repository, never()).salvar(any());
    }

    @Test
    void naoDeveCadastrarUsuarioComEmailVazioOuNull() {
        Usuario usuarioVazio = new Usuario("Clara Maria Alves", "", "12545758485", "!SenhaValida123", "10-10-2000", "4655784474", "Rua X", "Bairro Y", "Cidade Z", "77", "PE", "68371456", null);
        Usuario usuarioNull = new Usuario("Clara Maria Alves", null, "12545758485", "!SenhaValida123", "10-10-2000", "4655784474", "Rua X", "Bairro Y", "Cidade Z", "77", "PE", "68371456", null);
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioVazio));
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioNull));
        assertTrue(ex1.getMessage().contains("Email é obrigatório!"));
        assertTrue(ex2.getMessage().contains("Email é obrigatório!"));
        verify(repository, never()).salvar(any());
    }

    @Test
    void naoDeveCadastrarUsuarioComCpfVazioOuNull() {
        Usuario usuarioVazio = new Usuario("Clara Maria Alves", "email1@novo.com", "", "!SenhaValida123", "10-10-2000", "4655784474", "Rua X", "Bairro Y", "Cidade Z", "77", "PE", "68371456", null);
        Usuario usuarioNull = new Usuario("Clara Maria Alves", "email1@novo.com", null, "!SenhaValida123", "10-10-2000", "4655784474", "Rua X", "Bairro Y", "Cidade Z", "77", "PE", "68371456", null);
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioVazio));
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioNull));
        assertTrue(ex1.getMessage().contains("CPF é obrigatório!"));
        assertTrue(ex2.getMessage().contains("CPF é obrigatório!"));
        verify(repository, never()).salvar(any());
    }

    @Test
    void naoDeveCadastrarUsuarioComSenhaVaziaOuNull() {
        Usuario usuarioVazio = new Usuario("Clara Maria Alves", "email1@novo.com", "10478754781", "", "10-10-2000", "4655784474", "Rua X", "Bairro Y", "Cidade Z", "77", "PE", "68371456", null);
        Usuario usuarioNull = new Usuario("Clara Maria Alves", "email1@novo.com", "10478754781", null, "10-10-2000", "4655784474", "Rua X", "Bairro Y", "Cidade Z", "77", "PE", "68371456", null);
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioVazio));
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuarioNull));
        assertTrue(ex1.getMessage().contains("Senha é obrigatória!"));
        assertTrue(ex2.getMessage().contains("Senha é obrigatória!"));
        verify(repository, never()).salvar(any());
    }


    @Test
    void deveCadastrarUsuarioComTodosOsDadosValidos() {
        Usuario usuario = new Usuario(
            "Elaine Cristina Chagas", "email@email.novo.com", "12545758485", "!SenhaValida123",
            "10-10-2000", "4655784474", "Rua X", "Bairro Y", "Cidade Z", "77", "PE", "68371456", null
        );
        when(repository.existePorEmail(usuario.getEmail())).thenReturn(false);
        when(repository.existePorCpf(usuario.getCpf())).thenReturn(false);
        service.cadastrarUsuario(usuario);
        verify(repository, times(1)).salvar(usuario);
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
        Usuario usuario = new Usuario("João", "email@email.com", "123", "senha123",
            "01-01-1990", "81999999999", "Rua 1", "Centro", "Recife", "100", "PE", "50700000", null);
        when(repository.existePorEmail(usuario.getEmail())).thenReturn(false);
        when(repository.existePorCpf(usuario.getCpf())).thenReturn(false);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuario));
        assertTrue(ex.getMessage().contains("CPF deve ter 11 dígitos"));
        verify(repository, never()).salvar(any());
    }

    @Test
    void naoDeveCadastrarUsuarioComSenhaFraca() {
        Usuario usuario = new Usuario("João", "email@email.com", "12345678901", "123",
            "01-01-1990", "81999999999", "Rua 1", "Centro", "Recife", "100", "PE", "50700000", null);
        when(repository.existePorEmail(usuario.getEmail())).thenReturn(false);
        when(repository.existePorCpf(usuario.getCpf())).thenReturn(false);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.cadastrarUsuario(usuario));
        assertTrue(ex.getMessage().contains("Senha deve ter no mínimo 8 caracteres"));
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

