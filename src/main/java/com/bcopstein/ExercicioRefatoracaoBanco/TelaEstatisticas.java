package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.GregorianCalendar;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaEstatisticas {
	private Stage mainStage;
	private Scene cenaEntrada;
	private Scene cenaEstatisticas;
	private Operacoes operacoes;
	private ObservableList<Operacao> operacoesConta;

	private Conta conta;

	private TextField tfValorOperacao;
	private TextField tfSaldo;
	private Label cat;
	private Label lim;
	private String categoria;
	private String limRetDiaria;

	public TelaEstatisticas(Stage mainStage, Scene telaEntrada, Conta conta, Operacoes operacoes) { // conta
		this.mainStage = mainStage;
		this.cenaEntrada = telaEntrada;
		this.conta = conta;
		this.operacoes = operacoes;
	}

	public Scene getTelaEstatisticas() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		String dadosCorr = conta.getNumero() + " : " + conta.getCorrentista();
		Text scenetitle = new Text(dadosCorr);
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        
        Text saldoMedio = new Text("Saldo médio em: ");
        Text totalCredito = new Text("Total de crédito em: ");
        Text totalDebito = new Text("Total de débito em: ");
        VBox vbox = new VBox(saldoMedio,totalCredito,totalDebito);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(25));
        grid.add(vbox,1,0);
        
        

		cenaEstatisticas = new Scene(grid);
		return cenaEstatisticas;
	}

}
