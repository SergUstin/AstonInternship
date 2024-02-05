package queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleQueueTest {

    private SimpleQueue<String> queue;

    @BeforeEach // Да, или можно через BeforeEach
    void setQueue() {
        queue = new SimpleQueue<>();
    }

    @Test
    void testPull_ShouldShowsElement_WhenQueueHasElements() {
        queue.put("Alpha");
        queue.put("Beta");
        queue.put("Gamma");
        queue.put("Delta");

        assertEquals(4, queue.size());

        assertEquals("Alpha", queue.pull());
        assertEquals(3, queue.size());
    }

    @Test
    void testGet_ShouldGetElement_WhenQueueIsNull() {
        assertNull(queue.pull());
        assertEquals(0, queue.size());
    }

    @Test
    void testPeek_ShouldPeekElement_WhenQueueHasElements() {
        queue.put("Alpha");
        queue.put("Beta");
        queue.put("Gamma");
        queue.put("Delta");

        assertEquals(4, queue.size());

        assertEquals("Alpha", queue.peek());
        assertEquals(4, queue.size());

    }

    @Test
    void testGetPeek_ShouldGetElement_WhenQueueIsNull() {
        assertNull(queue.peek());
        assertEquals(0, queue.size());
    }

    @Test
    void testPut_ShouldThrowIllegalArgumentEx_WhenArgsNull() {
        assertThrows(IllegalArgumentException.class, () -> queue.put(null));
    }

    @Test
    void testPut_ShouldThrowExTextMessage_WhenArgsNull() {
        try {
            queue.put(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Args not been null", e.getMessage());
        }
    }

    @Test
    void testPutMessage_ShouldThrowExTextMessage_WhenArgsNull() {
        try {
            queue.put(null);
            fail("Ex can not be throw");
        } catch (IllegalArgumentException e) {
            assertEquals("Args not been null", e.getMessage());
        }
    }
}