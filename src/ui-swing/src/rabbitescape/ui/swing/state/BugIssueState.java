package rabbitescape.ui.swing.state;

import rabbitescape.ui.swing.GitHubIssue;

public class BugIssueState implements IssueState {
    @Override
    public void handleIssue(GitHubIssue issue) {
        // 버그 이슈에 특화된 처리
    }

    @Override
    public String getType() {
        return "bug";
    }
}

