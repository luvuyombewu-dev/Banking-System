import Card from "../ui/Card";


const AccountCard = ({
    account
}) => {


    return (

        <Card title="Account Details">


            <div className="account-card">


                <div className="account-item">

                    <span>
                        Account Number
                    </span>

                    <strong>
                        {account?.accountNumber || "N/A"}
                    </strong>

                </div>



                <div className="account-item">

                    <span>
                        Account Holder
                    </span>

                    <strong>
                        {account?.accountHolder || "N/A"}
                    </strong>

                </div>



                <div className="account-item">

                    <span>
                        Status
                    </span>

                    <strong className="text-success">
                        Active
                    </strong>

                </div>


            </div>


        </Card>

    );

};


export default AccountCard;