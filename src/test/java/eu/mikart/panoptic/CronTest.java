package eu.mikart.panoptic;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import eu.mikart.panoptic.timed.CronParser;

/**
 * Comprehensive JUnit test class for CronParser functionality
 */
@DisplayName("CronParser Tests")
public class CronTest {

    @Nested
    @DisplayName("Basic Expression Tests")
    class BasicExpressionTests {
        
        @Test
        @DisplayName("Daily at midnight should be valid")
        void testDailyMidnight() {
            assertTrue(CronParser.isValid("0 0 * * *"));
        }
        
        @Test
        @DisplayName("Daily at 12:30 should be valid")
        void testDailyNoon() {
            assertTrue(CronParser.isValid("30 12 * * *"));
        }
        
        @Test
        @DisplayName("Specific date/time should be valid")
        void testSpecificDateTime() {
            assertTrue(CronParser.isValid("15 9 1 1 *"));
        }
        
        @Test
        @DisplayName("Monthly on 1st should be valid")
        void testMonthlyFirst() {
            assertTrue(CronParser.isValid("0 0 1 * *"));
        }
        
        @Test
        @DisplayName("January only should be valid")
        void testJanuaryOnly() {
            assertTrue(CronParser.isValid("0 0 * 1 *"));
        }
        
        @Test
        @DisplayName("Sunday only should be valid")
        void testSundayOnly() {
            assertTrue(CronParser.isValid("0 0 * * 0"));
        }
    }

    @Nested
    @DisplayName("Wildcard Expression Tests")
    class WildcardExpressionTests {
        
        @Test
        @DisplayName("All wildcards should be valid")
        void testAllWildcards() {
            assertTrue(CronParser.isValid("* * * * *"));
        }
        
        @Test
        @DisplayName("Wildcard minute should be valid")
        void testWildcardMinute() {
            assertTrue(CronParser.isValid("* 0 * * *"));
        }
        
        @Test
        @DisplayName("Wildcard hour should be valid")
        void testWildcardHour() {
            assertTrue(CronParser.isValid("0 * * * *"));
        }
        
        @Test
        @DisplayName("Wildcard day/month/dow should be valid")
        void testWildcardDayMonthDow() {
            assertTrue(CronParser.isValid("0 0 * * *"));
        }
    }

    @Nested
    @DisplayName("Range Expression Tests")
    class RangeExpressionTests {
        
        @Test
        @DisplayName("Minute range should be valid")
        void testMinuteRange() {
            assertTrue(CronParser.isValid("0-59 * * * *"));
        }
        
        @Test
        @DisplayName("Hour range should be valid")
        void testHourRange() {
            assertTrue(CronParser.isValid("* 0-23 * * *"));
        }
        
        @Test
        @DisplayName("Day range should be valid")
        void testDayRange() {
            assertTrue(CronParser.isValid("* * 1-31 * *"));
        }
        
        @Test
        @DisplayName("Month range should be valid")
        void testMonthRange() {
            assertTrue(CronParser.isValid("* * * 1-12 *"));
        }
        
        @Test
        @DisplayName("Day of week range should be valid")
        void testDayOfWeekRange() {
            assertTrue(CronParser.isValid("* * * * 0-6"));
        }
        
        @Test
        @DisplayName("Business hours weekdays should be valid")
        void testBusinessHours() {
            assertTrue(CronParser.isValid("0 9-17 * * 1-5"));
        }
    }

    @Nested
    @DisplayName("List Expression Tests")
    class ListExpressionTests {
        
        @Test
        @DisplayName("Minute list should be valid")
        void testMinuteList() {
            assertTrue(CronParser.isValid("0,30 * * * *"));
        }
        
        @Test
        @DisplayName("Hour list should be valid")
        void testHourList() {
            assertTrue(CronParser.isValid("* 9,12,15 * * *"));
        }
        
        @Test
        @DisplayName("Day list should be valid")
        void testDayList() {
            assertTrue(CronParser.isValid("* * 1,15 * *"));
        }
        
        @Test
        @DisplayName("Month list should be valid")
        void testMonthList() {
            assertTrue(CronParser.isValid("* * * 1,6,12 *"));
        }
        
        @Test
        @DisplayName("Day of week list should be valid")
        void testDayOfWeekList() {
            assertTrue(CronParser.isValid("* * * * 1,3,5"));
        }
        
        @Test
        @DisplayName("Complex list should be valid")
        void testComplexList() {
            assertTrue(CronParser.isValid("15,45 9,17 * * 1,2,3,4,5"));
        }
    }

    @Nested
    @DisplayName("Step Expression Tests")
    class StepExpressionTests {
        
        @Test
        @DisplayName("Every 5 minutes should be valid")
        void testEveryFiveMinutes() {
            assertTrue(CronParser.isValid("*/5 * * * *"));
        }
        
        @Test
        @DisplayName("Every 2 hours should be valid")
        void testEveryTwoHours() {
            assertTrue(CronParser.isValid("* */2 * * *"));
        }
        
        @Test
        @DisplayName("Every 7 days should be valid")
        void testEverySevenDays() {
            assertTrue(CronParser.isValid("* * */7 * *"));
        }
        
        @Test
        @DisplayName("Every 3 months should be valid")
        void testEveryThreeMonths() {
            assertTrue(CronParser.isValid("* * * */3 *"));
        }
        
        @Test
        @DisplayName("Every 2 days of week should be valid")
        void testEveryTwoDaysOfWeek() {
            assertTrue(CronParser.isValid("* * * * */2"));
        }
        
        @Test
        @DisplayName("Range with step should be valid")
        void testRangeWithStep() {
            assertTrue(CronParser.isValid("0 9-17/2 * * *"));
        }
    }

    @Nested
    @DisplayName("Day of Week Expression Tests")
    class DayOfWeekExpressionTests {
        
        @Test
        @DisplayName("Sunday name should be valid")
        void testSundayName() {
            assertTrue(CronParser.isValid("0 0 * * SUN"));
        }
        
        @Test
        @DisplayName("Monday name should be valid")
        void testMondayName() {
            assertTrue(CronParser.isValid("0 0 * * MON"));
        }
        
        @Test
        @DisplayName("Tuesday name should be valid")
        void testTuesdayName() {
            assertTrue(CronParser.isValid("0 0 * * TUE"));
        }
        
        @Test
        @DisplayName("Wednesday name should be valid")
        void testWednesdayName() {
            assertTrue(CronParser.isValid("0 0 * * WED"));
        }
        
        @Test
        @DisplayName("Thursday name should be valid")
        void testThursdayName() {
            assertTrue(CronParser.isValid("0 0 * * THU"));
        }
        
        @Test
        @DisplayName("Friday name should be valid")
        void testFridayName() {
            assertTrue(CronParser.isValid("0 0 * * FRI"));
        }
        
        @Test
        @DisplayName("Saturday name should be valid")
        void testSaturdayName() {
            assertTrue(CronParser.isValid("0 0 * * SAT"));
        }
        
        @Test
        @DisplayName("Multiple day names should be valid")
        void testMultipleDayNames() {
            assertTrue(CronParser.isValid("0 0 * * MON,WED,FRI"));
        }
    }

    @Nested
    @DisplayName("Invalid Expression Tests")
    class InvalidExpressionTests {
        
        @Test
        @DisplayName("Empty string should be invalid")
        void testEmptyString() {
            assertFalse(CronParser.isValid(""));
        }
        
        @Test
        @DisplayName("Null should be invalid")
        void testNull() {
            assertFalse(CronParser.isValid(null));
        }
        
        @Test
        @DisplayName("Missing field should be invalid")
        void testMissingField() {
            assertFalse(CronParser.isValid("0 0 * *"));
        }
        
        @Test
        @DisplayName("Extra field should be invalid")
        void testExtraField() {
            assertFalse(CronParser.isValid("0 0 * * * *"));
        }
        
        @Test
        @DisplayName("Invalid minute (60) should be invalid")
        void testInvalidMinute() {
            assertFalse(CronParser.isValid("60 0 * * *"));
        }
        
        @Test
        @DisplayName("Invalid hour (24) should be invalid")
        void testInvalidHour() {
            assertFalse(CronParser.isValid("0 24 * * *"));
        }
        
        @Test
        @DisplayName("Invalid day (32) should be invalid")
        void testInvalidDay() {
            assertFalse(CronParser.isValid("0 0 32 * *"));
        }
        
        @Test
        @DisplayName("Invalid month (13) should be invalid")
        void testInvalidMonth() {
            assertFalse(CronParser.isValid("0 0 * 13 *"));
        }
        
        @Test
        @DisplayName("Invalid day of week (7) should be invalid")
        void testInvalidDayOfWeek() {
            assertFalse(CronParser.isValid("0 0 * * 7"));
        }
        
        @Test
        @DisplayName("Random text should be invalid")
        void testRandomText() {
            assertFalse(CronParser.isValid("invalid expression"));
        }
    }

    @Nested
    @DisplayName("Time Matching Tests")
    class TimeMatchingTests {
        
        @Test
        @DisplayName("Midnight should match daily at midnight")
        void testMidnightMatch() {
            LocalDateTime midnight = LocalDateTime.of(2025, 1, 1, 0, 0);
            assertTrue(CronParser.matches("0 0 * * *", midnight));
        }
        
        @Test
        @DisplayName("Midnight should match all wildcards")
        void testMidnightWildcardMatch() {
            LocalDateTime midnight = LocalDateTime.of(2025, 1, 1, 0, 0);
            assertTrue(CronParser.matches("* * * * *", midnight));
        }
        
        @Test
        @DisplayName("Midnight should not match noon")
        void testMidnightNoonMismatch() {
            LocalDateTime midnight = LocalDateTime.of(2025, 1, 1, 0, 0);
            assertFalse(CronParser.matches("0 12 * * *", midnight));
        }
        
        @Test
        @DisplayName("Noon should match daily at noon")
        void testNoonMatch() {
            LocalDateTime noon = LocalDateTime.of(2025, 1, 1, 12, 0);
            assertTrue(CronParser.matches("0 12 * * *", noon));
        }
        
        @Test
        @DisplayName("Noon should not match midnight")
        void testNoonMidnightMismatch() {
            LocalDateTime noon = LocalDateTime.of(2025, 1, 1, 12, 0);
            assertFalse(CronParser.matches("0 0 * * *", noon));
        }
        
        @Test
        @DisplayName("5 minutes should match every 5 minutes")
        void testFiveMinuteMatch() {
            LocalDateTime fiveMinutes = LocalDateTime.of(2025, 1, 1, 0, 5);
            assertTrue(CronParser.matches("*/5 * * * *", fiveMinutes));
        }
        
        @Test
        @DisplayName("5 minutes should match minute 5")
        void testSpecificMinuteMatch() {
            LocalDateTime fiveMinutes = LocalDateTime.of(2025, 1, 1, 0, 5);
            assertTrue(CronParser.matches("5 * * * *", fiveMinutes));
        }
        
        @Test
        @DisplayName("5 minutes should not match every 10 minutes")
        void testFiveMinuteTenMinuteMismatch() {
            LocalDateTime fiveMinutes = LocalDateTime.of(2025, 1, 1, 0, 5);
            assertFalse(CronParser.matches("*/10 * * * *", fiveMinutes));
        }
        
        @Test
        @DisplayName("Monday should match day 1")
        void testMondayNumericMatch() {
            LocalDateTime monday = LocalDateTime.of(2025, 1, 6, 0, 0); // Jan 6, 2025 is a Monday
            assertTrue(CronParser.matches("0 0 * * 1", monday));
        }
        
        @Test
        @DisplayName("Monday should match MON")
        void testMondayNameMatch() {
            LocalDateTime monday = LocalDateTime.of(2025, 1, 6, 0, 0); // Jan 6, 2025 is a Monday
            assertTrue(CronParser.matches("0 0 * * MON", monday));
        }
        
        @Test
        @DisplayName("Monday should not match Sunday (0)")
        void testMondaySundayMismatch() {
            LocalDateTime monday = LocalDateTime.of(2025, 1, 6, 0, 0); // Jan 6, 2025 is a Monday
            assertFalse(CronParser.matches("0 0 * * 0", monday));
        }
    }

    @Nested
    @DisplayName("Next Execution Tests")
    class NextExecutionTests {
        
        @Test
        @DisplayName("Should find next midnight")
        void testNextMidnight() {
            LocalDateTime now = LocalDateTime.of(2025, 1, 1, 12, 30);
            LocalDateTime nextMidnight = CronParser.getNextExecution("0 0 * * *", now);
            
            assertNotNull(nextMidnight);
            assertEquals(0, nextMidnight.getHour());
            assertEquals(0, nextMidnight.getMinute());
        }
        
        @Test
        @DisplayName("Should find next 5-minute interval")
        void testNextFiveMinuteInterval() {
            LocalDateTime now = LocalDateTime.of(2025, 1, 1, 12, 30);
            LocalDateTime nextFiveMin = CronParser.getNextExecution("*/5 * * * *", now);
            
            assertNotNull(nextFiveMin);
            assertEquals(0, nextFiveMin.getMinute() % 5);
        }
        
        @Test
        @DisplayName("Should find next Sunday")
        void testNextSunday() {
            LocalDateTime now = LocalDateTime.of(2025, 1, 1, 12, 30);
            LocalDateTime nextSunday = CronParser.getNextExecution("0 0 * * 0", now);
            
            assertNotNull(nextSunday);
            assertEquals(DayOfWeek.SUNDAY, nextSunday.getDayOfWeek());
        }
        
        @Test
        @DisplayName("Should calculate seconds until next execution")
        void testSecondsUntilNext() {
            LocalDateTime now = LocalDateTime.of(2025, 1, 1, 12, 30);
            long seconds = CronParser.getSecondsUntilNext("0 0 * * *", now);
            
            assertTrue(seconds > 0);
        }
        
        @Test
        @DisplayName("Should calculate ticks until next execution")
        void testTicksUntilNext() {
            LocalDateTime now = LocalDateTime.of(2025, 1, 1, 12, 30);
            long ticks = CronParser.getTicksUntilNext("0 0 * * *", now);
            
            assertTrue(ticks > 0);
            // Ticks should be 20 times the seconds
            long seconds = CronParser.getSecondsUntilNext("0 0 * * *", now);
            assertEquals(seconds * 20, ticks);
        }
    }

    @Nested
    @DisplayName("Description Tests")
    class DescriptionTests {
        
        @Test
        @DisplayName("Daily midnight description")
        void testDailyMidnightDescription() {
            assertEquals("Daily at midnight", CronParser.describe("0 0 * * *"));
        }
        
        @Test
        @DisplayName("Daily noon description")
        void testDailyNoonDescription() {
            assertEquals("Daily at noon", CronParser.describe("0 12 * * *"));
        }
        
        @Test
        @DisplayName("Weekly Sunday description")
        void testWeeklySundayDescription() {
            assertEquals("Weekly on Sunday at midnight", CronParser.describe("0 0 * * 0"));
        }
        
        @Test
        @DisplayName("Monthly description")
        void testMonthlyDescription() {
            assertEquals("Monthly on the 1st at midnight", CronParser.describe("0 0 1 * *"));
        }
        
        @Test
        @DisplayName("Invalid expression should return error message")
        void testInvalidExpressionDescription() {
            String invalidDesc = CronParser.describe("invalid");
            assertTrue(invalidDesc.contains("Invalid"));
        }
        
        @Test
        @DisplayName("Null expression should return error message")
        void testNullExpressionDescription() {
            String nullDesc = CronParser.describe(null);
            assertTrue(nullDesc.contains("Invalid"));
        }
        
        @Test
        @DisplayName("Empty expression should return error message")
        void testEmptyExpressionDescription() {
            String emptyDesc = CronParser.describe("");
            assertTrue(emptyDesc.contains("Invalid"));
        }
    }
}
