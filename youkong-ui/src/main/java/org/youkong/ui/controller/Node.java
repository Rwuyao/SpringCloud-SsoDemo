package org.youkong.ui.controller;

public class Node {

	private Node test;
	private String name;
	
	public Node(String name) {
		super();
		this.name = name;
	}
	
	
	public Node getTest() {
		return test;
	}


	public void setTest(Node test) {
		this.test = test;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public static void main(String[] args) {
		
		Node test = new Node("first");
		Node test2 = new Node("second");
		test.setTest(test2);
		
		System.out.println(test.getName());
		System.out.println(test.getTest().getName());
	}
	
}
