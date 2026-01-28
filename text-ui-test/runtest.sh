#!/usr/bin/env bash

# 1. Get the absolute path of the project root (one level up from this script)
# This ensures we always know where we are, even if we move folders
PROJ_ROOT=$(cd "$(dirname "$0")/.." && pwd)
TEST_DIR="$PROJ_ROOT/text-ui-test"

# 2. Clean up previous test output
rm -f "$TEST_DIR/ACTUAL.TXT"

# 3. Compile everything from the root - exactly how you do it manually
cd "$PROJ_ROOT"
if ! javac -cp ./src/main/java/ -Xlint:none -d ./bin ./src/main/java/james/*.java ./src/main/java/james/task/*.java ./src/main/java/james/exception/*.java ./src/main/java/james/command/*.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# 4. RUN - This is the crucial part.
# We run from $PROJ_ROOT so that './data/tasks.txt' resolves to YOUR data folder.
java -classpath ./bin james.James < "$TEST_DIR/input.txt" > "$TEST_DIR/ACTUAL.TXT"

# 5. Compare results
cd "$TEST_DIR"
cp EXPECTED.TXT EXPECTED-UNIX.TXT
dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT

diff ACTUAL.TXT EXPECTED-UNIX.TXT
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi