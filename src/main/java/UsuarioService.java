import java.util.ArrayList;
import java.util.Collection;

public interface UsuarioService {
    ArrayList<Usuario> iniciadorUsuarios();
    Boolean addUsuario(Usuario usuario);
    Collection<Usuario> getUsuarios();
    Usuario getUsuario (int id);
    Usuario editUsuario(Usuario usuario) throws ClasesException;
    void deleteUsuario(int id);
}
