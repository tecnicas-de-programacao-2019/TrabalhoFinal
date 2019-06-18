package com.bcopstein.ExercicioRefatoracaoBanco;
import java.time.Month;
import java.util.Calendar;
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
        int dia=1;
        double saldoMedio=0;

        Calendar mycal = new GregorianCalendar(ano,mes-1,1);
        int daysInMonth = mycal.getActualMaximum(mycal.DAY_OF_MONTH);
        
        for (Operacao op : operacoes){
            if (op.getNumeroConta()==conta && op.getAno()==ano && op.getMes()==mes){
                ops.add(op);
            }
        }

        while (dia <= daysInMonth){
            for (Operacao op : ops){
                if (op.getDia() == dia)
                    saldoAtual+=op.getValorOperacao();
                else
                    saldoAtual+=saldoAtual;
            }
            dia++;
        }
               
        saldoMedio = saldoAtual / daysInMonth;
        
        
         // if(op.getTipoOperacao() == 0)
                //     valorVariado += op.getValorOperacao();
                // else
                //     valorVariado -= op.getValorOperacao();
        return saldoMedio;
    }

    public void add(Operacao op){
        operacoes.add(op);
    }

    public double gastoDiario(Integer nroConta, Calendar date){
        double totalDiario = 0;
        for(Operacao op : operacoes){
            if(op.getTipoOperacao() == op.DEBITO && op.getNumeroConta() == nroConta){
                if(op.getDia() == date.get(Calendar.DAY_OF_MONTH) && 
                op.getMes() == date.get(Calendar.MONTH + 1) && 
                op.getAno() == date.get(Calendar.YEAR)){
                    totalDiario += op.getValorOperacao();
                }
            }
        }

        return totalDiario;
    }
}