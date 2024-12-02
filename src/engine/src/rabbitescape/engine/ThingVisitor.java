package rabbitescape.engine;

public interface ThingVisitor {
    void visit(Entrance token);
    void visit(Exit token);
    void visit(Fire token);
    void visit(Pipe token);
    void visit(Rabbit rabbit);
    void visit(Token token);
    void visit(WaterRegion token);
}
