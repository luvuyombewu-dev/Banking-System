import { useEffect, useState } from "react";

import accountService from "../../services/account/accountService";

import SummaryCards from "../../components/dashboard/SummaryCards";

import Loading from "../../components/common/Loading";
import SectionCard from "../../components/common/SectionCard";

import BalanceChart from "../../components/charts/BalanceChart";

import "../../assets/styles/pages/dashboard.css";


const Dashboard = () => {


    const [account, setAccount] = useState(null);

    const [transactions, setTransactions] = useState([]);

    const [loading, setLoading] = useState(true);



    useEffect(() => {


        const loadDashboard = async () => {


            try {


                const accountData =
                    await accountService.getMyAccount();



                setAccount(accountData);



                setTransactions(
                    accountData.transactions || []
                );


            } catch (error) {


                console.error(
                    "Dashboard loading error:",
                    error.response?.data || error
                );


            } finally {


                setLoading(false);


            }


        };


        loadDashboard();


    }, []);




    if (loading) {


        return (
            <Loading message="Loading dashboard..." />
        );


    }




    if (!account) {


        return (

            <div className="dashboard-page">


                <h2>
                    No account data available.
                </h2>


            </div>

        );


    }




    return (

        <main className="dashboard-page">


            <section className="dashboard-header">


                <h1>
                    Dashboard
                </h1>


                <p>
                    Welcome back, manage your banking activity.
                </p>


            </section>




            <section className="dashboard-summary">


                <SummaryCards
                    account={account}
                />


            </section>





            <section className="dashboard-chart">


                <SectionCard title="Balance Overview">


                    <BalanceChart
                        transactions={transactions}
                    />


                </SectionCard>


            </section>


        </main>

    );


};


export default Dashboard;