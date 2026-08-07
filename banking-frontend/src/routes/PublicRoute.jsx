import {
    Navigate,
    Outlet
} from "react-router-dom";

import {
    useAuthStore
} from "../store/authStore";

import Loading from "../components/common/Loading";


const PublicRoute = () => {


    const {
        authenticated,
        loading
    } = useAuthStore();



    if (loading) {
        return <Loading />;
    }



    if (authenticated) {
        return (
            <Navigate
                to="/dashboard"
                replace
            />
        );
    }



    return <Outlet />;


};


export default PublicRoute;