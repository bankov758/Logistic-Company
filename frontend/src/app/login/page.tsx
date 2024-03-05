"use client";
import {login} from "@/lib/actions";
import Link from "next/link";
import {Fragment, useEffect} from "react";
import {useFormState} from "react-dom";
import {redirect} from "next/navigation";

const LoginPage = () => {
    const [loginState, loginAction] = useFormState(login, {message: null, errors: ''})

    useEffect(() => {
        if (loginState.message?.username) {
            redirect("/");
        }
    }, [loginState]);

    return (
        <Fragment>
            {loginState.errors && typeof loginState.errors === 'string' &&
                <div>{loginState.errors}</div>
            }
            {loginState.errors && typeof loginState.errors === 'object' &&
                <div>
                    {
                        loginState.errors
                            .map((error, index) =>
                                <p key={index}>{error.message}</p>
                            )
                    }
                </div>
            }
            <div>
                <div className="bg-white p-40 rounded-xl shadow-md text-center">
                    <div className="mb-5">
                        <h2>Login</h2>
                    </div>
                    <form action={loginAction}>
                        <div className="input-group">
                            <label className="block">Username</label>
                            <input type="text" name="username"
                                   className="input-style"></input>
                        </div>
                        <div className="input-group">
                            <label className="block">Password</label>
                            <input type="password" name="password"
                                   className="input-style"></input>
                        </div>
                        <div className="flex justify-center">
                            <button type="submit"
                                    className="block px-3 py-1.5 mt-1  relative rounded-xl bg-green-400 text-white border-none cursor-pointer"
                                    name="login_user">Login
                            </button>
                        </div>
                        <p>
                            Not a member yet? <Link href="/register">Sign up</Link>
                        </p>
                    </form>
                </div>
            </div>
        </Fragment>
    )
}
export default LoginPage;
