import {
    createContext,
    useContext,
    useState
} from "react";


const TransactionContext = createContext(null);



export const TransactionProvider = ({ children }) => {


    const [transactions, setTransactions] = useState([]);



    const setTransactionList = (transactionData) => {

        setTransactions(
            transactionData
        );

    };





    const addTransaction = (transaction) => {

        setTransactions(
            (previousTransactions) => [

                transaction,

                ...previousTransactions

            ]
        );

    };





    const clearTransactions = () => {

        setTransactions([]);

    };





    return (

        <TransactionContext.Provider

            value={{

                transactions,

                setTransactionList,

                addTransaction,

                clearTransactions

            }}

        >

            {children}

        </TransactionContext.Provider>

    );

};





export const useTransactionStore = () => {

    return useContext(
        TransactionContext
    );

};