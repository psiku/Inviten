import {DateProposal} from './Date/DateProposal';
import {Participant} from './Participant';
import {PlaceProposal} from './Place/PlaceProposal';

export type Meeting = {
    id: string;
    name: string;
    participants: Participant[];
    createdAt: string;
    date: string;
    place: PlaceProposal;
    isPlaceChosen: boolean;
    isDateChosen: boolean;
    dateProposals: DateProposal[];
    placeProposals: PlaceProposal[];
};
