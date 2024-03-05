import React from "react";
import Link from "next/link";

import "./TheHeader.css";
import {signOut} from "@/lib/auth";

const TheHeader: React.FC = () => {
	return (
		<nav className="navbar">
			<ul>
				<li>
					<Link
						href="/"
					>
						Home
					</Link>
				</li>
				<li>
					<Link
						href="/login"
					>
						Login
					</Link>
				</li>
			</ul>
		</nav>
	);
};

export default TheHeader;
