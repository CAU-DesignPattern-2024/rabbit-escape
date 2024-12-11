package rabbitescape.ui.swing.state;

import rabbitescape.ui.swing.GitHubIssue;

public class LevelIssueState implements IssueState {
    @Override
    public void handleIssue(GitHubIssue issue) {
        // TODO: 레벨 이슈에 특화된 처리
    }

    @Override
    public String getType() {
        return StateUtil.STATE_LEVEL;
    }
}
