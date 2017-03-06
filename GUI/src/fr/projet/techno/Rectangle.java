package fr.projet.techno;

import com.jogamp.opengl.GL2;

public class Rectangle {
	private float m_xlen,m_ylen,m_zlen;
	private float[] m_color;
	public Rectangle(float xlen,float ylen, float zlen, float[] color) {
		m_xlen = xlen;
		m_ylen = ylen;
		m_zlen = zlen;
		m_color = color.clone();
	}
	
	public void draw(GL2 gl) {
        //gl.glColor3f(m_color[0],m_color[1],m_color[2]);    // 
        float[] rgba = {m_color[0],m_color[1],m_color[2]};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);
		gl.glBegin(gl.GL_QUADS);        // Draw The Cube Using quads
			gl.glNormal3d(0, 1, 0);
	        gl.glVertex3f( m_xlen, m_ylen,0.0f);    // Top Right Of The Quad (Top)
	        gl.glVertex3f(0.0f, m_ylen,0.0f);    // Top Left Of The Quad (Top)
	        gl.glVertex3f(0.0f, m_ylen, m_zlen);    // Bottom Left Of The Quad (Top)
	        gl.glVertex3f( m_xlen, m_ylen, m_zlen);    // Bottom Right Of The Quad (Top)
	        
	        gl.glNormal3d(0, -1, 0);
	        gl.glVertex3f( m_xlen,0.0f, m_zlen);    // Top Right Of The Quad (Bottom)
	        gl.glVertex3f(0.0f,0.0f, m_zlen);    // Top Left Of The Quad (Bottom)
	        gl.glVertex3f(0.0f,0.0f,0.0f);    // Bottom Left Of The Quad (Bottom)
	        gl.glVertex3f( m_xlen,0.0f,0.0f);    // Bottom Right Of The Quad (Bottom)
	        
	        gl.glNormal3d(0, 0, -1);
	        gl.glVertex3f( m_xlen, m_ylen, m_zlen);    // Top Right Of The Quad (Front)
	        gl.glVertex3f(0.0f, m_ylen, m_zlen);    // Top Left Of The Quad (Front)
	        gl.glVertex3f(0.0f,0.0f, m_zlen);    // Bottom Left Of The Quad (Front)
	        gl.glVertex3f( m_xlen,0.0f, m_zlen);    // Bottom Right Of The Quad (Front)
	        
	        gl.glNormal3d(0, 0, 1);
	        gl.glVertex3f( m_xlen,0.0f,0.0f);    // Top Right Of The Quad (Back)
	        gl.glVertex3f(0.0f,0.0f,0.0f);    // Top Left Of The Quad (Back)
	        gl.glVertex3f(0.0f, m_ylen,0.0f);    // Bottom Left Of The Quad (Back)
	        gl.glVertex3f( m_xlen, m_ylen,0.0f);    // Bottom Right Of The Quad (Back)
	        
	        gl.glNormal3d(-1, 0, 0);
	        gl.glVertex3f(0.0f, m_ylen, m_zlen);    // Top Right Of The Quad (Left)
	        gl.glVertex3f(0.0f, m_ylen,0.0f);    // Top Left Of The Quad (Left)
	        gl.glVertex3f(0.0f,0.0f,0.0f);    // Bottom Left Of The Quad (Left)
	        gl.glVertex3f(0.0f,0.0f, m_zlen);    // Bottom m_zlent Of The Quad (Left)
	        
	        gl.glNormal3d(1, 0, 0);
	        gl.glVertex3f( m_xlen, m_ylen,0.0f);    // Top Right Of The Quad (Right)
	        gl.glVertex3f( m_xlen, m_ylen, m_zlen);    // Top Left Of The Quad (Right)
	        gl.glVertex3f( m_xlen,0.0f, m_zlen);    // Bottom Left Of The Quad (Right)
	        gl.glVertex3f( m_xlen,0.0f,0.0f);    // Bottom Right Of The Quad (Right)
        gl.glEnd();            // End Drawing The Cube - See more at: http://www.codemiles.com/c-opengl-examples/draw-3d-cube-using-opengl-t9018.html#sthash.eyOYlvmM.dpuf
    
	}
}
