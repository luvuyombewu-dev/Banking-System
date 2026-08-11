import { useState } from "react";
import { Link, useSearchParams, useNavigate } from "react-router-dom";

import Input from "../../components/ui/Input";
import Button from "../../components/ui/Button";

import authService from "../../services/auth/authService";

import "../../assets/styles/pages/resetPassword.css";


const ResetPassword = () => {

    const [searchParams] = useSearchParams();

    const navigate = useNavigate();

    const token = searchParams.get("token");


    const [password, setPassword] = useState("");

    const [confirmPassword, setConfirmPassword] = useState("");

    const [loading, setLoading] = useState(false);

    const [message, setMessage] = useState("");

    const [error, setError] = useState("");


    const handleSubmit = async (e) => {

        e.preventDefault();

        setMessage("");
        setError("");


        if (!token) {

            setError(
                "Invalid or missing password reset token."
            );

            return;

        }


        if (password.length < 8) {

            setError(
                "Password must be at least 8 characters."
            );

            return;

        }


        if (password !== confirmPassword) {

            setError(
                "Passwords do not match."
            );

            return;

        }


        try {

            setLoading(true);

            await authService.resetPassword({

                token,

                password

            });


            setMessage(
                "Password reset successfully. Redirecting to login..."
            );


            setTimeout(() => {

                navigate("/login");

            }, 2000);


        } catch (error) {

            setError(
                error?.message ||
                "Unable to reset password. The token may be invalid or expired."
            );

        } finally {

            setLoading(false);

        }

    };


    return (

        <div className="reset-password-page">

            <div className="reset-password-card">

                <div className="reset-password-header">

                    <div className="reset-password-icon">
                        🔐
                    </div>


                    <h1>
                        Reset Password
                    </h1>


                    <p>
                        Enter your new password below.
                    </p>

                </div>


                <form
                    className="reset-password-form"
                    onSubmit={handleSubmit}
                >

                    <Input
                        label="New Password"
                        type="password"
                        name="password"
                        value={password}
                        placeholder="Enter your new password"
                        onChange={(e) =>
                            setPassword(e.target.value)
                        }
                        required
                    />


                    <Input
                        label="Confirm Password"
                        type="password"
                        name="confirmPassword"
                        value={confirmPassword}
                        placeholder="Confirm your new password"
                        onChange={(e) =>
                            setConfirmPassword(e.target.value)
                        }
                        required
                    />


                    <Button
                        type="submit"
                        disabled={loading}
                    >

                        {loading
                            ? "Resetting..."
                            : "Reset Password"
                        }

                    </Button>

                </form>


                {error && (

                    <div className="reset-password-error">

                        {error}

                    </div>

                )}


                {message && (

                    <div className="reset-password-message">

                        {message}

                    </div>

                )}


                <div className="reset-password-footer">

                    <Link to="/login">
                        Back to Login
                    </Link>

                </div>

            </div>

        </div>

    );

};


export default ResetPassword;