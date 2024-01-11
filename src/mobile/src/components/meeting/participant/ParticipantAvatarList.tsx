import React from 'react';
import {Participant} from '../../../types/Participant';
import {AvatarList} from '../../common/avatar/AvatarList';
import {Text} from 'react-native';

const getShortName = (participant: Participant) =>
    participant.nick != null && participant.nick.length > 0 ? participant.nick[0] : participant.phoneNumber[0];

export const ParticipantAvatarList = ({participants}: {participants: Participant[]}) => {
    if (participants?.length > 0) {
        return <AvatarList shortNames={participants.map(participant => getShortName(participant))} />;
    }

    return <Text className="text-gray-400 font-semibold">No participants</Text>;
};
