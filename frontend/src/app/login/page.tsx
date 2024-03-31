"use client";

import React, {Fragment, useEffect} from "react";
import Link from "next/link";
import {useFormState} from "react-dom";
import {useRouter} from "next/navigation";
import {login} from "@/lib/actions";

import SubmitButton from "@/components/UI/SubmitButton";

const LoginPage: React.FC = () => {
    const [loginState, loginAction] = useFormState(login, { message: '', errors: '' });
    const router = useRouter();

    useEffect(() => {
        if (loginState.message && typeof loginState.message === 'object' && loginState.message?.username) {
            router.push("/", { scroll: false });
        }
    }, [loginState, router]);

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
                        <p>Not a member yet?</p>
                        <Link href="/register" className='hover:font-bold'>Sign up</Link>
                    </form>
                </div>
            </div>
        </Fragment>
    )
}
export default LoginPage;
