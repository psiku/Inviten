import React from 'react';
import {Participant} from '../../../types/Participant';
import {AvatarList} from '../../common/avatar/AvatarList';

const getShortName = (name: string) => name[0];

export const ParticipantAvatarList = ({
    participants,
}: {
    participants: Participant[];
}) => (
    <AvatarList
        shortNames={participants.map(participant =>
            getShortName(participant.name),
        )}
    />
);
