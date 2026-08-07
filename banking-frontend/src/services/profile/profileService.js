import axiosClient from "../../api/axiosClient";
import apiErrorHandler from "../../api/apiErrorHandler";
import API_ENDPOINTS from "../../api/apiEndpoints";

const profileService = {

    getProfile: async () => {

        try {

            const response = await axiosClient.get(
                API_ENDPOINTS.ACCOUNT.MY_ACCOUNT
            );

            return response.data;

        } catch (error) {

            throw apiErrorHandler(error);

        }

    }

};

export default profileService;