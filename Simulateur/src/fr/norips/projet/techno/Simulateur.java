package fr.norips.projet.techno;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import fr.norips.busAPI.Bus;
import fr.norips.busAPI.Capteur;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.event.ChangeListener;

import org.json.JSONObject;

import javax.swing.event.ChangeEvent;

public class Simulateur {

	private JFrame frame;
	private JTextField tfServer;
	private final Action action = new SwingAction();
	
	private Bus b = null;
	private Capteur cGyro = null;
	double valX=0,valY=0,valZ=0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Simulateur window = new Simulateur();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Simulateur() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void sendGyroInfo() {
		if(cGyro == null) return;
		JSONObject contents = new JSONObject();
		contents.put("x", Double.toString(valX));
		contents.put("y", Double.toString(valY));
		contents.put("z", Double.toString(valZ));
		cGyro.send(contents);
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelGyro = new JPanel();
		tabbedPane.addTab("Gyroscope", null, panelGyro, null);
		panelGyro.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panelGyro.add(panel_2);
		
		JPanel pnAngleX = new JPanel();
		panel_2.add(pnAngleX);
		pnAngleX.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lbGyroX = new JLabel("Angle X");
		pnAngleX.add(lbGyroX);
		
		JSpinner spX = new JSpinner();
		spX.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSpinner mySpinner = (JSpinner)(e.getSource());
				valX = (Integer) mySpinner.getValue();
				sendGyroInfo();
			}
		});
		pnAngleX.add(spX);
		
		JPanel pnAngleY = new JPanel();
		panel_2.add(pnAngleY);
		pnAngleY.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lbGyroY = new JLabel("Angle Y");
		pnAngleY.add(lbGyroY);
		
		JSpinner spY = new JSpinner();
		spY.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSpinner mySpinner = (JSpinner)(e.getSource());
				valY = (Integer) mySpinner.getValue();
				sendGyroInfo();
			}
		});
		pnAngleY.add(spY);
		
		JPanel pnAngleZ = new JPanel();
		panel_2.add(pnAngleZ);
		pnAngleZ.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lbGyroZ = new JLabel("Angle Z");
		pnAngleZ.add(lbGyroZ);
		
		JSpinner spZ = new JSpinner();
		spZ.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSpinner mySpinner = (JSpinner)(e.getSource());
				valZ = (Integer) mySpinner.getValue();
				sendGyroInfo();
			}
		});
		pnAngleZ.add(spZ);
		
		JPanel panel = new JPanel();
		panelGyro.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lbServer = new JLabel("Adresse serveur:");
		panel_1.add(lbServer);
		
		tfServer = new JTextField();
		panel_1.add(tfServer);
		tfServer.setColumns(10);
		
		JButton btnConnectGyro = new JButton("Connection");
		btnConnectGyro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//TODO connect gyro to bus
				String url = tfServer.getText();
				
				try {
					b = new Bus(url);
					cGyro = new Capteur("Gyroscope", "SimuGyro", b);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnConnectGyro);
		
		JButton btnDeconnectGyro = new JButton("Deconnection");
		panel.add(btnDeconnectGyro);
		
		JPanel panelGPS = new JPanel();
		tabbedPane.addTab("GPS", null, panelGPS, null);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
