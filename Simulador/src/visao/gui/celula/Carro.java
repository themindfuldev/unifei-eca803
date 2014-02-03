package visao.gui.celula;

public class Carro extends ItemSimulado
{
	private int velocidade;
	private int aceleracao;
	private int combustivel;
	private int odometro;

	public Carro()
	{
		super("res" + System.getProperty("file.separator") + "carro.gif");
	}

	public int getAceleracao()
	{
		return aceleracao;
	}

	public void setAceleracao(int aceleracao)
	{
		this.aceleracao = aceleracao;
		velocidade += aceleracao;
	}

	public int getCombustivel()
	{
		return combustivel;
	}

	public void setCombustivel(int combustivel)
	{
		this.combustivel = combustivel;
	}

	public int getVelocidade()
	{
		return velocidade;
	}

	public int getOdometro()
	{
		return odometro;
	}

	public void setOdometro(int odometro)
	{
		this.odometro = odometro;
	}

}
