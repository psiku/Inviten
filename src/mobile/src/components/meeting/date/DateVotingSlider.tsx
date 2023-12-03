import {FlatList, Text, TouchableOpacity, View} from 'react-native';
import {DateProposal} from '../../../types/Date/DateProposal';
import React from 'react';
import Icon from 'react-native-vector-icons/AntDesign';
import {ParticipantAvatarList} from '../participant/ParticipantAvatarList';
import {ScheduleButton} from './ScheduleButton';
import {getDayName, getHour} from '../../../utils/dateHelpers';

export const DateVotingSlider = ({
    proposals,
    onVote = _ => {},
    onSchedule = _ => {},
}: {
    proposals: DateProposal[];
    onVote: (proposal: DateProposal) => void;
    onSchedule: (proposal: DateProposal) => void;
}) => {
    const handleVote = (proposal: DateProposal) => {
        const me = {id: '1', name: 'Me'};

        const vote = !proposal.voted;
        const voters = vote
            ? [me, ...proposal.voters]
            : proposal.voters.filter(voter => voter.id !== me.id);

        onVote({...proposal, voted: !proposal.voted, voters: voters});
    };

    const renderItem = ({item}: {item: DateProposal}) => {
        const handleSchedule = () => onSchedule(item);
        return (
            <View>
                <TouchableOpacity onPress={() => handleVote(item)}>
                    <View
                        className={
                            item.voted
                                ? 'flex mr-1 p-4 bg-violet-800/90 rounded-l-lg rounded-tr-lg'
                                : 'flex mr-1 p-4 bg-neutral-800/90 rounded-l-lg rounded-tr-lg'
                        }>
                        <View className="flex items-center">
                            <Text className="text-gray-200 font-semibold">
                                {getDayName(item.date)}
                            </Text>
                            <Text className="mt-2 text-xl font-bold text-gray-200">
                                {getHour(item.date)}
                            </Text>
                        </View>
                        <View className="flex items-center">
                            <View className="my-4">
                                <Icon
                                    name={
                                        item.voted
                                            ? 'checkcircle'
                                            : 'checkcircleo'
                                    }
                                    size={30}
                                    color={item.voted ? '#a78bfa' : '#737373'}
                                />
                            </View>
                            <View className="h-6">
                                {item.voters.length > 0 ? (
                                    <ParticipantAvatarList
                                        participants={item.voters}
                                    />
                                ) : (
                                    <Text className="text-gray-400 font-semibold">
                                        No votes
                                    </Text>
                                )}
                            </View>
                        </View>
                    </View>
                </TouchableOpacity>
                <ScheduleButton onPress={handleSchedule} />
            </View>
        );
    };

    // sort proposals by voters count
    const getOrderedProposals = (p: DateProposal[]) =>
        p.sort((a, b) => b.voters.length - a.voters.length);

    if (proposals?.length === 0) {
        return (
            <View className="flex items-center">
                <Text className="text-gray-400  italic">
                    No available dates
                </Text>
            </View>
        );
    }

    return (
        <View>
            <FlatList
                horizontal
                data={getOrderedProposals(proposals)}
                renderItem={renderItem}
                keyExtractor={item => item.id}
            />
        </View>
    );
};
