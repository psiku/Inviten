import React from 'react';
import {Text, View} from 'react-native';
import Icon from 'react-native-vector-icons/AntDesign';
import {PlaceProposal} from '../../../types/Place/PlaceProposal';

export const PlacePickedCard = ({place}: {place: PlaceProposal}) => {
    return (
        <View className="h-20 flex justify-center p-4 bg-lime-400 rounded-l-lg rounded-tr-lg">
            <View className="flex-row items-center justify-between">
                <View className="flex-row space-x-4">
                    <Icon name="enviromento" size={25} color="black" />

                    <Text className="text-xl font-bold text-black">{place?.name}</Text>
                </View>
                <View>
                    <Text className="font-bold black">Picked</Text>
                </View>
            </View>
        </View>
    );
};
