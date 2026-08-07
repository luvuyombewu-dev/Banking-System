import {
    createContext,
    useContext
} from "react";


import {
    toast
} from "react-toastify";



const NotificationContext = createContext(null);





export const NotificationProvider = ({
    children
}) => {



    const success = (message) => {

        toast.success(message);

    };




    const error = (message) => {

        toast.error(message);

    };




    const info = (message) => {

        toast.info(message);

    };




    const warning = (message) => {

        toast.warning(message);

    };




    return (


        <NotificationContext.Provider

            value={{

                success,

                error,

                info,

                warning

            }}

        >


            {children}


        </NotificationContext.Provider>


    );

};







export const useNotification = () => {


    const context = useContext(
        NotificationContext
    );



    if (!context) {

        throw new Error(
            "useNotification must be used inside NotificationProvider"
        );

    }



    return context;


};