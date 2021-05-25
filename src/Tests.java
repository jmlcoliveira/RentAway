import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

public class Tests {

	//modificar estas linhas com os nomes dos testes que forem para usar
	@Test public void test01() { test("01_in_help_exit_all.txt","01_out_help_exit_all.txt"); }
	@Test public void test02() { test("02_in_users_base.txt","02_out_users_base.txt"); }
	@Test public void test03() { test("03_in_users_pre.txt","03_out_users_pre.txt"); }
	@Test public void test04() { test("04_in_properties_base.txt","04_out_properties_base.txt"); }
	@Test public void test05() { test("05_in_properties_pre.txt","05_out_properties_pre.txt"); }
	@Test public void test06() { test("06_in_book_base.txt","06_out_book_base.txt"); }
	@Test public void test07() { test("07_in_book_pre.txt","07_out_book_pre.txt"); }
	@Test public void test08() { test("7_in_test.txt","7_out_test.txt"); }
	@Test public void test09() { test("7_in_test.txt","7_out_test.txt"); }
	@Test public void test10() { test("7_in_test.txt","7_out_test.txt"); }
	@Test public void test11() { test("7_in_test.txt","7_out_test.txt"); }
	@Test public void test12() { test("7_in_test.txt","7_out_test.txt"); }
	@Test public void test13() { test("7_in_test.txt","7_out_test.txt"); }
	@Test public void test14() { test("7_in_test.txt","7_out_test.txt"); }
	@Test public void test15() { test("7_in_test.txt","7_out_test.txt"); }
	@Test public void test16() { test("7_in_test.txt","7_out_test.txt"); }
	@Test public void test17() { test("7_in_test.txt","7_out_test.txt"); }
	@Test public void test18() { test("7_in_test.txt","7_out_test.txt"); }
	@Test public void test19() { test("7_in_test.txt","7_out_test.txt"); }
	@Test public void test20() { test("7_in_test.txt","7_out_test.txt"); }

	//nome da pasta em que os testes devem estar
	private static final File BASE = new File("tests");

	private PrintStream consoleStream;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setup() {
		consoleStream = System.out;
		System.setOut(new PrintStream(outContent));
	}

	public void test(String intput, String output) {
		test(new File(BASE, intput), new File(BASE, output));
	}

	public void test(File input, File output) {
		consoleStream.println("Testing!");
		consoleStream.println("Input: " + input.getAbsolutePath());
		consoleStream.println("Output: " + output.getAbsolutePath());

		String fullInput = "", fullOutput = "";
		try {
			fullInput = new String(Files.readAllBytes(input.toPath()));
			fullOutput = new String(Files.readAllBytes(output.toPath()));
			consoleStream.println("INPUT ============");
			consoleStream.println(new String(fullInput));
			consoleStream.println("OUTPUT ESPERADO =============");
			consoleStream.println(new String(fullOutput));
			consoleStream.println("OUTPUT =============");
		} catch(Exception e) {
			e.printStackTrace();
			fail("Erro a ler o ficheiro");
		}

		try {
			Locale.setDefault(Locale.US);
			System.setIn(new FileInputStream(input));
			Class<?> mainClass = Class.forName("Main");
			mainClass.getMethod("main", String[].class).invoke(null, new Object[] { new String[0] });
		} catch (Exception e) {
			e.printStackTrace();
			fail("Erro no programa");
		} finally {
			consoleStream.println(outContent.toString());

			assertEquals(removeCarriages(fullOutput), removeCarriages(outContent.toString()));
		}
	}

	private static String removeCarriages(String s) {
		return s.replaceAll("\r\n", "\n");
	}

}
