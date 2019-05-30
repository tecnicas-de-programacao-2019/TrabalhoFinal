package com.bcopstein.ExercicioRefatoracaoBanco;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
	private Contas contas;
	private Operacoes operacoes;
	
	private TelaEntrada telaEntrada;
	
    @Override
    public void start(Stage primaryStage) {
        contas = new Contas(Persistencia.getInstance());
    	operacoes = new Operacoes(Persistencia.getInstance());
    	
    	primaryStage.setTitle("$$ Banco NOSSA GRANA $$");

    	telaEntrada = new TelaEntrada(primaryStage, contas, operacoes); 

        primaryStage.setScene(telaEntrada.getTelaEntrada());
        primaryStage.show();
    }
    
    @Override
    public void stop() {
        contas.saveContas();
        operacoes.saveOperacoes();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

