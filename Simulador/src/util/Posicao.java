package util;

public class Posicao
{
	private int linha, coluna;

	public Posicao(int linha, int coluna)
	{
		super();
		this.linha = linha;
		this.coluna = coluna;
	}

	public int getColuna()
	{
		return coluna;
	}

	public int getLinha()
	{
		return linha;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

}
