import java.util.ArrayList;

public class Proyecto {
    private Integer id;
    private String titulo;
    private Usuario propietario;
    private ArrayList<Incidente> incidentes;

    public Proyecto(Integer id, String titulo, Usuario propietario, ArrayList<Incidente> incidentes) {
        this.id = id;
        this.titulo = titulo;
        this.propietario = propietario;
        this.incidentes = incidentes;
    }

    public ArrayList<Incidente> getIncidentes() {
        return incidentes;
    }

    public void setIncidentes(ArrayList<Incidente> incidentes) {
        this.incidentes = incidentes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }
}
