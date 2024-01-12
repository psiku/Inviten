import React from 'react';
import {FlatList, Text, TouchableOpacity, View} from 'react-native';
import {PlaceProposal} from '../../../types/Place/PlaceProposal';
import Icon from 'react-native-vector-icons/AntDesign';
import {ParticipantAvatarList} from '../participant/ParticipantAvatarList';
import {PickButton} from './PickButton';
import {Meeting} from '../../../types/Meeting';
import {useAuthStore} from '../../../lib/auth/authStore';
import {useMeetingsStore} from '../../../lib/meetings/meetingsStore';
import {Participant} from '../../../types/Participant';

export const PlaceVotingList = ({meeting}: {meeting: Meeting}) => {
    const {user, token} = useAuthStore();
    const {voteOnPlaceProposal, unvoteOnPlaceProposal, pickPlaceProposal} = useMeetingsStore();

    const hasVoted = (proposal: PlaceProposal) => proposal.votes?.some(voter => voter === user);

    const getParticipants = (proposal: PlaceProposal): Participant[] =>
        proposal.votes?.map(voter => ({
            phoneNumber: voter,
            role: '',
            nick: '',
        }));

    const handleVote = async (proposal: PlaceProposal) => {
        if (hasVoted(proposal)) {
            await unvoteOnPlaceProposal(token, meeting?.id, proposal?.id);
            return;
        }

        console.log('voteOnPlaceProposal', token, meeting?.id, proposal?.id);
        await voteOnPlaceProposal(token, meeting?.id, proposal?.id);
    };

    const renderItem = ({item}: {item: PlaceProposal}) => {
        const handlePick = async () => {
            await pickPlaceProposal(token, meeting?.id, item?.id);
        };

        return (
            <View className="flex-row">
                <TouchableOpacity onPress={() => handleVote(item)} className="flex-1">
                    <View
                        className={
                            hasVoted(item)
                                ? 'flex mb-1 p-4 bg-violet-800/90 rounded-l-lg rounded-tr-lg'
                                : 'flex mb-1 p-4 bg-neutral-800/90 rounded-l-lg rounded-tr-lg'
                        }>
                        <View>
                            <View className="flex-row justify-between items-center">
                                <View>
                                    <Text className="text-xl font-bold text-gray-200">{item.name}</Text>
                                    <View className="mt-2">
                                        <View className="h-6">
                                            {item.votes?.length > 0 ? (
                                                <ParticipantAvatarList participants={getParticipants(item)} />
                                            ) : (
                                                <Text className="text-gray-400 font-semibold">No votes</Text>
                                            )}
                                        </View>
                                    </View>
                                </View>
                                <View>
                                    <Icon
                                        name={hasVoted(item) ? 'checkcircle' : 'checkcircleo'}
                                        size={30}
                                        color={hasVoted(item) ? '#a78bfa' : '#737373'}
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
    const getOrderedProposals = (p: PlaceProposal[]) => p?.sort((a, b) => b.votes?.length - a.votes?.length);

    if (meeting?.placeProposals == null || meeting?.placeProposals?.length === 0) {
        return (
            <View className="h-16  flex items-center">
                <Text className="text-gray-400  italic">No available places</Text>
            </View>
        );
    }

    return (
        <FlatList
            data={getOrderedProposals(meeting?.placeProposals)}
            renderItem={renderItem}
            keyExtractor={item => item.id}
        />
    );
};
