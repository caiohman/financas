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

  public String getButtonAddMsg(){ return buttonAddMsg; }
  public String getButtonSubMsg(){ return buttonSubMsg; }
  public String getButtonAddCompMsg(){ return buttonAddCompMsg; }
  public String getButtonASubCompMsg(){ return buttonSubCompMsg; }
  public String getButtonTransferMsg(){ return buttonTransferMsg; }

}
