import {
    createContext,
    useContext
} from "react";

import {
    toast
} from "react-toastify";


export const NotificationContext = createContext(null);



export const NotificationProvider = ({ children }) => {


    const success = (message) => {

        toast.success(message);

    };


    const error = (message) => {

        toast.error(message);

    };


    const info = (message) => {

        toast.info(message);

    };


    return (

        <NotificationContext.Provider

            value={{
                success,
                error,
                info
            }}

        >

            {children}

        </NotificationContext.Provider>

    );

};



export const useNotification = () => {

    return useContext(NotificationContext);

};