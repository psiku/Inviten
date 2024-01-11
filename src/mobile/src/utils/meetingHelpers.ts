import {Meeting} from '../types/Meeting';

const isAdmin = (role: string) => role === 'owner' || role === 'admin';

export const isMeetingAdmin = (meeting: Meeting, user: string) => {
    return meeting.participants.some(
        p => p.phoneNumber === user && isAdmin(p.role),
    );
};
