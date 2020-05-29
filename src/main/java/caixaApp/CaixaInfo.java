package caixaApp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

  public BigDecimal getMod(){ return mod; }
  public BigDecimal getBalance(){ return balance; }
  public String getDescript(){return descript;}
  public LocalDateTime getDt(){ return dt; }

  public String getTime(){
    String localDateString = dt
    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    return localDateString;
  }
}
