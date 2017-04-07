package fr.projet.techno;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import com.jogamp.opengl.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class MyGLEventListener implements GLEventListener,MouseMotionListener {

    private Rectangle rect_X,rect_Y,rect_Z,rect_plane;
    private Object3D obj;
    public  double angleX = 0,angleY = 0, angleZ = 0;
	@Override  
    public void init(GLAutoDrawable drawable) {  
        GL2 gl = drawable.getGL().getGL2();  
 
        gl.glClearColor(0.8f, 0.5f, 0.0f, 1.0f);  
        gl.glEnable(GL2.GL_DEPTH_TEST);  
        gl.glClearDepth(1.0f);  
 
        gl.glShadeModel(GL2.GL_SMOOTH);  
       
        
        
        rect_X = new Rectangle(10.0f, 1.0f, 1.0f, new float[]{1.0f,0.0f,0.0f});
        rect_Y = new Rectangle(1.0f, 10.0f, 1.0f, new float[]{0.0f,1.0f,0.0f});
        rect_Z = new Rectangle(1.0f, 1.0f, 5.0f, new float[]{0.0f,0.0f,1.0f});
        rect_plane = new Rectangle(30.0f, 30.0f, 0.1f, new float[]{0.1f,0.1f,0.1f});
        
        
    }  
 
    @Override  
    public void dispose(GLAutoDrawable drawable) {  
        // TODO Auto-generated method stub  
 
    }  
 
    @Override  
    public void display(GLAutoDrawable drawable) {  
        GL2 gl = drawable.getGL().getGL2();  
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);  
        gl.glLoadIdentity();  
 
        glu.gluLookAt(  
                camera[0], camera[1], camera[2],  
                0.0f, 0.0f, 0.0f,  
                0.0f, 1.0f, 0.0f  
        );
        gl.glRotatef(angle, 0f, 1f, 1f);
        //gl.glRotatef(-90, 1f, 0f, 0f);
        //gl.glRotatef(90, 0f, 0f, 1f);
     // Prepare light parameters.
        float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = {2, 2, 2, SHINE_ALL_DIRECTIONS};
        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
        float[] lightColorSpecular = {0.7f, 0.7f, 0.7f, 1f};

        // Set light parameters.
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);

        // Enable lighting in GL.
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_LIGHTING);

        gl.glRotatef(-45, 1.0f, 0.0f, 0.0f);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        // Set material properties.
        gl.glPushMatrix();
	        gl.glRotated(angleX, 1f, 0f, 0f);
	        gl.glRotated(angleY, 0f, 1f, 0f);
	        gl.glRotated(angleZ, 0f, 0f, 1f);
	        rect_X.draw(gl);
	        rect_Y.draw(gl);
	        rect_Z.draw(gl);
	    gl.glPopMatrix();
        gl.glTranslatef(-15.0f, -15.0f, -5.0f);
        rect_plane.draw(gl);
        //glut.glutSolidSphere(1.0f, 20, 20);  
    }  
 
    @Override  
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,  
            int height) {  
        GL2 gl = drawable.getGL().getGL2();  
        gl.glViewport(x, y, width, height);  
 
        gl.glMatrixMode(GL2.GL_PROJECTION);  
        gl.glLoadIdentity();  
        glu.gluPerspective(60.0f, (float) width/height, 0.1f, 1000.0f);  
 
        gl.glMatrixMode(GL2.GL_MODELVIEW);
     
    }  
 
    private GLU glu = new GLU();  
    private GLUT glut = new GLUT();  
 
    private float camera [] = {0f, 3f, 15f};  
 
    private float [] light_0_ambient = {0.2f, 0.2f, 0.2f, 1.0f};  
    private float [] light_0_diffuse = {1.0f, 1.0f, 1.0f, 1.0f};  
    private float [] light_0_specular = {0.3f,0.3f, 0.3f, 1.0f};  
    private float [] light_0_position = {-1f, 5f, 50f, 0f};  
 
    private float [] material_specular = {10.0f, 10.0f, 10.0f, 10.0f};  
 
    private float angle = 0f;
    private int oldx=-1,oldy=-1;
    
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(oldx == -1) {
			oldx = e.getX();
		}
		if(oldy == -1) {
			oldy = e.getY();
		}
		
		if ((e.getX() - oldx) > 0)		// mouse moved to the right
			angle += 3.0f;
		else if ((e.getX() - oldx) < 0)	// mouse moved to the left
			angle -= 3.0f;
		
		oldx = e.getX();
		oldy = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}  
}  
