import axiosClient from "../../api/axiosClient";
import API_ENDPOINTS from "../../api/apiEndpoints";
import apiErrorHandler from "../../api/apiErrorHandler";


const accountService = {


    getMyAccount: async () => {

        try {

            const response = await axiosClient.get(
                API_ENDPOINTS.ACCOUNT.MY_ACCOUNT
            );

            return response.data;

        } catch (error) {

            throw apiErrorHandler(error);

        }

    },


    createAccount: async () => {

        try {

            const response = await axiosClient.post(
                API_ENDPOINTS.ACCOUNT.CREATE
            );

            return response.data;

        } catch (error) {

            throw apiErrorHandler(error);

        }

    },


    deposit: async (data) => {

        try {

            const response = await axiosClient.post(
                API_ENDPOINTS.ACCOUNT.DEPOSIT,
                data
            );

            return response.data;

        } catch (error) {

            throw apiErrorHandler(error);

        }

    },


    withdraw: async (data) => {

        try {

            const response = await axiosClient.post(
                API_ENDPOINTS.ACCOUNT.WITHDRAW,
                data
            );

            return response.data;

        } catch (error) {

            throw apiErrorHandler(error);

        }

    },


    transfer: async (data) => {

        try {

            const response = await axiosClient.post(
                API_ENDPOINTS.ACCOUNT.TRANSFER,
                data
            );

            return response.data;

        } catch (error) {

            throw apiErrorHandler(error);

        }

    }

};


export default accountService;