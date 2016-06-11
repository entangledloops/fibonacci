# Fibonacci Lab

**This very simple self-contained Java app is:**
 - primarily __meant to demonstrate the relationship between the Fibonacci sequence and the Golden Ratio__
 - a thrown-together project for my little cousin that I hope somebody else will benefit from
 - intentionally not up to the same standards as my other projects, as it was coded in a couple of hours

![Fibonacci Lab](http://www.entangledloops.com/img/fibonacci.png?random=203948092348.303)

## Instructions to Build Source

1. [Download](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and install a Java Development Kit (JDK; free) 1.8+ on your machine if you don't have one. 
     - **OS X / \*nix:** ensure `JAVA_HOME` is defined and points to the install directory for your JDK. Usually done by the installer automatically, but you'll need to restart any terminals you have open and may need to logout/login if you have an issue.

2. [Download](https://github.com/entangledloops/fibonacci/archive/master.zip) and unzip this project somewhere. If you're a minimalist, you only need to right-click and save <a href="https://github.com/entangledloops/fibonacci/blob/master/Fibonacci.java">`Fibonacci.java`</a> somewhere convenient.

3. Run the app:

   - Easy way:
     - Execute the provided `run-applet` script for your OS.
        - **Windows:** double-click `run-applet.bat`
        - **OS X / \*nix:** Open a terminal in the project folder, and copy the following line, paste into your terminal with `Cmd` (OS X) or `Ctrl` (\*nix) + `Shift` + `V`, and hit enter:

        		chmod +x run-applet.sh; ./run-applet.sh

  - Hard way:
     - Manually build the sources with `javac` and run as you want. 
     - If you don't want/have `appletviewer` on your system (you probably do in `$JAVA_HOME/bin`), you'll need to change the applet `Boolean` setting in `Fibonacci.java` source file and run with as a traditional console app with `java` executable. See the provided script for an example.
  

