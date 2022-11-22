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
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import reportes.VentasPorFechaCliente;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Usuario
 */
@Named(value = "reporteCliente")
@SessionScoped
public class ReporteClienteController implements Serializable {

    /**
     * Creates a new instance of ReporteCliente
     */
    private List<VentasPorFechaCliente> lista;
    private TimeZone Zona = TimeZone.getTimeZone("America/Buenos_Aires");
    private java.util.Date fecha1;
    private java.util.Date fecha2;

    public ReporteClienteController() {
        lista = new ArrayList<VentasPorFechaCliente>();
        fecha1 = new Date();
        fecha2 = new Date();
    }

    public List<VentasPorFechaCliente> getLista() {
        return lista;
    }

    public void setLista(List<VentasPorFechaCliente> lista) {
        this.lista = lista;
    }

    public TimeZone getZona() {
        return Zona;
    }

    public void setZona(TimeZone Zona) {
        this.Zona = Zona;
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

    public void buscaListaDatos() throws SQLException {
        FacesMessage msg;
        CallableStatement s = null;
        ResultSet r = null;
        int idClientec;
        String clientec;
        int idVentac;
        Date fechac;
        BigDecimal totalc;
        ArrayList resultados = new ArrayList();
        Connection conexion = null;
        if (fecha1 == null) {
            msg = new FacesMessage("Debe ingresar la fecha inicial de la busqueda");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        if (fecha2 == null) {
            msg = new FacesMessage("Debe ingresar la fecha final de la busqueda");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        try {
            //Conectamos a la base de datos
            Conector conector = new Conector();
            conexion = conector.connect();
            s = conexion.prepareCall("{call ventas_por_fecha_cliente(?, ?)}");
            s.setDate(1, new java.sql.Date(fecha1.getTime()));
            s.setDate(2, new java.sql.Date(fecha2.getTime()));

            r = s.executeQuery();
            while (r.next()) {
                idClientec = r.getInt(1);
                clientec = r.getString(2);
                idVentac = r.getInt(3);
                fechac = r.getDate(4);
                totalc = r.getBigDecimal(5);
                VentasPorFechaCliente registro = new VentasPorFechaCliente(idClientec, clientec, idVentac, fechac, totalc);
                resultados.add(registro);
            }
            s.close();
            this.setLista(resultados);
        } catch (SQLException e) {
            msg = new FacesMessage("Error " + e.getCause().getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } finally {
            if (conexion != null) {
                conexion.close();
            }
            if (r != null) {
                r.close();
            }
        }
    }

    public String volverAtras() {
        return "/index";
    }
}
