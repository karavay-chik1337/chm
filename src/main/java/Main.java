import static java.lang.Math.abs;

public class Main {
    public static void main(String[] args) {
        //графики
        SimpleGUI simpleGUI = new SimpleGUI();
        simpleGUI.setVisible(true);
        //погрешность для разных n и разбиений
        int n = 5;
        double delta;
        double maxForCheb = 0;
        double maxForRavnom = 0;
        System.out.println("Для полинома Лагража");
        for (int j = 0; j < 13; j++) {
            Section section = new Section(-10, 10, n, 0);
            Function<Double, Double> lagrange = Lagrange.createLagrangePolynomial(section);
            double[] xValues = section.separation(section.xGenerate());
            double[] yValues = new double[xValues.length];
            for (int i = 0; i < xValues.length; i++) {
                yValues[i] = Lagrange.func().apply(xValues[i]);
            }

            for (int i = 0; i < yValues.length; i++) {
                delta = abs(yValues[i] - lagrange.apply(xValues[i]));
                if (delta > maxForCheb) {
                    maxForRavnom = delta;
                }
            }
            section.setPartition(1);
            lagrange = Lagrange.createLagrangePolynomial(section);
            xValues = section.separation(section.xGenerate());
            for (int i = 0; i < xValues.length; i++) {
                yValues[i] = Lagrange.func().apply(xValues[i]);
            }

            for (int i = 0; i < yValues.length; i++) {
                delta = abs(yValues[i] - lagrange.apply(xValues[i]));
                if (delta > maxForCheb)
                    maxForCheb = delta;
            }
            System.out.println("n = " + section.getN() + "\tравномерное = " + maxForRavnom + "\t чебышев = " + maxForCheb);

            n += 5;
            maxForCheb = 0;
            maxForRavnom = 0;
        }

        n = 2;
        System.out.println("\nКубический сплайн");
        for (int j = 0; j < 12; j++) {
            Section section = new Section(1, 10, n, 0);
            Function<Double, Double> spline = Spline.createCubicSpline(section);
            double[] xValues = section.separation(section.xGenerate());
            double[] yValues = new double[xValues.length];
            for (int i = 0; i < xValues.length; i++) {
                yValues[i] = Spline.func().apply(xValues[i]);
            }

            for (int i = 0; i < yValues.length; i++) {
                delta = abs(yValues[i] - spline.apply(xValues[i]));
                if (delta > maxForRavnom) {
                    maxForRavnom = delta;
                }
            }
            section.setPartition(1);
            section.xGenerate();
            spline = Spline.createCubicSpline(section);
            xValues = section.separation(section.xGenerate());
            for (int i = 0; i < xValues.length; i++) {
                yValues[i] = Spline.func().apply(xValues[i]);
            }

            for (int i = 0; i < yValues.length; i++) {
                delta = abs(yValues[i] - spline.apply(xValues[i]));
                if (delta > maxForCheb)
                    maxForCheb = delta;
            }
            System.out.println("n = " + section.getN() + "\t\t\tравномерное = " + maxForRavnom + "\t\t\t чебышев = " + maxForCheb);
            n *= 2;
            maxForCheb = 0;
            maxForRavnom = 0;
        }
    }
}


