package ch.mm.v1.codeGenerator;

import ch.mm.v1.scanner.base.Item;

/**
 * Operations-Objekt, was von der Expression-Klasse abgefüllt wird und vom Code-Generator verwendet werden kann
 * @author MARIUS
 *
 */
public class Operation {

	private int operator;
	private Item item;
	private Operation subOperX;
	private Operation subOperY;
	
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Operation getSubOperX() {
		return subOperX;
	}
	public void setSubOperX(Operation subOperX) {
		this.subOperX = subOperX;
	}
	public Operation getSubOperY() {
		return subOperY;
	}
	public void setSubOperY(Operation subOperY) {
		this.subOperY = subOperY;
	}
	
}
