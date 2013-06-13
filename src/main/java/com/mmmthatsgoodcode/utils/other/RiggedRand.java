package com.mmmthatsgoodcode.utils.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class RiggedRand<T> {

	@SuppressWarnings("serial")
	public static class ParticipantDistributionException extends Exception  {

		
	}

	private class Participant<T> {
		
		private int chance = 100;
		private T winner;
		
		public Participant(int chance, T winner) {
			this.chance = chance;
			this.winner = winner;
		}
		
		public int getChance() {
			return chance;
		}
		
		public T getWinner() {
			return winner;
		}
		
		
	}

	private List<Participant<T>> participants = new ArrayList<Participant<T>>();
	private final int MAX_RAND = 100;
	private int chanceSum = 100;
	
	public synchronized RiggedRand<T> add(Participant<T> participant) throws ParticipantDistributionException {
		
		if (chanceSum-participant.getChance() < 0) throw new ParticipantDistributionException();
		chanceSum -= participant.getChance();
		
		participants.add(participant);
		
		return this;
	}
	
	public synchronized RiggedRand<T> add(int chance, T participant) throws ParticipantDistributionException {
		
		return this.add(new Participant<T>(chance, participant));
		
	}
	
	public synchronized RiggedRand<T> add(Participant...participants) throws ParticipantDistributionException {
		
		for (Participant participant:participants) {
			
			this.add(participant);
			
		}
		
		
		return this;
		
	}
	

	public T shuffle() throws ParticipantDistributionException {
		if (chanceSum != 0) throw new ParticipantDistributionException();
		
		int luckyNumber = 1 + (int)(Math.random() * (MAX_RAND-1));
		// create list of store orders by weight
		int min = MAX_RAND;
		for (Participant<T> participant:participants) {
			if (luckyNumber > (min-participant.getChance())) return participant.getWinner();
			min -= participant.getChance();

		}
		
		throw new ParticipantDistributionException(); // impossible

	}
	
	public boolean full() {
		return chanceSum == 0;
	}	
	
	
}
