package sa02;

import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jdo.Cesta;
import jdo.Producto;
import jdo.Usuario;

import javax.jdo.*;
import java.util.List;

@Path("productos")
public class ProductosResource {

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public static List<Producto> getProductos() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		Query<Producto> q = pm.newQuery(Producto.class);

		List<Producto> productos = q.executeList();

		pm.close();

		return productos;
	}

	@GET
	@Path("nom")
	@Produces(MediaType.APPLICATION_JSON)
	public static List<Producto> getProductosNom(@QueryParam("nombre") String nombre) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		List<Producto> productos = null;
		try {
			Query<Producto> q = pm.newQuery("SELECT FROM " + Producto.class.getName() + " WHERE nombre== '" + nombre + "'");

			productos = q.executeList();
		} catch (Exception e) {

		} finally {
			pm.close();
		}

		return productos;
	}

	@GET
	@Path("user")
	@Produces(MediaType.APPLICATION_JSON)
	public static List<Producto> getProductosUser(@QueryParam("usuario") String usuario) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		List<Producto> productos = null;

		try {
			Query<Producto> q = pm
					.newQuery("SELECT FROM " + Producto.class.getName() + " WHERE usuario== '" + usuario + "'");

			productos = q.executeList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return productos;
	}

	@POST
	@Path("elim")
	@Produces(MediaType.APPLICATION_JSON)
	public static void eliminarProducto(List<String> productoL) {
		String nombre = productoL.get(0);
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			Query<Producto> q = pm.newQuery("SELECT FROM " + Producto.class.getName() + " WHERE nombre== '" + nombre + "'");
			List<Producto> product = q.executeList();
			pm.deletePersistentAll(product);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}

	@POST
	@Path("ins")
	@Produces(MediaType.APPLICATION_JSON)
	public static void insertarProducto(List<String> productoL) {
		String nombre = productoL.get(0);
		String descripcion = productoL.get(1);
		String precio = productoL.get(2);
		String usuario = productoL.get(3);
		String cantidad = productoL.get(4);

		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Producto p = new Producto(nombre, descripcion, Double.parseDouble(precio), usuario,
					Integer.parseInt(cantidad));
			pm.makePersistent(p);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public static List<Producto> getProductosCesta(List<Cesta> cesta) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		List<Producto> productos;

		for (Cesta cestav : cesta) {
			try {
				Query<Producto> q = pm.newQuery(
						"SELECT FROM " + Producto.class.getName() + " WHERE nombre== '" + cestav.getNombreproducto() + "'");

				List<Producto> productosv = q.executeList();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pm.close();
			}
		}
		return null;
	}

	@POST
	@Path("update")
	@Produces(MediaType.APPLICATION_JSON)
	public static void modificarProducto(Producto p) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			Query<Producto> q = pm.newQuery("javax.jdo.query.SQL", "UPDATE producto SET nombre= '" + p.getNombre() + "',descripcion= '"
					+ p.getDescripcion() + "' ,precio= " + p.getPrecio() + " WHERE codigo= " + p.getCodigo());
			q.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}

	@POST
	@Path("modifprecio")
	@Produces(MediaType.APPLICATION_JSON)
	public static void modificarPrecio(Producto p) {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		try {
			Query<Producto> q = pm.newQuery("javax.jdo.query.SQL", "UPDATE producto SET precio= "
					+ p.getPrecio() + " WHERE codigo= '" + p.getCodigo() + "'");
			q.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("comprar")
	@Consumes(MediaType.APPLICATION_JSON)
	public static void comprarProductos(Usuario usuario) {

		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Producto producto = new Producto();


		List<Cesta> cestav = null;
		try {
			Query<Cesta> cesta = pm.newQuery("SELECT FROM "+Cesta.class.getName()+" WHERE NombreUsuario == '" + usuario.getUsername() + "'");
			cestav = cesta.executeList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		
		for (Cesta cesv : cestav) {
			try {
				producto.setNombre(cesv.getNombreproducto());

				Query qq = pm.newQuery("javax.jdo.query.SQL", "UPDATE producto SET CANTIDAD = CANTIDAD - 1   WHERE Nombre = '" + producto.getNombre() + "'");

				qq.execute();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pm.close();
			}
			
		}

		List<Producto> cestavg = null;
		try {
			Query<Producto> cestag = pm.newQuery("SELECT FROM "+Producto.class.getName());

			cestavg = cestag.executeList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		
		for (Producto p : cestavg) {

			if (p.getCantidad() < 5) {
				MandarMail mm = new MandarMail();
				mm.mandarStockAviso(usuario.getEmail(), p);
			} else {

			}

		}
		
		System.out.println("Boraamoscesta");
		Client cliente = ClientBuilder.newClient();
		final WebTarget appTarget = cliente.target("http://localhost:8080/myapp");
		final WebTarget cestaTarget = appTarget.path("cesta");
		WebTarget borrarTarget = cestaTarget.path("borrar");
		borrarTarget.request().post(Entity.entity(usuario, MediaType.APPLICATION_JSON));

		pm.close();

	}

}
