import {Participant} from './Participant';

export type Meeting = {
    id: string;
    name: string;
    participants: Participant[];
    createdAt: Date;
    date: string;
    time: string;
    isPlaceChosen: boolean;
    isDateChosen: boolean;
};
