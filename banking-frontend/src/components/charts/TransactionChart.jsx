import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    Tooltip,
    ResponsiveContainer,
    CartesianGrid
} from "recharts";


const TransactionChart = ({
    transactions = []
}) => {


    const data = transactions.map(
        (transaction) => ({

            type: transaction.type,

            amount: Number(
                transaction.amount
            )

        })
    );



    return (

        <div className="chart-card">


            <h3>
                Transaction Activity
            </h3>



            <div className="chart-container">


                <ResponsiveContainer
                    width="100%"
                    height="100%"
                >


                    <BarChart
                        data={data}
                    >


                        <CartesianGrid
                            strokeDasharray="3 3"
                        />



                        <XAxis

                            dataKey="type"

                        />



                        <YAxis />



                        <Tooltip />



                        <Bar

                            dataKey="amount"

                        />


                    </BarChart>


                </ResponsiveContainer>


            </div>


        </div>

    );

};


export default TransactionChart;