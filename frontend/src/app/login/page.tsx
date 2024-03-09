"use client";
import React, {Fragment, useEffect} from "react";
import Notification from "@/components/UI/Notification";
import {useFormState} from "react-dom";
import Link from "next/link";

import {useRouter} from "next/navigation";
import {FormState, login} from "@/lib/actions";
import {ZodError} from "zod";
import SubmitButton from "@/components/UI/SubmitButton";

const initialState: FormState = {
    message: '',
    errors: null
}

const LoginPage: React.FC = () => {
    const [loginState, loginAction] = useFormState(login, initialState);
    const router = useRouter();

    useEffect(() => {
        if (loginState.message && typeof loginState.message === 'object' && loginState.message?.username) {
            router.push("/", { scroll: false });
        }
    }, [loginState]);

    return (
        <Fragment>
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
                            <SubmitButton formState={loginState} />
                        </div>
                        <p>
                            Not a member yet?
                            <Link href="/register">Sign up</Link>
                        </p>
                    </form>
                </div>
            </div>
        </Fragment>
    )
}
export default LoginPage;
