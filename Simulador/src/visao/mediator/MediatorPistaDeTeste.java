package visao.mediator;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JOptionPane;

import simulacao.Simulacao;
import util.Posicao;
import util.TipoDeMovimento;
import visao.gui.EstadoDoPanelPistaDeTeste;
import visao.gui.PanelCelula;
import visao.gui.PanelPistaDeTeste;
import visao.gui.celula.Bandeira;
import visao.gui.celula.Carro;
import visao.gui.celula.Obstaculo;
import visao.gui.celula.Vazio;

public class MediatorPistaDeTeste
{
	private PanelPistaDeTeste panel;
	private int numeroLinhas, numeroColunas;
	private int delayAnimacao;
	private Queue<Movimentacao> filaMovimentacoes;
	private List<Posicao> listaObstaculos;
	private Posicao posicaoInicial1, posicaoFinal1, posicaoInicial2, posicaoFinal2;
	private ThreadMovimentacao thread;

	public MediatorPistaDeTeste(PanelPistaDeTeste panelPistaDeTeste,
			int numeroLinhas, int numeroColunas, int delayAnimacao)
	{
		this.panel = panelPistaDeTeste;
		this.numeroLinhas = numeroLinhas;
		this.numeroColunas = numeroColunas;
		this.delayAnimacao = delayAnimacao;
		this.filaMovimentacoes = new LinkedList<Movimentacao>();
		this.listaObstaculos = new ArrayList<Posicao>();
	}

	public void novaSimulacao()
	{
		filaMovimentacoes.clear();
		new Simulacao(numeroLinhas, numeroColunas, posicaoInicial1,
				posicaoFinal1, posicaoInicial2, posicaoFinal2, listaObstaculos, this);
	}

	public void mover()
	{
		thread = new ThreadMovimentacao(delayAnimacao,
				filaMovimentacoes);

		thread.start();
	}

	public void addMovimento(PanelCelula origem, PanelCelula destino,
			TipoDeMovimento movimento)
	{
		switch (movimento)
		{
		case ABAIXO:
			filaMovimentacoes.add(new Movimentacao(origem, destino,
					PanelCelula.passo, 0));
			break;

		case ACIMA:
			filaMovimentacoes.add(new Movimentacao(origem, destino,
					-PanelCelula.passo, 0));
			break;

		case DIREITA:
			filaMovimentacoes.add(new Movimentacao(origem, destino, 0,
					PanelCelula.passo));
			break;

		case ESQUERDA:
			filaMovimentacoes.add(new Movimentacao(origem, destino, 0,
					-PanelCelula.passo));
			break;

		case SUPERIOR_DIREITO:
			filaMovimentacoes.add(new Movimentacao(origem, destino,
					-PanelCelula.passo, 0));
			filaMovimentacoes.add(new Movimentacao(origem, destino, 0,
					PanelCelula.passo));			
			break;

		case SUPERIOR_ESQUERDO:
			filaMovimentacoes.add(new Movimentacao(origem, destino,
					-PanelCelula.passo, 0));
			filaMovimentacoes.add(new Movimentacao(origem, destino, 0,
					-PanelCelula.passo));			
			break;
		
		case INFERIOR_DIREITO:
			filaMovimentacoes.add(new Movimentacao(origem, destino,
					PanelCelula.passo, 0));
			filaMovimentacoes.add(new Movimentacao(origem, destino, 0,
					PanelCelula.passo));			
			break;			
			
		case INFERIOR_ESQUERDO:
			filaMovimentacoes.add(new Movimentacao(origem, destino,
					PanelCelula.passo, 0));
			filaMovimentacoes.add(new Movimentacao(origem, destino, 0,
					-PanelCelula.passo));
			break;			
		}
	}

	public PanelPistaDeTeste getPanel()
	{
		return panel;
	}

	public void registraEventos()
	{
		PanelCelula[][] matrizCelulas = panel.getMatrizPosicoes();

		for (int i = 0; i < numeroLinhas; i++)
			for (int j = 0; j < numeroColunas; j++)
				matrizCelulas[i][j].addMouseListener(new MouseAdapter()
				{
					@Override
					public void mousePressed(MouseEvent arg0)
					{
						PanelCelula panelCelula = (PanelCelula) arg0.getSource();

						switch (PanelPistaDeTeste.getEstado())
						{
						case CONFIGURACAO_OBSTACULOS:
							panelCelula.setItemSimulado(new Obstaculo());
							listaObstaculos.add(panelCelula.getPosicao());
							break;

						case CONFIGURACAO_INICIAL_1:
							panelCelula.setItemSimulado(new Carro());
							PanelPistaDeTeste
									.setEstado(EstadoDoPanelPistaDeTeste.CONFIGURACAO_FINAL_1);
							posicaoInicial1 = panelCelula.getPosicao();
							break;

						case CONFIGURACAO_FINAL_1:
							panelCelula.setItemSimulado(new Bandeira());
							PanelPistaDeTeste
									.setEstado(EstadoDoPanelPistaDeTeste.CONFIGURACAO_INICIAL_2);
							posicaoFinal1 = panelCelula.getPosicao();
							
							JOptionPane.showMessageDialog(null,
									"Selecione as posicoes inicial e final do veiculo 2.",
									"Configuracao da pista", JOptionPane.INFORMATION_MESSAGE);
							break;
							
						case CONFIGURACAO_INICIAL_2:
							panelCelula.setItemSimulado(new Carro());
							PanelPistaDeTeste
									.setEstado(EstadoDoPanelPistaDeTeste.CONFIGURACAO_FINAL_2);
							posicaoInicial2 = panelCelula.getPosicao();
							break;

						case CONFIGURACAO_FINAL_2:
							panelCelula.setItemSimulado(new Bandeira());
							PanelPistaDeTeste
									.setEstado(EstadoDoPanelPistaDeTeste.PRONTO);
							posicaoFinal2 = panelCelula.getPosicao();
							break;							
						}
					}
				});
	}

	public void clean()
	{
		PanelCelula[][] matrizCelulas = panel.getMatrizPosicoes();

		for (int i = 0; i < numeroLinhas; i++)
		{
			for (int j = 0; j < numeroColunas; j++)
			{
				matrizCelulas[i][j].setItemSimulado(new Vazio());
			}
		}
		
		listaObstaculos.clear();

	}

	public ThreadMovimentacao getThread()
	{
		return thread;
	}
	
}
