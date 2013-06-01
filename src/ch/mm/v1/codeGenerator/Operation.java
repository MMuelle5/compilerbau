package ch.mm.v1.codeGenerator;

import ch.mm.v1.scanner.base.Item;

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
