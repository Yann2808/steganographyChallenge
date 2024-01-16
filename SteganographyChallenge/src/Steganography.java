import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Steganography {

    public static void main(String[] args) throws IOException {
        // Charger l'image d'origine
        BufferedImage image = ImageIO.read(new File("image.png"));

        // Créer une nouvelle image pour chaque message caché
        BufferedImage image1 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        BufferedImage image2 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        BufferedImage image3 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

        // Parcourir chaque pixel de l'image d'origine
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                // Extraire les bits les plus faibles des couleurs RGB du pixel
                int r = image.getRGB(i, j) & 1;
                int g = (image.getRGB(i, j) >> 1) & 1;
                int b = (image.getRGB(i, j) >> 2) & 1;

                // Modifier les bits les plus faibles des couleurs RGB de l'image cachée
                image1.setRGB(i, j, r);
                image2.setRGB(i, j, g);
                image3.setRGB(i, j, b);
            }
        }

        // Enregistrer les images cachées
        ImageIO.write(image1, "png", new File("image1.png"));
        ImageIO.write(image2, "png", new File("image2.png"));
        ImageIO.write(image3, "png", new File("image3.png"));

        // Afficher le texte caché
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < image.getWidth(); i++) {
            message.append((image.getRGB(i, 0) >> 7) == 1 ? "1" : "0");
        }
        
     // Vérifier si le message est vide
        if (message.isEmpty()) {
            System.out.println("Il n'y a pas de message caché.");
        } else {
            System.out.println("Le message caché est : " + message.toString());
        }
        //System.out.println(message);
    }
}