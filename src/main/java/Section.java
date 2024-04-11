public class Section {
    double a;
    double b;
    int n;
    int partition;

    public Section(double a, double b, int n) {
        this.a = a;
        this.b = b;
        this.n = n;
    }
    public Section(double a, double b, int n, int partition) {
        this.a = a;
        this.b = b;
        this.n = n;
        this.partition = partition;
    }

    public int getN() {
        return n;
    }

    public double[] separation(double[] xValues){
        double[] xValuesNew = new double[xValues.length - 1];
        for (int i = 0; i < xValuesNew.length; i++)
        {
            xValuesNew[i] = (xValues[i + 1] + xValues[i]) / 2;
        }
        return xValuesNew;
    }
    public double[] partitioning(){
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
    public double[] partitioning(int k){
        double[] x = new double[n + 1];
        if(k == 0) {
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
}
