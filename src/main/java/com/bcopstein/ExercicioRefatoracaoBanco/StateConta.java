package com.bcopstein.ExercicioRefatoracaoBanco;

public interface StateConta{
    String getStrStatus();
    double getLimRetiradaDiaria();
    void deposito(double valor);
    void retirada();
    int getStatus();
}