/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryAndNum;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxNazione"
    private ComboBox<Country> boxNazione; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
	
	@FXML
	void doCalcolaConfini(ActionEvent event) {
	
		String annoS = txtAnno.getText() ;
		
		if(annoS.length() == 0) {
			txtResult.appendText("ERRORE: devi inserire un anno\n");
			return;
		}
		
		int anno;
		try {
			anno = Integer.parseInt(annoS);
		} catch (NumberFormatException e) {
			txtResult.appendText("ERRORE: l'anno deve essere in formato numerico\n");
			return;
		}
		
		if(anno < 1816) {
			txtResult.appendText("ERRORE: per anni inferiori a 1816 non ci sono dati\n");
			return;
		}
		
		List<CountryAndNum> stati = model.creaGrafo(anno);
		
		for(CountryAndNum cn : stati) {
			txtResult.appendText(String.format("%s: %d\n", cn.getCountry(), cn.getNum()));
		}
		
		// popolo la tendina con le Country appena stampate
		boxNazione.getItems().clear();		
		for(CountryAndNum cn : stati) {
			boxNazione.getItems().add(cn.getCountry());
		}
		Collections.sort(boxNazione.getItems());
		
    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	Country partenza = boxNazione.getValue();
    	
    	if(partenza==null) {
    		txtResult.appendText("ERRORE: devi selezionare una nazione\n");
    		return ;
    	}
    	
    	int passi = model.simula(partenza) ;
    	List<CountryAndNum> stanziali = model.getStanziali() ;
		
    	// stampa i risultati
    	txtResult.appendText("SIMULATI "+passi+" PASSI\n");
    	for(CountryAndNum cn : stanziali) {
    		txtResult.appendText(String.format("%s: %d\n", cn.getCountry(), cn.getNum()));
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
        assert boxNazione != null : "fx:id=\"boxNazione\" was not injected: check your FXML file 'Borders.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";

    }
    
    public void setModel(Model model) {
		this.model = model;	
	}
}
