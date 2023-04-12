# Latte language compiler

Latte Compiler is a Java-based compiler that performs various extensions and optimizations on Latte language code.


## Optimizations
The Latte compiler includes several optimizations to improve the performance of the generated code. These optimizations include:

 - Constant folding: Folding of constants in expressions.
 - Constant propagation: Propagation of constant values.
 - Copy propagation: Propagation of copied values.
 - PHI Nodes insertion and registers usage instead of memory allocation
 - Global strings reusage
 - Static single assignment (SSA) form: Conversion of the program to SSA form.
 - Dead code elimination: Removal of dead code, including dead variables and code after return statements.
 - Removal of unreachable blocks of code.
 - Removal of unnecessary blocks (in generated code).
 - Local common subexpression elimination (GCSE): Elimination of common subexpressions at the local level.
 - Global common subexpression elimination (GCSE): Elimination of common subexpressions at the global level.
 
## Extensions
The Latte compiler also includes several language extensions, including support for:
 - Arrays
 - Structures
 - Objects (attributes, methods, inheritance)
 - Virtual methods


## How to Use
To compile the compiler, run the following command: make

To use the compiler, run the following command: ./latc filename.lat, where filename.lat is the name of the Latte source file that you want to compile.

## Syntax
Below some syntax examples. Full grammar can be found in /Latte.cf in BNFC format.

```java
// print positive even numbers up to 10

int main () {
  int i = 1 ;
  while (i < 10){
    if (i % 2 == 0) printInt(i) ; 
    i++ ;
  }
  printInt(i) ;
  return 0 ;
}
```

```java
int[] sum (int[] a, int[] b) {
  int[] res = new int [a.length];
  int i = 0;

  while (i < a.length) {
    res[i] = a[i] + b[i];
    i++;
  }
  return res;
}
```

```java
class Node {
  Shape elem;
  Node next;

  void setElem(Shape c) { elem = c; }

  void setNext(Node n) { next = n; }

  Shape getElem() { return elem; }

  Node getNext() { return next; }
}

class Stack {
  Node head;

  void push(Shape c) {
    Node newHead = new Node;
    newHead.setElem(c);
    newHead.setNext(head);
    head = newHead;
  }

  boolean isEmpty() {
    return head==(Node)null;
  }

  Shape top() {
    return head.getElem();
  }

  void pop() {
    head = head.getNext();
  }
}

class Shape {
  void tell () {
    printString("I'm a shape");
  }

  void tellAgain() {
     printString("I'm just a shape");
  }
}

class Rectangle extends Shape {
  void tellAgain() {
    printString("I'm really a rectangle");
  }
}

class Circle extends Shape {
  void tellAgain() {
    printString("I'm really a circle");
  }
}

class Square extends Rectangle {
  void tellAgain() {
    printString("I'm really a square");
  }
}

int main() {
  Stack stk = new Stack;
  Shape s = new Shape;
  stk.push(s);
  s = new Rectangle;
  stk.push(s);
  s = new Square;
  stk.push(s);
  s = new Circle;
  stk.push(s);
  while (!stk.isEmpty()) {
    s = stk.top();
    s.tell();
    s.tellAgain();
    stk.pop();
  }
  return 0;
}
```



