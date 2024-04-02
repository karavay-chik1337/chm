
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class SimpleGUI extends JFrame {

    private static final int RENDER_QUALITY = 200;

    private static final JButton button = new JButton("Построить");
    private static final JTextField inputA = new JTextField("1", 10);
    private static final JTextField inputB = new JTextField("10", 10);
    private static final JTextField inputN = new JTextField("5", 10);
    //private static final JTextField inputX0 = new JTextField("0.3125", 10);
    //private static final JTextField inputXN = new JTextField("-4", 10);
    private static final JRadioButton inputSeparation1 = new JRadioButton("Равномерное");
    private static final JRadioButton inputSeparation2 = new JRadioButton("Чебышев");
    //private static final JRadioButton inputMethod1 = new JRadioButton("Кубический сплайн");
    private static final JRadioButton inputMethod2 = new JRadioButton("Полином Лагранжа");
    private static final ButtonGroup buttonGroup1 = new ButtonGroup();
    private static final ButtonGroup buttonGroup2 = new ButtonGroup();

    private static final JLabel labelA = new JLabel("Левый конец отрезка(a): ");
    private static final JLabel labelB = new JLabel("Правый конец отрезка(b): ");
    private static final JLabel labelN = new JLabel("Количество разбиений(n): ");
    private static final JLabel labelSeparation = new JLabel("Вид разбиения: ");
    private static final JLabel labelMethod = new JLabel("Метод интерполирования: ");

    //private static final JLabel labelX0 = new JLabel("s1(x0) (для сплайнов): ");
    //private static final JLabel labelXN = new JLabel("s2(xN) (для сплайнов): ");

    private static final JLabel text = new JLabel("");

    private String graphType;

    public SimpleGUI(Function<Double, Double> func,  Function<Double,Double> langrenge) {

        super("CHM4");
        this.setBounds(100, 100, 400, 250);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buttonGroup1.add(inputSeparation1);
        buttonGroup1.add(inputSeparation2);

        //buttonGroup2.add(inputMethod1);
        buttonGroup2.add(inputMethod2);

        inputSeparation1.doClick();
        //inputMethod1.doClick();

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(9, 1, 2, 2));
        container.add(labelA);
        container.add(inputA);
        container.add(labelB);
        container.add(inputB);
        container.add(labelN);
        container.add(inputN);
        //container.add(labelX0);
        //container.add(inputX0);
        //container.add(labelXN);
        //container.add(inputXN);
        container.add(labelSeparation);
        container.add(text);
        container.add(inputSeparation1);
        container.add(inputSeparation2);
        //container.add(text);
        container.add(labelMethod);
        //container.add(inputMethod1);
        container.add(inputMethod2);

        button.addActionListener(e -> {
                    this.graphType = inputMethod2.getText();
                    int partition = inputSeparation1.isSelected() ? 0 : 1;
                    JFreeChart[] graphs = createGraphs(
                            Integer.parseInt(inputN.getText()),
                            Double.parseDouble(inputA.getText()),
                            Double.parseDouble(inputB.getText()),
                            partition,
                            func,
                            langrenge);
                    displayGraphs(graphs);
                }
        );
        container.add(button);

//        button.addActionListener(e -> {
//                    Section section = new Section(
//                            Double.parseDouble(inputA.getText()),
//                            Double.parseDouble(inputB.getText()),
//                            Integer.parseInt(inputN.getText()),
//                            inputSeparation1.isSelected() ? SeparationType.UNIFORM : SeparationType.CHEBYSHEV
//                    );
//                    int renderQuality = RENDER_QUALITY % section.getN() == 0 ? RENDER_QUALITY + 1 : RENDER_QUALITY;
//                    Section renderSection = new Section(
//                            Double.parseDouble(inputA.getText()),
//                            Double.parseDouble(inputB.getText()),
//                            renderQuality,
//                            SeparationType.UNIFORM
//                    );
//                    InterpolatedFunction interpolatedFunction = inputMethod1.isSelected() ?
//                            new CubicSpline(
//                                    function,
//                                    section,
//                                    Double.parseDouble(inputX0.getText()),
//                                    Double.parseDouble(inputXN.getText())
//                            ) :
//                            new Polynomial(function, section);
//                    this.graphType = inputMethod1.isSelected() ? inputMethod1.getText() : inputMethod2.getText();
//
//                    JFreeChart[] graphs = createGraphs(renderSection, function, interpolatedFunction);
//                    displayGraphs(graphs);
//                }
//        );
//        container.add(button);
    }

    private void displayGraphs(JFreeChart[] graphs) {
        JFrame graphFrame = new JFrame("Polimon Langrange");
        graphFrame.setSize(1600, 1200);
        graphFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane();
        graphFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel graphPanel = new JPanel(new GridLayout(1, 3));
        scrollPane.setViewportView(graphPanel);

        for (JFreeChart graph : graphs) {
            ChartPanel chartPanel = new ChartPanel(graph, true);
            graphPanel.add(chartPanel);
        }
        graphFrame.setVisible(true);
    }

    private JFreeChart[] createGraphs(int n, double a, double b, int partition, Function<Double, Double> func,  Function<Double,Double> langrenge) {

        XYSeries sourceFunction = new XYSeries("f(x)");
        XYSeries interpolatedFunction = new XYSeries("interpolated_func(x)");
        XYSeries error = new XYSeries("accuracy(x)");

        double[] xValues = Lagrange.separition(Lagrange.partitioning(a, b, n, partition));
        var maxFYAxis = Double.MIN_VALUE;
        var minFYAxis = Double.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            var yValue = func.apply(xValues[i]);
            if (yValue > maxFYAxis) {
                maxFYAxis = yValue;
            }
            if (yValue < minFYAxis) {
                minFYAxis = yValue;
            }
            var interpFuncValue = langrenge.apply(xValues[i]);
            var errXValue = Math.abs(yValue - interpFuncValue);

            sourceFunction.add(xValues[i], yValue);
            interpolatedFunction.add(xValues[i], interpFuncValue);
            error.add(xValues[i], errXValue);
        }

        NumberAxis xAxis = new NumberAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");

        xAxis.setAutoRange(false);
        yAxis.setAutoRange(false);

        xAxis.setRange(xValues[0] - 1, xValues[n-1] + 1);
        yAxis.setRange(minFYAxis - 1, maxFYAxis + 1);

        // Создаем графики
        JFreeChart chartF = ChartFactory.createXYLineChart("f(x)", "X", "Y",
                new XYSeriesCollection(sourceFunction), PlotOrientation.VERTICAL, true, true, false);

        JFreeChart chartInterpFunc = ChartFactory.createXYLineChart("interpolated_func(x)", "X", "Y",
                new XYSeriesCollection(interpolatedFunction), PlotOrientation.VERTICAL, true, true, false);

        JFreeChart chartErr = ChartFactory.createXYLineChart("accuracy(x)", "X", "Y",
                new XYSeriesCollection(error), PlotOrientation.VERTICAL, true, true, false);

        XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
        renderer1.setSeriesLinesVisible(0, true);
        renderer1.setSeriesShapesVisible(0, true);
        renderer1.setSeriesShape(0, new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
        renderer1.setSeriesPaint(0, Color.RED);

        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
        renderer2.setSeriesLinesVisible(0, true);
        renderer2.setSeriesShapesVisible(0, true);
        renderer2.setSeriesShape(0, new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
        renderer2.setSeriesPaint(0, Color.GREEN);

        XYLineAndShapeRenderer renderer3 = new XYLineAndShapeRenderer();
        renderer3.setSeriesLinesVisible(0, true);
        renderer3.setSeriesShapesVisible(0, true);
        renderer3.setSeriesShape(0, new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
        renderer3.setSeriesPaint(0, Color.BLUE);

        chartF.getXYPlot().setDomainAxis(xAxis);
        chartF.getXYPlot().setRangeAxis(yAxis);
        chartF.getXYPlot().setRenderer(renderer1);

        chartInterpFunc.getXYPlot().setDomainAxis(xAxis);
        chartInterpFunc.getXYPlot().setRangeAxis(yAxis);
        chartInterpFunc.getXYPlot().setRenderer(renderer2);

        NumberAxis eXAxis = new NumberAxis("X");
        NumberAxis eYAxis = new NumberAxis("Y");
        eXAxis.setAutoRange(false);
        eXAxis.setRange(xValues[0] - 1, xValues[n-1] + 1);
        eYAxis.setAutoRange(true);

        chartErr.getXYPlot().setDomainAxis(xAxis);
        chartErr.getXYPlot().setRangeAxis(eYAxis);
        chartErr.getXYPlot().setRenderer(renderer3);

        return new JFreeChart[] {
                chartF,
                chartInterpFunc,
                chartErr
        };
    }
}
