JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
	ImmedInstr.java \
	Instruction.java \
	JumpInstr.java \
	Labels.java \
	Parser.java \
	RegInstr.java \
	Registers.java \
	typeInstruction.java \
	driver.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class

