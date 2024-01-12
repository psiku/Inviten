import {create} from 'zustand';
import {
    addUserDateProposal,
    addUserMeeting,
    addUserPlaceProposal,
    getUserMeetings,
    inviteUser,
    pickUserPlaceProposal,
    scheduleUserDateProposal,
    unvoteOnUserDateProposal,
    unvoteOnUserPlaceProposal,
    voteOnUserDateProposal,
    voteOnUserPlaceProposal,
} from './meetingsService';
import {DateProposal} from '../../types/Date/DateProposal';

type MeetingsState = {
    meetings: any[];
    fetchMeetings: (token: string) => Promise<void>;
    addMeeting: (token: string, meeting: any) => Promise<void>;
    addDateProposal: (token: string, meetingId: string, date: any) => Promise<void>;
    voteOnDateProposal: (token: string, meetingId: string, proposalId: string) => Promise<void>;
    unvoteOnDateProposal: (token: string, meetingId: string, proposalId: string) => Promise<void>;
    scheduleDateProposal: (token: string, meetingId: string, proposalId: string) => Promise<void>;
    addPlaceProposal: (token: string, meetingId: string, place: any) => Promise<void>;
    voteOnPlaceProposal: (token: string, meetingId: string, proposalId: string) => Promise<void>;
    unvoteOnPlaceProposal: (token: string, meetingId: string, proposalId: string) => Promise<void>;
    pickPlaceProposal: (token: string, meetingId: string, proposalId: string) => Promise<void>;
    inviteUser: (token: string, meetingId: string, phoneNumber: string) => Promise<void>;
};

export const useMeetingsStore = create<MeetingsState>(set => ({
    meetings: [],
    fetchMeetings: async (token: string) => {
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
    addMeeting: async (token: string, meeting: any) => {
        await addUserMeeting(token, meeting);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
    addDateProposal: async (token: string, meetingId: string, proposal: DateProposal) => {
        await addUserDateProposal(token, meetingId, proposal);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
    voteOnDateProposal: async (token: string, meetingId: string, proposalId: string) => {
        await voteOnUserDateProposal(token, meetingId, proposalId);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
    unvoteOnDateProposal: async (token: string, meetingId: string, proposalId: string) => {
        await unvoteOnUserDateProposal(token, meetingId, proposalId);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
    scheduleDateProposal: async (token: string, meetingId: string, proposalId: string) => {
        await scheduleUserDateProposal(token, meetingId, proposalId);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
    addPlaceProposal: async (token: string, meetingId: string, place: any) => {
        await addUserPlaceProposal(token, meetingId, place);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
    voteOnPlaceProposal: async (token: string, meetingId: string, proposalId: string) => {
        await voteOnUserPlaceProposal(token, meetingId, proposalId);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
    unvoteOnPlaceProposal: async (token: string, meetingId: string, proposalId: string) => {
        await unvoteOnUserPlaceProposal(token, meetingId, proposalId);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
    pickPlaceProposal: async (token: string, meetingId: string, proposalId: string) => {
        await pickUserPlaceProposal(token, meetingId, proposalId);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
    inviteUser: async (token: string, meetingId: string, phoneNumber: string) => {
        await inviteUser(token, meetingId, phoneNumber);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
}));
