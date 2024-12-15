package com.maksymiliangach.ai;

import java.io.IOException;

public interface Model {
    public void save(String fileName) throws IOException;
    public Model load(String fileName) throws IOException;
    public void plot();
}
