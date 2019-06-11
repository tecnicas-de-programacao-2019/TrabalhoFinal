package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.GregorianCalendar;
import java.util.LinkedList;
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

    public double getSaldoMedio(int conta, int ano, int mes, double saldoAtual){
        LinkedList<Operacao> ops = new LinkedList<>();
        double valorVariado=0;
        int n=0;
        int dia=0;

        YearMonth yearMonthObject = YearMonth.of(ano, mes);
        int daysInMonth = yearMonthObject.lengthOfMonth(); 

        for (Operacao op : operacoes){
            if (op.getNumeroConta()==conta && op.getAno()==ano && op.getMes()==mes){
                ops.add(op);
            }
        }
               
        
        
        
         // if(op.getTipoOperacao() == 0)
                //     valorVariado += op.getValorOperacao();
                // else
                //     valorVariado -= op.getValorOperacao();
                // n++;


        double saldoMedio = saldo / n;
        return saldoMedio;
    }

    public void add(Operacao op){
        operacoes.add(op);
    }

    public double gastoDiario(Integer nroConta, GregorianCalendar date){
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

        return totalDiario;
    }
}