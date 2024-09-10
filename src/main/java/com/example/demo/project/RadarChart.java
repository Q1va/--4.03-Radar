package com.example.demo.project;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

public class RadarChart extends JFrame {

    private XYSeries series;

    public RadarChart(String title) {
        super(title);
        series = new XYSeries("Targets");


        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createPolarChart("Беник Іван Радар ІПЗ-4.03 ", dataset, false, true, false);
        PolarPlot plot = (PolarPlot) chart.getPlot();
        plot.setRadiusGridlinesVisible(true); // Enable gridlines for radar appearance

        // Установите фиксированное значение дальности действия радара (радиус = 120).
        NumberAxis axis = (NumberAxis) plot.getAxis();
        axis.setRange(0, 120); // Устанавливаем фиксированный диапазон от 0 до 120 единиц
        axis.setTickUnit((NumberTickUnit) new NumberAxis().getStandardTickUnits().getCeilingTickUnit(20)); // Установите единицу измерения 20.



        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

    // Метод обновления положения цели на статическом радаре
    public void updateChart(double angle, double distance) {
        series.clear(); // Удаления последней точки
        series.add(Math.toRadians(angle), Math.min(distance, 120));
    }

    public static void main(String[] args) {
        RadarChart chart = new RadarChart("Radar Chart Example");
        chart.pack();
        chart.setVisible(true);

        try {
            WebSocketClient client = new WebSocketClient(new URI("ws://localhost:4000")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected to WebSocket");
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Received: " + message);

                    // Обновление диаграммы, указав новую позицию цели.
                    try {
                        JsonReader reader = javax.json.Json.createReader(new java.io.StringReader(message));
                        JsonObject json = reader.readObject();
                        int scanAngle = json.getInt("scanAngle");
                        JsonArray echoResponses = json.getJsonArray("echoResponses");
                        for (int i = 0; i < echoResponses.size(); i++) {
                            JsonObject echo = echoResponses.getJsonObject(i);
                            double time = echo.getJsonNumber("time").doubleValue();
                            double distance = time * 150000; // Расчет расстояния по времени
                            chart.updateChart(scanAngle, distance); // Обновить положение цели
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("WebSocket closed: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };

            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}