import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { toast } from "react-toastify";

import authService from "../../services/auth/authService";
import { useAuthStore } from "../../store/authStore";

import Input from "../../components/ui/Input";
import Button from "../../components/ui/Button";

import "../../assets/styles/pages/login.css";

const Login = () => {

    const navigate = useNavigate();

    const { login } = useAuthStore();


    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);


    const handleSubmit = async (event) => {

        event.preventDefault();


        try {

            setLoading(true);


            const userData = await authService.login({

                email,
                password

            });


            login(userData);


            toast.success(
                `Welcome ${userData.firstName}!`
            );


            navigate("/dashboard");


        } catch (error) {

            toast.error(
                error?.message ||
                "Invalid email or password."
            );


            console.error(error);


        } finally {

            setLoading(false);

        }

    };


    const handleGoogleLogin = () => {

        toast.info(
            "Google Sign-In will be available soon."
        );

    };


    return (

        <div className="login-page">

            <div className="login-card">

                <h1>
                    Banking System
                </h1>


                <form
                    className="login-form"
                    onSubmit={handleSubmit}
                >

                    <Input
                        type="email"
                        name="email"
                        value={email}
                        placeholder="Email"
                        onChange={(event) =>
                            setEmail(event.target.value)
                        }
                        required
                    />


                    <Input
                        type="password"
                        name="password"
                        value={password}
                        placeholder="Password"
                        onChange={(event) =>
                            setPassword(event.target.value)
                        }
                        required
                    />


                    <Button
                        className="login-button"
                        type="submit"
                        disabled={loading}
                    >

                        {
                            loading
                                ? "Logging in..."
                                : "Login"
                        }

                    </Button>

                </form>


                <div className="login-divider">

                    <span>OR</span>

                </div>


                <button
                    type="button"
                    className="google-login-button"
                    onClick={handleGoogleLogin}
                    disabled={loading}
                >

                    <span className="google-icon">
                        G
                    </span>

                    <span>
                        Continue with Google
                    </span>

                </button>


                <p>

                    <Link to="/forgot-password">
                        Forgot password?
                    </Link>

                </p>


                <p className="login-register">

                    Don't have an account?{" "}

                    <Link to="/register">
                        Sign Up
                    </Link>

                </p>



            </div>

        </div>

    );

};

export default Login;