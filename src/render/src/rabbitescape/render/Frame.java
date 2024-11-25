package rabbitescape.render;


public class Frame {
    private final String name;
    private final int offsetX;
    private final int offsetY; 
    private final String soundEffect;

    private Frame(FrameBuilder builder) {
        this.name = builder.name;
        this.offsetX = builder.offsetX;
        this.offsetY = builder.offsetY;
        this.soundEffect = builder.soundEffect;
    }

    public static class FrameBuilder {
        private final String name;  // required
        private int offsetX = 0;    // optional
        private int offsetY = 0;    // optional
        private String soundEffect = null;  // optional

        public FrameBuilder(String name) {
            this.name = name;
        }

        public FrameBuilder offsetX(int offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        public FrameBuilder offsetY(int offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        public FrameBuilder soundEffect(String soundEffect) {
            this.soundEffect = soundEffect;
            return this;
        }

        public Frame build() {
            return new Frame(this);
        }
    }

    public String getName() { return name; }
    public int getOffsetX() { return offsetX; }
    public int getOffsetY() { return offsetY; }
    public String getSoundEffect() { return soundEffect; }
}
