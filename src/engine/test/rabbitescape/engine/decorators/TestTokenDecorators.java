package rabbitescape.engine.decorators;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static rabbitescape.engine.ChangeDescription.State.*;

import org.junit.Test;
import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.Token.Type;
import rabbitescape.engine.World;
import rabbitescape.engine.textworld.TextWorldManip;

public class TestTokenDecorators {
    
    @Test
    public void test_base_token_behavior() {
        TokenComponent token = new BaseToken(1, 2, Type.bridge);
        assertThat(token.getX(), is(1));
        assertThat(token.getY(), is(2));
        assertThat(token.getType(), is(Type.bridge));
        assertThat(token.getState(), is(TOKEN_BRIDGE_STILL));
    }

    @Test
    public void test_enhanced_range_decorator() {
        TokenComponent baseToken = new BaseToken(1, 2, Type.bridge);
        TokenComponent enhancedToken = new EnhancedRangeDecorator(baseToken, baseToken.getX(), baseToken.getY(), baseToken.getState(), 2);
        
        // 기본 속성들이 제대로 위임되는지 확인
        assertThat(enhancedToken.getX(), is(1));
        assertThat(enhancedToken.getY(), is(2));
        assertThat(enhancedToken.getType(), is(Type.bridge));
        assertThat(enhancedToken.getState(), is(TOKEN_BRIDGE_STILL));
        
        // World 객체 생성 및 토큰 동작 테스트
        String[] worldText = new String[] {
            "    ",
            "    ",
            "  r ",
            "####"
        };
        World world = TextWorldManip.createWorld(worldText);
        enhancedToken.behave(world, 1, 2);
    }

    @Test
    public void test_duration_decorator() {
        TokenComponent baseToken = new BaseToken(1, 2, Type.bridge);
        DurationDecorator durationToken = new DurationDecorator(baseToken, baseToken.getX(), baseToken.getY(), baseToken.getState(), 3);
        
        assertThat(durationToken.getRemainingDuration(), is(3));
        assertThat(durationToken.getState(), is(TOKEN_BRIDGE_STILL));
        
        // World 객체 생성 및 토큰 동작 테스트
        String[] worldText = new String[] {
            "    ",
            "    ",
            "  r ",
            "####"
        };
        World world = TextWorldManip.createWorld(worldText);
        
        // 토큰을 3번 동작시키고 지속시간이 감소하는지 확인
        durationToken.behave(world, 1, 2);
        assertThat(durationToken.getRemainingDuration(), is(2));
        
        durationToken.behave(world, 1, 2);
        assertThat(durationToken.getRemainingDuration(), is(1));
        
        durationToken.behave(world, 1, 2);
        assertThat(durationToken.getRemainingDuration(), is(0));
    }

    @Test
    public void test_special_effect_decorator() {
        TokenComponent baseToken = new BaseToken(1, 2, Type.explode);
        TokenComponent specialToken = new SpecialEffectDecorator(baseToken, baseToken.getX(), baseToken.getY(), baseToken.getState(), "explosion");
        
        // 기본 속성들이 제대로 위임되는지 확인
        assertThat(specialToken.getX(), is(1));
        assertThat(specialToken.getY(), is(2));
        assertThat(specialToken.getType(), is(Type.explode));
        assertThat(specialToken.getState(), is(TOKEN_EXPLODE_STILL));
        
        // World 객체 생성 및 토큰 동작 테스트
        String[] worldText = new String[] {
            "    ",
            "    ",
            "  r ",
            "####"
        };
        World world = TextWorldManip.createWorld(worldText);
        specialToken.behave(world, 1, 2);
    }

    @Test
    public void test_multiple_decorators() {
        TokenComponent baseToken = new BaseToken(1, 2, Type.bridge);
        TokenComponent enhancedToken = new EnhancedRangeDecorator(baseToken, baseToken.getX(), baseToken.getY(), baseToken.getState(), 2);
        TokenComponent durationToken = new DurationDecorator(enhancedToken, baseToken.getX(), baseToken.getY(), baseToken.getState(), 3);
        TokenComponent specialToken = new SpecialEffectDecorator(durationToken, baseToken.getX(), baseToken.getY(), baseToken.getState(), "explosion");
        
        // 모든 데코레이터를 거친 후에도 기본 속성들이 제대로 위임되는지 확인
        assertThat(specialToken.getX(), is(1));
        assertThat(specialToken.getY(), is(2));
        assertThat(specialToken.getType(), is(Type.bridge));
        assertThat(specialToken.getState(), is(TOKEN_BRIDGE_STILL));
        
        // World 객체 생성 및 토큰 동작 테스트
        String[] worldText = new String[] {
            "    ",
            "    ",
            "  r ",
            "####"
        };
        World world = TextWorldManip.createWorld(worldText);
        specialToken.behave(world, 1, 2);
    }

    @Test
    public void test_token_initial_state() {
        TokenComponent baseToken = new BaseToken(1, 2, Type.bridge);
        assertThat(baseToken.getState(), is(TOKEN_BRIDGE_STILL));

        TokenComponent explodeToken = new BaseToken(1, 2, Type.explode);
        assertThat(explodeToken.getState(), is(TOKEN_EXPLODE_STILL));
    }
}
