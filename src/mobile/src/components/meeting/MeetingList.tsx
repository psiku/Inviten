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
import {classNames} from '../../utils/styleHelpers';
import {DurationBadge} from './date/DurationBadge';
import {DateScheduledCard} from './date/DateScheduledCard';
import {DateScheduledBadge} from './date/DateScheduledBadge';

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
        const running = meetings.filter(meeting => meeting.isRunning);
        const scheduled = meetings.filter(meeting => meeting.isDateChosen && !meeting.isRunning && !meeting.isFinished);
        const inPlaning = meetings.filter(meeting => !meeting.isDateChosen);
        const past = meetings.filter(meeting => meeting.isFinished);

        const partitonedMeetingsList = [];

        if (running.length > 0) {
            partitonedMeetingsList.push({
                title: 'Running',
                data: running,
            });
        }

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
        await Promise.all([fetchMeetings(token), new Promise(resolve => setTimeout(resolve, 900))]);
        setRefreshing(false);
    };

    const renderItem = ({item}: {item: Meeting}) => (
        <TouchableOpacity onPress={() => onSelect(item)}>
            <View className="flex mb-1 p-4 bg-neutral-800/90 rounded-l-lg rounded-tr-lg">
                <View className="mb-1 flex-row justify-between">
                    <View className="flex-row">
                        <MeetingOwnerBadge meeting={item} />
                        {/* <MeetingStateBadge meeting={item} /> */}
                    </View>
                    {item.isRunning ? (
                        <DurationBadge meeting={item} />
                    ) : !item.isFinished && item.isDateChosen ? (
                        <DateScheduledBadge date={item.date} />
                    ) : (
                        <MeetingCreatedAtBadge meeting={item} />
                    )}
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
