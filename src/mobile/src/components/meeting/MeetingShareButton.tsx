import React from 'react';
import {GestureHandlerRootView, TouchableOpacity} from 'react-native-gesture-handler';
import Icon from 'react-native-vector-icons/AntDesign';

export const MeetingShareButton = (props: any) => (
    <GestureHandlerRootView>
        <TouchableOpacity {...props}>
            <Icon name="export" size={25} color="#a78bfa" />
        </TouchableOpacity>
    </GestureHandlerRootView>
);
