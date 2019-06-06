package com.bcopstein.ExercicioRefatoracaoBanco;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class ContasTest {
    private Contas contas;
    private Conta contaTeste;

    @BeforeEach
    public void init() {
        HashMap<Integer, Conta> listContas = new HashMap<Integer, Conta>();
        contaTeste = new Conta(1, "Bruno");
        listContas.put(1, contaTeste);
        listContas.put(2, new Conta(2, "Felipe"));
        listContas.put(3, new Conta(3, "Maicon"));
        listContas.put(4, new Conta(4, "Bernardo"));

        Persistencia mockPersistencia = mock(Persistencia.class);
        when(mockPersistencia.loadContas()).thenReturn(listContas);
        Operacoes mockOps = mock(Operacoes.class);

        this.contas = new Contas(mockPersistencia, mockOps);
    }

    @Test
    @DisplayName("Testa se uma conta existente esta sendo achada")
    public void testaContaExiste() {
        assertTrue(this.contas.contaExiste(1));
    }

    @Test
    @DisplayName("Testa se uma conta nao existente esta sendo achada")
    public void testaContaNaoExiste() {
        assertTrue(!this.contas.contaExiste(6));
    }


    @Test
    @DisplayName("Testa ao setar uma conta atual ela eh retornada como conta atual")
    public void testaSetaContaAtual() {
        this.contas.setContaAtual(contaTeste);
        assertEquals(contaTeste, this.contas.getContaAtual());
    }

    @Test
    @DisplayName("Testa se uma conta esta sendo retornada ao enviar um numero")
    public void testagetContaNro() {
        assertEquals(contaTeste, this.contas.get(1));
    }

    @Test
    @DisplayName("Testa se uma conta não existente chamada por nro eh null")
    public void testagetContaNroNull() {
        assertEquals(null, this.contas.get(10));
    }

}