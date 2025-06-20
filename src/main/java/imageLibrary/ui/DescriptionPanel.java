package imageLibrary.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import imageLibrary.util.MetadataEditor;

/**
 * GUI panel for displaying and editing image descriptions.
 * Supports loading/saving descriptions to image EXIF metadata.
 */
public class DescriptionPanel extends JPanel {
    private JTextArea descriptionArea;
    private JButton saveDescriptionButton;
    private File currentImage;

    /**
     * Initializes the UI components.
     */
    public DescriptionPanel() {
        setLayout(new BorderLayout());

        descriptionArea = new JTextArea(4, 30);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setBorder(BorderFactory.createTitledBorder("Descripción")); 

        saveDescriptionButton = new JButton("Guardar descripción"); 
        saveDescriptionButton.addActionListener(e -> saveDescription());

        add(descriptionScrollPane, BorderLayout.CENTER);
        add(saveDescriptionButton, BorderLayout.SOUTH);
    }

    /**
     * Loads an image description into the text area.
     * @param imageFile Image file to read the description from
     */
    public void loadDescriptionForImage(File imageFile) {
        this.currentImage = imageFile;
        try {
            String desc = MetadataEditor.readDescription(imageFile);
            descriptionArea.setText(desc != null ? desc : "");
        } catch (Exception e) {
            e.printStackTrace();
            descriptionArea.setText("");
            JOptionPane.showMessageDialog(this, "Error al leer la descripción EXIF: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE); 
        }
    }

    /**
     * Saves the current description to the image EXIF metadata.
     * Uses a temporary file to avoid data corruption.
     */
    private void saveDescription() {
        if (currentImage != null) {
            String desc = descriptionArea.getText().trim();
            try {
                File tempFile = File.createTempFile("temp_image", ".jpg");
                MetadataEditor.writeDescription(currentImage, tempFile, desc.getBytes("UTF-8"));

                if (currentImage.delete() && tempFile.renameTo(currentImage)) {
                    JOptionPane.showMessageDialog(this, "Descripción guardada en EXIF.");
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo actualizar el archivo de imagen.",
                            "Error", JOptionPane.ERROR_MESSAGE); 
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar la descripción EXIF: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE); 
            }
        }
    }
}