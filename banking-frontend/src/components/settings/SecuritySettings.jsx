import { useState } from "react";
import { toast } from "react-toastify";

import Card from "../ui/Card";

import authService from "../../services/auth/authService";

const SecuritySettings = () => {

    const [showChangePassword, setShowChangePassword] =
        useState(false);

    const [currentPassword, setCurrentPassword] =
        useState("");

    const [newPassword, setNewPassword] =
        useState("");

    const [confirmPassword, setConfirmPassword] =
        useState("");

    const [loading, setLoading] =
        useState(false);


    const handleChangePassword = async (event) => {

        event.preventDefault();


        if (newPassword !== confirmPassword) {

            toast.error(
                "New passwords do not match."
            );

            return;

        }


        if (newPassword.length < 8) {

            toast.error(
                "New password must be at least 8 characters."
            );

            return;

        }


        if (currentPassword === newPassword) {

            toast.error(
                "New password must be different from your current password."
            );

            return;

        }


        try {

            setLoading(true);


            await authService.changePassword({

                currentPassword,

                newPassword,

                confirmPassword

            });


            toast.success(
                "Password changed successfully."
            );


            setCurrentPassword("");

            setNewPassword("");

            setConfirmPassword("");

            setShowChangePassword(false);


        } catch (error) {

            toast.error(
                error?.message ||
                "Unable to change password."
            );


            console.error(error);

        } finally {

            setLoading(false);

        }

    };


    return (

        <Card title="Security">


            <div className="settings-option">

                <div>

                    <h3>
                        Password
                    </h3>

                    <p>
                        Change your account password.
                    </p>

                </div>


                <button
                    className="btn btn-primary"
                    onClick={() =>
                        setShowChangePassword(
                            !showChangePassword
                        )
                    }
                    disabled={loading}
                >

                    {
                        showChangePassword
                            ? "Cancel"
                            : "Change Password"
                    }

                </button>

            </div>


            {
                showChangePassword && (

                    <form
                        className="change-password-form"
                        onSubmit={handleChangePassword}
                    >

                        <div className="form-group">

                            <label htmlFor="currentPassword">
                                Current Password
                            </label>

                            <input
                                id="currentPassword"
                                type="password"
                                value={currentPassword}
                                onChange={(event) =>
                                    setCurrentPassword(
                                        event.target.value
                                    )
                                }
                                placeholder="Enter current password"
                                required
                                disabled={loading}
                            />

                        </div>


                        <div className="form-group">

                            <label htmlFor="newPassword">
                                New Password
                            </label>

                            <input
                                id="newPassword"
                                type="password"
                                value={newPassword}
                                onChange={(event) =>
                                    setNewPassword(
                                        event.target.value
                                    )
                                }
                                placeholder="Enter new password"
                                required
                                minLength={8}
                                disabled={loading}
                            />

                        </div>


                        <div className="form-group">

                            <label htmlFor="confirmPassword">
                                Confirm New Password
                            </label>

                            <input
                                id="confirmPassword"
                                type="password"
                                value={confirmPassword}
                                onChange={(event) =>
                                    setConfirmPassword(
                                        event.target.value
                                    )
                                }
                                placeholder="Confirm new password"
                                required
                                minLength={8}
                                disabled={loading}
                            />

                        </div>


                        <button
                            type="submit"
                            className="btn btn-primary"
                            disabled={loading}
                        >

                            {
                                loading
                                    ? "Changing Password..."
                                    : "Update Password"
                            }

                        </button>

                    </form>

                )
            }


        </Card>

    );

};

export default SecuritySettings;