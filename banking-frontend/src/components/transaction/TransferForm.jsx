import { useState } from "react";
import { toast } from "react-toastify";

import accountService from "../../services/account/accountService";

import "./transactionForm.css";

const TransferForm = ({ onSuccess }) => {

    const [accountNumber, setAccountNumber] = useState("");

    const [amount, setAmount] = useState("");

    const [loading, setLoading] = useState(false);


    const handleSubmit = async (e) => {

        e.preventDefault();


        if (
            !accountNumber ||
            !amount ||
            Number(amount) <= 0
        ) {
            toast.error("Please enter a valid account number and amount.");
            return;
        }


        try {

            setLoading(true);


            await accountService.transfer({

                receiverAccountNumber: accountNumber,

                amount: Number(amount)

            });


            toast.success(
                `Transfer of R ${Number(amount).toFixed(2)} was successful.`
            );


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


            toast.error(
                error?.response?.data?.message ||
                error?.message ||
                "Transfer failed. Please try again."
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

                step="0.01"

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