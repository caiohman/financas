package caixaApp;

public class Credit{
  private boolean interest; /* false without interest, true with interest*/
  private boolean type; /* false to simple interest and true to compound interest */
  private int value; /*principal amount*/
  private float rate; /* interest rate*/
  private int time; /* term of loan */

  Credit(boolean interest, boolean type, int value, float rate, int time){
    this.interest = interest;
    this.type = type;
    this.value = value;
    this.rate = rate;
    this.time = time;
  }

  Credit(boolean iterest, int value, int time){
    this.interest = interest;
    this.value = value;
    this.time = time;
  }

  public boolean getType(){ return type; }
  public boolean getInterest(){ return interest; }
  public int getValue(){ return value; }
  public float getRate(){ return rate; }
  public int getTime(){ return time; }


  public float totalAmount(){
    if(interest == false){
      return value;
    }
    else{
      if(type == false){
        return (value * rate * time) + value;
      }
      else{
        float total = 0;
        for(int i = 0 ; i < time ; i++){
          total += (1 + rate);
        }
        return total * value;
      }
    }

  }

  public float installments(int number){
    if(interest == false){
      return value / time;
    }
    else{
      if(type == false){
        return ((value * rate) + (value / time));
      }
      else{
        float accumulated = 0, installment = 0;
        for(int i = 0 ; i < number ; i++){
          accumulated = value * rate + accumulated;
          installment = (value / time) + accumulated;
        }
        return installment;
      }
    }

  }


}
