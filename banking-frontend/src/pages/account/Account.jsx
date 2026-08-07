import accountService from "../../services/account/accountService";

import useFetch from "../../hooks/useFetch";

import Loading from "../../components/common/Loading";
import InfoCard from "../../components/cards/InfoCard";

import formatCurrency from "../../utils/formatCurrency";

import "../../assets/styles/pages/account.css";


const Account = () => {


    const {
        data: account,
        loading,
        error
    } = useFetch(
        () => accountService.getMyAccount(),
        []
    );



    if (loading) {

        return <Loading />;

    }



    if (error) {

        return (

            <h2>
                Unable to load account details
            </h2>

        );

    }



    if (!account) {

        return (

            <h2>
                No account found
            </h2>

        );

    }



    return (

        <div className="account-page">


            <h1>
                Account Details
            </h1>



            <div className="account-grid">


                <InfoCard

                    title="Account Number"

                    value={account.accountNumber}

                />



                <InfoCard

                    title="Balance"

                    value={formatCurrency(account.balance)}

                />



                <InfoCard

                    title="Account Holder"

                    value={account.accountHolder}

                />


            </div>


        </div>

    );

};


export default Account;