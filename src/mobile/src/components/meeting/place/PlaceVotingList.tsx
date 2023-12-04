import React from 'react';
import {FlatList, Text, TouchableOpacity, View} from 'react-native';
import {PlaceProposal} from '../../../types/Place/PlaceProposal';
import Icon from 'react-native-vector-icons/AntDesign';
import {ParticipantAvatarList} from '../participant/ParticipantAvatarList';
import {PickButton} from './PickButton';

export const PlaceVotingList = ({
    proposals,
    onVote = _ => {},
    onPick = _ => {},
}: {
    proposals: PlaceProposal[];
    onVote: (proposal: PlaceProposal) => void;
    onPick: (proposal: PlaceProposal) => void;
}) => {
    const renderItem = ({item}: {item: PlaceProposal}) => {
        const handlePick = () => {
            onPick(item);
        };

        const handleVote = (proposal: PlaceProposal) => {
            const me = {id: '1', name: 'Me'};

            const vote = !proposal.voted;
            const voters = vote
                ? [me, ...proposal.voters]
                : proposal.voters.filter(voter => voter.id !== me.id);

            onVote({
                ...proposal,
                voted: !proposal.voted,
                voters: voters,
            });
        };

        return (
            <View className="flex-row">
                <TouchableOpacity
                    onPress={() => handleVote(item)}
                    className="flex-1">
                    <View
                        className={
                            item.voted
                                ? 'flex mb-1 p-4 bg-violet-800/90 rounded-l-lg rounded-tr-lg'
                                : 'flex mb-1 p-4 bg-neutral-800/90 rounded-l-lg rounded-tr-lg'
                        }>
                        <View>
                            <View className="flex-row justify-between items-center">
                                <View>
                                    <Text className="text-xl font-bold text-gray-200">
                                        {item.name}
                                    </Text>
                                    <View className="mt-2">
                                        <View className="h-6">
                                            {item.voters?.length > 0 ? (
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
                                <View>
                                    <Icon
                                        name={
                                            item.voted
                                                ? 'checkcircle'
                                                : 'checkcircleo'
                                        }
                                        size={30}
                                        color={
                                            item.voted ? '#a78bfa' : '#737373'
                                        }
                                    />
                                </View>
                            </View>
                        </View>
                    </View>
                </TouchableOpacity>
                <View className="ml-2 flex items-end justify-center">
                    <PickButton onPress={handlePick} />
                </View>
            </View>
        );
    };

    // sort proposals by voters count
    const getOrderedProposals = (p: PlaceProposal[]) =>
        p.sort((a, b) => b.voters.length - a.voters.length);

    if (proposals?.length === 0) {
        return (
            <View className="h-16  flex items-center">
                <Text className="text-gray-400  italic">
                    No available places
                </Text>
            </View>
        );
    }

    return (
        <FlatList
            data={getOrderedProposals(proposals)}
            renderItem={renderItem}
            keyExtractor={item => item.id}
        />
    );
};
