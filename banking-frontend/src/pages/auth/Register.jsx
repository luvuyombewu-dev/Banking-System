import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { toast } from "react-toastify";

import authService from "../../services/auth/authService";
import useAuth from "../../hooks/useAuth";

import Input from "../../components/ui/Input";
import Button from "../../components/ui/Button";

import "../../assets/styles/pages/login.css";

const Register = () => {

    const navigate = useNavigate();

    const { login } = useAuth();

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [loading, setLoading] = useState(false);

    const handleSubmit = async (event) => {

        event.preventDefault();

        try {

            setLoading(true);

            const response = await authService.register({
                firstName,
                lastName,
                email,
                password
            });

            login(response);

            toast.success(
                "Registration successful"
            );

            navigate("/dashboard");

        } catch (error) {

            toast.error(
                error?.message ||
                "Registration failed."
            );

            console.error(error);

        } finally {

            setLoading(false);

        }

    };

    return (

        <div className="login-page">

            <div className="login-card">

                <h1>
                    Create Account
                </h1>

                <form
                    className="login-form"
                    onSubmit={handleSubmit}
                >

                    <Input
                        type="text"
                        name="firstName"
                        value={firstName}
                        placeholder="First Name"
                        onChange={(event) =>
                            setFirstName(event.target.value)
                        }
                        required
                    />

                    <Input
                        type="text"
                        name="lastName"
                        value={lastName}
                        placeholder="Last Name"
                        onChange={(event) =>
                            setLastName(event.target.value)
                        }
                        required
                    />

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
                        {loading
                            ? "Creating..."
                            : "Register"
                        }
                    </Button>

                </form>

                <p className="login-register">

                    Already have an account?{" "}

                    <Link to="/login">
                        Login
                    </Link>

                </p>

            </div>

        </div>

    );

};

export default Register;