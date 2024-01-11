import React, {useEffect, useState} from 'react';
import {SafeAreaView, Text, View} from 'react-native';
import {DateVotingSlider} from '../../components/meeting/date/DateVotingSlider';
import {NavigationFunctionComponent} from 'react-native-navigation';
import {Meeting} from '../../types/Meeting';
import {PlaceVotingList} from '../../components/meeting/place/PlaceVotingList';
import {DateAddButton} from '../../components/meeting/date/DateAddButton';
import {PlaceAddButton} from '../../components/meeting/place/PlaceAddButton';
import {DateScheduledCard} from '../../components/meeting/date/DateScheduledCard';
import {PlacePickedCard} from '../../components/meeting/place/PlacePickedCard';
import {MeetingOwnerBadge} from '../../components/meeting/MeetingOwnerBadge';
import {MeetingStateBadge} from '../../components/meeting/MeetingStateBadge';
import {MeetingCreatedAtBadge} from '../../components/meeting/MeetingCreatedAtBadge';
import {useMeetingsStore} from '../../lib/meetings/meetingsStore';
import {useAuthStore} from '../../lib/auth/authStore';
import {isMeetingAdmin} from '../../utils/meetingHelpers';

type MeetingScreenProps = {
    meeting: Meeting;
};

export const MeetingScreen: NavigationFunctionComponent<MeetingScreenProps> = props => {
    const {user} = useAuthStore();
    const meetings = useMeetingsStore(state => state.meetings);

    const getCurrentMeeting = () => meetings.find(m => m.id === props.meeting?.id);

    const [meeting, setMeeting] = useState<Meeting>(getCurrentMeeting());

    useEffect(() => {
        const currentMeeting = getCurrentMeeting();
        setMeeting(currentMeeting);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [meetings, props.meeting?.id]);

    return (
        <SafeAreaView>
            <View className="p-5 h-screen">
                <View className="mb-1 flex-row justify-between">
                    <View className="flex-row">
                        <MeetingOwnerBadge meeting={meeting} />
                        <MeetingStateBadge meeting={meeting} />
                    </View>
                    <MeetingCreatedAtBadge meeting={meeting} />
                </View>

                <Text className="text-gray-100 text-3xl font-semibold">{meeting?.name}</Text>
                <View className="mt-5">
                    <View className="mb-4 flex-row justify-between items-center">
                        <Text className="text-gray-400/90 font-bold">Meeting date</Text>
                        {meeting?.isDateChosen || !isMeetingAdmin(meeting, user) ? null : (
                            <DateAddButton meetingId={meeting?.id} />
                        )}
                    </View>
                    {meeting?.isDateChosen ? (
                        <DateScheduledCard date={meeting?.date} />
                    ) : (
                        <DateVotingSlider meeting={meeting} />
                    )}

                    <View className="my-4 mb-4 flex-row justify-between items-center">
                        <Text className="mt-4 text-gray-400/90 font-bold">Meeting place</Text>
                        {meeting?.isPlaceChosen || !isMeetingAdmin(meeting, user) ? null : (
                            <PlaceAddButton meetingId={meeting?.id} />
                        )}
                    </View>
                    {meeting?.isPlaceChosen ? (
                        <PlacePickedCard place={meeting?.place} />
                    ) : (
                        <PlaceVotingList meeting={meeting} />
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
