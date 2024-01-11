<<<<<<< HEAD
package com.inviten.api.features.meetings;

public interface IMeetingRepository {
    public Meeting one(String id);

    public void create(Meeting meeting);

    public Meeting createAndSave(Meeting meeting);
}
=======
package com.inviten.api.features.meetings;


import com.inviten.api.features.users.User;
import org.springframework.web.bind.annotation.PathVariable;

public interface IMeetingRepository {
    public Meeting one(String id);

    public void create(Meeting meeting);

    public Meeting createAndSave(Meeting meeting);

<<<<<<< HEAD
<<<<<<< HEAD
    public void addMember(String meetingId, Member member);
=======
    public void invite(String meetingId, String phoneNumber);
>>>>>>> 6ff8976 (add invite method to add friend to meeting)

    public void deleteMember(String meetingId, String phoneNumber);
<<<<<<< HEAD
=======
    public void put(Meeting meeting);
>>>>>>> 20c175c (Implement placeProposal)
=======

    public void put(Meeting meeting);
<<<<<<< HEAD
>>>>>>> 16719cb (added put method)
=======

    public void leaveMeeting(String meetingId);
>>>>>>> 6d750cd (add leaveMeeting function)
}
>>>>>>> c1f624b (implementation of addMember and deleteMember functions)
