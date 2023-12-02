<<<<<<< HEAD
package com.inviten.api.features.meetings;

public interface IMeetingRepository {
    public Meeting one(String id);

    public void create(Meeting meeting);

    public Meeting createAndSave(Meeting meeting);
}
=======
package com.inviten.api.features.meetings;


public interface IMeetingRepository {
    public Meeting one(String id);

    public void create(Meeting meeting);

    public Meeting createAndSave(Meeting meeting);

<<<<<<< HEAD
    public void addMember(String meetingId, Member member);

    public void deleteMember(String meetingId, String phoneNumber);
=======
    public void put(Meeting meeting);
>>>>>>> 20c175c (Implement placeProposal)
}
>>>>>>> c1f624b (implementation of addMember and deleteMember functions)
