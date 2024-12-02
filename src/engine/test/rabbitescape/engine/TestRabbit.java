package rabbitescape.engine;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static rabbitescape.engine.ChangeDescription.State.*;

import org.junit.Test;

public class TestRabbit
{
    @Test
    public void Rabbit_reports_state_in_lowercase()
    {
        Rabbit r = new Rabbit( 1, 1, Direction.LEFT, Rabbit.Type.RABBIT );
        r.state = RABBIT_WALKING_LEFT;
        assertThat(r.stateName(), equalTo("rabbit_walking_left"));
    }

    @Test
    public void Rabbot_reports_state_except_it_says_rabbot()
    {
        Rabbit r = new Rabbit( 1, 1, Direction.LEFT, Rabbit.Type.RABBOT );
        r.state = RABBIT_WALKING_LEFT;
        assertThat(r.stateName(), equalTo("rabbot_walking_left"));
    }
    
    @Test
    public void rabbit_creation() throws CloneNotSupportedException {
    	
        final int TEST_COUNT = 100000; // 생성할 Rabbit 객체 수

        // 시간 측정 시작
        long startTime = System.nanoTime();

        for (int i = 0; i < TEST_COUNT; i++) {
            Rabbit rabbit = new Rabbit(i, i, Direction.LEFT, Rabbit.Type.RABBIT);
        }

        // 시간 측정 종료
        long endTime = System.nanoTime();

        // 결과 출력
        long elapsedTime = endTime - startTime;
        System.out.println("Time taken to create " + TEST_COUNT + " Rabbit objects: " 
                           + elapsedTime / 1_000_000.0 + " ms");
    	

        	// 시간 측정 시작
        startTime = System.nanoTime();
        Rabbit rabbit = new Rabbit(0, 0, Direction.LEFT, Rabbit.Type.RABBIT);

        for (int i = 0; i < TEST_COUNT; i++) {
        	rabbit = (Rabbit) rabbit.clone();
        }

        // 시간 측정 종료
       endTime = System.nanoTime();

        // 결과 출력
        elapsedTime = endTime - startTime;
        System.out.println("Time taken to create " + TEST_COUNT + " Rabbit objects: " 
                           + elapsedTime / 1_000_000.0 + " ms");
        
    }
 
}
