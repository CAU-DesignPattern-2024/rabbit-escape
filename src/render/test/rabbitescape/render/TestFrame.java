package rabbitescape.render;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TestFrame {
    
    @Test
    public void should_create_frame_with_default_values() {
        // Given
        String expectedName = "test_frame";
        
        // When
        Frame frame = new Frame.FrameBuilder(expectedName).build();
        
        // Then
        assertThat(frame.getName()).isEqualTo(expectedName);
        assertThat(frame.getOffsetX()).isEqualTo(0);
        assertThat(frame.getOffsetY()).isEqualTo(0);
        assertThat(frame.getSoundEffect()).isNull();
    }
    
    @Test
    public void should_create_frame_with_custom_values() {
        // Given
        String expectedName = "test_frame";
        int expectedOffsetX = 10;
        int expectedOffsetY = 20;
        String expectedSound = "boom";
        
        // When
        Frame frame = new Frame.FrameBuilder(expectedName)
            .offsetX(expectedOffsetX)
            .offsetY(expectedOffsetY)
            .soundEffect(expectedSound)
            .build();
            
        // Then
        assertThat(frame.getName()).isEqualTo(expectedName);
        assertThat(frame.getOffsetX()).isEqualTo(expectedOffsetX);
        assertThat(frame.getOffsetY()).isEqualTo(expectedOffsetY);
        assertThat(frame.getSoundEffect()).isEqualTo(expectedSound);
    }
    
    @Test
    public void should_maintain_immutability_when_builder_changes() {
        // Given
        Frame.FrameBuilder builder = new Frame.FrameBuilder("test_frame")
            .offsetX(10)
            .offsetY(20);
        Frame frame1 = builder.build();
        
        // When
        builder.offsetX(30).offsetY(40);
        Frame frame2 = builder.build();
        
        // Then
        assertThat(frame1.getOffsetX()).isEqualTo(10);
        assertThat(frame1.getOffsetY()).isEqualTo(20);
        assertThat(frame2.getOffsetX()).isEqualTo(30);
        assertThat(frame2.getOffsetY()).isEqualTo(40);
    }
    
    @Test
    public void should_create_same_frame_regardless_of_builder_method_order() {
        // Given
        String expectedName = "test_frame";
        int expectedOffsetX = 10;
        int expectedOffsetY = 20;
        String expectedSound = "boom";
        
        // When
        Frame frame1 = new Frame.FrameBuilder(expectedName)
            .soundEffect(expectedSound)
            .offsetX(expectedOffsetX)
            .offsetY(expectedOffsetY)
            .build();
            
        Frame frame2 = new Frame.FrameBuilder(expectedName)
            .offsetY(expectedOffsetY)
            .offsetX(expectedOffsetX)
            .soundEffect(expectedSound)
            .build();
            
        // Then
        assertThat(frame1.getName()).isEqualTo(frame2.getName());
        assertThat(frame1.getOffsetX()).isEqualTo(frame2.getOffsetX());
        assertThat(frame1.getOffsetY()).isEqualTo(frame2.getOffsetY());
        assertThat(frame1.getSoundEffect()).isEqualTo(frame2.getSoundEffect());
    }
    
    @Test
    public void should_reset_optional_values_for_new_builder() {
        // Given
        Frame frame1 = new Frame.FrameBuilder("test_frame")
            .offsetX(10)
            .offsetY(20)
            .soundEffect("boom")
            .build();
        
        // When
        Frame frame2 = new Frame.FrameBuilder("another_frame").build();
        
        // Then
        assertThat(frame2.getOffsetX()).isEqualTo(0);
        assertThat(frame2.getOffsetY()).isEqualTo(0);
        assertThat(frame2.getSoundEffect()).isNull();
    }
}
