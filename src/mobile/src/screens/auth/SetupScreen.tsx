import React, {useState} from 'react';
import {Navigation} from 'react-native-navigation';
import {Button, Colors, Text, TextField, View} from 'react-native-ui-lib';
import {homeRoot} from '../../navigation/roots';

export const SetupScreen = () => {
    const phoneRegex = /^\d{3}\s?\d{3}\s?\d{3}$/;
    const [phoneNumber, setPhoneNumber] = useState<string>('');

    const isPhoneNumberValid = (value: string) => phoneRegex.test(value);

    const authenticate = () => {
        // TODO: Some auth logic

        Navigation.setRoot(homeRoot);
    };

    return (
        <View flex paddingH-25 paddingT-80>
            <Text grey80 text20>
                Hello!
            </Text>
            <Text grey60 text70>
                Please provide your mobile phone number to authenticate
            </Text>
            <View marginT-50 marginB-50 center>
                <TextField
                    backgroundDark
                    blue30
                    text40
                    maxLength={9}
                    keyboardType="numeric"
                    placeholder="123 777 888"
                    value={phoneNumber}
                    onChangeText={text => setPhoneNumber(text)}
                />
            </View>
            <View center>
                <Button
                    text70
                    black
                    backgroundColor={Colors.grey80}
                    disabledBackgroundColor={Colors.grey20}
                    label="Continue"
                    disabled={!isPhoneNumberValid(phoneNumber)}
                    onPress={authenticate}
                />
            </View>
        </View>
    );
};
