import formatCurrency from "../../utils/formatCurrency";
import formatDate from "../../utils/formatDate";

import "./transactionTable.css";

const TransactionTable = ({
    transactions = []
}) => {

    if (!transactions.length) {

        return (
            <div className="empty-transactions">
                No transactions available.
            </div>
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

                                <span
                                    className={`type-badge ${transaction.type
                                        ?.toLowerCase()
                                        .replace("_", "-")}`}
                                >
                                    {transaction.type
                                        ?.replace("_", " ")}
                                </span>

                            </td>


                            <td className="transaction-amount">

                                {formatCurrency(
                                    transaction.amount
                                )}

                            </td>


                            <td className="transaction-date">

                                {formatDate(
                                    transaction.date
                                )}

                            </td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </div>

    );

};

export default TransactionTable;