package com.bcopstein.ExercicioRefatoracaoBanco;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
	private Fachada fachada;
	
	private TelaEntrada telaEntrada;
	
    @Override
    public void start(Stage primaryStage) {
        Operacoes operacoes = new Operacoes(Persistencia.getInstance());
        fachada = Fachada.getInstance();
    	
    	primaryStage.setTitle("$$ Banco NOSSA GRANA $$");

    	telaEntrada = new TelaEntrada(primaryStage, fachada); 

        primaryStage.setScene(telaEntrada.getTelaEntrada());
        primaryStage.show();
    }
    
    @Override
    public void stop() {
        fachada.saveContas();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

