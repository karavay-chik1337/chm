import static java.lang.Math.abs;

public class Main {
    public static void main(String[] args) {
        SimpleGUI simpleGUI = new SimpleGUI();
        simpleGUI.setVisible(true);


        int n = 50;
        boolean flag = true;
        double maxForCheb = 0;
        double maxForRavnom = 0;
        System.out.println("Кол-во отрезков\t" + "\tРавномерное разбиение\t" + "\t\t\tРазбиение Чебышева");
        for (int j = 0; j < 3; j++) {
            Section section = new Section(1, 10, n);
            for (int k = 0; k < 2; k++) {
                if (flag) {
                    Function<Double, Double> lagrange = Lagrange.createLagrangePolynomial(section, k);
                    double[] xValues = section.separation(section.partitioning(k));
                    double[] yValues = new double[xValues.length];
                    for (int i = 0; i < xValues.length; i++) {
                        yValues[i] = Lagrange.func().apply(xValues[i]);
                    }

                    for (int i = 0; i < n; i++) {
                        double delta = abs(yValues[i] - lagrange.apply(xValues[i]));
                        //System.out.println("delta = " + delta);
                        if (delta > maxForCheb)
                            maxForRavnom = delta;
                    }
                    flag = false;
                }
                else{
                    Function<Double, Double> lagrange = Lagrange.createLagrangePolynomial(section, k);
                    double[] xValues = section.separation(section.partitioning(k));
                    double[] yValues = new double[xValues.length];
                    for (int i = 0; i < xValues.length; i++) {
                        yValues[i] = Lagrange.func().apply(xValues[i]);
                    }

                    for (int i = 0; i < n; i++) {
                        double delta = abs(yValues[i] - lagrange.apply(xValues[i]));
                        if (delta > maxForCheb)
                            maxForCheb = delta;
                    }
                }
            }
            System.out.println("n = " + n + "\t\t\t\t\t" + maxForRavnom + "\t\t\t" + maxForCheb);

            n += 10;
            maxForCheb = 0;
            maxForRavnom = 0;
        }
    }
}
