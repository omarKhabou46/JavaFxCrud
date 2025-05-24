package ma.enset.demo3;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import ma.enset.demo3.dao.ClientDaoImpl;
import ma.enset.demo3.entity.Client;
import ma.enset.demo3.service.ClientService;
import ma.enset.demo3.service.ClientServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientService clientService = new ClientServiceImpl(new ClientDaoImpl());
        AtomicReference<Client> cl1 = new AtomicReference<>();
        VBox root = new VBox();
        TableView<Client> table = new TableView<>();

        Scene scene = new Scene(root, 400, 600);

        Label error = new Label("");

        Label title = new Label("my First App");
        Label firstName = new Label("firstName : ");
        Label lastName = new Label("last Name : ");
        Label age = new Label("Age : ");
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField ageField = new TextField();
        firstNameField.setPromptText("enter your Last Name");
        lastNameField.setPromptText("enter your First Name");
        ageField.setPromptText("enter your Age ");


        HBox hBox1 = new HBox(16);
        hBox1.getChildren().addAll(firstName, firstNameField);
        HBox hBox2 = new HBox(16);
        hBox2.getChildren().addAll(lastName, lastNameField);
        HBox hBox3 = new HBox(70);
        hBox3.getChildren().addAll(age, ageField);

        HBox.setMargin(lastName, new Insets(10,0,10,0));
        HBox.setMargin(lastNameField, new Insets(10,0,10,0));

        VBox vBox = new VBox();
        Button ajouter = new Button("ajouter");
        ajouter.setPrefWidth(150);
        vBox.getChildren().addAll(hBox1, hBox2,hBox3, ajouter, error);
        root.getChildren().add(vBox);
        VBox.setMargin(vBox, new Insets(10, 0, 0, 0));


        ajouter.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String lastName = lastNameField.getText();
                String firstName = firstNameField.getText();
                String age = ageField.getText();
                if (lastName.isEmpty() || firstName.isEmpty() || age.isEmpty()) {
                    error.setText("un champs est vide");
                    actionEvent.consume();
                }else {
                    try {
                        if (ajouter.getText().equals("ajouter")) {
                            clientService.addClient(new Client(firstName, lastName, Integer.valueOf(age)));
                            firstNameField.setText("");
                            lastNameField.setText("");
                            ageField.setText("");
                            error.setText("le client a bien ajouté");
                        }else {
                            Client cl = cl1.get();
                            cl.setFirstName(firstNameField.getText());
                            cl.setLastName(lastNameField.getText());
                            cl.setAge(Integer.valueOf(ageField.getText()));
                            clientService.updateClient(cl);
                            error.setText("le client a bien modifier");
                            firstNameField.setText("");
                            lastNameField.setText("");
                            ageField.setText("");
                            ajouter.setText("ajouter");

                        }
                        refreachTable(table, clientService);

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


       TableColumn idColumn = new TableColumn<Client, Long>("Id");
       idColumn.setCellValueFactory(new PropertyValueFactory<Client, Long>("id"));

       TableColumn firstNameColumn = new TableColumn<Client, String>("firstName");
       firstNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("firstName"));

        TableColumn lastNameColumn = new TableColumn<Client, String>("lastName");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("lastName"));

       TableColumn ageColumn = new TableColumn<Client, Integer>("age");
       ageColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("age"));

       TableColumn<Client, Void> actionColumn = new TableColumn<>("action");
       actionColumn.setCellValueFactory(new PropertyValueFactory<Client, Void>(""));
       actionColumn.setCellFactory(param -> new TableCell<Client, Void>() {
           private final Button edit = new Button("editer");
           private final Button delete = new Button("supprimer");
           private final HBox hBox = new HBox(12, edit, delete);

           {
               edit.setOnAction(event -> {
                   Client client = getTableView().getItems().get(getIndex());

                   firstNameField.setText(client.getFirstName());
                   lastNameField.setText(client.getLastName());
                   ageField.setText(String.valueOf(client.getAge()));
                   ajouter.setText("modifier");
                   cl1.set(client);

               });

               delete.setOnAction(event -> {
                   Client cl = getTableView().getItems().get(getIndex());
                   try {
                       clientService.delete(cl.getId());
                       refreachTable(table, clientService);
                   }catch (SQLException e) {
                       System.out.println("erreur");
                   }
               });
           }

           @Override
           protected void updateItem(Void item, boolean empty) {
               super.updateItem(item, empty);
               if (empty) {
                   setGraphic(null);
               } else {
                   setGraphic(hBox);
               }
           }
       });


       table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
       table.getColumns().add(idColumn);
       table.getColumns().add(firstNameColumn);
       table.getColumns().add(lastNameColumn);
       table.getColumns().add(ageColumn);
       table.getColumns().add(actionColumn);

        try {
            List<Client> clients = clientService.fetchClients();
            table.getItems().addAll(clients);
        } catch (SQLException e) {
            System.out.println("error");
        }

       root.getChildren().add(table);

       primaryStage.setTitle("Table View");
       scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
       primaryStage.setScene(scene);

       primaryStage.show();
    }

    public void refreachTable(TableView<Client> table, ClientService service) throws SQLException{
        table.getItems().clear();
        table.getItems().addAll(service.fetchClients());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
