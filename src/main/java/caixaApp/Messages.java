package caixaApp;

public class Messages{
  private String buttonAddMsg = "Digite o valor que deseja ADICIONAR ao saldo \n" +
                                "O valor precisa ser no formato 0.00\n"  +
                                "E depois clique aqui.";

  private String buttonSubMsg = "Digite o valor que deseja RETIRAR do saldo \n" +
                                "O valor precisa ser no formato 0.00\n"  +
                                "E depois clique aqui.";

  private String buttonAddCompMsg = "Preecha os campos de acordo com o padrão: \n" +
                                    "No campo do título adicionar um texto \n" +
                                    "No campo da data colocar no formato dd/mm/aaaaa \n" +
                                    "No campo do valor adiconar no formato 00.00 \n" +
                                    "Se o valor for para retirada do caixa coloque o sinal - antes" +
                                    "E depois clique aqui.";;

  private String buttonSubCompMsg = "Selecione a linha com o compromisso \n" +
                                    "que deseja REMOVER \n" +
                                    "E depois clique aqui " ;

  private String buttonTransferMsg = "Selecione a linha com o compromisso \n" +
                                     "que deseja adicionar ou remover do SALDO \n" +
                                     "E depois clique aqui" ;

  private String historyInfoMsg = "Coloque a data e o horário\n" +
                                  "Pode filtra o inicio preechendo só o primeiro campo\n" +
                                  "Pode filtra o final preechendo só o segundo campo\n" +
                                  "Pode filtrar ambos preenchendo os respectivos campos";

  private String errorMsgOne = "Valor inválido. Por favor, informe um valor válido";

  private String errorMsgTwo = "Valor inválido." +
                               "\nCerifique-se de colocar no formato 0.0";

  /* Getters */                             
  public String getButtonAddMsg(){ return buttonAddMsg; }
  public String getButtonSubMsg(){ return buttonSubMsg; }
  public String getButtonAddCompMsg(){ return buttonAddCompMsg; }
  public String getButtonASubCompMsg(){ return buttonSubCompMsg; }
  public String getButtonTransferMsg(){ return buttonTransferMsg; }
  public String getErrorMsgOne(){ return errorMsgOne; }
  public String getErrorMsgTwo(){ return errorMsgTwo; }
  public String getHistoryInfoMsg(){ return historyInfoMsg; }

}
