package caixaApp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.input.MouseEvent;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.beans.binding.Bindings;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.*;
import java.util.ArrayList;


public class Telas{
    private Scene login, principal, compromissos, signInScene;
    private Stage primaryStage;
    private String loginStr, passwordStr;
    private TableView<TableInfo> table;
    private User user;
    /*perform connection to db*/
    private ConnectionMySQL conn = new ConnectionMySQL();
    /*Messages to show*/
    private Messages msg = new Messages();
    /*boolean to check authorization */
    boolean authorization = true;
    /*boolean to check request to insert value from compromissos table to saldo*/
    SimpleBooleanProperty tableUpdateRequest = new SimpleBooleanProperty(false);
    /**/
    Credit creditPayment;

    public Telas(Stage primaryStage)
    {
        this.login = new Scene(telaLogin(), 753, 377);
        this.login.getStylesheets().add(this.getClass().getResource("/lstyle.css").toExternalForm());
        this.compromissos = new Scene(compScreen() , 753 , 377);
        this.compromissos.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());
        this.principal = new Scene(basicConf(), 753, 377);
        this.principal.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());

        this.primaryStage = primaryStage;
    }

    //Getters and setters
    public String getPasswordStr()
    {
        return this.passwordStr;
    }
    public void setPasswordStr(String passwordStr)
    {
        this.passwordStr = passwordStr;
    }
    public String getLoginStr()
    {
        return this.loginStr;
    }
    public void setLoginStr(String loginStr)
    {
        this.loginStr = loginStr;
    }
    public Scene getLogin()
    {
        return this.login;
    }
    public void setLogin(Scene loginScene)
    {
        this.login = loginScene;
    }
    public Scene getPrincipal()
    {
        return this.principal;
    }
    public void setPrincipal(Scene principalScene)
    {
        this.principal = principalScene;
    }
    public Scene getCompromissos()
    {
        return this.compromissos;
    }
    public void setCompromissos(Scene compromissosScene)
    {
        this.compromissos = compromissosScene;
    }
    public Scene getSignInScene()
    {
        return this.signInScene;
    }
    public void setSignInScene(Scene signInScene)
    {
        this.signInScene = signInScene;
    }

    /* @Description: Login Screen config           */
    /* @param: none                                */
    /* @Return : class with screen config          */
    private GridPane telaLogin()
    {
        //Variaveis da tela de login
        GridPane layout = new GridPane();
        //layout.setId("set-border"); // for debug
        Text title = new Text("Bem vindo!");
        title.setId("title-text");
        Label username = new Label("Login");
        username.setId("label-text");
        TextField userTextField = new TextField();
        Label passwrd = new Label("Senha");
        passwrd.setId("label-text");
        PasswordField passwrdField = new PasswordField();
        Button btnLogin = new Button("Login");
        btnLogin.setId("bank-buttons");

        //ajustes de tela
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.add(title, 0, 0, 2, 1);
        layout.add(username, 0, 1);
        layout.add(userTextField, 1, 1);
        layout.add(passwrd, 0, 2);
        layout.add(passwrdField, 1, 2);
        layout.add(btnLogin , 1 , 4);
        layout.setHalignment(btnLogin , HPos.RIGHT);

        btnLogin.disableProperty().bind( // lock login button while password textfiel is empty
          Bindings.createBooleanBinding( () ->
            passwrdField.getText().trim().isEmpty(), passwrdField.textProperty()
          )
        );

        //Acionamento dos botoes
        btnLogin.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {
              //perform user validation
              byte [] usr , usr2;
              user = new User(
               userTextField.getText(),
               passwrdField.getText()
              );
              byte [] hs = conn.querySpecificHashSalt(user.getLogin()); //get hash salt stored
              usr = user.hash(hs); //perfom hash
              usr2 = conn.querySpecificPwdUsrs(user.getLogin()); //get user hash stored
              boolean validation = user.comparePasswords(usr , usr2); //compare to validate

              /*clear infos typed*/
              userTextField.clear();
              passwrdField.clear();

              if(validation == true)
                  primaryStage.setScene(getPrincipal());
                else
                {
                    Alert errorSub = new Alert(Alert.AlertType.ERROR);
                    errorSub.setTitle("ERRO");
                    errorSub.setHeaderText("Login nao encontrado");
                    errorSub.setContentText("Por favor, tente novamente");
                    errorSub.showAndWait();
                }
            }
        });
        return layout;
    }

    /* @Description: UpperBar to perform Scene Transition           */
    /* @param: none                                                 */
    /* @Return : class with UpperBar                                */
    private HBox SceneTransition(){
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        hbox.setAlignment(Pos.TOP_RIGHT);

        Button buttonCurrent = new Button("Caixa");
        buttonCurrent.setPrefSize(100, 20);
        buttonCurrent.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                primaryStage.setScene(getPrincipal());
            }
        });
        Button buttonProjected = new Button("Compromissos");
        buttonProjected.setPrefSize(100, 20);
        buttonProjected.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                primaryStage.setScene(getCompromissos());
            }
        });
        Button buttonReturn = new Button("Retornar");
        buttonReturn.setPrefSize(100, 20);
        buttonReturn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                primaryStage.setScene(getLogin());
            }
        });
        hbox.getChildren().addAll(buttonReturn, buttonCurrent, buttonProjected);
        return hbox;
	 }

   /* @Description: Caixa Screen config           */
   /* @param: none                                */
   /* @Return : class with screen config          */
    private GridPane basicConf() {

        /* Configura a tela */
        GridPane layout = new GridPane();
        //layout.setId("set-border"); // for debug
        layout.setHalignment(layout, HPos.CENTER);
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(20, 20, 20, 20));

        /* Ajust size of grids */
        ColumnConstraints colOne = new ColumnConstraints(135 , 135 , 135);
        ColumnConstraints colTwo = new ColumnConstraints();
        ColumnConstraints colThree = new ColumnConstraints();
        ColumnConstraints colFour = new ColumnConstraints(53, 53, 53);
        ColumnConstraints colFive = new ColumnConstraints(300, 300, 300);

        /* set Priority to maintain grip size`s config */
        colOne.setHgrow(Priority.ALWAYS); //label is lower priority and get messy
        colFour.setHgrow(Priority.ALWAYS);
        colFive.setHgrow(Priority.ALWAYS);

        layout.getColumnConstraints().addAll(colOne, colTwo, colThree, colFour,
         colFive );

        layout.add(SceneTransition(), 0, 0, 5, 1); /* column ,  line , column to grow , line to grow */

        /* Configura o texto */
        Text title = new Text("Caixa");
        title.setId("title-text");
        layout.add(title, 0, 1, 2, 1);

        /******************************************/
        /*Adicionar valor*/
        Label addValueText = new Label("Adicionar o valor");
        addValueText.setId("label-text");
        layout.add(addValueText, 0, 3); /* column 0 , line 3*/

        TextField addValueField = new TextField();
        addValueField.setPromptText("valor X ou X.XX");

        Button buttonAdd = new Button();
        buttonAdd.setShape(new Circle(30));
        buttonAdd.setId("button-text-add");
        buttonAdd.setTooltip(new Tooltip(msg.getButtonAddMsg()));
        buttonAdd.setDisable(!authorization);
        /******************************************/

        /********************************************/
        /*Retirar valor*/
        Label subValueText = new Label("Retirar o valor");
        subValueText.setId("label-text");

        layout.add(subValueText, 0, 4);
        layout.setHalignment(subValueText, HPos.RIGHT);

        TextField subValueField = new TextField();
        subValueField.setPromptText("valor X ou X.XX");

        Button buttonSub = new Button();
        buttonSub.setShape(new Circle(30));
        buttonSub.setId("button-text-sub");
        buttonSub.setTooltip(new Tooltip(msg.getButtonSubMsg()));
        buttonSub.setDisable(!authorization);
        /*******************************************/

        /****************************************/
        /*Saldo*/
        Label balanceValueText = new Label("Saldo");
        balanceValueText.setId("label-text");
        layout.add(balanceValueText, 0, 5);
        layout.setHalignment(balanceValueText, HPos.RIGHT);

        TextField balanceValueField = new TextField();
        balanceValueField.setEditable(false);
        balanceValueField.setText(conn.querySaldo().toString());
        /******************************************/

        /*****************************************/
        TextArea lastHistory = new TextArea();
        lastHistory.setEditable(false);
        lastHistory.setMinSize(300, 50);
        lastHistory.setMaxSize(300, 50);
        lastHistory.setId("history-text-area");
        /****************************************/

        /***************************************/
        /*Descricao*/
        Label descriptionValueText = new Label("Descrição");
        descriptionValueText.setId("label-text");
        layout.add(descriptionValueText, 4, 3);
        layout.setHalignment(descriptionValueText, HPos.CENTER);

        TextArea descriptionValueField = new TextArea();
        descriptionValueField.setPromptText("Adicione uma descrição.");
        descriptionValueField.setMinSize(300, 70);
        descriptionValueField.setMaxSize(300, 70); // will not grow because I only
                                                   // have 100 char to store
        /*******************************************/
        HBox hbox = new HBox(10); /*spacing 10*/

        VBox vboxOne = new VBox(10);
        VBox vboxTwo = new VBox(10);

        Button histAllBtn = new Button("Histórico");
        histAllBtn.setPrefSize(90, 20);
        histAllBtn.setId("bank-buttons");
        histAllBtn.setOnAction((event) -> {
          ArrayList<CaixaInfo> historyData = conn.queryCaixaTable();

          Dialog dialog = new Dialog(); // create Dialog box
          dialog.setHeaderText("HISTÓRICO");

          GridPane gridpane = new GridPane();
          gridpane.setHgap(10);
          gridpane.setVgap(10);

          TextField fromDate = new TextField();
          TextField toDate = new TextField();

          Button submit = new Button("filtrar por data"); // button to submit filter
          submit.setPrefSize(100, 30);
          submit.setStyle("-fx-border-color: transparent ;" +
        	                "-fx-background-color: #336699;" +
        	                "-fx-text-fill: white; "
                         );
          submit.setTooltip(new Tooltip(msg.getHistoryInfoMsg()));

          TextArea textArea = new TextArea();
          textArea.setEditable(false);
          textArea.setText(createText(historyData , "" , ""));

          submit.setOnAction((eventt) -> {
            textArea.clear();
            textArea.setText(createText(historyData ,
             fromDate.getText() ,
             toDate.getText())
            );

          });

          gridpane.add(fromDate , 0 , 0);
          gridpane.add(toDate , 1 , 0);
          gridpane.add(submit , 2 , 0);
          gridpane.add(textArea , 0 , 1 , 3 , 1);

          dialog.getDialogPane().setContent(gridpane);
          dialog.getDialogPane()
          .getButtonTypes()
          .add(new ButtonType("Fechar", ButtonBar.ButtonData.CANCEL_CLOSE));
          dialog.showAndWait();
        });

        Button onCredit = new Button("Parcelar");
        onCredit.setPrefSize(90, 20);
        onCredit.setId("bank-buttons");
        onCredit.setOnAction((event) -> {
          Dialog dialog = new Dialog();
          dialog.setHeaderText("PARCELAR"); // i can put a picture

          GridPane gridpane = new GridPane();
          //gridpane.setId("set-border"); // for debug
          gridpane.setHgap(10);
          gridpane.setVgap(10);

          ObservableList<String> options = FXCollections.observableArrayList(
          "Sem juros", "Juros simples", "Juros compostos"
          );
          ComboBox<String> comboBox = new ComboBox<String>(options);
          comboBox.setValue("Opções");

          comboBox.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<String>(){
              @Override
              public void changed(ObservableValue<? extends String> ov,
              String old_val,
              String new_val ){
                if(new_val.equals("Sem juros")){System.out.println("Sem juros");}
                if(new_val.equals("Juros simples")){System.out.println("Juros simples");}
                if(new_val.equals("Juros compostos")){System.out.println("Juros compostos");}
              }
            }
          );

          gridpane.add(comboBox, 1, 1);

          dialog.getDialogPane().setContent(gridpane);
          dialog.getDialogPane()
          .getButtonTypes()
          .add(new ButtonType("Fechar", ButtonBar.ButtonData.CANCEL_CLOSE));
          dialog.showAndWait();
        });

        Button type = new Button("Categorias");
        type.setPrefSize(90, 20);
        type.setId("bank-buttons");
        Button graph = new Button("Gráficos");
        graph.setPrefSize(90, 20);
        graph.setId("bank-buttons");

        vboxOne.getChildren().addAll(histAllBtn , onCredit);
        vboxTwo.getChildren().addAll(type , graph);

        hbox.getChildren().addAll(vboxOne , vboxTwo);
        /*******************************************/
        lastHistory.setText(LastHistoryMessage());

        tableUpdateRequest.addListener( //listener to Compromissos table request to update Caixa
          new ChangeListener<Boolean>() {
            @Override
            public void changed(
                 ObservableValue<? extends Boolean> observable,
                 Boolean oldValue, Boolean newValue
            ) {
                balanceValueField.setText(conn.querySaldo().toString());
                lastHistory.setText(LastHistoryMessage());
            }
          }
        );
        /*Acionamento dos botões*/
        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try { //converte o texto para numerico
                    float value = Float.parseFloat(addValueField.getText());
                    if ( value > 0f) {
                        System.out.println("O valor adicionado " + value);
                        balanceValueField.setText(
                         conn.setToCaixa(
                            value ,
                            descriptionValueField.getText()
                          ).toString()
                        );
                        addValueField.clear(); /*clear editing spaces*/
                        descriptionValueField.clear();

                        lastHistory.setText(LastHistoryMessage());
                    } else { //Dialog
                        errorMessages(msg.getErrorMsgOne());
                    }
                }catch(NumberFormatException ex){
                    errorMessages(msg.getErrorMsgTwo());
                };
            }
        });
        buttonSub.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {       //converte o texto para numerico
                    float value = Float.parseFloat(subValueField.getText());
                    if (value > 0f) {
                        System.out.println("O valor retirado " + value);
                        balanceValueField.setText(
                        conn.setToCaixa(
                         (value * -1) , /*make it negative*/
                         descriptionValueField.getText()
                        ).toString()
                        );
                        subValueField.clear();     /*clear editing spaces*/
                        descriptionValueField.clear();

                        lastHistory.setText(LastHistoryMessage());
                    } else { //dialog
                      errorMessages(msg.getErrorMsgOne());
                    }
                }catch(NumberFormatException ex){
                    errorMessages(msg.getErrorMsgTwo());
                };
            }
        });

        /************************************************/
        /*              TextFields               */
        layout.add(addValueField , 1 , 3);
        layout.add(subValueField , 1 , 4);
        layout.add(balanceValueField , 1 , 5);
        layout.add(descriptionValueField , 4 , 4 , 1 , 2);
        layout.add(lastHistory, 0, 6 , 2 , 1);
        /*****************************************/
        /*               Buttons                 */
        layout.add(buttonAdd ,2,3);
        layout.add(buttonSub ,2,4);
        layout.add(hbox , 4 , 6);
        /*****************************************/
        return layout;
    }

    /* @Description: Get last history of Money transation */
    /* @param: none                                       */
    /* @Return : last history                             */
    private String LastHistoryMessage(){
      ArrayList<CaixaInfo> lHistory = conn.queryCaixaTable();
      String text;
      return text = "Ultima operação: R$" +
      lHistory.get(lHistory.size() - 1)
      .getMod()
      .toString()
      + "     " +
      lHistory.get(lHistory.size() - 1)
      .getTime()
      + "\n" +
      lHistory.get(lHistory.size() - 1)
      .getDescript();
    }

    /* @Description: Create filter of history                 */
    /* @param: historyData - list of all datas                */
    /*         DateInit - date to init filter                 */
    /*         DateEnd - date to end filter                   */
    /* @Return : text with history data filtered              */
    private String createText(ArrayList<CaixaInfo> historyData ,
     String DateInit , String DateEnd){

      int length = historyData.size();
      String text = "";

      for(int i = 0 ; i < length ; i++){ // get list and insert into String
          if(!DateInit.equals("")){
            int diffInit = historyData.get(i).compareDate(DateInit);

            if(!DateEnd.equals("")){
              int diffEnd = historyData.get(i).compareDate(DateEnd);

              if(diffInit != diffEnd &&
               diffInit <= 0 &&
               diffEnd >= 0 &&
               diffInit != -100 && // not error
               diffEnd != -100) //not error
               {
                text = text + historyData.get(i).getTime()
                + "    R$" + historyData.get(i).getMod().toString() + "\n"
                + historyData.get(i).getDescript() + "\n\n";
               }
            }
            else if(diffInit <= 0 && diffInit != -100){
              text = text + historyData.get(i).getTime()
              + "    R$" + historyData.get(i).getMod().toString() + "\n"
              + historyData.get(i).getDescript() + "\n\n";
            }

          } else {
            text = text + historyData.get(i).getTime()
            + "    R$" + historyData.get(i).getMod().toString() + "\n"
            + historyData.get(i).getDescript() + "\n\n";
          }
      }
      return text;
    }

    /* @Description: Messages that indicates error          */
    /* @param: msg - String with msg required               */
    /* @Return : none                                       */
    private void errorMessages(String msg){
      Alert errorSub = new Alert(Alert.AlertType.ERROR);
      errorSub.setTitle("ERRO");
      errorSub.setHeaderText("Ocorreu um erro na ação");
      errorSub.setContentText(msg);
      errorSub.showAndWait();
    }

    /* @Description: save table data                       */
    /* @param: infos - list with data to store             */
    /* @Return : none                                      */
    private void saveTable(ObservableList<TableInfo> infos){
      conn.clearTable();
      int size = infos.size();
      if(conn.getConnection() != null){  //there is connection
        for( int i = 0 ; i < size - 1 ; i++){ //the last is aways null because it is a new one to write.
          //Add security when someone click add without write anything
          if( infos.get(i).getTitle() != null &&
              infos.get(i).getDate() != null &&
              infos.get(i).getValue() != null
            ){
              boolean reponse = conn.setToTableCompromissos(
              infos.get(i).getId() ,
              infos.get(i).getTitle() ,
              infos.get(i).getDate() ,
              infos.get(i).getValue());

              if(reponse == false){
                // alert
              }
            }
        }
      }
      return;
    }

    /* @Description: Compromissos's left sideBar           */
    /* @param: none                                        */
    /* @Return : class config                              */
    private VBox sideToolbar(){
      VBox vbox = new VBox(20); /* spacing 20*/

      Button addButton = new Button();
      addButton.setTooltip(new Tooltip(msg.getButtonAddCompMsg()));
      addButton.setShape(new Circle(30));
      addButton.setId("button-text-add");
      addButton.setOnAction((event) -> {
        boolean update = true;
        int id = conn.updateIdTable(update);
        TableInfo newLine = null;
        if( id != 0){
          newLine = new TableInfo(String.valueOf(id) , null , null , null);
          table.getItems().add(newLine); // Add to table
        }
      });

      Button searchButton = new Button();
      searchButton.setShape(new Circle(30));
      searchButton.setId("side-search-button");

      Button removeButton = new Button();
      removeButton.setTooltip(new Tooltip(msg.getButtonASubCompMsg()));
      removeButton.setShape(new Circle(30));
      removeButton.setId("button-text-sub");
      removeButton.setOnAction((event) -> {
        ObservableList<TableInfo> commitSelected , allCommits;
        allCommits = table.getItems();
        commitSelected = table.getSelectionModel().getSelectedItems();
        commitSelected.forEach(allCommits::remove);
      });
      vbox.getChildren().addAll(searchButton , addButton , removeButton);
      vbox.setAlignment(Pos.CENTER);
      return vbox;
    }

    /* @Description: Compromissos's right sideBar          */
    /* @param: none                                        */
    /* @Return : class config                              */
    private VBox sideToolbarRight(){
      VBox vbox = new VBox(20); /* spacing 20*/
      /* Button to transfer data to caixa's saldo*/
      Button transferButton = new Button();
      transferButton.setShape(new Circle(30));
      transferButton.setTooltip(new Tooltip(msg.getButtonTransferMsg()));
      transferButton.setId("side-transfer-button");
      transferButton.setOnAction((event) -> {
        ObservableList<TableInfo> commitSelected , allCommits;
        commitSelected = table.getSelectionModel().getSelectedItems();
        conn.setToCaixa(
          Float.parseFloat(commitSelected.get(0).getValue()) ,
          commitSelected.get(0).getTitle()
        );
        allCommits = table.getItems();
        commitSelected.forEach(allCommits::remove);
        boolean status = tableUpdateRequest.get();
        tableUpdateRequest.set(!status);
      });

      vbox.getChildren().addAll(transferButton);
      vbox.setAlignment(Pos.CENTER);

      return vbox;
    }

    /* @Description: Itens to add to Compromissos table    */
    /* @param: none                                        */
    /* @Return : table data                                */
    private ObservableList<TableInfo> getItems(){
      ObservableList<TableInfo> infos = FXCollections.observableArrayList();
      ArrayList<ArrayList<String>> table = conn.queryFromTableCompromissos();
      if(table != null ){
        int size = table.size();
        for(int i = 0 ; i < size ; i++){
          infos.add( new TableInfo(table.get(i).get(0) , table.get(i).get(1) ,
          table.get(i).get(2) , table.get(i).get(3)));
        }
      }
      boolean update = false;
      int id = conn.updateIdTable(update);
      if(id != 0){
        infos.add(new TableInfo(String.valueOf(id) , null , null , null)); //create a new one to client edition
      }
      infos.addListener((ListChangeListener.Change<? extends TableInfo> c) -> {
        System.out.println("Detected a change! ");
        saveTable(infos);
      });
       return infos;
    }

    /* @Description: Compromissos's table config               */
    /* @param: none                                            */
    /* @Return : class with table config                       */
    private VBox listId(){
      table = new TableView<TableInfo>();
      table.setEditable(true); // Enable editing
      table.setItems(getItems()); // Get items stored

      Callback<TableColumn<TableInfo, String>, TableCell<TableInfo, String>>
       cellFactory = (TableColumn<TableInfo, String> param) -> new EditingCell();

      /* Id column*/
      TableColumn<TableInfo , String> idColumn = new TableColumn<TableInfo , String>("Id");
      idColumn.setMinWidth(50);
      idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
      /* title column*/
      TableColumn<TableInfo , String> titleColumn = new TableColumn<TableInfo , String>("Titulo");
      titleColumn.setMinWidth(300);
      titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
      titleColumn.setCellFactory(cellFactory);
      titleColumn.setOnEditCommit(
       (TableColumn.CellEditEvent<TableInfo , String> t) -> {
         ((TableInfo) t.getTableView().getItems()
         .get(t.getTablePosition().getRow()))
         .setTitle(t.getNewValue());
       }
       );
      /* date column*/
      TableColumn<TableInfo , String> dateColumn = new TableColumn<TableInfo , String>("Data");
      dateColumn.setMinWidth(75);
      dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
      dateColumn.setCellFactory(cellFactory);
      dateColumn.setOnEditCommit(
       (TableColumn.CellEditEvent<TableInfo , String> t) -> {
         ((TableInfo) t.getTableView().getItems()
         .get(t.getTablePosition().getRow()))
         .setDate(t.getNewValue());
       });
      /* value column*/
      TableColumn<TableInfo , String> valueColumn = new TableColumn<TableInfo , String>("Valor (R$)");
      valueColumn.setMinWidth(75);
      valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
      valueColumn.setCellFactory(cellFactory);
      valueColumn.setOnEditCommit(
       (TableColumn.CellEditEvent<TableInfo , String> t) -> {
         ((TableInfo) t.getTableView().getItems()
         .get(t.getTablePosition().getRow()))
         .setValue(t.getNewValue());
       });

       table.getColumns().addAll(idColumn ,titleColumn , dateColumn , valueColumn);

      VBox tableSpace = new VBox();
      tableSpace.getChildren().addAll(table);

      return tableSpace;
    }

    /* @Description: Compromissos's Screen                             */
    /* @param: none                                                    */
    /* @Return : class with Compromissos's config                      */
    private GridPane compScreen(){
      GridPane layout = new GridPane();
      layout.setPadding(new Insets(20, 20, 20, 20));
      //layout.setId("set-border"); // for debug
      layout.add(SceneTransition(), 0, 0, 5, 1);

      ColumnConstraints colOne = new ColumnConstraints(56 , 56 , 56);
      ColumnConstraints colTwo = new ColumnConstraints(50 , 50 , 50);
      ColumnConstraints colThree = new ColumnConstraints(500 , 500 , 500);
      ColumnConstraints colFour = new ColumnConstraints(50 , 50 , 50);
      ColumnConstraints colFive = new ColumnConstraints(57 , 57 , 57); //increase SceneTransition bar
      //I had to count because padding was not holding

      /* set Priority to maintain grip size`s config */
      colOne.setHgrow(Priority.ALWAYS);
      colTwo.setHgrow(Priority.ALWAYS);
      colThree.setHgrow(Priority.ALWAYS);
      colFour.setHgrow(Priority.ALWAYS);
      colFive.setHgrow(Priority.ALWAYS);

      layout.getColumnConstraints().addAll(colOne, colTwo,
       colThree , colFour , colFive );

      RowConstraints rowOne = new RowConstraints();
      RowConstraints rowTwo = new RowConstraints(20 , 20 , 20);
      RowConstraints rowThree = new RowConstraints(40 , 40 , 40);
      RowConstraints rowFour = new RowConstraints(30 , 30 , 30);

      layout.getRowConstraints().addAll(rowOne , rowTwo , rowThree , rowFour);

      Text title = new Text("Compromissos");
      title.setId("title-text");

      layout.add(title, 0, 2, 1, 1);
      layout.add(sideToolbar() , 1 , 4);
      layout.add(listId() , 2 , 4);
      layout.add(sideToolbarRight() , 3 , 4);

      return layout;
    }

}
