"use client";

import dynamic from "next/dynamic";
import React, { Fragment } from "react";
import { useFormStatus } from "react-dom";
import {FormState} from "@/lib/actions";
import {ZodIssue} from "zod";

const Notification = dynamic(() => import('./Notification'));

type SubmitButtonProps = {
	formState: FormState;
	className?: string;
};

const SubmitButton: React.FC<SubmitButtonProps> = ({
	formState,
	className
}) => {
	const { pending } = useFormStatus();

	return (
		<Fragment>
			{!pending &&
				(
					formState.errors ||
					formState.message && typeof formState.message === "string"
				) && (
					<Notification
						status={formState.message ? "success" : "error"}
						timeout={4000}
					>
						{ formState.message && typeof formState.message === 'string' ?
							<span>{formState.message}</span> :

							(
								Array.isArray(formState.errors) ?

								formState.errors!.map((error: ZodIssue, index: number) => (
									<span key={index} className='block'>{error.message}</span>
								)) :

								<span>{formState.errors}</span>
							)
						}
					</Notification>
				)}
			<button
				type="submit"
				aria-disabled={pending}
				disabled={pending}
				className={`base-btn ${className ?? ""}`.trim()}
			>
				{pending ? "Submitting" : "Submit"}
			</button>
		</Fragment>
	);
};

export default SubmitButton;