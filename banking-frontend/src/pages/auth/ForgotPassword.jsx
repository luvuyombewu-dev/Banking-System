import { useState } from "react";
import { Link } from "react-router-dom";

import Input from "../../components/ui/Input";
import Button from "../../components/ui/Button";

import authService from "../../services/auth/authService";

import "../../assets/styles/pages/login.css";


const ForgotPassword = () => {


    const [email, setEmail] = useState("");

    const [loading, setLoading] = useState(false);

    const [message, setMessage] = useState("");



    const handleSubmit = async (e) => {

        e.preventDefault();


        try {

            setLoading(true);

            setMessage("");


            await authService.forgotPassword({
                email
            });


            setMessage(
                "If this email exists, password reset instructions will be sent."
            );


        } catch (error) {

            setMessage(
                "Unable to process password reset request."
            );


        } finally {

            setLoading(false);

        }

    };



    return (

        <div className="auth-page">


            <div className="auth-card">


                <h2>
                    Forgot Password
                </h2>



                <p>
                    Enter your email address to reset your password.
                </p>



                <form onSubmit={handleSubmit}>


                    <Input

                        label="Email Address"

                        type="email"

                        name="email"

                        value={email}

                        placeholder="Enter your email"

                        onChange={(e) =>
                            setEmail(e.target.value)
                        }

                        required

                    />



                    <Button

                        type="submit"

                        disabled={loading}

                    >

                        {loading
                            ? "Sending..."
                            : "Reset Password"
                        }

                    </Button>


                </form>



                {message && (

                    <p className="success-message">

                        {message}

                    </p>

                )}



                <Link to="/login">

                    Back to Login

                </Link>


            </div>


        </div>

    );

};


export default ForgotPassword;