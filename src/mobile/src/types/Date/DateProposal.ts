import {Participant} from '../Participant';

export type DateProposal = {
    id: string;
    date: Date;
    voters: Participant[];
    voted: boolean;
};
