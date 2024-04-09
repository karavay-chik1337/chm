
public class Spline {
    public static Function<Double, Double> func(){
        Function<Double, Double> res = (x) -> {return x*x*x + 7*x*x + 8*x;};
        return res;
    }
    public static Function<Double, Double> func1proizvodnaya(){
        Function<Double, Double> res = (x) -> {return 3*x*x + 14*x + 8;};
        return res;
    }
    public static Function<Double, Double> func2proizvodnaya(){
        Function<Double, Double> res = (x) -> {return 6*x + 14;};
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

    public static void metodprog(int n, double[] h, double start, double end, double[] fValue){
        double[] x = new double[n];
        double[] a = new double[n];
        double[] b = new double[n];
        double[] c = new double[n];
        double[] y = new double[n];
        double[] f = new double[n];
        double[] alpha = new double[n-1];
        double[] betta = new double[n];
        f[0] = func1proizvodnaya().apply(start);
        f[n-1] = func2proizvodnaya().apply(end);
        for (int i = 1; i < n - 1; i++){
            f[i] = 6*((fValue[i+1]-fValue[i])/h[i+1] - (fValue[i] - fValue[i-1])/h[i]);
        }
        b[0]=2;
        for(int i = 0; i < n; i++){
            b[i] =
        }
        a[0] = c[3] = 0;
//        a[1] = 1;
//        a[2] = 1;
//        a[3] = 0;
//        b[0] = 1;
//        b[1] = 4;
//        b[2] = 4;
//        b[3] = 1;
//        c[0] = 0.5D;
//        c[1] = 1;
//        c[2] = 1;
//        f[0] = 51;
//        f[1] = 228;
//        f[2] = 264;
//        f[3] = 50;
//        y[0] = b[0];
//        alpha[0] = -c[0]/y[0];
//        betta[0] = f[0]/y[0];
//        y[1] = b[1] + a[1]*alpha[0];
//        alpha[1]=-c[1]/y[1];
//        betta[1]=(f[1]-a[1]*betta[0])/y[1];
//        y[2] = b[2] + a[2]*alpha[1];
//        alpha[2]=-c[2]/y[2];
//        betta[2]=(f[2]-a[2]*betta[1])/y[2];
//        y[3] = b[3]+a[3]*alpha[2];
//        betta[3]=(f[3]-a[3]*betta[2])/y[3];
//        x[3]=betta[3];
//        x[2]=alpha[2]*x[3]+betta[2];
//        x[1]=alpha[1]*x[2]+betta[1];
//        x[0]=alpha[0]*x[1]+betta[0];
//        for(double val : x)
//        System.out.println(val + " ");
    }

    public static void main(String[] args) {
//        double a = 3;
//        double b = 6;
//        int n = 10;
//        double[] xValue = partitioning(a, b, n, 0);
//        double[] yValue = new double[n + 1];
//        for (int i = 0; i <= n; i++){
//            yValue[i] = func().apply(xValue[i]);
//        }
        metodprog();
    }
}
