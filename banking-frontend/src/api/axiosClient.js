import axios from "axios";

import appConfig from "../config/appConfig";
import storage from "../utils/storage";
import STORAGE_KEYS from "../constants/storageKeys";


const axiosClient = axios.create({

    baseURL: appConfig.api.baseURL,

    timeout: appConfig.api.timeout,

    headers: {

        "Content-Type": "application/json"

    }

});



axiosClient.interceptors.request.use(

    (config) => {


        const token = storage.get(
            STORAGE_KEYS.TOKEN
        );


        if (token) {

            config.headers.Authorization =
                `Bearer ${token}`;

        }


        return config;

    },


    (error) => {

        return Promise.reject(error);

    }

);



axiosClient.interceptors.response.use(

    (response) => response,


    (error) => {


        if (
            error.response &&
            error.response.status === 401
        ) {


            storage.removeToken();

            storage.removeUser();


            window.location.href = "/login";

        }


        return Promise.reject(error);

    }

);


export default axiosClient;