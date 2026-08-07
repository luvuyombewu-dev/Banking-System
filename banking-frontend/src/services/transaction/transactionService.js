import axiosClient from "../../api/axiosClient";
import API_ENDPOINTS from "../../api/apiEndpoints";
import apiErrorHandler from "../../api/apiErrorHandler";


const transactionService = {


    getTransactions: async () => {

        try {

            const response = await axiosClient.get(
                API_ENDPOINTS.TRANSACTION.GET_TRANSACTIONS
            );

            return response.data;

        } catch (error) {

            throw apiErrorHandler(error);

        }

    }


};


export default transactionService;