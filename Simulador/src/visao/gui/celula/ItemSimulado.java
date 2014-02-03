package visao.gui.celula;

import java.awt.Image;

import javax.swing.ImageIcon;

public abstract class ItemSimulado
{
	private Image imagem;

	protected ItemSimulado(String caminhoDaImagem)
	{
		imagem = new ImageIcon(caminhoDaImagem).getImage();
	}

	protected ItemSimulado()
	{
		imagem = null;
	}

	public Image getImagem()
	{
		return imagem;
	}

}
