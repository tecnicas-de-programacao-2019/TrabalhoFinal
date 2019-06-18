package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.GregorianCalendar;
import java.util.Map;

import javafx.collections.ObservableList;

public class Contas {
    private Map<Integer,Conta> contas;
    private Persistencia persistencia;
    private Operacoes operacoes;
    private Conta contaAtual;
    
    public Contas(Persistencia persistencia, Operacoes operacoes){
        this.persistencia = persistencia;
        contas = persistencia.loadContas();
        this.operacoes = operacoes;
    }

    public void saveContas(){
        persistencia.saveContas(contas.values());
        operacoes.saveOperacoes();
    }

    public Conta get(int nro){
        return contas.get(nro);
    }

    public boolean contaExiste(int nro){
        return this.get(nro) != null;
    }

    public void addOperacao(Operacao op){
        operacoes.add(op);
    }

    public void setContaAtual(Conta conta){
        this.contaAtual = conta;
    }

    public Conta getContaAtual(){
        return this.contaAtual;
    }

    public ObservableList<Operacao> getOperacoesConta(){
        return this.operacoes.getOperacoesConta(this.contaAtual.getNumero());
    }

    public double gastoDiario(){
        GregorianCalendar date = new GregorianCalendar();
        return this.operacoes.gastoDiario(this.contaAtual.getNumero(), date);
    }

    public double getSaldoMedio(int conta, int ano, int mes){
        return this.operacoes.getSaldoMedio(conta, ano, mes);
    }

    public double getSaldo(){
        return this.contaAtual.getSaldo();
    }



    public double getLimRetiradaDiaria(){
        return this.contaAtual.getLimRetiradaDiaria();
    }

    public void retiraValor(double valor){
        this.contaAtual.retirada(valor);
    }
}