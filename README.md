# Compiler

Welcome to my compiler project for AP Computer Science A 2022-2023. Examples are in the `compiler/examples` directory. 

## Overview of the language

To help making parsing data more simple, I use a very assembly-looking style for my language. Each line of code begins with an instruction telling the compiler what sort of line of code it is. For example, to declare a variable, you start the line with the `DECL` command. For instance, this program that reads in a string and outputs in to the console highlights the basic structure of the language:
```
DECL: string s
FUNC: print "Give me a string\n"
FUNC: read s
FUNC: print "Your string is " s "\n"
```
We begin by declaring (`DECL`) a variable that is known as `s` of type `string`. We then print to the console asking the user for a string, read in a variable, and then echo that string back.

Whenever the compiler runs into a compilation error, it throws either an `InstructionException`, `VariableException`, or `FunctionException` with a detailed message on what in the code is broken.

## Variables

Let's take a closer look at variables now. The way to declare a variable is as such:
```
DECL: <type> <name> <value>
```
`type` is the type of the variable. My language only supports `string` and `int` as possible values. Then `name` is the name of the variable. The only requirement for variable names is that they cannot have any spaces in them. You can declare a variable with the name `#$3@$##@@#%@#%@#%` and it would still work. Finally, we have an optional initialization `value`. You can leave this blank to set it to `0` in the case of an integer and `""` in the case of a string.

Variables declared at one point in the program may not be re-declared. This means that you cannot declare a variable within a loop. Also, once variables are declared, they are acceessible from everywhere in the program, i.e. variables do not have scopes. These design choices are due to the fact that the compiler internally handles variables using a *registry*. This is a map from variable names to variables themselves. Not limiting variables to a particular scope makes the registry a lot more simple to implement.

The only variables that will not be stored in the registry are literals. Literals do not have a formal name associated with them, so when they are passed to a function, temporary unnamed variables holding their value are created and disposed of after the function returns.

## Standard Library Functions

Currently, the only two functions you can call are `print` and `read`, both of which work with an arbritarty number of variable arguements. `print` will take the arguments passed to it and concatenate them into one big string and print that (without placing a newline at the end) to the console. `read` will take the arguments passed to it and take console input. When integers are passed to `print`, it will them into strings. When integers are passed into `read`, it will fetch the next integer input. When strings are passed into `read`, it will grab the next line from input. 

The synatx for calling a function looks like this:
```
FUNC: <func> <var0> <var1> <var2> ... <varN>
```

## Math

To make parsing math as simple as possible, I use instructions that do simple operations (multiplication, addition, etc) one at a time. Suppose you wanted to evalute the equation `y=5(x+1)` for a particular variable `x`. Here is what your code would look like:
```
DECL: int x <value>
DECL: int y
MATH: ADD x 1
STOI: y
MATH: MUL y 5
STOI: y
FUNC: print "The value is " y
```
That looks a bit complicated, so lets break it down. We first declare `x` and `y`. Then we execute the first `MATH` instruction. `MATH` instructions follow this general syntax:
```
MATH: <op> <lhs> <rhs>
```
Here `<op>` is used to specify wheter this is a addition, subtraction, etc between the integer variables `lhs` and `rhs`. For example, `MUL lhs rhs` is `lhs*rhs`. There will be full list of what operations do what at the bottom of this section. Instead, let's now focus on where the result of `MATH` operations end up and what the `STOI` instruction is. All `MATH` instructions store their result in an integer register, `ireg`. The code cannot access this register, however, it can store the value of that register in a variable. `STOI` takes in a variable and does just that. For example, let's review this code from the above snippet:
```
MATH: ADD x 1
STOI: y
```
We add `1` to `x` and store that in `ireg`. We then write the value of `ireg` to `y`. We can do further operations with `y`, such as multiplying it by 5. The value of `ireg` is not cleared until the next `MATH` operation, in which case it is overwritten.

### Table of operations

| Operation | Sub-instruction | Result |
| --- | ----------- | --- |
| Addition | `ADD` | `lhs+rhs` |
| Subtraction | `SUB` | `lhs-rhs` |
| Multiplication | `MUL` | `lhs*rhs` |
| Division | `DIV` | `lhs/rhs` |
| Modulo | `MOD` | `lhs%rhs` |

## If statements

The general syntax of an `if` statement looks like this:
```
COND: <condition>
<code to execute if condition is true>
ALTR:
<code to execute if condition is false>
ENDI:
```
Or:
```
COND: <condition>
<code to execute if condition is true>
ENDI:
```
Here, `COND` is short for conditional, `ALTR` is short for alternative, and `ENDI` is short for end if. Either ints and ints can be compared, or strings and strings. All basic comparison operations are supported (less than, greater than, lequal, gequal, equal, nequal) using the same syntax as Java. String comparison uses the result of `compare` to zero. For example `s < t` evalutes as `s.compare(t) < 0`. 

## Loops
Loops are like this:
```
LOOP: <condition>
<code>
ENDL:
```
Where `ENDL` is short for end loop. If `<condition>` is true, then the block of code in the loop executes, and when `ENDL` is reached, it goes back to `LOOP`. if `LOOP` is false, then we skip to the next instruction after `ENDL`. This code iterates from 0...9 and prints to console:
```
DECL: i 0
LOOP: i < 10
FUNC: print i "\n"
ENDL:
```

And that sums up how to use my assembly-looking language. 