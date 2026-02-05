package com.example.exceptionlogs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exceptions")
public class ExceptionController {
  private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

  @GetMapping("/null-pointer")
  public String nullPointer() {
    log.info("Triggering NullPointerException");
    String value = null;
    return value.toString();
  }

  @GetMapping("/illegal-argument")
  public String illegalArgument() {
    log.info("Triggering IllegalArgumentException");
    return UUID.fromString("not-a-uuid").toString();
  }

  @GetMapping("/illegal-state")
  public String illegalState() {
    log.info("Triggering IllegalStateException");
    Iterator<String> iterator = List.of("alpha").iterator();
    iterator.remove();
    return "unreachable";
  }

  @GetMapping("/arithmetic")
  public String arithmetic() {
    log.info("Triggering ArithmeticException");
    int value = 10 / 0;
    return String.valueOf(value);
  }

  @GetMapping("/index-out-of-bounds")
  public String indexOutOfBounds() {
    log.info("Triggering IndexOutOfBoundsException");
    int[] values = {1, 2, 3};
    return String.valueOf(values[10]);
  }

  @GetMapping("/number-format")
  public String numberFormat() {
    log.info("Triggering NumberFormatException");
    return String.valueOf(Integer.parseInt("not-a-number"));
  }

  @GetMapping("/file-not-found")
  public String fileNotFound() throws IOException {
    log.info("Triggering FileNotFoundException");
    try (FileInputStream ignored = new FileInputStream("/tmp/does-not-exist.txt")) {
      return "unreachable";
    }
  }

  @GetMapping("/io")
  public String io() throws IOException {
    log.info("Triggering IOException");
    return new String(Files.readAllBytes(Path.of("/tmp")));
  }

  @GetMapping("/timeout")
  public String timeout() throws TimeoutException, InterruptedException, java.util.concurrent.ExecutionException {
    log.info("Triggering TimeoutException");
    CompletableFuture<Void> future = new CompletableFuture<>();
    future.get(10, TimeUnit.MILLISECONDS);
    return "unreachable";
  }

  @GetMapping("/unsupported-operation")
  public String unsupportedOperation() {
    log.info("Triggering UnsupportedOperationException");
    List<String> items = List.of("one", "two");
    items.add("three");
    return "unreachable";
  }
}
