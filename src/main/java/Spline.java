import java.util.Arrays;

public class Spline {
    public static Function<Double, Double> func() {
        return (x) -> {
            return x * x * x + 7 * x * x + 8 * x;
            //return Math.sin(x);
            //return Math.abs(x);
        };
    }

    public static Function<Double, Double> funcFirstDerivative() {
        return (x) -> {
//            if(x>0)
//                return 1d;
//            else {
//                if (x < 0)
//                    return -1d;
//                else return null;
//            }
            return 3*x*x + 14*x + 8d;
            //return Math.cos(x);
        };
    }

    public static Function<Double, Double> funcSecondDerivative() {
        return (x) -> {
            //return 0d;
            return 6 * x + 14;
            //return -1d * Math.sin(x);
        };
    }

    public static double[] searchCoefficientC(Section section, double[] fValue) {
        int n = section.getN() + 1;
        double[] x = new double[n];
        double[] a = new double[n];
        double[] b = new double[n];
        double[] c = new double[n];
        double[] y = new double[n];
        double[] f = new double[n];
        double[] alpha = new double[n - 1];
        double[] betta = new double[n];
        double h = section.h;
        f[0] = 6d / h * ((fValue[1] - fValue[0]) / h - funcFirstDerivative().apply(section.a));
        f[n - 1] = funcSecondDerivative().apply(section.b);
        for (int i = 1; i < n - 1; i++) {
            f[i] = 6 * ((fValue[i + 1] - fValue[i]) / h - (fValue[i] - fValue[i - 1]) / h);
        }

        //заполняем вектора ленточной матрицы(a,b,c)
        b[0] = 2d;
        c[0] = 1d;
        for (int i = 1; i < n; i++) {
            if (i < n - 1) {
                b[i] = 4 * h;
                a[i] = h;
                c[i] = h;
            } else {
                a[0] = 0d;
                a[n - 1] = 0d;
                b[i] = 1d;
                c[n - 1] = 0d;
            }
        }

        //прямой ход
        y[0] = b[0];
        alpha[0] = -c[0] / y[0];
        betta[0] = f[0] / y[0];
        for (int i = 1; i < n; i++) {
            y[i] = b[i] + a[i] * alpha[i - 1];
            if (i < n - 1)
                alpha[i] = -c[i] / y[i];
            betta[i] = (f[i] - a[i] * betta[i - 1]) / y[i];
        }

        //обратный ход
        x[n - 1] = betta[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            x[i] = alpha[i] * x[i + 1] + betta[i];
        }
        return x;
//        for(double val : x)
//            System.out.println(val + " ");
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

    public static double[][] searchAllCoefficients(Section section, double[] fValue) {
        double[] c = searchCoefficientC(section, fValue);
        double h = section.h;
        double[][] all = new double[fValue.length - 1][4];
        for (int i = 1; i <= fValue.length - 1; i++) {
            all[i - 1][0] = fValue[i];
            all[i - 1][1] = (fValue[i] - fValue[i - 1]) / h + (c[i] * h / 3) + (c[i - 1] * h / 6);
            all[i - 1][2] = c[i];
            all[i - 1][3] = (c[i] - c[i - 1]) / h;
        }
        return all;
    }

    public static Function<Double, Double> createCubicSpline(Section section) {
        double[] xValue = section.xGenerate();
        double[] fValue = new double[section.n + 1];
        for (int i = 0; i < fValue.length; i++) {
            fValue[i] = func().apply(xValue[i]);
        }
        double[][] all = searchAllCoefficients(section, fValue);
//        for (int i = 0; i < section.n-1; i++) {
//            for (int j = 0; j < 4; j++)
//                System.out.print(all[i][j] + " ");
//            System.out.println();
//        }
        return (x) -> {
            int k = 0;
            for (int i = 0; i < fValue.length - 1; i++) {
                if (x >= xValue[i] && x <= xValue[i + 1]) {
                    k = i;
                    break;
                }
            }
            double dif = x - xValue[k + 1];
            return all[k][0] + all[k][1] * (dif)
                    + all[k][2] / 2d * Math.pow(dif, 2)
                    + all[k][3] / 6d * Math.pow(dif, 3);
        };
    }
}
