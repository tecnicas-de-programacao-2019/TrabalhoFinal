package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
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
	private List<Operacao> operacoes;
	private ObservableList<Operacao> operacoesConta;

	private Conta conta;

	private TextField tfValorOperacao;
	private TextField tfSaldo;
	private Label cat;
	private Label lim;
	private String categoria;
	private String limRetDiaria;

	public TelaOperacoes(Stage mainStage, Scene telaEntrada, Conta conta, List<Operacao> operacoes) { // conta
		this.mainStage = mainStage;
		this.cenaEntrada = telaEntrada;
		this.conta = conta;
		this.operacoes = operacoes;
	}

	public Scene getTelaOperacoes() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		String dadosCorr = conta.getNumero() + " : " + conta.getCorrentista();
		Text scenetitle = new Text(dadosCorr);
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		categoria = "Categoria: " + conta.getStrStatus();
		limRetDiaria = "Limite retirada diaria: " + conta.getLimRetiradaDiaria();

		cat = new Label(categoria);
		grid.add(cat, 0, 1);

		lim = new Label(limRetDiaria);
		grid.add(lim, 0, 2);

		Label tit = new Label("Ultimos movimentos");
		grid.add(tit, 0, 3);

		// Seleciona apenas o extrato da conta atual
		operacoesConta = FXCollections.observableArrayList(operacoes.stream()
				.filter(op -> op.getNumeroConta() == this.conta.getNumero()).collect(Collectors.toList()));

		ListView<Operacao> extrato = new ListView<>(operacoesConta);
		extrato.setPrefHeight(140);
		grid.add(extrato, 0, 4);

		tfSaldo = new TextField();
		tfSaldo.setDisable(true);
		tfSaldo.setText("" + conta.getSaldo());
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
		HBox hbBtn = new HBox(20);
		hbBtn.setAlignment(Pos.TOP_CENTER);
		hbBtn.getChildren().add(btnCredito);
		hbBtn.getChildren().add(btnDebito);
		hbBtn.getChildren().add(btnVoltar);
		grid.add(hbBtn, 1, 2);

		btnCredito.setOnAction(e -> {
			try {
				double valor = Integer.parseInt(tfValorOperacao.getText());
				if (valor < 0.0) {
					throw new NumberFormatException("Valor invalido");
				}
				conta.deposito(valor);
				GregorianCalendar date = new GregorianCalendar();
				Operacao op = new Operacao(date.get(GregorianCalendar.DAY_OF_MONTH),
						date.get(GregorianCalendar.MONTH + 1), date.get(GregorianCalendar.YEAR),
						date.get(GregorianCalendar.HOUR), date.get(GregorianCalendar.MINUTE),
						date.get(GregorianCalendar.SECOND), conta.getNumero(), conta.getStatus(), valor, 0);
				operacoes.add(op);
				tfSaldo.setText("" + conta.getSaldo());
				operacoesConta.add(op);
				categoria = "Categoria: " + conta.getStrStatus();
				limRetDiaria = "Limite retirada diaria: " + conta.getLimRetiradaDiaria();
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
				
				//Calcula o limite diario
				double totalDiario = 0;
				for(Operacao op : operacoes){
					if(op.getTipoOperacao() == op.DEBITO && op.getNumeroConta() == conta.getNumero()){
						if(op.getDia() == date.get(GregorianCalendar.DAY_OF_MONTH) && 
						op.getMes() == date.get(GregorianCalendar.MONTH + 1) && 
						op.getAno() == date.get(GregorianCalendar.YEAR)){
							totalDiario += op.getValorOperacao();
						}
					}
				}

				double total = totalDiario + valor;
				if (valor < 0.0 || valor > conta.getSaldo()) {
					throw new NumberFormatException("Saldo insuficiente");
				} else if(total > conta.getLimRetiradaDiaria()){
					throw new NumberFormatException("Limite diario de saque atingido.");
				}
				conta.retirada(valor);
				
				Operacao op = new Operacao(date.get(GregorianCalendar.DAY_OF_MONTH),
						date.get(GregorianCalendar.MONTH + 1), date.get(GregorianCalendar.YEAR),
						date.get(GregorianCalendar.HOUR), date.get(GregorianCalendar.MINUTE),
						date.get(GregorianCalendar.SECOND), conta.getNumero(), conta.getStatus(), valor, 1);
				operacoes.add(op);
				tfSaldo.setText("" + conta.getSaldo());
				operacoesConta.add(op);

				categoria = "Categoria: " + conta.getStrStatus();
				limRetDiaria = "Limite retirada diaria: " + conta.getLimRetiradaDiaria();
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

		cenaOperacoes = new Scene(grid);
		return cenaOperacoes;
	}

}
