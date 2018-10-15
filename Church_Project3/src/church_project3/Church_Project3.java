/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package church_project3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import static javafx.geometry.Pos.CENTER;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
// card

/**
 *
 * @author trent_000
 */
public class Church_Project3 extends Application {

    ObservableList<Card> listContent = FXCollections.observableArrayList();
    boolean flag = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);

    }

    public boolean aiWon(ArrayList<Card> list) {
        for (int i = 1; i < 4; i++) {
            if (!(list.get(0).getRank().equals(list.get(i).getRank()))) {
                return false;
            }
        }
        return true;
    }

    public boolean humanWon(ObservableList<Card> list) {
        for (int i = 1; i < 4; i++) {
            if (!(list.get(0).getRank().equals(list.get(i).getRank()))) {
                return false;
            }
        }
        return true;
    }

    public void aimove(ArrayList<Card> list, Queue<Card> drawPile, ArrayDeque<Card> discardPile) {
        int keep = 0;
        boolean same = false;
        search:
        {
            for (int i = 0; i < 4; i++) {
                for (int j = 1; j < 4; j++) {
                    if (list.get(i).getRank().equals(list.get(j).getRank())) {
                        if (i != j) {
                            keep = i;
                            same = true;
                            break search;
                        }
                    }
                }
            }
        }
        if (same) {
            if (!(discardPile.isEmpty())) {

                if (list.get(keep).getRank().equals(discardPile.peek().getRank())) {
                    list.add(discardPile.pop());
                } else {
                    list.add(drawPile.poll());
                }

            } else {
                list.add(drawPile.poll());
            }

        } else {
            for (int i = 0; i < 4; i++) {
                if (list.get(i).getRank().equals(discardPile.peek().getRank())) {
                    list.add(discardPile.pop());
                    keep = i;
                    same = true;
                    break;
                }
            }
            if (!same) {
                list.add(drawPile.poll());
            }
        }
        for (int i = 0; i < 5; i++) {
            if (!(list.get(keep).getRank().equals(list.get(i).getRank()))) {
                discardPile.push(list.get(i));
                list.remove(i);
                break;
            }
        }
    }

    public Image setPic(ArrayDeque<Card> discardPile) {
        File f;
        if (discardPile.isEmpty()) {
            f = new File("empty.png");
        } else {
            f = new File(discardPile.peek().display());
        }
        InputStream is;
        Image picCard = null;
        try {
            is = new FileInputStream(f);
            picCard = new Image(is);

        } catch (FileNotFoundException ex) {

        }
        return picCard;
    }

    @Override
    public void start(Stage stage) throws Exception {
        HBox base = new HBox(10);
        HBox selection = new HBox(10);
        VBox menu = new VBox(10);
        VBox hand = new VBox();
        menu.setPadding(new Insets(5));

        Label handName = new Label("Your hand:");
        Label comment = new Label();
        Label instruct = new Label();

        instruct.setStyle("-fx-border-style: solid inside;");
        instruct.setStyle("-fx-border-color: gray;");
        instruct.setPadding(new Insets(5));
        selection.setStyle("-fx-border-style: solid inside;");
        selection.setPadding(new Insets(10));

        ListView<Card> listView = new ListView(listContent);
        Button draw = new Button("Draw");
        Button pickup = new Button("Pickup");
        Button discard = new Button("Discard");
        Label pic = new Label();
        Label discardName = new Label("Discard Pile:");
        hand.getChildren().addAll(handName, listView);
        selection.getChildren().addAll(draw, pickup);
        menu.getChildren().addAll(instruct, selection, discard, discardName, pic, comment);
        base.getChildren().addAll(hand, menu);
        String[] suite = {"Spades", "Diamonds", "Hearts", "Clubs"};
        String[] rank = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        ArrayList<Card> deck = new ArrayList<>();
        ArrayList<Card> ai = new ArrayList<>();
        ArrayDeque<Card> discardPile = new ArrayDeque<>();
        Queue<Card> drawPile = new LinkedList();
        for (int i = 0; i < suite.length; i++) {
            for (int j = 0; j < rank.length; j++) {
                deck.add(new Card(suite[i], rank[j]));
            }
        }
        Collections.shuffle(deck);
        drawPile.addAll(deck);
        for (int i = 0; i < 4; i++) {
            listContent.add(drawPile.poll());
            ai.add(drawPile.poll());
        }
        stage.setMinHeight(430);
        stage.setMinWidth(450);
        stage.setMaxHeight(430);
        stage.setMaxWidth(450);
        Scene scene = new Scene(base, 400, 350);
        stage.setScene(scene);
        stage.show();

        File g = new File("empty.png");
        InputStream is1;
        Image picCard1;
        try {
            is1 = new FileInputStream(g);
            picCard1 = new Image(is1);
            pic.setGraphic(new ImageView(picCard1));

        } catch (FileNotFoundException ex) {

        }
        instruct.setText("Draw from the pile or \npickup from the discard");
        if (humanWon(listContent)) {
            Alert exist = new Alert(Alert.AlertType.INFORMATION);
            exist.setTitle("YOU WON");
            exist.setHeaderText("YOU WON");
            exist.showAndWait();
            stage.close();
        }
        if (aiWon(ai)) {
            Alert exist = new Alert(Alert.AlertType.INFORMATION);
            exist.setTitle("I won");
            exist.setHeaderText("I won");
            exist.showAndWait();
            stage.close();
        }
        discard.setDisable(true);
        draw.setOnAction(e -> {
            listContent.add(drawPile.poll());
            listView.refresh();
            draw.setDisable(true);
            pickup.setDisable(true);
            discard.setDisable(false);
            instruct.setText("Discard a card from \nyour hand");

        });
        pickup.setOnAction(e -> {
            listContent.add(discardPile.pop());
            draw.setDisable(true);
            pickup.setDisable(true);
            discard.setDisable(false);
            instruct.setText("Discard a card from \nyour hand");

            if (!discardPile.isEmpty()) {
                pic.setGraphic(new ImageView(setPic(discardPile)));
            }

        });
        discard.setOnAction(e -> {
            discardPile.push(listView.getSelectionModel().getSelectedItem());
            listContent.remove(listView.getSelectionModel().getSelectedItem());
            draw.setDisable(false);
            pickup.setDisable(false);
            discard.setDisable(true);
            if (humanWon(listContent)) {
                Alert exist = new Alert(Alert.AlertType.INFORMATION);
                exist.setTitle("YOU WON");
                exist.setHeaderText("YOU WON");
                exist.showAndWait();
                stage.close();
            } else {
                if (drawPile.isEmpty()) {
                    ArrayList<Card> deck1 = new ArrayList<>();

                    while (!(discardPile.isEmpty())) {

                        deck1.add(discardPile.pop());
                        pic.setGraphic(new ImageView(setPic(discardPile)));
                    }

                    Collections.shuffle(deck1);
                    drawPile.addAll(deck1);

                }
                aimove(ai, drawPile, discardPile);


                if (aiWon(ai)) {
                    Alert exist = new Alert(Alert.AlertType.INFORMATION);
                    exist.setTitle("I won");
                    exist.setHeaderText("I won");
                    exist.showAndWait();
                    stage.close();
                }
                if (drawPile.isEmpty()) {
                    ArrayList<Card> deck1 = new ArrayList<>();

                    while (!(discardPile.isEmpty())) {

                        deck1.add(discardPile.pop());
                        pic.setGraphic(new ImageView(setPic(discardPile)));
                    }

                    Collections.shuffle(deck1);
                    drawPile.addAll(deck1);

                }
                if (!discardPile.isEmpty()) {
                    pic.setGraphic(new ImageView(setPic(discardPile)));
                }
            }
                    instruct.setText("Draw from the pile or \npickup from the discard");

        });

    }

}
