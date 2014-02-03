package visao.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import visao.mediator.MediatorPrincipal;

@SuppressWarnings("serial")
public class JanelaPrincipal extends JFrame
{
	private MediatorPrincipal mediator;
	private PanelPistaDeTeste panelPistaDeTeste;
	private JPanel panelBotoes;
	private JButton btnSimular;
	private JButton btnConfigurar;
	private JButton btnConfirmar;
	private EstadoDaJanelaPrincipal estadoDaJanela;

	public JanelaPrincipal()
	{
		// Construtor da superclasse
		super("Trabalho 01 - Simulador de pista de testes");

		// Inicializacao
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 600);
		setResizable(false);

		// Posicionamento
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int posX = (d.width - this.getWidth()) / 2;
		int posY = (d.height - this.getHeight()) / 2;
		setLocation(posX, posY);

		// Inicializacao dos componentes
		panelPistaDeTeste = new PanelPistaDeTeste(10, 10, 50);
		panelBotoes = new JPanel();
		btnSimular = new JButton("Simular");
		btnConfigurar = new JButton("Configurar");
		btnConfirmar = new JButton("Confirmar");

		// Configuracao do layout
		setLayout(new BorderLayout());
		panelBotoes.add(btnSimular);
		panelBotoes.add(btnConfigurar);
		panelBotoes.add(btnConfirmar);

		// Adicao dos componentes
		add(panelPistaDeTeste, BorderLayout.CENTER);
		add(panelBotoes, BorderLayout.SOUTH);
		mediator = new MediatorPrincipal(this);
		// Registro de eventos
		mediator.registraEventos();

		// Exibicao
		setVisible(true);
	}

	public void verificaEstado(EstadoDaJanelaPrincipal estadoDaJanela)
	{
		switch (estadoDaJanela)
		{
		case INICIAL:
			btnSimular.setEnabled(false);
			btnConfigurar.setEnabled(true);
			btnConfirmar.setEnabled(false);
			break;

		case CONFIGURACAO:
			btnSimular.setEnabled(false);
			btnConfigurar.setEnabled(false);
			btnConfirmar.setEnabled(true);
			break;

		case CONFIRMACAO:
			btnSimular.setEnabled(false);
			btnConfigurar.setEnabled(false);
			btnConfirmar.setEnabled(false);
			break;

		case PRONTO:
			btnSimular.setEnabled(true);
			btnConfigurar.setEnabled(true);
			btnConfirmar.setEnabled(false);
			break;

		case SIMULANDO:
			btnSimular.setEnabled(false);
			break;

		default:
			break;
		}

	}

	public JButton getBtnConfigurar()
	{
		return btnConfigurar;
	}

	public JButton getBtnConfirmar()
	{
		return btnConfirmar;
	}

	public JButton getBtnSimular()
	{
		return btnSimular;
	}

	public PanelPistaDeTeste getPanelPistaDeTeste()
	{
		return panelPistaDeTeste;
	}

	public EstadoDaJanelaPrincipal getEstadoDaJanela()
	{
		return estadoDaJanela;
	}

	public void setEstadoDaJanela(EstadoDaJanelaPrincipal estadoDaJanela)
	{
		this.estadoDaJanela = estadoDaJanela;
		verificaEstado(estadoDaJanela);
	}

	public void setPanelPistaDeTeste(PanelPistaDeTeste panelPistaDeTeste)
	{
		this.panelPistaDeTeste = panelPistaDeTeste;
	}

}
