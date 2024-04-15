
import java.util.ArrayList;
import java.util.List;

public class Lagrange {
    public static Function<Double, Double> func() {
        return (x) -> {
            return Math.cos(x);
            //return x * x * x + x * x + x;
        };
    }
    public static Function<Double, Double> createBasicPolynomial(double[] xValues, int i) {
        return (x) -> {
            double div = 1;
            double res = 1;
            for (int j = 0; j < xValues.length; j++) {
                if (j != i) {
                    res *= x - xValues[j];
                    div *= xValues[i] - xValues[j];
                }
            }
            return (Double) (res / div);
        };
    }
    public static Function<Double, Double> createLagrangePolynomial(Section section) {
        double[] xValues = section.xGenerate();
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
}
