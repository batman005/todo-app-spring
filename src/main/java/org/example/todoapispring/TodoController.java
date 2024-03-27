package org.example.todoapispring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {



    private TodoService todoService; //composition statement todocontroller has todoervice
    private TodoService todoService2;
    private static List<Todo> todoList;
    private static final String TODO_NOT_FOUND = "Todo not found";

    public TodoController(@Qualifier("anotherTodoService") TodoService todoService, @Qualifier("FakeTodoService") TodoService todoService2){
        this.todoService = todoService; //allocating todoservice bean to todocontroller
        this.todoService2 = todoService2;
        todoList = new ArrayList<>();
        todoList.add(new Todo(1, false, "Todo 1", 1));
        todoList.add(new Todo(2, true, "Todo 2", 2 ));
    }

    //getting todo data
    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(@RequestParam(required = false) Boolean isCompleted) {
        System.out.println("Incoming params is: " + isCompleted + " " + this.todoService.doSomething() + " " + this.todoService2.doSomething());
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