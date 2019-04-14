import java.util.ArrayList;
import java.util.Collection;

public interface ProyectoService {
    void iniciadorProyecto(ArrayList<Incidente> incidentes,ArrayList<Usuario> usuarios);
    void addProyecto(Proyecto proyecto, Usuario usuario);
    Collection<Proyecto> getProyectos();
    Proyecto getProyecto (int id);
    Proyecto editProyecto(Proyecto proyecto,Usuario usuario) ;
    Boolean deleteProyecto(int id);
    ArrayList<Proyecto> getProyectosPorUsuario(int id);
    ArrayList<Incidente> getIncidentesPorProyecto(Integer id);
    void addIncidenteProyecto(int id,Incidente incidente);
}
