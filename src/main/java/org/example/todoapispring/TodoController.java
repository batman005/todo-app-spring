package org.example.todoapispring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    private static List<Todo> todoList;
    private static final String TODO_NOT_FOUND = "Todo not found";

    public TodoController(){
        todoList = new ArrayList<>();
        todoList.add(new Todo(1, false, "Todo 1", 1));
        todoList.add(new Todo(2, true, "Todo 2", 2 ));
    }

    //getting todo data
    @GetMapping
    public ResponseEntity<List<Todo>> getTodos() {
        return ResponseEntity.ok(todoList);
    }

    //making a new todo
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo) {

        /**
         * we can use this annotation to set the status code @ResponseStatus(HttpStatus.CREATED)
         *
         */
        todoList.add(newTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }

    //getting a particular task
    @GetMapping("/{todoId}")
    public ResponseEntity<?> getTodoById(@PathVariable Long todoId) {
        for (Todo todo : todoList) {
            if (todo.getId() == todoId) {
                return ResponseEntity.ok(todo);
            }
        }
        // HW: Along with 404 status code, try to send a json {message: Todo not found}
        //try to send custom a json message Todo not found


        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TODO_NOT_FOUND);
    }
}