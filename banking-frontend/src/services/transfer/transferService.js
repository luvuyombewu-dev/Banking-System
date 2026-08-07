import axiosClient from "../../api/axiosClient";
import API_ENDPOINTS from "../../api/apiEndpoints";
import apiErrorHandler from "../../api/apiErrorHandler";


const transferService = {


    transfer: async (transferData) => {

        try {

            const response = await axiosClient.post(
                API_ENDPOINTS.ACCOUNT.TRANSFER,
                transferData
            );

            return response.data;

        } catch (error) {

            throw apiErrorHandler(error);

        }

    }


};


export default transferService;