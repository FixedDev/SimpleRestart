import net.minebukket.restart.util.DateUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateUtilTest {

    @Test
    public void testDateParse() {
        String relativeDate = "15s";
        long relativeTime = DateUtil.parseRelativeHumanTime(relativeDate);

        assertEquals(15000, relativeTime);
    }

    @Test
    public void testDateReverseParse() {
        String relativeDate = DateUtil.getRelativeHumanTime(15000);

        assertEquals("15s", relativeDate);
    }
}
