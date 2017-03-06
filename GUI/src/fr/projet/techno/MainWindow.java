package fr.projet.techno;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JProgressBar;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

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
        JPanel panelOne = new JPanel();
        panelOne.setMinimumSize(new Dimension(400, 10));
        JPanel panelTwo = new JPanel();
        panelTwo.setMaximumSize(new Dimension(200, 32767));
        
		JSplitPane jsp = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, true, panelOne, panelTwo);
        jsp.setDividerLocation(400);
        frame.getContentPane().add(jsp, BorderLayout.CENTER);

        GLCapabilities glCapabilities = new GLCapabilities(GLProfile.getDefault());  
        GLCanvas glCanvas = new GLCanvas(glCapabilities);
        glCanvas.setMinimumSize(new Dimension(300, 50));
        MyGLEventListener glListener = new MyGLEventListener(); 
        
        new Thread(new ClientBus(url, glListener)).start();
        
        
        glCanvas.addMouseMotionListener(glListener);
        glCanvas.addGLEventListener(glListener);
        panelTwo.setLayout(new BoxLayout(panelTwo, BoxLayout.Y_AXIS));
        panelOne.add(glCanvas);
        
        
        
        
        
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
        panelOne.setLayout(new BoxLayout(panelOne, BoxLayout.Y_AXIS));
        
        JPanel panel_accel = new JPanel();
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
        
        
        
        frame.setMinimumSize(new Dimension(600, 500));
        frame.setPreferredSize(new Dimension(600, 500));
        frame.setResizable(true);
        frame.setVisible(true);

        frame.pack();
        
        
	}

}
