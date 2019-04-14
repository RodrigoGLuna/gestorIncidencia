import java.util.Date;

public class Incidente {
    private Integer id;
    private Clasificacion clasificacion;
    private String descripcion;
    private Usuario reportador;
    private Usuario responsable;
    private Estado estado;
    private Date fechaCreacion;
    private Date fechaSolucion;

    public Incidente(Integer id, Clasificacion clasificacion, String descripcion, Usuario reportador, Usuario responsable, Estado estado, Date fechaCreacion, Date fechaSolucion) {
        this.id = id;
        this.clasificacion = clasificacion;
        this.descripcion = descripcion;
        this.reportador = reportador;
        this.responsable = responsable;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaSolucion = fechaSolucion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getReportador() {
        return reportador;
    }

    public void setReportador(Usuario reportador) {
        this.reportador = reportador;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(Date fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }
}
