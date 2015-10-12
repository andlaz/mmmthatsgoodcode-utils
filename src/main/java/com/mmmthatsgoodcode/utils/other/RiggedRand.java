package com.mmmthatsgoodcode.utils.other;

import java.util.ArrayList;
import java.util.List;

public class RiggedRand<T> {

	public static class Participant<T> {
		
		private int chance = 1;
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
	private int chanceSum = 0;
	
	public synchronized RiggedRand<T> add(Participant<T> participant) {
		
		if (participant.getChance() <= 0) throw new IllegalArgumentException();
		chanceSum += participant.getChance();
		
		participants.add(participant);
		
		return this;
	}
	
	public synchronized RiggedRand<T> add(int chance, T participant) {
		
		return this.add(new Participant<T>(chance, participant));
		
	}
	
	public synchronized RiggedRand<T> add(Participant...participants) {
		
		for (Participant participant:participants) {
			
			this.add(participant);
			
		}
		
		
		return this;
		
	}
	

	public T shuffle() {
		
		int luckyNumber = 1 + (int)(Math.random() * (chanceSum-1));
		int eliminated = 0;
		// create list of store orders by weight
		for (Participant<T> participant:participants) {
			if (luckyNumber <= (participant.getChance())+eliminated) return participant.getWinner();
			eliminated += participant.getChance();

		}
		
		throw new IllegalStateException(); // impossible

	}

	
	
}
