import {create} from 'zustand';
import {
    addUserDateProposal,
    addUserMeeting,
    addUserPlaceProposal,
    changeUserMeetingDuration,
    changeUserMeetingIcon,
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
import {getOrderedDateProposals, getOrderedMeetings, getOrderedPlaceProposals} from '../../utils/meetingHelpers';
import {Meeting} from '../../types/Meeting';

type MeetingsState = {
    meetings: Meeting[];
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
    changeIcon: (token: string, meetingId: string, icon: string) => Promise<void>;
    changeDuration: (token: string, meetingId: string, duration: number) => Promise<void>;
    updateMeeting: (meeting: Meeting) => void;
};

export const useMeetingsStore = create<MeetingsState>((set, get) => ({
    meetings: [],
    fetchMeetings: async (token: string) => {
        const meetings = await getUserMeetings(token);
        set({meetings: getOrderedMeetings(meetings)});
    },
    addMeeting: async (token: string, meeting: any) => {
        const newMeeting = await addUserMeeting(token, meeting);
        const meetings = [newMeeting, ...get().meetings];
        set({meetings: getOrderedMeetings(meetings)});
    },
    addDateProposal: async (token: string, meetingId: string, proposal: DateProposal) => {
        const meeting = get().meetings.find(m => m.id === meetingId);
        const newProposal = await addUserDateProposal(token, meetingId, proposal);
        meeting?.dateProposals.push(newProposal);
        const meetings = get().meetings.map(m => (m.id === meetingId ? meeting : m));
        set({meetings: getOrderedMeetings(meetings)});
    },
    voteOnDateProposal: async (token: string, meetingId: string, proposalId: string) => {
        const meeting = get().meetings.find(m => m.id === meetingId);
        const proposal = await voteOnUserDateProposal(token, meetingId, proposalId);
        meeting.dateProposals = meeting?.dateProposals.map(p => (p.id === proposal.id ? proposal : p));
        get().updateMeeting(getOrderedDateProposals(meeting));
    },
    unvoteOnDateProposal: async (token: string, meetingId: string, proposalId: string) => {
        const meeting = get().meetings.find(m => m.id === meetingId);
        const proposal = await unvoteOnUserDateProposal(token, meetingId, proposalId);
        meeting.dateProposals = meeting?.dateProposals.map(p => (p.id === proposal.id ? proposal : p));
        get().updateMeeting(getOrderedDateProposals(meeting));
    },
    scheduleDateProposal: async (token: string, meetingId: string, proposalId: string) => {
        const meeting = get().meetings.find(m => m.id === meetingId);
        const date = await scheduleUserDateProposal(token, meetingId, proposalId);
        meeting.isDateChosen = true;
        meeting.date = date.proposedDate;
        get().updateMeeting(meeting);
    },
    addPlaceProposal: async (token: string, meetingId: string, place: any) => {
        const meeting = get().meetings.find(m => m.id === meetingId);
        const newProposal = await addUserPlaceProposal(token, meetingId, place);
        meeting?.placeProposals.push(newProposal);
        get().updateMeeting(meeting);
    },
    voteOnPlaceProposal: async (token: string, meetingId: string, proposalId: string) => {
        const meeting = get().meetings.find(m => m.id === meetingId);
        const proposal = await voteOnUserPlaceProposal(token, meetingId, proposalId);
        meeting.placeProposals = meeting?.placeProposals.map(p => (p.id === proposal.id ? proposal : p));
        get().updateMeeting(getOrderedPlaceProposals(meeting));
    },
    unvoteOnPlaceProposal: async (token: string, meetingId: string, proposalId: string) => {
        const meeting = get().meetings.find(m => m.id === meetingId);
        const proposal = await unvoteOnUserPlaceProposal(token, meetingId, proposalId);
        meeting.placeProposals = meeting?.placeProposals.map(p => (p.id === proposal.id ? proposal : p));
        get().updateMeeting(getOrderedPlaceProposals(meeting));
    },
    pickPlaceProposal: async (token: string, meetingId: string, proposalId: string) => {
        const meeting = get().meetings.find(m => m.id === meetingId);
        const place = await pickUserPlaceProposal(token, meetingId, proposalId);
        meeting.isPlaceChosen = true;
        meeting.place = place;
        get().updateMeeting(meeting);
    },
    inviteUser: async (token: string, meetingId: string, phoneNumber: string) => {
        const meeting = get().meetings.find(m => m.id === meetingId);
        const user = await inviteUser(token, meetingId, phoneNumber);
        console.log(user);

        meeting.participants.push(user);
        get().updateMeeting(meeting);
    },
    changeIcon: async (token: string, meetingId: string, icon: string) => {
        const meeting = get().meetings.find(m => m.id === meetingId);
        await changeUserMeetingIcon(token, meetingId, icon);
        meeting.icon = icon;
        get().updateMeeting(meeting);
    },
    changeDuration: async (token: string, meetingId: string, duration: number) => {
        const meeting = get().meetings.find(m => m.id === meetingId);
        await changeUserMeetingDuration(token, meetingId, duration);
        meeting.duration = duration;
        get().updateMeeting(meeting);
    },
    updateMeeting: (meeting: Meeting | undefined) => {
        const meetings = get().meetings.map(m => (m.id === meeting.id ? meeting : m));
        set({meetings: getOrderedMeetings(meetings)});
    },
}));
