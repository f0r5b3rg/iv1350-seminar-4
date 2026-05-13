#!/bin/sh

set -e

echo "Compiling..."
mvn -q compile

echo "Running..."
mvn -q exec:java