package com.woocash;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Paint Controller class
 *
 * @author Łukasz
 */
public class PaintController implements Initializable {

    @FXML
    private Canvas finalCanvas, workingCanvas;
    
    @FXML
    private ToggleButton rectangleButton, lineButton, curveButton, ovalButton, pencilButton;
    
    @FXML 
    private RadioButton fillRb;
    
    @FXML 
    private ColorPicker colorPicker;
    
    @FXML 
    private Slider sizeSlider;
    
    private GraphicsContext finalPicture, workingPicture;
    
    private String option = "rectangle";
    
    double startX, startY, lastX, lastY, oldCurveX, oldCurveY;
    
    boolean curveHelper = false, lineHelper = false;
    
    final ToggleGroup toggleGroupForShapes = new ToggleGroup();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        finalPicture = finalCanvas.getGraphicsContext2D();
        workingPicture = workingCanvas.getGraphicsContext2D();

        sizeSlider.setMin(1);
        sizeSlider.setMax(15);
        
        colorPicker.setValue(Color.BLACK);
        
        rectangleButton.setToggleGroup(toggleGroupForShapes);
        lineButton.setToggleGroup(toggleGroupForShapes);
        curveButton .setToggleGroup(toggleGroupForShapes);
        ovalButton.setToggleGroup(toggleGroupForShapes);
        pencilButton.setToggleGroup(toggleGroupForShapes);
    }    

    @FXML
    private void onMousePressedListener(MouseEvent e) {
        
        this.startX = e.getX();
        this.startY = e.getY();
        
        if(curveHelper) {
            this.oldCurveX = startX;
            this.oldCurveY = startY;
            this.curveHelper = false;
        }
    }

    @FXML
    private void onMouseDraggedListener(MouseEvent e) {
        
        this.lastX = e.getX();
        this.lastY = e.getY();

        switch(option) {
            case "rectangle":
                drawRectangleEffect();
                break;
            case "oval":
                drawOvalEffect();
                break;
            case "line":
                drawLineEffect();
                lineHelper = true;
                break;
            case "pencil":
                pencilEffect();
                break;
        }
    }

    @FXML
    private void onMouseReleaseListener(MouseEvent e) {
        
        switch(option) {
            case "rectangle":
                drawRectangle();
                break;
            case "oval":
                drawOval();
                break;
            case "line":
                if(lineHelper) {
                    drawLine();
                    lineHelper = false;
                }
                break;
            case "curve":
                drawCurve();
                break;    
        }
    }
 
    private void drawRectangle() {
        
        double width = lastX - startX;
        double height = lastY - startY;
        
        finalPicture.setLineWidth(sizeSlider.getValue());

        if(fillRb.isSelected()) {
            finalPicture.setFill(colorPicker.getValue());       // setFill - ustawiamy kolor wypełnienia
            finalPicture.fillRect(startX, startY, width, height);
        } else {
            finalPicture.setStroke(colorPicker.getValue());     // setStroke - ustawiamy kolor kontury
            finalPicture.strokeRect(startX, startY, width, height);
        }
    }
    
    private void drawOval() {
        
        double width = lastX - startX;
        double height = lastY - startY;
        
        finalPicture.setLineWidth(sizeSlider.getValue());

        if(fillRb.isSelected()) {
            finalPicture.setFill(colorPicker.getValue());       
            finalPicture.fillOval(startX, startY, width, height);
        } else {
            finalPicture.setStroke(colorPicker.getValue());     
            finalPicture.strokeOval(startX, startY, width, height);
        }
    }

    private void drawLine() {
        
        finalPicture.setLineWidth(sizeSlider.getValue());
        finalPicture.setStroke(colorPicker.getValue());
        finalPicture.strokeLine(startX, startY, lastX, lastY);
    }
 
    private void pencilEffect() {
        
        finalPicture.setLineWidth(sizeSlider.getValue());
        finalPicture.setStroke(colorPicker.getValue());
        finalPicture.strokeLine(startX, startY, lastX, lastY);
        this.startX = lastX;
        this.startY = lastY;
    }
    
    private void drawCurve() {
        
        finalPicture.setLineWidth(sizeSlider.getValue());
        finalPicture.setStroke(colorPicker.getValue());
        finalPicture.strokeLine(oldCurveX, oldCurveY, startX, startY);
        this.oldCurveX = startX;
        this.oldCurveY = startY;
    }

    // working area (working canvas)

    private void drawOvalEffect() {
        
        double width = lastX - startX;
        double height = lastY - startY;
        
        workingPicture.setLineWidth(sizeSlider.getValue());

        if(fillRb.isSelected()) {
            workingPicture.clearRect(0, 0, workingCanvas.getWidth(), workingCanvas.getHeight());
            workingPicture.setFill(colorPicker.getValue());
            workingPicture.fillOval(startX, startY, width, height);
        } else {
            workingPicture.clearRect(0, 0, workingCanvas.getWidth(), workingCanvas.getHeight());
            workingPicture.setStroke(colorPicker.getValue());
            workingPicture.strokeOval(startX, startY, width, height);
        }
    }

    private void drawRectangleEffect() {
        
        double width = lastX - startX;
        double height = lastY - startY;
        
        workingPicture.setLineWidth(sizeSlider.getValue());

        if(fillRb.isSelected()) {
            workingPicture.clearRect(0, 0, workingCanvas.getWidth(), workingCanvas.getHeight());
            workingPicture.setFill(colorPicker.getValue());
            workingPicture.fillRect(startX, startY, width, height);
        } else {
            workingPicture.clearRect(0, 0, workingCanvas.getWidth(), workingCanvas.getHeight());
            workingPicture.setStroke(colorPicker.getValue());
            workingPicture.strokeRect(startX, startY, width, height );
        }
    }

    private void drawLineEffect() {
        
        workingPicture.setLineWidth(sizeSlider.getValue());
        workingPicture.setStroke(colorPicker.getValue());
        workingPicture.clearRect(0, 0, workingCanvas.getWidth() , workingCanvas.getHeight());
        workingPicture.strokeLine(startX, startY, lastX, lastY);
    }

    // setting current shape
    
    @FXML
    private void setOvalAsCurrentShape(ActionEvent e) {
        this.option = "oval";
    }

    @FXML
    private void setLineAsCurrentShape(ActionEvent e) {
        this.option = "line";
    }
    
    @FXML
    private void setRectangleAsCurrentShape(ActionEvent e) {
        this.option = "rectangle";
    }
    
    @FXML
    private void setCurveAsCurrentShape(ActionEvent e) {
        this.option = "curve";
        // if we want draw first or new curve this variable helps us to set
        // beginning (X, Y) of curve
        this.curveHelper = true;
    }
    
    @FXML
    private void setPencilAsCurrentShape(ActionEvent e) {
        this.option = "pencil";
    }

    @FXML 
    private void clearCanvas(ActionEvent e) {
        finalPicture.clearRect(0, 0, finalCanvas.getWidth(), finalCanvas.getHeight());
        workingPicture.clearRect(0, 0, finalCanvas.getWidth(), finalCanvas.getHeight());
        this.curveHelper = true;
    }
}
