"use client";
import React, {useEffect, useState} from "react";
import Link from "next/link";
import {getSession, signOut} from "@/lib/auth";

import "./TheHeader.css";

const TheHeader: React.FC = () => {
	const [session, setSession] = useState<{ username: string; } | {}>({})

	useEffect(() => {
		getSession()
			.then((response) => setSession(response))
	}, []);

	return (
		<nav className="navbar">
			<ul>
				<li>
					<Link
						href="/login"
						onClick={async () => {
							await signOut();
							setSession({});
						}}
					>
						{session && session.hasOwnProperty("username") ? "Logout" : "Login"}
					</Link>
				</li>
			</ul>
		</nav>
	);
};

export default TheHeader;
