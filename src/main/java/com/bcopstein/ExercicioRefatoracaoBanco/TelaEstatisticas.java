package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.Calendar;
import java.time.LocalDate;
import java.time.Month;
import java.util.GregorianCalendar;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
	private Scene cenaOperacoes;
	private Scene cenaEstatisticas;
	private ObservableList<Operacao> operacoesConta;

	private Fachada fachada;

	private TextField tfValorOperacao;
	private TextField tfSaldo;
	private Label cat;
	private Label lim;
	private String categoria;
	private String limRetDiaria;

	public TelaEstatisticas(Stage mainStage, Scene cenaOperacoes) { // conta
		this.mainStage = mainStage;
		this.cenaOperacoes = cenaOperacoes;
		fachada = Fachada.getInstance();
	}

	public Scene getTelaEstatisticas() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		String dadosCorr = fachada.getContaAtual().getNumero() + " : " + fachada.getContaAtual().getCorrentista();
		Text scenetitle = new Text(dadosCorr);
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);


															
		Label sm = new Label( "Saldo médio: ");
		grid.add(sm, 0, 1);
		Label smValue = new Label("");
		grid.add(smValue, 2, 1);

		Label tc = new Label("Total de crédito: " );
		grid.add(tc, 0, 2);
		Label tcValue = new Label("");
		grid.add(tcValue, 2, 2);

		Label td = new Label("Total de débito: ");
		grid.add(td, 0, 3);
		Label tdValue = new Label("");
		grid.add(tdValue, 2, 3);


	
		ChoiceBox<String> choiceMes = new ChoiceBox<String>();
		choiceMes.getItems().addAll("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
		
		Calendar now = Calendar.getInstance();
		choiceMes.getSelectionModel().select(now.get(Calendar.MONTH));
		

		ChoiceBox<String> choiceAno = new ChoiceBox<String>();
		choiceAno.getItems().addAll("2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008");
		
		int year = now.get(Calendar.YEAR);
		String yearInString = String.valueOf(year);	
		choiceAno.setValue(yearInString);

		 
		Button btnGet = new Button("Selecionar");
		btnGet.setOnAction(e -> {
			int mes = choiceMes.getSelectionModel().getSelectedIndex()+1;
			int ano = Integer.parseInt(choiceAno.getValue());
			int conta = fachada.getContaAtual().getNumero();

			double saldoMedio = fachada.getSaldoMedio(conta, ano, mes);
			smValue.setText(String.valueOf(saldoMedio));

			double totalDebito = fachada.getTotalDebito(conta, ano, mes);
			tdValue.setText(String.valueOf(totalDebito));

			double totalCredito = fachada.getTotalCredito(conta, ano, mes);
			tcValue.setText(String.valueOf(totalCredito));
		});


		HBox hbBtn = new HBox(30);
		hbBtn.setAlignment(Pos.BOTTOM_LEFT);
		hbBtn.getChildren().addAll(choiceMes,choiceAno, btnGet);
		grid.add(hbBtn, 0, 4);
		
		Button btnBack = new Button("Voltar");
		grid.add(btnBack, 0, 5);
		
		btnBack.setOnAction(e -> {
			mainStage.setScene(cenaOperacoes);
		});
		
		cenaEstatisticas = new Scene(grid);
		return cenaEstatisticas;
	}


}
