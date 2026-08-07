import {
    createContext,
    useContext,
    useState
} from "react";


const AccountStoreContext = createContext(null);



export const AccountStoreProvider = ({ children }) => {


    const [account, setAccount] = useState(null);



    const setAccountData = (accountData) => {

        setAccount(accountData);

    };



    const clearAccount = () => {

        setAccount(null);

    };



    return (

        <AccountStoreContext.Provider

            value={{

                account,

                setAccountData,

                clearAccount

            }}

        >

            {children}

        </AccountStoreContext.Provider>

    );

};




export const useAccountStore = () => {


    const context = useContext(
        AccountStoreContext
    );


    if (!context) {

        throw new Error(
            "useAccountStore must be used within AccountStoreProvider"
        );

    }


    return context;

};