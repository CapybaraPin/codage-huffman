package iut.info1.huffman.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
// import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application {

    private static Scene menuApplicationHuffman;

    private static Scene menuCompressionHuffman;

    private static Scene menuDecompressionHuffman;

    private static Stage fenetrePrincipale; 

    public static void activerCompression() {
	fenetrePrincipale.setScene(menuCompressionHuffman);
    }

    public static void activerDecompression() {
	fenetrePrincipale.setScene(menuDecompressionHuffman);
    }

    public static void activerMenu() {
	fenetrePrincipale.setScene(menuApplicationHuffman);
    }

    @Override
    public void start(Stage primaryStage) {

	try {
	    /* Chargement de la page principale */
	    FXMLLoader chargeurFXML = new FXMLLoader();
	    chargeurFXML.setLocation(
		         getClass()
		        .getResource("menuApplicationHuffman.fxml"));
	    Parent conteneur = chargeurFXML.load();
	    menuApplicationHuffman = new Scene(conteneur,625,240);

	    /* Chargement de la page de compression */
	    FXMLLoader chargeurFXMLCompression = new FXMLLoader();
	    chargeurFXMLCompression.setLocation(
		                    getClass()
		                   .getResource("menuCompressionHuffman.fxml"));
	    conteneur = chargeurFXMLCompression.load();
	    menuCompressionHuffman = new Scene(conteneur,550,350);

	    /* Chargement de la page de dÃ©compression */
	    FXMLLoader chargeurFXMLDecompression = new FXMLLoader();
	    chargeurFXMLDecompression.setLocation(
		                      getClass()
		                     .getResource("menuDecompressionHuffman.fxml"));
	    conteneur = chargeurFXMLDecompression.load();
	    menuDecompressionHuffman = new Scene(conteneur,600,400);

	    /* Ce qui concerne la page principale */
	    primaryStage.setTitle("Application Huffman-V0.3.1-alpha");
	    primaryStage.setScene(menuApplicationHuffman);
	    fenetrePrincipale = primaryStage;
	    primaryStage.show();
	} catch(Exception e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	launch(args);
    }
}