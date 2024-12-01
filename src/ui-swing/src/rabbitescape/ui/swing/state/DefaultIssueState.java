package rabbitescape.ui.swing.state;

import rabbitescape.ui.swing.GitHubIssue;

public class DefaultIssueState implements IssueState {
    @Override
    public void handleIssue(GitHubIssue issue) {
        // 기본 상태 처리
    }

    @Override
    public String getType() {
        return "default";
    }
}
