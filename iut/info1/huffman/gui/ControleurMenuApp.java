/**
 * ConroleurMenuApp.java28/05/2024
 * IUT DE RODEZPas de copyrights
 */

package iut.info1.huffman.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControleurMenuApp {

    @FXML
    private Button compresser;

    @FXML
    private Button decompresser;

    @FXML
    private ImageView logoIUT;

    @FXML
    public void initialize() {
	Image image = new Image(getClass().getResourceAsStream("/iut/info1/huffman/gui/Bloc_logo_IUT_2024.jpg"));
	logoIUT.setImage(image);
    }

    @FXML
    void activerCompresser(ActionEvent event) {
	Main.activerCompression();
    }

    @FXML
    void activerDecompresser(ActionEvent event) {
	Main.activerDecompression();
    }
}