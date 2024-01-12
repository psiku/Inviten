package com.inviten.api.features.notes;

import com.inviten.api.features.notes.INoteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.awt.*;

@RestController
public class NoteController {

    private final INoteRepository noteRepository;

    public NoteController(INoteRepository noteRepository){ this.noteRepository = noteRepository; }


    @PostMapping("meetings/{meetingId}/note")
    public void createNote(@PathVariable String meetingId, @RequestBody String text){
         noteRepository.createNote(meetingId, text);
    }

    @GetMapping("meetings/{meetingId}/notes")
    public List<Note> showNotes(@PathVariable String meetingId){
        return noteRepository.showNotes(meetingId);
    }

    @DeleteMapping("meetings/{meetingId}/note/{noteId}")
    public void deleteNote(@PathVariable String meetingId, @PathVariable String noteId){
        noteRepository.deleteNote(meetingId, noteId);
    }

    @PostMapping("meetings/{meetingId}/note/{noteId}")
    public void updateNote(@PathVariable String meetingId, @PathVariable String noteId, @RequestBody String newText){
        noteRepository.updateNote(meetingId, noteId, newText);
    }


}
