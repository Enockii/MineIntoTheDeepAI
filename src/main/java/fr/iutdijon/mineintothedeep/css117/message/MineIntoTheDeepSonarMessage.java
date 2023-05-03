package fr.iutdijon.mineintothedeep.css117.message;

public class MineIntoTheDeepSonarMessage extends MineIntoTheDeepMessage<MineIntoTheDeepSonarMessage.MineIntoTheDeepSonarResponse> {
    public MineIntoTheDeepSonarMessage(int x, int y) {
        super(MineIntoTheDeepMessages.CLIENT_SONAR, Integer.toString(y), Integer.toString(x));
    }

    @Override
    public MineIntoTheDeepResponse<MineIntoTheDeepSonarResponse> parse(String[] responses) {
        if (responses.length == 0)
            throw new IllegalArgumentException("The response cannot be null");

        if (responses.length != 4)
            throw new IllegalArgumentException("The response must have 4 values, received " + responses.length + " values");

        int valueInHigherLayer = Integer.parseInt(responses[0]);
        int valueInHigherLayerMinus1 = Integer.parseInt(responses[1]);
        int valueInHigherLayerMinus2 = Integer.parseInt(responses[2]);
        int valueInHigherLayerMinus3 = Integer.parseInt(responses[3]);

        return new MineIntoTheDeepResponse<>(new MineIntoTheDeepSonarResponse(valueInHigherLayer, valueInHigherLayerMinus1, valueInHigherLayerMinus2, valueInHigherLayerMinus3), null);
    }

    public static class MineIntoTheDeepSonarResponse
    {
        private final int valueInHigherLayer;
        private final int valueInHigherLayerMinus1;
        private final int valueInHigherLayerMinus2;
        private final int valueInHigherLayerMinus3;

        private MineIntoTheDeepSonarResponse(int valueInHigherLayer, int valueInHigherLayerMinus1, int valueInHigherLayerMinus2, int valueInHigherLayerMinus3) {
            this.valueInHigherLayer = valueInHigherLayer;
            this.valueInHigherLayerMinus1 = valueInHigherLayerMinus1;
            this.valueInHigherLayerMinus2 = valueInHigherLayerMinus2;
            this.valueInHigherLayerMinus3 = valueInHigherLayerMinus3;
        }

        public int getValueInHigherLayer() {
            return valueInHigherLayer;
        }

        public int getValueInHigherLayerMinus1() {
            return valueInHigherLayerMinus1;
        }

        public int getValueInHigherLayerMinus2() {
            return valueInHigherLayerMinus2;
        }

        public int getValueInHigherLayerMinus3() {
            return valueInHigherLayerMinus3;
        }

        @Override
        public String toString() {
            return "MineIntoTheDeepSonarResponse{" +
                    "valueInHigherLayer=" + valueInHigherLayer +
                    ", valueInHigherLayerMinus1=" + valueInHigherLayerMinus1 +
                    ", valueInHigherLayerMinus2=" + valueInHigherLayerMinus2 +
                    ", valueInHigherLayerMinus3=" + valueInHigherLayerMinus3 +
                    '}';
        }
    }
}
