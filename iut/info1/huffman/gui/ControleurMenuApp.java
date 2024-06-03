/**
 * ConroleurMenuApp.java28/05/2024
 * IUT DE RODEZPas de copyrights
 */

package iut.info1.huffman.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControleurMenuApp {

	@FXML
	private Button compresser;

	@FXML
	private Button decompresser;

	@FXML
	void activerCompresser(ActionEvent event) {
		Main.activerCompression();
	}

	@FXML
	void activerDecompresser(ActionEvent event) {
		Main.activerDecompression();
	}
}