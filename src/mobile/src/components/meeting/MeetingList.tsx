import React, {useEffect, useState} from 'react';
import {Meeting} from '../../types/Meeting';
import {ActivityIndicator, FlatList, RefreshControl, SectionList, Text, TouchableOpacity, View} from 'react-native';
import Icon from 'react-native-vector-icons/AntDesign';
import * as emoji from 'node-emoji';
import {ParticipantAvatarList} from './participant/ParticipantAvatarList';
import {useAuthStore} from '../../lib/auth/authStore';
import {useMeetingsStore} from '../../lib/meetings/meetingsStore';
import {MeetingStateBadge} from './MeetingStateBadge';
import {MeetingOwnerBadge} from './MeetingOwnerBadge';
import {MeetingCreatedAtBadge} from './MeetingCreatedAtBadge';

const defaultIcon = 'handshake';

export const MeetingList = ({onSelect = _ => {}}: {onSelect: (meeting: Meeting) => void}) => {
    const {token} = useAuthStore();
    const {meetings, fetchMeetings} = useMeetingsStore();

    const [partitionedMeetings, setPartitionedMeetings] = useState<any>([]);
    const [refreshing, setRefreshing] = useState<boolean>(false);

    useEffect(() => {
        fetchMeetings(token);
    }, []);

    useEffect(() => {
        const scheduled = meetings.filter(
            meeting => meeting.isDateChosen && meeting.isPlaceChosen && !meeting.isFinished,
        );
        const inPlaning = meetings.filter(meeting => !meeting.isDateChosen || !meeting.isPlaceChosen);
        const past = meetings.filter(meeting => meeting.isFinished);

        const partitonedMeetingsList = [];

        if (scheduled.length > 0) {
            partitonedMeetingsList.push({
                title: 'Scheduled',
                data: scheduled,
            });
        }

        if (inPlaning.length > 0) {
            partitonedMeetingsList.push({
                title: 'In planing',
                data: inPlaning,
            });
        }

        if (past.length > 0) {
            partitonedMeetingsList.push({
                title: 'Past',
                data: past,
            });
        }

        setPartitionedMeetings(partitonedMeetingsList);
    }, [meetings]);

    const onRefresh = async () => {
        setRefreshing(true);
        await fetchMeetings(token);
        setRefreshing(false);
    };

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
                <Text className="text-gray-200 text-lg  font-semibold">
                    {item.icon ? emoji.get(item.icon) : emoji.get(defaultIcon)} {item.name}
                </Text>
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

    const renderSectionSeparator = () => <View className="h-4" />;

    return (
        <View>
            <SectionList
                sections={partitionedMeetings}
                renderItem={renderItem}
                keyExtractor={item => item.id}
                showsVerticalScrollIndicator={false}
                renderSectionHeader={({section: {title}}) => (
                    <Text className="text-gray-400/90 font-bold">{title}</Text>
                )}
                SectionSeparatorComponent={renderSectionSeparator}
                refreshControl={
                    <RefreshControl refreshing={refreshing} onRefresh={onRefresh}>
                        {refreshing && <ActivityIndicator size="small" />}
                    </RefreshControl>
                }
            />
        </View>
    );
};
