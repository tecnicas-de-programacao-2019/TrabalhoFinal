package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.collections.ObservableList;

public class Fachada {
    private static Fachada fachada;
    private Contas contas;

    private Fachada(){
        Operacoes operacoes = new Operacoes(Persistencia.getInstance());
        contas = new Contas(Persistencia.getInstance(), operacoes);
    }

    public static Fachada getInstance(){
        if(fachada == null)
            fachada = new Fachada();
        return fachada;
    }

    public void saveContas(){
        contas.saveContas();
    }

    public boolean login(int nroConta){
        if(this.contas.contaExiste(nroConta))
            contas.setContaAtual(contas.get(nroConta));
        return this.contas.contaExiste(nroConta);
    }

    public Conta getContaAtual(){
        return contas.getContaAtual();
    }

    public ObservableList<Operacao> getOperacoesConta(){
        return this.contas.getOperacoesConta();
    }

    public Operacao addOperacao(int numeroConta, int statusConta,
    double valorOperacao, int tipoOperacao){
        if(tipoOperacao == Operacao.DEBITO){
            Operacao op = Operacao.criaRetirada(numeroConta, statusConta, valorOperacao);
            this.contas.addOperacao(op);
            return op;
        }else if (tipoOperacao == Operacao.CREDITO){
            Operacao op = Operacao.criaDeposito(numeroConta, statusConta, valorOperacao);
            this.contas.addOperacao(op);
            return op;
        } 
        throw new IllegalArgumentException("Tipo de operação invalida");
    }

    public double gastoDiario(){
        return this.contas.gastoDiario();
    }

    public double getSaldoMedio(int conta, int ano, int mes){
        return this.contas.getSaldoMedio(conta,ano,mes);
    }

    public boolean temSaldoParaDebito(double valor){
        return this.contas.getContaAtual().getSaldo() - valor >= 0 && valor >= 0;
    }

    public boolean estourouLimiteDebitoDiario(double valor){
        return this.contas.gastoDiario() + valor >= this.contas.getLimRetiradaDiaria() 
        && valor >= 0;
    }
    
    public boolean retiraValor(double valor){
        if(temSaldoParaDebito(valor) && !estourouLimiteDebitoDiario(valor)){
            contas.retiraValor(valor);
            return true;
        }
        return false;
    }

    public void deposita(double valor){
        this.getContaAtual().deposito(valor);
    }
}