package com.inviten.api.features.notes;

import java.util.List;

public interface INoteRepository {

    public void createNote(String meetingId, String text);

    public List<Note> showNotes(String meetingId);

    public void deleteNote(String meetingId, String noteId);

    public void updateNote(String meetingId, String noteId, String newText);
}
