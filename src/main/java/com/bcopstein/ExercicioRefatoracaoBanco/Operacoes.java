package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Operacoes {
    private Persistencia persistencia;
    private List<Operacao> operacoes;

    public Operacoes(Persistencia persistencia){
        this.persistencia = persistencia;
        operacoes = persistencia.loadOperacoes();
    }

    public void saveOperacoes(){
        persistencia.saveOperacoes(operacoes);
    }

    public ObservableList<Operacao> getOperacoesConta(int nro){
        return FXCollections.observableArrayList(operacoes.stream()
				.filter(op -> op.getNumeroConta() == nro).collect(Collectors.toList()));
    }

    public void add(Operacao op){
        operacoes.add(op);
    }

    public double gastoDiario(Integer nroConta, double valorAdicionado, GregorianCalendar date){
        double totalDiario = 0;
        for(Operacao op : operacoes){
            if(op.getTipoOperacao() == op.DEBITO && op.getNumeroConta() == nroConta){
                if(op.getDia() == date.get(GregorianCalendar.DAY_OF_MONTH) && 
                op.getMes() == date.get(GregorianCalendar.MONTH + 1) && 
                op.getAno() == date.get(GregorianCalendar.YEAR)){
                    totalDiario += op.getValorOperacao();
                }
            }
        }

        return totalDiario + valorAdicionado;
    }
}