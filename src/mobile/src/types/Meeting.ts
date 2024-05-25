import {DateProposal} from './Date/DateProposal';
import {Participant} from './Participant';
import {PlaceProposal} from './Place/PlaceProposal';

export type Meeting = {
    id: string;
    name: string;
    participants: Participant[];
    createdAt: string;
    date: string | null;
    place: PlaceProposal | null;
    isPlaceChosen: boolean;
    isDateChosen: boolean;
    dateProposals: DateProposal[];
    placeProposals: PlaceProposal[];
    icon: string | null;
    isFinished: boolean;
    isRunning: boolean;
    duration: number;
};
