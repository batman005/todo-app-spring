package org.example.todoapispring;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("FakeTodoService")
public class FakeTodoService implements TodoService {
    @TimeMonitor
    public String doSomething() {
        for(long i = 0; i < 1000000000; i++) {}
            return "Something";
    }
}
