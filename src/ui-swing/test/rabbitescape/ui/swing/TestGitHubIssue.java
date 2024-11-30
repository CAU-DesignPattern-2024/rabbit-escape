package rabbitescape.ui.swing;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

import rabbitescape.ui.swing.state.BugIssueState;
import rabbitescape.ui.swing.state.LevelIssueState;
import rabbitescape.ui.swing.state.DefaultIssueState;

public class TestGitHubIssue {
    private GitHubIssue issue;
    private static final int TEST_ISSUE_NUMBER = 1;
    private static final String TEST_TITLE = "Test Issue";
    private static final String TEST_BODY = "Test Body";

    @Before
    public void setUp() {
        issue = new GitHubIssue(TEST_ISSUE_NUMBER, TEST_TITLE, TEST_BODY);
    }

    @Test
    public void defaultStateTest() {
        assertThat(issue.isBug()).isFalse();
        assertThat(issue.isLevel()).isFalse();
    }

    @Test
    public void bugStateTest() {
        // Given
        GitHubIssue bugIssue = new GitHubIssue(TEST_ISSUE_NUMBER, TEST_TITLE, TEST_BODY, 
            new String[]{"bug"});

        // Then
        assertThat(bugIssue.isBug()).isTrue();
        assertThat(bugIssue.isLevel()).isFalse();
    }

    @Test
    public void levelStateTest() {
        // Given
        GitHubIssue levelIssue = new GitHubIssue(TEST_ISSUE_NUMBER, TEST_TITLE, TEST_BODY, 
            new String[]{"level"});

        // Then
        assertThat(levelIssue.isBug()).isFalse();
        assertThat(levelIssue.isLevel()).isTrue();
    }

    @Test
    public void changeToBugStateTest() {
        // When
        issue.setState(new BugIssueState());

        // Then
        assertThat(issue.isBug()).isTrue();
        assertThat(issue.isLevel()).isFalse();
    }

    @Test
    public void changeToLevelStateTest() {
        // When
        issue.setState(new LevelIssueState());

        // Then
        assertThat(issue.isBug()).isFalse();
        assertThat(issue.isLevel()).isTrue();
    }

    @Test
    public void changeToDefaultStateTest() {
        // Given
        issue.setState(new BugIssueState());
        
        // When
        issue.setState(new DefaultIssueState());

        // Then
        assertThat(issue.isBug()).isFalse();
        assertThat(issue.isLevel()).isFalse();
    }

    @Test
    public void changeToLevelStateWithMultiLabelTest() {
        // Given
        GitHubIssue multiLabelIssue = new GitHubIssue(TEST_ISSUE_NUMBER, TEST_TITLE, TEST_BODY, 
            new String[]{"bug", "level"});

        // Then
        assertThat(multiLabelIssue.isBug()).isFalse();
        assertThat(multiLabelIssue.isLevel()).isTrue();
    }

    @Test
    public void nullCouldNotChangeStateTest() {
        // Given
        issue.setState(new BugIssueState());
        
        // When
        issue.setState(null);

        // Then
        assertThat(issue.isBug()).isTrue();
    }
}
