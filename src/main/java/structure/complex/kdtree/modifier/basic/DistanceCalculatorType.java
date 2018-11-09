package structure.complex.kdtree.modifier.basic;

import util.DistanceUtil;

public enum DistanceCalculatorType {

    EUCLIDEAN_DISTANCE(new IDistanceCalculator() {
        @Override
        public double distance(double[] pointA, double[] pointB) {
            return DistanceUtil.euclideanDistance(pointA, pointB);
        }
    }),

    HAMMING_DISTANCE(new IDistanceCalculator() {
        @Override
        public double distance(double[] pointA, double[] pointB) {
            return DistanceUtil.hammingDistance(pointA, pointB);
        }
    }),

    SIMPLE_POWERED_DISTANCE(new IDistanceCalculator() {
        @Override
        public double distance(double[] pointA, double[] pointB) {
            return DistanceUtil.poweredDistance(pointA, pointB);
        }
    }),

    ;

    private IDistanceCalculator calculator;

    DistanceCalculatorType(IDistanceCalculator calculator) {
        this.calculator = calculator;
    }

    public IDistanceCalculator getCalculator() {
        return calculator;
    }

}
