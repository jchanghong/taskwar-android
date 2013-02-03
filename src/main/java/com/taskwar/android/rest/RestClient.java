
package com.taskwar.android.rest;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Post;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;
import com.taskwar.android.model.DoneTask;
import com.taskwar.android.model.House;
import com.taskwar.android.model.Housemate;
import com.taskwar.android.model.IdList;
import com.taskwar.android.model.Invitation;
import com.taskwar.android.model.User;

@Rest(rootUrl = "http://192.168.1.3:3000", converters = { MappingJacksonHttpMessageConverter.class })
@Accept(MediaType.APPLICATION_JSON)
public interface RestClient {

    @Post("/done_tasks")
    public DoneTask save(DoneTask doneTask);
    
    @Get("/done_tasks/{id}")
    public DoneTask getDoneTaskById(int id);

    @Post("/housemates")
    public Housemate save(Housemate housemate);
    
    @Get("/housemates/{id}")
    public Housemate getHousemateById(int id);
    
    @Get("/houses/{id}")
    public House getHouseById(int id);
    
    @Post("/houses")
    public House save(House house);
    
    @Get("/houses/{id}/house_tasks")
    public IdList getHouseTasksOfHouse(int id);
    
    @Get("/houses/{id}/done_tasks")
    public IdList getDoneTasksOfHouse(int id);
    
    @Get("/houses/{id}/housemates")
    public IdList getHousematesOfHouse(int id);
    
    @Get("/houses/exists?name={houseName}&password={housePassword}")
    public House houseExists(String houseName, String housePassword);
    
    @Post("/invitations")
    public Invitation save(Invitation invitation);
    
    @Post("/users")
    public User save(User user);
    
    @Get("/users/login?email={userEmail}&password={userPassword}")
    public User login(String userEmail, String userPassword);
    
    @Get("/users/{id}")
    public User getUserById(int id);
}
