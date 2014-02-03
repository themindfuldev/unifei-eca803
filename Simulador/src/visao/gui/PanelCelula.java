package visao.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import util.Posicao;
import visao.gui.celula.ItemSimulado;
import visao.gui.celula.Vazio;

@SuppressWarnings("serial")
public class PanelCelula extends JPanel
{
	private Posicao posicao, posicaoImagem;
	private ItemSimulado itemSimulado;
	private Image imagem;
	public static int dimensao = 50;
	public static int iteracoes = 25;
	public static int passo = dimensao / iteracoes;

	public PanelCelula(Posicao posicao)
	{
		this.posicao = posicao;
		this.posicaoImagem = new Posicao(0, 5);
		this.itemSimulado = new Vazio();

		setBackground(Color.LIGHT_GRAY);
		setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, Color.DARK_GRAY));
		setSize(dimensao, dimensao);
	}

	public Posicao getPosicao()
	{
		return posicao;
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		if (imagem != null)
			g.drawImage(imagem, posicaoImagem.getColuna(), posicaoImagem
					.getLinha(), null);
	}

	public void passoImagem(int passoX, int passoY)
	{
		posicaoImagem = new Posicao(posicaoImagem.getLinha() + passoX,
				posicaoImagem.getColuna() + passoY);
	}

	public ItemSimulado getItemSimulado()
	{
		return itemSimulado;
	}

	public void setItemSimulado(ItemSimulado itemSimulado)
	{
		this.itemSimulado = itemSimulado;
		imagem = itemSimulado.getImagem();
		repaint();
	}

	public void cleanImagem()
	{
		itemSimulado = null;
		imagem = null;
		cleanPosicao();
	}

	public void cleanPosicao()
	{
		posicaoImagem = new Posicao(0, 5);
		repaint();
	}

}
