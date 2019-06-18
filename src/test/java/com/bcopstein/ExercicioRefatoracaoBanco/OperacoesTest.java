package com.bcopstein.ExercicioRefatoracaoBanco;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

public class OperacoesTest{
    private Operacoes ops;
    
    @BeforeEach
    public void init() {
        ArrayList<Operacao> listOp = new ArrayList<Operacao>();

        listOp.add(Operacao.criaOperacaoAntiga(1, 5, 2019, 15, 15, 56, 1, 0, 1000, 1));
        listOp.add(Operacao.criaOperacaoAntiga(4, 3, 2018, 16, 25, 23, 1, 0, 654, 0));
        listOp.add(Operacao.criaOperacaoAntiga(6, 8, 2017, 19, 16, 1, 3, 2, 432, 1));
        listOp.add(Operacao.criaOperacaoAntiga(23, 1, 2019, 22, 19, 4, 2, 1, 32, 1));
        listOp.add(Operacao.criaOperacaoAntiga(12, 2, 2018, 4, 4, 59, 3, 2, 12, 0));

        Persistencia mockPersistencia = mock(Persistencia.class);
        when(mockPersistencia.loadOperacoes()).thenReturn(listOp);
        this.ops = new Operacoes(mockPersistencia);
    }

    @Test
    @DisplayName("Testa se uma lista de operacoes eh retornada sendo passado o numero da conta.")
    public void testaGetOperacoes() {
        ObservableList<Operacao> lista = ops.getOperacoesConta(1);
        assertEquals(2, lista.size());
        assertEquals(1000, lista.get(0).getValorOperacao());
        assertEquals(654, lista.get(1).getValorOperacao());
    }
    
    @Test
    @DisplayName("Testa se o gasto diario de uma conta esta sendo calculado")
    public void testaGastoDiario() {
        Calendar date = new GregorianCalendar();

        ops.add(Operacao.criaOperacaoAntiga(date.get(Calendar.DAY_OF_MONTH),
        date.get(Calendar.MONTH + 1),
        date.get(Calendar.YEAR),
        15, 15, 56, 1, 0, 1000, 1));

        double valor = ops.gastoDiario(1, date);
        assertEquals(1000, valor);
    }

    @Test
    @DisplayName("Testa se o gasto diario de uma conta eh zero se nao ha operacoes diarias")
    public void testaGastoDiarioSemOps() {
        Calendar date = new GregorianCalendar();
        double valor = ops.gastoDiario(2, date);
        assertEquals(0, valor);
    }

    @Test
    @DisplayName("Testa se o gasto diario de uma conta nao existente eh zero")
    public void testaGastoDiarioSemConta() {
        Calendar date = new GregorianCalendar();
        double valor = ops.gastoDiario(8, date);
        assertEquals(0, valor);
    }

}