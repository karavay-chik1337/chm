public class Section {
    double a;
    double b;
    int n;
    double h;
    int partition;

    public Section(double a, double b, int n, int partition) {
        this.a = a;
        this.b = b;
        this.n = n;
        h = (b - a) / (double) (n);
        this.partition = partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public double[] separation(double[] xValues) {
        double[] xValuesNew = new double[xValues.length - 1];
        for (int i = 0; i < xValuesNew.length; i++) {
            xValuesNew[i] = (xValues[i + 1] + xValues[i]) / 2;
        }
        return xValuesNew;
    }

    public double[] uniformPart() {
        double[] x = new double[n + 1];
        for (int i = 0; i <= n; i++)//равномерное разбиение
            x[i] = a + (h * i);
        return x;
    }

    public double[] chebeshevPart() {//разбиение Чебышева
        double[] x = new double[n + 1];
        for (int i = 0; i <= n; i++)
            x[i] = 0.5 * (a + b) + (b - a) * 0.5 * Math.cos(Math.PI * (n - i) / n);
        return x;
    }
    public double[] xGenerate(){
        return partition == 0 ? uniformPart() : chebeshevPart();
    }
}
