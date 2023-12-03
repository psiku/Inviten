import {Participant} from '../Participant';

export type PlaceProposal = {
    id: string;
    name: string;
    address?: string;
    description?: string;
    note?: string;
    creator: Participant;
    voters: Participant[];
    voted: boolean;
};
