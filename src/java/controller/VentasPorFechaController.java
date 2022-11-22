/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Conexion.Conector;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;
import reportes.VentasPorFecha;
import java.sql.Connection;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Usuario
 */
@Named(value = "ventasPorFechaController")
@SessionScoped
public class VentasPorFechaController implements Serializable {

    /**
     * Creates a new instance of VentasPorFechaController
     */
    public VentasPorFechaController() {
        ventasPorFecha = new ArrayList<VentasPorFecha>();
        fecha1 = new Date();
        fecha2 = new Date();
    }
    private List<VentasPorFecha> ventasPorFecha;
    private Date fecha1, fecha2;

    public List<VentasPorFecha> getVentasPorFecha() {
        return ventasPorFecha;
    }

    public void setVentasPorFecha(List<VentasPorFecha> ventasPorFecha) {
        this.ventasPorFecha = ventasPorFecha;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }

    public void consultarVentasPorFecha() throws SQLException {
        FacesMessage msg;
        if(fecha1==null){
            msg =  new FacesMessage("Debe ingresar la fecha inicial de la busqueda");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        if(fecha2==null){
            msg = new FacesMessage("Debe ingresar la fecha final de la búsqueda");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        CallableStatement s = null;
        ResultSet r = null;
        int idventac;
        int idclientec;
        String clientec;
        Date fechac;
        BigDecimal totalc;
        ArrayList resultados = new ArrayList();
        Connection conexion = null;
        try {
            //Conextamos a la base
            Conector conector = new Conector();
            conexion = conector.connect();
            s = conexion.prepareCall("{call filtrar_ventas_por_fecha(?, ?)}");          
            s.setDate(1,new java.sql.Date(fecha1.getTime()));
            s.setDate(2, new java.sql.Date( fecha2.getTime()));
            r= s.executeQuery();
            while(r.next()){
                idventac =  r.getInt(1);
                idclientec =  r.getInt(2);
                clientec = r.getString(3);
                fechac = r.getDate(4);
                totalc = r.getBigDecimal(5);
                VentasPorFecha registro = new VentasPorFecha(idventac, idclientec, clientec, fechac, totalc);
                resultados.add(registro);
            }
            s.close();
            this.setVentasPorFecha(resultados);
        } catch (SQLException e) {
            msg = new FacesMessage("Error: " + e.getCause().getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        finally{
            if (conexion != null) conexion.close();
            if (r != null) r.close();
        }
    }
    public String volverAtras(){
        return "/index";
    }
}
