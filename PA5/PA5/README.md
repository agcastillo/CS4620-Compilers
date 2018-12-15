CS4620 - Compilers
Alex Castillo - agc3km
Fintan Horan - fbh6dc

Most compilation files work, we did run into two bugs that we're aware of:
1. while(true) loops eventually halt with an error message
2. for some cases (we found this in PA5obj.java), it executes setPixel with an incorrect bit - not sure why it does that 

To test:
1. Open PA5
2. "make"
3. Compile .java file
4. Use MJSIM
