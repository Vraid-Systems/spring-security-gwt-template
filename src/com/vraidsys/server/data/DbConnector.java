/**
 * Build-Id: EMPTY
 *
 * create MSSQL java.sql.Connection from .properties file configuration values
 */
package com.vraidsys.server.data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jason Zerbe
 */
public class DbConnector {

    private static final String kDbHostKey = "kDbHost";
    private static final String kDefaultDbHost = "localhost";
    private static final String kDbNameKey = "kDbName";
    private static final String kDefaultDbName = "Some_Db";
    private static final String kDbUserKey = "kDbUser";
    private static final String kDefaultDbUser = "username";
    private static final String kDbPassKey = "kDbPass";
    private static final String kDefaultDbPass = "password";

    private Properties myProperties = null;

    public DbConnector(final String thePropertiesFileName) {
        // TODO: change location for your application
        final InputStream aInputStream = this.getClass().getResourceAsStream(
                        "/com/vraidsys/server/data/" + thePropertiesFileName);
        this.myProperties = new Properties();
        try {
            this.myProperties.load(aInputStream); // source of throws
        } catch (final IOException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE,
                            null, ex);
            this.myProperties = null;
        }
        try {
            aInputStream.close();
        } catch (final IOException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.WARNING,
                            null, ex);
        }
    }

    private String getDbHost() {
        if ((this.myProperties == null) || this.myProperties.isEmpty()) {
            return DbConnector.kDefaultDbHost;
        } else {
            return this.myProperties.getProperty(DbConnector.kDbHostKey,
                            DbConnector.kDefaultDbHost);
        }
    }

    private String getDbName() {
        if ((this.myProperties == null) || this.myProperties.isEmpty()) {
            return DbConnector.kDefaultDbName;
        } else {
            return this.myProperties.getProperty(DbConnector.kDbNameKey,
                            DbConnector.kDefaultDbName);
        }
    }

    private String getDbPass() {
        if ((this.myProperties == null) || this.myProperties.isEmpty()) {
            return DbConnector.kDefaultDbPass;
        } else {
            return this.myProperties.getProperty(DbConnector.kDbPassKey,
                            DbConnector.kDefaultDbPass);
        }
    }

    private String getDbUser() {
        if ((this.myProperties == null) || this.myProperties.isEmpty()) {
            return DbConnector.kDefaultDbUser;
        } else {
            return this.myProperties.getProperty(DbConnector.kDbUserKey,
                            DbConnector.kDefaultDbUser);
        }
    }

    public Connection getSqlConnection() {
        Connection aConnection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (final ClassNotFoundException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE,
                            null, ex);
        }
        try {
            aConnection = DriverManager.getConnection(
                            "jdbc:sqlserver://" + this.getDbHost()
                                            + ";databaseName="
                                            + this.getDbName(),
                            this.getDbUser(), this.getDbPass());
        } catch (final SQLException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE,
                            null, ex);
        }
        return aConnection;
    }
}
