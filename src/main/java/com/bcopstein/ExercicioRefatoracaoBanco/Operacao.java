package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.Calendar;

public class Operacao {
	public static final int CREDITO = 0;
	public static final int DEBITO = 1;
    
	private int dia;
    private int mes;
    private int ano;
    private int hora;
    private int minuto;
    private int segundo;
    private int numeroConta;
    private int statusConta;
    private double valorOperacao;
    private int tipoOperacao;
    
	private Operacao(int dia, int mes, int ano, int hora, int minuto, int segundo, int numeroConta, int statusConta,
			double valorOperacao, int tipoOperacao) {
		super();
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
		this.hora = hora;
		this.minuto = minuto;
		this.segundo = segundo;
		this.numeroConta = numeroConta;
		this.statusConta = statusConta;
		this.valorOperacao = valorOperacao;
		this.tipoOperacao = tipoOperacao;
		// System.out.println("Conta: "+numeroConta+" Mês: "+mes);
	}

	public static Operacao criaDeposito(int numeroConta, int statusConta, double valorOperacao){
		Calendar date = Calendar.getInstance();
        Operacao op = new Operacao(date.get(Calendar.DAY_OF_MONTH),
        date.get(Calendar.MONTH + 1), date.get(Calendar.YEAR),
        date.get(Calendar.HOUR), date.get(Calendar.MINUTE),
		date.get(Calendar.SECOND), numeroConta, statusConta, valorOperacao, CREDITO);
		
		return op;
	}

	public static Operacao criaRetirada(int numeroConta, int statusConta, double valorOperacao){
		Calendar date = Calendar.getInstance();
        Operacao op = new Operacao(date.get(Calendar.DAY_OF_MONTH),
        date.get(Calendar.MONTH + 1), date.get(Calendar.YEAR),
        date.get(Calendar.HOUR), date.get(Calendar.MINUTE),
		date.get(Calendar.SECOND), numeroConta, statusConta, valorOperacao, DEBITO);
		
		return op;
	}

	public static Operacao criaOperacaoAntiga(int dia, int mes, int ano, int hora, int minuto, int segundo, int numeroConta, int statusConta,
	double valorOperacao, int tipoOperacao){
		return new Operacao(dia, mes, ano, hora, minuto, segundo, numeroConta, statusConta, valorOperacao, tipoOperacao);
	}

	public int getDia() {
		return dia;
	}

	public int getMes() {
		return mes;
	}

	public int getAno() {
		return ano;
	}

	public int getHora() {
		return hora;
	}

	public int getMinuto() {
		return minuto;
	}

	public int getSegundo() {
		return segundo;
	}

	public int getNumeroConta() {
		return numeroConta;
	}

	public int getStatusConta() {
		return statusConta;
	}

	public double getValorOperacao() {
		return valorOperacao;
	}

	public int getTipoOperacao() {
		return tipoOperacao;
	}
    
	@Override
	public String toString() {
		String tipo = "<C>";
		if (tipoOperacao == 1) {
			tipo = "<D>"; 
		}
		String line = dia+"/"+mes+"/"+ano+" "+
	                  hora+":"+minuto+":"+segundo+" "+
				      numeroConta+" "+
	                  statusConta +" "+
				      tipo+" "+
	                  valorOperacao;
		return(line);
	}
}
