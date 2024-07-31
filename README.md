# Huffman Compression and Decompression

This Java project implements the Huffman coding algorithm for compressing and decompressing files. Huffman coding is a popular method for lossless data compression. This project consists of a main class HuffmanCode and an inner class HuffmanTreeNode that represents the nodes of the Huffman tree.

## Usage
### Compression
To compress a file, create an instance of HuffmanCode using an array of character frequencies. Then, use the save method to save the Huffman codes to a file.

### Decompression
To decompress a file, create an instance of HuffmanCode using a Scanner object to read the saved Huffman codes. Then, use the translate method to decode the data.

### Example
Here is a simple example of how to use the HuffmanCode class:
