import {FlatList, Text, TouchableOpacity, View} from 'react-native';
import {DateProposal} from '../../../types/Date/DateProposal';
import React from 'react';
import Icon from 'react-native-vector-icons/AntDesign';
import {ParticipantAvatarList} from '../participant/ParticipantAvatarList';
import {ScheduleButton} from './ScheduleButton';
import {getDayName, getHour} from '../../../utils/dateHelpers';
import {useAuthStore} from '../../../lib/auth/authStore';
import {Participant} from '../../../types/Participant';
import {useMeetingsStore} from '../../../lib/meetings/meetingsStore';
import {Meeting} from '../../../types/Meeting';
import {isMeetingAdmin} from '../../../utils/meetingHelpers';

export const DateVotingSlider = ({meeting}: {meeting: Meeting}) => {
    const {user, token} = useAuthStore();
    const {voteOnDateProposal, unvoteOnDateProposal, scheduleDateProposal} = useMeetingsStore();

    const hasVoted = (proposal: DateProposal) => proposal.votes?.some(voter => voter === user);

    const getParticipants = (proposal: DateProposal): Participant[] =>
        proposal.votes?.map(voter => ({
            phoneNumber: voter,
            role: '',
            nick: '',
        }));

    const handleVote = async (proposal: DateProposal) => {
        if (hasVoted(proposal)) {
            await unvoteOnDateProposal(token, meeting?.id, proposal?.id);
            return;
        }

        await voteOnDateProposal(token, meeting?.id, proposal?.id);
    };

    const renderItem = ({item}: {item: DateProposal}) => {
        const handleSchedule = async () => await scheduleDateProposal(token, meeting?.id, item?.id);

        return (
            <View>
                <TouchableOpacity onPress={() => handleVote(item)}>
                    <View
                        className={
                            hasVoted(item)
                                ? 'flex mr-1 p-4 bg-violet-800/90 rounded-l-lg rounded-tr-lg'
                                : 'flex mr-1 p-4 bg-neutral-800/90 rounded-l-lg rounded-tr-lg'
                        }>
                        <View className="flex items-center">
                            <Text className="text-gray-200 font-semibold">
                                {getDayName(new Date(item.proposedDate))}
                            </Text>
                            <Text className="mt-2 text-xl font-bold text-gray-200">
                                {getHour(new Date(item.proposedDate))}
                            </Text>
                        </View>
                        <View className="flex items-center">
                            <View className="my-4">
                                <Icon
                                    name={hasVoted(item) ? 'checkcircle' : 'checkcircleo'}
                                    size={30}
                                    color={hasVoted(item) ? '#a78bfa' : '#737373'}
                                />
                            </View>
                            <View className="h-6">
                                {item.votes?.length > 0 ? (
                                    <ParticipantAvatarList participants={getParticipants(item)} />
                                ) : (
                                    <Text className="text-gray-400 font-semibold">No votes</Text>
                                )}
                            </View>
                        </View>
                    </View>
                </TouchableOpacity>
                {isMeetingAdmin(meeting, user) ? <ScheduleButton onPress={handleSchedule} /> : null}
            </View>
        );
    };

    if (meeting?.dateProposals == null || meeting?.dateProposals?.length === 0) {
        return (
            <View className="h-16 flex items-center">
                <Text className="text-gray-400  italic">No available dates</Text>
            </View>
        );
    }

    return (
        <View>
            <FlatList horizontal data={meeting?.dateProposals} renderItem={renderItem} keyExtractor={item => item.id} />
        </View>
    );
};
