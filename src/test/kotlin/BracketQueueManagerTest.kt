import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import ru.kipolad.bracketpro.BracketQueueManager

class BracketQueueManagerTest {

    @Test
    fun indexNoneTest() {
        assertEquals(0, BracketQueueManager.INDEX_NONE, "INDEX_NONE is not zero")
    }

    @Test
    fun isBracketCharTest() {
        val queueManager = BracketQueueManager()

        assertAll(
            { assertTrue(queueManager.isBracketChar('('), "isBracketChar does not accept '('") },
            { assertTrue(queueManager.isBracketChar(')'), "isBracketChar does not accept ')'") },
            { assertTrue(queueManager.isBracketChar(']'), "isBracketChar does not accept ']'") },
            { assertTrue(queueManager.isBracketChar('['), "isBracketChar does not accept '['") },
            { assertTrue(queueManager.isBracketChar('}'), "isBracketChar does not accept '}'") },
            { assertTrue(queueManager.isBracketChar('{'), "isBracketChar does not accept '{'") },
            { assertTrue(queueManager.isBracketChar('>'), "isBracketChar does not accept '>'") },
            { assertTrue(queueManager.isBracketChar('<'), "isBracketChar does not accept '<'") },
            { assertFalse(queueManager.isBracketChar('h'), "isBracketChar returns true when char == 'h'") },
            { assertFalse(queueManager.isBracketChar('1'), "isBracketChar returns true when char == '1'") },
        )
    }

    @Test
    fun isBracketNormalFirstRoundCloseBracketTest() {
        val queueManager = BracketQueueManager()

        assertAll(
            {
                assertFalse(
                    queueManager.isBracketNormal(')', 1),
                    "isBracketNormal returns true, if the first bracket is rounded closing"
                )
            },
            {
                assertEquals(
                    2, queueManager.getWrongIndex(),
                    "getWrongIndex() returns wrong index"
                )
            }
        )
    }

    @Test
    fun isBracketNormalFirstSquareCloseBracketTest() {
        val queueManager = BracketQueueManager()

        assertAll(
            {
                assertFalse(
                    queueManager.isBracketNormal(']', 0),
                    "isBracketNormal returns true, if the first bracket is a squared closing"
                )
            },
            { assertEquals(1, queueManager.getWrongIndex(), "getWrongIndex() returns wrong index") }
        )
    }

    @Test
    fun isBracketNormalFirstCrochetCloseBracketTest() {
        val queueManager = BracketQueueManager()

        assertAll(
            {
                assertFalse(
                    queueManager.isBracketNormal('}', 10),
                    "isBracketNormal returns true, if the first bracket is crocheted closing"
                )
            },
            { assertEquals(11, queueManager.getWrongIndex(), "getWrongIndex() returns wrong index") }
        )
    }

    @Test
    fun isBracketNormalFirstAngleCloseBracketTest() {
        val queueManager = BracketQueueManager()

        assertAll(
            {
                assertFalse(
                    queueManager.isBracketNormal('>', 182),
                    "isBracketNormal returns true, if the first bracket is angled closing"
                )
            },
            { assertEquals(183, queueManager.getWrongIndex(), "getWrongIndex() returns wrong index") }
        )
    }

    @Test
    fun isBracketNormalTrueTest() {
        val queueManager = BracketQueueManager()

        assertAll(
            {
                assertTrue(
                    queueManager.isBracketNormal('(', 10),
                    "isBracketNormal returns false, when brackets are angled"
                )
            },
            {
                assertTrue(
                    queueManager.isBracketNormal('[', 10),
                    "isBracketNormal returns false, when brackets are crocheted"
                )
            },
            {
                assertTrue(
                    queueManager.isBracketNormal('{', 10),
                    "isBracketNormal returns false, when brackets are squared"
                )
            },
            {
                assertTrue(
                    queueManager.isBracketNormal('<', 10),
                    "isBracketNormal returns false, when brackets are rounded"
                )
            },
            {
                assertTrue(
                    queueManager.isBracketNormal('>', 10),
                    "isBracketNormal returns false, when brackets are angled"
                )
            },
            {
                assertTrue(
                    queueManager.isBracketNormal('}', 10),
                    "isBracketNormal returns false, when brackets are crocheted"
                )
            },
            {
                assertTrue(
                    queueManager.isBracketNormal(']', 10),
                    "isBracketNormal returns false, when brackets are squared"
                )
            },
            {
                assertTrue(
                    queueManager.isBracketNormal(')', 10),
                    "isBracketNormal returns false, when brackets are rounded"
                )
            }
        )
    }

    @ParameterizedTest(name = "strings with correct brackets should return \"Success\"")
    @ValueSource(strings = ["(){12}(po1)(<12>)", "{12}([w])1", "({12}(po1)<12>)", "123-{1}"])
    fun successTest(testString: String) {
        val queueManager = BracketQueueManager()
        val chars = testString.toCharArray()
        var isNormal = true
        var result = "not success"
        var index: Int
        for (i in chars.indices) {
            if (queueManager.isBracketChar(chars[i])) {
                isNormal = queueManager.isBracketNormal(chars[i], i)
                if (!isNormal) {
                    index = queueManager.getWrongIndex()
                    break
                }
            }
        }
        if (isNormal) {
            index = queueManager.getWrongIndex()
            if (index == BracketQueueManager.INDEX_NONE) {
                result = "Success"
            }
        }
        assertEquals(result, "Success")
    }

    @ParameterizedTest(name = "strings with wrong brackets should return wrong bracket index")
    @CsvSource("{[}, 3", "([]))[, 5", "foo(bar[i), 10", ")[1]2[3](, 1")
    fun wrongIndexTest(testString: String, expect: Int) {
        val queueManager = BracketQueueManager()
        val chars = testString.toCharArray()
        var isNormal = true
        var result = "not success"
        var index = 0
        for (i in chars.indices) {
            if (queueManager.isBracketChar(chars[i])) {
                isNormal = queueManager.isBracketNormal(chars[i], i)
                if (!isNormal) {
                    index = queueManager.getWrongIndex()
                    break
                }
            }
        }
        if (isNormal) {
            index = queueManager.getWrongIndex()
            if (index == BracketQueueManager.INDEX_NONE) {
                result = "Success"
            }
        }
        assertEquals(expect, index)
    }
}


