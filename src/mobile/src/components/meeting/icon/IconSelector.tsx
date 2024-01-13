import React from 'react';
import RNPickerSelect from 'react-native-picker-select';
import * as emoji from 'node-emoji';
import {useState} from 'react';

const samples = 30;

const items = new Array(samples)
    .fill(1)
    .map(() => emoji.random())
    .map(e => ({label: e.emoji, value: e.name}));

export const IconSelector = () => {
    const [selectedIcon, setSelectedIcon] = useState<string>(items[0].value);

    const handleIconChange = (value: string) => {
        setSelectedIcon(value);
    };

    return (
        <RNPickerSelect
            value={selectedIcon}
            onValueChange={handleIconChange}
            items={items}
            darkTheme={true}
            style={{inputIOS: {fontSize: 30}}}
            placeholder={{}}
        />
    );
};
