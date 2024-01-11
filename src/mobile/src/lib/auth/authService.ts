import {apiClient} from '../api/apiClient';

export const getAuthToken = async (phoneNmber: string) => {
    const response = await apiClient.post('/auth/login', {
        phoneNumber: phoneNmber,
        password: phoneNmber,
    });

    return response.data.token;
};
