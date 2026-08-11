import {
    LineChart,
    Line,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    ResponsiveContainer
} from "recharts";

import formatCurrency from "../../utils/formatCurrency";
import formatDate from "../../utils/formatDate";

import "../../assets/styles/components/charts.css";


const BalanceChart = ({
    transactions = []
}) => {


    if (!transactions.length) {

        return (

            <div className="chart-card">

                <p>
                    No transaction data available.
                </p>

            </div>

        );

    }



    const chartData = transactions.map(
        (transaction) => ({

            date: formatDate(transaction.date),

            balance: transaction.amount

        })
    );



    return (

        <div className="chart-card">






            <div className="chart-container">


                <ResponsiveContainer
                    width="100%"
                    height="100%"
                >


                    <LineChart
                        data={chartData}
                    >


                        <CartesianGrid
                            strokeDasharray="3 3"
                        />



                        <XAxis
                            dataKey="date"
                        />



                        <YAxis />



                        <Tooltip
                            formatter={(value) =>
                                formatCurrency(value)
                            }
                        />



                        <Line
                            type="monotone"
                            dataKey="balance"
                            strokeWidth={3}
                        />


                    </LineChart>


                </ResponsiveContainer>


            </div>


        </div>

    );

};


export default BalanceChart;