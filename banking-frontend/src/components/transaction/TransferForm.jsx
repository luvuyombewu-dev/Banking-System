import { useState } from "react";

import accountService from "../../services/account/accountService";

import "./transactionForm.css";


const TransferForm = ({ onSuccess }) => {


    const [accountNumber, setAccountNumber] = useState("");

    const [amount, setAmount] = useState("");

    const [loading, setLoading] = useState(false);



    const handleSubmit = async (e) => {

        e.preventDefault();


        if (!accountNumber || !amount || Number(amount) <= 0) {
            return;
        }



        try {

            setLoading(true);



            await accountService.transfer({

                accountNumber,

                amount: Number(amount)

            });



            setAccountNumber("");

            setAmount("");



            if (onSuccess) {

                onSuccess();

            }



        } catch (error) {


            console.error(
                "Transfer failed:",
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
                Account Number
            </label>


            <input

                type="text"

                placeholder="Enter account number"

                value={accountNumber}

                onChange={(e) =>
                    setAccountNumber(
                        e.target.value
                    )
                }

                required

            />



            <label>
                Amount
            </label>


            <input

                type="number"

                placeholder="Enter amount"

                value={amount}

                min="1"

                onChange={(e) =>
                    setAmount(
                        e.target.value
                    )
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
                        : "Transfer"
                }

            </button>


        </form>

    );

};


export default TransferForm;