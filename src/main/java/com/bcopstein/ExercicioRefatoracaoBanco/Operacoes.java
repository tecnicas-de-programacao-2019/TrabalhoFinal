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


    public double getSaldoMedio(int conta, int ano, int mes ){
        //System.out.println("Conta = "+conta+" Ano = "+ ano+ " Mês = "+mes);
       
        LinkedList<Operacao> ops = new LinkedList<>();
        double saldoAtual=0;
        int dia=1;
        double saldoMedio=0;

        Calendar mycal = new GregorianCalendar(ano,mes-1,1);
        int daysInMonth = mycal.getActualMaximum(mycal.DAY_OF_MONTH);

        for (Operacao op : operacoes){
            if (op.getAno()>=ano){
                if(op.getAno()==ano && op.getMes()>=mes)
                    break;
                if (op.getAno()==ano && op.getMes()<mes && op.getNumeroConta()==conta)
                    saldoAtual+=op.getValorOperacao();

                if(op.getAno()>ano)
                    break;
                
            } 
           
            
            else if(op.getNumeroConta()==conta){
                saldoAtual+=op.getValorOperacao();
                //System.out.println("Adicionei a operacao do ano "+op.getAno() + " "+op.getMes());
              //  System.out.println(op.getAno()>=ano);
               // System.out.println(op.getMes()>=ano);
            }
        }
        //System.out.println("Saldo atual: "+saldoAtual);

        for (Operacao op1 : operacoes){
           
           // System.out.println("Conta = "+op1.getNumeroConta()+" Ano = "+ op1.getAno()+ " Mês = "+op1.getMes());
            
            if (op1.getNumeroConta()==conta && op1.getAno()==ano && op1.getMes()==mes){
                ops.add(op1);
             //   System.out.println("Valor da operação: "+ op1.getValorOperacao());
            }
        }
        double saldoAux = 0;
        double saldoAux2 = 0;
        while (dia <= daysInMonth){
            for (Operacao op : ops){
                if (op.getDia() == dia){
                    if(op.getTipoOperacao() == Operacao.CREDITO)
                        saldoAux2+=op.getValorOperacao();
                    else
                        saldoAux2-=op.getValorOperacao();
                }
                   
                
            }
            saldoAux+=saldoAtual;
            dia++;
        }

       // System.out.println("Saldo aux: "+saldoAux);
        //System.out.println("Saldo aux2: "+saldoAux2);

        saldoAux+=saldoAux2;
        //System.out.println("Saldo atual2: "+saldoAux);
        //System.out.println("Days in month: "+daysInMonth);
        saldoMedio = saldoAux / daysInMonth;
        
        return saldoMedio;
    }


    public double getTotalDebito(int conta, int ano, int mes ){
        //System.out.println("Conta = "+conta+" Ano = "+ ano+ " Mês = "+mes);
       
        LinkedList<Operacao> ops = new LinkedList<>();
        double debito=0;
        int dia=1;
        

        Calendar mycal = new GregorianCalendar(ano,mes-1,1);
        int daysInMonth = mycal.getActualMaximum(mycal.DAY_OF_MONTH);

        for (Operacao op : operacoes){
            if (op.getNumeroConta() == conta && op.getAno()==ano && op.getMes()==mes && op.getTipoOperacao()==Operacao.DEBITO){
                debito+=op.getValorOperacao();
                
            } 
        }
        return debito;
    }

    public double getTotalCredito(int conta, int ano, int mes ){
        //System.out.println("Conta = "+conta+" Ano = "+ ano+ " Mês = "+mes);
       
        LinkedList<Operacao> ops = new LinkedList<>();
        double credito=0;
        int dia=1;
        

        Calendar mycal = new GregorianCalendar(ano,mes-1,1);
        int daysInMonth = mycal.getActualMaximum(mycal.DAY_OF_MONTH);

        for (Operacao op : operacoes){
            if (op.getNumeroConta() == conta && op.getAno()==ano && op.getMes()==mes && op.getTipoOperacao()==Operacao.CREDITO){
                credito+=op.getValorOperacao();
                
            } 
        }
        return credito;
    }




   

    public void add(Operacao op){
        operacoes.add(op);
    }

    public double gastoDiario(Integer nroConta, Calendar date){
        double totalDiario = 0;
        for(Operacao op : operacoes){
            if(op.getTipoOperacao() == op.DEBITO && op.getNumeroConta() == nroConta){
                if(op.getDia() == date.get(Calendar.DAY_OF_MONTH) && 
                op.getMes() == date.get(Calendar.MONTH)+1 && 
                op.getAno() == date.get(Calendar.YEAR)){
                    totalDiario += op.getValorOperacao();
                }
            }
        }

        return totalDiario;
    }
}