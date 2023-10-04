package sio.tp2cabinetmedical;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sio.tp2cabinetmedical.Model.RendezVous;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class TP2Controller implements Initializable {


    @FXML
    private Button btnValider;
    @FXML
    private TextField txtNomµPatiant;
    @FXML
    private ComboBox cboNomPathologie;
    @FXML
    private DatePicker dateRendezVous;
    @FXML
    private Spinner spHeure;
    @FXML
    private Spinner spMinute;
    private TreeMap<String, ArrayList<RendezVous>> mesRendezVous;
    @FXML
    private TreeView tvRendezVous;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cboNomPathologie.getItems().addAll("Angine","Grippe","Covid","Gastro");
        cboNomPathologie.getSelectionModel().selectFirst();

        SpinnerValueFactory spinnerValueFactoryHeure = new SpinnerValueFactory.IntegerSpinnerValueFactory(8,19,8,1);
        spHeure.setValueFactory(spinnerValueFactoryHeure);

        SpinnerValueFactory spinnerValueFactoryMinute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,45,0,15);
        spMinute.setValueFactory(spinnerValueFactoryMinute);

        mesRendezVous = new TreeMap<>();
    }

    @FXML
    public void cboValiderCliked(Event event) {
        if(txtNomµPatiant.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur saisie");
            alert.setHeaderText("");
            alert.setContentText("Veuillez saisir le nom du patient");
            alert.showAndWait();
        }
        else if (dateRendezVous.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur selection");
            alert.setHeaderText("");
            alert.setContentText("Veuillez selectionner une date");
            alert.showAndWait();
        }
        else

        {
            String dateSelectionne = dateRendezVous.getValue().toString();

            String heure = "";
            if(spMinute.getValue().toString().equals("0"))
            {
                heure =spHeure.getValue().toString() +":"+spMinute.getValue().toString()+"0";
            }
            else
            {
                heure =spHeure.getValue().toString() +":"+spMinute.getValue().toString();
            }
            boolean trouve = false;

            if(!mesRendezVous.containsKey(dateSelectionne))
            {
                ArrayList<RendezVous> rdv = new ArrayList<>();
                mesRendezVous.put(dateRendezVous.getValue().toString(),rdv);

            }

            for(RendezVous lesrendezVous:mesRendezVous.get(dateSelectionne))
            {   if(heure.equals(lesrendezVous.getHeureRdv()))
                {
                    trouve = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("heure erreur");
                    alert.setHeaderText("");
                    alert.setContentText("l'horaire n'est pas disponible");
                    alert.showAndWait();

                }

            }
            if(!trouve)
            {
                RendezVous rdv = new RendezVous(heure, txtNomµPatiant.getText(), cboNomPathologie.getValue().toString());
                mesRendezVous.get(dateRendezVous.getValue().toString()).add(rdv);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("prise de rdv");
                alert.setHeaderText("");
                alert.setContentText("rdv ajoute");
                alert.showAndWait();
            }




            TreeItem root = new TreeItem("Mon planning");
            TreeItem feuille = null;
            TreeItem fils = null;
            TreeItem pttFils = null;




            feuille= new TreeItem(dateSelectionne);

            for(RendezVous rendezVous:mesRendezVous.get(dateSelectionne)) {
                fils = new TreeItem(rendezVous.getHeureRdv());
                feuille.getChildren().add(fils);
                feuille.setExpanded(true);
                pttFils = new TreeItem<>(rendezVous.getNomPatient());
                fils.getChildren().add(pttFils);
                fils.setExpanded(true);
                pttFils = new TreeItem<>(rendezVous.getNomPathologie());
                fils.getChildren().add(pttFils);
                fils.setExpanded(true);
            }
                root.getChildren().add(feuille);

                tvRendezVous.setRoot(root);
                tvRendezVous.getRoot().setExpanded(true);





        }
    }
}