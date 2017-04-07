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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

import org.json.JSONObject;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.event.ChangeEvent;
import com.jgoodies.forms.layout.FormSpecs;

public class Simulateur {

	private JFrame frame;
	private JTextField tfServer;
	private final Action action = new SwingAction();
	
	private Bus b = null;
	private Capteur cGyro = null;
	double valX=0,valY=0,valZ=0;
	private JTextField tfGPSServer;

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
		
		JPanel panelGps = new JPanel();
		tabbedPane.addTab("GPS", null, panelGps, null);
		panelGps.setLayout(new GridLayout(0, 2, 0, 0));
		//MAP #####################
		JPanel panelMap = new JPanel();
		JXMapViewer mapViewer = new JXMapViewer();

		// Create a TileFactoryInfo for OpenStreetMap
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		mapViewer.setTileFactory(tileFactory);
		
		// Use 8 threads in parallel to load the tiles
		tileFactory.setThreadPoolSize(8);
		// Set the focus
		GeoPosition frankfurt = new GeoPosition(50.11, 8.68);
	
		mapViewer.setZoom(7);
		mapViewer.setAddressLocation(frankfurt);
		MouseInputListener mia = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(mia);
		mapViewer.addMouseMotionListener(mia);

		mapViewer.addMouseListener(new CenterMapListener(mapViewer));
		
		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
		
		mapViewer.addKeyListener(new PanKeyListener(mapViewer));
		
		// Create a waypoint painter that takes all the waypoints
		WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
		Set<Waypoint> waypoints = new HashSet<Waypoint>();
		DefaultWaypoint defaultWaypoint = new DefaultWaypoint(frankfurt);
		waypoints.add(defaultWaypoint);
		waypointPainter.setWaypoints(waypoints);
		List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
		painters.add(waypointPainter);
		CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
		mapViewer.setOverlayPainter(painter);
		final MapClickListenerSend sa = new MapClickListenerSend(mapViewer,defaultWaypoint); 
		mapViewer.addMouseListener(sa); 
		mapViewer.addMouseMotionListener(sa); 
				
		panelGps.add(mapViewer);
		
		JPanel panel_8 = new JPanel();
		panelGps.add(panel_8);
		
		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9);
		panel_9.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel label_3 = new JLabel("Adresse serveur:");
		panel_9.add(label_3);
		
		tfGPSServer = new JTextField();
		tfGPSServer.setColumns(10);
		panel_9.add(tfGPSServer);
		
		JButton btConnectGPS = new JButton("Connection");
		btConnectGPS.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String url = tfGPSServer.getText();
				
				try {
					Bus b = new Bus(url);
					Capteur cGPS = new Capteur("GPS", "SimuGPS", b);
					sa.setCapteur(cGPS);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_8.add(btConnectGPS);
		
		JButton btDeconnectGPS = new JButton("Deconnection");
		panel_8.add(btDeconnectGPS);
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
