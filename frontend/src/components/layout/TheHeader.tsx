"use client";
import React from "react";
import Link from "next/link";
import {signOut} from "@/lib/auth";

const TheHeader: React.FC = () => {

    return (
        <nav className="bg-gray-800 fixed w-full top-0 start-0 border-b-4 border-green-600 mb-8">
            <ul className="flex justify-end">
                <li>
                    <Link onClick={()=>signOut()} className="text-green-600 mx-3 hover:bg-green-900" href='/login'>Logout</Link>
                </li>

            </ul>
        </nav>
    )
}

export default TheHeader;