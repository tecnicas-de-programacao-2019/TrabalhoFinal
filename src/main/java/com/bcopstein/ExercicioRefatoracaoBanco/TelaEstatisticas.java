package com.bcopstein.ExercicioRefatoracaoBanco;

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


															
		Label sm = new Label( "Saldo médio em: ");
		grid.add(sm, 0, 1);
		Label smValue = new Label("Teste");
		grid.add(smValue, 2, 1);

		Label tc = new Label("Total de crédito em: " );
		grid.add(tc, 0, 2);
		Label tcValue = new Label("Teste");
		grid.add(tcValue, 2, 2);

		Label td = new Label("Total de débito em: ");
		grid.add(td, 0, 3);
		Label tdValue = new Label("Teste");
		grid.add(tdValue, 2, 3);


		ChoiceBox<String> choiceBox = new ChoiceBox<String>();
		choiceBox.getItems().addAll("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
		choiceBox.setValue("Junho");
		 

		
		Button btnback = new Button("Voltar");
		HBox hbBtn = new HBox(30);
		
		hbBtn.setAlignment(Pos.BOTTOM_LEFT);
		
		hbBtn.getChildren().addAll(btnback,choiceBox);
		grid.add(hbBtn, 0, 4);	
		btnback.setOnAction(e -> {
			mainStage.setScene(cenaOperacoes);
		});
		
		cenaEstatisticas = new Scene(grid);
		return cenaEstatisticas;
	}


}
