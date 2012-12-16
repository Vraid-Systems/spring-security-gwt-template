/**
 * Build-Id: EMPTY
 */
package com.vraidsys.server.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vraidsys.shared.FooObj;

/**
 * @author jzerbe
 */
public class FooModel {

    public static List<FooObj> get(final String theUserName) {
        final ArrayList<FooObj> aRetList = new ArrayList<FooObj>();

        final Connection aSqlConnection = new DbConnector("foo.db.properties")
                        .getSqlConnection();

        try {
            final CallableStatement aCStmt = aSqlConnection
                            .prepareCall("{call ApplicationList_Get(?)}");

            try {
                aCStmt.setString("in_UserName", theUserName);

                final ResultSet aResultSet = aCStmt.executeQuery();

                try {
                    while (aResultSet.next()) {
                        final FooObj aFooObj = new FooObj();
                        aFooObj.setId(aResultSet.getInt("id"));
                        aFooObj.setName(aResultSet.getString("name"));

                        aRetList.add(aFooObj);
                    }
                } catch (final SQLException ex) {
                    Logger.getLogger(FooModel.class.getName()).log(
                                    Level.WARNING, null, ex);
                } finally {
                    if (aResultSet != null) {
                        aResultSet.close();
                    }
                }
            } catch (final SQLException ex) {
                Logger.getLogger(FooModel.class.getName()).log(Level.WARNING,
                                null, ex);
            } finally {
                if (aCStmt != null) {
                    aCStmt.close();
                }
            }
        } catch (final SQLException ex) {
            Logger.getLogger(FooModel.class.getName()).log(Level.SEVERE, null,
                            ex);
        } finally {
            if (aSqlConnection != null) {
                try {
                    aSqlConnection.close();
                } catch (final SQLException ex) {
                    Logger.getLogger(FooModel.class.getName()).log(
                                    Level.SEVERE, null, ex);
                }
            }
        }

        return aRetList;
    }
}
