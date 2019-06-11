package com.bcopstein.ExercicioRefatoracaoBanco;
public class Conta {
	public final int LIM_SILVER_GOLD = 50000;
	public final int LIM_GOLD_PLATINUM = 200000;
	public final int LIM_PLATINUM_GOLD = 100000;
	public final int LIM_GOLD_SILVER = 25000;

	private int numero;
	private String correntista;
	private double saldo;
	private StateConta status;

	public Conta(int umNumero, String umNome) {
		numero = umNumero;
		correntista = umNome;
		saldo = 0.0;
		status = new Silver();
	}
	
	public Conta(int umNumero, String umNome,double umSaldo, int umStatus) {
		numero = umNumero;
		correntista = umNome;
		saldo = umSaldo;

		if(umStatus == 0)
			status = new Silver();
		else if(umStatus == 1)
			status = new Gold();
		else if(umStatus == 2)
			status = new Platinum();
		else
			throw new IllegalArgumentException("Estado invalido");
	}
	
	public double getSaldo() {
		return saldo;
	}

	public Integer getNumero() {
		return numero;
	}
	
	public String getCorrentista() {
		return correntista;
	}
	
	public int getStatus() {
		return status.getStatus();
	}
	
	public String getStrStatus() {
		return status.getStrStatus();
	}
	
	public double getLimRetiradaDiaria() {
		return status.getLimRetiradaDiaria();
	}
	
	public void deposito(double valor) {
		status.deposito(valor);
	}

	public void retirada(double valor) {
		if (saldo - valor < 0.0) {
			return;
		} else {
			saldo = saldo - valor;
			status.retirada();
		}
	}

	@Override
	public String toString() {
		return "Conta [numero=" + numero + ", correntista=" + correntista + ", saldo=" + saldo + ", status=" + status
				+ "]";
	} 

	// Implementação dos estados
	//---------------------------------------------------------

	class Silver implements StateConta{

		@Override
		public String getStrStatus() {
			return "Silver";
		}

		@Override
		public double getLimRetiradaDiaria() {
			return 10000.0;
		}


		@Override
		public int getStatus() {
			return 0;
		}

		@Override
		public void deposito(double valor) {
			saldo += valor;

			if (saldo >= LIM_SILVER_GOLD) {
				status = new Gold();
			}
		}

		@Override
		public void retirada() {

		}
		
	}

	class Gold implements StateConta{

		@Override
		public String getStrStatus() {
			return "Gold";
		}

		@Override
		public double getLimRetiradaDiaria() {
			return 100000.0;
		}

		@Override
		public int getStatus() {
			return 1;
		}

		@Override
		public void deposito(double valor) {
			saldo += (valor * 1.01);

			if (saldo >= LIM_GOLD_PLATINUM) {
				status = new Platinum();
			}
		}

		@Override
		public void retirada() {
			if (saldo < LIM_GOLD_SILVER) {
				status = new Silver();
			}
		}
		
	}

	class Platinum implements StateConta{

		@Override
		public String getStrStatus() {
			return "Platinum";
		}

		@Override
		public double getLimRetiradaDiaria() {
			return 500000.0;
		}

		@Override
		public int getStatus() {
			return 2;
		}

		@Override
		public void deposito(double valor) {
			saldo += (valor * 1.025);
		}

		@Override
		public void retirada() {
			if (saldo < LIM_PLATINUM_GOLD) {
				status = new Gold();
			}
		}
		
	}
}