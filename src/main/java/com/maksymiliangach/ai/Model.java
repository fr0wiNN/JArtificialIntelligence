package com.maksymiliangach.ai;

import java.io.IOException;
import java.io.Serializable;

public interface Model extends Serializable {
    default String summary() { return "Generic Model"; }
}
