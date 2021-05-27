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
	@Test public void test08() { test("08_in_confirm_base.txt","08_out_confirm_base.txt"); }
	@Test public void test09() { test("09_in_confirm_pre.txt","09_out_confirm_pre.txt"); }
	@Test public void test10() { test("10_in_reject_base.txt","10_out_reject_base.txt"); }
	@Test public void test11() { test("11_in_reject_pre.txt","11_out_reject_pre.txt"); }
	@Test public void test12() { test("12_in_rejections_base.txt","12_out_rejections_base.txt"); }
	@Test public void test13() { test("13_in_rejections_pre.txt","13_out_rejections_pre.txt"); }
	@Test public void test14() { test("14_in_pay_base.txt","14_out_pay_base.txt"); }
	@Test public void test15() { test("15_in_pay_pre.txt","15_out_pay_pre.txt"); }
	@Test public void test16() { test("16_in_review_base.txt","16_out_review_base.txt"); }
	@Test public void test17() { test("17_in_review_pre.txt","17_out_review_pre.txt"); }
	@Test public void test18() { test("18_in_guest.txt","18_out_guest.txt"); }
	@Test public void test19() { test("19_in_stays.txt","19_out_stays.txt"); }
	@Test public void test20() { test("20_in_search.txt","20_out_search.txt"); }

	//nome da pasta em que os testes devem estar
	private static final File BASE = new File("tests");

	private PrintStream consoleStream;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setup() {
		consoleStream = System.out;
		System.setOut(new PrintStream(outContent));
	}

	public void test(String input, String output) {
		test(new File(BASE, input), new File(BASE, output));
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
			consoleStream.println(fullInput);
			consoleStream.println("OUTPUT ESPERADO =============");
			consoleStream.println(fullOutput);
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
			consoleStream.println(outContent);

			assertEquals(removeCarriages(fullOutput), removeCarriages(outContent.toString()));
		}
	}

	private static String removeCarriages(String s) {
		return s.replaceAll("\r\n", "\n");
	}

}
