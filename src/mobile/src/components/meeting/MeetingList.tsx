import React, {useEffect, useState} from 'react';
import {Meeting} from '../../types/Meeting';
import {FlatList, Text, TouchableOpacity, View} from 'react-native';
import Icon from 'react-native-vector-icons/AntDesign';
import {ParticipantAvatarList} from './participant/ParticipantAvatarList';
import {useAuthStore} from '../../lib/auth/authStore';
import {useMeetingsStore} from '../../lib/meetings/meetingsStore';
import {MeetingStateBadge} from './MeetingStateBadge';
import {MeetingOwnerBadge} from './MeetingOwnerBadge';
import {MeetingCreatedAtBadge} from './MeetingCreatedAtBadge';

export const MeetingList = ({onSelect = _ => {}}: {onSelect: (meeting: Meeting) => void}) => {
    const {token} = useAuthStore();
    const {meetings, fetchMeetings} = useMeetingsStore();

    const [scheduledMeetings, setScheduledMeetings] = useState<Meeting[]>([]);
    const [inPlaningMeetings, setInPlaningMeetings] = useState<Meeting[]>([]);

    useEffect(() => {
        fetchMeetings(token);
    }, []);

    useEffect(() => {
        const scheduled = meetings.filter(meeting => meeting.isDateChosen && meeting.isPlaceChosen);
        const inPlaning = meetings.filter(meeting => !meeting.isDateChosen || !meeting.isPlaceChosen);

        setScheduledMeetings(scheduled);
        setInPlaningMeetings(inPlaning);
    }, [meetings]);

    const renderItem = ({item}: {item: Meeting}) => (
        <TouchableOpacity onPress={() => onSelect(item)}>
            <View className="flex mb-1 p-4 bg-neutral-800/90 rounded-l-lg rounded-tr-lg">
                <View className="mb-1 flex-row justify-between">
                    <View className="flex-row">
                        <MeetingOwnerBadge meeting={item} />
                        <MeetingStateBadge meeting={item} />
                    </View>
                    <MeetingCreatedAtBadge meeting={item} />
                </View>
                <Text className="text-gray-200 text-lg  font-semibold">{item.name}</Text>
                <View className="mt-2">
                    <View className="flex-row justify-between items-center">
                        <View className="h-6 flex justify-center">
                            <ParticipantAvatarList participants={item.participants} />
                        </View>
                        <View>
                            <Icon name="arrowright" size={25} color="#737373" />
                        </View>
                    </View>
                </View>
            </View>
        </TouchableOpacity>
    );

    return (
        <View>
            {scheduledMeetings.length > 0 && (
                <>
                    <Text className="text-gray-400/90 font-bold mb-4">Scheduled</Text>
                    <FlatList
                        data={scheduledMeetings}
                        renderItem={renderItem}
                        keyExtractor={item => item.id}
                        showsVerticalScrollIndicator={false}
                    />
                </>
            )}
            <Text className="text-gray-400/90 font-bold my-4">In planing</Text>
            <FlatList
                data={inPlaningMeetings}
                renderItem={renderItem}
                keyExtractor={item => item.id}
                showsVerticalScrollIndicator={false}
            />
        </View>
    );
};
