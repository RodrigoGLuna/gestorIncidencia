import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class UsuarioServiceMapImpl implements UsuarioService {

    private HashMap<Integer, Usuario> usuarioHashMap;

    @Override
    public ArrayList<Usuario> iniciadorUsuarios() {
        ArrayList<Usuario> usuarios=new ArrayList<>();
        usuarios.add( new Usuario(1, "Martin","Suarez"));
        usuarios.add(new Usuario(2,"Martina","Basualdo"));
        usuarios.add(new Usuario(3, "Julio","Boca"));
        usuarios.add(new Usuario(4,"Florencia","Sain"));
        usuarios.add(new Usuario(5,"Roberto","Vallejo"));
        usuarioHashMap.put(usuarios.get(0).getId(),usuarios.get(0));
        usuarioHashMap.put(usuarios.get(1).getId(),usuarios.get(1));
        usuarioHashMap.put(usuarios.get(2).getId(),usuarios.get(2));
        usuarioHashMap.put(usuarios.get(3).getId(),usuarios.get(3));
        usuarioHashMap.put(usuarios.get(4).getId(),usuarios.get(4));
        return usuarios;
    }

    public UsuarioServiceMapImpl(){
        usuarioHashMap=new HashMap<Integer, Usuario>();
    }

    public Boolean addUsuario(Usuario usuario) {
        final Boolean[] res = {true};
        usuarioHashMap.forEach((k,v)->{
            if (v.getId()==usuario.getId())
            {
                res[0] =false;
            }
        });
        if (res[0] ==true){
        usuarioHashMap.put(usuario.getId(),usuario);
        return true;
        }else {return false;}
    }

    public Collection<Usuario> getUsuarios() {
        return usuarioHashMap.values();
    }

    public Usuario getUsuario(int id) {
        return usuarioHashMap.get(id);
    }

    public Usuario editUsuario(Usuario usuario) throws ClasesException {
        try{
            if (String.valueOf(usuario.getId()) == null)
            {
                throw  new ClasesException("El id del usuario no puede ser nulo");
            }
            Usuario usuarioEditar= usuarioHashMap.get(usuario.getId());
            if (usuarioEditar==null){
                return usuarioEditar;
            }
            if(usuario.getNombre() != null){
                usuarioEditar.setNombre(usuario.getNombre());
            }
            if(usuario.getApellido() != null){
                usuarioEditar.setApellido(usuario.getApellido());
            }

            return usuarioEditar;
        }catch (Exception exception){
            throw  new ClasesException(exception.getMessage());
        }
    }

    public void deleteUsuario(int id) {
        final int[] key = new int[1];
        usuarioHashMap.forEach((k,v)->{
            if (v.getId()==id){
               key[0] =k;
            }
        });
        usuarioHashMap.remove(key[0]);
    }
}
