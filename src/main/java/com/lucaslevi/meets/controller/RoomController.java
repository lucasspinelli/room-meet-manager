package com.lucaslevi.meets.controller;

import com.lucaslevi.meets.exception.ResourceNotFoundException;
import com.lucaslevi.meets.model.Room;
import com.lucaslevi.meets.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.module.ResolutionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1")
public class RoomController {

    @Autowired
    private RoomRepository repository;

    @GetMapping("/room")
    public List<Room> getAllRooms(){
        return repository.findAll();
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable(value= "id") long id)
    throws ResourceNotFoundException {
        Room room = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Room not foun:: " + id));
        return ResponseEntity.ok().body(room);
    }

    @PostMapping("/rooms")
    public Room createRoom(@Valid @RequestBody Room room){
        return repository.save(room);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable(value = "id") long id,
                                           @Valid @RequestBody Room roomDetails) throws ResourceNotFoundException{
        Room room = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Room not find for this id:: "+ id));
        room.setName(roomDetails.getName());
        room.setDate(roomDetails.getDate());
        room.setStartHour(roomDetails.getStartHour());
        room.setEndHour(roomDetails.getEndHour());
        final Room updatedRoom = repository.save(room);
        return ResponseEntity.ok(updatedRoom);
    }

    public Map<String, Boolean> deleteRoom(@PathVariable(value = "id") long id)
        throws ResourceNotFoundException{
        Room room = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not find for this id:: "+ id));
        repository.delete(room);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
