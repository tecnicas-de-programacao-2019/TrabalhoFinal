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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelaOperacoes {
	private Stage mainStage;
	private Scene cenaEntrada;
	private Scene cenaOperacoes;
	private ObservableList<Operacao> operacoesConta;
	private Fachada fachada;

	private TextField tfValorOperacao;
	private TextField tfSaldo;
	private Label cat;
	private Label lim;
	private String categoria;
	private String limRetDiaria;

	public TelaOperacoes(Stage mainStage, Scene telaEntrada, int nroConta) { // conta
		this.mainStage = mainStage;
		this.cenaEntrada = telaEntrada;
		this.fachada = Fachada.getInstance();
	}

	public Scene getTelaOperacoes() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		String dadosCorr = fachada.getContaAtual().getNumero() + " : " + fachada.getContaAtual().getCorrentista();
		Text scenetitle = new Text(dadosCorr);
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		categoria = "Categoria: " + fachada.getContaAtual().getStrStatus();
		limRetDiaria = "Limite retirada diaria: " + fachada.getContaAtual().getLimRetiradaDiaria();

		cat = new Label(categoria);
		grid.add(cat, 0, 1);

		lim = new Label(limRetDiaria);
		grid.add(lim, 0, 2);

		Label tit = new Label("Ultimos movimentos");
		grid.add(tit, 0, 3);

		// Seleciona apenas o extrato da conta atual
		operacoesConta = fachada.getOperacoesConta();

		ListView<Operacao> extrato = new ListView<>(operacoesConta);
		extrato.setPrefHeight(140);
		grid.add(extrato, 0, 4);

		tfSaldo = new TextField();
		tfSaldo.setDisable(true);
		tfSaldo.setText("" + fachada.getContaAtual().getSaldo());
		HBox valSaldo = new HBox(20);
		valSaldo.setAlignment(Pos.BOTTOM_LEFT);
		valSaldo.getChildren().add(new Label("Saldo"));
		valSaldo.getChildren().add(tfSaldo);
		grid.add(valSaldo, 0, 5);

		tfValorOperacao = new TextField();
		HBox valOper = new HBox(30);
		valOper.setAlignment(Pos.BOTTOM_CENTER);
		valOper.getChildren().add(new Label("Valor operacao"));
		valOper.getChildren().add(tfValorOperacao);
		grid.add(valOper, 1, 1);

		Button btnCredito = new Button("Credito");
		Button btnDebito = new Button("Debito");
		Button btnVoltar = new Button("Voltar");
		Button btnEstatisticas = new Button("Estatísticas");
		HBox hbBtn = new HBox(20);
		hbBtn.setAlignment(Pos.TOP_CENTER);
		hbBtn.getChildren().add(btnCredito);
		hbBtn.getChildren().add(btnDebito);
		hbBtn.getChildren().add(btnVoltar);
		//hbBtn.getChildren().add(btnEstatisticas);
		grid.add(hbBtn, 1, 2);
		grid.add(btnEstatisticas, 1, 4);

		btnCredito.setOnAction(e -> {
			try {
				double valor = Integer.parseInt(tfValorOperacao.getText());
				if (valor < 0.0) {
					throw new NumberFormatException("Valor invalido");
				}
				fachada.deposita(valor);
				GregorianCalendar date = new GregorianCalendar();

				Operacao op = fachada.addOperacao(date.get(GregorianCalendar.DAY_OF_MONTH),
				date.get(GregorianCalendar.MONTH + 1), date.get(GregorianCalendar.YEAR),
				date.get(GregorianCalendar.HOUR), date.get(GregorianCalendar.MINUTE),
				date.get(GregorianCalendar.SECOND), fachada.getContaAtual().getNumero(), fachada.getContaAtual().getStatus(), valor, 0);

				tfSaldo.setText("" + fachada.getContaAtual().getSaldo());
				operacoesConta.add(op);
				categoria = "Categoria: " + fachada.getContaAtual().getStrStatus();
				limRetDiaria = "Limite retirada diaria: " + fachada.getContaAtual().getLimRetiradaDiaria();
				cat.setText(categoria);
				lim.setText(limRetDiaria);
			} catch (NumberFormatException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Valor inválido !!");
				alert.setHeaderText(null);
				alert.setContentText("Valor inválido para operacao de crédito!!");

				alert.showAndWait();
			}
		});

		btnDebito.setOnAction(e -> {
			try {
				GregorianCalendar date = new GregorianCalendar();
				double valor = Integer.parseInt(tfValorOperacao.getText());

				if (!fachada.temSaldoParaDebito(valor)) {
					throw new NumberFormatException("Saldo insuficiente");
				} else if(fachada.estourouLimiteDebitoDiario(valor)){
					throw new NumberFormatException("Limite diario de saque atingido.");
				}

				fachada.retiraValor(valor);
				
				Operacao op = fachada.addOperacao(date.get(GregorianCalendar.DAY_OF_MONTH),
				date.get(GregorianCalendar.MONTH + 1), date.get(GregorianCalendar.YEAR),
				date.get(GregorianCalendar.HOUR), date.get(GregorianCalendar.MINUTE),
				date.get(GregorianCalendar.SECOND), fachada.getContaAtual().getNumero(), fachada.getContaAtual().getStatus(), valor, 1);
				tfSaldo.setText("" + fachada.getContaAtual().getSaldo());
				operacoesConta.add(op);

				categoria = "Categoria: " + fachada.getContaAtual().getStrStatus();
				limRetDiaria = "Limite retirada diaria: " + fachada.getContaAtual().getLimRetiradaDiaria();
				cat.setText(categoria);
				lim.setText(limRetDiaria);
			} catch (NumberFormatException ex) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Valor inválido !!");
				alert.setHeaderText(null);
				alert.setContentText("Valor inválido para operacao de débito!\n"+ex.getMessage());

				alert.showAndWait();
			}
		});

		btnVoltar.setOnAction(e -> {
			mainStage.setScene(cenaEntrada);
		});

		btnEstatisticas.setOnAction(e -> {
			TelaEstatisticas telaEstatisticas = new TelaEstatisticas(mainStage, cenaOperacoes);
			Scene scene = telaEstatisticas.getTelaEstatisticas();
			mainStage.setScene(scene);
		});

		cenaOperacoes = new Scene(grid);
		return cenaOperacoes;
	}

}
