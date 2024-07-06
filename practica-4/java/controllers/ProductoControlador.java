package controllers;

import encapsulation.Comentario;
import encapsulation.Foto;
import encapsulation.Producto;
import encapsulation.Usuario;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import org.jetbrains.annotations.NotNull;
import services.ComentarioService;
import services.FotoService;
import services.ProductoServicio;
import services.UsuarioServicio;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductoControlador {
  private final static ProductoServicio productoServicio = ProductoServicio.getInstance();
  private final static FotoService fotoService = FotoService.getInstance();
  private final static ComentarioService comentarioService = ComentarioService.getInstance();
  private final static UsuarioServicio usuarioServicio = UsuarioServicio.getInstance();

  public static void listarProductos(@NotNull Context ctx) throws Exception {
    Usuario usuario = new Usuario("admin", "Administrador", "admin", true);
    Usuario adminExiste = usuarioServicio.getUsuario("admin");
    if (adminExiste == null) usuarioServicio.dbCreate(usuario);
    Usuario usuario2 = new Usuario("usuario", "Usuario", "usuario", false);
    Usuario usuarioExiste = usuarioServicio.getUsuario("usuario");
    if (usuarioExiste == null) usuarioServicio.dbCreate(usuario2);

    String pagina = ctx.queryParam("pagina");
    if (pagina == null) {
      ctx.redirect("/comprar?pagina=1");
      return;
    }

    List<Producto> listaProductos = productoServicio.getListaProductosPaginados(Integer.parseInt(pagina));
    Map<String, Object> listaProductosMap = new HashMap<>();
    listaProductosMap.put("productos", listaProductos);
    listaProductosMap.put("paginaSiguiente", Integer.parseInt(pagina) + 1);
    listaProductosMap.put("paginaAnterior", Integer.parseInt(pagina) - 1);
    ctx.render("/public/templates/comprar.html", listaProductosMap);
  }

  public static void listarProductosAdmin(@NotNull Context ctx) throws Exception {
    List<Producto> listaProductos = productoServicio.getListaProductos();
    Map<String, Object> listaProductosMap = new HashMap<>();
    listaProductosMap.put("productos", listaProductos);
    listaProductosMap.put("titulo", "Crear Producto");
    listaProductosMap.put("boton", "Crear Producto");
    ctx.render("/public/templates/administrar-productos.html", listaProductosMap);
  }

  public static void listarProductosEditar(@NotNull Context ctx) throws Exception {
    List<Producto> listaProductos = productoServicio.getListaProductos();
    int id = Integer.parseInt(ctx.pathParam("id"));
    Producto producto = productoServicio.getProducto(id);
    Map<String, Object> listaProductosMap = new HashMap<>();
    listaProductosMap.put("productos", listaProductos);
    listaProductosMap.put("producto", producto);
    listaProductosMap.put("titulo", "Editar Producto");
    listaProductosMap.put("boton", "Editar Producto");
    listaProductosMap.put("nombre", producto.getNombre());
    listaProductosMap.put("precio", producto.getPrecio());
    listaProductosMap.put("descripcion", producto.getDescripcion());

    ctx.render("/public/templates/administrar-productos.html", listaProductosMap);
  }

  public static void eliminarProducto(@NotNull Context ctx) throws Exception {
    int idProducto = Integer.parseInt(ctx.pathParam("id"));
    productoServicio.deleteProducto(idProducto);
    ctx.redirect("/productos");
  }

  public static void agregarProducto(@NotNull Context context) {
    String nombre = context.formParam("nombre");
    String precio = context.formParam("precio");
    String descripcion = context.formParam("descripcion");
    UploadedFile file = context.uploadedFile("foto");
    Foto foto = fotoService.create(file);


    assert precio != null;
    BigDecimal precioDecimal = BigDecimal.valueOf(Float.parseFloat(precio));

    Producto producto = new Producto(nombre, precioDecimal, descripcion, List.of(foto));
    productoServicio.addProducto(producto);
    context.redirect("/productos");
  }

  public static void editarProducto(@NotNull Context context) {
    int id = Integer.parseInt(context.pathParam("id"));
    Producto producto = productoServicio.getProducto(id);
    String nombre = context.formParam("nombre");
    String precio = context.formParam("precio");
    String descripcion = context.formParam("descripcion");
    UploadedFile file = context.uploadedFile("foto");


    assert precio != null;
    BigDecimal precioDecimal = BigDecimal.valueOf(Float.parseFloat(precio));

    producto.setNombre(nombre);
    producto.setPrecio(precioDecimal);
    producto.setDescripcion(descripcion);
    producto.getFotos().add(fotoService.create(file));
    productoServicio.updateProducto(producto);
    context.redirect("/productos");
  }

  public static void visualizarProducto(@NotNull Context context) {
    int id = Integer.parseInt(context.pathParam("id"));
    Producto producto = productoServicio.getProducto(id);
    Map<String, Object> modelo = new HashMap<>();
    modelo.put("producto", producto);
    modelo.put("fotos", producto.getFotos());
    modelo.put("comentarios", producto.getComentarios());
    context.render("/public/templates/producto.html", modelo);
  }

  public static void agregarComentario(@NotNull Context context) {
    int id = Integer.parseInt(context.pathParam("id"));
    Producto producto = productoServicio.getProducto(id);
    String comentario = context.formParam("comentario");
    Usuario autor = context.sessionAttribute("usuario");
    System.out.println(autor.getUsuario());

    Comentario nuevoComentario = new Comentario();
    nuevoComentario.setComentario(comentario);
    nuevoComentario.setAutor(autor);
    nuevoComentario.setProducto(producto);
    ComentarioService.getInstance().create(nuevoComentario);

    productoServicio.agregarComentario(producto, nuevoComentario);
    context.redirect("/productos/visualizar/" + id);
  }

  public static void eliminarComentario(@NotNull Context context) {
    int id = Integer.parseInt(context.pathParam("id"));
    int idComentario = Integer.parseInt(context.pathParam("comentarioId"));
    Producto producto = productoServicio.getProducto(id);
    List<Comentario> comentarios = producto.getComentarios();
    comentarios.removeIf(c -> c.getId() == idComentario);
    productoServicio.eliminarComentario(producto, comentarios);
    ComentarioService.getInstance().delete(String.valueOf(idComentario));
    context.redirect("/productos/visualizar/" + id);
  }
}
