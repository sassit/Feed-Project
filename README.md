<h1><u>The feed project represents a common problem</u></h1>

How to read a feed properly within the least amount of time processing it.

Obviously processing splits up into validation of input and generation of output. Output itself comprises valid output and error output report generation.

<b>This project is based on a case study with the given constraints:</b>

A generator has to be implemented which generates randomly financial certificate updates which is represented by the following csv line:

[timestamp],[isin],[currency],[bidprice],[bidsize],[askprice],[asksize]

Prices are limited to the format ###.##

A parser has to be implemented which parses and validates the input and generates a statistic (average of bid/ask prices per certificate over all updates) and an error report.

<h2>Own constraints</h2>

I've setup my own constraint to not use anything else except the JDK libraries, exception is the dependencies on the testing libraries, JUnit, JMock and Hamcrest.

<h2>Constraints for the generator</h2>

- The number of the to be generated certificates are specified on the command line as well as the number of certificate updates per certificate (as well as the number of threads).
- The isin generated has to be a valid isin, an example in words of the checkdigit algo was given.
- No constraints on the currency generation except being ISO compliant (3 alphas).
- Ask and bid price spread is 5, ask price always bigger or equal to bid price (no inverse spreads) but both within spread.
- A series of certificate updates (per isin) are generated, the price spread within the series shall not be bigger than 10.
- The number of threads processing the collection of generated certificates can be specified on the command line (e.g. specified that multithreading shall take place only after generation of the updates).

<h2>Constraints for the parser</h2>

- The parser processes the generated output of the generator.
- It validates the types and constraints given as constraints for the generator.
- It writes an error output in a defined xml format for each line parsed, shows the erroneous line as it was read from the file and shows the rules violated.
- It writes a report file which reports the average prices per certificate over all parsed updates for valid input updates.

Both components are executable jars.

<h2>Implementation</h2>

The solution has been implemented on a tdd basis, classes are reduced to the minimum to facilitate testability and composability.
Implementation definitions come to play mostly in the factories the rest is interface based.
The current solution writes 1 million lines without errors within 9 seconds on a dual core laptop.
After trying to solve it non multithreaded by writing it to a bytebuffer per thread, the current solution is reverted to lock the buffer per put operation when writing multithreaded to a random acces file.

<h2>Open issues</h2>

According to a feedback it seems that on a windows box the run was not succesful, having checked that on a virtual windows box it shows that on windows the following bug manifests itself: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4724038
After further digging it is found that other projects workaround that unresolved issue (since java 1.4), so I will consider in implementing one of the workarounds to fix it (using a Cleaner).

Given the price format constraints the space of numbers is limited to 100000, which should not be a problem given the other properties of the update to make it random, but the issue still is that around less than 10 updates of one million are not unique, in that case additional updates should be generated in order to generate the specified amount, this boils down to using a set collecting the updates which is on purpose as to not collect duplicates.

<h2>Closed issues</h2>

<del>Given the constraints and looking at the multithreaded file writer constraint to write/parse only after generation/parsing of the data, it is still open if there is not a solution without usage of locking, as mentioned, by partioning the writes per thread to dedicated byte buffers and merging them afterwards.
This has been attempted but failed on the fragmentation and afterwards the truncating of the file issue resulting out of it, the bytebuffers could not be merged into a contiguous one and the resulting file size reflecting the actual content size and not the merged buffer size.</del>

Another attempt has shown that writing it to dedicated buffers per thread the merge operation (by writing into the filechannel from each allocated bytebuffer) the overhead of the operation results in a 50% lower performance in terms of processing time.

The main part of the attempt is shown here:
https://gist.github.com/4517191

<h2>Running it</h2>

- Clone the project. 
- Run <b>mvn install</b> just below the feed-project folder level.
- In the target folders of feed-generator and feed-parser copy the jars into a directory of your choice.
- Run:<br>
        <b>java -jar -Xms1024m -Xmx1280m feed-generator-1.0-SNAPSHOT.jar 1000 1000 10 test.csv</b><br>
        <b>java -jar -Xms1024m -Xmx1280m feed-parser-1.0-SNAPSHOT.jar 10 test.csv report.xml error.xml</b>
