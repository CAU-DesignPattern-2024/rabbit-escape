package rabbitescape.ui.swing;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import rabbitescape.ui.swing.state.BugIssueState;
import rabbitescape.ui.swing.state.LevelIssueState;
import rabbitescape.ui.swing.state.StateUtil;
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
        assertThat(issue.getStateType(), is(StateUtil.STATE_DEFAULT));
    }

    @Test
    public void bugStateTest() {
        // When
        GitHubIssue bugIssue = new GitHubIssue(TEST_ISSUE_NUMBER, TEST_TITLE, TEST_BODY, 
            new String[]{"bug"});

        // Then
        assertThat(bugIssue.getStateType(), is(StateUtil.STATE_BUG));
    }

    @Test
    public void levelStateTest() {
        // When
        GitHubIssue levelIssue = new GitHubIssue(TEST_ISSUE_NUMBER, TEST_TITLE, TEST_BODY, 
            new String[]{"level"});

        // Then
        assertThat(levelIssue.getStateType(), is(StateUtil.STATE_LEVEL));
    }

    @Test
    public void changeToBugStateTest() {
        // When
        issue.setState(new BugIssueState());

        // Then
        assertThat(issue.getStateType(), is(StateUtil.STATE_BUG));
    }

    @Test
    public void changeToLevelStateTest() {
        // When
        issue.setState(new LevelIssueState());

        // Then
        assertThat(issue.getStateType(), is(StateUtil.STATE_LEVEL));
    }

    @Test
    public void changeToDefaultStateTest() {
        // When
        issue.setState(new DefaultIssueState());

        // Then
        assertThat(issue.getStateType(), is(StateUtil.STATE_DEFAULT));
    }

    @Test
    public void changeToLevelStateWithMultiLabelTest() {
        // When
        GitHubIssue multiLabelIssue = new GitHubIssue(TEST_ISSUE_NUMBER, TEST_TITLE, TEST_BODY, 
            new String[]{"bug", "level"});

        // Then
        assertThat(multiLabelIssue.getStateType(), is(StateUtil.STATE_LEVEL));
    }

    @Test
    public void nullCouldNotChangeStateTest() {
        // When
        issue.setState(new BugIssueState());
        issue.setState(null);

        // Then
        assertThat(issue.getStateType(), is(StateUtil.STATE_BUG));
    }
}
