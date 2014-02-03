package visao.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import util.Posicao;
import visao.mediator.MediatorPistaDeTeste;

@SuppressWarnings("serial")
public class PanelPistaDeTeste extends JPanel
{
	private MediatorPistaDeTeste mediator;
	private PanelCelula[][] matrizPosicoes;
	private static EstadoDoPanelPistaDeTeste estado;

	public PanelPistaDeTeste(int numeroLinhas, int numeroColunas,
			int delayAnimacao)
	{
		mediator = new MediatorPistaDeTeste(this, numeroLinhas, numeroColunas,
				delayAnimacao);
		estado = EstadoDoPanelPistaDeTeste.PRONTO;

		matrizPosicoes = new PanelCelula[numeroLinhas][numeroColunas];
		setLayout(new GridLayout(numeroLinhas, numeroColunas));

		for (int i = 0; i < numeroLinhas; i++)
		{
			for (int j = 0; j < numeroColunas; j++)
			{
				matrizPosicoes[i][j] = new PanelCelula(new Posicao(i, j));
				add(matrizPosicoes[i][j]);
			}
		}

		setBackground(Color.WHITE);

		mediator.registraEventos();
	}

	public void novaSimulacao()
	{
		mediator.novaSimulacao();
	}

	public PanelCelula[][] getMatrizPosicoes()
	{
		return matrizPosicoes;
	}

	public static void setEstado(EstadoDoPanelPistaDeTeste est)
	{
		estado = est;
	}

	public static EstadoDoPanelPistaDeTeste getEstado()
	{
		return estado;
	}

	public void clean()
	{
		mediator.clean();
	}

}
