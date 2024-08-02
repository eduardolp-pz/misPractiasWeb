package controladores;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entidades.Coordenadas;
import entidades.Formulario;
import entidades.Usuario;
import io.javalin.http.Context;
import io.javalin.websocket.WsMessageContext;
import org.jetbrains.annotations.NotNull;
import servicios.AuthServicio;
import servicios.CoordenadasServicio;
import servicios.FormularioServicio;
import servicios.UsuarioServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;

public class FormularioControlador {
  private final static FormularioServicio formularioServicio = FormularioServicio.getInstancia();
  private final static UsuarioServicio usuarioServicio = UsuarioServicio.getInstancia();
  private final static CoordenadasServicio coordenadasServicio = CoordenadasServicio.getInstancia();

  public static void esAdmin(@NotNull Context ctx) {
    Usuario usuario = ctx.sessionAttribute("user");
    if (usuario == null) {
      ctx.redirect("/auth/login");
      return;
    }

    if (!usuario.isAdministrador() && !usuario.isEncuestador())
      ctx.redirect("/");
  }

  public static void vistaInicial(@NotNull Context ctx) {
    ctx.render("/public/templates/cover.html");
  }

  public static void vistaFormulario(@NotNull Context ctx) {
    ctx.render("/public/templates/formulario.html");
  }

  public static void postFormulario(@NotNull Context ctx) {
    String nombre = ctx.formParam("name");
    String sector = ctx.formParam("sector");
    String educacion = ctx.formParam("education");
    String username = ctx.formParam("username");
    Usuario usuario = usuarioServicio.findByUsername(username);
    String latitud = ctx.formParam("latitude").isEmpty() ? "0" : ctx.formParam("latitude");
    String longitud = ctx.formParam("longitude").isEmpty() ? "0" : ctx.formParam("longitude");


    Coordenadas coordenadas = coordenadasServicio.crear(latitud, longitud);
    formularioServicio.crear(nombre, sector, educacion, usuario, coordenadas);
    ctx.redirect("/dashboard");
  }

  public static void mostrarFormulario(@NotNull Context ctx) {
    String id = ctx.pathParam("id");
    Formulario formulario = formularioServicio.encontrar(id);

    Map<String, Object> model = new HashMap<>();
    model.put("form", formulario);

    ctx.render("/public/templates/showForm.html", model);
  }

  public static void editarFormulario(@NotNull Context ctx) {
    String id = ctx.pathParam("id");
    String nombre = ctx.formParam("name");
    String sector = ctx.formParam("sector");
    String educacion = ctx.formParam("education");
    String username = ctx.formParam("username");
    Usuario usuario = usuarioServicio.findByUsername(username);

    formularioServicio.modificar(id, nombre, sector, educacion, usuario);
    ctx.redirect("/form/" + id);
  }

  public static void formulariosOffline(@NotNull Context ctx) {
    ctx.render("/public/templates/formOffline.html");
  }

  public static void dashboardOffline(@NotNull Context ctx) {
    ctx.render("/public/templates/dashboardOffline.html");
  }

  public static void onMessage(@NotNull WsMessageContext ctx) {
    String json = ctx.message();
    System.out.println(json);
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      JsonNode jsonNode = objectMapper.readTree(json);
      String nombre = jsonNode.get("name").asText();
      String sector = jsonNode.get("sector").asText();
      String educacion = jsonNode.get("educationLevel").asText();
      String username = jsonNode.get("username").asText();
      Usuario usuario = usuarioServicio.findByUsername(username);
      String latitude = jsonNode.get("latitude").asText();
      String longitude = jsonNode.get("longitude").asText();
      Coordenadas coordenadas = coordenadasServicio.crear(latitude, longitude);
      formularioServicio.crear(nombre, sector, educacion, usuario, coordenadas);
    } catch (Exception ignored) {
    }
  }

  public static void getUserForms(@NotNull Context ctx) {
    String token = ctx.header("Authorization");
    if (token == null) {
      ctx.status(401).result("Token invalido");
      return;
    }

    try {
      Algorithm algorithm = Algorithm.HMAC256("qwertytest");
      JWT.require(algorithm).build().verify(token);
    } catch (JWTVerificationException exception) {
      ctx.status(401).result("Token invalido");
      return;
    }

    Usuario usuario = usuarioServicio.findByUsername(ctx.pathParam("username"));
    if (usuario == null) {
      Map<String, Object> model = new HashMap<>();
      model.put("user", "not found");
      ctx.json(model);
      return;
    }

    List<Formulario> allUserForms = formularioServicio.encontrarPorUsuario(usuario);
    Map<String, Object> model = new HashMap<>();
    model.put("forms", allUserForms);
    ctx.status(200).json(model);
  }

  public static void createNewForm(@NotNull Context ctx) {
    String token = ctx.header("Authorization");
    if (token == null) {
      Map<String, Object> model = new HashMap<>();
      model.put("auth", "not found");
      ctx.status(401).json(model);
      return;
    }

    try {
      Algorithm algorithm = Algorithm.HMAC256("qwertyTest");
      JWT.require(algorithm).build().verify(token);
    } catch (JWTVerificationException exception) {
      Map<String, Object> model = new HashMap<>();
      model.put("token", "not valid");
      ctx.status(401).json(model);
      return;
    }

    Gson gson = new Gson();
    Map<String, Object> requestBody = gson.fromJson(ctx.body(), Map.class);
    String name = (String) requestBody.get("name");
    String sector = (String) requestBody.get("sector");
    String educationLevel = (String) requestBody.get("educationLevel");
    String userId = (String) requestBody.get("userId");
    String latitude = (String) requestBody.get("latitude");
    String longitude = (String) requestBody.get("longitude");

    Usuario user = usuarioServicio.findDbByID(userId);
    if (user == null) {
      Map<String, Object> model = new HashMap<>();
      model.put("user", "not found");
      ctx.status(404).json(model);
      return;
    }

    Coordenadas coordenadas = coordenadasServicio.crear(latitude, longitude);
    Formulario form = formularioServicio.crear(name, sector, educationLevel, user, coordenadas);

    Map<String, Object> model = new HashMap<>();
    model.put("form", form);
    ctx.status(201).json(model);
  }

  public static void restLogin(@NotNull Context ctx) {
    Gson gson = new Gson();
    Map<String, Object> requestBody = gson.fromJson(ctx.body(), Map.class);
    String username = (String) requestBody.get("username");
    String password = (String) requestBody.get("password");

    Usuario usuario = usuarioServicio.findByUsername(username);
    if (usuario == null || !usuarioServicio.checkPassword(username, password)) {
      ctx.status(401).json(new HashMap<String, String>() {{
        put("message", "Invalid username or password");
      }});
      return;
    }

    Algorithm algorithm = Algorithm.HMAC256("qwertytest");
    String token = JWT.create()
      .withSubject(usuario.getUsuario())
      .sign(algorithm);

    ctx.status(200).json(new HashMap<String, String>() {{
      put("token", token);
    }});
  }
}
