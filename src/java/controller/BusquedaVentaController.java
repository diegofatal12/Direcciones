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
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Venta;
import reportes.VentasPorFecha;

/**
 *
 * @author Usuario
 */
@Named(value = "busquedaVentaController")
@SessionScoped
public class BusquedaVentaController implements Serializable {

    /**
     * Creates a new instance of BusquedaVentaController
     */
    private List<VentasPorFecha> ventas = new ArrayList<VentasPorFecha>();
    private String parametro;
    public BusquedaVentaController() {
    }

    public List<VentasPorFecha> getVentas() {
        return ventas;
    }

    public void setVentas(List<VentasPorFecha> ventas) {
        this.ventas = ventas;
    }
    
    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }
    public void buscar() throws SQLException{
        FacesMessage msg;
        if(parametro==null||parametro.equals("")){
            msg = new FacesMessage("Debe ingresar un nombre o un apellido");
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
        ArrayList resultados =  new ArrayList();
        Connection conexion = null;
        try {
            //Conectamos a la base
            Conector conector = new Conector();
            conexion = conector.connect();
            s = conexion.prepareCall("{call buscar_venta_por_nombre_o_apellido(?)}");
            s.setString(1, parametro);
            r = s.executeQuery();
            while(r.next()){
                idventac = r.getInt(1);
                idclientec = r.getInt(2);
                clientec = r.getString(3);
                fechac = r.getDate(4);
                totalc = r.getBigDecimal(5);
                VentasPorFecha registro = new VentasPorFecha(idventac, idclientec, clientec, fechac, totalc);
                resultados.add(registro);
            }
            s.close();
            this.setVentas(resultados);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally{
            if (conexion != null) conexion.close();
            if (r != null) r.close();
        }
    }
    public String volverAtras(){
        this.ventas =  new ArrayList<>();
        this.parametro = new String();
        return "/index";
    }
}
