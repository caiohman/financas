package caixaApp;
/*
 Objetivo: testar o javafx
 Main.java
*/

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.text.*;


public class Main extends Application{

	@Override
	public void start(Stage primaryStage) {

		Font.loadFont(getClass().getResourceAsStream("/Acme-Regular.ttf"), 30);  
		Telas telas = new Telas(primaryStage);
		primaryStage.setTitle("Finanças");
		primaryStage.setScene(telas.getLogin());
		primaryStage.show();

	}

	public static void main(String[] args){
		launch(args);
	}
}
