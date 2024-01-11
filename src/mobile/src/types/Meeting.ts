import {DateProposal} from './Date/DateProposal';
import {Participant} from './Participant';

export type Meeting = {
    id: string;
    name: string;
    participants: Participant[];
    createdAt: string;
    date: string;
    isPlaceChosen: boolean;
    isDateChosen: boolean;
    dateProposals: DateProposal[];
};
