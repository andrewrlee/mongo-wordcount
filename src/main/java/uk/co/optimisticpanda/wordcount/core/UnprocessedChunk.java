package uk.co.optimisticpanda.wordcount.core;

public class UnprocessedChunk {

	private int index;
	private String text;

	public UnprocessedChunk(int index, String text){
		this.index = index;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public int getIndex() {
		return index;
	}
	
}
