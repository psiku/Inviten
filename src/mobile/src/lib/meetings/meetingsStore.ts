import {create} from 'zustand';
import {
    addUserDateProposal,
    addUserMeeting,
    getUserMeetings,
    scheduleUserDateProposal,
    unvoteOnUserDateProposal,
    voteOnUserDateProposal,
} from './meetingsService';
import {DateProposal} from '../../types/Date/DateProposal';

type MeetingsState = {
    meetings: any[];
    fetchMeetings: (token: string) => Promise<void>;
    addMeeting: (token: string, meeting: any) => Promise<void>;
    addDateProposal: (
        token: string,
        meetingId: string,
        date: any,
    ) => Promise<void>;
    voteOnDateProposal: (
        token: string,
        meetingId: string,
        proposalId: string,
    ) => Promise<void>;
    unvoteOnDateProposal: (
        token: string,
        meetingId: string,
        proposalId: string,
    ) => Promise<void>;
    scheduleDateProposal: (
        token: string,
        meetingId: string,
        proposalId: string,
    ) => Promise<void>;
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
    addDateProposal: async (
        token: string,
        meetingId: string,
        proposal: DateProposal,
    ) => {
        await addUserDateProposal(token, meetingId, proposal);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
    voteOnDateProposal: async (
        token: string,
        meetingId: string,
        proposalId: string,
    ) => {
        await voteOnUserDateProposal(token, meetingId, proposalId);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
    unvoteOnDateProposal: async (
        token: string,
        meetingId: string,
        proposalId: string,
    ) => {
        await unvoteOnUserDateProposal(token, meetingId, proposalId);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
    scheduleDateProposal: async (
        token: string,
        meetingId: string,
        proposalId: string,
    ) => {
        await scheduleUserDateProposal(token, meetingId, proposalId);
        const meetings = await getUserMeetings(token);
        set({meetings: meetings});
    },
}));
