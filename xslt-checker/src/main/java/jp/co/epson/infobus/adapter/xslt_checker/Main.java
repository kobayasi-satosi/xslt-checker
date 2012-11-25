package jp.co.epson.infobus.adapter.xslt_checker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	// JavaFXアプリケーション起動時にコールバックされるメソッド

	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle("XSLT Checker");

		Parent root = FXMLLoader.load(getClass().getResource(
				"xslt-checker.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}