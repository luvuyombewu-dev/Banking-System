import { useState } from "react";

import DepositForm from "../../components/transaction/DepositForm";
import WithdrawForm from "../../components/transaction/WithdrawForm";
import TransferForm from "../../components/transaction/TransferForm";

import SectionCard from "../../components/common/SectionCard";

import "../../assets/styles/pages/transactions.css";


const Transactions = () => {


    const [refresh, setRefresh] = useState(false);



    const handleSuccess = () => {

        setRefresh((previous) => !previous);

    };



    return (

        <div className="transactions-page">


            <div className="page-header">


                <h1>
                    Transactions
                </h1>


                <p>
                    Deposit, withdraw and transfer money
                </p>


            </div>





            <div className="transaction-grid">



                <SectionCard

                    title="Deposit Money"

                >

                    <DepositForm

                        onSuccess={handleSuccess}

                    />

                </SectionCard>







                <SectionCard

                    title="Withdraw Money"

                >

                    <WithdrawForm

                        onSuccess={handleSuccess}

                    />

                </SectionCard>







                <SectionCard

                    title="Transfer Money"

                >

                    <TransferForm

                        onSuccess={handleSuccess}

                    />

                </SectionCard>



            </div>




        </div>

    );

};


export default Transactions;