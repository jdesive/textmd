# TextMd Help page

# Heading one
## Heading two
### Heading three
#### Heading four
##### Heading five
###### Heading six

To write a paragraph you just type like normal.

To write in **Bold** text you surround the text you want to be bold with two asterisks.  
To write in *Italic* text you surround the text with one asterisks.  
To write `inline-code` you surround the text with a accent.  

To [hyperlink](https://link.com) some text  
To add an image ![Alt Text](http://via.placeholder.com/50x50)


Horizontal Rules
***
******************

### Extensions
[Strikethrough] To write in ~~Strikethrough~~ text you surround the text with two tildes  
[Auto-Link] You can also create hyperlinks by pasting the link https://link.com

## Lists
1. Item 1
2. Item 2
3. Item 3

* Item 1
* Item 2
* Item 3

### Extensions
[Task List]

- [x] Task 1
- [ ] Task 2
- [x] Task 3

## Code
Java:
```java
/* HelloWorld.java
 */

public class HelloWorld
{
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}
}
```
Python:
```python
prices = {'apple': 0.40, 'banana': 0.50}
my_purchase = {
    'apple': 1,
    'banana': 6}
grocery_bill = sum(prices[fruit] * my_purchase[fruit]
                   for fruit in my_purchase)
print 'I owe the grocer $%.2f' % grocery_bill
```
Rust:
```rust
// This is a comment, and will be ignored by the compiler
// This is the main function
fn main() {
    // Print text to the console
    println!("Hello World!");
}
```

## Table
| Table Heading 1 | Table Heading 2 | Center align    | Right align     | Table Heading 5 |
| :-------------- | :-------------- | :-------------: | --------------: | :-------------- |
| Item 1          | Item 2          | Item 3          | Item 4          | Item 5          |
| Item 1          | Item 2          | Item 3          | Item 4          | Item 5          |
| Item 1          | Item 2          | Item 3          | Item 4          | Item 5          |
| Item 1          | Item 2          | Item 3          | Item 4          | Item 5          |
| Item 1          | Item 2          | Item 3          | Item 4          | Item 5          |

## Blockqoutes
**Q:** How many blockqoutes can I do in a row? 
> *A:* As many as you want

This is just a normal paragraph
> This is a blockqoute