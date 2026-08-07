import {
    useEffect,
    useState
} from "react";


import accountService from "../../services/account/accountService";

import Loading from "../../components/common/Loading";
import SectionCard from "../../components/common/SectionCard";
import TransactionTable from "../../components/tables/TransactionTable";


import "../../assets/styles/pages/recentTransactions.css";



const RecentTransactions = () => {


    const [transactions, setTransactions] = useState([]);

    const [loading, setLoading] = useState(true);



    useEffect(() => {


        const loadTransactions = async () => {


            try {


                const response =
                    await accountService.getMyAccount();



                const accountData =
                    response?.data ?? response;



                setTransactions(
                    accountData?.transactions || []
                );


            } catch (error) {


                console.error(
                    "Recent transactions loading error:",
                    error
                );


            } finally {


                setLoading(false);

            }

        };



        loadTransactions();


    }, []);




    if (loading) {

        return (
            <Loading message="Loading transactions..." />
        );

    }




    return (

        <div className="recent-transactions-page">


            <div className="page-header">

                <h1>
                    Recent Transactions
                </h1>


                <p>
                    View your latest banking activity.
                </p>

            </div>



            <SectionCard title="Transaction History">


                <TransactionTable

                    transactions={transactions}

                />


            </SectionCard>



        </div>

    );

};



export default RecentTransactions;