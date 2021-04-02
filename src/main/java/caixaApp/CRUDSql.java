package caixaApp;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class CRUDSql{

  /*perform connection to db*/
  private Connection conn;

  CRUDSql(Connection conn){
    this.conn = conn;
  }

  /* @Description: Clear table                                  */
  /* @param: none                                               */
  /* @Return : none                                             */
  public void clearTable(String table){
    try{
      PreparedStatement clearStatement = this.conn.prepareStatement(
      "TRUNCATE " + table //faster than delete
      );
      clearStatement.executeUpdate();
      clearStatement.close();
    }catch(SQLException problem){
      System.out.println("SQLException: " + problem.getMessage());
    }
      return;
  }

  /* @Description: Update table with values                          */
  /* @param: table name and value                                    */
  /* @Return : none                                                  */
  public int updateTable(String table , String value){
    try{
        PreparedStatement updateStatement = conn.prepareStatement(
        "UPDATE " + table + " set " + value
        );
        updateStatement.executeUpdate();
        updateStatement.close();
    }catch(SQLException problem){
      System.out.println("SQLException: " + problem.getMessage());
    }
    return 0; //problem
  }

  /* @Description: Create table with values                          */
  /* @param: table name and params                                   */
  /* @Return : none                                                  */
  public int createTables(String tableName, String tableParams){
    try{
      PreparedStatement createStatement = conn.prepareStatement(
      "CREATE TABLE " + tableName + "(  " + tableParams + " )"
      );
      createStatement.executeUpdate();
      createStatement.close();
    }catch(SQLException problem){
      System.out.println("SQLException: " + problem.getMessage());
    }
    return 0;
  }

  public boolean tableExist(String tableName) {
    boolean tExists = false;
    try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
        while (rs.next()) {
            String tName = rs.getString("TABLE_NAME");
            if (tName != null && tName.equals(tableName)) {
                tExists = true;
                break;
            }
        }
    }catch(SQLException problem){
      System.out.println("SQLException: " + problem.getMessage());
    }
    return tExists;
}
}
