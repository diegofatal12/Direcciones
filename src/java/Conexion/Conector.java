/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.Connection;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author Usuario
 */
public class Conector {

    Connection conexion;

    public Conector() {
    }

    public Connection connect() {
        String ls_data_source = "Diego";
        Connection con_base = null;
        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup(ls_data_source);
            con_base = ds.getConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con_base;
    }

}
