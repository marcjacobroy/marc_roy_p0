package FlashCard.pojos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// Test the update percentage method of Card 
public class CardTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void updateTrueTest() {
		Entry term = new Entry("term");
		Card card = new Card(term, term);
		card.updatePercentage(true);
		double expectedOutput = 0.5;
		double actualOutput = card.getPercentage();
		double precisionLoss = 0.01;
		assertEquals(expectedOutput, actualOutput, precisionLoss);
	}
	
	@Test
	public void updateFalseTest() {
		Entry term = new Entry("term");
		Card card = new Card(term, term);
		card.updatePercentage(false);
		double expectedOutput = 0.;
		double actualOutput = card.getPercentage();
		double precisionLoss = 0.01;
		assertEquals(expectedOutput, actualOutput, precisionLoss);
	}
	
	
	@Test
	public void updateTwiceTrueTest() {
		Entry term = new Entry("term");
		Card card = new Card(term, term);
		card.updatePercentage(true);
		card.updatePercentage(true);
		double expectedOutput = 2./3.;
		double actualOutput = card.getPercentage();
		double precisionLoss = 0.01;
		assertEquals(expectedOutput, actualOutput, precisionLoss);
	}
	
	@Test
	public void updateTrueFalseTest() {
		Entry term = new Entry("term");
		Card card = new Card(term, term);
		card.updatePercentage(true);
		card.updatePercentage(false);
		double expectedOutput = 1./3.;
		double actualOutput = card.getPercentage();
		double precisionLoss = 0.01;
		assertEquals(expectedOutput, actualOutput, precisionLoss);
	}

}
