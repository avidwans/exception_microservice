#!/bin/sh

# Minimal Maven Wrapper starter.
# Downloads the wrapper jar if missing, then delegates to it.

BASE_DIR=$(cd "$(dirname "$0")" && pwd)
WRAPPER_JAR="$BASE_DIR/.mvn/wrapper/maven-wrapper.jar"
WRAPPER_PROPS="$BASE_DIR/.mvn/wrapper/maven-wrapper.properties"

if [ ! -f "$WRAPPER_JAR" ]; then
  WRAPPER_URL=$(grep -E '^wrapperUrl=' "$WRAPPER_PROPS" | cut -d'=' -f2-)
  if [ -z "$WRAPPER_URL" ]; then
    echo "wrapperUrl not set in $WRAPPER_PROPS" >&2
    exit 1
  fi
  echo "Downloading Maven Wrapper..." >&2
  if command -v curl >/dev/null 2>&1; then
    curl -fSL -o "$WRAPPER_JAR" "$WRAPPER_URL" || exit 1
  elif command -v wget >/dev/null 2>&1; then
    wget -O "$WRAPPER_JAR" "$WRAPPER_URL" || exit 1
  else
    echo "Neither curl nor wget is available to download the Maven Wrapper." >&2
    exit 1
  fi
fi

JAVA_CMD=${JAVA_HOME:+"$JAVA_HOME/bin/java"}
JAVA_CMD=${JAVA_CMD:-java}

exec "$JAVA_CMD" -jar "$WRAPPER_JAR" "$@"
