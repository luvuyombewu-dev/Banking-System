import Card from "../ui/Card";


const BalanceCard = ({
    balance = 0
}) => {


    return (

        <Card title="Current Balance">


            <div className="balance-card">


                <span className="balance-label">
                    Available Balance
                </span>



                <h1 className="balance-value">

                    R {Number(balance).toFixed(2)}

                </h1>


            </div>


        </Card>

    );

};


export default BalanceCard;