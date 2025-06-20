package imageLibrary.ui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;

/**
 * Custom panel for displaying images with automatic scaling.
 * Maintains aspect ratio while fitting the image within the panel dimensions.
 */
public class ImagePanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private ImageIcon imagenIcon;

    /**
     * Creates an ImagePanel with the specified image file.
     * @param imagenFile The image file to display (supports standard image formats)
     */
    public ImagePanel(File imagenFile) {
        imagenIcon = new ImageIcon(imagenFile.getPath());
        setPreferredSize(new Dimension(400, 400));
    }

    /**
     * Paints the scaled image to fit the panel dimensions.
     * @param g The Graphics context for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}