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
        //try to send custom a json message Todo not found
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TODO_NOT_FOUND);
    }
    /**
     * Api to delete a Todo
     * We can delete a particular todo by passing the id of the todo
     * */
    @DeleteMapping("/{todoId}")
    public ResponseEntity<?> deleteTodoById(@PathVariable Long todoId){
        Todo todoToRemove = null;
        for(Todo todo: todoList){
            if(todo.getId() == todoId){
                todoToRemove = todo;
                break;
            }
        }
        if(todoToRemove != null){
            todoList.remove(todoToRemove);
            String deleteSuccessMessage = "Todo with id " + todoId + " deleted successfully";
            return ResponseEntity.status(HttpStatus.OK).body(deleteSuccessMessage);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TODO_NOT_FOUND);
        }
    }

    /**
     * Api to update a Todo
     * We can update a particular todo by passing the id of the todo
     * @param todoId
     * @RequestBody
     * @return
     * * */
    @PatchMapping("/{todoId}")
    public ResponseEntity<?> updateTodoById(@PathVariable Long todoId, @RequestBody Todo updatedTodo) {
        if (updatedTodo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Updated todo cannot be null");
        }

        Todo todoToUpdate = null;
        for (Todo todo : todoList) {
            if (todo.getId()== todoId) {
                todoToUpdate = todo;
                break;
            }
        }

        if (todoToUpdate != null) {
            if (updatedTodo.getTitle() != null) {
                todoToUpdate.setTitle(updatedTodo.getTitle());
            }
            if (updatedTodo.getUserId() != null){
                todoToUpdate.setUserId(updatedTodo.getUserId());
            }
            if (updatedTodo.isCompleted() != null) {
                todoToUpdate.setCompleted(updatedTodo.isCompleted());
            }
            return ResponseEntity.ok(todoToUpdate);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TODO_NOT_FOUND);
        }
    }

}