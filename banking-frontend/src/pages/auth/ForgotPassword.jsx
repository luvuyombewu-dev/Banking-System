import { useState } from "react";
import { Link } from "react-router-dom";

import Input from "../../components/ui/Input";
import Button from "../../components/ui/Button";

import authService from "../../services/auth/authService";

import "../../assets/styles/pages/forgotPassword.css";

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

        <div className="forgot-password-page">

            <div className="forgot-password-card">

                <div className="forgot-password-header">

                    <div className="forgot-password-icon">
                        🔐
                    </div>

                    <h1>
                        Forgot Password?
                    </h1>

                    <p>
                        Enter the email address associated with your account
                        and we'll send you instructions to reset your password.
                    </p>

                </div>


                <form
                    className="forgot-password-form"
                    onSubmit={handleSubmit}
                >

                    <Input
                        label="Email Address"
                        type="email"
                        name="email"
                        value={email}
                        placeholder="Enter your email address"
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
                            : "Send Reset Instructions"
                        }
                    </Button>

                </form>


                {message && (

                    <div className="forgot-password-message">

                        {message}

                    </div>

                )}


                <div className="forgot-password-footer">

                    <span>
                        Remember your password?
                    </span>

                    <Link to="/login">
                        Back to Login
                    </Link>

                </div>

            </div>

        </div>

    );

};

export default ForgotPassword;