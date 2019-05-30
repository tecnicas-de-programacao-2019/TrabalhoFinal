package com.bcopstein.ExercicioRefatoracaoBanco;

import java.util.Map;

public class Contas {
    private Map<Integer,Conta> contas;
    private Persistencia persistencia;
    
    public Contas(Persistencia persistencia){
        this.persistencia = persistencia;
        contas = persistencia.loadContas();
    }

    public void saveContas(){
        persistencia.saveContas(contas.values());
    }

    public Conta get(int nro){
        return contas.get(nro);
    }
}