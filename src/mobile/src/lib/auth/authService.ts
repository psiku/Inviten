import {apiClient} from '../api/apiClient';

export const getAuth = async (phoneNmber: string) => {
    const response = await apiClient.post('/auth/login', {
        phoneNumber: phoneNmber,
        password: phoneNmber,
    });

    return response.data;
};
