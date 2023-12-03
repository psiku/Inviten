import React from 'react';
import {Meeting} from '../../types/Meeting';
import {FlatList, Text, TouchableOpacity, View} from 'react-native';
import Icon from 'react-native-vector-icons/AntDesign';
import {ParticipantAvatarList} from './participant/ParticipantAvatarList';

export const MeetingList = ({
    meetings,
    onSelect = _ => {},
}: {
    meetings: Meeting[];
    onSelect: (meeting: Meeting) => void;
}) => {
    const renderItem = ({item}: {item: Meeting}) => (
        <TouchableOpacity onPress={() => onSelect(item)}>
            <View className="flex mb-1 p-4 bg-neutral-800/90 rounded-l-lg rounded-tr-lg">
                <View className="flex-row items-center">
                    <Text className="text-gray-200 text-lg  font-semibold">
                        {item.name}
                    </Text>
                    <Text className="ml-4 text-xs font-bold text-violet-400 italic">
                        in planing
                    </Text>
                </View>
                <View className="mt-2">
                    <View className="flex-row justify-between items-center">
                        <View className="h-6 flex justify-center">
                            {item.participants?.length > 0 ? (
                                <ParticipantAvatarList
                                    participants={item.participants}
                                />
                            ) : (
                                <Text className="text-gray-400 font-semibold">
                                    No participants
                                </Text>
                            )}
                        </View>
                        <View>
                            <Icon name="arrowright" size={25} color="#737373" />
                        </View>
                    </View>
                </View>
            </View>
        </TouchableOpacity>
    );

    const getOrderedMeetings = (m: Meeting[]) =>
        m.sort((a, b) => a.name.localeCompare(b.name));

    return (
        <FlatList
            data={getOrderedMeetings(meetings)}
            renderItem={renderItem}
            keyExtractor={item => item.id}
        />
    );
};
