import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;;

public class SteganographyBis {
	
	public static void main(String[] args) throws IOException {
	    // Charger l'image d'origine
	    BufferedImage image = ImageIO.read(new File("image.png"));
	    
	    int width = image.getWidth();
	    int height = image.getHeight();

	    // Créer une image pour le texte en noir et blanc
	    BufferedImage blackAndWhiteImage1 = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
	    BufferedImage blackAndWhiteImage2 = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
	    BufferedImage blackAndWhiteImage3 = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

	    //StringBuilder asciiText = new StringBuilder();
	    
	    //==============================================================================
	    
	    for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	            int rgb = image.getRGB(x, y);
	            int redLSB = (rgb >> 16) & 1; // Extraction du LSB de la composante rouge
	            int greenLSB = (rgb >> 8) & 1; // Extraction du LSB de la composante verte
	            int blueLSB = rgb & 1; // Extraction du LSB de la composante bleue

	            // Mettre à l'échelle le LSB pour une pleine intensité afin de le rendre visible
	            int bwValue1 = redLSB * 255; // sera 255 si redLSB est 1, sinon 0
	            int bwValue2 = greenLSB * 255; // sera 255 si greenLSB est 1, sinon 0
	            int bwValue3 = blueLSB * 255; // sera 255 si blueLSB est 1, sinon 0

	            // Définir les valeurs RGB pour les pixels de l'image LSB
	            int rgbValue1 = (bwValue1 << 16) | (bwValue1 << 8) | bwValue1;
	            int rgbValue2 = (bwValue2 << 16) | (bwValue2 << 8) | bwValue2;
	            int rgbValue3 = (bwValue3 << 16) | (bwValue3 << 8) | bwValue3;

	            // Définir les pixels pour les images LSB
	            blackAndWhiteImage1.setRGB(x, y, rgbValue1);
	            blackAndWhiteImage2.setRGB(x, y, rgbValue2);
	            blackAndWhiteImage3.setRGB(x, y, rgbValue3);
	        }
	    }
	    
	    //Sauvegarder les images en blanc-noir obtenues
	    ImageIO.write(blackAndWhiteImage1, "png", new File("bwImage1.png"));
	    ImageIO.write(blackAndWhiteImage2, "png", new File("bwImage2.png"));
	    ImageIO.write(blackAndWhiteImage3, "png", new File("bwImage3.png"));
	    
	    // Afficher le message ASCII
	    //System.out.println(message.toString());
	    //System.out.println(asciiText.toString());
	    
	    //=========================================================================
	    
	 // Créer un StringBuilder pour le texte caché
	    StringBuilder hiddenText = new StringBuilder();

	    for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	            int rgb = image.getRGB(x, y);
	            int blueLSB = rgb & 1;
	            hiddenText.append(blueLSB);
	        }
	    }

	    // Convertissez la chaîne de bits en texte ASCII
	    StringBuilder asciiText = new StringBuilder();
	    for (int i = 0; i < hiddenText.length(); i += 8) {
	        String byteString = hiddenText.substring(i, Math.min(i + 8, hiddenText.length()));
	        int charCode = Integer.parseInt(byteString, 2);
	        asciiText.append((char) charCode);
	    }

	    // Affichez le texte ASCII
	    System.out.println("Texte caché: " + asciiText.toString());

	}
}

