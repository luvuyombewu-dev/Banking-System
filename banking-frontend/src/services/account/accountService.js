import axiosClient from "../../api/axiosClient";
import API_ENDPOINTS from "../../api/apiEndpoints";

const accountService = {

    getMyAccount: async () => {

        const response = await axiosClient.get(
            API_ENDPOINTS.ACCOUNT.MY_ACCOUNT
        );

        return response.data;

    },

    deposit: async (data) => {

        const response = await axiosClient.post(
            API_ENDPOINTS.ACCOUNT.DEPOSIT,
            data
        );

        return response.data;

    },

    withdraw: async (data) => {

        const response = await axiosClient.post(
            API_ENDPOINTS.ACCOUNT.WITHDRAW,
            data
        );

        return response.data;

    },

    transfer: async (data) => {

        const response = await axiosClient.post(
            API_ENDPOINTS.ACCOUNT.TRANSFER,
            data
        );

        return response.data;

    }

};

export default accountService;