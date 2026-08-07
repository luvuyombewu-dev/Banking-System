import { useState } from "react";

import accountService from "../../services/account/accountService";

import "./transactionForm.css";


const WithdrawForm = ({ onSuccess }) => {


    const [amount, setAmount] = useState("");

    const [error, setError] = useState("");




    const handleSubmit = async (e) => {


        e.preventDefault();


        setError("");



        const withdrawAmount = Number(amount);



        if (withdrawAmount <= 0) {

            setError(
                "Enter a valid amount"
            );

            return;

        }



        try {


            await accountService.withdraw({

                amount: withdrawAmount

            });



            setAmount("");



            if (onSuccess) {

                onSuccess();

            }



        } catch (error) {


            console.error(
                "Withdraw failed:",
                error
            );


            setError(
                "Withdrawal failed. Please try again."
            );


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


                onChange={
                    (e) =>
                    setAmount(
                        e.target.value
                    )
                }


                required


            />




            {
                error && (

                    <p className="form-error">

                        {error}

                    </p>

                )
            }





            <button

                type="submit"

                className="transaction-button"

            >

                Withdraw

            </button>



        </form>


    );

};


export default WithdrawForm;