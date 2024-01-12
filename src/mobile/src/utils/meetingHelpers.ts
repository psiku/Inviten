import {Meeting} from '../types/Meeting';

const isAdmin = (role: string) => role === 'owner' || role === 'admin';

export const isMeetingAdmin = (meeting: Meeting, user: string) => {
    return meeting?.participants.some(p => p.phoneNumber === user && isAdmin(p.role));
};

export const getOrderedMeetings = (m: Meeting[]): Meeting[] =>
    m.sort((a, b) => {
        if (a.createdAt && b.createdAt) {
            return -a.createdAt.localeCompare(b.createdAt);
        } else {
            return -1;
        }
    });

export const getOrderedDateProposals = (m: Meeting): Meeting => {
    m.dateProposals?.sort((a, b) => b.votes?.length - a.votes?.length);
    return m;
};

export const getOrderedPlaceProposals = (m: Meeting): Meeting => {
    m.placeProposals?.sort((a, b) => b.votes?.length - a.votes?.length);
    return m;
};
