import "../../assets/styles/components/summaryCards.css";

const SummaryCards = ({ account }) => {

    return (

        <div className="summary-cards">


            <div className="summary-card">

                <h4>
                    Current Balance
                </h4>

                <h2>
                    R {account?.balance?.toFixed(2) || "0.00"}
                </h2>

            </div>




            <div className="summary-card">

                <h4>
                    Account Number
                </h4>

                <h2>
                    {account?.accountNumber || "N/A"}
                </h2>

            </div>




            <div className="summary-card">

                <h4>
                    Account Status
                </h4>

                <h2 className="status-active">
                    Active
                </h2>

            </div>


        </div>

    );

};

export default SummaryCards;