package rabbitescape.engine.solution;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import rabbitescape.engine.Token;

public class TestComposite {
    @Test
    public void testSingleCommandActionSerialization() {
        // Given
        CommandAction waitAction = new WaitAction(3);
        SolutionCommand command = new SolutionCommand(waitAction);
        Solution solution = new Solution(command);

        // When
        String serialized = SolutionParser.serialise(solution);

        // Then
        assertEquals("3;", serialized);
    }

    @Test
    public void testMultipleCommandActionsSerialization() {
        // Given
        CommandAction waitAction = new WaitAction(2);
        CommandAction selectAction = new SelectAction(Token.Type.dig);
        SolutionCommand command = new SolutionCommand(waitAction, selectAction);
        Solution solution = new Solution(command);

        // When
        String serialized = SolutionParser.serialise(solution);

        // Then
        assertEquals("2&DIG;", serialized);
    }

    @Test
    public void testNestedSolutionSerialization() {
        // Given
        SolutionCommand innerCommand = new SolutionCommand(new WaitAction(1));
        Solution nestedSolution = new Solution(innerCommand);

        SolutionCommand outerCommand = new SolutionCommand(new WaitAction(2));
        Solution outerSolution = new Solution(outerCommand);
        outerSolution.add(nestedSolution);

        // When
        String serialized = SolutionParser.serialise(outerSolution);

        // Then
        assertEquals("2;;1;", serialized);
    }

    @Test
    public void testSerializationAndDeserializationConsistency() {
        // Given
        CommandAction waitAction = new WaitAction(5);
        CommandAction selectAction = new SelectAction(Token.Type.bridge);
        SolutionCommand command = new SolutionCommand(waitAction, selectAction);
        Solution solution = new Solution(command);

        // When
        String serialized = SolutionParser.serialise(solution);
        Solution deserializedSolution = SolutionParser.parse(serialized);

        // Then
        assertEquals(solution, deserializedSolution);
    }

    @Test
    public void testSolutionRecorderAppendAndSerialization() {
        // Given
        SolutionRecorder recorder = new SolutionRecorder();
        recorder.append(new WaitAction(3));
        recorder.appendStepEnd();
        recorder.append(new SelectAction(Token.Type.climb));
        recorder.appendStepEnd();

        // When
        String serialized = recorder.getRecord();

        // Then
        assertEquals("3;climb;", serialized);
    }

    @Test(expected = SolutionCommand.WaitActionInMultiActionCommand.class)
    public void testWaitActionInMultiActionCommandThrowsException() {
        // Given
        CommandAction waitAction = new WaitAction(2);
        CommandAction selectAction = new SelectAction(Token.Type.bridge);

        // When
        new SolutionCommand(waitAction, selectAction);

        // Then -> Expect exception
    }
}
