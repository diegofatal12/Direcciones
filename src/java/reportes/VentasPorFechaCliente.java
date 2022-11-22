/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Usuario
 */
public class VentasPorFechaCliente {
    private int idCliente; 
    private String cliente;
    private int idVenta;
    private Date fecha;
    private BigDecimal total;

    public VentasPorFechaCliente() {
    }
    
    
    public VentasPorFechaCliente(int idCliente, String cliente, int idVenta, Date fecha, BigDecimal total) {
        this.idCliente = idCliente;
        this.cliente = cliente;
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.total = total;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
}
