import {
    createContext,
    useContext,
    useState
} from "react";


import storage from "../utils/storage";
import STORAGE_KEYS from "../constants/storageKeys";


const AuthStoreContext = createContext(null);



export const AuthStoreProvider = ({
    children
}) => {


    const [user, setUser] = useState(
        storage.getUser()
    );


    const [token, setToken] = useState(
        storage.getToken()
    );



    const login = (authResponse) => {


        const userData = {

            id: authResponse.userId,

            firstName: authResponse.firstName,

            lastName: authResponse.lastName,

            email: authResponse.email,

            role: authResponse.role

        };



        storage.setToken(
            authResponse.token
        );


        storage.setUser(
            userData
        );



        setToken(
            authResponse.token
        );


        setUser(
            userData
        );

    };




    const logout = () => {


        storage.removeToken();

        storage.removeUser();


        setToken(null);

        setUser(null);

    };




    return (

        <AuthStoreContext.Provider

            value={{

                user,

                token,

                authenticated: Boolean(token),

                login,

                logout

            }}

        >

            {children}

        </AuthStoreContext.Provider>

    );

};



export const useAuthStore = () => {


    const context = useContext(
        AuthStoreContext
    );


    if (!context) {

        throw new Error(
            "useAuthStore must be used inside AuthStoreProvider"
        );

    }


    return context;

};