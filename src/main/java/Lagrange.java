
import java.util.ArrayList;
import java.util.List;

public class Lagrange {
    public static Function<Double, Double> func(){
        Function<Double, Double> res = (x) -> {return x*x*x + x*x + x;};
        return res;
    }
    public static double[] separition(double[] xValues){
        double[] xValuesNew = new double[xValues.length - 1];
        for (int i = 0; i < xValuesNew.length; i++)
        {
            xValuesNew[i] = (xValues[i + 1] + xValues[i]) / 2;
        }
        return xValuesNew;
    }
    public static double[] partitioning(double a, double b, int n, int partition){
        double[] x = new double[n + 1];
        if(partition == 0) {
            for (int i = 0; i <= n; i++)//равномерное разбиение
                x[i] = a + ((b - a) / n * i);
            return x;
        }
        else {
            for (int i = 0; i <= n; i++)//разбиение Чебышева
                x[i] = 0.5 * (a + b) + (b - a) * 0.5  * Math.cos(Math.PI * (n - i) / n);
            return x;
        }
    }
    public static Function<Double, Double> createBasicPolynomial(double[] xValues, int i){
        Function<Double, Double> basicPolynomial = (x) -> {
            double div = 1;
            double res = 1;
            for (int j = 0; j < xValues.length; j++){
                if(j != i){
                    res *= x - xValues[j];
                    div *= xValues[i] - xValues[j];
                }
            }
            return (Double) (res / div);
        };
        return basicPolynomial;
    }
    public static Function<Double, Double> createLagrangePolynomial(double[] xValues, double[] yValues){
        List<Function<Double, Double>> basicPolynomial = new ArrayList<>();
        for (int i = 0; i < xValues.length; i++)
            basicPolynomial.add(createBasicPolynomial(xValues, i));
        Function<Double, Double> lagrangePolinome = (x) -> {
            double res = 0;
            for (int i = 0; i < yValues.length; i++)
                res += yValues[i] * basicPolynomial.get(i).apply(x);
            return res;
        };
        return lagrangePolinome;
    }


    public static void main(String[] args) {
        //начинаем строить полином лагранжа
        double a = 1;
        double b = 100;
        int n = 5;
        double maxForCheb = 0;
        double maxForRavnom = 0;
        System.out.println("Кол-во отрезков\t" + "\tРавномерное разбиение\t" + "\t\t\tРазбиение Чебышева");
        //Fuction<Double, Double> lagrange = createLangrangePolynomial(xValues, yValues);
        //DecimalFormat decimalFormat = new DecimalFormat("0.######E0");
        //проверяем что правильно постороился
//        for(int i = 0; i <= n; i++) {
//            System.out.println("x = " + xValues[i] + "\ty = " + yValues[i] + "\ty(lagran) = " + lagrange.apply(xValues[i]));
//        }
        //заканчиваем стоить
//        for (int j = 0; j < 3; j++) {
//            for (int k = 0; k < 2; k++) {
//                if (k == 0) {
//                    double[] xValues = partitioning(a, b, n, k);
//                    double[] yValues = new double[n + 1];
//                    for (int i = 0; i <= n; i++) {
//                        yValues[i] = func().apply(xValues[i]);
//                    }
//                    double[] xValuesNew = separition(xValues);
//                    double[] yValuesNew = new double[xValuesNew.length];
//                    for (int i = 0; i < xValuesNew.length; i++) {
//                        yValuesNew[i] = func().apply(xValuesNew[i]);
//                    }
//                    Function<Double, Double> lagrange = createLangrangePolynomial(xValues, yValues);
//                    for (int i = 0; i < n; i++) {
//                        double delta = abs(yValuesNew[i] - lagrange.apply(xValuesNew[i]));
//                        //System.out.println("delta = " + delta);
//                        if (delta > maxForCheb)
//                            maxForRavnom = delta;
//                    }
//                }
//                else{
//                    double[] xValues = partitioning(a, b, n, k);
//                    double[] yValues = new double[n + 1];
//                    for (int i = 0; i <= n; i++) {
//                        yValues[i] = func().apply(xValues[i]);
//                    }
//                    double[] xValuesNew = separition(xValues);
//                    double[] yValuesNew = new double[xValuesNew.length];
//                    for (int i = 0; i < xValuesNew.length; i++) {
//                        yValuesNew[i] = func().apply(xValuesNew[i]);
//                    }
//                    Function<Double, Double> lagrange = createLangrangePolynomial(xValues, yValues);
//                    for (int i = 0; i < n; i++) {
//                        double delta = abs(yValuesNew[i] - lagrange.apply(xValuesNew[i]));
//                        if (delta > maxForCheb)
//                            maxForCheb = delta;
//                    }
//                }
//            }
//            System.out.println("n = " + n + "\t\t\t\t\t" + maxForRavnom + "\t\t\t" + maxForCheb);
//
//            n *= 10;
//            maxForCheb = 0;
//            maxForRavnom = 0;
//        }
        double[] xValues = partitioning(a, b, n, 0);
        double[] yValues = new double[n + 1];
        for (int i = 0; i <= n; i++) {
            yValues[i] = func().apply(xValues[i]);
        }
        Function<Double, Double> lagrange = createLagrangePolynomial(xValues, yValues);
        SimpleGUI simpleGUI = new SimpleGUI(func(), lagrange);
        simpleGUI.setVisible(true);
    }
}
