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

/**
 * Paint Controller class
 *
 * @author Łukasz
 */
public class PaintController implements Initializable {
    //>>>>>>>>>>>>>>>>>>>>>>>Other variables<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private GraphicsContext finalPicture, workingPicture;
    private String option = "line";
    double startX, startY, lastX, lastY, oldX, oldY;
    final ToggleGroup toggleGroupForShapes = new ToggleGroup();
    //>>>>>>>>>>>>>>>>>>>>>>>FXML Variables<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @FXML 
    private RadioButton strokeRB,fillRB;
    
    @FXML 
    private ColorPicker colorPicker;
    
    @FXML
    private Canvas finalCanvas, workingCanvas;
    
    @FXML
    private ToggleButton rectangleButton, lineButton, curveButton, ovalButton, pencilButton;
    
    @FXML 
    private Slider sizeSlider;
    //////////////////////////////////////////////////////////////////////////////


    @FXML
    private void onMousePressedListener(MouseEvent e){
        this.startX = e.getX();
        this.startY = e.getY();
        this.oldX = e.getX();
        this.oldY = e.getY();
        
        switch(option) {
            case "curve":
                drawCurve();
                break;
        }
    }

    @FXML
    private void onMouseDraggedListener(MouseEvent e){
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
                break;
            case "pencil":
                pencilEffect();
                break;
        }
    }

    @FXML
    private void onMouseReleaseListener(MouseEvent e){
        
        switch(option) {
            case "rectangle":
                drawRectangle();
                break;
            case "oval":
                drawOval();
                break;
            case "line":
                drawLine();
                break;
        }
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>Draw methods<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private void drawOval()
    {
        double wh = lastX - startX;
        double hg = lastY - startY;
        finalPicture.setLineWidth(sizeSlider.getValue());

        if(fillRB.isSelected()){
            finalPicture.setFill(colorPicker.getValue());
            finalPicture.fillOval(startX, startY, wh, hg);
        }else{
            finalPicture.setStroke(colorPicker.getValue());
            finalPicture.strokeOval(startX, startY, wh, hg);
        }
    }

    private void drawRectangle()
    {
        double wh = lastX - startX;
        double hg = lastY - startY;
        finalPicture.setLineWidth(sizeSlider.getValue());

        if(fillRB.isSelected()){
            finalPicture.setFill(colorPicker.getValue());
            finalPicture.fillRect(startX, startY, wh, hg);
        }else{
            finalPicture.setStroke(colorPicker.getValue());
            finalPicture.strokeRect(startX, startY, wh, hg);
        }
    }

    private void drawLine()
    {
        finalPicture.setLineWidth(sizeSlider.getValue());
        finalPicture.setStroke(colorPicker.getValue());
        finalPicture.strokeLine(startX, startY, lastX, lastY);
    }

    private void pencilEffect()
    {
        finalPicture.setLineWidth(sizeSlider.getValue());
        finalPicture.setStroke(colorPicker.getValue());
        finalPicture.strokeLine(oldX, oldY, lastX, lastY);
        oldX = lastX;
        oldY = lastY;
    }
    
    private void drawCurve() {
        
        finalPicture.setLineWidth(sizeSlider.getValue());
        finalPicture.setStroke(colorPicker.getValue());
        finalPicture.strokeLine(startX, startY, oldX, oldX);
    }

    //////////////////////////////////////////////////////////////////////
    //>>>>>>>>>>>>>>>>>>>>>>>>>>Draw effects methods<<<<<<<<<<<<<<<<<<<<<<<

    private void drawOvalEffect()
    {
        double wh = lastX - startX;
        double hg = lastY - startY;
        workingPicture.setLineWidth(sizeSlider.getValue());

        if(fillRB.isSelected()){
            workingPicture.clearRect(0, 0, workingCanvas.getWidth(), workingCanvas.getHeight());
            workingPicture.setFill(colorPicker.getValue());
            workingPicture.fillOval(startX, startY, wh, hg);
        }else{
            workingPicture.clearRect(0, 0, workingCanvas.getWidth(), workingCanvas.getHeight());
            workingPicture.setStroke(colorPicker.getValue());
            workingPicture.strokeOval(startX, startY, wh, hg );
        }
       }

    private void drawRectangleEffect()
    {
        double wh = lastX - startX;
        double hg = lastY - startY;
        workingPicture.setLineWidth(sizeSlider.getValue());

        if(fillRB.isSelected()){
            workingPicture.clearRect(0, 0, workingCanvas.getWidth(), workingCanvas.getHeight());
            workingPicture.setFill(colorPicker.getValue());
            workingPicture.fillRect(startX, startY, wh, hg);
        }else{
            workingPicture.clearRect(0, 0, workingCanvas.getWidth(), workingCanvas.getHeight());
            workingPicture.setStroke(colorPicker.getValue());
            workingPicture.strokeRect(startX, startY, wh, hg );
        }
    }

    private void drawLineEffect()
    {
        workingPicture.setLineWidth(sizeSlider.getValue());
        workingPicture.setStroke(colorPicker.getValue());
        workingPicture.clearRect(0, 0, workingCanvas.getWidth() , workingCanvas.getHeight());
        workingPicture.strokeLine(startX, startY, lastX, lastY);
    }
    ///////////////////////////////////////////////////////////////////////

    @FXML 
    private void clearCanvas(ActionEvent e)
    {
        finalPicture.clearRect(0, 0, finalCanvas.getWidth(), finalCanvas.getHeight());
        workingPicture.clearRect(0, 0, finalCanvas.getWidth(), finalCanvas.getHeight());
    }


    //>>>>>>>>>>>>>>>>>>>>>Buttons control<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
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
    }
    
    @FXML
    private void setPencilAsCurrentShape(ActionEvent e) {
        this.option = "pencil";
    }

    //////////////////////////////////////////////////////////////////


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        finalPicture = finalCanvas.getGraphicsContext2D();
        workingPicture = workingCanvas.getGraphicsContext2D();

        sizeSlider.setMin(1);
        sizeSlider.setMax(50);
        
        rectangleButton.setToggleGroup(toggleGroupForShapes);
        lineButton.setToggleGroup(toggleGroupForShapes);
        curveButton .setToggleGroup(toggleGroupForShapes);
        ovalButton.setToggleGroup(toggleGroupForShapes);
        pencilButton.setToggleGroup(toggleGroupForShapes);
    }    

}
