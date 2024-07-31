/*Name: Ankith Tunuguntla
  Date: 12/04/2024
  Course: CSE 123
  Program Name: Huffman*/

/*Class name: HuffmanCode
  Class function: This class compresses and decompresses files using a Huffman algorithm.*/

import java.util.*;
import java.io.*;

public class HuffmanCode {
    private HuffmanTreeNode overallRoot; 

    /*Constructor name: HuffmanCode
      Constructor parameters: 
          ->int[] frequencies: stores an input array of all frequencies of all characters. The 
              frequency of each character is stored in the index of the array equal to that 
              of the character's ASCII value  
      Constructor function: to construct a new HuffmanCode object by accepting the frequency array 
                            The character is not considered for the frequency array if its 
                            frequency is 0 or negative.*/

    public HuffmanCode(int [] frequencies){
        Queue<HuffmanTreeNode> pq = new PriorityQueue<>();
        for(int i = 0; i < frequencies.length;i++){
            if(frequencies[i] > 0){
                HuffmanTreeNode node = new HuffmanTreeNode(i, frequencies[i]);
                pq.add(node);
            }
        }
        while (pq.size() > 1) {
            HuffmanTreeNode leftNode = pq.remove();
            HuffmanTreeNode rightNode = pq.remove();
            HuffmanTreeNode parentNode = new HuffmanTreeNode(-1, leftNode.frequency+ 
                rightNode.frequency, leftNode, rightNode);
            pq.add(parentNode);
        }
        overallRoot = pq.remove();
    }

    /*Constructor name: HuffmanCode
      Constructor parameters: 
          -> Scanner input: Scanner object to read the .code file 
      Constructor function: to construct a new HuffmanCode object and read through the previously
          constructed .code file. The ASCII value and encoding are found here.*/

    public HuffmanCode(Scanner input){
        overallRoot = new HuffmanTreeNode(-1,-1);
        while(input.hasNextLine()){
            String ascii = input.nextLine();
            int asciiValue = Integer.parseInt(ascii);
            String code = input.nextLine();
            overallRoot = HuffmanCode(overallRoot, asciiValue, code);
        }
    }

    /*Private method name: HuffmanCode
    Private method parameters:
        ->overallRoot: root of the Huffman Tree
        ->int ascii: stores the ASCII value
        ->String code: stores the encoding 
    Private method function: to create aid the constructor in creating the Huffman Tree
    Private method return type: HuffmanTreeNode
    Private method exceptions: none*/

    private HuffmanTreeNode HuffmanCode(HuffmanTreeNode overallRoot, int ascii, String code){
        HuffmanTreeNode temp = overallRoot;
        if(code.length() == 0){
            temp.ascii = ascii;
        } 
        else{
            if(code.substring(0, 1).equals("1")){
                if(overallRoot.right == null){
                    overallRoot.right = new HuffmanTreeNode(-1, -1);
                }
                temp.right = HuffmanCode(overallRoot.right, ascii, code.substring(1));
            } 
            else{
                if(overallRoot.left == null){
                    overallRoot.left = new HuffmanTreeNode(-1, -1);
                }
                temp.left = HuffmanCode(overallRoot.left, ascii, code.substring(1));
            }
        }
        return temp;
    }

    /*Method name: save
    Method parameters: 
        ->PrintStream output: PrintStream object to help save the Huffman code
            to a .code file
    Method function: to store the current Huffman Code to the given output stream
        in the standard format (the .code file format alternates between the ASCII value
        for a character and its encoding.) 
    Method return type: void
    Method exceptions: none*/

    public void save(PrintStream output){
        save(output, overallRoot, "");
    }

    /*Private method name: save
    Private method parameters: 
        ->PrintStream output: PrintStream object to help save the Huffman code
            to a .code file
        -> HuffmanTreeNode overallRoot: stores root of the Huffman Tree
        ->String code: stores the encoding
    Private method function: to store the current Huffman Code to the given output stream
        in the standard format (the .code file format alternates between the ASCII value
        for a character and its encoding.) 
    Private method return type: void
    Private method exceptions: none*/

    private void save(PrintStream output, HuffmanTreeNode overallRoot, String code){
        if(overallRoot.ascii != -1){
            output.println(overallRoot.ascii);
            output.println(code);
        } 
        else{
            save(output, overallRoot.left, code + "0");
            save(output, overallRoot.right, code + "1");
        }
    }

    /*Method name: translate
    Method parameters:
        ->BitInputStream input: BitInputStream object to read data bit by bit 
        ->PrintStream output: PrintStream object to help save the Huffman code
    Method function: to read individual bits from a .short file containing binary data
                     and print the respective characters to the output. 
    Method return type: void
    Method exceptions: none*/

    public void translate(BitInputStream input, PrintStream output){
        HuffmanTreeNode temp = overallRoot;
        while (input.hasNextBit()) {
            int bit = input.nextBit();
            if(bit == 0){
                temp = temp.left;
            } 
            else{
                temp = temp.right;
            }
            if(temp.left == null && temp.right == null){
                output.write((char) temp.ascii);
                temp = overallRoot;
            }
        }
    }

    /*Class name: HuffmanTreeNode
    Class function: This class represents a binary tree node with its ASCII value, its frequency,
                    reference to its left node(if not null) and to its right node(if not null)*/

    private static class HuffmanTreeNode implements Comparable<HuffmanTreeNode>{
        private int ascii;
        private int frequency;
        private HuffmanTreeNode left;
        private HuffmanTreeNode right;

        /*Constructor name: HuffmanTreeNode
        Constructor parameters: 
            int asciiVal: stores the ASCII value of the character
            int freq: stores the frequency of each character
        Constructor function: to construct a new HuffmanTreeNode with no child nodes*/ 

        public HuffmanTreeNode(int asciiVal, int freq) {
            this(asciiVal, freq, null, null);
        }

        /*Constructor name: HuffmanTreeNode
        Constructor parameters: 
            ->int asciiVal: stores the ASCII value of the character
            ->int freq: stores the frequency of each character
            ->HuffmanTreeNode left: stores the reference to left child node
            ->HuffmanTreeNode right: stores the reference to right child node
        Constructor function: to construct a new QuizTreeNode with reference to child nodes*/

        public HuffmanTreeNode(int asciiVal,int freq,HuffmanTreeNode left,HuffmanTreeNode right){
            this.ascii = asciiVal;
            this.frequency = freq;
            this.left = left;
            this.right = right;
        }

        /*Method name: compareTo
        Method parameters:
           ->HuffmanTreeNode other: another HuffmanTreeNode object(node) to compare with
        Method function: to find and return the difference in frequency between two nodes
        Method return type: int
        Method exceptions: none*/

        public int compareTo(HuffmanTreeNode other) {
            return this.frequency - other.frequency;
        }
    }    
}
