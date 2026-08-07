import Card from "../ui/Card";


const SecuritySettings = () => {


    const handleAction = (action) => {

        console.log(`${action} selected`);

    };



    return (

        <Card title="Security">


            <div className="settings-option">


                <div>

                    <h3>
                        Password
                    </h3>

                    <p>
                        Change your account password.
                    </p>

                </div>



                <button

                    className="btn btn-primary"

                    onClick={() =>
                        handleAction("Change Password")
                    }

                >

                    Change Password

                </button>


            </div>





            <div className="settings-option">


                <div>

                    <h3>
                        Two-Factor Authentication
                    </h3>

                    <p>
                        Protect your account with an additional security layer.
                    </p>

                </div>



                <button

                    className="btn btn-secondary"

                    onClick={() =>
                        handleAction("Configure 2FA")
                    }

                >

                    Configure

                </button>


            </div>





            <div className="settings-option">


                <div>

                    <h3>
                        Active Sessions
                    </h3>

                    <p>
                        Review and manage devices signed in to your account.
                    </p>

                </div>



                <button

                    className="btn btn-secondary"

                    onClick={() =>
                        handleAction("View Sessions")
                    }

                >

                    View Sessions

                </button>


            </div>


        </Card>

    );

};


export default SecuritySettings;