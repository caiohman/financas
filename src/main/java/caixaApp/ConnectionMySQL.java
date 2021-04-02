package caixaApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class ConnectionMySQL {

  Connection conn = null;
  Keys keys = new Keys(); //infos of db
  private final String DATABASE_NAME = keys.getDatabaseName();
  private final String INSTANCE_CONNECTION_NAME = keys.getInstanceConnName();
  private final String  MYSQL_USER_NAME = keys.getMysqlUserName();
  private final String MYSQL_USER_PASSWORD = keys.getMysqlUserPassword();
  private final String MYSQL_ADDRESS_PORT = keys.getMysqlAddressPort();

    /* @Description: Init connection to db                             */
    /* @param: none                                                    */
    /* @Return : none                                                  */
    ConnectionMySQL() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(Exception ex) {
            // handle the error
        }
      try{
          conn = DriverManager.getConnection(
           "jdbc:mysql://" + MYSQL_ADDRESS_PORT + DATABASE_NAME ,
           MYSQL_USER_NAME ,
           MYSQL_USER_PASSWORD
          );

      } catch(SQLException problem){
        System.out.println("SQLException: " + problem.getMessage());
      }
    }

    /* @Description: Call this function to check connection*/
    /* @param: none                                        */
    /* @Return: if null - connection failed                */
    public Connection getConnection(){ return conn; }

}
