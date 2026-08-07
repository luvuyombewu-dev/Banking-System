import formatCurrency from "../../utils/formatCurrency";
import formatDate from "../../utils/formatDate";

import "./transactionTable.css";

const TransactionTable = ({
    transactions = []
}) => {

    if (!transactions.length) {

        return (
            <p>
                No transactions available.
            </p>
        );

    }

    return (

        <div className="transaction-table-container">

            <table className="transaction-table">

                <thead>

                    <tr>

                        <th>Type</th>

                        <th>Amount</th>

                        <th>Date</th>

                    </tr>

                </thead>

                <tbody>

                    {transactions.map((transaction) => (

                        <tr key={transaction.id}>

                            <td>
                                {transaction.type.replace("_", " ")}
                            </td>

                            <td>
                                {formatCurrency(transaction.amount)}
                            </td>

                            <td>
                                {formatDate(transaction.date)}
                            </td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </div>

    );

};

export default TransactionTable;