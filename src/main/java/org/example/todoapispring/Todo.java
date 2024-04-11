package org.example.todoapispring;

public class Todo {
    private Integer id;
    private Boolean completed;
    private String title;
    private Integer userId;

//  basic constructor for this class
    public Todo(Integer id, Boolean completed, String title, Integer userId) {
        this.id = id;
        this.completed = completed;
        this.title = title;
        this.userId = userId;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setCompleted(Boolean completed){
        this.completed = completed;
    }

    public Boolean isCompleted(){
        return completed;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public Integer getUserId(){
        return userId;
    }

    @Override
    public String toString(){
        return
                "Todo{" +
                        "id = '" + id + '\'' +
                        ",completed = '" + completed + '\'' +
                        ",title = '" + title + '\'' +
                        ",userId = '" + userId + '\'' +
                        "}";
    }
}
