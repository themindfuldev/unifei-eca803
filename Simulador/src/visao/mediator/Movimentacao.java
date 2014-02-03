package visao.mediator;

import visao.gui.PanelCelula;

class Movimentacao
{
	private PanelCelula origem, destino;
	private int passoX, passoY;

	Movimentacao(PanelCelula origem, PanelCelula destino, int passoX, int passoY)
	{
		this.origem = origem;
		this.destino = destino;
		this.passoX = passoX;
		this.passoY = passoY;
	}

	public PanelCelula getOrigem()
	{
		return origem;
	}

	public PanelCelula getDestino()
	{
		return destino;
	}

	public int getPassoX()
	{
		return passoX;
	}

	public int getPassoY()
	{
		return passoY;
	}

}
