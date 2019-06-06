package com.bcopstein.ExercicioRefatoracaoBanco;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

public class ContasTest{
    private Contas contas;
    
    @BeforeEach
    public void init() {
        ArrayList<Operacao> listOp = new ArrayList<Operacao>();

        listOp.add(new Operacao(1, 5, 2019, 15, 15, 56, 1, 0, 1000, 1));
        listOp.add(new Operacao(4, 3, 2018, 16, 25, 23, 1, 0, 654, 0));
        listOp.add(new Operacao(6, 8, 2017, 19, 16, 1, 3, 2, 432, 1));
        listOp.add(new Operacao(23, 1, 2019, 22, 19, 4, 2, 1, 32, 1));
        listOp.add(new Operacao(12, 2, 2018, 4, 4, 59, 3, 2, 12, 0));

        Persistencia mockPersistencia = mock(Persistencia.class);
        when(mockPersistencia.loadOperacoes()).thenReturn(listOp);
        Operacoes mockOps= mock(Operacoes.class);

        this.contas = new Contas(mockPersistencia, mockOps);
    }


}