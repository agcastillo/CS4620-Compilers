#!/bin/sh
# regress.sh is a shell script that compares a MeggyJava compiler 
# written in Haskell and compiled down to mjc
# generated .s files run through the Meggy Simulator (MJSIM.jar) against
# a Java-only version of the Meggy run-time library.  Both the Java only 
# version and MJSIM print out text messages about what is
# happening in the program so that diffs can be performed.
# This script assumes that MJSIM.jar is in the same directory
# as regress.sh is being run.  Additionally, there needs to be a meggy/
# subdirectory with the Java-only meggy package.
# The script assumes that the compiler being checked is one directory up
# in ../mjc.
#
# This script will by default do the comparison for all .java files in the 
# WorkingTestCases/ and WorkingErrorTestCases/ subdirectories.
# However, if one specific file is given as input, then the test will only
# be done on that file.

# usage examples: 
#   ./regress.sh
#   ./regress.sh file.java

# Note that test cases in .java files can have corresponding .arg_opt
# files.  Such files indicate button presses and other user interaction
# with the device.  Both the meggy simulator and the Java-only Meggy
# library use these files when they exist.

# After you run this script, there will be a lot of .class, .dot, and .s
# files in this directory.  They can be safely deleted. 

# This function actually does take a parameter, the input file name.
function compareMJCwithJava()
{
    echo "========================================================="
    echo "Regression testing mjc $filename"

    # Copy the source file to this directory.
    # This is so that everything works with the meggy/ Java package.
    filename=`basename $1 .java`
    cp $1 .

    # compile the input file with our compiler
    rm -rf $filename.java.s
    rm -rf t1
    ../mjc $filename.java

    # check if there is an arg_opts file to go with it
    # and copy that into meggy/arg_opts
    if test -f $1.arg_opts 
    then
        cp -f $1.arg_opts meggy/arg_opts
    fi
    
    # remove any previous versions of the file
    rm *.class t2  > /dev/null 2>&1
    # Compile the java only version of the program.
    javac $filename.java >& t2
    if [ $? -eq 0 ] 
    then
        # run java on Java bytecode
        java $filename &> t2
    fi

    # run the simulator on the generated .s file
    # and do the diff
    size=`wc -c < $filename.java.s`
    if  [ "$size" -gt 0 ];
    then
        java -jar MJSIM.jar -b -f $filename.java.s &> t1
        echo "diff between MJSIM.jar output and Java only output"
        diff t1 t2
    fi
    
    echo "DONE with ../mjc $filename"
    echo "========================================================="
}

##############################################################
# Main routine for the regression testing.
# Will either do the comparison on a single file or
# on all the files in certain subdirectories.

# Check if we got a file name on the command line.
if [ $# -gt 0 ]
then
    # Test the single file provided
    compareMJCwithJava $1
    echo $1
else
    echo
    echo "#### Testing with the files in WorkingTestCases ####"
    for filename in `ls WorkingTestCases/*.java`
    do
        compareMJCwithJava $filename
    done

    echo
    echo "#### Testing with the files in WorkingErrorTestCases ####"
    for filename in `ls WorkingErrorTestCases/*.java`
    do
        compareMJCwithJava $filename
    done

fi

echo

