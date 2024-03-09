"use client";

import dynamic from "next/dynamic";
import React, { Fragment } from "react";
import { useFormStatus } from "react-dom";
import {FormState} from "@/lib/actions";
import {ZodIssue} from "zod";

const Notification = dynamic(() => import('./Notification'));

type SubmitButtonProps = {
	formState: FormState;
};

const SubmitButton: React.FC<SubmitButtonProps> = ({
   formState,
}) => {
	const { pending } = useFormStatus();

	return (
		<Fragment>
			{!pending &&
				(
					formState.errors && !formState.message ||
					formState.message && typeof formState.message === "string"
				) && (
				<Notification
					status={formState.message ? "success" : "error"}
					timeout={4000}
				>
					{formState.message ||
						(
							Array.isArray(formState.errors) ?
								formState.errors!.map((error: ZodIssue) => error.message).join("\n") :
								formState.errors
						)
					}
				</Notification>
			)}
			<button
				type="submit"
				aria-disabled={pending}
				disabled={pending}
				className={`base-btn`.trim()}
			>
				{pending ? "Submitting" : "Submit"}
			</button>
		</Fragment>
	);
};

export default SubmitButton;