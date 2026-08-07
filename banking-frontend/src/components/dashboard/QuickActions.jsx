import React from "react";
import { Link } from "react-router-dom";

import {
    ArrowRightLeft,
    Receipt,
    UserRound
} from "lucide-react";

import Card from "../ui/Card";


const QuickActions = () => {


    const actions = [

        {
            title: "Transfer Money",
            path: "/transfer",
            icon: <ArrowRightLeft />
        },


        {
            title: "View Transactions",
            path: "/transactions",
            icon: <Receipt />
        },


        {
            title: "Manage Account",
            path: "/account",
            icon: <UserRound />
        }

    ];



    return (

        <Card title="Quick Actions">


            <div className="quick-actions">


                {
                    actions.map((action) => (

                        <Link

                            key={action.title}

                            to={action.path}

                            className="action-card"

                        >


                            <div className="action-icon">

                                {action.icon}

                            </div>


                            <span>

                                {action.title}

                            </span>


                        </Link>

                    ))
                }


            </div>


        </Card>

    );

};


export default QuickActions;