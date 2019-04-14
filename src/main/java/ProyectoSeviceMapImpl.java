import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ProyectoSeviceMapImpl implements ProyectoService {

    private HashMap<Integer, Proyecto> proyectoHashMap;

    public ProyectoSeviceMapImpl() {
        proyectoHashMap = new HashMap<Integer, Proyecto>();
    }

    @Override
    public void iniciadorProyecto(ArrayList<Incidente> incidentes, ArrayList<Usuario> usuarios) {
        ArrayList<Incidente> incidentesProyecto1=new ArrayList<>();
        incidentesProyecto1.add(incidentes.get(0));
        Proyecto proyecto1 = new Proyecto(1,"Ramal Vial",usuarios.get(0),incidentesProyecto1);

        ArrayList<Incidente> incidentesProyecto2=new ArrayList<>();
        incidentesProyecto2.add(incidentes.get(1));
        Proyecto proyecto2 = new Proyecto(2,"ErSEP",usuarios.get(1),incidentesProyecto2);
        proyectoHashMap.put(1,proyecto1);
        proyectoHashMap.put(2,proyecto2);
    }

    @Override
    public void addProyecto(Proyecto proyecto,Usuario usuario) {
        proyecto.setPropietario(usuario);
        proyectoHashMap.put(proyecto.getId(),proyecto);
    }

    @Override
    public Collection<Proyecto> getProyectos() {
        return proyectoHashMap.values();
    }

    @Override
    public Proyecto getProyecto(int id) {

        return proyectoHashMap.get(id);
    }

    @Override
    public Proyecto editProyecto(Proyecto proyecto, Usuario usuario)  {

            Proyecto proyectoEditar= proyectoHashMap.get(proyecto.getId());
            if(proyecto.getTitulo() != null){
                proyectoEditar.setTitulo(proyecto.getTitulo());
                proyectoEditar.setPropietario(usuario);
                proyectoEditar.setIncidentes(proyecto.getIncidentes());
            }


            return proyectoEditar;

    }

    @Override
    public Boolean deleteProyecto(int id) {
        final ArrayList<Integer> key=new ArrayList<Integer>();
        proyectoHashMap.forEach((k,v)->{
            if (v.getId()==id){
                if(v.getIncidentes().isEmpty()){
                key.add(k);
                }else if (v.getIncidentes().stream().allMatch(i -> i.getEstado()==Estado.RESUELTO)){
                    key.add(k);
                }
            }
        });
        if ( key.isEmpty()){
            return false;
        }else {proyectoHashMap.remove(key.get(0));
            return true;}

    }

    @Override
    public ArrayList<Proyecto> getProyectosPorUsuario(int id) {
        ArrayList<Proyecto> proyectos = new ArrayList<Proyecto>();
        proyectoHashMap.forEach((k,v) -> {if(v.getPropietario().getId()==id){
        proyectos.add(v);
        }
        });
        return proyectos;
    }

    @Override
    public ArrayList<Incidente> getIncidentesPorProyecto(Integer id) {
        final ArrayList<Incidente>[] incidentes = new ArrayList[]{new ArrayList<>()};
        proyectoHashMap.forEach((k,v)->{
            if (v.getId()==id)
            {
                incidentes[0] = v.getIncidentes();

            }
        });
        return incidentes[0];

    }

    @Override
    public void addIncidenteProyecto(int id, Incidente incidente) {
        proyectoHashMap.forEach((k,v)->{
            if (v.getId()==id)
            {
                v.getIncidentes().add(incidente);

            }
        });
    }
}
