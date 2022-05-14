package com.company;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SceneBuilder extends Application {
    public static void main(String[] args) {
        launch();
    }
    public static List<List<String>> allCombinationsOfMessage(int slots, int messagesNumber){
        List<List<String>> allCombinations = new ArrayList<>();
        List<Integer> messagesIndexes = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        for (int i = 0; i < slots; i++) {
            if (i<messagesNumber){
                messagesIndexes.add(i);
                messages.add("1");
            }else {
                messages.add("0");
            }
        }
        allCombinations.add(new ArrayList<>(messages));
        while (true){
            for (int i = 1; i < messagesIndexes.size(); i++) {
                if (messagesIndexes.get(0)==slots-messagesNumber){
                    break;
                }
                if (slots-messagesNumber+i==messagesIndexes.get(i)){
                    messages.set(messagesIndexes.get(i-1), "0");
                    messagesIndexes.set(i-1, messagesIndexes.get(i-1)+1);
                    messages.set(messagesIndexes.get(i-1), "1");
                    for (int j = i; j < messagesIndexes.size(); j++) {
                        messages.set(messagesIndexes.get(j), "0");
                        messagesIndexes.set(j, messagesIndexes.get(j-1)+1);
                        messages.set(messagesIndexes.get(j), "1");
                    }
                    allCombinations.add(new ArrayList<>(messages));
                    i=0;
                }

            }
            if (messagesIndexes.get(0)==slots-messagesNumber){
                break;
            }
            messages.set(messagesIndexes.get(messagesIndexes.size()-1), "0");
            messagesIndexes.set(messagesIndexes.size()-1, messagesIndexes.get(messagesIndexes.size()-1)+1);
            messages.set(messagesIndexes.get(messagesIndexes.size()-1), "1");
            allCombinations.add(new ArrayList<>(messages));

        }
        return allCombinations;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        HBox machine1details = new HBox();
        HBox machine2details = new HBox();
        Label label1 = new Label("Size of package from machine 1: ");
        Label label2 = new Label("Size of package from machine 2: ");
        TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        Label label3 = new Label("Number of packages: ");
        Label label4 = new Label("Number of packages: ");
        TextField textField3 = new TextField();
        TextField textField4 = new TextField();
        Label label5 = new Label("Timeline: ");
        TextField textField5 = new TextField();
        Button calculateButton = new Button("Calculate");
        Button clearAllButton = new Button("Clear All");
        ScrollPane resultsScrollPane = new ScrollPane();
        HBox resultHBox = new HBox();
        resultsScrollPane.setTranslateX(0);
        resultsScrollPane.setTranslateY(60);
        resultsScrollPane.setPrefWidth(1525);
        resultsScrollPane.setPrefHeight(80);
        resultsScrollPane.setContent(resultHBox);
        machine1details.setTranslateX(0);
        machine1details.setTranslateY(0);
        machine2details.setTranslateX(0);
        machine2details.setTranslateY(30);
        machine1details.getChildren().add(label1);
        machine1details.getChildren().add(textField1);
        machine1details.getChildren().add(label3);
        machine1details.getChildren().add(textField3);
        machine1details.getChildren().add(label5);
        machine1details.getChildren().add(textField5);
        machine2details.getChildren().add(label2);
        machine2details.getChildren().add(textField2);
        machine2details.getChildren().add(label4);
        machine2details.getChildren().add(textField4);
        machine2details.getChildren().add(calculateButton);
        machine2details.getChildren().add(clearAllButton);
        root.getChildren().add(machine1details);
        root.getChildren().add(machine2details);
        root.getChildren().add(resultsScrollPane);

        double[] helper = new double[2];

        int[] helper2 = new int[3];



        textField1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                helper[0] = Double.parseDouble(textField1.getText());
            }
        });

        textField2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                helper[1] = Double.parseDouble(textField2.getText());
            }
        });

        textField3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                helper2[0] = Integer.parseInt(textField3.getText());
            }
        });

        textField4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                helper2[1] = Integer.parseInt(textField4.getText());
            }
        });
        int[] helper3 = {0};
        textField5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                helper2[2] = Integer.parseInt(textField5.getText());
            }
        });

        clearAllButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.getChildren().remove(3);
                root.getChildren().remove(3);
                helper3[0]=0;
            }
        });

        calculateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int sizeOfHbox = resultHBox.getChildren().size();
                for (int i = 0; i < sizeOfHbox; i++) {
                    resultHBox.getChildren().remove(0);
                }

                Map<Integer, Integer> collisionTimes = new LinkedHashMap<>();
                double packageSize1 = helper[0]*100;
                double packageSize2 = helper[1]*100;

                int numberOfPackages1 = helper2[0];
                int numberOfPackages2 = helper2[1];
                int timeline = helper2[2]*10;
                int slots = 0;
                int biggerPackage = 0;
                if (packageSize1>packageSize2){
                    biggerPackage = (int)packageSize1;
                    slots = (int)(timeline/packageSize1);
                }else{
                    biggerPackage = (int)packageSize2;
                    slots = (int)(timeline/packageSize2);
                }
                List<List<String>> combinationsMachine1 = allCombinationsOfMessage(slots, numberOfPackages1);
                List<List<String>> combinationsMachine2 = allCombinationsOfMessage(slots, numberOfPackages2);
                int numberOfCombinations = combinationsMachine2.size();
                /*for (int i = 0; i < combinationsMachine1.size()-1; i++) {
                    combinationsMachine2.addAll(allCombinationsOfMessage(slots, numberOfPackages2));
                }*/
                for (int i = 0; i < combinationsMachine1.size(); i++) {
                    for (int j = 0; j < numberOfCombinations; j++) {
                        List<String> resultM1 = new ArrayList<>(combinationsMachine1.get(i));
                        List<String> resultM2 = new ArrayList<>(combinationsMachine2.get(j));
                        collisionTimes = new LinkedHashMap<>(times(collisionTimes, resultM1, resultM2));
                        String toShow1 = "";
                        String toShow2 = "";
                        for (int k = 0; k < resultM1.size(); k++) {
                            toShow1+=resultM1.get(k);
                        }
                        for (int k = 0; k < resultM2.size(); k++) {
                            toShow2+=resultM2.get(k);
                        }
                        Label label6 = new Label(toShow1);
                        Label label7 = new Label(toShow2);
                        Button button = new Button("Show result");
                        int finalBiggerPackage = biggerPackage;
                        Group root1 = new Group();
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                if (helper3[0] >0){
                                    root.getChildren().remove(root.getChildren().size()-1);
                                }
                                helper3[0]++;
                                //Group root1 = new Group();
                                List<String> m1 = new ArrayList<>(resultM1);
                                List<String> m2 = new ArrayList<>(resultM2);
                                int slots = m1.size();
                                Rectangle rectangleM1 = new Rectangle();
                                rectangleM1.setHeight(50);
                                rectangleM1.setWidth(50);
                                rectangleM1.setTranslateX(20);
                                rectangleM1.setTranslateY(160);
                                rectangleM1.setFill(Color.WHITE);
                                rectangleM1.setStroke(Color.BLACK);
                                Rectangle rectangleM2 = new Rectangle();
                                rectangleM2.setHeight(50);
                                rectangleM2.setWidth(50);
                                rectangleM2.setTranslateX(20);
                                rectangleM2.setTranslateY(230);
                                rectangleM2.setFill(Color.WHITE);
                                rectangleM2.setStroke(Color.BLACK);
                                Line line1 = new Line();
                                line1.setStartX(100);
                                line1.setStartY(210);
                                line1.setEndY(210);
                                line1.setEndX(100+timeline);
                                Line line2 = new Line();
                                line2.setStartX(100);
                                line2.setStartY(280);
                                line2.setEndY(280);
                                line2.setEndX(100+timeline);
                                int distance = 0;
                                for (int k = 0; k < slots; k++) {
                                    Line line3 = new Line();
                                    line3.setStartX(100+distance);
                                    line3.setStartY(160);
                                    line3.setEndY(280);
                                    line3.setEndX(100+distance);
                                    line3.getStrokeDashArray().addAll(2d, 21d);
                                    distance = distance + finalBiggerPackage;
                                    root1.getChildren().add(line3);
                                }
                                for (int k = 0; k < slots; k++) {
                                    if (m1.get(k).equals("1")){
                                        Rectangle rectangle = new Rectangle();
                                        rectangle.setHeight(20);
                                        rectangle.setWidth(packageSize1);
                                        rectangle.setTranslateX(100+k*finalBiggerPackage);
                                        rectangle.setTranslateY(190);
                                        rectangle.setFill(Color.WHITE);
                                        rectangle.setStroke(Color.BLACK);
                                        root1.getChildren().add(rectangle);
                                    }
                                    if (m2.get(k).equals("1")){
                                        Rectangle rectangle = new Rectangle();
                                        rectangle.setHeight(20);
                                        rectangle.setWidth(packageSize2);
                                        rectangle.setTranslateX(100+k*finalBiggerPackage);
                                        rectangle.setTranslateY(260);
                                        rectangle.setFill(Color.WHITE);
                                        rectangle.setStroke(Color.BLACK);
                                        root1.getChildren().add(rectangle);

                                    }
                                }
                                root1.getChildren().add(line1);
                                root1.getChildren().add(line2);
                                root1.getChildren().add(rectangleM1);
                                root1.getChildren().add(rectangleM2);
                                root.getChildren().add(root1);


                            }
                        });
                        VBox vBox = new VBox();
                        vBox.setPrefWidth(100);
                        vBox.setPrefHeight(80);
                        vBox.getChildren().add(label6);
                        vBox.getChildren().add(label7);
                        vBox.getChildren().add(button);
                        resultHBox.getChildren().add(vBox);
                    }
                }
                VBox vBox = new VBox();
                vBox.setTranslateX(0);
                vBox.setTranslateY(400);
                Label label = new Label("Collisions:");
                vBox.getChildren().add(label);
                for (Map.Entry<Integer, Integer> entry: collisionTimes.entrySet()){
                    String number = String.valueOf(entry.getKey());
                    String times = String.valueOf(entry.getValue());
                    Label label1 = new Label(number+": "+times);
                    vBox.getChildren().add(label1);
                }
                root.getChildren().add(vBox);
            }
        });
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("ALOHA");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static Map<Integer, Integer> times(Map<Integer, Integer> collisionTimes, List<String> resultM1, List<String> resultM2){
        int collisions = 0;

        for (int i = 0; i < resultM1.size(); i++) {
            if (resultM1.get(i).equals("1") && resultM2.get(i).equals("1")){
                collisions++;
            }
        }
        if (!collisionTimes.containsKey(collisions)){
            collisionTimes.put(collisions, 1);
        }else {
            collisionTimes.put(collisions, collisionTimes.get(collisions)+1);
        }
        return collisionTimes;
    }
}
