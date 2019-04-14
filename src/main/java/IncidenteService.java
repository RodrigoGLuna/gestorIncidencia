import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

public interface IncidenteService {
    ArrayList<Incidente> iniciadorIncidentes(ArrayList<Usuario> usuarios) throws ParseException;
    void addIncidente(Incidente incidente);
    Collection<Incidente> getIncidentes();
    Incidente getIncidente (int id);
    Incidente editIncidente(Incidente incidente) throws ClasesException;
    Collection<Incidente> getIncidentePorUsuario(Integer id);
    Collection<Incidente> getIncidenteCreadosPorUsuario(Integer id);
    Collection<Incidente> getIncidentesPendientes();
    Collection<Incidente> getIncidentesCerrados();
}

