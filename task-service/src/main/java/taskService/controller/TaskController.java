package taskService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import taskService.domain.Task;
import taskService.domain.TaskWrapper;
import taskService.domain.UpdateUsersWrapper;
import taskService.service.TaskService;

@Controller
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/createTask/v1")
    @ResponseBody
    public Object createTaskV1(
    //public ResponseEntity<Task> createTaskV1(
            @RequestBody TaskWrapper taskWrapper
    ) {
        int id = taskService.generateTaskId();
        Task task = new Task(
                id,
                taskWrapper.getName(),
                taskWrapper.getContent(),
                taskWrapper.getOwnerId(),
                taskWrapper.getTaskState(),
                taskWrapper.getPriority(),
                taskWrapper.getDate(),
                taskWrapper.getUsers(),
                taskWrapper.getSubtasks()
        );
        taskService.createTask(task);
        //return new ResponseEntity<Task>(task, HttpStatus.OK);
        return task;
    }

    @PostMapping("/updateTask/addUserToTask/v1")
    @ResponseBody
    public ResponseEntity<Task> addUserToTaskV1(
            @RequestBody UpdateUsersWrapper updateUsersWrapper
    ) {
        taskService.addUserToTask(updateUsersWrapper.getId(), updateUsersWrapper.getUserId());
        return new ResponseEntity<Task>(taskService.getTaskById(updateUsersWrapper.getId()), HttpStatus.OK);
    }

    @PostMapping("/updateTask/v1")
    @ResponseBody
    public ResponseEntity<Task> updateTaskInfoV1(
            @RequestBody TaskWrapper taskWrapper
    ) {
        Task task = new Task(
                taskWrapper.getId(),
                taskWrapper.getName(),
                taskWrapper.getContent(),
                taskWrapper.getOwnerId(),
                taskWrapper.getTaskState(),
                taskWrapper.getPriority(),
                taskWrapper.getDate(),
                taskWrapper.getUsers(),
                taskWrapper.getSubtasks()
        );
        if (taskService.updateTask(task) == true) {
            return new ResponseEntity<Task>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<Task>(task, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/getTask/{id}")
    @ResponseBody
    public ResponseEntity<Task> getTask(
            @PathVariable int id
    ) {
        return new ResponseEntity<Task>(taskService.getTaskById(id), HttpStatus.OK);
    }
}