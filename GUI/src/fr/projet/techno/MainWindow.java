package fr.projet.techno;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

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

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import fr.norips.busAPI.Bus;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JProgressBar;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow(args[0]);
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
	public MainWindow(String url) {
		initialize(url);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String url) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
        JPanel container = new JPanel();

        GLCapabilities glCapabilities = new GLCapabilities(GLProfile.getDefault());  
        GLCanvas glCanvas = new GLCanvas(glCapabilities);
        glCanvas.setMinimumSize(new Dimension(300, 50));
        MyGLEventListener glListener = new MyGLEventListener(); 
        Bus b;
		try {
			b = new Bus(url);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
        
        new Thread(new ClientBusGyro(b, glListener)).start();
        
        
        glCanvas.addMouseMotionListener(glListener);
        glCanvas.addGLEventListener(glListener);
        
        
        
        
        
        ChangeListener listenerX = new ChangeListener() {
      	  public void stateChanged(ChangeEvent e) {
      		  JSpinner mySpinner = (JSpinner)(e.getSource());
      		  if( mySpinner.getModel() instanceof SpinnerNumberModel) {
      			  int val = (Integer) mySpinner.getValue();
      			  glListener.angleX = val;
      		  }
      	  }
      	};
      	ChangeListener listenerY = new ChangeListener() {
    	  public void stateChanged(ChangeEvent e) {
    		  JSpinner mySpinner = (JSpinner)(e.getSource());
    		  if( mySpinner.getModel() instanceof SpinnerNumberModel) {
    			  int val = (Integer) mySpinner.getValue();
    			  glListener.angleY = val;
    		  }
    	  }
    	};
    	ChangeListener listenerZ = new ChangeListener() {
    	  public void stateChanged(ChangeEvent e) {
    		  JSpinner mySpinner = (JSpinner)(e.getSource());
    		  if( mySpinner.getModel() instanceof SpinnerNumberModel) {
    			  int val = (Integer) mySpinner.getValue();
    			  glListener.angleZ = val;
    		  }
    	  }
    	};
        SpinnerModel spinnerMod = new SpinnerNumberModel(0, -360, 360, 1);
        
        

        final FPSAnimator animator = new FPSAnimator(glCanvas, 60);  
        frame.addWindowListener(new WindowAdapter() {  
        	 
            @Override  
            public void windowClosing(WindowEvent e) {  
                animator.stop();  
                System.exit(0);  
            }  
 
        });  
        animator.start();
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		JPanel panelOne = new JPanel();
		panelOne.setMinimumSize(new Dimension(400, 10));
		JPanel panelTwo = new JPanel();
		panelTwo.setMaximumSize(new Dimension(200, 32767));
		
		JSplitPane jsp = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, true, panelOne, panelTwo);
		tabbedPane.addTab("Gyroscope", null, jsp, null);
		jsp.setDividerLocation(400);
		panelTwo.setLayout(new BoxLayout(panelTwo, BoxLayout.Y_AXIS));
		panelOne.setLayout(new BoxLayout(panelOne, BoxLayout.Y_AXIS));
		

		
		JPanel panel_accel = new JPanel();
		panelOne.add(glCanvas);
		panelOne.add(panel_accel);
		panel_accel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		panel_accel.add(panel);
		
		JLabel lblaccel_X = new JLabel("X");
		panel.add(lblaccel_X);
		
		JProgressBar pBX = new JProgressBar();
		panel.add(pBX);
		
		JPanel panel_1 = new JPanel();
		panel_accel.add(panel_1);
		
		JLabel lblaccel_Y = new JLabel("Y");
		panel_1.add(lblaccel_Y);
		
		JProgressBar pBY = new JProgressBar();
		panel_1.add(pBY);
		
		JPanel panel_2 = new JPanel();
		panel_accel.add(panel_2);
		
		JLabel lblaccel_Z = new JLabel("Z");
		panel_2.add(lblaccel_Z);
		
		JProgressBar pBZ = new JProgressBar();
		panel_2.add(pBZ);
		
        
        JPanel panel_gyroMod = new JPanel();
        panelTwo.add(panel_gyroMod);
        panel_gyroMod.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JPanel panel_x = new JPanel();
        panel_gyroMod.add(panel_x);
        panel_x.setLayout(new GridLayout(1, 2, 0, 0));
        
        
        
        
        
        JLabel lblRotX = new JLabel("Rotation X");
        panel_x.add(lblRotX);
        JSpinner spinner_x = new JSpinner(new SpinnerNumberModel(0, -360, 360, 1));
        spinner_x.addChangeListener(listenerX);
        panel_x.add(spinner_x);
        
        JPanel panel_y = new JPanel();
        panel_gyroMod.add(panel_y);
        panel_y.setLayout(new GridLayout(1, 2, 0, 0));
        
        JLabel lblRotY = new JLabel("Rotation Y");
        panel_y.add(lblRotY);
        
        JSpinner spinner_y = new JSpinner(new SpinnerNumberModel(0, -360, 360, 1));
        spinner_y.addChangeListener(listenerY);
        panel_y.add(spinner_y);
        
        JPanel panel_z = new JPanel();
        panel_gyroMod.add(panel_z);
        panel_z.setLayout(new GridLayout(1, 2, 0, 0));
        
        JLabel lblRotZ = new JLabel("Rotation Z");
        panel_z.add(lblRotZ);
        
        JSpinner spinner_z = new JSpinner(new SpinnerNumberModel(0, -360, 360, 1));
        spinner_z.addChangeListener(listenerZ);
        panel_z.add(spinner_z);
        
        JPanel panel_accelMod = new JPanel();
        panelTwo.add(panel_accelMod);
        
        JPanel panel_accelX = new JPanel();
        panel_accelMod.add(panel_accelX);
        panel_accelX.setLayout(new GridLayout(1, 2, 0, 0));
        
        JLabel lblAccelX = new JLabel("Accel X");
        panel_accelX.add(lblAccelX);
        
        JSpinner spinner_accelx = new JSpinner(new SpinnerNumberModel(0.0, -2.0, 2.0, 0.1));
        panel_accelX.add(spinner_accelx);
        
        JPanel panel_accelY = new JPanel();
        panel_accelMod.add(panel_accelY);
        panel_accelY.setLayout(new GridLayout(1, 2, 0, 0));
        
        JLabel lblAccelY = new JLabel("Accel Y");
        panel_accelY.add(lblAccelY);
        
        JSpinner spinner_accely = new JSpinner(new SpinnerNumberModel(0.0, -2.0, 2.0, 0.1));
        panel_accelY.add(spinner_accely);
        
        JPanel panel_accelZ = new JPanel();
        panel_accelMod.add(panel_accelZ);
        panel_accelZ.setLayout(new GridLayout(1, 2, 0, 0));
        
        JLabel lblAccelZ = new JLabel("Accel Z");
        panel_accelZ.add(lblAccelZ);
        
        JSpinner spinner_accelz = new JSpinner(new SpinnerNumberModel(0.0, -2.0, 2.0, 0.1));
        panel_accelZ.add(spinner_accelz);
		
		//######### PANEL GPS #############
		JPanel panel_GPS = new JPanel();
		tabbedPane.addTab("GPS", null, panel_GPS, null);
		JXMapViewer mapViewer = new JXMapViewer();

		// Create a TileFactoryInfo for OpenStreetMap
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		mapViewer.setTileFactory(tileFactory);
		
		// Use 8 threads in parallel to load the tiles
		tileFactory.setThreadPoolSize(8);

		// Set the focus
		GeoPosition frankfurt = new GeoPosition(50.11, 8.68);
		panel_GPS.setLayout(new GridLayout(0, 1, 0, 0));

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
		// Display the viewer in a JFrame
		panel_GPS.add(mapViewer);
		
		JPanel panel_3 = new JPanel();
		panel_GPS.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblLatitude = new JLabel("Latitude :");
		lblLatitude.setBounds(0, 0, 75, 67);
		panel_3.add(lblLatitude);
		
		JLabel lblLongitude = new JLabel("Longitude :");
		lblLongitude.setBounds(0, 58, 85, 67);
		panel_3.add(lblLongitude);
		
		JTextField lblLat = new JTextField("LAT");
		lblLat.setEditable(false);
		lblLat.setBounds(87, 0, 197, 67);
		panel_3.add(lblLat);
		
		JTextField lblLong = new JTextField("LONG");
		lblLong.setEditable(false);
		lblLong.setBounds(97, 58, 217, 67);
		panel_3.add(lblLong);
		
		MapClickListenerLabel sa = new MapClickListenerLabel(mapViewer,lblLat,lblLong,defaultWaypoint); 
		mapViewer.addMouseListener(sa); 
		mapViewer.addMouseMotionListener(sa); 
		new Thread(new ClientBusGPS(b, defaultWaypoint)).start();
        
        
        frame.setMinimumSize(new Dimension(600, 500));
        frame.setPreferredSize(new Dimension(600, 500));
        frame.setResizable(true);
        frame.setVisible(true);

        frame.pack();
        
        
	}
}
