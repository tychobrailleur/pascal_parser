package pascal_parser.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void testNullCollection() {
        String example = StringUtils.join(null, null);
        assertNull(example);
    }

    @Test
    public void testJoinWithSeparator() {
        List<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");
        list.add("third");
        String example = StringUtils.join(list, "~");
        assertEquals("first~second~third", example);
    }
}