/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ClienteDao;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import modelo.Cliente;
import modelo.Direccion;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import persistencia.HibernateUtil;

/**
 *
 * @author Usuario
 */
@Named(value = "clienteController")
@SessionScoped
public class ClienteController implements Serializable {

    /**
     * Creates a new instance of clienteController
     */
    private List<Cliente> listaClientes;
    Cliente registroSel;
    Cliente registroMod;
    Direccion registroModDireccion;
    private List<Direccion> direcciones;

    public Cliente getRegistroSel() {
        return registroSel;
    }

    public void setRegistroSel(Cliente registroSel) {
        this.registroSel = registroSel;
    }

    public Cliente getRegistroMod() {
        return registroMod;
    }

    public void setRegistroMod(Cliente registroMod) {
        this.registroMod = registroMod;
    }

    public Direccion getRegistroModDireccion() {
        return registroModDireccion;
    }

    public void setRegistroModDireccion(Direccion registroModDireccion) {
        this.registroModDireccion = registroModDireccion;
    }

    public ClienteController() {

    }

    public List<Cliente> getListaCliente() {
        ClienteDao cd = new ClienteDao();
        listaClientes = cd.getAll();
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public void nuevo() {
        registroSel = new Cliente();
        registroMod = new Cliente();
    }

    public void agregarCliente() {
        ClienteDao cd = new ClienteDao();
        cd.agregar(registroMod);
        nuevo();
    }

    public void modificarCliente() {
        ClienteDao cd = new ClienteDao();
        registroMod = registroSel;
        cd.modificar(registroMod);
        nuevo();
    }
    public void vistaEditarCliente(){
        registroMod = registroSel;
    }
    public void eliminarCliente() {
        ClienteDao cd = new ClienteDao();
        cd.eliminar(registroSel);
        nuevo();
    }

    /*--------------------- TODO LO QUE TIENE QUE VER CON LA DIRECCIÓN ------------------------------*/
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------
    public String gestionarDirecciones() {
        if(registroSel!=null){
            /*Session session = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                this.registroMod = (Cliente) session.get(Cliente.class, registroSel.getId());
                Hibernate.initialize(this.registroMod.getDirecciones());
                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                return null;
            } finally {
                session.close();
            }*/
            ClienteDao cd =  new ClienteDao();
            registroMod = cd.getOne(registroSel.getId());
        }
        return "/gestionarDirecciones";
    }

    public void nuevaDireccion() {
        this.registroModDireccion = new Direccion();
    }

    public void agregarDireccionCliente() {
        Direccion direccion = new Direccion();
        direccion = registroModDireccion;
        direccion.setCliente(registroMod);
        this.getRegistroMod().getDirecciones().add(direccion);
        ClienteDao cd = new ClienteDao();
        cd.modificar(registroMod);
    }

    public void actualizaDireccionCliente() {
        this.registroMod.getDirecciones().add(registroModDireccion);
        ClienteDao cd = new ClienteDao();
        cd.modificar(registroMod);
    }

    public void eliminaDireccion() {
        this.registroMod.getDirecciones().remove(registroModDireccion);
        ClienteDao cd = new ClienteDao();
        cd.modificar(registroMod);

    }

    public String cancelar() {
        registroSel = new Cliente();
        registroModDireccion = new Direccion();
        return "/index";
    }
    /*--------------------------------------
    -
    --------------
    ------------
    ------------*/
    public String ventasPorFecha(){
        return "/VentasPorFecha";
    }
    public String busquedaVentas(){
        return "/BusquedaDeVentas";
    }
    public String totalVentasCliente(){
        return "/ventasPorFechaCliente";
    }
}
