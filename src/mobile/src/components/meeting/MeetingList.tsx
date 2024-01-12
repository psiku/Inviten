import React, {useEffect} from 'react';
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

    useEffect(() => {
        fetchMeetings(token);
    }, []);

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
        <FlatList
            data={meetings}
            renderItem={renderItem}
            keyExtractor={item => item.id}
            showsVerticalScrollIndicator={false}
        />
    );
};
