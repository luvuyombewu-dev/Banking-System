import {
    useEffect,
    useState
} from "react";


import profileService from "../../services/profile/profileService";

import Loading from "../../components/common/Loading";
import SectionCard from "../../components/common/SectionCard";

import "../../assets/styles/pages/profile.css";


const Profile = () => {


    const [user, setUser] = useState(null);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState(null);



    useEffect(() => {


        const loadProfile = async () => {


            try {


                const profileData =
                    await profileService.getProfile();


                setUser(profileData);


            } catch (err) {


                console.error(
                    "Profile loading error:",
                    err
                );


                setError(
                    "Unable to load profile data."
                );


            } finally {


                setLoading(false);


            }


        };


        loadProfile();


    }, []);





    if (loading) {

        return (
            <Loading message="Loading profile..." />
        );

    }




    if (error) {

        return (

            <div className="profile-page">

                <h2>
                    {error}
                </h2>

            </div>

        );

    }




    if (!user) {

        return (

            <div className="profile-page">

                <h2>
                    No profile data available.
                </h2>

            </div>

        );

    }



    const names =
        user.accountHolder?.split(" ") || [];


    const firstName =
        names[0] || "";


    const initials =
        firstName
            .charAt(0)
            .toUpperCase();



    return (

        <div className="profile-page">


            <div className="page-header">

                <h1>
                    Profile
                </h1>

            </div>





            <SectionCard >


                <div className="profile-container">


                    <div className="profile-user">


                        <div className="profile-avatar">

                            {initials}

                        </div>



                        <div>


                            <h2>
                                {user.accountHolder}
                            </h2>


                            <p>
                                Banking Customer
                            </p>


                        </div>


                    </div>





                    <div className="profile-details">


                        <div className="profile-item">

                            <span>
                                Account Number
                            </span>


                            <strong>
                                {user.accountNumber}
                            </strong>


                        </div>




                        <div className="profile-item">

                            <span>
                                Available Balance
                            </span>


                            <strong>
                                R {user.balance.toFixed(2)}
                            </strong>


                        </div>



                        <div className="profile-item">

                            <span>
                                Account Status
                            </span>


                            <strong className="active-status">

                                Active

                            </strong>


                        </div>


                    </div>



                </div>



            </SectionCard>



        </div>

    );

};


export default Profile;