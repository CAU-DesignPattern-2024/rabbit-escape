package rabbitescape.engine.solution;

import static org.junit.Assert.fail;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import rabbitescape.engine.Token;
import rabbitescape.engine.World;
import rabbitescape.engine.World.CompletionState;
import rabbitescape.engine.solution.SolutionExceptions;
import rabbitescape.engine.textworld.TextWorldManip;

public class TestSolutionRunner
{
    @Test( expected = SolutionExceptions.UnexpectedState.class )
    public void Unexpected_state_is_an_error()
    {
        SolutionRunner.runSolution(
            expectingSolution( CompletionState.LOST ), neverEndingWorld() );
    }

    @Test
    public void Unexpected_state_is_serialised_to_helpful_message()
    {
        try
        {
            SolutionRunner.runSolution(
                expectingSolution( CompletionState.LOST ), neverEndingWorld() );
            fail( "Expected exception!" );
        }
        catch( SolutionExceptions.UnexpectedState e )
        {
            e.solutionId = 3;

            assertThat(
                e.getMessage(),
                equalTo(
                    "Solution failed: state was RUNNING but we expected LOST"
                    + " at instruction 1 of solution 3."
                )
            );
        }
    }

    @Test( expected = SolutionExceptions.DidNotWin.class )
    public void Failing_unexpectedly_is_an_error()
    {
        SolutionRunner.runSolution(
            expectingSolution( CompletionState.WON ), neverEndingWorld() );
    }

    @Test
    public void Failing_unexpectedly_is_serialised_to_helpful_message()
    {
        try
        {
            SolutionRunner.runSolution(
                expectingSolution( CompletionState.WON ), neverEndingWorld() );
            fail( "Expected exception!" );
        }
        catch( SolutionExceptions.UnexpectedState e )
        {
            e.solutionId = 4;

            assertThat(
                e.getMessage(),
                equalTo(
                    "Solution failed: We expected to win, but the state was"
                    + " RUNNING at instruction 1 of solution 4."
                )
            );
        }
    }

    @Test( expected = SolutionExceptions.RanPastEnd.class )
    public void Going_on_beyond_the_end_is_an_error()
    {
        SolutionRunner.runSolution( waitFourSolution(), threeStepWorld() );
    }

    @Test
    public void Going_on_beyond_the_end_is_serialised_to_helpful_message()
    {
        try
        {
            SolutionRunner.runSolution( waitFourSolution(), threeStepWorld() );
            fail( "Expected exception!" );
        }
        catch( SolutionExceptions.RanPastEnd e )
        {
            e.solutionId = 5;

            assertThat(
                e.getMessage(),
                equalTo(
                    "Solution failed: world has stopped (state: WON) but"
                    + " there are more solution steps"
                    + " at instruction 3 of solution 5."
                )
            );
        }
    }

    @Test( expected = SolutionExceptions.UsedRunOutAbility.class )
    public void Using_missing_ability_is_an_error()
    {
        SolutionRunner.runSolution(
            useBash30Solution(), neverEndingWorldWithBash() );
    }

    @Test
    public void Using_missing_ability_is_serialised_to_helpful_message()
    {
        try
        {
            SolutionRunner.runSolution(
                useBash30Solution(), neverEndingWorldWithBash() );

            fail( "Expected exception!" );
        }
        catch( SolutionExceptions.UsedRunOutAbility e )
        {
            e.solutionId = 6;

            assertThat(
                e.getMessage(),
                equalTo(
                    "Solution failed: ability 'bash' was used when there"
                    + " were none left at instruction 4 of solution 6."
                )
            );
        }
    }

    @Test( expected = SolutionExceptions.UsedMissingAbility.class )
    public void Using_run_out_ability_is_an_error()
    {
        SolutionRunner.runSolution( useBash30Solution(), neverEndingWorld() );
    }

    @Test
    public void Using_run_out_ability_is_serialised_to_helpful_message()
    {
        try
        {
            SolutionRunner.runSolution(
                useBash30Solution(), neverEndingWorld() );

            fail( "Expected exception!" );
        }
        catch( SolutionExceptions.UsedMissingAbility e )
        {
            e.solutionId = 7;

            assertThat(
                e.getMessage(),
                equalTo(
                    "Solution failed: ability 'bash' was used but this level"
                    + " does not provide it at instruction 2 of solution 7."
                )
            );
        }
    }

    @Test( expected = SolutionExceptions.PlacedTokenOutsideWorld.class )
    public void Placing_a_token_outside_the_world_is_an_error()
    {
        SolutionRunner.runSolution(
            useBash100Solution(), neverEndingWorldWithBash() );
    }

    @Test
    public void Placing_a_token_outside_the_world_is_serialised_nicely()
    {
        try
        {
            SolutionRunner.runSolution(
                useBash100Solution(), neverEndingWorldWithBash() );

            fail( "Expected exception!" );
        }
        catch( SolutionExceptions.PlacedTokenOutsideWorld e )
        {
            e.solutionId = 8;

            assertThat(
                e.getMessage(),
                equalTo(
                    "Solution failed: placed a token at (10, 0) but the"
                    + " world is only 5x2 in size"
                    + " at instruction 2 of solution 8."
                )
            );
        }
    }

    @Test
    public void Rabbit_dying_by_walking_out_of_level_is_not_an_error()
    {
        SolutionRunner.runSolution(
            waitFiveThenLostSolution(),
            TextWorldManip.createWorld(
                "#r   ",
                "#####",
                ":num_rabbits=0",
                ":num_to_save=1"
            )
        );
    }

    @Test( expected = SolutionExceptions.FailedToPlaceToken.class )
    public void Placing_a_token_on_a_block_is_an_error()
    {
        SolutionRunner.runSolution( useBash30Solution(), blockAt30World() );
    }

    @Test
    public void Placing_a_token_on_a_block_is_serialised_nicely()
    {
        try
        {
            SolutionRunner.runSolution( useBash30Solution(), blockAt30World() );

            fail( "Expected exception!" );
        }
        catch( SolutionExceptions.FailedToPlaceToken e )
        {
            e.solutionId = 9;

            assertThat(
                e.getMessage(),
                equalTo(
                    "Solution failed: tried to place a bash token at (3, 0) but"
                    + " a block was already there so it did not place"
                    + " at instruction 4 of solution 9."
                )
            );
        }
    }

    // --

    private World neverEndingWorld()
    {
        return TextWorldManip.createWorld(
            "#r  #",
            "#####"
        );
    }

    private World neverEndingWorldWithBash()
    {
        return TextWorldManip.createWorld(
            "#r  #",
            "#####",
            ":bash=1"
        );
    }

    private World threeStepWorld()
    {
        return TextWorldManip.createWorld(
            "#r O",
            "####",
            ":num_rabbits=0",
            ":num_to_save=1"
        );
    }

    private World blockAt30World()
    {
        return TextWorldManip.createWorld(
            "#r ##",
            "#####",
            ":bash=2"
        );
    }

    private Solution expectingSolution( CompletionState expected )
    {
        return new Solution( new TargetState( expected ) );
    }

    private Solution waitFourSolution()
    {
        return new Solution(
            new WaitInstruction( 1 ),
            new WaitInstruction( 2 ),
            new WaitInstruction( 2 )
        );
    }

    private Solution waitFiveThenLostSolution()
    {
        return new Solution(
            new WaitInstruction( 5 ),
            new TargetState( CompletionState.LOST )
        );
    }

    private Solution useBash30Solution()
    {
        return new Solution(
            new SelectInstruction( Token.Type.bash ),
            new PlaceTokenInstruction( 1, 0 ),
            new WaitInstruction( 1 ),
            new PlaceTokenInstruction( 3, 0 )
        );
    }

    private Solution useBash100Solution()
    {
        return new Solution(
            new SelectInstruction( Token.Type.bash ),
            new PlaceTokenInstruction( 10, 0 )
        );
    }
}
