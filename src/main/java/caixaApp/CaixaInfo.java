package caixaApp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CaixaInfo{
  private BigDecimal mod;
  private String descript;
  private BigDecimal balance;
  private LocalDateTime dt;

  CaixaInfo(float mod , String descript ,
  float balance, LocalDateTime dt){
    this.mod = new BigDecimal(mod);
    this.descript = descript;
    this.balance = new BigDecimal(balance);
    this.dt = dt;
  }

  /* Getters */
  public BigDecimal getMod(){ return mod; }
  public BigDecimal getBalance(){ return balance; }
  public String getDescript(){return descript;}
  public LocalDateTime getDt(){ return dt; }

  /* @Description: Get date and time                              */
  /* @param: none                                                 */
  /* @Return : String with date and time                          */
  public String getTime(){
    String localDateString = dt
    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    return localDateString;
  }

  /* @Description: Compare Dates                                                 */
  /* @param: dateTimeBrPattern - date in dd/MM/yyyy HH:mm:ss pattern             */
  /* @Return : int difference or -100 if error                                   */
  public int compareDate(String dateTimeBrPattern){
    try{
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
      LocalDateTime dateTime = LocalDateTime.parse(dateTimeBrPattern, formatter);
      return dateTime.compareTo(dt);
    }catch(DateTimeParseException exc){
      System.out.println(exc.getMessage());
    }
    return -100; //error
  }
}
