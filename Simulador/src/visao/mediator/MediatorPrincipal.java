package visao.mediator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import visao.gui.EstadoDaJanelaPrincipal;
import visao.gui.EstadoDoPanelPistaDeTeste;
import visao.gui.JanelaPrincipal;
import visao.gui.PanelPistaDeTeste;

public class MediatorPrincipal
{
	private JanelaPrincipal janela;

	public MediatorPrincipal(JanelaPrincipal janela)
	{
		this.janela = janela;
		janela.setEstadoDaJanela(EstadoDaJanelaPrincipal.INICIAL);
	}

	public void registraEventos()
	{
		janela.getBtnSimular().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				janela.setEstadoDaJanela(EstadoDaJanelaPrincipal.SIMULANDO);
				janela.getPanelPistaDeTeste().novaSimulacao();
				janela.setEstadoDaJanela(EstadoDaJanelaPrincipal.PRONTO);
			}
		});

		janela.getBtnConfigurar().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				janela.setEstadoDaJanela(EstadoDaJanelaPrincipal.CONFIGURACAO);
				janela.getPanelPistaDeTeste().clean();
				PanelPistaDeTeste
						.setEstado(EstadoDoPanelPistaDeTeste.CONFIGURACAO_OBSTACULOS);
				JOptionPane
						.showMessageDialog(
								janela,
								"Adicione os obstaculos na pista. \nQuando terminar clique em confirmar.",
								"Configuracao da pista",
								JOptionPane.INFORMATION_MESSAGE);
			}
		});

		janela.getBtnConfirmar().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				janela.setEstadoDaJanela(EstadoDaJanelaPrincipal.CONFIRMACAO);

				PanelPistaDeTeste
						.setEstado(EstadoDoPanelPistaDeTeste.CONFIGURACAO_INICIAL_1);
				JOptionPane.showMessageDialog(janela,
						"Selecione as posicoes inicial e final do veiculo 1.",
						"Configuracao da pista", JOptionPane.INFORMATION_MESSAGE);

				janela.setEstadoDaJanela(EstadoDaJanelaPrincipal.PRONTO);
			}
		});

	}
}
