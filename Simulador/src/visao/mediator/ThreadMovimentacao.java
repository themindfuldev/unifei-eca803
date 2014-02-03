package visao.mediator;

import java.util.Queue;

import visao.gui.PanelCelula;
import visao.gui.celula.Carro;
import visao.gui.celula.ItemSimulado;

public class ThreadMovimentacao extends Thread
{
	private int delayAnimacao;
	private Queue<Movimentacao> filaMovimentacoes;
	private boolean continuar;

	public ThreadMovimentacao(int delayAnimacao, Queue<Movimentacao> filaMovimentacoes)
	{
		this.delayAnimacao = delayAnimacao;
		this.filaMovimentacoes = filaMovimentacoes;
		continuar = true;
	}

	public void run()
	{
		ItemSimulado item;
		PanelCelula origem, destino;
		int deslocamentoX, deslocamentoY, passoX, passoY;

		for (Movimentacao movimentacao : filaMovimentacoes)
		{
			if (continuar == false) return;
			
			deslocamentoX = 0;
			deslocamentoY = 0;
			origem = movimentacao.getOrigem();
			destino = movimentacao.getDestino();
			passoX = movimentacao.getPassoX();
			passoY = movimentacao.getPassoY();

			item = origem.getItemSimulado();

			if (!(item instanceof Carro))
				continue;

			destino.setItemSimulado(item);

			if (passoX > 0)
				deslocamentoX = -PanelCelula.dimensao - 3;
			else if (passoX < 0)
				deslocamentoX = PanelCelula.dimensao + 3;
			if (passoY > 0)
				deslocamentoY = -PanelCelula.dimensao - 8;
			else if (passoY < 0)
				deslocamentoY = PanelCelula.dimensao + 8;

			destino.passoImagem(deslocamentoX, deslocamentoY);

			if (passoX > 0)
				deslocamentoX = Math.abs(deslocamentoX) - 3;
			else if (passoX < 0)
				deslocamentoX = Math.abs(deslocamentoX) - 3;
			if (passoY > 0)
				deslocamentoY = Math.abs(deslocamentoY) - 3;
			else if (passoY < 0)
				deslocamentoY = Math.abs(deslocamentoY) - 3;

			try
			{
				for (int pX = 0, pY = 0; pX <= deslocamentoX && pY <= deslocamentoY; pX += Math
						.abs(passoX), pY += Math.abs(passoY))
				{
					origem.passoImagem(passoX, passoY);
					destino.passoImagem(passoX, passoY);
					origem.repaint();
					destino.repaint();
					Thread.sleep(delayAnimacao);
				}

			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			origem.cleanImagem();
			destino.cleanPosicao();

		}
	}

	public void setContinuar(boolean continuar)
	{
		this.continuar = continuar;
	}
	
}
