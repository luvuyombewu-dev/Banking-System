import Card from "../ui/Card";


const ProfileCard = ({ user }) => {


    if (!user) return null;



    return (

        <Card title="User Information">


            <div className="user-information-card">


                <p>

                    <strong>
                        Name:
                    </strong>

                    {" "}

                    {user.firstName} {user.lastName}

                </p>




                <p>

                    <strong>
                        Email:
                    </strong>

                    {" "}

                    {user.email}

                </p>




                <p>

                    <strong>
                        Role:
                    </strong>

                    {" "}

                    {user.role || "Customer"}

                </p>


            </div>


        </Card>

    );

};


export default ProfileCard;