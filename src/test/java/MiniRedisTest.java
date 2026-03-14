import com.sun.tools.javac.Main;
import com.valenvalag.protocol.CommandParser;
import com.valenvalag.store.Store;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MiniRedisTest {

    @Test
    void testSetAndGet () {
        Store.store.clear();

        String response = CommandParser.processCommand("SET name Valentín 100");
        assertEquals("OK", response);

        String value = CommandParser.processCommand("GET name");
        assertEquals("Valentín", value);
    }

    @Test
    void testDelete() {
        Store.store.clear();

        CommandParser.processCommand("SET ciudad Alicante 100");
        String response = CommandParser.processCommand("DEL ciudad");
        assertEquals("OK", response);

        String value = CommandParser.processCommand("GET ciudad");
        assertEquals("NULL", value);
    }

    @Test
    void testTTLExpiration() throws InterruptedException {

        Store.store.clear();

        CommandParser.processCommand("SET token abc 1");
        Thread.sleep(1500);

        String value = CommandParser.processCommand("GET token");
        assertEquals("NULL", value);

        CommandParser.processCommand("SET token abc 10");
        Thread.sleep(1000);

        String ttl = CommandParser.processCommand("TTL token");
        assertEquals("9", ttl);
    }

    @Test
    void testSomeErrors() {
        Store.store.clear();
        String response = CommandParser.processCommand("SET");
        assertEquals("ERROR: no key specified!", response);

        String response2 = CommandParser.processCommand("SET abc");
        assertEquals("ERROR: no value specified! Use SET <key> <value> <expiresIn>", response2);

        String response3 = CommandParser.processCommand("SET abc bca");
        assertEquals("ERROR: no expires in time specified! Use SET <key> <value> <expiresIn>", response3);

        String response4 = CommandParser.processCommand("SET abc bca fkf");
        assertEquals("ERROR: ttl must be a number", response4);

        String response5 = CommandParser.processCommand("unknown command");
        assertEquals("ERROR: unknown command!", response5);

        String value = CommandParser.processCommand("DEL pruebaNoExists");
        assertEquals("ERROR: Entry does not exist!", value);
    }
}
