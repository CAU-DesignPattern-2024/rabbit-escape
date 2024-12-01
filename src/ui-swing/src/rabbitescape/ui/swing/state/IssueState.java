package rabbitescape.ui.swing.state;

import rabbitescape.ui.swing.GitHubIssue;

public interface IssueState {
    void handleIssue(GitHubIssue issue);
    String getType();
}
