package simulacao;

import java.util.List;

import javax.swing.JOptionPane;

import util.Posicao;
import util.TipoDeMovimento;
import visao.gui.PanelCelula;
import visao.mediator.MediatorPistaDeTeste;

public class Simulacao
{
	private int numeroLinhas, numeroColunas;
	private List<Posicao> listaObstaculos;
	private Posicao posicaoInicial1, posicaoFinal1, posicaoInicial2, posicaoFinal2;
	private MediatorPistaDeTeste mediator;
	private int matrizTabuleiro[][];

	public Simulacao(int numeroLinhas, int numeroColunas,
			Posicao posicaoInicial, Posicao posicaoFinal,
			Posicao posicaoInicial2, Posicao posicaoFinal2, List<Posicao> listaObstaculos,
			MediatorPistaDeTeste mediatorPistaDeTeste)
	{
		this.numeroLinhas = numeroLinhas;
		this.numeroColunas = numeroColunas;
		this.posicaoInicial1 = posicaoInicial;
		this.posicaoFinal1 = posicaoFinal;
		this.posicaoInicial2 = posicaoInicial2;
		this.posicaoFinal2 = posicaoFinal2;
		this.listaObstaculos = listaObstaculos;
		this.mediator = mediatorPistaDeTeste;

		simular();
	}

	private void simular()
	{
		/*
		 * TODO: - Criar matriz de Posicao - Alimenta-la com posicao inicial,
		 * final e obstaculos - Realizar simulacao - A cada movimento, chamar
		 * mediator.addMovimento() - Ao final, chamar mediator.mover()
		 */

		// Cria matriz de posicao
		montaMatrizComItens();

		// Gera movimentacao
		if (geraMovimentacao())
		{
			// Solicita movimento visual
			mediator.mover();
		} else
		{
			JOptionPane
					.showMessageDialog(null,
							"Algoritmo de rota não foi capaz de determinar um caminho válido.");
		}

	}

	private boolean geraMovimentacao()
	{
		Posicao pAtual = new Posicao(posicaoInicial1.getLinha(), posicaoInicial1
				.getColuna());
		Posicao pAnterior = new Posicao(posicaoInicial1.getLinha(), posicaoInicial1
				.getColuna());
		PanelCelula[][] matrizCelulas = mediator.getPanel().getMatrizPosicoes();
		TipoDeMovimento tipoMovimento = TipoDeMovimento.ABAIXO;
		boolean movimento = false;
		boolean movimentoDeContorno = false;
		boolean jaTenteiOOutroLado = false;
		boolean jaTenteiOsDoisLados = false;
		boolean linhaEncontrada = false;
		boolean colunaEncontrada = false;
		boolean caminhoImpossivel = false;
		int contadorPassos = 0;
		TipoDeMovimento direcao = TipoDeMovimento.NULO;

		// Algoritmo de movimentacao
		while (((Math.abs(pAtual.getLinha() - posicaoFinal1.getLinha()) != 0) || (Math
				.abs(pAtual.getColuna() - posicaoFinal1.getColuna()) != 0))
				&& !caminhoImpossivel && contadorPassos < 100)
		{
			// JOptionPane.showMessageDialog(null, "direcao:"+direcao);
			// ---Determina direcao do movimento
			// Direita
			if (pAtual.getColuna() < posicaoFinal1.getColuna()
					&& (direcao == TipoDeMovimento.NULO || direcao == TipoDeMovimento.DIREITA))
			{
				/*
				 * JOptionPane.showMessageDialog(null, "Direita");
				 * if(movimentoPermitido(TipoDeMovimento.DIREITA, pAtual) &&
				 * !colunaEncontrada){ pAtual.setColuna(pAtual.getColuna()+1);
				 * movimento = true; } // Seta tipo de movimento if(movimento)
				 * tipoMovimento = TipoDeMovimento.DIREITA; else {
				 * JOptionPane.showMessageDialog(null, "Direita: impossivel
				 * pA["+pAtual.getLinha()+"]["+pAtual.getColuna()+"]");
				 * caminhoImpossivel = true; }
				 */
				// Seta direcao
				direcao = TipoDeMovimento.DIREITA;

				// JOptionPane.showMessageDialog(null, "Direita:
				// movP["+movimentoPermitido(TipoDeMovimento.DIREITA, pAtual)+"]
				// matriz["+matrizTabuleiro[(pAtual.getLinha())][(pAtual.getColuna()+1)]+"]
				// colEnc["+colunaEncontrada+"]");
				if (movimentoPermitido(TipoDeMovimento.DIREITA, pAtual)
						&& !colunaEncontrada)
				{
					pAtual.setColuna(pAtual.getColuna() + 1);
					movimento = true;
					tipoMovimento = TipoDeMovimento.DIREITA;

					direcao = TipoDeMovimento.NULO;
				} else
				{

					// ---Contorna obstaculo
					// --Escolhe direcao de contorno
					// Tenta contornar por cima
					if (!jaTenteiOOutroLado)
					{
						// JOptionPane.showMessageDialog(null, "Tentativa por Cima");
						if (movimentoPermitido(TipoDeMovimento.ACIMA, pAtual)
								&& !linhaEncontrada)
						{
							pAtual.setLinha(pAtual.getLinha() - 1);
							movimentoDeContorno = true;
							tipoMovimento = TipoDeMovimento.ACIMA;
							// JOptionPane.showMessageDialog(null, "Tentativa por Cima:
							// OK");
						} else
						{
							jaTenteiOOutroLado = true;
							// JOptionPane.showMessageDialog(null, "Tentativa por Cima:
							// FALHOU");
						}
					}
					// Tenta contornar por baixo
					else if (!jaTenteiOsDoisLados)
					{
						// JOptionPane.showMessageDialog(null, "Tentativa por Baixo
						// movP "+movimentoPermitido(TipoDeMovimento.ABAIXO, pAtual)+"
						// / linEnc "+linhaEncontrada);
						if (movimentoPermitido(TipoDeMovimento.ABAIXO, pAtual)
								&& !linhaEncontrada)
						{
							pAtual.setLinha(pAtual.getLinha() + 1);
							movimentoDeContorno = true;
							tipoMovimento = TipoDeMovimento.ABAIXO;
							// JOptionPane.showMessageDialog(null, "Tentativa por
							// Baixo: OK");
						} else
						{
							jaTenteiOsDoisLados = true;
							// JOptionPane.showMessageDialog(null, "Tentativa por
							// Baixo: FALHOU");
						}
					}
					// Caminho impossivel
					else
					{
						// JOptionPane.showMessageDialog(null, "Direita: impossivel
						// pA["+pAtual.getLinha()+"]["+pAtual.getColuna()+"]");
						caminhoImpossivel = true;
					}
				}
			}

			// Acima
			else if (pAtual.getLinha() > posicaoFinal1.getLinha()
					&& (direcao == TipoDeMovimento.NULO || direcao == TipoDeMovimento.ACIMA))
			{
				/*
				 * JOptionPane.showMessageDialog(null, "Acima");
				 * if(movimentoPermitido(TipoDeMovimento.ACIMA, pAtual) &&
				 * !linhaEncontrada){ pAtual.setLinha(pAtual.getLinha()-1);
				 * movimento = true; } // Seta tipo de movimento if(movimento)
				 * tipoMovimento = TipoDeMovimento.ACIMA; else {
				 * JOptionPane.showMessageDialog(null, "Cima: impossivel
				 * pA["+pAtual.getLinha()+"]["+pAtual.getColuna()+"]");
				 * caminhoImpossivel = true; }
				 */
				// Seta direcao
				direcao = TipoDeMovimento.ACIMA;

				// JOptionPane.showMessageDialog(null, "Acima
				// movP"+movimentoPermitido(TipoDeMovimento.DIREITA,
				// pAtual)+"colEnc"+colunaEncontrada);
				if (movimentoPermitido(TipoDeMovimento.ACIMA, pAtual)
						&& !linhaEncontrada)
				{
					pAtual.setLinha(pAtual.getLinha() - 1);
					movimento = true;
					tipoMovimento = TipoDeMovimento.ACIMA;

					direcao = TipoDeMovimento.NULO;
				} else
				{

					// ---Contorna obstaculo
					// --Escolhe direcao de contorno
					// Tenta contornar pela direita
					if (!jaTenteiOOutroLado)
					{
						// JOptionPane.showMessageDialog(null, "Tentativa pela
						// Direita");
						if (movimentoPermitido(TipoDeMovimento.DIREITA, pAtual)
								&& !colunaEncontrada)
						{
							pAtual.setColuna(pAtual.getColuna() + 1);
							movimentoDeContorno = true;
							tipoMovimento = TipoDeMovimento.DIREITA;
							// JOptionPane.showMessageDialog(null, "Tentativa por
							// Direita: OK");
						} else
						{
							jaTenteiOOutroLado = true;
							// JOptionPane.showMessageDialog(null, "Tentativa por
							// Direita: FALHOU");
						}
					}
					// Tenta contornar pela esquerda
					else if (!jaTenteiOsDoisLados)
					{
						// JOptionPane.showMessageDialog(null, "Tentativa pela
						// Esquerda");
						if (movimentoPermitido(TipoDeMovimento.ESQUERDA, pAtual)
								&& !colunaEncontrada)
						{
							pAtual.setColuna(pAtual.getColuna() - 1);
							movimentoDeContorno = true;
							tipoMovimento = TipoDeMovimento.ESQUERDA;
							// JOptionPane.showMessageDialog(null, "Tentativa por
							// Esquerda: OK");
						} else
						{
							jaTenteiOsDoisLados = true;
							// JOptionPane.showMessageDialog(null, "Tentativa por
							// Esquerda: FALHOU");
						}
					}
					// Caminho impossivel
					else
					{
						// JOptionPane.showMessageDialog(null, "Acima: impossivel
						// pA["+pAtual.getLinha()+"]["+pAtual.getColuna()+"]");
						caminhoImpossivel = true;
					}
				}
			}

			// Abaixo
			else if (pAtual.getLinha() < posicaoFinal1.getLinha()
					&& (direcao == TipoDeMovimento.NULO || direcao == TipoDeMovimento.ABAIXO))
			{
				// Seta direcao
				direcao = TipoDeMovimento.ABAIXO;

				// JOptionPane.showMessageDialog(null, "Abaixo
				// movP"+movimentoPermitido(TipoDeMovimento.DIREITA,
				// pAtual)+"colEnc"+colunaEncontrada);
				if (movimentoPermitido(TipoDeMovimento.ABAIXO, pAtual)
						&& !linhaEncontrada)
				{
					pAtual.setLinha(pAtual.getLinha() + 1);
					movimento = true;
					tipoMovimento = TipoDeMovimento.ABAIXO;

					direcao = TipoDeMovimento.NULO;
				} else
				{

					// ---Contorna obstaculo
					// --Escolhe direcao de contorno
					// Tenta contornar pela direita
					if (!jaTenteiOOutroLado)
					{
						// JOptionPane.showMessageDialog(null, "Tentativa pela Direita
						// movP["+movimentoPermitido(TipoDeMovimento.DIREITA,
						// pAtual)+"] colEnc["+colunaEncontrada+"]");
						if (movimentoPermitido(TipoDeMovimento.DIREITA, pAtual)
								&& !colunaEncontrada)
						{
							pAtual.setColuna(pAtual.getColuna() + 1);
							movimentoDeContorno = true;
							tipoMovimento = TipoDeMovimento.DIREITA;
							// JOptionPane.showMessageDialog(null, "Tentativa por
							// Direita: OK");
						} else
						{
							jaTenteiOOutroLado = true;
							// JOptionPane.showMessageDialog(null, "Tentativa por
							// Direita: FALHOU");
						}
					}
					// Tenta contornar pela esquerda
					else if (!jaTenteiOsDoisLados)
					{
						// JOptionPane.showMessageDialog(null, "Tentativa pela
						// Esquerda");
						if (movimentoPermitido(TipoDeMovimento.ESQUERDA, pAtual)
								&& !colunaEncontrada)
						{
							pAtual.setColuna(pAtual.getColuna() - 1);
							movimentoDeContorno = true;
							tipoMovimento = TipoDeMovimento.ESQUERDA;
							// JOptionPane.showMessageDialog(null, "Tentativa por
							// Esquerda: OK");
						} else
						{
							jaTenteiOsDoisLados = true;
							// JOptionPane.showMessageDialog(null, "Tentativa por
							// Esquerda: FALHOU");
						}
					}
					// Caminho impossivel
					else
					{
						// JOptionPane.showMessageDialog(null, "Abaixo: impossivel
						// pA["+pAtual.getLinha()+"]["+pAtual.getColuna()+"]");
						caminhoImpossivel = true;
					}
				}
			}

			// Esquerda
			else if (pAtual.getColuna() > posicaoFinal1.getColuna()
					&& (direcao == TipoDeMovimento.NULO || direcao == TipoDeMovimento.ESQUERDA))
			{
				/*
				 * JOptionPane.showMessageDialog(null, "Esquerda");
				 * if(movimentoPermitido(TipoDeMovimento.ESQUERDA, pAtual) &&
				 * !colunaEncontrada){ pAtual.setColuna(pAtual.getColuna()-1);
				 * movimento = true; } // Seta tipo de movimento if(movimento)
				 * tipoMovimento = TipoDeMovimento.ESQUERDA; else {
				 * JOptionPane.showMessageDialog(null, "Esquerda: impossivel
				 * pA["+pAtual.getLinha()+"]["+pAtual.getColuna()+"]");
				 * caminhoImpossivel = true; }
				 */

				// Seta direcao
				direcao = TipoDeMovimento.ESQUERDA;

				// JOptionPane.showMessageDialog(null, "Esquerda
				// movP"+movimentoPermitido(TipoDeMovimento.DIREITA,
				// pAtual)+"colEnc"+colunaEncontrada);
				if (movimentoPermitido(TipoDeMovimento.ESQUERDA, pAtual)
						&& !colunaEncontrada)
				{
					pAtual.setColuna(pAtual.getColuna() - 1);
					movimento = true;
					tipoMovimento = TipoDeMovimento.ESQUERDA;

					direcao = TipoDeMovimento.NULO;
				} else
				{

					// ---Contorna obstaculo
					// --Escolhe direcao de contorno
					// Tenta contornar por cima
					if (!jaTenteiOOutroLado)
					{
						// JOptionPane.showMessageDialog(null, "Tentativa por Cima");
						if (movimentoPermitido(TipoDeMovimento.ACIMA, pAtual)
								&& !linhaEncontrada)
						{
							pAtual.setLinha(pAtual.getLinha() - 1);
							movimentoDeContorno = true;
							tipoMovimento = TipoDeMovimento.ACIMA;
							// JOptionPane.showMessageDialog(null, "Tentativa por Cima:
							// OK");
						} else
						{
							jaTenteiOOutroLado = true;
							// JOptionPane.showMessageDialog(null, "Tentativa por Cima:
							// FALHOU");
						}
					}
					// Tenta contornar por baixo
					else if (!jaTenteiOsDoisLados)
					{
						// JOptionPane.showMessageDialog(null, "Tentativa por Baixo");
						if (movimentoPermitido(TipoDeMovimento.ABAIXO, pAtual)
								&& !linhaEncontrada)
						{
							pAtual.setLinha(pAtual.getLinha() + 1);
							movimentoDeContorno = true;
							tipoMovimento = TipoDeMovimento.ABAIXO;
							// JOptionPane.showMessageDialog(null, "Tentativa por
							// Baixo: OK");
						} else
						{
							jaTenteiOsDoisLados = true;
							// JOptionPane.showMessageDialog(null, "Tentativa por
							// Baixo: FALHOU");
						}
					}
					// Caminho impossivel
					else
					{
						// JOptionPane.showMessageDialog(null, "Esquerda: impossivel
						// pA["+pAtual.getLinha()+"]["+pAtual.getColuna()+"]");
						caminhoImpossivel = true;
					}
				}
			}

			// ERRO: Movimento nao detectado
			else
			{
				JOptionPane.showMessageDialog(null,
						"ERRO: Movimento não identificado.");
			}

			if (movimento || movimentoDeContorno)
			{
				// Guarda movimento correto
				if (movimento)
				{
					if ((pAtual.getLinha() == posicaoFinal1.getLinha())
							&& !linhaEncontrada)
					{
						// linhaEncontrada=true;
					}
					if ((pAtual.getColuna() == posicaoFinal1.getColuna())
							&& !colunaEncontrada)
					{
						// colunaEncontrada=true;
					}
				}

				// Registra movimento
				mediator.addMovimento(matrizCelulas[pAnterior.getLinha()][pAnterior
						.getColuna()], matrizCelulas[pAtual.getLinha()][pAtual
						.getColuna()], tipoMovimento);

				// Atualiza passo anteior
				pAnterior.setLinha(pAtual.getLinha());
				pAnterior.setColuna(pAtual.getColuna());

				// Trata variaveis de controle
				if (movimento)
				{
					jaTenteiOOutroLado = false;
					jaTenteiOsDoisLados = false;
					movimentoDeContorno = false;

					movimento = false;
				}
				if (movimentoDeContorno)
				{
					movimentoDeContorno = false;
				}
			}// if: houve movimento

			// Controla limite de tentativas
			contadorPassos++;

		}// while

		if (contadorPassos >= 100)
		{
			return false;
		}

		if (!caminhoImpossivel)
		{
			return true;
		}

		return false;
	}

	private void montaMatrizComItens()
	{
		matrizTabuleiro = new int[numeroLinhas][numeroColunas];

		// ---Zera matriz
		for (int i = 0; i < numeroLinhas; i++)
		{
			for (int j = 0; j < numeroColunas; j++)
			{
				matrizTabuleiro[i][j] = 0;
			}// for
		}// for

		// ---Inclui obstaculos
		// 0: vazio
		// 1: carro
		// 2: obstaculo
		// 3: posicao inicial
		// 4: posicao final
		for (Posicao p : listaObstaculos)
		{
			matrizTabuleiro[p.getLinha()][p.getColuna()] = 2;
		}// for

		// ---Seta posicoes INICIAL e FINAL
		matrizTabuleiro[posicaoInicial1.getLinha()][posicaoInicial1.getColuna()] = 3;
		matrizTabuleiro[posicaoFinal1.getLinha()][posicaoFinal1.getColuna()] = 4;

		/*
		 * for(int i=0;i<numeroLinhas;i++){ for(int j=0;j<numeroColunas;j++){
		 * System.out.print("["+matrizTabuleiro[i][j]+"]"); }
		 * System.out.println(); }
		 */
	}

	private boolean movimentoPermitido(TipoDeMovimento direcao,
			Posicao posicaoAtual)
	{
		switch (direcao)
		{
		case ACIMA:
			if (
			// A posicao final do movimento esta dentro da matriz
			((posicaoAtual.getLinha() - 1) >= 0) &&
			// Nao obstaculos para o movimento
					(matrizTabuleiro[(posicaoAtual.getLinha() - 1)][posicaoAtual
							.getColuna()] != 2))
			{
				return true;
			}
			break;

		case ABAIXO:
			if (
			// A posicao final do movimento esta dentro da matriz
			((posicaoAtual.getLinha() + 1) < numeroLinhas) &&
			// Nao obstaculos para o movimento
					(matrizTabuleiro[(posicaoAtual.getLinha() + 1)][posicaoAtual
							.getColuna()] != 2))
			{
				return true;
			}
			break;

		case ESQUERDA:
			if (
			// A posicao final do movimento esta dentro da matriz
			((posicaoAtual.getColuna() - 1) >= 0) &&
			// Nao obstaculos para o movimento
					(matrizTabuleiro[posicaoAtual.getLinha()][(posicaoAtual
							.getColuna() - 1)] != 2))
			{
				return true;
			}
			break;

		case DIREITA:
			if (
			// A posicao final do movimento esta dentro da matriz
			((posicaoAtual.getColuna() + 1) < numeroColunas) &&
			// Nao obstaculos para o movimento
					(matrizTabuleiro[posicaoAtual.getLinha()][(posicaoAtual
							.getColuna() + 1)] != 2))
			{
				return true;
			}
			break;
		default:
			break;
		}

		return false;
	}
}
