package sa02;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jdo.Producto;
import jdo.VentaProducto;
//import jdo.Producto;


@Path("ventasproductos")
public class VentaProductoResource {

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public static List<VentaProducto> getVentaProducto() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        PersistenceManager pm = pmf.getPersistenceManager();

        Query<VentaProducto> q = pm.newQuery(VentaProducto.class);

        List<VentaProducto> ventaProducto = q.executeList();

        pm.close();

        return ventaProducto;
    }

    @GET
    @Path("producto")
    @Produces(MediaType.APPLICATION_JSON)
    public static List<VentaProducto> getProductosNom(@QueryParam("producto") String producto) {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        PersistenceManager pm = pmf.getPersistenceManager();

        Query<VentaProducto> q = pm.newQuery("SELECT FROM " + VentaProducto.class.getName() + " WHERE producto== '" + producto + "'");

        List<VentaProducto> ventasProducto = q.executeList();

        pm.close();

        return ventasProducto;
    }

    @GET
    @Path("usuario")
    @Produces(MediaType.APPLICATION_JSON)
    public static List<VentaProducto> getProductosUser(@QueryParam("usuario") String usuario) {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        PersistenceManager pm = pmf.getPersistenceManager();

        Query<VentaProducto> q = pm.newQuery("SELECT FROM " + VentaProducto.class.getName() + " WHERE usuario== '" + usuario + "'");

        List<VentaProducto> productos = q.executeList();

        pm.close();

        return productos;
    }

    @POST
    @Path("elim")
    @Produces(MediaType.APPLICATION_JSON)
    public static void eliminarProducto(List<String> VentaProductoL) {
        String usuario = VentaProductoL.get(0);
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        PersistenceManager pm = pmf.getPersistenceManager();
        Query<VentaProducto> q = pm.newQuery("SELECT FROM " + VentaProducto.class.getName() + " WHERE usuario== '" + usuario + "'");
        List<VentaProducto> product = q.executeList();
        pm.deletePersistentAll(product);
        pm.close();
    }

    @POST
    @Path("ins")
    @Produces(MediaType.APPLICATION_JSON)
    public static void insertarProducto(List<String> productoL) {
        String producto = productoL.get(0);
        String usuario = productoL.get(1);
        String cantidad = productoL.get(2);

        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            VentaProducto p = new VentaProducto(producto, usuario, Integer.parseInt(cantidad));
            pm.makePersistent(p);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @POST
    @Path("updatequantity")
    @Produces(MediaType.APPLICATION_JSON)
    public static void setCantidad(VentaProducto vp) {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        PersistenceManager pm = pmf.getPersistenceManager();
        Query<VentaProducto> q = pm.newQuery("UPDATE " + VentaProducto.class.getName() + "SET cantidad= "+ vp.getCantidad() +",  WHERE producto= '"+ vp.getProducto() +"' AND usuario= '"+ vp.getUsuario() +"'");

        q.execute();
    }
}
