import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class IncidenteServiceMapImpl implements IncidenteService {
    private HashMap<Integer,Incidente> incidenteHashMap;

    public IncidenteServiceMapImpl() {
       incidenteHashMap = new HashMap<Integer,Incidente>();
    }

    @Override
    public ArrayList<Incidente> iniciadorIncidentes(ArrayList<Usuario> usuarios) throws ParseException{

            Incidente incidente =new Incidente(1,Clasificacion.MENOR,"esto es una descripcion", usuarios.get(0), usuarios.get(1), Estado.ASIGNADO,
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/05/2015"),null);

            Incidente incidente2 =new Incidente(2,Clasificacion.NORMAL,"esto es una descripcion", usuarios.get(2), usuarios.get(3), Estado.RESUELTO,
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/05/2015"),new SimpleDateFormat("dd/MM/yyyy").parse("21/06/2017"));
            incidenteHashMap.put(1,incidente);
            incidenteHashMap.put(2,incidente2);
            ArrayList<Incidente> incidentes=new ArrayList<Incidente>();
            incidentes.add(incidente);
            incidentes.add(incidente2);
            return incidentes;
        }


    @Override
    public void addIncidente(Incidente incidente) {
        incidenteHashMap.put(incidente.getId(),incidente);
    }
    @Override
    public Collection<Incidente> getIncidentes() {
        return incidenteHashMap.values();
    }

    @Override
    public Incidente getIncidente(int id) {
        return incidenteHashMap.get(id);
    }
    @Override
    public Incidente editIncidente(Incidente incidente)  {

            Incidente incidenteEditar= incidenteHashMap.get(incidente.getId());
            incidenteEditar.setDescripcion(incidenteEditar.getDescripcion()+System.lineSeparator()+incidente.getDescripcion());
            incidenteEditar.setEstado(Estado.RESUELTO);
            return incidenteEditar;

    }

    @Override
    public Collection<Incidente> getIncidentePorUsuario(Integer id) {
        ArrayList<Incidente> incidentes=new ArrayList<>();
        incidenteHashMap.forEach((k,v)->{
            if (v.getResponsable().getId()==id){
                incidentes.add(v);
            }
        });
        return incidentes;
    }

    @Override
    public Collection<Incidente> getIncidenteCreadosPorUsuario(Integer id) {
        ArrayList<Incidente> incidentes=new ArrayList<>();
        incidenteHashMap.forEach((k,v)->{
            if (v.getReportador().getId()==id){
                incidentes.add(v);
            }
        });
        return incidentes;
    }

    @Override
    public Collection<Incidente> getIncidentesPendientes() {
        ArrayList<Incidente> incidentes=new ArrayList<>();
        incidenteHashMap.forEach((k,v)->{
            if (v.getEstado()==Estado.ASIGNADO){
                incidentes.add(v);
            }
        });
        return incidentes;
    }

    @Override
    public Collection<Incidente> getIncidentesCerrados() {
        ArrayList<Incidente> incidentes=new ArrayList<>();
        incidenteHashMap.forEach((k,v)->{
            if (v.getEstado()==Estado.RESUELTO){
                incidentes.add(v);
            }
        });
        return incidentes;
    }
}
