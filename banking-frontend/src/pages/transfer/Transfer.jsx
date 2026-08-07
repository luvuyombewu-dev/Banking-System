import TransferForm from "../../components/transaction/TransferForm";

import SectionCard from "../../components/common/SectionCard";

import "../../assets/styles/pages/transactions.css";


const Transfer = () => {



    const handleSuccess = () => {

        console.log(
            "Transfer completed successfully"
        );

    };



    return (

        <div className="transactions-page">



            <div className="page-header">


                <h1>
                    Transfer Money
                </h1>


                <p>
                    Transfer money between accounts
                </p>


            </div>





            <SectionCard

                title="Transfer Money"

            >


                <TransferForm

                    onSuccess={handleSuccess}

                />


            </SectionCard>




        </div>

    );

};


export default Transfer;