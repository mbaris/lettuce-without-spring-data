package io.baris.performance.model;

public interface GenieRepository {

    Genie createGenieLettuce(Genie Genie) throws InterruptedException;

    Genie getByIdLettuce(String isbn) throws InterruptedException;

}