import {create} from 'zustand';
import {addUserMeeting, getUserMeetings} from './meetingsService';

type MeetingsState = {
    meetings: any[];
    fetchMeetings: (token: string) => Promise<void>;
    addMeeting: (token: string, meeting: any) => Promise<void>;
};

export const useMeetingsStore = create<MeetingsState>(set => ({
    meetings: [],
    fetchMeetings: async (token: string) => {
        console.log(token);

        const meetings = await getUserMeetings(token);
        console.log(meetings);
        set({meetings: meetings});
    },
    addMeeting: async (token: string, meeting: any) => {
        await addUserMeeting(token, meeting);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
}));
