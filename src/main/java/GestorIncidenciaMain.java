import static spark.Spark.*;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.ArrayList;

public class GestorIncidenciaMain {

    public static void main(String[] args) throws ParseException {

        final UsuarioService usuarioService=new UsuarioServiceMapImpl();
        final ProyectoService proyectoService=new ProyectoSeviceMapImpl();
        final IncidenteService incidenteService=new IncidenteServiceMapImpl();
        ArrayList<Usuario>usuarios=usuarioService.iniciadorUsuarios();
        ArrayList<Incidente> incidentes= incidenteService.iniciadorIncidentes(usuarios);
        proyectoService.iniciadorProyecto(incidentes,usuarios);

        post("/usuario", ((request,response) -> {
            response.type("application/json");
            Usuario usuario=new Gson().fromJson(request.body(),Usuario.class);
            Boolean res= usuarioService.addUsuario(usuario);
            if (res==true) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
            }
            else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"El usuario ya existe"));
            }
        }));

        get("/usuario", ((request, response) -> {
                response.type("application/json");
                return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(usuarioService.getUsuarios())));
            }));

        get("/usuario/:id", ((request, response) -> {
                response.type("application/json");
                Usuario usuario = usuarioService.getUsuario(Integer.valueOf(request.params(":id")));
                if (usuario == null){
                    response.status(404);
                    return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"El usuario no existe"));
                }else{
                return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(usuario)));
                }
            }));

        put("/usuario", ((request, response) -> {
                response.type("application/json");
                Usuario usuario = new Gson().fromJson(request.body(),Usuario.class);
                Usuario usuarioEditado = usuarioService.editUsuario(usuario);
                if (usuarioEditado != null){
                    return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                            new Gson().toJsonTree(usuarioEditado)));
                }else{
                    response.status(404);
                    return  new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
                            "Error al editar usuario"));
                }
            }));

        delete("/usuario/:id",((request, response) -> {
                response.type("application/json");
                if(proyectoService.getProyectosPorUsuario(Integer.valueOf(request.params(":id"))).isEmpty() && incidenteService.getIncidentePorUsuario(Integer.valueOf(request.params(":id"))).isEmpty()
                        && incidenteService.getIncidenteCreadosPorUsuario(Integer.valueOf(request.params(":id"))).isEmpty() && usuarioService.getUsuario(Integer.valueOf(request.params(":id")))!=null) {
                    usuarioService.deleteUsuario(Integer.valueOf(request.params(":id")));
                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Usuario borrado"));
                }
                else if  (usuarioService.getUsuario(Integer.valueOf(request.params(":id")))==null){
                    return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "El usuario no se puede borrar ya que no existe"));
                }
                else {

                    return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "El usuario no se puede borrar ya que esta asignado"));
                }
            }));




        post("/proyecto", ((request,response) -> {
            response.type("application/json");
            Proyecto proyecto=new Gson().fromJson(request.body(),Proyecto.class);
            if (usuarioService.getUsuario(proyecto.getPropietario().getId()) == null){
                response.status(404);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"El usuario no existe"));
            }else if  (proyectoService.getProyecto(proyecto.getId())!=null){
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "El id del proyecto ya existe"));
            }
            else {
                proyectoService.addProyecto(proyecto,usuarioService.getUsuario(proyecto.getPropietario().getId()));
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
            }
        }));

        get("/proyecto", ((request, response) -> {
            response.type("application/json");
            return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(proyectoService.getProyectos())));
        }));

        get("/proyecto/:id", ((request, response) -> {
            response.type("application/json");
            Proyecto proyecto=proyectoService.getProyecto(Integer.valueOf(request.params(":id")));
            if (proyecto == null){
                response.status(404);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"El proyecto no existe"));
            }else{
                return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(proyecto)));
            }
        }));

        put("/proyecto", ((request, response) -> {
            response.type("application/json");
            Proyecto proyecto = new Gson().fromJson(request.body(),Proyecto.class);

            if (proyecto.getId()==null || proyectoService.getProyecto(proyecto.getId()) == null ){
                response.status(404);
                return  new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "El proyecto no existe"));
            }else if (usuarioService.getUsuario(proyecto.getPropietario().getId())==null){
                response.status(404);
                return  new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "El usuario no existe"));
            }
            else{
                return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(proyectoService.editProyecto(proyecto, usuarioService.getUsuario(proyecto.getPropietario().getId())))));
            }
        }));

        delete("/proyecto/:id",((request, response) -> {
            response.type("application/json");
            if (proyectoService.getProyecto(Integer.valueOf(request.params(":id")))!=null){
                if (proyectoService.deleteProyecto(Integer.valueOf(request.params(":id")))==true){
                    return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "Proyecto borrado"));
                }
                else {
                    return  new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "El proyecto tiene incidentes sin resolver"));
                }

            }else {
                response.status(404);
                return  new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "El proyecto no existe"));
            }


        }));

        get("/proyecto/usuario/:id", (((request, response) -> {
            response.type("application/json");
            if(proyectoService.getProyectosPorUsuario(Integer.valueOf(request.params(":id"))).isEmpty()){
                response.status(404);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "El usuario no es propietario de proyectos") );
            }else {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(proyectoService.getProyectosPorUsuario(Integer.valueOf(request.params(":id"))))));
            }
        })));

        get("/proyecto/:id/incidente", ((request, response) ->{
            response.type("application/json");
            if (proyectoService.getProyecto(Integer.valueOf(request.params(":id"))) == null) {
                response.status(404);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "El proyecto no existe"));
            }else {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(proyectoService.getIncidentesPorProyecto(Integer.valueOf(request.params(":id"))))));
            }
        }));





        post("/proyecto/:id/incidente", ((request,response) -> {
            response.type("application/json");
            Incidente incidente=new Gson().fromJson(request.body(),Incidente.class);
            if (proyectoService.getProyecto(Integer.valueOf(request.params(":id"))) == null){
                response.status(404);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"El proyecto no existe"));
            }else if (incidenteService.getIncidente(incidente.getId())!=null){
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"El id del incidente ya existe"));
            }else if(usuarioService.getUsuario(incidente.getReportador().getId())==null || usuarioService.getUsuario(incidente.getResponsable().getId())==null){
                response.status(404);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"El usuario no existe"));
            }

            else
                {
                proyectoService.addIncidenteProyecto(Integer.valueOf(request.params(":id")),incidente);
                incidenteService.addIncidente(incidente);
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
            }
        }));

        get("/incidente", ((request, response) -> {
            response.type("application/json");
            return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(incidenteService.getIncidentes())));
        }));

        get("/incidente/:id", ((request, response) -> {
            response.type("application/json");
            return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(incidenteService.getIncidente(Integer.valueOf(request.params(":id"))))));
        }));

        put("/incidente", ((request, response) -> {
            response.type("application/json");
            Incidente incidente = new Gson().fromJson(request.body(),Incidente.class);
            if (incidente.getId()==null || incidenteService.getIncidente(incidente.getId()) == null ){
                response.status(404);
                return  new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "El incidente no existe"));
            }
            else{
                return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(incidenteService.editIncidente(incidente))));
            }
        }));

        get("/incidente/usuario-responsable/:id", (((request, response) -> {
            response.type("application/json");
            if (incidenteService.getIncidentePorUsuario(Integer.valueOf(request.params(":id"))).isEmpty()){
                response.status(404);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "El usuario no tiene asignado ningun incidente") );
            }else {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(incidenteService.getIncidentePorUsuario(Integer.valueOf(request.params(":id"))))));
            }

        })));

        get("/incidente/usuario-reportador/:id", (((request, response) -> {
            response.type("application/json");
            if (incidenteService.getIncidenteCreadosPorUsuario(Integer.valueOf(request.params(":id"))).isEmpty()){
                response.status(404);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "El usuario no creo ningun incidente") );
            }else {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(incidenteService.getIncidenteCreadosPorUsuario(Integer.valueOf(request.params(":id"))))));
            }

        })));

        get("/incidente-abierto", ((request, response) -> {
            response.type("application/json");
            if (incidenteService.getIncidentesPendientes().isEmpty()){
                response.status(404);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"No existen incidentes pendientes"));
            }
            else {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(incidenteService.getIncidentesPendientes())));
            }
        }));

        get("/incidente-resuelto", ((request, response) -> {
            response.type("application/json");
            if (incidenteService.getIncidentesCerrados().isEmpty()){
                response.status(404);
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,"No existen incidentes cerrados"));
            }
            else {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(incidenteService.getIncidentesCerrados())));
            }
        }));






    }


}
