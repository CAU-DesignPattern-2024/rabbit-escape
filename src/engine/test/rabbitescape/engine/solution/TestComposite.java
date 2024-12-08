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
        String serialized = SolutionParser.serialize(solution);

        // Then
        assertEquals("3", serialized);
    }

    @Test
    public void testMultipleCommandActionsSerialization() {
        // Given
        CommandAction climbAction = new SelectAction(Token.Type.climb);
        CommandAction digAction = new SelectAction(Token.Type.dig);
        SolutionCommand command = new SolutionCommand(climbAction, digAction);
        Solution solution = new Solution(command);

        // When
        String serialized = SolutionParser.serialize(solution);

        // Then
        assertEquals("climb&dig", serialized);
    }

    @Test
    public void testSerializationAndDeserializationConsistency() {
        // Given
        CommandAction waitAction = new WaitAction(5);
        SolutionCommand command = new SolutionCommand(waitAction);
        Solution solution = new Solution(command);

        // When
        String serialized = SolutionParser.serialize(solution);
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
        assertEquals("3;climb", serialized);
    }

    @Test
    public void testCombineWaitActionsUsingAppendSolutionCommand() {
        // Given
        SolutionRecorder recorder = new SolutionRecorder();

        // Append two WaitActions, wrapped in SolutionCommand
        recorder.append(new SolutionCommand(new WaitAction(3))); // Wrap first WaitAction
        recorder.append(new SolutionCommand(new WaitAction(5))); // Wrap second WaitAction
        recorder.appendStepEnd();

        // When
        String serialized = recorder.getRecord();

        // Then
        // Both WaitActions should be combined into a single WaitAction with 8 steps
        assertEquals("8", serialized);
    }

    @Test
    public void testCombineMultipleWaitActionsSequentially() {
        // Given
        SolutionRecorder recorder = new SolutionRecorder();

        // Append multiple WaitActions, wrapped in SolutionCommand
        recorder.append(new SolutionCommand(new WaitAction(2)));
        recorder.append(new SolutionCommand(new WaitAction(4)));
        recorder.append(new SolutionCommand(new WaitAction(1)));
        recorder.appendStepEnd();

        // When
        String serialized = recorder.getRecord();

        // Then
        // All WaitActions should be combined into a single WaitAction with 7 steps
        assertEquals("7", serialized);
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
