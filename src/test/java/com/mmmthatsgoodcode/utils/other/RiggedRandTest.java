package com.mmmthatsgoodcode.utils.other;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.mmmthatsgoodcode.utils.other.RiggedRand;
import com.mmmthatsgoodcode.utils.other.RiggedRand.ParticipantDistributionException;

public class RiggedRandTest {


	@Test(expected=ParticipantDistributionException.class)
	public void testAddDoesNotAllowOverflow() throws ParticipantDistributionException {
		
		new RiggedRand<String>().add(10, "foo").add(91, "bar");
	
	}
	
	@Test(expected=ParticipantDistributionException.class)
	public void testDoesNotShuffleBeforeFull() throws ParticipantDistributionException {
		new RiggedRand<String>().add(10, "foo").add(1, "bar").shuffle();
	}
	
	@Test
	public void testAccuracy() throws ParticipantDistributionException {
		
		final int runs = 100000;
		final float accuracy = 0.02f; // 2% accuracy with 100k tries should be realistic
		RiggedRand<String> randy = new RiggedRand<String>().add(10, "10").add(15, "15").add(25, "25").add(50, "50");
		
		Map<String, Integer> results = new HashMap<String, Integer>();
		for(int i =1;i<runs;i++) {
			String v = randy.shuffle();
			results.put(v, (results.containsKey(v)?results.get(v)+1:1));
		}
		
//		System.out.println("Results: "+results.toString());
		
		for(Entry<String, Integer> participant:results.entrySet()) {
			float expected = runs*(new Float(new Integer(participant.getKey()))/100);
//			System.out.println("Expected "+expected+", got "+participant.getValue());
			assertTrue(participant.getValue() > (expected-(runs*accuracy)) && participant.getValue() < (expected+(runs*accuracy)));
		}
		
		
		
	}	
	
}
