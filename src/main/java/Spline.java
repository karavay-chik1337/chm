
public class Spline {
    public static Function<Double, Double> func(){
        Function<Double, Double> res = (x) -> {return x*x*x + 7*x*x + 8*x;};
        return res;
    }
    public static double[] separition(double[] xValues) {
        double[] xValuesNew = new double[xValues.length - 1];
        for (int i = 0; i < xValuesNew.length; i++)
        {
            xValuesNew[i] = (xValues[i + 1] + xValues[i]) / 2;
        }
        return xValuesNew;
    }
    public static double[] partitioning(double a, double b, int n, int partition) {
        double[] x = new double[n + 1];
        if (partition == 0) {
            for (int i = 0; i <= n; i++)//равномерное разбиение
                x[i] = a + ((b - a) / n * i);
            return x;
        } else {
            for (int i = 0; i <= n; i++)//разбиение Чебышева
                x[i] = 0.5 * (a + b) + (b - a) * 0.5 * Math.cos(Math.PI * (n - i) / n);
            return x;
        }
    }
    public static Function<Double, Double> spline(){
        Function<Double, Double> splin = (x) ->{
            return x;
        };
        return splin;
    }


    public static void main(String[] args) {
        double a = 3;
        double b = 6;
        int n = 10;
        double[] xValue = partitioning(a, b, n, 0);
        double[] yValue = new double[n + 1];
        for (int i = 0; i <= n; i++){
            yValue[i] = func().apply(xValue[i]);
        }
        
    }
}
