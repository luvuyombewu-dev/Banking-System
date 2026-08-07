import { useState } from "react";

import accountService from "../../services/account/accountService";

import "./transactionForm.css";


const DepositForm = ({ onSuccess }) => {


    const [amount, setAmount] = useState("");

    const [loading, setLoading] = useState(false);



    const handleSubmit = async (e) => {

        e.preventDefault();


        if (!amount || Number(amount) <= 0) {
            return;
        }


        try {

            setLoading(true);


            await accountService.deposit({

                amount: Number(amount)

            });



            setAmount("");



            if (onSuccess) {

                onSuccess();

            }


        } catch (error) {


            console.error(
                "Deposit failed:",
                error
            );


        } finally {


            setLoading(false);


        }

    };



    return (

        <form

            className="transaction-form"

            onSubmit={handleSubmit}

        >


            <label>

                Amount

            </label>



            <input

                type="number"

                placeholder="Enter amount"

                value={amount}

                min="1"

                onChange={(e) =>
                    setAmount(e.target.value)
                }

                required

            />



            <button

                type="submit"

                className="transaction-button"

                disabled={loading}

            >

                {
                    loading
                        ? "Processing..."
                        : "Deposit"
                }

            </button>


        </form>

    );

};


export default DepositForm;