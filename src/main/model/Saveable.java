package model;

import java.io.IOException;

public interface Saveable {
    void save(String fileName) throws IOException;
}
