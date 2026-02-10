import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import com.ifpe.ifpe.Endereco;
import com.ifpe.ifpe.EnderecoService;

public class EnderecoServiceTest {
    @Test
    void devePreencherEnderecoAutomaticamenteAoBuscarPorCep() {
        String cep = "50740340";
        Endereco enderecoMock = new Endereco("Rua Poloni", "Várzea", "Recife", "PE");
        EnderecoService enderecoService = mock(EnderecoService.class);
        when(enderecoService.buscarEnderecoPorCep(cep)).thenReturn(enderecoMock);

        Endereco endereco = enderecoService.buscarEnderecoPorCep(cep);

        assertEquals("Rua Poloni", endereco.getLogradouro());
        assertEquals("Várzea", endereco.getBairro());
        assertEquals("Recife", endereco.getCidade());
        assertEquals("PE", endereco.getEstado());
    }
}
