package resenas;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import resenas.utils.Correo;
import resenas.utils.Encriptar;
import resenas.utils.JwtUtils;
import spark.Request;
import spark.Response;
import resenas.controlador.ControladorDireccion;
import resenas.controlador.ControladorPago;
import resenas.controlador.ControladorPaquete;
import resenas.controlador.ControladorProducto;
import resenas.controlador.ControladorProductoVenta;
import resenas.controlador.ControladorProducto_Paquete;
import resenas.controlador.ControladorProveedor;
import resenas.controlador.ControladorReporte;
import resenas.controlador.ControladorUsuario;
import resenas.controlador.ControladorVenta;
import resenas.dao.DAOPersona;
import resenas.controlador.ControladorObra;
import resenas.modelo.Persona;
import resenas.modelo.Usuario;
import resenas.utils.FileBinario;

public class App {

    private static Gson gson = new Gson();
    private static Map<String, String> tokensPassword = new HashMap<String, String>();
    private static FileBinario fileBinario = new FileBinario();

    static {
        tokensPassword = fileBinario.readBinaryFile();
    }

    public static void main(String[] args) {

        port(getHerokuAssignedPort());

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Credentials", "true");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");

            if ("OPTIONS".equals(request.requestMethod())) {
                response.status(200);
                return;
            }

            String path = request.pathInfo();
            if ("/login".equals(path)) {
                return;
            }
            if ("/olvide-contrasena".equals(path)) {
                return;
            }
            if ("/cambiar-contrasena".equals(path)) {
                return;
            }
            String token = request.headers("Authorization");

            if (token == null || token.isEmpty()) {
                halt(401, "Acceso no autorizado");
                return;
            }
            String messageVerifiedToken = JwtUtils.verifyToken(token.replace("Bearer ",
                    ""));
            if (messageVerifiedToken.equals("Token valido")) {
                return;
            } else {
                halt(401, "Acceso no autorizado");
                return;
            }
        });
        options("/*", (request, response) -> {
            response.status(200);
            return "OK";
        });

        post("/login", (req, res) -> {
            Usuario dataCliente = gson.fromJson(req.body(), Usuario.class);
            String contrasenEncriptada = Encriptar.encriptar(dataCliente.getPassword());
            JsonObject usuario = ControladorUsuario.iniciarSesion(dataCliente.getUser(), contrasenEncriptada);

            if (usuario != null) {
                String token = JwtUtils.generateToken(usuario.get("rol").getAsString(),
                        usuario.get("id").getAsString());
                if (token != null && !token.isEmpty()) {
                    res.header("Access-Control-Expose-Headers", "ACCESS_TOKEN");
                    res.header("Access-Control-Expose-Headers", "ROL");
                    res.header("ACCESS_TOKEN", token);
                    res.header("ROL", usuario.get("rol").getAsString());
                    return "Usuario autenticado";
                } else {
                    res.status(200);
                    return "No logramos autenticar al usuario";
                }
            } else {
                res.status(200);
                return "Usuario o contraseña incorrectos";
            }
        });

        get("/olvide-contrasena", (req, res) -> {
            String correo = req.queryParams("correo");
            String token = JwtUtils.generateTokenPassword(correo);
            tokensPassword.put(correo, token);
            String mensaje = Correo.olvideContrasena(correo, token);
            res.status(200);
            fileBinario.writeBinaryFile(tokensPassword);
            tokensPassword = fileBinario.readBinaryFile();
            return mensaje;
        });

        put("/cambiar-contrasena", (req, res) -> {
            JsonObject data = gson.fromJson(req.body(), JsonObject.class);
            String token = data.get("token").getAsString();
            String contrasena = data.get("password").getAsString();
            for (Map.Entry<String, String> entry : tokensPassword.entrySet()) {
                if (entry.getValue().equals(token)) {
                    String isValidToken = JwtUtils.verifyToken(token);
                    if (isValidToken.equals("Token valido")) {
                        String email = JwtUtils.obtenerCorreo(token);
                        String message = ControladorUsuario.cambiarContrasena(email, Encriptar.encriptar(contrasena));
                        tokensPassword.remove(entry.getKey());
                        fileBinario.writeBinaryFile(tokensPassword);
                        tokensPassword = fileBinario.readBinaryFile();
                        return message;
                    }
                }
            }
            return "Ha vencido el tiempo para actualizar la contraseña";
        });

        // Rutas protegidas por el middleware
        get("/", (req, res) -> {
            System.out.println("Hola como estas");
            return "Hola mundo";
        });

        path("/direccion", () -> {
            post("/agregar-direccion", ControladorDireccion::crearDireccion);
        });

        path("/proveedor", () -> {
            post("/agregar", ControladorProveedor::registrarProveedor);
            get("/obtener", ControladorProveedor::obtenerProveedores);
            put("/actualizar-proveedor", ControladorProveedor::actualizarProveedor);
            delete("/eliminar", ControladorProveedor::eliminarProveedor);
        });

        path("/producto", () -> {
            post("/agregar-producto", ControladorProducto::crearProducto);
            get("/obtener-productos", ControladorProducto::obtenerProductos);
            get("/obtener-producto", ControladorProducto::obtenerProducto);
            delete("/eliminar-producto", ControladorProducto::eliminarProducto);
            put("/editar-producto", ControladorProducto::editarProducto);
            put("/configurar-stock-minimo", ControladorProducto::personalizarStockMinimo);
        });

        path("/venta", () -> {
            post("/guardar", ControladorVenta::guardarVenta);
            get("/diaria", ControladorVenta::ventaDiaria);
        });

        path("/reporte", () -> {
            post("/guardar", ControladorReporte::guardarReporte);
        });

        path("/producto-venta", () -> {
            get("/listaMasVendidos", ControladorProductoVenta::listaMasVendida);
            get("/listaMenosVendido", ControladorProductoVenta::listaMenosVendido);

        });

        path("/obra", () -> {
            post("/agregar", ControladorObra::guardarObra);
            get("/obtener", ControladorObra::obtenerObras);
            get("/obtener-obra", ControladorObra::obtenerById);
            delete("/eliminar", ControladorObra::eliminarObra); 
            put("/editar-proyecto", ControladorObra::editarProyecto);
        });

        path("/venta", () -> {
            put("/editar-venta", ControladorVenta::aV);
        });

        path("/pago", () -> {
            post("/agregar-pago", ControladorPago::crearPago);
        });

        path("/paquete", () -> {
            post("/agregar", ControladorPaquete::agregarPaquete);
            get("/obtener-paquete", ControladorPaquete::obtenerById);
            put("/editar", ControladorPaquete::editarPaquete);
            put("/eliminar", ControladorPaquete::eliminarPaquete);
        });

        path("/producto-paquete", () -> {
            post("/agregar", ControladorProducto_Paquete::agregarProductoPaquete);
        });

        post("/enviar-correo", (Request req, Response res) -> {
            JsonObject data = gson.fromJson(req.body(), JsonObject.class);
            String asunto = data.get("asunto").getAsString();
            String mensaje = data.get("mensaje").getAsString();
            String idPersona = req.queryParams("id");
            DAOPersona daoPersona = new DAOPersona();
            Persona persona = daoPersona.obtenerPersonaById(idPersona);
            JsonObject msjResponse = null;
            if (persona != null) {
                msjResponse = Correo.solicitarPedidoCorreo(persona.getCorreo(), asunto, mensaje);
            }
            return msjResponse;
        });

    
    
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}
