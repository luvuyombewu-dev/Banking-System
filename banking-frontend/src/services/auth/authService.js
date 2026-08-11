import axiosClient from "../../api/axiosClient";
import API_ENDPOINTS from "../../api/apiEndpoints";
import apiErrorHandler from "../../api/apiErrorHandler";

import storage from "../../utils/storage";
import STORAGE_KEYS from "../../constants/storageKeys";

const authService = {

    login: async (credentials) => {

        try {

            const response = await axiosClient.post(
                API_ENDPOINTS.AUTH.LOGIN,
                credentials
            );

            return response.data;

        } catch (error) {

            throw apiErrorHandler(error);

        }

    },


    register: async (userData) => {

        try {

            const response = await axiosClient.post(
                API_ENDPOINTS.AUTH.REGISTER,
                userData
            );

            return response.data;

        } catch (error) {

            throw apiErrorHandler(error);

        }

    },


    forgotPassword: async (emailData) => {

        try {

            const response = await axiosClient.post(
                API_ENDPOINTS.AUTH.FORGOT_PASSWORD,
                emailData
            );

            return response.data;

        } catch (error) {

            throw apiErrorHandler(error);

        }

    },


    resetPassword: async (resetData) => {

        try {

            const response = await axiosClient.post(
                API_ENDPOINTS.AUTH.RESET_PASSWORD,
                resetData
            );

            return response.data;

        } catch (error) {

            throw apiErrorHandler(error);

        }

    },


    changePassword: async (passwordData) => {

        try {

            const response = await axiosClient.put(
                API_ENDPOINTS.AUTH.CHANGE_PASSWORD,
                passwordData
            );

            return response.data;

        } catch (error) {

            throw apiErrorHandler(error);

        }

    },


    logout: () => {

        storage.remove(
            STORAGE_KEYS.TOKEN
        );

        storage.remove(
            STORAGE_KEYS.USER
        );

    }

};

export default authService;