import React, {useState} from 'react';
import {SafeAreaView, ScrollView, Text, View} from 'react-native';
import {DateVotingSlider} from '../../components/meeting/date/DateVotingSlider';
import {NavigationFunctionComponent} from 'react-native-navigation';
import {Meeting} from '../../types/Meeting';
import {DateProposal} from '../../types/Date/DateProposal';
import {PlaceVotingList} from '../../components/meeting/place/PlaceVotingList';
import {PlaceProposal} from '../../types/Place/PlaceProposal';
import {DateAddButton} from '../../components/meeting/date/DateAddButton';
import {PlaceAddButton} from '../../components/meeting/place/PlaceAddButton';
import {DateScheduledCard} from '../../components/meeting/date/DateScheduledCard';
import {PlacePickedCard} from '../../components/meeting/place/PlacePickedCard';

type MeetingScreenProps = {
    meeting: Meeting;
};

const dateProposalsData = [1, 2, 3, 4, 5].map(id => ({
    id,
    date: new Date(),
    voted: false,
    voters: [
        {
            id: '3',
            name: 'Kasia',
        },
        {
            id: '4',
            name: 'Kasia',
        },
    ],
}));

const placeProposalsData = [1, 2, 3, 4, 5, 6, 7].map(id => ({
    id,
    name: 'Zyrafa',
    address: 'ul. Karmelicka 1',
    voted: false,
    voters: [
        {
            id: '3',
            name: 'Kasia',
        },
        {
            id: '4',
            name: 'Kasia',
        },
    ],
}));

export const MeetingScreen: NavigationFunctionComponent<
    MeetingScreenProps
> = props => {
    const meeting = props.meeting;

    const [dateProposals, setDateProposals] = useState<DateProposal[]>([]);
    const [scheduledDate, setScheduledDate] = useState<DateProposal | null>(
        null,
    );

    const [placeProposals, setPlaceProposals] = useState<PlaceProposal[]>([]);
    const [pickedPlace, setPickedPlace] = useState<PlaceProposal | null>(null);

    const handleDateAdd = (proposal: DateProposal) => {
        setDateProposals([...dateProposals, proposal]);
    };

    const handleDateVote = (proposal: DateProposal) => {
        setDateProposals(
            dateProposals.map(p => (p.id === proposal.id ? proposal : p)),
        );
    };

    const handlePlaceAdd = (proposal: PlaceProposal) => {
        setPlaceProposals([...placeProposals, proposal]);
    };

    const handlePlaceVote = (proposal: PlaceProposal) => {
        setPlaceProposals(
            placeProposals.map(p => (p.id === proposal.id ? proposal : p)),
        );
    };

    return (
        <SafeAreaView>
            <View className="p-5 h-screen">
                <View className="mb-1 flex-row space-x-2">
                    <Text className="text-gray-300 text-xs font-semibold italic">
                        owned
                    </Text>
                    <Text className="text-violet-400 text-xs font-semibold italic">
                        in planing
                    </Text>
                </View>

                <Text className="text-gray-100 text-3xl font-semibold">
                    {meeting?.name}
                </Text>
                <View className="mt-5">
                    <View className="mb-4 flex-row justify-between items-center">
                        <Text className="text-gray-400/90 font-bold">
                            Meeting date
                        </Text>
                        {scheduledDate ? null : (
                            <DateAddButton onAdd={handleDateAdd} />
                        )}
                    </View>
                    {scheduledDate ? (
                        <DateScheduledCard date={scheduledDate} />
                    ) : (
                        <DateVotingSlider
                            proposals={dateProposals}
                            onVote={handleDateVote}
                            onSchedule={setScheduledDate}
                        />
                    )}

                    <View className="my-4 mb-4 flex-row justify-between items-center">
                        <Text className="mt-4 text-gray-400/90 font-bold">
                            Meeting place
                        </Text>
                        {pickedPlace ? null : (
                            <PlaceAddButton onAdd={handlePlaceAdd} />
                        )}
                    </View>
                    {pickedPlace ? (
                        <PlacePickedCard place={pickedPlace} />
                    ) : (
                        <PlaceVotingList
                            proposals={placeProposals}
                            onVote={handlePlaceVote}
                            onPick={setPickedPlace}
                        />
                    )}
                </View>
            </View>
        </SafeAreaView>
    );
};

MeetingScreen.options = {
    topBar: {
        rightButtons: [
            {
                id: 'MeetingShareButton',
                component: {
                    name: 'com.inviten.MeetingShareButton',
                },
            },
        ],
    },
};
