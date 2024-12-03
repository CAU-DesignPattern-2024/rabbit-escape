package rabbitescape.engine;

public interface ThingVisitor {
    void visit(Entrance token);
    void visit(Exit exit);
    void visit(Fire fire);
    void visit(Pipe pipe);
    void visit(Rabbit rabbit);
    void visit(Token token);
    void visit(WaterRegion waterRegion);
}
