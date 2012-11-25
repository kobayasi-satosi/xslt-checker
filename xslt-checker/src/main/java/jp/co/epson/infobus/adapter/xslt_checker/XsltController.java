package jp.co.epson.infobus.adapter.xslt_checker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class XsltController {

	@FXML
	private TextField xsltPath;
	@FXML
	private TextField inputFIlePath;
	@FXML
	private TextField outputFilePath;

	@FXML
	private Button exeBtn;

	@FXML
	private TextArea logTxt;
	
	private String logMessage ="";
	
	@FXML
	protected void selectXmlFile(ActionEvent e) {

		FileChooser fc = new FileChooser();
		fc.setTitle("select file");
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		fc.getExtensionFilters().add(
				new ExtensionFilter("XML", "*.xml", "*.xsl"));

		File f = fc.showOpenDialog(null);
		if (f != null) {
			Path path = f.toPath();

			String id = ((Button) e.getSource()).getId();

			switch (id) {
			case "xsltRefBtn":
				this.xsltPath.setText(path.toString());
				break;
			case "inputFilePathRefBtn":
				this.inputFIlePath.setText(path.toString());
				break;
			case "outputFilePathRefBtn":
				this.outputFilePath.setText(path.toString());
				break;
			default:
				break;
			}
		}
	}

	@FXML
	protected void executeConverter(ActionEvent e) {

		if ((isBlank(this.xsltPath)) || (isBlank(this.xsltPath))
				|| (isBlank(this.xsltPath))) {
			String msg = "設定されていないファイルパスがあります。";
			writeLogMsg(msg);
			return;
		}

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = factory.newTransformer(new StreamSource(this.xsltPath
					.getText()));

			transformer.setOutputProperty("encoding", "utf-8");
			transformer.transform(
					new StreamSource(this.inputFIlePath.getText()),
					new StreamResult(new FileOutputStream(this.outputFilePath
							.getText())));
		} catch (FileNotFoundException | TransformerException ex) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			String msg = sw.toString();
			writeLogMsg(msg);
		}
	}

	private void writeLogMsg(String msg) {
		logMessage +=  msg + System.getProperty("line.separator");
		logTxt.setText(logMessage);
		this.logTxt.setScrollTop(Double.MAX_VALUE);
	}

	private boolean isBlank(TextField tf) {
		String txt = tf.getText();
		return (txt == null) || "".equals(txt);
	}

}
