package rabbitescape.ui.swing.state;

import rabbitescape.ui.swing.GitHubIssue;

public class DefaultIssueState implements IssueState {
    @Override
    public void handleIssue(GitHubIssue issue) {
        // 기본 상태 처리
        issue.setIsBug(false);
        issue.setIsLevel(false);
    }

    @Override
    public String getType() {
        return "default";
    }
}
