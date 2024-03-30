"use server";

import { jwtVerify, SignJWT } from "jose";
import { cookies } from "next/headers";
import {RequestCookie} from "next/dist/compiled/@edge-runtime/cookies";

export type Session = {
	id: number;
	username: string;
	roles: string[];
	expires: Date;
}

const secretKey = process.env["JTW_SECRET"];
const key = new TextEncoder().encode(secretKey);

export async function encrypt(payload: any) {
	return await new SignJWT(payload)
		.setProtectedHeader({ alg: "HS256" })
		.setIssuedAt()
		.setExpirationTime("1h")
		.sign(key);
}

export async function decrypt(session: string): Promise<any> {
	const { payload } = await jwtVerify(session, key, {
		algorithms: ["HS256"]
	});

	return payload;
}

export async function signIn(
	userData: { username: string; roles: string[]; id: number},
	jsession: string
): Promise<void> {
	const expires = new Date(Date.now() + (60 * 60 * 1000));

	const session = await encrypt({
		id: userData.id,
		username: userData.username,
		roles: userData.roles,
		expires
	});

	cookies().set("session", session, { expires, httpOnly: true });
	cookies().set("JSESSIONID", jsession, { expires, httpOnly: true, path: '/' });
}

export async function signOut() {
	cookies().set("session", "", { expires: new Date(0) });
	cookies().set("JSESSIONID", "", { expires: new Date(0) });
}

export async function getSession(): Promise<Session | null> {
	const session: string | undefined = cookies().get("session")?.value;

	if (!session) return null;

	return await decrypt(session);
}

export async function getCookies(): Promise<RequestCookie | undefined> {
	return cookies().get("JSESSIONID")
}