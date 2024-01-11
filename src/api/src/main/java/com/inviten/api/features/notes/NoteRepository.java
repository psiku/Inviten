package com.inviten.api.features.notes;

import com.inviten.api.features.meetings.IMeetingRepository;
import com.inviten.api.features.meetings.Meeting;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;

@Service
public class NoteRepository implements INoteRepository {
    private final DynamoDbTable<Meeting> meetingTable;

    private final IMeetingRepository meetingRepository;

    public NoteRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient, IMeetingRepository meetingRepository) {
        this.meetingTable = dynamoDbEnhancedClient.table("meetings", TableSchema.fromBean(Meeting.class));
        this.meetingRepository = meetingRepository;
    }


    @Override
    public void createNote(String meetingId, String text){
        // pobierz numer z tokena
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String phoneNumber = (String) authentication.getPrincipal();


        // stwórz notatkę
        Note note = new Note();
        note.setText(text);
        note.setProposedBy(phoneNumber);

        // pobierz spotkanie, do którego dodamy notatkę
        Meeting meeting = meetingRepository.one(meetingId);

        List<Note> notes = meeting.getNotes();

        if(notes == null){
            notes = List.of(note);
        }else{
            notes.add(note);
        }

        // aktualizuj spotkanie
        meeting.setNotes(notes);

        //aktualizuj tabelę
        meetingTable.putItem(meeting);
    }

    @Override
    public List<Note> showNotes(String meetingId){
        // pobierz spotkanie i zwróć listę notatek
        Meeting meeting = meetingRepository.one(meetingId);
        return meeting.getNotes();
    }

    @Override
    public void deleteNote(String meetingId, String noteId){
        // pobierz numer z tokena
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String phoneNumber = (String) authentication.getPrincipal();

        // pobierz listę notatek

        Meeting meeting = meetingRepository.one(meetingId);
        List<Note> notes = meeting.getNotes(); // zakładamy, że jakieś notatki istnieją

        for(int i = 0; i <= notes.size(); i++){
            if(notes.get(i).getId().equals(noteId) && notes.get(i).getProposedBy().equals(phoneNumber)){
                notes.remove(i);
                break;
            }
        }

        // ustaw nowe notatki
        meeting.setNotes(notes);

        // aktualizacja tabeli
        meetingTable.putItem(meeting);
    }


    @Override
    public void updateNote(String meetingId, String noteId, String newText){
        // pobierz numer z tokena
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String phoneNumber = (String) authentication.getPrincipal();

        Meeting meeting = meetingRepository.one(meetingId);
        List<Note> notes = meeting.getNotes(); // zakładamy, że jakieś notatki istnieją

        for(int i = 0; i <= notes.size(); i++){
            if(notes.get(i).getId().equals(noteId) && notes.get(i).getProposedBy().equals(phoneNumber)) {
                notes.get(i).setText(newText);
                break;
            }
        }

        //aktualizacja notatek
        meeting.setNotes(notes);

        // aktualizacja tabeli
        meetingTable.putItem(meeting);
    }
}
