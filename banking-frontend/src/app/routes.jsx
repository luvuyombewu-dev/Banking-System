import {
    Routes,
    Route
} from "react-router-dom";


import Splash from "../pages/splash/Splash";


import AuthLayout from "../layouts/AuthLayout";
import DashboardLayout from "../layouts/DashboardLayout";


import Login from "../pages/auth/Login";
import Register from "../pages/auth/Register";
import ForgotPassword from "../pages/auth/ForgotPassword";
import ResetPassword from "../pages/auth/ResetPassword";


import Dashboard from "../pages/dashboard/Dashboard";
import Account from "../pages/account/Account";
import Transactions from "../pages/transactions/Transactions";
import RecentTransactions from "../pages/transactions/RecentTransactions";
import Transfer from "../pages/transfer/Transfer";


import Profile from "../pages/profile/Profile";
import Settings from "../pages/settings/Settings";


import NotFound from "../pages/errors/NotFound";
import ServerError from "../pages/errors/ServerError";


import ProtectedRoute from "../routes/ProtectedRoute";



const AppRoutes = () => {

    return (

        <Routes>


            <Route
                path="/"
                element={<Splash />}
            />



            {/* Authentication Routes */}

            <Route element={<AuthLayout />}>


                <Route
                    path="/login"
                    element={<Login />}
                />


                <Route
                    path="/register"
                    element={<Register />}
                />


                <Route
                    path="/forgot-password"
                    element={<ForgotPassword />}
                />


                <Route
                    path="/reset-password"
                    element={<ResetPassword />}
                />


            </Route>





            {/* Protected Routes */}

            <Route element={<ProtectedRoute />}>


                <Route element={<DashboardLayout />}>


                    <Route
                        path="/dashboard"
                        element={<Dashboard />}
                    />


                    <Route
                        path="/account"
                        element={<Account />}
                    />


                    <Route
                        path="/transactions"
                        element={<Transactions />}
                    />


                    <Route
                        path="/recent-transactions"
                        element={<RecentTransactions />}
                    />


                    <Route
                        path="/transfer"
                        element={<Transfer />}
                    />


                    <Route
                        path="/profile"
                        element={<Profile />}
                    />


                    <Route
                        path="/settings"
                        element={<Settings />}
                    />


                </Route>


            </Route>





            {/* Error Routes */}

            <Route
                path="/500"
                element={<ServerError />}
            />


            <Route
                path="*"
                element={<NotFound />}
            />


        </Routes>

    );

};



export default AppRoutes;
