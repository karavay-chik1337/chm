
import java.util.ArrayList;
import java.util.List;

public class Lagrange {
    public static Function<Double, Double> func(){
        return (x) -> {return x*x*x + x*x + x;};
    }
    public static Function<Double, Double> createBasicPolynomial(double[] xValues, int i){
        return (x) -> {
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
    }
    public static Function<Double, Double> createLagrangePolynomial(Section section){
        double[] xValues = section.partitioning();
        double[] yValues = new double[xValues.length];
        for (int i = 0; i < yValues.length; i++) {
            yValues[i] = func().apply(xValues[i]);
        }
        List<Function<Double, Double>> basicPolynomial = new ArrayList<>();
        for (int i = 0; i < xValues.length; i++)
            basicPolynomial.add(createBasicPolynomial(xValues, i));
        return (x) -> {
            double res = 0;
            for (int i = 0; i < yValues.length; i++)
                res += yValues[i] * basicPolynomial.get(i).apply(x);
            return res;
        };
    }
    public static Function<Double, Double> createLagrangePolynomial(Section section, int k){
        double[] xValues = section.partitioning(k);
        double[] yValues = new double[xValues.length];
        for (int i = 0; i < yValues.length; i++) {
            yValues[i] = func().apply(xValues[i]);
        }
        List<Function<Double, Double>> basicPolynomial = new ArrayList<>();
        for (int i = 0; i < xValues.length; i++)
            basicPolynomial.add(createBasicPolynomial(xValues, i));
        return (x) -> {
            double res = 0;
            for (int i = 0; i < yValues.length; i++)
                res += yValues[i] * basicPolynomial.get(i).apply(x);
            return res;
        };
    }
//    public static void main(String args[]) {
//        double maxForCheb = 0;
//        double maxForRavnom = 0;
//        System.out.println("Кол-во отрезков\t" + "\tРавномерное разбиение\t" + "\t\t\tРазбиение Чебышева");
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
//        double[] xValues = partitioning(a, b, n, 0);
//        double[] yValues = new double[n + 1];
//        for (int i = 0; i <= n; i++) {
//            yValues[i] = func().apply(xValues[i]);
//        }
//        Function<Double, Double> lagrange = createLagrangePolynomial(xValues, yValues);
//
//    }
}
