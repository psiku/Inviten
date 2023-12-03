import {Participant} from './Participant';

export type Meeting = {
    id: string;
    name: string;
    participants: Participant[];
    createdAt: Date;
};
