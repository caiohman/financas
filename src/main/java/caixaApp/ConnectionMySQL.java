package caixaApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.sql.Timestamp;

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

    public void clearTable(){
      try{
        PreparedStatement clearStatement = conn.prepareStatement(
        "TRUNCATE compromissos" //faster than delete
        );
        clearStatement.executeUpdate();
        clearStatement.close();
      }catch(SQLException problem){
        System.out.println("SQLException: " + problem.getMessage());
      }
        return;
    }

    public int updateIdTable(boolean update){
      try{
        if(update == true){
          PreparedStatement updateStatement = conn.prepareStatement(
          "UPDATE id_gen set id = id + 1"
          );
          updateStatement.executeUpdate();
          updateStatement.close();
        }

        PreparedStatement queryStatement = conn.prepareStatement(
        "SELECT id FROM id_gen"
        );
        ResultSet result = queryStatement.executeQuery();
        int id = 0;
        if(result.next()){
          id = result.getInt("id");
        }
        queryStatement.close();
        return id;
      }catch(SQLException problem){
        System.out.println("SQLException: " + problem.getMessage());
      }
      return 0; //problem
    }

    public ArrayList<ArrayList<String>> queryFromTableCompromissos(){
      try{
        ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
        ArrayList<LocalDate> dateArray = new ArrayList<LocalDate>();
        int tableVPosition = 0;
        PreparedStatement queryStatement = conn.prepareStatement(
         "SELECT id , titulo , data , valor FROM compromissos"
        );
        ResultSet result = queryStatement.executeQuery();
        while(result.next()){
          table.add(new ArrayList<String>());
          table.get(tableVPosition).add( String.valueOf(result.getInt("id")));
          table.get(tableVPosition).add(result.getString("titulo"));

          //format data to Brazilian pattern
          java.sql.Date sqlDate = result.getDate("data");
          LocalDate localDate = new java.sql.Date (sqlDate.getTime()).toLocalDate();
          dateArray.add(localDate);
          String localDateString = localDate
          .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

          table.get(tableVPosition).add(localDateString);
          table.get(tableVPosition).add( String.valueOf(result.getFloat("valor")));
          tableVPosition++;
        }

        int length = dateArray.size();
        /* selection sort */
        for(int i = 0 ; i < (length - 1) ; i++){
          int lowest = i;
          for(int j = i + i ; j < length ; j++){
            Period period = Period.between(dateArray.get(lowest) ,
            dateArray.get(j));
            int daysDiff = period.getDays();
            if(daysDiff < 0){
                lowest = j;
            }
          }
          if(lowest != i){
              Collections.swap(dateArray , lowest , i);
              Collections.swap(table , lowest , i);
          }
        }
        queryStatement.close();
        return table;
      } catch(SQLException problem){
        System.out.println("SQLException: " + problem.getMessage());
      }
      return null;
    }
    /* @Description: set values into table    */
    /* @return: true if OK and false if not OK */
    public boolean setToTableCompromissos(String id , String title , String date ,
     String value ){
      boolean check = false;
      try{
        String isoDate = date.substring(6 , 10) + "-" + date.substring(3 , 5) +
        "-" + date.substring(0 , 2);

        java.sql.Date mysqlDate = java.sql.Date.valueOf(isoDate);

        PreparedStatement setStatement = conn.prepareStatement(
        "INSERT INTO compromissos(" +
        "id , titulo , data , valor) VALUES(? , ? , ? , ?)"
        );
        setStatement.setInt(1 , Integer.parseInt(id));
        setStatement.setString(2 , title);
        setStatement.setDate(3 , mysqlDate);
        setStatement.setBigDecimal(4 , new BigDecimal(value));
        setStatement.executeUpdate();
        setStatement.close();
        return check = true;
      }catch(SQLException problem){
        System.out.println("SQLException: " + problem.getMessage());
    }
    return check ;
  }

  public void setUsrsTable(String usr , byte [] pwd){
    try{
      PreparedStatement setStatement = conn.prepareStatement(
      "INSERT INTO usrs(usr , pwd) VALUES(? , ?)"
      );
      setStatement.setString(1 , usr);
      setStatement.setBytes(2 , pwd);
      setStatement.executeUpdate();
      setStatement.close();
    }
    catch(SQLException problem){
      System.out.println("SQLException: " + problem.getMessage());
    }
  }

  public byte [] querySpecificPwdUsrs(String usr){
    try{
      byte [] tablePwd = null;
      PreparedStatement queryStatement = conn.prepareStatement(
      "SELECT usr , pwd FROM usrs"
      );
      ResultSet result = queryStatement.executeQuery();
      while(result.next()){
        String tableUsr = result.getString("usr");
        if(tableUsr.equals(usr) == true){
           tablePwd = result.getBytes("pwd");
        }
      }
      queryStatement.close();
      return tablePwd;
    }catch(SQLException problem){
      System.out.println("SQLException: " + problem.getMessage());
    }
    return null;
  }
  /*only one per user*/
  public void setHashSalt(String usr , byte [] hs){
    try{
      PreparedStatement setStatement = conn.prepareStatement(
      "INSERT INTO usrss(usr , hs) VALUES(? , ?)"
      );
      setStatement.setString(1 , usr);
      setStatement.setBytes(2 , hs);
      setStatement.executeUpdate();
      setStatement.close();
    }
    catch(SQLException problem){
      System.out.println("SQLException: " + problem.getMessage());
    }
  }

  public byte [] querySpecificHashSalt(String usr){
    try{
      byte [] tableHs = null;
      PreparedStatement queryStatement = conn.prepareStatement(
      "SELECT usr , hs FROM usrss"
      );
      ResultSet result = queryStatement.executeQuery();
      while(result.next()){
        String tableUsr = result.getString("usr");
        if(tableUsr.equals(usr) == true){
           tableHs = result.getBytes("hs");
        }
      }
      queryStatement.close();
      return tableHs;
    }catch(SQLException problem){
      System.out.println("SQLException: " + problem.getMessage());
    }
    return null;
  }

  public BigDecimal querySaldo(){
    BigDecimal valueConverted = null;
    float saldo = 0;
    try{
      PreparedStatement queryStatement = conn.prepareStatement(
      "SELECT saldo from caixa"
      );
      ResultSet result = queryStatement.executeQuery();
      while(result.next())
          saldo = result.getFloat("saldo");
      valueConverted = new BigDecimal(saldo);
      queryStatement.close();
      return valueConverted;
    }catch(SQLException problem){
      System.out.println("SQLException: " + problem.getMessage());
    }
    return null;
  }

  public BigDecimal setToCaixa(float value , String descript){
    BigDecimal saldo = querySaldo();
    BigDecimal valueConverted = new BigDecimal(value);
    BigDecimal newSaldo = valueConverted.add(saldo);
    try{
      PreparedStatement setStatement = conn.prepareStatement(
      "INSERT INTO caixa(modifica , descricao , saldo , dt)" +
      " VALUES(? , ? , ? , now())"
      );
      setStatement.setBigDecimal(1 , valueConverted);
      setStatement.setString(2 , descript);
      setStatement.setBigDecimal(3 , newSaldo);
      setStatement.executeUpdate();
      setStatement.close();
      return newSaldo;
    }
    catch(SQLException problem){
      System.out.println("SQLException: " + problem.getMessage());
    }
    return null;
  }

  public ArrayList<CaixaInfo> queryCaixaTable(){
    ArrayList<CaixaInfo> item = new ArrayList<CaixaInfo>();

    try{
      PreparedStatement queryStatement = conn.prepareStatement(
      "SELECT modifica , descricao , saldo , dt from caixa"
      );
      ResultSet result = queryStatement.executeQuery();
      while(result.next()){
        float mod = result.getFloat("modifica");
        String descript = result.getString("descricao");
        float balance = result.getFloat("saldo");
        Timestamp dt = result.getTimestamp("dt");
        item.add(new CaixaInfo(mod , descript , balance , dt.toLocalDateTime()));
      }
      queryStatement.close();
      return item;
      }catch(SQLException problem){
        System.out.println("SQLException: " + problem.getMessage());
      }
      return null;
  }

    /*@Description: Call this function to check connection*/
    /* if null - connection failed                        */
    public Connection getConnection(){ return conn; }
}
