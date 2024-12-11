package rabbitescape.engine.logic;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static rabbitescape.engine.Block.Shape.*;
import static rabbitescape.engine.Direction.*;
import static rabbitescape.engine.Rabbit.Type.*;
import rabbitescape.engine.behaviours.bridge.*;
import rabbitescape.engine.textworld.Comment;
import rabbitescape.engine.util.Dimension;
import rabbitescape.engine.util.Position;
import rabbitescape.engine.behaviours.Bridging.BridgeType;

import org.junit.Test;
import java.util.*;

import rabbitescape.engine.Block;
import rabbitescape.engine.Rabbit;
import rabbitescape.engine.Thing;
import rabbitescape.engine.Token;
import rabbitescape.engine.VoidMarkerStyle;
import rabbitescape.engine.World;
import rabbitescape.engine.WorldStatsListener;


public class TestBridgingStrategies {

    // 초기 전략들이 true를 반환하는지 확인하는테스트 케이스
    @Test
    public void test_initial_strategies_return_true() {
        World world = createEmptyWorld();
        Rabbit rabbit = new Rabbit(2, 2, RIGHT, RABBIT);
        
        assertThat(new InitialAlongBridgingStrategy().execute(world, rabbit), is(true));
        assertThat(new InitialUpBridgingStrategy().execute(world, rabbit), is(true));
        assertThat(new InitialDownUpBridgingStrategy().execute(world, rabbit), is(true));
        assertThat(new InitialInCornerBridgingStrategy().execute(world, rabbit), is(true));
    }
    
    // 초기 전략들의 BridgeType을 검증하는 테스트 케이스
    @Test
    public void test_bridge_type_providers() {
        assertThat(new InitialAlongBridgingStrategy().getBridgeType(), is(BridgeType.ALONG));
        assertThat(new InitialUpBridgingStrategy().getBridgeType(), is(BridgeType.UP));
        assertThat(new InitialDownUpBridgingStrategy().getBridgeType(), is(BridgeType.DOWN_UP));
        assertThat(new InitialInCornerBridgingStrategy().getBridgeType(), is(BridgeType.ALONG));
    }
    
    // RightBridgingStrategy: 토끼가 오른쪽으로 이동하고 BRIDGE_UP_RIGHT 블록을 추가하는지 확인하는 테스트 케이스
    @Test
    public void test_right_bridging_strategy() {
        // Given
        World world = createEmptyWorld();
        Rabbit rabbit = new Rabbit(2, 2, RIGHT, RABBIT);
        RightBridgingStrategy strategy = new RightBridgingStrategy();
        
        // When
        System.out.println("Executing RightBridgingStrategy");
        boolean result = strategy.execute(world, rabbit);
        System.out.println("Blocks to add: " + world.changes.getBlocksToAdd().size());
        
        // Then
        assertThat(result, is(true));
        assertThat(rabbit.x, is(3));  // x position increased
        assertThat(rabbit.y, is(2));  // y position unchanged
        assertThat(world.changes.getBlocksToAdd().size(), is(1));
        Block addedBlock = world.changes.getBlocksToAdd().get(0);
        assertThat(addedBlock.shape, is(BRIDGE_UP_RIGHT));
    }
    
    // LeftBridgingStrategy: 토끼가 왼쪽으로 이동하고 BRIDGE_UP_LEFT 블록을 추가하는지 확인하는 테스트 케이스
    @Test
    public void test_left_bridging_strategy() {
        // Given
        World world = createEmptyWorld();
        Rabbit rabbit = new Rabbit(2, 2, LEFT, RABBIT);
        LeftBridgingStrategy strategy = new LeftBridgingStrategy();
        
        // When
        boolean result = strategy.execute(world, rabbit);
        
        // Then
        assertThat(result, is(true));
        assertThat(rabbit.x, is(1));  // x position decreased
        assertThat(rabbit.y, is(2));  // y position unchanged
        assertThat(world.changes.getBlocksToAdd().size(), is(1));
        Block addedBlock = world.changes.getBlocksToAdd().get(0);
        assertThat(addedBlock.shape, is(BRIDGE_UP_LEFT));
    }
    
    // UpRightBridgingStrategy: 토끼가 오른쪽 위로 이동하고 BRIDGE_UP_RIGHT 블록을 추가하는지 확인하는 테스트 케이스
    @Test
    public void test_up_right_bridging_strategy() {
        // Given
        World world = createEmptyWorld();
        Rabbit rabbit = new Rabbit(2, 2, RIGHT, RABBIT);
        UpRightBridgingStrategy strategy = new UpRightBridgingStrategy();
        
        // When
        boolean result = strategy.execute(world, rabbit);
        
        // Then
        assertThat(result, is(true));
        assertThat(rabbit.x, is(3));  // x position increased
        assertThat(rabbit.y, is(1));  // y position decreased
        assertThat(world.changes.getBlocksToAdd().size(), is(1));
        Block addedBlock = world.changes.getBlocksToAdd().get(0);
        assertThat(addedBlock.shape, is(BRIDGE_UP_RIGHT));
    }
    
    // UpLeftBridgingStrategy: 토끼가 왼쪽 위로 이동하고 BRIDGE_UP_LEFT 블록을 추가하는지 확인하는 테스트 케이스
    @Test
    public void test_up_left_bridging_strategy() {
        // Given
        World world = createEmptyWorld();
        Rabbit rabbit = new Rabbit(2, 2, LEFT, RABBIT);
        UpLeftBridgingStrategy strategy = new UpLeftBridgingStrategy();
        
        // When
        boolean result = strategy.execute(world, rabbit);
        
        // Then
        assertThat(result, is(true));
        assertThat(rabbit.x, is(1));  // x position decreased
        assertThat(rabbit.y, is(1));  // y position decreased
        assertThat(world.changes.getBlocksToAdd().size(), is(1));
        Block addedBlock = world.changes.getBlocksToAdd().get(0);
        assertThat(addedBlock.shape, is(BRIDGE_UP_LEFT));
    }
    
    // InCornerRightBridgingStrategy: 토끼가 제자리에 머물고 onSlope가 true가 되며 BRIDGE_UP_RIGHT 블록을 추가하는지 확인하는 테스트 케이스
    @Test
    public void test_in_corner_right_bridging_strategy() {
        // Given
        World world = createEmptyWorld();
        Rabbit rabbit = new Rabbit(2, 2, RIGHT, RABBIT);
        InCornerRightBridgingStrategy strategy = new InCornerRightBridgingStrategy();
        
        // When
        boolean result = strategy.execute(world, rabbit);
        
        // Then
        assertThat(result, is(true));
        assertThat(rabbit.x, is(2));  // x position unchanged
        assertThat(rabbit.y, is(2));  // y position unchanged
        assertThat(rabbit.onSlope, is(true));
        assertThat(world.changes.getBlocksToAdd().size(), is(1));
        Block addedBlock = world.changes.getBlocksToAdd().get(0);
        assertThat(addedBlock.shape, is(BRIDGE_UP_RIGHT));
    }
    
    // InCornerLeftBridgingStrategy: 토끼가 제자리에 머물고 onSlope가 true가 되며 BRIDGE_UP_LEFT 블록을 추가하는지 확인하는 테스트 케이스
    @Test
    public void test_in_corner_left_bridging_strategy() {
        // Given
        World world = createEmptyWorld();
        Rabbit rabbit = new Rabbit(2, 2, LEFT, RABBIT);
        InCornerLeftBridgingStrategy strategy = new InCornerLeftBridgingStrategy();
        
        // When
        boolean result = strategy.execute(world, rabbit);
        
        // Then
        assertThat(result, is(true));
        assertThat(rabbit.x, is(2));  // x position unchanged
        assertThat(rabbit.y, is(2));  // y position unchanged
        assertThat(rabbit.onSlope, is(true));
        assertThat(world.changes.getBlocksToAdd().size(), is(1));
        Block addedBlock = world.changes.getBlocksToAdd().get(0);
        assertThat(addedBlock.shape, is(BRIDGE_UP_LEFT));
    }
    
    // InCornerUpRightBridgingStrategy: 토끼가 위로 이동하고 onSlope가 true가 되며 BRIDGE_UP_RIGHT 블록을 추가하는지 확인하는 테스트 케이스
    @Test
    public void test_in_corner_up_right_bridging_strategy() {
        // Given
        World world = createEmptyWorld();
        Rabbit rabbit = new Rabbit(2, 2, RIGHT, RABBIT);
        InCornerUpRightBridgingStrategy strategy = new InCornerUpRightBridgingStrategy();
        
        // When
        boolean result = strategy.execute(world, rabbit);
        
        // Then
        assertThat(result, is(true));
        assertThat(rabbit.x, is(2));  // x position unchanged
        assertThat(rabbit.y, is(1));  // y position decreased
        assertThat(rabbit.onSlope, is(true));
        assertThat(world.changes.getBlocksToAdd().size(), is(1));
        Block addedBlock = world.changes.getBlocksToAdd().get(0);
        assertThat(addedBlock.shape, is(BRIDGE_UP_RIGHT));
    }
    
    // InCornerUpLeftBridgingStrategy: 토끼가 위로 이동하고 onSlope가 true가 되며 BRIDGE_UP_LEFT 블록을 추가하는지 확인하는 테스트 케이스
    @Test
    public void test_in_corner_up_left_bridging_strategy() {
        // Given
        World world = createEmptyWorld();
        Rabbit rabbit = new Rabbit(2, 2, LEFT, RABBIT);
        InCornerUpLeftBridgingStrategy strategy = new InCornerUpLeftBridgingStrategy();
        
        // When
        boolean result = strategy.execute(world, rabbit);
        
        // Then
        assertThat(result, is(true));
        assertThat(rabbit.x, is(2));  // x position unchanged
        assertThat(rabbit.y, is(1));  // y position decreased
        assertThat(rabbit.onSlope, is(true));
        assertThat(world.changes.getBlocksToAdd().size(), is(1));
        Block addedBlock = world.changes.getBlocksToAdd().get(0);
        assertThat(addedBlock.shape, is(BRIDGE_UP_LEFT));
    }
    
    // 테스트용 World 생성
    private World createEmptyWorld() {
        Dimension size = new Dimension(10, 10);
        List<Block> blocks = new ArrayList<>();
        List<Rabbit> rabbits = new ArrayList<>();
        List<Thing> things = new ArrayList<>();
        Map<Position, Integer> waterAmounts = new HashMap<>();
        Map<Token.Type, Integer> abilities = new HashMap<>();
        String[] hints = new String[0];
        String[] solutions = new String[0];
        Comment[] comments = new Comment[0];
        World world = new World(
            size, blocks, rabbits, things, waterAmounts, abilities,
            "TestWorld", "", "", "", hints, solutions,
            0, 0, new int[0], "", 0, 0, 0, 0, false, comments,
            new WorldStatsListener() {
                @Override
                public void worldStats(int numSaved, int numToSave) {
                    // 테스트용 임시 리스너
                }
            }, VoidMarkerStyle.Style.HIGHLIGHTER
        );
        world.num_waiting = 1; // 오직 1마리의 토끼만 테스트
        world.setPaused(false);
        return world;
    }
}
