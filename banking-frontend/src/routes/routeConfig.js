import Login from "../pages/auth/Login";
import Register from "../pages/auth/Register";
import ForgotPassword from "../pages/auth/ForgotPassword";

import Dashboard from "../pages/dashboard/Dashboard";
import Account from "../pages/account/Account";
import Transactions from "../pages/transactions/Transactions";
import Transfer from "../pages/transfer/Transfer";
import RecentTransactions from "../pages/recentTransactions/RecentTransactions";
import Profile from "../pages/profile/Profile";
import Settings from "../pages/settings/Settings";


const routeConfig = {


    publicRoutes: [


        {
            path: "/login",
            element: <Login />
        },


        {
            path: "/register",
            element: <Register />
        },


        {
            path: "/forgot-password",
            element: <ForgotPassword />
        }


    ],





    protectedRoutes: [


        {
            path: "/dashboard",
            element: <Dashboard />
        },


        {
            path: "/account",
            element: <Account />
        },


        {
            path: "/transactions",
            element: <Transactions />
        },


        {
            path: "/transfer",
            element: <Transfer />
        },


        {
            path: "/recent-transactions",
            element: <RecentTransactions />
        },


        {
            path: "/profile",
            element: <Profile />
        },


        {
            path: "/settings",
            element: <Settings />
        }


    ]


};


export default routeConfig;